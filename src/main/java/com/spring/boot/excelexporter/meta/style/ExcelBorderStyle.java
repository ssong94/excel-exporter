package com.spring.boot.excelexporter.meta.style;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.poi.ss.usermodel.BorderStyle;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelBorderStyle {
   BorderStyle top() default BorderStyle.THIN;
	BorderStyle bottom() default BorderStyle.THIN;
	BorderStyle left() default BorderStyle.THIN;
	BorderStyle right() default BorderStyle.THIN;
}
