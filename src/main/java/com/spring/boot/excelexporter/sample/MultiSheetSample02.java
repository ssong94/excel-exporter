package com.spring.boot.excelexporter.sample;

import com.spring.boot.excelexporter.meta.ExcelBody;
import com.spring.boot.excelexporter.meta.ExcelHeader;
import com.spring.boot.excelexporter.meta.ExcelSheet;
import lombok.AllArgsConstructor;

@ExcelSheet(name = "Sheet 2")
@AllArgsConstructor
public class MultiSheetSample02 {

	@ExcelHeader(name = "header4", order = 0)
	@ExcelBody(order = 0, dataFormat = "0;[Red]-0")
	private int number;

	@ExcelHeader(name = "header5", order = 1)
	@ExcelBody(order = 1)
	private String value;

	@ExcelHeader(name = "header6", order = 2)
	@ExcelBody(order = 2)
	private int won;
}
