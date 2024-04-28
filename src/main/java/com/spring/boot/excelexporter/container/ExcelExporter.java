package com.spring.boot.excelexporter.container;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public interface ExcelExporter {
	void export(HttpServletResponse response, String fileName);
	boolean save(String path);
	<T> void appendSheet(List<T> data, Class<T> tClass);
}
