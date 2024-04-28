package com.spring.boot.excelexporter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spring.boot.excelexporter.container.ExcelExporter;
import com.spring.boot.excelexporter.factory.ExcelFactory;
import com.spring.boot.excelexporter.sample.MultiSheetSample01;
import com.spring.boot.excelexporter.sample.MultiSheetSample02;
import com.spring.boot.excelexporter.sample.SampleVo;
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

	String path = "C:\\Users\\kyugw\\OneDrive\\바탕 화면\\test\\sample.xlsx";

	@DisplayName("기본 엑셀 결과 출력")
	@Test
	void exportDefaultExcel() throws IOException {
		Random random = new Random();

		List<SampleVo> list = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			SampleVo sampleVo = new SampleVo(random.nextInt(), "data" + i, Math.random(), LocalDate.now(),
					LocalDateTime.now(), "value" + i, i);
			list.add(sampleVo);
		}

		ExcelExporter excel = ExcelFactory.makeExcel(list, SampleVo.class);
		excel.save(path);

		assertTrue(Files.exists(Path.of(path)));
	}


	@DisplayName("150만건 테스트")
	@Test
	void exportBigDataExportTest() throws IOException {
		Random random = new Random();

		LocalDate from = LocalDate.of(2016, 1, 1);
		LocalDate to = LocalDate.now();

		List<SampleVo> list = new ArrayList<>();

		for (int i = 0; i < 1_100_000; i++) {

			long days = from.until(to, ChronoUnit.DAYS);
			long randomDays = ThreadLocalRandom.current().nextLong(days + 1);
			LocalDate randomDate = from.plusDays(randomDays);

			int randomSeconds = new Random().nextInt(3600 * 24);
			LocalDateTime anyTime = LocalDateTime.now().minusSeconds(randomSeconds);

			SampleVo sampleVo = new SampleVo(random.nextInt(), "value" + i, Math.random(), randomDate, anyTime,
					i + "value", random.nextInt() / 100);
			list.add(sampleVo);
		}

		ExcelExporter excel = ExcelFactory.makeExcel(list, SampleVo.class);
		excel.save(path);

		assertTrue(Files.exists(Path.of(path)));
	}


	@DisplayName("다중 시트 출력")
	@Test
	void exportMultiSheetTest01() {
		ExcelExporter excel = ExcelFactory.makeEmptyExcel();

		SampleVo sampleVo = new SampleVo(-1234, "data1", 0.1, LocalDate.now(), LocalDateTime.now(), "value", 1);
		SampleVo sampleVo1 = new SampleVo(0, "data2", 0.2, LocalDate.now(), LocalDateTime.now(), "value", 12);
		List<SampleVo> list = List.of(sampleVo, sampleVo1);

		MultiSheetSample01 testVo21 = new MultiSheetSample01(-1234, "data1", 1000);
		MultiSheetSample01 testVo22 = new MultiSheetSample01(0, "data2", 2000);
		List<MultiSheetSample01> list2 = List.of(testVo21, testVo22);

		excel.appendSheet(list, SampleVo.class);
		excel.appendSheet(list2, MultiSheetSample01.class);

		excel.save(path);
		assertTrue(Files.exists(Path.of(path)));
	}

	@DisplayName("다중 시트 출력")
	@Test
	void exportMultiSheetTest02() {

		SampleVo sampleVo = new SampleVo(-1234, "data1", 0.1, LocalDate.now(), LocalDateTime.now(), "value", 1);
		SampleVo sampleVo1 = new SampleVo(0, "data2", 0.2, LocalDate.now(), LocalDateTime.now(), "value", 12);
		List<SampleVo> list = List.of(sampleVo, sampleVo1);

		ExcelExporter excel = ExcelFactory.makeExcel(list, SampleVo.class);

		MultiSheetSample01 sample01 = new MultiSheetSample01(-1234, "data1", 1000);
		MultiSheetSample01 sample02 = new MultiSheetSample01(0, "data2", 2000);
		List<MultiSheetSample01> list2 = List.of(sample01, sample02);

		excel.appendSheet(list2, MultiSheetSample01.class);

		MultiSheetSample02 sample021 = new MultiSheetSample02(-1234, "data1", 1000);
		MultiSheetSample02 sample022 = new MultiSheetSample02(0, "data2", 2000);
		List<MultiSheetSample02> list3 = List.of(sample021, sample022);

		excel.appendSheet(list3, MultiSheetSample02.class);

		excel.save(path);
		assertTrue(Files.exists(Path.of(path)));
	}


}
