package com.spring.boot.excelexporter.container;

import com.spring.boot.excelexporter.exception.ExcelExporterException;
import com.spring.boot.excelexporter.meta.style.ExcelStyle;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MimeTypeUtils;



@Slf4j
@SuperBuilder
public abstract class Excel implements ExcelExporter {

	private final static String CONTENT_TYPE = MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE;
	private final static String CONTENT_DISPOSITION = HttpHeaders.CONTENT_DISPOSITION;
	private final static String HEADER = "\"attachment; filename=%s.xlsx\"";

	protected final Workbook workbook;

	abstract void renderHeader(Sheet sheet, int rowStartIndex);
	abstract <T> void renderBody(Sheet sheet, int startRowIndex, List<T> data);


	static CellStyle getCellStyleAppliedFont(ExcelStyle excelStyle, Workbook workbook) {
		CellStyle cellStyle = getCellStyle(excelStyle, workbook);
		Font font = getFont(excelStyle, workbook);
		cellStyle.setFont(font);
		return cellStyle;
	}


	static CellStyle getCellStyle(ExcelStyle excelStyle, Workbook workbook) {
		HorizontalAlignment horizontalAlignment = excelStyle.horizontalAlign();
		VerticalAlignment verticalAlignment = excelStyle.verticalAlign();
		boolean isWrapText = excelStyle.wrapText();

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(horizontalAlignment);
		cellStyle.setVerticalAlignment(verticalAlignment);
		cellStyle.setWrapText(isWrapText);

		return cellStyle;
	}

	static Font getFont(ExcelStyle excelStyle, Workbook workbook) {

		String fontName = excelStyle.fontName();
		short fontSize = excelStyle.fontSize();
		boolean isBold = excelStyle.bold();
		HSSFColorPredefined fontColor = excelStyle.fontColor();

		Font font = workbook.createFont();
		font.setFontName(fontName);
		font.setColor(fontColor.getIndex());
		font.setBold(isBold);
		font.setFontHeightInPoints(fontSize);

		return font;
	}


	protected void mergeRegion(Sheet sheet, String region) {
		CellRangeAddress cellAddresses = CellRangeAddress.valueOf(region);
		sheet.addMergedRegion(cellAddresses);
	}


	@Override
	public void export(HttpServletResponse response, String fileName) {

		String formatFileName = String.format(HEADER, fileName);

		String encodeFileName = URLEncoder.encode(formatFileName, StandardCharsets.UTF_8);
		response.setContentType(CONTENT_TYPE);
		response.setHeader(CONTENT_DISPOSITION, encodeFileName + ";");

		try (ServletOutputStream out = response.getOutputStream()) {
			workbook.write(out);
		} catch (IOException ex) {
			throw new RuntimeException("Failed Excel Export", ex);
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}


	@Override
	public boolean save(String filePath) {
		try {
			Path path = Path.of(filePath);
			FileUtils.createParentDirectories(path.getParent().toFile());
		} catch (IOException e) {
			throw new ExcelExporterException(e);
		}

		try(FileOutputStream out = new FileOutputStream(filePath)) {
			workbook.write(out);
		} catch (IOException e) {
			throw new ExcelExporterException(e);
		}

		return true;
	}


}
