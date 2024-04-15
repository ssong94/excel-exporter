package com.spring.boot.excelexporter.exception;

public class ExcelExporterException extends RuntimeException {

	public ExcelExporterException() {
		super();
	}
	public ExcelExporterException(String message) {
		super(message);
	}

	public ExcelExporterException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcelExporterException(Throwable cause) {
		super(cause);
	}


}
