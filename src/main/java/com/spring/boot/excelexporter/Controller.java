package com.spring.boot.excelexporter;

import com.spring.boot.excelexporter.container.ExcelExporter;
import com.spring.boot.excelexporter.factory.ExcelFactory;
import com.spring.boot.excelexporter.test.TestVo;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
@RequestMapping
public class Controller {

	@GetMapping("/excel-download")
	void test(HttpServletResponse res) {
		TestVo testVo = new TestVo(-1234, "data1", 0.1, LocalDate.now(), LocalDateTime.now(), "value", 1);
		TestVo testVo1 = new TestVo(0, "data2", 0.2, LocalDate.now(), LocalDateTime.now(), "value", 12);

		List<TestVo> list = List.of(testVo, testVo1);

		ExcelExporter excel = ExcelFactory.makeExcel(list, TestVo.class);
		excel.export(res, "파일 명");
	}
}
