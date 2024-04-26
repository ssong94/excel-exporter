package com.spring.boot.excelexporter.factory;

import com.spring.boot.excelexporter.container.ExcelExporter;
import com.spring.boot.excelexporter.container.OneSheetExcel;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExcelFactory {

	public static <T> ExcelExporter makeExcel(List<T> data, Class<T> tClass) {
		return OneSheetExcel.of(data, tClass, createWorkbook(true));
	}

	public static <T> ExcelExporter makeExcelForXSSFWorkbook(List<T> data, Class<T> tClass) {
		return OneSheetExcel.of(data, tClass, createXSSFWorkbook());
	}


	/**
	 * Workbook Factory Implement Produce
	 * @return workbook
	 */

	private static Workbook createWorkbook(boolean isCompressTempFile) {
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		workbook.setCompressTempFiles(isCompressTempFile);
		return workbook;
	}

	private static Workbook createXSSFWorkbook() {
		return new XSSFWorkbook();
	}


}
