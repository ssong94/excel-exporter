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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExcelExportHelperApplicationTests {

	String path = "C:\\Users\\kyugw\\OneDrive\\바탕 화면\\test\\test.xlsx";

	@org.junit.jupiter.api.Test
	void excelCreateTest() throws IOException {

		TestVo testVo = new TestVo(-1234, "data1", 0.1, LocalDate.now(), LocalDateTime.now(), "value", 1);
		TestVo testVo1 = new TestVo(0, "data2", 0.2, LocalDate.now(), LocalDateTime.now(), "value", 12);
		TestVo testVo2 = new TestVo(-1, "data3", 0.3, LocalDate.now(), LocalDateTime.now(), "value", -1313);
		TestVo testVo3 = new TestVo(31, "data4", 0.5, LocalDate.now(), LocalDateTime.now(), "value", 155);
		TestVo testVo4 = new TestVo(124, "data5", 0.7, LocalDate.now(), LocalDateTime.now(), "value", 166);
		TestVo testVo5 = new TestVo(234, "data6", 0.8, LocalDate.now(), LocalDateTime.now(), "value", -10110);
		TestVo testVo6 = new TestVo(2, "data7", 0.9, LocalDate.now(), LocalDateTime.now(), "value", 545);
		TestVo testVo7 = new TestVo(3, "data8", 1, LocalDate.now(), LocalDateTime.now(), "value", 10000);
		TestVo testVo8 = new TestVo(-1010, "data9", 2, LocalDate.now(), LocalDateTime.now(), "value", -12323);
		TestVo testVo9 = new TestVo(1010, "data10", 3, LocalDate.now(), LocalDateTime.now(), "value", 0);
		TestVo testVo10 = new TestVo(9999, "data11", 0.4, LocalDate.now(), LocalDateTime.now(), "value", 999999);
		TestVo testVo11 = new TestVo(-9999, "data12", 0.04, LocalDate.now(), LocalDateTime.now(), "value", -1010);
		TestVo testVo12 = new TestVo(10000, "data13", 0.03, LocalDate.now(), LocalDateTime.now(), "value", 1010);

		List<TestVo> list = List.of(testVo, testVo1, testVo2, testVo3, testVo4, testVo5, testVo6, testVo7, testVo8,
				testVo9, testVo10, testVo11, testVo12);

		ExcelExporter excel = ExcelFactory.makeExcel(list, TestVo.class);
		excel.save(path);

		assertTrue(Files.exists(Path.of(path)));
	}


	@DisplayName("100만건 테스트")
	@Test
	void doBigDataExportTest() throws IOException {
		Random random = new Random();
		int maxSize = 100 * 1024 * 1024;
		int minSize = 10 * 1024 * 1024;

		LocalDate from = LocalDate.of(2016, 1, 1);
		LocalDate to = LocalDate.now();

		List<TestVo> list = new ArrayList<>();

		for (int i=0; i< 1000000; i++) {

			long days = from.until(to, ChronoUnit.DAYS);
			long randomDays = ThreadLocalRandom.current().nextLong(days + 1);
			LocalDate randomDate = from.plusDays(randomDays);

			int randomSeconds = new Random().nextInt(3600 * 24);
			LocalDateTime anyTime = LocalDateTime.now().minusSeconds(randomSeconds);

			TestVo testVo = new TestVo(random.nextInt(), "value" + i, Math.random() , randomDate, anyTime, i + "value" , random.nextInt() / 100);
			list.add(testVo);
		}

		ExcelExporter excel = ExcelFactory.makeExcel(list, TestVo.class);
		excel.save(path);

		assertTrue(Files.exists(Path.of(path)));
		assertTrue(Files.size(Path.of(path)) < maxSize);
		assertTrue(Files.size(Path.of(path)) > minSize);

	}
}
