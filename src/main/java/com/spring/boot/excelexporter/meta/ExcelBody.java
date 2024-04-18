package com.spring.boot.excelexporter.meta;

import com.spring.boot.excelexporter.meta.style.ExcelStyle;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelBody {

	int order();
	boolean autoSize() default true;
	boolean grouping() default false;
	ExcelStyle bodyStyle() default @ExcelStyle;

}
