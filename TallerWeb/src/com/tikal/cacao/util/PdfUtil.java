package com.tikal.cacao.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public abstract class PdfUtil {
	
	static void agregarCelda(String contenidoCelda, Font fuente, PdfPTable tabla, BaseColor colorBorde, int alineamientoHorizontal) {
		PdfPCell celda = new PdfPCell(new Paragraph(contenidoCelda, fuente));
		celda.setBorderWidth(1);
		celda.setBorderColor(colorBorde);
		celda.setPadding(2.5F);

		celda.setHorizontalAlignment(alineamientoHorizontal);

		tabla.addCell(celda);
	}
	
	static void agregarCeldaConFondo(String contenidoCelda, Font fuente, PdfPTable tabla, BaseColor colorFondo, BaseColor colorBorde, int alineamientoHorizontal) {
		PdfPCell celda = new PdfPCell(new Paragraph(contenidoCelda, fuente));
		celda.setBorderWidth(1);
		celda.setBorderColor(colorBorde);
		celda.setPadding(2.5F);
		celda.setBackgroundColor(colorFondo);

		celda.setHorizontalAlignment(alineamientoHorizontal);

		tabla.addCell(celda);
	}

	static void agregarCeldaSinBorde(String contenidoCelda, Font fuente, PdfPTable tabla, int alineamientoVertical, int alineamientoHorizontal) {
		PdfPCell celda = new PdfPCell(new Paragraph(contenidoCelda, fuente));
		celda.setBorder(PdfPCell.NO_BORDER);
		celda.setPadding(2.5F);

		celda.setHorizontalAlignment(alineamientoHorizontal);
		celda.setVerticalAlignment(alineamientoVertical);

		tabla.addCell(celda);
	}
	
}
