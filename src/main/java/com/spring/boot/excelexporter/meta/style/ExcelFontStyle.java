package com.spring.boot.excelexporter.meta.style;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Font;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelFontStyle {
	String fontName() default "나눔 고딕";
	boolean bold() default false;
	byte underline() default Font.U_NONE;
	HSSFColorPredefined fontColor() default HSSFColorPredefined.BLACK;
	short fontSize() default 11;
}
