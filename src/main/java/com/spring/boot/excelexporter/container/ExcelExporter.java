package com.spring.boot.excelexporter.container;

import jakarta.servlet.http.HttpServletResponse;

public interface ExcelExporter {
	void export(HttpServletResponse response, String fileName);
	boolean save(String path);
}
