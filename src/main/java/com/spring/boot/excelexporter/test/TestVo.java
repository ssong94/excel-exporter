package com.spring.boot.excelexporter.test;

import com.spring.boot.excelexporter.meta.ExcelBody;
import com.spring.boot.excelexporter.meta.ExcelHeader;
import com.spring.boot.excelexporter.meta.ExcelSheet;
import com.spring.boot.excelexporter.meta.style.ExcelStyle;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

@ExcelSheet(name = "시트명")
@AllArgsConstructor
public class TestVo {

	@ExcelHeader(name = "헤더명이 너무 길면 어떻게 되지", order = 0,
			headerStyle = @ExcelStyle(
					fontSize = 11,
					bold = true,
					fontName = "맑은 고딕",
//					wrapText = true,
					fontColor = HSSFColorPredefined.RED,
					horizontalAlign = HorizontalAlignment.LEFT
			)
	)
	@ExcelBody(order = 0,
			bodyStyle = @ExcelStyle(
					fontSize = 30,
					fontColor = HSSFColorPredefined.BLUE
			))
	private int column1;

	@ExcelHeader(name = "헤더명2", order = 1)
	@ExcelBody(order = 1, autoSize = true, grouping = true,
			bodyStyle = @ExcelStyle(
			fontSize = 12,
			fontColor = HSSFColorPredefined.DARK_YELLOW
	))
	private String column2;


	@ExcelBody(order = 2, autoSize = false)
	private String column3;
}
