package com.spring.boot.excelexporter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spring.boot.excelexporter.container.ExcelExporter;
import com.spring.boot.excelexporter.factory.ExcelFactory;
import com.spring.boot.excelexporter.test.TestVo;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExcelExportHelperApplicationTests {

	String path = "C:\\Users\\kyugw\\OneDrive\\바탕 화면\\test\\test.xlsx";

	@org.junit.jupiter.api.Test
	void excelCreateTest() throws IOException {

		TestVo testVo = new TestVo(-1234, "data1", 0.1, LocalDate.now(), LocalDateTime.now(), "value", 1);
		TestVo testVo1 = new TestVo(0, "data1", 0.2, LocalDate.now(), LocalDateTime.now(), "value", 12);
		TestVo testVo2 = new TestVo(-1, "data1", 0.3, LocalDate.now(), LocalDateTime.now(), "value", -1313);
		TestVo testVo3 = new TestVo(31, "data1", 0.5, LocalDate.now(), LocalDateTime.now(), "value", 155);
		TestVo testVo4 = new TestVo(124, "data1", 0.7, LocalDate.now(), LocalDateTime.now(), "value", 166);
		TestVo testVo5 = new TestVo(234, "data1", 0.8, LocalDate.now(), LocalDateTime.now(), "value", -10110);
		TestVo testVo6 = new TestVo(2, "data1", 0.9, LocalDate.now(), LocalDateTime.now(), "value", 545);
		TestVo testVo7 = new TestVo(3, "data1", 1, LocalDate.now(), LocalDateTime.now(), "value", 10000);
		TestVo testVo8 = new TestVo(-1010, "data1", 2, LocalDate.now(), LocalDateTime.now(), "value", -12323);
		TestVo testVo9 = new TestVo(1010, "data1", 3, LocalDate.now(), LocalDateTime.now(), "value", 0);
		TestVo testVo10 = new TestVo(9999, "data1", 0.4, LocalDate.now(), LocalDateTime.now(), "value", 999999);
		TestVo testVo11 = new TestVo(-9999, "data1", 0.04, LocalDate.now(), LocalDateTime.now(), "value", -1010);
		TestVo testVo12 = new TestVo(10000, "data1", 0.03, LocalDate.now(), LocalDateTime.now(), "value", 1010);

		List<TestVo> list = List.of(testVo, testVo1, testVo2, testVo3, testVo4, testVo5, testVo6, testVo7, testVo8,
				testVo9, testVo10, testVo11, testVo12);

		ExcelExporter excel = ExcelFactory.makeExcel(list, TestVo.class);
		excel.save(path);

		assertTrue(Files.exists(Path.of(path)));

	}
}
