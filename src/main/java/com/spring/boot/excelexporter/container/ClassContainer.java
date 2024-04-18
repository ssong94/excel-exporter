package com.spring.boot.excelexporter.container;

import com.spring.boot.excelexporter.exception.ExcelExporterException;
import com.spring.boot.excelexporter.meta.ExcelBody;
import com.spring.boot.excelexporter.meta.ExcelHeader;
import com.spring.boot.excelexporter.meta.ExcelSheet;
import com.spring.boot.excelexporter.meta.style.ExcelStyle;
import com.spring.boot.excelexporter.util.PoiUtil;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.StringUtils;

@Getter
@SuperBuilder
public class ClassContainer extends Excel {

	private static final int STANDARD_NUMBER = 1;

	private final Map<Field, CellStyle> headerFieldCellStyleMap;
	private final Map<Field, CellStyle> bodyFieldCellStyleMap;



	public static <T> ClassContainer from(List<T> data, Class<T> tClass, Workbook workbook) {

		validateAnnotation(tClass);
		Field[] fields = tClass.getDeclaredFields();

		Map<Field, CellStyle> headerStyleMap = createHeaderFieldStyleMap(fields, workbook);
		Map<Field, CellStyle> bodyStyleMap = createBodyFieldStyleMap(fields, workbook);

		ClassContainer container = ClassContainer.builder()
				.workbook(workbook)
				.headerFieldCellStyleMap(headerStyleMap)
				.bodyFieldCellStyleMap(bodyStyleMap)
				.build();

		Sheet sheet = createSheet(workbook, tClass);
		container.renderHeader(sheet, 0);
		container.renderBody(sheet, 1, data);

		return container;
	}



	private static void validateAnnotation(Class<?> clazz) {
		boolean hasAnnotation = clazz.isAnnotationPresent(ExcelSheet.class);
		String defaultMessage = "Exception occurred in class: " + clazz.getName() + " Cause By: ";
		if (!hasAnnotation) {
			throw new ExcelExporterException(defaultMessage + "ExcelSheet 어노테이션이 존재하지 않습니다.");
		}
	}

	private static Map<Field, CellStyle> createHeaderFieldStyleMap(Field[] fields, Workbook workbook) {
		Map<Field, CellStyle> resultMap = new HashMap<>();
		for (Field field : fields) {
			if(field.isAnnotationPresent(ExcelHeader.class)) {

				ExcelHeader annotation = field.getAnnotation(ExcelHeader.class);
				ExcelStyle excelStyle = annotation.headerStyle();

				CellStyle cellStyle = getCellStyleAppliedFont(excelStyle, workbook);

				resultMap.put(field, cellStyle);
			}
		}

		return resultMap;
	}

	private static Map<Field, CellStyle> createBodyFieldStyleMap(Field[] fields, Workbook workbook) {
		Map<Field, CellStyle> resultMap = new HashMap<>();
		for (Field field : fields) {
			if(field.isAnnotationPresent(ExcelBody.class)) {

				ExcelBody annotation = field.getAnnotation(ExcelBody.class);
				ExcelStyle excelStyle = annotation.bodyStyle();

				CellStyle cellStyle = getCellStyleAppliedFont(excelStyle, workbook);

				resultMap.put(field, cellStyle);
			}
		}

		return resultMap;
	}


	protected static Sheet createSheet(Workbook workbook, Class<?> clazz) {
		ExcelSheet sheet = clazz.getAnnotation(ExcelSheet.class);
		return workbook.createSheet(sheet.name());
	}

	@Override
	void renderHeader(Sheet sheet, int startRowIndex) {
		Row row = sheet.createRow(startRowIndex);

		headerFieldCellStyleMap.forEach((field, style) -> {
			ExcelHeader header = field.getAnnotation(ExcelHeader.class);
			int index = header.order();
			String name = header.name();
			String mergedRegion = header.mergedRegion();
			float columnWidth = header.columnWidth();

			if(StringUtils.hasText(mergedRegion)) {
				sheet.addMergedRegion(CellRangeAddress.valueOf(mergedRegion));
			}

			int width = PoiUtil.calculateWidth(columnWidth);
			sheet.setColumnWidth(index, width);

			Cell cell = row.createCell(index);
			cell.setCellStyle(style);
			cell.setCellValue(name);

		});

	}

	@Override
	<T> void renderBody(Sheet sheet, int startRowIndex, List<T> dataList) {

		if(dataList == null || dataList.isEmpty()) return;

		for (T data : dataList) {
			Row row = sheet.createRow(startRowIndex++);
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
				renderCellValue(cell, o);


			} catch (NoSuchFieldException | IllegalAccessException e) {
				throw new ExcelExporterException(e);
			}
		});
	}

	private void renderCellValue(Cell cell, Object object) {
		String cellValue = String.valueOf(object);
		cell.setCellValue(cellValue == null ? "" : cellValue);
	}

}
