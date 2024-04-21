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
import org.apache.poi.ss.usermodel.HorizontalAlignment;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelBody {

	int order();
	String dataFormat() default "";

	/* Custom 공통 영역 */
	ExcelAlignmentStyle alignmentStyle() default @ExcelAlignmentStyle(
			horizontalAlign = HorizontalAlignment.CENTER);

	ExcelBackgroundStyle backgroundStyle() default @ExcelBackgroundStyle;
	ExcelBorderStyle borderStyle() default @ExcelBorderStyle;
	ExcelFontStyle fontStyle() default @ExcelFontStyle;
	ExcelCellStyle cellStyle() default @ExcelCellStyle;


}
