package com.spring.boot.excelexporter.meta;

import com.spring.boot.excelexporter.meta.style.ExcelStyle;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelHeader {
	String name();
	int order();
	String mergedRegion() default "";
	ExcelStyle headerStyle() default @ExcelStyle(
			fontSize = 14,
			isFontBold = true,
			isWrapText = true
	);


}