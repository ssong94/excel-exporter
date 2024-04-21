package com.spring.boot.excelexporter.util;

import com.spring.boot.excelexporter.meta.ExcelBody;
import com.spring.boot.excelexporter.meta.ExcelHeader;
import com.spring.boot.excelexporter.meta.style.ExcelAlignmentStyle;
import com.spring.boot.excelexporter.meta.style.ExcelBackgroundStyle;
import com.spring.boot.excelexporter.meta.style.ExcelBorderStyle;
import com.spring.boot.excelexporter.meta.style.ExcelCellStyle;
import com.spring.boot.excelexporter.meta.style.ExcelFontStyle;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public final class StyleUtil {

	public static void applyBorderStyle(ExcelBorderStyle borderStyle, CellStyle cellStyle) {
		if (borderStyle == null) {
			return;
		}
		BorderStyle top = borderStyle.top();
		BorderStyle bottom = borderStyle.bottom();
		BorderStyle left = borderStyle.left();
		BorderStyle right = borderStyle.right();

		cellStyle.setBorderTop(top);
		cellStyle.setBorderBottom(bottom);
		cellStyle.setBorderLeft(left);
		cellStyle.setBorderRight(right);
	}

	public static void applyBackgroundStyle(ExcelBackgroundStyle background, CellStyle cellStyle) {
		if (background == null) {
			return;
		}
		IndexedColors color = background.color();
		FillPatternType fillPattern = background.pattern();

		cellStyle.setFillForegroundColor(color.getIndex());
		cellStyle.setFillPattern(fillPattern);
	}

	public static void applyAlignmentStyle(ExcelAlignmentStyle alignStyle, CellStyle cellStyle) {
		if (alignStyle == null) {
			return;
		}
		HorizontalAlignment horizontalAlignment = alignStyle.horizontalAlign();
		VerticalAlignment verticalAlignment = alignStyle.verticalAlign();

		cellStyle.setAlignment(horizontalAlignment);
		cellStyle.setVerticalAlignment(verticalAlignment);
	}

	public static CellStyle getHeaderCellStyle(ExcelHeader header, Workbook workbook) {
		return getCellStyle(workbook, header.alignmentStyle(), header.backgroundStyle(), header.borderStyle(),
				header.fontStyle(), header.cellStyle());
	}


	public static CellStyle getBodyCellStyle(ExcelBody body, Workbook workbook) {
		return getCellStyle(workbook, body.alignmentStyle(), body.backgroundStyle(), body.borderStyle(),
				body.fontStyle(),
				body.cellStyle());
	}

	private static CellStyle getCellStyle(Workbook workbook,
	                                      ExcelAlignmentStyle alignmentStyle,
	                                      ExcelBackgroundStyle background,
	                                      ExcelBorderStyle borderStyle,
	                                      ExcelFontStyle fontStyle,
	                                      ExcelCellStyle excelCellStyle) {
		CellStyle cellStyle = workbook.createCellStyle();
		applyAlignmentStyle(alignmentStyle, cellStyle);
		applyBackgroundStyle(background, cellStyle);
		applyBorderStyle(borderStyle, cellStyle);
		applyCellStyle(excelCellStyle, cellStyle);
		Font font = getFont(fontStyle, workbook);
		cellStyle.setFont(font);
		return cellStyle;
	}

	public static void applyCellStyle(ExcelCellStyle excelCellStyle, CellStyle cellStyle) {
		if(excelCellStyle == null) return;
		boolean wrapText = excelCellStyle.wrapText();
		cellStyle.setWrapText(wrapText);
	}

	public static Font getFont(ExcelFontStyle fontStyle, Workbook workbook) {
		Font font = workbook.createFont();
		if (fontStyle == null) {
			return font;
		}

		String fontName = fontStyle.fontName();
		short fontSize = fontStyle.fontSize();
		boolean bold = fontStyle.bold();
		HSSFColorPredefined fontColor = fontStyle.fontColor();
		byte underline = fontStyle.underline();

		font.setFontName(fontName);
		font.setColor(fontColor.getIndex());
		font.setBold(bold);
		font.setFontHeightInPoints(fontSize);
		font.setUnderline(underline);

		return font;
	}

}
