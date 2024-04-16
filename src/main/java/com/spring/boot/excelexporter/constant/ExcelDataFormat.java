package com.spring.boot.excelexporter.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExcelDataFormat {

	USD("$#,##0.00"),
	RUR("#,##0.00 [$р.-419];-#,##0.00 [$р.-419]"),
	PERCENTAGE("0.00%"),
	NUMBER("#,##0.00"),
	DATE("D MMM YY;@"),
	TIME("[$-F400]H:MM:SS AM/PM"),
	NONE("");

	private final String formatPattern;

}
