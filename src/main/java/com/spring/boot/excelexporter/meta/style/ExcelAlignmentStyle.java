package com.spring.boot.excelexporter.meta.style;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelAlignmentStyle {
	HorizontalAlignment horizontalAlign() default HorizontalAlignment.CENTER;
	VerticalAlignment verticalAlign() default VerticalAlignment.CENTER;

}
