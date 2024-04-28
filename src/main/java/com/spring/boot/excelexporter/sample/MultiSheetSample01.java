package com.spring.boot.excelexporter.sample;

import com.spring.boot.excelexporter.meta.ExcelBody;
import com.spring.boot.excelexporter.meta.ExcelHeader;
import com.spring.boot.excelexporter.meta.ExcelSheet;
import lombok.AllArgsConstructor;

@ExcelSheet(name = "Sheet 1")
@AllArgsConstructor
public class MultiSheetSample01 {

	@ExcelHeader(name = "header1", order = 0)
	@ExcelBody(order = 0, dataFormat = "0;[Red]-0")
	private int header;

	@ExcelHeader(name = "header2", order = 1)
	@ExcelBody(order = 1)
	private String value;

	@ExcelHeader(name = "header3", order = 3)
	@ExcelBody(order = 3)
	private int won;
}
