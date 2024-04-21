package com.spring.boot.excelexporter.container;

import com.spring.boot.excelexporter.exception.ExcelExporterException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MimeTypeUtils;


@Slf4j
@SuperBuilder
public abstract class Excel implements ExcelExporter {

	private final static String CONTENT_TYPE = MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE;
	private final static String CONTENT_DISPOSITION = HttpHeaders.CONTENT_DISPOSITION;

	protected final Workbook workbook;
	private CreationHelper createHelper;

	abstract Sheet renderSheet(Workbook workbook, Class<?> clazz);

	abstract void renderHeader(Sheet sheet, int rowStartIndex);

	abstract <T> void renderBody(Sheet sheet, int startRowIndex, List<T> data);



	protected void mergeRegion(Sheet sheet, String region) {
		CellRangeAddress cellAddresses = CellRangeAddress.valueOf(region);
		sheet.addMergedRegion(cellAddresses);
	}


	@Override
	public void export(HttpServletResponse response, String fileName) {

		String encodeFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
		response.setContentType(CONTENT_TYPE);
		response.setHeader(CONTENT_DISPOSITION, "attachment; filename=" + encodeFileName + ".xlsx;");

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
			Files.createDirectories(path.getParent());
		} catch (IOException e) {
			throw new ExcelExporterException(e);
		}

		try (FileOutputStream out = new FileOutputStream(filePath)) {
			workbook.write(out);
		} catch (IOException e) {
			throw new ExcelExporterException(e);
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
		return true;
	}

	protected short formatPattern(String formatPattern) {
		if(createHelper == null) {
			createHelper = workbook.getCreationHelper();
		}
		DataFormat dataFormat = createHelper.createDataFormat();
		return dataFormat.getFormat(formatPattern);
	}

}
