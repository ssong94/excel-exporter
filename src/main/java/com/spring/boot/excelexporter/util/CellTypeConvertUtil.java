package com.spring.boot.excelexporter.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;

public class CellTypeConvertUtil {

	private static final DataFormatter formatter;

	static {
		formatter = new DataFormatter();
	}

	public static String toString(Cell cell) {
		String value = formatter.formatCellValue(cell);
		return value == null ? "" : value.trim();
	}




}
