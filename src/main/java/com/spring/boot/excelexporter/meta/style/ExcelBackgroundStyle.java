package com.spring.boot.excelexporter.meta.style;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelBackgroundStyle {
	IndexedColors color() default IndexedColors.WHITE;
	FillPatternType pattern() default FillPatternType.SOLID_FOREGROUND;
}
