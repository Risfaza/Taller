package com.tikal.tallerWeb.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import com.tikal.tallerWeb.control.restControllers.VO.DatosPresupuestoVO;

public class GeneradorPresupuestoPDF extends AbstractPdfView {

	public class HeaderTable extends PdfPageEventHelper {
		protected PdfPTable table;
		protected float tableHeight;

		public HeaderTable() throws MalformedURLException, IOException, DocumentException {
			table = new PdfPTable(2);
			table.setTotalWidth(523);
			table.setLockedWidth(true);
			//http://127.0.0.1:8888/servicio/image/qAMszxQYHcjo5eu9-UC3Ww
			
			Image img = Image.getInstance(new URL("http://127.0.0.1:8888/_ah/img/qAMszxQYHcjo5eu9-UC3Ww"));
			PdfPCell uno = new PdfPCell(img, true);
			//uno.setRowspan(2);
			uno.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			Phrase holis = new Phrase();
			holis.add(new Chunk("Orden de Servicio", FontFactory.getFont(FontFactory.HELVETICA, 16)));
			holis.add(new Chunk(
					" \nAuto Control Especializado México S de RL de CV \nAV. Pino Suarez No 2012. \nCol. La Magdalena \nToluca, Estado de México, CP. 50190 \nTel. 722 232 55 56",
					FontFactory.getFont(FontFactory.HELVETICA, 10)));
			
			PdfPCell dos = new PdfPCell(holis);
			
			
			uno.setBorder(Rectangle.NO_BORDER);
			dos.setBorder(Rectangle.NO_BORDER);
			
			PdfPCell celulin = new PdfPCell(new Paragraph("\n\n\n\n"));
			celulin.setColspan(2);
			celulin.setBorder(Rectangle.NO_BORDER);
			
			table.addCell(celulin);
			table.addCell(uno);
			//table.addCell(new PdfPCell(new Paragraph("")));
			table.addCell(dos);
			tableHeight = table.getTotalHeight();
		}

		public float getTableHeight() {
			return tableHeight;
		}

		public void onEndPage(PdfWriter writer, Document document) {
			table.writeSelectedRows(0, -1, document.left(), document.top() + ((document.topMargin() + tableHeight) / 2),
					writer.getDirectContent());
		}
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> mapa, Document documento, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		DatosPresupuestoVO datos = (DatosPresupuestoVO) mapa.get("aquinoseporquevaesto");
		HeaderTable event = new HeaderTable();
		// Formato de la página

		// step 2
		documento.setPageSize(PageSize.A4);
		documento.setMargins(36, 36, 20 + event.getTableHeight(), 50);
		writer.setPageEvent(event);

		// Template de la página

		
		Paragraph datosClienteLabel = new Paragraph(
				new Chunk("Datos del cliente", FontFactory.getFont(FontFactory.HELVETICA, 14)));

		Paragraph datosV = new Paragraph(
				new Chunk("Datos del vehiculo", FontFactory.getFont(FontFactory.HELVETICA, 14)));

		Paragraph inventarioLabel = new Paragraph(
				new Chunk("Inventario de daños", FontFactory.getFont(FontFactory.HELVETICA, 14)));

		Paragraph especificacionesLabel = new Paragraph(
				new Chunk("Especificaciones", FontFactory.getFont(FontFactory.HELVETICA, 14)));
		
		Paragraph leyendaLabel = new Paragraph(
				new Chunk("*Los costos no incluyen IVA.", FontFactory.getFont(FontFactory.HELVETICA, 14)));


		PdfPTable datosCliente = new PdfPTable(4);
		datosCliente.getDefaultCell().setBorder(1);
		datosCliente.setWidthPercentage(100);

		PdfPCell noServicio = new PdfPCell(new Paragraph("No. de Servicio: "));
		noServicio.setBorder(Rectangle.NO_BORDER);
		noServicio.setColspan(2);

		PdfPCell fecha = new PdfPCell(new Paragraph("Fecha: "));
		fecha.setBorder(Rectangle.NO_BORDER);
		fecha.setColspan(2);

		PdfPCell nombreLabel = new PdfPCell(new Paragraph("Nombre: "));
		PdfPCell nombreField = new PdfPCell(new Paragraph(datos.getNombre()));
		nombreField.setColspan(3);
		nombreField.disableBorderSide(0);
		PdfPCell dirLabel = new PdfPCell(new Paragraph("Dirección: "));
		PdfPCell dirField = new PdfPCell(new Paragraph(datos.getDireccion()));
		dirField.setColspan(3);
		PdfPCell telLabel = new PdfPCell(new Paragraph("Teléfono: "));
		PdfPCell telField = new PdfPCell(new Paragraph(datos.getTelefono()));
		telField.setColspan(3);
		PdfPCell emailLabel = new PdfPCell(new Paragraph("E-mail: "));
		PdfPCell emailField = new PdfPCell(new Paragraph(datos.getEmail()));
		emailField.setColspan(3);
		PdfPCell asesorLabel = new PdfPCell(new Paragraph("Asesor: "));
		PdfPCell asesorField = new PdfPCell(new Paragraph(datos.getAsesor()));
		asesorField.setColspan(3);

