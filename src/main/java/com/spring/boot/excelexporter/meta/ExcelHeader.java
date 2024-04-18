package com.spring.boot.excelexporter.meta;

import com.spring.boot.excelexporter.meta.style.ExcelStyle;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelHeader {
	String name();
	int order();
	String mergedRegion() default "";
	float columnWidth() default 10.71f;
	ExcelStyle headerStyle() default @ExcelStyle(
			fontSize = 14,
			bold = true,
			wrapText = true,
			fontName = "맑은 고딕",
			fontColor = HSSFColorPredefined.RED
	);



}
