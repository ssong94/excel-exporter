package com.spring.boot.excelexporter;

import com.spring.boot.excelexporter.container.ExcelExporter;
import com.spring.boot.excelexporter.factory.ExcelFactory;
import com.spring.boot.excelexporter.test.TestVo;
import java.io.IOException;
import java.util.List;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExcelExportHelperApplicationTests {

	String path = "C:\\Users\\kyugw\\OneDrive\\바탕 화면\\test\\test.xlsx";

	@org.junit.jupiter.api.Test
	void excelCreateTest() throws IOException {

		TestVo testVo = new TestVo(1, "data1", "data2");
		TestVo testVo1 = new TestVo(2, "data3", "data4");
		TestVo testVo2 = new TestVo(3, "데이터가 너무 길면", "data5");
		List<TestVo> testVo3 = List.of(testVo, testVo1, testVo2);


		ExcelExporter excel = ExcelFactory.makeExcel(testVo3, TestVo.class);

		excel.save(path);
		// save 후 resource 반환함.


	}
}
