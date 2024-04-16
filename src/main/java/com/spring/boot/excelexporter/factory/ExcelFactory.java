package com.spring.boot.excelexporter.factory;

import com.spring.boot.excelexporter.container.ClassContainer;
import com.spring.boot.excelexporter.container.ExcelExporter;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExcelFactory {
	public static <T> ExcelExporter makeExcel(List<T> data, Class<T> tClass) {
		return ClassContainer.from(data, tClass);
	}

}
