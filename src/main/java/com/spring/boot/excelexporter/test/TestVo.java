package com.spring.boot.excelexporter.test;

import com.spring.boot.excelexporter.meta.ExcelBody;
import com.spring.boot.excelexporter.meta.ExcelHeader;
import com.spring.boot.excelexporter.meta.ExcelSheet;
import com.spring.boot.excelexporter.meta.style.ExcelAlignmentStyle;
import com.spring.boot.excelexporter.meta.style.ExcelBackgroundStyle;
import com.spring.boot.excelexporter.meta.style.ExcelFontStyle;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

@ExcelSheet(name = "시트명")
@AllArgsConstructor
public class TestVo {

	@ExcelHeader(name = "number", order = 0)
	@ExcelBody(order = 0, dataFormat = "0;[Red]-0")
	private int number;

	@ExcelHeader(name = "default", order = 1)
	@ExcelBody(order = 1)
	private String value;

	@ExcelHeader(name = "percent", order = 2)
	@ExcelBody(order = 2, dataFormat = "0%;[Red]-0")
	private double percent;

	@ExcelHeader(name = "Date", order = 3)
	@ExcelBody(order = 3, dataFormat = "yy-m-d")
	private LocalDate localDate;

	@ExcelHeader(name = "DateTime", order = 4, width = 10.00f)
	@ExcelBody(order = 4, dataFormat = "yy.m.d h:mm",
			alignmentStyle = @ExcelAlignmentStyle(
					horizontalAlign = HorizontalAlignment.LEFT
			)
	)
	private LocalDateTime localDateTime;

	@ExcelHeader(name = "Custom", order = 5,
	backgroundStyle = @ExcelBackgroundStyle(
			color = IndexedColors.BROWN),
			fontStyle = @ExcelFontStyle(
					fontSize = 13,
					fontName = "verdana",
					bold = true,
					underline = Font.U_SINGLE,
					fontColor = HSSFColorPredefined.YELLOW))
	@ExcelBody(order = 5, dataFormat = "yy/m/d h:mm")
	private String customValue;

	@ExcelHeader(name = "DateTime", order = 6, width = 10.00f)
	@ExcelBody(order = 6, dataFormat = "$#,##0;[Blue]-$#,##0")
	private int won;


}