		datosCliente.addCell(noServicio);
		datosCliente.addCell(fecha);
		datosCliente.addCell(nombreLabel);
		datosCliente.addCell(nombreField);
		datosCliente.addCell(dirLabel);
		datosCliente.addCell(dirField);
		datosCliente.addCell(telLabel);
		datosCliente.addCell(telField);
		datosCliente.addCell(emailLabel);
		datosCliente.addCell(emailField);
		datosCliente.addCell(asesorLabel);
		datosCliente.addCell(asesorField);

		PdfPTable datosVe = new PdfPTable(7);
		datosVe.setWidthPercentage(100);
		datosVe.addCell(new PdfPCell(new Paragraph("Marca: ")));
		datosVe.addCell(new PdfPCell(new Paragraph("Tipo: ")));
		datosVe.addCell(new PdfPCell(new Paragraph("Modelo: ")));
		datosVe.addCell(new PdfPCell(new Paragraph("Color:")));
		datosVe.addCell(new PdfPCell(new Paragraph("Placas: ")));
		datosVe.addCell(new PdfPCell(new Paragraph("KM: ")));
		datosVe.addCell(new PdfPCell(new Paragraph("Serie: ")));
		datosVe.addCell(new PdfPCell(new Paragraph(datos.getMarca())));
		datosVe.addCell(new PdfPCell(new Paragraph(datos.getTipo())));
		datosVe.addCell(new PdfPCell(new Paragraph(datos.getModelo())));
		datosVe.addCell(new PdfPCell(new Paragraph(datos.getColor())));
		datosVe.addCell(new PdfPCell(new Paragraph(datos.getPlacas())));
		datosVe.addCell(new PdfPCell(new Paragraph(datos.getKilometros())));
		datosVe.addCell(new PdfPCell(new Paragraph(datos.getSerie())));
		PdfPCell servicioCell = new PdfPCell(new Paragraph("Servicio: " + datos.getServicio()));
		servicioCell.setColspan(7);
		datosVe.addCell(servicioCell);
		
		
		

//		Hacer la consulta para sacar las imagenes y meterlas a la tabla 
		
		PdfPTable imagenesBox = new PdfPTable(1);
	

		PdfPTable especificacionesTable = new PdfPTable(4);
		especificacionesTable.setWidthPercentage(100);

		PdfPCell combustibleLabel = new PdfPCell(new Paragraph("\n Nivel de Combustible:"));
		PdfPCell combustibleField = new PdfPCell(new Paragraph(datos.getNivelCombustible()));
		combustibleField.setColspan(3);

		PdfPCell observacionesLabel = new PdfPCell(new Paragraph("\n Observaciones:"));
		PdfPCell observacionesField = new PdfPCell(new Paragraph(datos.getObservaciones()));
		observacionesField.setColspan(3);

		especificacionesTable.addCell(combustibleLabel);
		especificacionesTable.addCell(combustibleField);
		especificacionesTable.addCell(observacionesLabel);
		especificacionesTable.addCell(observacionesField);

		
		
		
		//Crear la tabla para las reparaciones que se van a realizar
		PdfPTable repTable = new PdfPTable(3);
		repTable.setWidthPercentage(100);
		PdfPCell cantLabel = new PdfPCell(new Paragraph("Cantidad"));
		PdfPCell desLabel = new PdfPCell(new Paragraph("Descripción"));
		PdfPCell costLabel = new PdfPCell(new Paragraph("Costo"));
		cantLabel.setHorizontalAlignment(Element.ALIGN_CENTER);
		desLabel.setHorizontalAlignment(Element.ALIGN_CENTER);
		costLabel.setHorizontalAlignment(Element.ALIGN_CENTER);
		repTable.addCell(cantLabel);
		repTable.addCell(desLabel);
		repTable.addCell(costLabel);
		
		
		for(int i = 0; i < datos.getListaServicios().size(); i ++){
		repTable.addCell(datos.getListaServicios().get(i).getPresupuestos().get(i).getCantidad()+"");
		repTable.addCell(datos.getListaServicios().get(i).getPresupuestos().get(i).getConcepto());
		repTable.addCell(datos.getListaServicios().get(i).getPresupuestos().get(i).getPrecioCliente()+"");
		}
		
//		for (int i = 0; i < 50; i++)
//            documento.add(new Paragraph("Hello World!"));
//        documento.newPage();
//        documento.add(new Paragraph("Hello World!"));
//        documento.newPage();
//        documento.add(new Paragraph("Hello World!"));
        
        documento.add(new Paragraph("\n\n"));
		documento.add(datosClienteLabel);
		documento.add(datosCliente);
		documento.add(datosV);
		documento.add(new Paragraph("\n"));
		documento.add(datosVe);
		documento.add(inventarioLabel);
		documento.add(especificacionesLabel);
		documento.add(new Paragraph("\n"));
		documento.add(especificacionesTable);
		documento.add(new Paragraph("\n"));
		documento.add(repTable);
		documento.add(leyendaLabel);
	

	}

}