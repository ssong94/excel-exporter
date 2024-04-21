package com.spring.boot.excelexporter.meta;

import com.spring.boot.excelexporter.meta.style.ExcelAlignmentStyle;
import com.spring.boot.excelexporter.meta.style.ExcelBackgroundStyle;
import com.spring.boot.excelexporter.meta.style.ExcelBorderStyle;
import com.spring.boot.excelexporter.meta.style.ExcelCellStyle;
import com.spring.boot.excelexporter.meta.style.ExcelFontStyle;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelHeader {
	String name();
	int order();
//	String mergedRegion() default "";
	float columnWidth() default 20.10f;


	/* Custom 공통 영역 */
	ExcelAlignmentStyle alignmentStyle() default @ExcelAlignmentStyle;
	ExcelBackgroundStyle backgroundStyle() default @ExcelBackgroundStyle(
			color = IndexedColors.GREY_25_PERCENT
	);

	ExcelBorderStyle borderStyle() default @ExcelBorderStyle;
	ExcelFontStyle fontStyle() default @ExcelFontStyle(
			fontSize = 14,
			fontColor = HSSFColorPredefined.RED,
			bold = true,
			fontName = "맑은 고딕",
			underline = Font.U_SINGLE
	);
	ExcelCellStyle cellStyle() default @ExcelCellStyle(
			wrapText = true
	);

}
