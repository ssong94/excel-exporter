package com.spring.boot.excelexporter.factory;

import com.spring.boot.excelexporter.container.ClassContainer;
import com.spring.boot.excelexporter.container.ExcelExporter;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExcelFactory {

	public static <T> ExcelExporter makeExcel(List<T> data, Class<T> tClass) {
		return ClassContainer.from(data, tClass, createSXSSFWorkbook());
	}

	public static <T> ExcelExporter makeExcelForXSSFWorkbook(List<T> data, Class<T> tClass) {
		return ClassContainer.from(data, tClass, createXSSFWorkbook());
	}


	/**
	 * Workbook Factory Implement Produce
	 * @return workbook
	 */
	private static Workbook createXSSFWorkbook() {
		return new XSSFWorkbook();
	}
	private static Workbook createSXSSFWorkbook() {
		return new SXSSFWorkbook();
	}

}
