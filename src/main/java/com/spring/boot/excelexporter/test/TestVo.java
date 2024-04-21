package com.spring.boot.excelexporter.test;

import com.spring.boot.excelexporter.meta.ExcelBody;
import com.spring.boot.excelexporter.meta.ExcelHeader;
import com.spring.boot.excelexporter.meta.ExcelSheet;
import lombok.AllArgsConstructor;

@ExcelSheet(name = "시트명")
@AllArgsConstructor
public class TestVo {

	@ExcelHeader(name = "헤더명이 너무 길면 어떻게 되지", order = 0)
	@ExcelBody(order = 0)
	private int column1;

	@ExcelHeader(name = "헤더명2", order = 1)
	@ExcelBody(order = 1)
	private String column2;


	@ExcelBody(order = 2)
	private String column3;
}
