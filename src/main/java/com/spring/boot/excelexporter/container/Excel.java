package com.spring.boot.excelexporter.container;

import com.spring.boot.excelexporter.exception.ExcelExporterException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MimeTypeUtils;


@Slf4j
public abstract class Excel implements ExcelExporter {

	private final static String CONTENT_TYPE = MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE;
	private final static String CONTENT_DISPOSITION = HttpHeaders.CONTENT_DISPOSITION;
	private CreationHelper createHelper;

	protected final Workbook workbook;
	protected Sheet sheet;

	abstract void renderSheet(Class<?> clazz);

	abstract void renderHeader(int rowStartIndex);

	abstract <T> void renderBody(int startRowIndex, List<T> data, Class<T> tClass);


	protected Excel(Workbook workbook) {
		this.workbook = workbook;
	}

	protected void mergeRegion(Sheet sheet, String region) {
		CellRangeAddress cellAddresses = CellRangeAddress.valueOf(region);
		sheet.addMergedRegion(cellAddresses);
	}


	@Override
	public void export(HttpServletResponse response, String fileName) {

		String encodeFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
		response.setContentType(CONTENT_TYPE);
		response.setHeader(CONTENT_DISPOSITION, "attachment; filename=" + encodeFileName + ".xlsx;");

		try {
			ServletOutputStream out = response.getOutputStream();
			write(out);
		} catch (IOException e) {
			throw new ExcelExporterException(e);
		}
	}


	@Override
	public boolean save(String filePath) {
		try {
			Path path = Path.of(filePath);
			Files.createDirectories(path.getParent());
			FileOutputStream out = new FileOutputStream(filePath);
			write(out);
		} catch (IOException e) {
			throw new ExcelExporterException(e);
		}

		return true;
	}

	private void write(OutputStream outputStream) {
		try (outputStream) {
			workbook.write(outputStream);
		} catch (IOException ex) {
			throw new RuntimeException("Failed Excel Export", ex);
		} finally {
			try {
				workbook.close();
				if (workbook instanceof SXSSFWorkbook) {
					((SXSSFWorkbook) workbook).dispose();
				}
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}

	protected short formatPattern(String formatPattern) {
		if (createHelper == null) {
			createHelper = workbook.getCreationHelper();
		}
		DataFormat dataFormat = createHelper.createDataFormat();
		return dataFormat.getFormat(formatPattern);
	}

}
