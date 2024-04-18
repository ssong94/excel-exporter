package com.spring.boot.excelexporter.util;

import org.apache.poi.util.Units;

public class PoiUtil {
	private static final short EXCEL_COLUMN_WIDTH_FACTOR = 256;

	public static int calculateWidth(float width) {
		return (int) Math.floor((width * Units.DEFAULT_CHARACTER_WIDTH + 5) / Units.DEFAULT_CHARACTER_WIDTH * EXCEL_COLUMN_WIDTH_FACTOR);
	}

}
