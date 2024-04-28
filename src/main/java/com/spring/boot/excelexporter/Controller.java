package com.spring.boot.excelexporter;

import com.spring.boot.excelexporter.container.ExcelExporter;
import com.spring.boot.excelexporter.factory.ExcelFactory;
import com.spring.boot.excelexporter.sample.SampleVo;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
@RequestMapping
public class Controller {

	@GetMapping("/excel-download")
	void test(HttpServletResponse res) {
		Random random = new Random();

		LocalDate from = LocalDate.of(2016, 1, 1);
		LocalDate to = LocalDate.now();

		List<SampleVo> list = new ArrayList<>();

		for (int i = 0; i < 100_000; i++) {

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
		excel.export(res, "파일명");
	}
}
