package com.spring.boot.excelexporter.util;

import com.spring.boot.excelexporter.exception.ExcelExporterException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public final class ReflectionUtil {

	private ReflectionUtil() {};

	public static <T> T createNewInstance(Class<T> clazz) {
		try {
			Constructor<T> constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(true);
			return constructor.newInstance();
		} catch (Exception e) {
			throw new ExcelExporterException("Cannot create a new instance of " + clazz.getName(), e);
		}
	}

	public static void setFieldData(Field field, Object o, Object instance) {
		try {
			field.setAccessible(true);
			field.set(instance, field);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new ExcelExporterException("Unexpected cast type {" + o + "} of field" + field.getName(), e);
		}
	}
}
