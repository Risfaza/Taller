package com.tikal.tallerWeb.util;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.tikal.tallerWeb.control.restControllers.VO.DatosPresupuestoVO;
import com.tikal.tallerWeb.control.restControllers.VO.GruposCosto;
import com.tikal.tallerWeb.modelo.entity.PresupuestoEntity;

public class PdfMaker {

	private DatosPresupuestoVO datos;
	private Document document;
	static Font font1 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
	static Font font2 = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
	static Font font3 = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL);
	static Font font4 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
	static Font font5 = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
	static Font font6 = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);

	public PdfMaker() throws DocumentException, IOException {
		this.document = new Document();
		this.document.setPageSize(PageSize.A4);
		this.document.setMargins(40, 40, 40, 40); // Left Right Top Bottom
	}

	public void imprimirPresupuesto() throws DocumentException, IOException {

		////////////////////////////////////////////////////////////////////////////// HEADER

		document.add(crearHeader());

		////////////////////////////////////////////////////////////////////////////// HEADER
		/////////////////////////////////////////////////////////////////////////////// TABLA1

		PdfPTable table1 = new PdfPTable(1);
		table1.setWidthPercentage(100);
		PdfPCell cell1table1 = new PdfPCell(new Paragraph("Datos del Cliente", font3));
		cell1table1.setBorderWidth(0);
		table1.addCell(cell1table1);
		document.add(table1);

		/////////////////////////////////////////////////////////////////////////////// TABLA1
		/////////////////////////////////////////////////////////////////////////////// TABLA2

		PdfPTable table2 = new PdfPTable(2);
		table2.setWidthPercentage(100);
		table2.setWidths(new int[] { 3, 7 });

		Phrase line1 = new Phrase();
		Chunk line1_1 = new Chunk("No. de Servicio ", font4);
		Chunk line1_2 = new Chunk(" ", font2);

		line1.add(line1_1);
		line1.add(line1_2);

		PdfPCell cell11table2 = new PdfPCell(line1);
		cell11table2.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell11table2.setBorderWidth(0);

		Phrase line2 = new Phrase();
		Chunk line2_1 = new Chunk("Fecha ", font4);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
		String date = sdf.format(new Date());
		Chunk line2_2 = new Chunk(date, font2);

		line2.add(line2_1);
		line2.add(line2_2);

		PdfPCell cell12table2 = new PdfPCell(line2);
		cell12table2.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell12table2.setBorderWidth(0);

		table2.addCell(cell11table2);
		table2.addCell(cell12table2);

		PdfPCell cell1table2 = new PdfPCell(new Paragraph("Nombre", font4));
		cell1table2.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell cell3table2 = new PdfPCell(new Paragraph("Dirección", font4));
		cell3table2.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell cell5table2 = new PdfPCell(new Paragraph("Teléfono", font4));
		cell5table2.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell cell7table2 = new PdfPCell(new Paragraph("e-mail", font4));
		cell7table2.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell cell9table2 = new PdfPCell(new Paragraph("Asesor", font4));
		cell9table2.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell cell2table2 = new PdfPCell(new Paragraph(datos.getNombre(), font2));
		cell2table2.setBorderWidthBottom(0);
		cell2table2.setBorderWidthLeft(0);

		PdfPCell cell4table2 = new PdfPCell(new Paragraph(datos.getDireccion(), font2));
		cell4table2.setBorderWidth(0);
		cell4table2.setBorderWidthRight(1);

		PdfPCell cell6table2 = new PdfPCell(new Paragraph(datos.getTelefono(), font2));
		cell6table2.setBorderWidth(0);
		cell6table2.setBorderWidthRight(1);

		PdfPCell cell8table2 = new PdfPCell(new Paragraph(datos.getEmail(), font2));
		cell8table2.setBorderWidth(0);
		cell8table2.setBorderWidthRight(1);

		PdfPCell cell10table2 = new PdfPCell(new Paragraph(datos.getAsesor() + " ", font2));
		cell10table2.setBorderWidthTop(0);
		cell10table2.setBorderWidthLeft(0);

		table2.addCell(cell1table2);
		table2.addCell(cell2table2);
		table2.addCell(cell3table2);
		table2.addCell(cell4table2);
		table2.addCell(cell5table2);
		table2.addCell(cell6table2);
		table2.addCell(cell7table2);
		table2.addCell(cell8table2);
		table2.addCell(cell9table2);
		table2.addCell(cell10table2);

		document.add(table2);
		///////////////////////////////////////////////////////////////////////////// TABLA2
		///////////////////////////////////////////////////////////////////////////// TABLA3

		PdfPTable table3 = new PdfPTable(1);
		table3.setWidthPercentage(100);
		PdfPCell cell1table3 = new PdfPCell(new Paragraph("Datos del Vehiculo", font3));
		cell1table3.setBorderWidth(0);
		table3.addCell(cell1table3);
		document.add(table3);

		///////////////////////////////////////////////////////////////////////////// TABLA3
		///////////////////////////////////////////////////////////////////////////// TABLA4

		PdfPTable table4 = new PdfPTable(7);
		table4.setWidthPercentage(100);

		PdfPCell cell1table4 = new PdfPCell(new Paragraph("Marca", font4));
		PdfPCell cell2table4 = new PdfPCell(new Paragraph("Tipo", font4));
		PdfPCell cell3table4 = new PdfPCell(new Paragraph("Modelo", font4));
		PdfPCell cell4table4 = new PdfPCell(new Paragraph("Color", font4));
		PdfPCell cell5table4 = new PdfPCell(new Paragraph("Placas", font4));
		PdfPCell cell6table4 = new PdfPCell(new Paragraph("KM", font4));
		PdfPCell cell7table4 = new PdfPCell(new Paragraph("Serie", font4));
		PdfPCell cell8table4 = new PdfPCell(new Paragraph(datos.getMarca(), font2));
		PdfPCell cell9table4 = new PdfPCell(new Paragraph(datos.getTipo(), font2));
		PdfPCell cell10table4 = new PdfPCell(new Paragraph(datos.getModelo(), font2));
		PdfPCell cell11table4 = new PdfPCell(new Paragraph(datos.getColor(), font2));
		PdfPCell cell12table4 = new PdfPCell(new Paragraph(datos.getPlacas(), font2));
		PdfPCell cell13table4 = new PdfPCell(new Paragraph(datos.getKilometros(), font2));
		PdfPCell cell14table4 = new PdfPCell(new Paragraph(datos.getSerie(), font2));
		Phrase linea = new Phrase();
		Chunk texto1 = new Chunk("    Servicio: ", font4);
		Chunk texto2 = new Chunk(datos.getServicio(), font2);
		linea.add(texto1);
		linea.add(texto2);
		PdfPCell cell15table4 = new PdfPCell(linea);
		cell15table4.setFixedHeight(35f);
		cell15table4.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell15table4.setColspan(7);

		table4.addCell(cell1table4);
		table4.addCell(cell2table4);
		table4.addCell(cell3table4);
		table4.addCell(cell4table4);
		table4.addCell(cell5table4);
		table4.addCell(cell6table4);
		table4.addCell(cell7table4);
		table4.addCell(cell8table4);
		table4.addCell(cell9table4);
		table4.addCell(cell10table4);
		table4.addCell(cell11table4);
		table4.addCell(cell12table4);
		table4.addCell(cell13table4);
		table4.addCell(cell14table4);
		table4.addCell(cell15table4);

		document.add(table4);

		///////////////////////////////////////////////////////////////////////////// TABLA4

		///////////////////////////////////////////////////////////////////////////// TABLA5

		if (datos.isConImagenes() == true) {
			PdfPTable table5 = new PdfPTable(2);
			PdfPCell cellh = new PdfPCell(new Paragraph("Inventario de Daños", font3));
			cellh.setColspan(2);
			cellh.setBorderWidth(0);
			table5.addCell(cellh);
			table5.setWidthPercentage(100);
			for (String path : datos.getListaImages()) {
				String ruta = "http://tikal.mx/tallerWeb/images/";
				Image img = Image.getInstance(new URL(ruta.concat(path)));
				img.scalePercent(50);
				PdfPCell cell = new PdfPCell(img, true);
				table5.addCell(cell);
			}

			document.add(table5);
		}

		///////////////////////////////////////////////////////////////////////////// TABLA5

		///////////////////////////////////////////////////////////////////////////// TABLA6

		PdfPTable table6 = new PdfPTable(2);
		table6.setWidthPercentage(100);
		table6.setWidths(new int[] { 3, 7 });
		PdfPCell cell1table6 = new PdfPCell(new Paragraph("Especificaciones", font3));
		cell1table6.setBorderWidth(0);
		cell1table6.setColspan(2);

		PdfPCell cell2table6 = new PdfPCell(new Paragraph("Nivel de Combustible", font4));
		cell2table6.setFixedHeight(35f);
		cell2table6.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell2table6.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell3table6 = new PdfPCell(new Paragraph(datos.getNivelCombustible(), font2));
		cell3table6.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell3table6.setHorizontalAlignment(Element.ALIGN_CENTER);

		PdfPCell cell4table6 = new PdfPCell(new Paragraph("Observaciones", font4));
		cell4table6.setFixedHeight(35f);
		cell4table6.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell4table6.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell5table6 = new PdfPCell(new Paragraph(datos.getObservaciones(), font2));
		cell5table6.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell5table6.setVerticalAlignment(Element.ALIGN_MIDDLE);

		table6.addCell(cell1table6);
		table6.addCell(cell2table6);
		table6.addCell(cell3table6);
		table6.addCell(cell4table6);
		table6.addCell(cell5table6);

		document.add(table6);

		///////////////////////////////////////////////////////////////////////////// TABLA6

		if (datos.isConFirmasSencillas() == true) {

			PdfPTable firmasTableS = new PdfPTable(2);

			firmasTableS.setWidthPercentage(100);

			PdfPCell em = new PdfPCell(new Paragraph(" "));
			em.setBorderWidth(0);

			firmasTableS.addCell(em);
			firmasTableS.addCell(em);

			PdfPCell fechaLabel = new PdfPCell(new Paragraph("Fecha ", font4));
			firmasTableS.addCell(fechaLabel);

			PdfPCell nombreFirmaLabel = new PdfPCell(new Paragraph("Nombre y Firma ", font4));
			firmasTableS.addCell(nombreFirmaLabel);

			PdfPCell fechaInput = new PdfPCell();
			fechaInput.setFixedHeight(60f);
			firmasTableS.addCell(fechaInput);

			PdfPCell nombreFirmaInput = new PdfPCell(new Paragraph("Autorizo la revisión " + "de la unidad", font4));
			nombreFirmaInput.setFixedHeight(50f);
			nombreFirmaInput.setVerticalAlignment(Element.ALIGN_BOTTOM);
			firmasTableS.addCell(nombreFirmaInput);

			firmasTableS.addCell(em);
			firmasTableS.addCell(em);

			document.add(firmasTableS);
		}

		//////////////////////////////////////////////////////////////////////////// TABLA7

		// Tamaño de la tabla es de 45

		if (datos.isConDiagnostico() == true) {

			document.newPage();
			document.add(crearHeader());
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			PdfPTable table7 = new PdfPTable(2);
			table7.setWidthPercentage(100);

			Phrase line1t7 = new Phrase();
			Chunk line1_1t7 = new Chunk("No. de Servicio ", font4);
			Chunk line1_2t7 = new Chunk(" ", font2);

			line1t7.add(line1_1t7);
			line1t7.add(line1_2t7);

			PdfPCell cell11table7 = new PdfPCell(line1t7);
			cell11table7.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11table7.setBorderWidth(0);

			Phrase line2t7 = new Phrase();
			Chunk line2_1t7 = new Chunk("Nombre Cliente ", font4);
			Chunk line2_2t7 = new Chunk(" ", font2);

			line2t7.add(line2_1t7);
			line2t7.add(line2_2t7);

			PdfPCell cell12table7 = new PdfPCell(line2t7);
			cell12table7.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell12table7.setBorderWidth(0);

			table7.addCell(cell11table7);
			table7.addCell(cell12table7);

			document.add(table7);

			//////////////////////////////////////////////////////////////////////////// TABLA7
			//////////////////////////////////////////////////////////////////////////// TABLA8

			NumberFormat formatter = NumberFormat.getCurrencyInstance();

			PdfPTable table8 = new PdfPTable(3);
			table8.setWidthPercentage(100);
			table8.setWidths(new int[] { 25, 60, 15 });

			PdfPCell cell1table8 = new PdfPCell(new Paragraph("Cantidad", font5));
			cell1table8.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1table8.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell1table8.setFixedHeight(35f);
			PdfPCell cell2table8 = new PdfPCell(new Paragraph("Descripción", font5));
			cell2table8.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2table8.setVerticalAlignment(Element.ALIGN_MIDDLE);
			PdfPCell cell3table8 = new PdfPCell(new Paragraph("Costo", font5));
			cell3table8.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3table8.setVerticalAlignment(Element.ALIGN_MIDDLE);

			PdfPCell emptytable8 = new PdfPCell();
			emptytable8.setMinimumHeight(5);

			table8.addCell(cell1table8);
			table8.addCell(cell2table8);
			table8.addCell(cell3table8);

			PdfPCell emptyCell = new PdfPCell();
			emptyCell.setBorderWidthBottom(0);
			emptyCell.setBorderWidthTop(0);

			float total = 0;
			int contadorRenglones = 0;

			PdfPCell celdin = new PdfPCell(new Paragraph(" "));
			celdin.setBorderWidthTop(0);
			celdin.setBorderWidthBottom(0);

			PdfPCell celdout = new PdfPCell(new Paragraph(" "));
			celdout.setBorderWidth(0);

			if (datos.isConCosto() == true) {
				for (GruposCosto grupo : datos.getListaServicios()) {
					int tamGrupo = grupo.getPresupuestos().size();
					if ((tamGrupo + 2) > (45 - contadorRenglones)) {
						for (int i = 0; i < 43 - contadorRenglones; i++) {
							table8.addCell(celdin);
							table8.addCell(celdout);
							table8.addCell(celdin);
						}
						contadorRenglones = 0;
						PdfPCell c = new PdfPCell(new Paragraph(" "));
						c.setBorderWidthTop(0);
						table8.addCell(c);
						table8.addCell(c);
						table8.addCell(c);
						PdfPCell h = new PdfPCell(crearHeader());
						h.setBorderWidth(0);
						h.setColspan(3);
						table8.addCell(h);
						PdfPCell d = new PdfPCell(new Paragraph(" "));
						d.setColspan(3);
						d.setBorderWidth(0);
						table8.addCell(d);
						table8.addCell(cell1table8);
						table8.addCell(cell2table8);
						table8.addCell(cell3table8);
					}
					table8.addCell(emptyCell);
					PdfPCell nombreGrupo = new PdfPCell(new Paragraph(grupo.getNombre(), font4));
					nombreGrupo.setBorderWidth(0);
					table8.addCell(nombreGrupo);
					table8.addCell(emptyCell);
					contadorRenglones += 1;

					for (PresupuestoEntity presupuesto : grupo.getPresupuestos()) {
						PdfPCell cantCell = new PdfPCell(new Paragraph(presupuesto.getCantidad() + "", font2));
						cantCell.setBorderWidthTop(0);
						cantCell.setBorderWidthBottom(0);
						cantCell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table8.addCell(cantCell);

						PdfPCell conceptoCell = new PdfPCell(new Paragraph(presupuesto.getConcepto(), font2));
						conceptoCell.setBorderWidth(0);
						table8.addCell(conceptoCell);

						PdfPCell precioClienteCell = new PdfPCell(new Paragraph(
								formatter.format(Float.parseFloat(presupuesto.getPrecioCliente().getValue())
										* presupuesto.getCantidad()),
								font2));
						precioClienteCell.setBorderWidthTop(0);
						precioClienteCell.setBorderWidthBottom(0);
						precioClienteCell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table8.addCell(precioClienteCell);
						contadorRenglones += 1;
					}

					table8.addCell(emptyCell);
					float subtotal = 0;
					for (PresupuestoEntity presupuesto : grupo.getPresupuestos()) {
						subtotal += presupuesto.getCantidad()
								* Float.parseFloat(presupuesto.getPrecioCliente().getValue());
					}
					total += subtotal;

					PdfPCell subTotalCellLabel = new PdfPCell(new Paragraph("SubTotal ", font4));
					subTotalCellLabel.setBorderWidth(0);
					subTotalCellLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table8.addCell(subTotalCellLabel);

					PdfPCell subTotalCell = new PdfPCell(new Paragraph(formatter.format(subtotal), font4));
					subTotalCell.setBorderWidthBottom(0);
					subTotalCell.setBorderWidthTop(0);
					subTotalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table8.addCell(subTotalCell);
					contadorRenglones += 1;
				}

				table8.addCell(emptytable8);

				PdfPCell SubTotalCellLabel = new PdfPCell(new Paragraph("SubTotal ", font4));
				SubTotalCellLabel.setBorderWidthLeft(0);
				SubTotalCellLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
				SubTotalCellLabel.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table8.addCell(SubTotalCellLabel);

				PdfPCell SubTotalCell = new PdfPCell(new Paragraph(formatter.format(total), font4));
				SubTotalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				SubTotalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				SubTotalCell.setFixedHeight(30f);
				table8.addCell(SubTotalCell);

				table8.addCell(emptytable8);

				PdfPCell IvaTotalCellLabel = new PdfPCell(new Paragraph("IVA ", font4));
				IvaTotalCellLabel.setBorderWidthLeft(0);
				IvaTotalCellLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
				IvaTotalCellLabel.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table8.addCell(IvaTotalCellLabel);

				PdfPCell IvaTotalCell = new PdfPCell(new Paragraph(formatter.format(total * 0.16), font4));
				IvaTotalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				IvaTotalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				IvaTotalCell.setFixedHeight(30f);
				table8.addCell(IvaTotalCell);

				table8.addCell(emptytable8);

				PdfPCell TotalCellLabel = new PdfPCell(new Paragraph("Total ", font4));
				TotalCellLabel.setBorderWidthLeft(0);
				TotalCellLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
				TotalCellLabel.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table8.addCell(TotalCellLabel);

				PdfPCell TotalCell = new PdfPCell(new Paragraph(formatter.format(total * 1.16), font4));
				TotalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				TotalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				TotalCell.setFixedHeight(30f);
				table8.addCell(TotalCell);

				PdfPCell leyenda = new PdfPCell(new Paragraph("*Los costos no incluyen IVA", font6));
				leyenda.setBorderWidth(0);
				leyenda.setColspan(3);
				table8.addCell(leyenda);

			} else {

				for (GruposCosto grupo : datos.getListaServicios()) {
					int tamGrupo = grupo.getPresupuestos().size();
					if ((tamGrupo + 2) > (45 - contadorRenglones)) {
						for (int i = 0; i < 43 - contadorRenglones; i++) {
							table8.addCell(celdin);
							table8.addCell(celdout);
							table8.addCell(celdin);
						}
						contadorRenglones = 0;
						PdfPCell c = new PdfPCell(new Paragraph(" "));
						c.setBorderWidthTop(0);
						table8.addCell(c);
						table8.addCell(c);
						table8.addCell(c);
						PdfPCell h = new PdfPCell(crearHeader());
						h.setBorderWidth(0);
						h.setColspan(3);
						table8.addCell(h);
						PdfPCell d = new PdfPCell(new Paragraph(" "));
						d.setColspan(3);
						d.setBorderWidth(0);
						table8.addCell(d);
						table8.addCell(cell1table8);
						table8.addCell(cell2table8);
						table8.addCell(cell3table8);
					}
					table8.addCell(emptyCell);
					PdfPCell nombreGrupo = new PdfPCell(new Paragraph(grupo.getNombre(), font4));
					nombreGrupo.setBorderWidth(0);
					table8.addCell(nombreGrupo);
					table8.addCell(emptyCell);
					contadorRenglones += 1;

					for (PresupuestoEntity presupuesto : grupo.getPresupuestos()) {
						PdfPCell cantCell = new PdfPCell(new Paragraph(presupuesto.getCantidad() + "", font2));
						cantCell.setBorderWidthTop(0);
						cantCell.setBorderWidthBottom(0);
						cantCell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table8.addCell(cantCell);

						PdfPCell conceptoCell = new PdfPCell(new Paragraph(presupuesto.getConcepto(), font2));
						conceptoCell.setBorderWidth(0);
						table8.addCell(conceptoCell);

						PdfPCell precioClienteCell = new PdfPCell(new Paragraph("", font2));
						precioClienteCell.setBorderWidthTop(0);
						precioClienteCell.setBorderWidthBottom(0);
						precioClienteCell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table8.addCell(precioClienteCell);
						contadorRenglones += 1;
					}

					table8.addCell(emptyCell);
					float subtotal = 0;
					for (PresupuestoEntity presupuesto : grupo.getPresupuestos()) {
						subtotal += presupuesto.getCantidad()
								* Float.parseFloat(presupuesto.getPrecioCliente().getValue());
					}
					total += subtotal;

					PdfPCell subTotalCellLabel = new PdfPCell(new Paragraph("\n", font4));
					subTotalCellLabel.setBorderWidth(0);
					subTotalCellLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table8.addCell(subTotalCellLabel);

					PdfPCell subTotalCell = new PdfPCell(new Paragraph("", font4));
					subTotalCell.setBorderWidthBottom(0);
					subTotalCell.setBorderWidthTop(0);
					subTotalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table8.addCell(subTotalCell);
					contadorRenglones += 1;
				}

				table8.addCell(emptytable8);

				PdfPCell TotalCellLabel = new PdfPCell(new Paragraph("", font4));
				TotalCellLabel.setBorderWidthLeft(0);
				TotalCellLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
				TotalCellLabel.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table8.addCell(TotalCellLabel);

				PdfPCell TotalCell = new PdfPCell(new Paragraph("", font4));
				TotalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				TotalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				TotalCell.setFixedHeight(30f);
				table8.addCell(TotalCell);
			}

			document.add(table8);
		}

		//////////////////////////////////////////////////////////////////////////// TABLA8

		///////////////////////////////////////////////////////////////////////////// TABLA
		///////////////////////////////////////////////////////////////////////////// FIRMAS
		if (datos.isConFirmas() == true) {
			document.newPage();
			document.add(crearHeader());

			PdfPTable firmasTable = new PdfPTable(2);
			firmasTable.setWidthPercentage(100);
			
			PdfPCell em = new PdfPCell(new Paragraph(" "));
			em.setBorderWidth(0);
			
			firmasTable.addCell(em);
			firmasTable.addCell(em);
			
			PdfPCell fechaLabel = new PdfPCell(new Paragraph("Fecha ", font4));
			firmasTable.addCell(fechaLabel);

			PdfPCell nombreFirmaLabel = new PdfPCell(new Paragraph("Nombre y Firma ", font4));
			firmasTable.addCell(nombreFirmaLabel);

			PdfPCell fechaInput = new PdfPCell();
			fechaInput.setFixedHeight(60f);
			firmasTable.addCell(fechaInput);

			PdfPCell nombreFirmaInput = new PdfPCell(new Paragraph("Autorizo la revisión " + "de la unidad", font4));
			nombreFirmaInput.setFixedHeight(50f);
			nombreFirmaInput.setVerticalAlignment(Element.ALIGN_BOTTOM);
			firmasTable.addCell(nombreFirmaInput);

			firmasTable.addCell(fechaLabel);
			firmasTable.addCell(nombreFirmaLabel);
			firmasTable.addCell(fechaInput);

			PdfPCell nombreFirmaInputSalida = new PdfPCell(
					new Paragraph("Autorizo la reparación \n" + "de la unidad", font4));
			nombreFirmaInputSalida.setFixedHeight(60f);
			nombreFirmaInputSalida.setVerticalAlignment(Element.ALIGN_BOTTOM);
			firmasTable.addCell(nombreFirmaInputSalida);

			firmasTable.addCell(nombreFirmaInput);
			
			firmasTable.addCell(em);
			firmasTable.addCell(em);

			document.add(firmasTable);

		}
		///////////////////////////////////////////////////////////////////////////// TABLA
		///////////////////////////////////////////////////////////////////////////// FIRMAS
	}

	private static PdfPTable crearHeader() throws IOException, IOException, DocumentException {
		PdfPTable headerTable = new PdfPTable(2);
		headerTable.setWidthPercentage(100);
		Image img = Image.getInstance("WEB-INF/Images/ACELogo.PNG");
		PdfPCell cell1 = new PdfPCell(img, true);
		cell1.setBorderWidth(0);
		headerTable.addCell(cell1);

		Phrase line1Cell2 = new Phrase();
		Chunk line1Cell2Chunk = new Chunk("Orden de Servicio \n", font1);
		Chunk line2Cell2Chunk = new Chunk(
				"Auto Control Especializado México S de RL de CV \n" + "AV. Pino Suarez No. 2012. \n"
						+ "Col. La Magdalena \n" + "Toluca, Estado de México, CP.50190. \n" + "Tel. 722 232 55 56",
				font2);
		line1Cell2.add(line1Cell2Chunk);
		line1Cell2.add(line2Cell2Chunk);
		PdfPCell cell2 = new PdfPCell(line1Cell2);
		cell2.setBorderWidth(0);
		headerTable.addCell(cell2);

		return headerTable;
	}

	public DatosPresupuestoVO getDatos() {
		return datos;
	}

	public void setDatos(DatosPresupuestoVO datos) {
		this.datos = datos;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}
