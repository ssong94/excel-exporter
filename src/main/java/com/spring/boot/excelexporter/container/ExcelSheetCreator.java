package com.spring.boot.excelexporter.container;

import static com.spring.boot.excelexporter.util.StyleUtil.getBodyCellStyle;
import static com.spring.boot.excelexporter.util.StyleUtil.getHeaderCellStyle;

import com.spring.boot.excelexporter.exception.ExcelExporterException;
import com.spring.boot.excelexporter.meta.ExcelBody;
import com.spring.boot.excelexporter.meta.ExcelHeader;
import com.spring.boot.excelexporter.meta.ExcelSheet;
import com.spring.boot.excelexporter.util.PoiUtil;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.StringUtils;

public class ExcelSheetCreator extends Excel {

	private final Map<Field, CellStyle> headerFieldCellStyleMap;
	private final Map<Field, CellStyle> bodyFieldCellStyleMap;

	private static final int DRAW_MAX_ROW_LIMIT = 1_000_000; // https://rowzero.io/blog/excel-row-limit
	private int currentRowCount = 0;


	public static <T> ExcelSheetCreator of(List<T> data, Class<T> tClass, Workbook workbook) {
		return new ExcelSheetCreator(data, tClass, workbook);
	}

	public static ExcelSheetCreator from(Workbook workbook) {
		return new ExcelSheetCreator(workbook);
	}

	private ExcelSheetCreator(Workbook workbook) {
		super(workbook);
		headerFieldCellStyleMap = Collections.emptyMap();
		bodyFieldCellStyleMap = Collections.emptyMap();
	}

	private <T> ExcelSheetCreator(List<T> data, Class<T> tClass, Workbook workbook) {
		super(workbook);
		validateAnnotation(tClass);
		Field[] fields = tClass.getDeclaredFields();
		headerFieldCellStyleMap = createHeaderFieldStyleMap(fields, workbook);
		bodyFieldCellStyleMap = createBodyFieldStyleMap(fields, workbook);
		make(data, tClass);
	}

	private <T> void make(List<T> data, Class<T> tClass) {
		_sheet = renderSheet(_workbook, tClass);
		renderHeader( 0);
		renderBody(1, data, tClass);
	}

	private void validateAnnotation(Class<?> clazz) {
		boolean hasAnnotation = clazz.isAnnotationPresent(ExcelSheet.class);
		String defaultMessage = "Exception occurred in class: " + clazz.getName() + " Cause By: ";
		if (!hasAnnotation) {
			throw new ExcelExporterException(defaultMessage + "ExcelSheet 어노테이션이 존재하지 않습니다.");
		}
	}

	private Map<Field, CellStyle> createHeaderFieldStyleMap(Field[] fields, Workbook workbook) {
		Map<Field, CellStyle> resultMap = new HashMap<>();
		for (Field field : fields) {
			if(field.isAnnotationPresent(ExcelHeader.class)) {

				ExcelHeader annotation = field.getAnnotation(ExcelHeader.class);

				CellStyle cellStyle = getHeaderCellStyle(annotation, workbook);

				resultMap.put(field, cellStyle);
			}
		}

		return resultMap;
	}

	private Map<Field, CellStyle> createBodyFieldStyleMap(Field[] fields, Workbook workbook) {
		Map<Field, CellStyle> resultMap = new HashMap<>();
		for (Field field : fields) {
			if(field.isAnnotationPresent(ExcelBody.class)) {

				ExcelBody annotation = field.getAnnotation(ExcelBody.class);

				CellStyle cellStyle = getBodyCellStyle(annotation, workbook);

				resultMap.put(field, cellStyle);
			}
		}

		return resultMap;
	}


	@Override
	Sheet renderSheet(Workbook workbook, Class<?> clazz) {
		ExcelSheet excelSheet = clazz.getAnnotation(ExcelSheet.class);
		Sheet sheet;
		try {
			sheet = workbook.createSheet(excelSheet.name());
		} catch (IllegalArgumentException e) {
			String sheetName = excelSheet.name() + " (" + workbook.getNumberOfSheets() + ")";
			sheet = workbook.createSheet(sheetName);
		}

		return sheet;
	}

	@Override
	void renderHeader(int startRowIndex) {
		Row row = _sheet.createRow(startRowIndex);

		headerFieldCellStyleMap.forEach((field, style) -> {
			ExcelHeader header = field.getAnnotation(ExcelHeader.class);
			int index = header.order();
			String name = header.name();
//			String mergedRegion = header.mergedRegion();
			float columnWidth = header.width();

//			if(StringUtils.hasText(mergedRegion)) {
//				sheet.addMergedRegion(CellRangeAddress.valueOf(mergedRegion));
//			}

			int width = PoiUtil.calculateWidth(columnWidth);
			_sheet.setColumnWidth(index, width);

			Cell cell = row.createCell(index);
			cell.setCellStyle(style);
			cell.setCellValue(name);

		});

	}

	@Override
	<T> void renderBody(int startRowIndex, List<T> dataList, Class<T> tClass) {

		if(dataList == null || dataList.isEmpty()) return;

		for (T data : dataList) {

			plusRowCount();

			if(isMaxRowCount()) {
				renderNextSheet(tClass);
				startRowIndex = 1;
			}

			Row row = _sheet.createRow(startRowIndex++);
			drawBody(row, data);
		}
	}

	private <T> void drawBody(Row row, Object object) {

		bodyFieldCellStyleMap.forEach((field, cellStyle) -> {
			try {
				Field declaredField = object.getClass().getDeclaredField(field.getName());
				declaredField.setAccessible(true);

				ExcelBody body = field.getAnnotation(ExcelBody.class);

				int cellIndex = body.order();
//				boolean grouping = body.grouping();

				Cell cell = row.createCell(cellIndex);
				Object o = declaredField.get(object);
				cell.setCellStyle(cellStyle);
				renderCellValue(cell, o);

				String pattern = body.dataFormat();
				if(StringUtils.hasText(pattern)) {
					short idx = formatPattern(pattern);
					cellStyle.setDataFormat(idx);
					cellStyle.setShrinkToFit(true);
				}

			} catch (NoSuchFieldException | IllegalAccessException e) {
				throw new ExcelExporterException(e);
			}
		});
	}

	private void renderCellValue(Cell cell, Object object) {

		if(object == null) {
			cell.setCellValue("");
			return;
		}


		switch (object) {
			case String s -> cell.setCellValue((String) s);
			case Integer i -> cell.setCellValue((Integer) i);
			case Long l -> cell.setCellValue((Long) l);
			case Float f -> cell.setCellValue((float) f);
			case Double d -> cell.setCellValue((double) d);
			case LocalDateTime ldt -> cell.setCellValue((LocalDateTime) ldt);
			case LocalDate ld -> cell.setCellValue((LocalDate) ld);
			case Boolean b -> cell.setCellValue((Boolean) b);
			default -> cell.setCellValue(String.valueOf(object) );
		}

	}

	private <T> void renderNextSheet(Class<T> tClass) {
		_sheet = renderSheet(_workbook, tClass);
		renderHeader(0);
		initRowCount();
	}


	private void plusRowCount() {
		currentRowCount++;
	}

	private void initRowCount() {
		currentRowCount = 0;
	}

	private boolean isMaxRowCount() {
		return currentRowCount > DRAW_MAX_ROW_LIMIT;
	}

	@Override
	public <T> void appendSheet(List<T> data, Class<T> tClass) {
		new ExcelSheetCreator(data, tClass, _workbook);
	}
}
