package com.tikal.cacao.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.tikal.cacao.factura.Estatus;
import com.tikal.cacao.factura.FormatoFecha;
import com.tikal.cacao.sat.cfd.Comprobante;
import com.tikal.cacao.sat.cfd.catalogos.C_FormaPago;
import com.tikal.cacao.sat.cfd.catalogos.C_MetodoPago;
import com.tikal.cacao.sat.cfd.nomina.C_TipoContrato;
import com.tikal.cacao.sat.cfd.nomina.C_TipoRegimen;
import com.tikal.cacao.sat.cfd.nomina.NominaElement;
import com.tikal.cacao.sat.cfd33.Comprobante.Conceptos;
import com.tikal.cacao.sat.cfd33.Comprobante.Conceptos.Concepto;
import com.tikal.cacao.springController.viewObjects.ListaPagosVO;

import mx.gob.sat.timbrefiscaldigital.TimbreFiscalDigital;

public class PdfMakerV2 {
	
	private Document document;
	private ListaPagosVO lista;
	
	Font fontBoldBig = new Font(Font.FontFamily.HELVETICA, 10.5F, Font.BOLD);
	Font fontBoldMedium = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
	Font fontBoldWhite = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);
	Font fontBoldWhiteBig = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE);
	Font fontNormal = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
	
	Font fontBoldSellos = new Font(Font.FontFamily.HELVETICA, 5.5F, Font.BOLD);
	Font fontSellos = new Font(Font.FontFamily.HELVETICA, 5.5F, Font.NORMAL);
	
	BaseColor colorEncabezados = new BaseColor(0, 130, 215);
	PdfPCell emptyCell = new PdfPCell();
	
	NumberFormat formatter = NumberFormat.getCurrencyInstance();
	

	public PdfMakerV2() {
		this.emptyCell.setBorderWidth(0);
		this.document = new Document();
		this.document.setPageSize(PageSize.A4);
		this.document.setMargins(30, 30, 20, 20); // Left Right Top Bottom
	}
	
//	public Document imprimirPagos() throws ParseException {
//		for (PagoVO p : this.lista.getLista()) {
//			try {
//				this.document = construirPdf(p);
//			} catch (DocumentException e) {
//				e.printStackTrace();
//			}
//		}
//
//		return this.document;
//	}
	
	private void obtenerComplementos(Comprobante cfdi, TimbreFiscalDigital tfd, NominaElement nomina) {
		List<Object> complemento = cfdi.getComplemento().getAny();
		if (complemento.size() > 0) {
			for (Object object : complemento) {
				if (object instanceof TimbreFiscalDigital) {
					tfd = (TimbreFiscalDigital) object;
				} 
				else if (object instanceof NominaElement) {
					nomina = (NominaElement) object;
				}
			}
		}
	}
	
	
	public Document construirPDF(com.tikal.cacao.sat.cfd33.Comprobante cfdi, Estatus estatus, String selloDigital, byte[] codigoQR, String selloCancelacion, Date fechaCancelacion) throws DocumentException, MalformedURLException, IOException {
		TimbreFiscalDigital tfd = null;
		NominaElement nomina = null;
		List<Object> complemento = cfdi.getComplemento().get(0).getAny();
		if (complemento.size() > 0) {
			for (Object object : complemento) {
				if (object instanceof TimbreFiscalDigital) {
					tfd = (TimbreFiscalDigital) object;
				} 
				else if (object instanceof NominaElement) {
					nomina = (NominaElement) object;
				}
			}
		}
		
		PdfPCell firmaCell = new PdfPCell(new Paragraph("Firma del Empleado", this.fontNormal));
		firmaCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		firmaCell.setBorderWidthBottom(0);
		firmaCell.setBorderWidthLeft(0);
		firmaCell.setBorderWidthRight(0);
		PdfPTable tablaFirma = new PdfPTable(1);
		tablaFirma.setWidthPercentage(30.0F);
		tablaFirma.setSpacingBefore(20.0F);
		tablaFirma.addCell(firmaCell);
//		if (estatus.equals(Estatus.GENERADO)) {
			this.construirEncabezado(cfdi, estatus, tfd);
			this.construirConceptoYDetalleIAS(cfdi);
			this.construirInformacionLaboral(cfdi, nomina, estatus);
			this.contruirTablaPercepcionesYDeducciones();
			this.construirContenidoTablaPerYDed(nomina, cfdi.getTotal());
			
			if (estatus.equals(Estatus.TIMBRADO) || estatus.equals(Estatus.CANCELADO)) {
				this.construirTimbre(tfd, codigoQR, selloDigital, cfdi, estatus, selloCancelacion, fechaCancelacion);
			} else {
				document.add(tablaFirma);
			}
			
			// SEPARADOR
			PdfPTable tablaLineaSeparadora = new PdfPTable(1);
			tablaLineaSeparadora.setWidthPercentage(99.5F);
			
			LineSeparator separador = new LineSeparator();
			//separador.setAlignment(Element.ALIGN_CENTER);
			Chunk linebreak = new Chunk(separador);
			PdfPCell celdaLineaSeparadora = new PdfPCell();
			celdaLineaSeparadora.setBorder(Rectangle.NO_BORDER);
			//celdaLineaSeparadora.setVerticalAlignment(Element.ALIGN_MIDDLE);
			celdaLineaSeparadora.addElement(linebreak);
			tablaLineaSeparadora.addCell(celdaLineaSeparadora);
			if (estatus.equals(Estatus.GENERADO)) {
				tablaLineaSeparadora.setSpacingBefore(35.0F);
				tablaLineaSeparadora.setSpacingAfter(20.0F);
			} else {
				tablaLineaSeparadora.setSpacingAfter(1.5F);
			}
			
			document.add(tablaLineaSeparadora);
//		}
		
		
		
		this.construirEncabezado(cfdi, estatus, tfd);
		this.construirConceptoYDetalleIAS(cfdi);
		this.construirInformacionLaboral(cfdi, nomina, estatus);
		this.contruirTablaPercepcionesYDeducciones();
		this.construirContenidoTablaPerYDed(nomina, cfdi.getTotal());
		
		if (estatus.equals(Estatus.TIMBRADO)|| estatus.equals(estatus.CANCELADO)) {
			this.construirTimbre(tfd, codigoQR, selloDigital, cfdi,estatus,selloCancelacion, fechaCancelacion);
		} else {
			document.add(tablaFirma);
		}
		return document;
	}
	
	
	
	private void darEstiloACelda(PdfPCell celda, int anchoBorde, BaseColor colorBorde, float espacioInterior) {
		celda.setBorderWidth(anchoBorde);
		celda.setBorderColor(colorBorde);
		celda.setPadding(espacioInterior);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	}
	
	
	private void construirEncabezado(com.tikal.cacao.sat.cfd33.Comprobante cfdi, Estatus estatus, TimbreFiscalDigital tfd) throws DocumentException {
		PdfPTable tablaEncabezado = new PdfPTable(2);
		tablaEncabezado.setWidthPercentage(99.5F);
		
		Phrase fraseNombreEmpresa = new Phrase();
		Chunk chunkEtqNombreEmpresa = new Chunk("Empresa Emisora: ", this.fontNormal);
		Chunk chunkNombreEmpresa = new Chunk(cfdi.getEmisor().getNombre(), this.fontBoldMedium);
		fraseNombreEmpresa.add(chunkEtqNombreEmpresa);
		fraseNombreEmpresa.add(chunkNombreEmpresa);
		PdfPCell celdaNombreEmpresa = new PdfPCell(fraseNombreEmpresa);
		celdaNombreEmpresa.setBorderWidth(1);
		celdaNombreEmpresa.setBorderColor(BaseColor.WHITE);
		celdaNombreEmpresa.setBackgroundColor(BaseColor.WHITE);
		celdaNombreEmpresa.setPadding(2.0f);
		celdaNombreEmpresa.setVerticalAlignment(Element.ALIGN_MIDDLE);
		tablaEncabezado.addCell(celdaNombreEmpresa);
		
		Phrase fraseRFCEmpresaYSerieFolio = new Phrase();
		Chunk chunkRFCEmpresa = new Chunk("RFC: " + cfdi.getEmisor().getRfc(), this.fontNormal);
		Chunk chunkEtqSerieFolio = new Chunk("  Serie y folio: ", this.fontNormal);
		Chunk chunkSerieFolio = new Chunk(cfdi.getSerie() + " - " + cfdi.getFolio(), this.fontBoldMedium);
		fraseRFCEmpresaYSerieFolio.add(chunkRFCEmpresa);
		fraseRFCEmpresaYSerieFolio.add(chunkEtqSerieFolio);
		fraseRFCEmpresaYSerieFolio.add(chunkSerieFolio);
		PdfPCell celdaRFCEmpresaYSerieFolio = new PdfPCell(fraseRFCEmpresaYSerieFolio);
		celdaRFCEmpresaYSerieFolio.setBorderWidth(1);
		celdaRFCEmpresaYSerieFolio.setBorderColor(BaseColor.WHITE);
		celdaRFCEmpresaYSerieFolio.setPadding(2.0f);
		tablaEncabezado.addCell(celdaRFCEmpresaYSerieFolio);
		
		/*PdfPCell celdaEtqSerieYFolio = new PdfPCell(new Paragraph("Serie y folio", this.fontBoldWhite));
		celdaEtqSerieYFolio.setBorderWidth(1);
		celdaEtqSerieYFolio.setBorderColor(BaseColor.WHITE);
		celdaEtqSerieYFolio.setBackgroundColor(colorEncabezados);
		celdaEtqSerieYFolio.setPadding(4.0F);
		celdaEtqSerieYFolio.setVerticalAlignment(Element.ALIGN_MIDDLE);
		tablaEncabezado.addCell(celdaEtqSerieYFolio);
		
		PdfPCell celdaSerieYFolio = new PdfPCell(new Paragraph(cfdi.getSerie() + " - " + cfdi.getFolio(), this.fontBoldMedium));
		celdaSerieYFolio.setBorderWidth(1);
		celdaSerieYFolio.setBorderColor(BaseColor.WHITE);
		celdaSerieYFolio.setPadding(4.0F);
		tablaEncabezado.addCell(celdaSerieYFolio);*/
		
//		PdfPCell celdaEtqFolioFiscal = null;
		PdfPCell celdaFolioFiscal = null;
//		PdfPCell celdaEtqNoCertificado = null;
		PdfPCell celdaNoCertificado = null;
		if (estatus.equals(Estatus.TIMBRADO)) {
			Phrase fraseFolioFiscal = new Phrase();
			Chunk chunkEtqFolioFiscal = new Chunk("Folio Fiscal: ", this.fontNormal);
			Chunk chunkFolioFiscal = new Chunk(tfd.getUUID(), this.fontBoldMedium);
			fraseFolioFiscal.add(chunkEtqFolioFiscal);
			fraseFolioFiscal.add(chunkFolioFiscal);
			
//			celdaEtqFolioFiscal = new PdfPCell(new Paragraph("Folio Fiscal", this.fontBoldWhite));
//			celdaEtqFolioFiscal.setBorderWidth(1);
//			celdaEtqFolioFiscal.setBackgroundColor(colorEncabezados);
//			celdaEtqFolioFiscal.setBorderColor(BaseColor.WHITE);
			
			celdaFolioFiscal = new PdfPCell(fraseFolioFiscal); ////////////////////////////Folio Fiscal
			celdaFolioFiscal.setBorderWidth(1);
			celdaFolioFiscal.setBorderColor(BaseColor.WHITE);
			
			Phrase fraseNoCertificado = new Phrase();
			Chunk chunkEtqNoCertificado = new Chunk("No. de Serie del Certificado del CSD: ", this.fontNormal);
			Chunk chunkNoCertificado = new Chunk(cfdi.getNoCertificado(), this.fontBoldMedium);
			fraseNoCertificado.add(chunkEtqNoCertificado);
			fraseNoCertificado.add(chunkNoCertificado);
//			celdaEtqNoCertificado = new PdfPCell(new Paragraph("No. de Serie del Certificado del CSD", this.fontBoldWhite));
//			celdaEtqNoCertificado.setBorderWidth(1);
//			celdaEtqNoCertificado.setBorderColor(BaseColor.WHITE);
//			celdaEtqNoCertificado.setBackgroundColor(colorEncabezados);
			
			celdaNoCertificado = new PdfPCell(fraseNoCertificado); //////////////////////No. de Serie del Certificado del CSD
			celdaNoCertificado.setBorderWidth(1);
			celdaNoCertificado.setBorderColor(BaseColor.WHITE);
		} 
		else {
//			tablaEncabezado.addCell(emptyCell);
		}
		
		if (estatus.equals(Estatus.TIMBRADO)) {
//			tablaEncabezado.addCell(celdaEtqFolioFiscal);
			tablaEncabezado.addCell(celdaFolioFiscal);
//			tablaEncabezado.addCell(celdaEtqNoCertificado);
			tablaEncabezado.addCell(celdaNoCertificado);
//			tablaEncabezado.addCell(emptyCell);
//			tablaEncabezado.addCell(emptyCell);
			
		} else {
//			tablaEncabezado.addCell(emptyCell);
		}
		
		Phrase fraseRegimenFiscal = new Phrase();
		Chunk chunkEtqRegimenFiscal = new Chunk("Régimen Fiscal: ", this.fontNormal);
		Chunk chunkRegimenFiscal = new Chunk("601 General de Ley Personas Morales", this.fontBoldMedium);
		fraseRegimenFiscal.add(chunkEtqRegimenFiscal);
		fraseRegimenFiscal.add(chunkRegimenFiscal);
		
//		PdfPCell celdaEtqRegimenFiscal = new PdfPCell(new Paragraph("Régimen Fiscal", this.fontBoldWhite));
//		this.darEstiloACelda(celdaEtqRegimenFiscal, 1, BaseColor.WHITE, 4.0F);
//		celdaEtqRegimenFiscal.setBackgroundColor(colorEncabezados);
//		tablaEncabezado.addCell(celdaEtqRegimenFiscal);
		
		//TODO agregar la descripción del regimen fiscal
		PdfPCell celdaRegimenFiscal = new PdfPCell(fraseRegimenFiscal);
		celdaRegimenFiscal.setBorderWidth(1);
		celdaRegimenFiscal.setBorderColor(BaseColor.WHITE);
		celdaRegimenFiscal.setPadding(2.0f);
		//this.darEstiloACelda(celdaRegimenFiscal, 1, BaseColor.WHITE, 2.0f);
		tablaEncabezado.addCell(celdaRegimenFiscal);

		Phrase fraseLugarFechaEmision = new Phrase();
		Chunk chunkEtqLugarFechaEmision = new Chunk("Lugar, fecha y hora de emisión: ", this.fontNormal);
		String lugarFechaEmiHora = "";
		lugarFechaEmiHora = cfdi.getLugarExpedicion().getValor().concat(" a ")
				.concat(cfdi.getFecha().toString());
		Chunk chunkLugarFechaEmision = new Chunk(lugarFechaEmiHora, this.fontBoldMedium);
		fraseLugarFechaEmision.add(chunkEtqLugarFechaEmision);
		fraseLugarFechaEmision.add(chunkLugarFechaEmision);
		
//		PdfPCell celdaEtqLugarFechaEmision = new PdfPCell(new Paragraph("Lugar, fecha y hora de emisión", this.fontBoldWhite));
//		this.darEstiloACelda(celdaEtqLugarFechaEmision, 1, BaseColor.WHITE, 4.0f);
//		celdaEtqLugarFechaEmision.setBackgroundColor(colorEncabezados);
//		tablaEncabezado.addCell(celdaEtqLugarFechaEmision);
		
//		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
//	    Date now = new Date();
//	    String strDate = sdfDate.format(now);
		
		PdfPCell celdaLugarFechaEmision = new PdfPCell(fraseLugarFechaEmision);
		celdaLugarFechaEmision.setBorderWidth(1);
		celdaLugarFechaEmision.setBorderColor(BaseColor.WHITE);
		celdaLugarFechaEmision.setPadding(2.0f);
		//this.darEstiloACelda(celdaLugarFechaEmision, 1, BaseColor.WHITE, 2.0f);
		tablaEncabezado.addCell(celdaLugarFechaEmision);
		
//		tablaEncabezado.addCell(emptyCell);
//		tablaEncabezado.addCell(emptyCell);
		
		tablaEncabezado.setSpacingAfter(2.5f);
		if (estatus.equals(Estatus.GENERADO)) {
			tablaEncabezado.setSpacingBefore(7.0F);
		}
		document.add(tablaEncabezado);
	}
	
	//TODO afinar detalles de estilo
	private void construirConceptoYDetalleIAS(com.tikal.cacao.sat.cfd33.Comprobante comprobante) throws DocumentException {
		PdfPTable tablaConceptoYDetalleIAS = new PdfPTable(1);
		tablaConceptoYDetalleIAS.setWidthPercentage(100F);
		
		// TABLA CONCEPTO IAS
		PdfPCell celdaTablaConcepto = new PdfPCell();
		celdaTablaConcepto.setBorder(Rectangle.NO_BORDER);
		PdfPTable subTablaConcepto = new PdfPTable(6);
		subTablaConcepto.setWidthPercentage(100F);
		subTablaConcepto.setWidths(new int[] { 9, 12, 16, 25, 18, 20});
		
		PdfUtil.agregarCeldaConFondo("Cantidad", this.fontBoldWhite, subTablaConcepto, this.colorEncabezados, BaseColor.LIGHT_GRAY, Element.ALIGN_LEFT);
		PdfUtil.agregarCeldaConFondo("Clave Unidad", this.fontBoldWhite, subTablaConcepto,  this.colorEncabezados, BaseColor.LIGHT_GRAY, Element.ALIGN_LEFT);
		PdfUtil.agregarCeldaConFondo("ClaveProdServ", this.fontBoldWhite, subTablaConcepto, this.colorEncabezados, BaseColor.LIGHT_GRAY, Element.ALIGN_LEFT);
		PdfUtil.agregarCeldaConFondo("Descripción", this.fontBoldWhite, subTablaConcepto,  this.colorEncabezados, BaseColor.LIGHT_GRAY, Element.ALIGN_LEFT);
		PdfUtil.agregarCeldaConFondo("Precio unitario", this.fontBoldWhite, subTablaConcepto,  this.colorEncabezados, BaseColor.LIGHT_GRAY, Element.ALIGN_LEFT);
		PdfUtil.agregarCeldaConFondo("Importe", this.fontBoldWhite, subTablaConcepto,  this.colorEncabezados, BaseColor.LIGHT_GRAY, Element.ALIGN_LEFT);
	
		Conceptos conceptos = comprobante.getConceptos();
		Concepto concepto = conceptos.getConcepto().get(0);
		PdfUtil.agregarCelda(concepto.getCantidad().toPlainString(), this.fontNormal, subTablaConcepto, BaseColor.LIGHT_GRAY, Element.ALIGN_UNDEFINED);
		PdfUtil.agregarCelda(concepto.getClaveUnidad().getValor(), this.fontNormal, subTablaConcepto, BaseColor.LIGHT_GRAY, Element.ALIGN_UNDEFINED);
		PdfUtil.agregarCelda(concepto.getClaveProdServ().getValor(), this.fontNormal, subTablaConcepto, BaseColor.LIGHT_GRAY, Element.ALIGN_UNDEFINED);
		PdfUtil.agregarCelda("IAS", this.fontNormal, subTablaConcepto, BaseColor.LIGHT_GRAY, Element.ALIGN_UNDEFINED);
		PdfUtil.agregarCelda(
				this.formatter.format(concepto.getValorUnitario()), this.fontNormal, subTablaConcepto, BaseColor.LIGHT_GRAY, Element.ALIGN_RIGHT);
		PdfUtil.agregarCelda(
				this.formatter.format(concepto.getImporte()), this.fontNormal, subTablaConcepto, BaseColor.LIGHT_GRAY, Element.ALIGN_RIGHT);
		
		celdaTablaConcepto.addElement(subTablaConcepto);
		tablaConceptoYDetalleIAS.addCell(celdaTablaConcepto);
		
		
		PdfPCell celdaTablaDetalleIAS = new PdfPCell();
		celdaTablaDetalleIAS.setBorder(Rectangle.NO_BORDER);
		PdfPTable subTablaDetalleIAS = new PdfPTable(3);
		subTablaDetalleIAS.setWidthPercentage(100F);
		subTablaDetalleIAS.setWidths(new int[] { 65, 18, 20});
		
		PdfUtil.agregarCeldaSinBorde("Importe con letra: ", this.fontNormal, subTablaDetalleIAS, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT);
		PdfUtil.agregarCeldaSinBorde("Subtotal", this.fontBoldMedium, subTablaDetalleIAS, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT);
		PdfUtil.agregarCeldaSinBorde(
				this.formatter.format(comprobante.getSubTotal()), this.fontNormal, subTablaDetalleIAS, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT);
		
		PdfUtil.agregarCeldaSinBorde(
				NumberToLetterConverter.convertNumberToLetter(comprobante.getTotal().doubleValue(), "MXN"),
				this.fontBoldMedium, subTablaDetalleIAS, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT);
		PdfUtil.agregarCeldaSinBorde("ISR(Deducciones)", this.fontBoldMedium, subTablaDetalleIAS, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT);
		PdfUtil.agregarCeldaSinBorde(
				this.formatter.format(comprobante.getDescuento()), this.fontNormal, subTablaDetalleIAS, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT);
		
		// Agregar un espacio del tamaño de un renglón
		subTablaDetalleIAS.addCell(emptyCell);
		subTablaDetalleIAS.addCell(emptyCell);
		subTablaDetalleIAS.addCell(emptyCell);
		
		String metodoDePago = "";
		if (comprobante.getMetodoPago().getValor().equals(C_MetodoPago.PUE.toString())) {
			metodoDePago = "PUE - Pago en una sola exhibición";
		} else {
			metodoDePago = "PPD - En parcialidades o diferido";
		}
		//String descripcionFormaDePago = Util.getSerializedName(comprobante.getFormaPago());
		String descripcionFormaDePago = Util.getSerializedName(C_FormaPago.fromValue( comprobante.getFormaPago().getValor() ) );
		PdfUtil.agregarCeldaConFondo(" | " + metodoDePago + " | " + comprobante.getFormaPago().getValor() + "-" + descripcionFormaDePago,
				this.fontBoldWhite, subTablaDetalleIAS, this.colorEncabezados, this.colorEncabezados, Element.ALIGN_LEFT);
		PdfUtil.agregarCeldaConFondo("TOTAL", this.fontBoldWhite, subTablaDetalleIAS, this.colorEncabezados, this.colorEncabezados, Element.ALIGN_LEFT);
		PdfUtil.agregarCeldaConFondo(
				this.formatter.format(comprobante.getTotal()), this.fontBoldWhite, subTablaDetalleIAS, this.colorEncabezados, this.colorEncabezados, Element.ALIGN_RIGHT);
		
		celdaTablaDetalleIAS.addElement(subTablaDetalleIAS);
		tablaConceptoYDetalleIAS.addCell(celdaTablaDetalleIAS);
		
		tablaConceptoYDetalleIAS.setSpacingAfter(1.0f);
		document.add(tablaConceptoYDetalleIAS);
	}
	
	
	private void construirInformacionLaboral(com.tikal.cacao.sat.cfd33.Comprobante cfdi, NominaElement nomina, Estatus estatus) throws DocumentException {
		PdfPTable tablaInfoLaboral= new PdfPTable(3);
		tablaInfoLaboral.setWidthPercentage(99.5F);
		
		//if (estatus.equals(Estatus.TIMBRADO)) {
			PdfPCell celdaLeyendaFiscal = new PdfPCell(new Paragraph("ESTE DOCUMENTO ES UNA REPRESENTACIÓN IMPRESA DE UN CFDI", this.fontBoldWhite));
			celdaLeyendaFiscal.setBorderWidth(0.5F);
			celdaLeyendaFiscal.setBorderColor(BaseColor.WHITE);
			celdaLeyendaFiscal.setBackgroundColor(this.colorEncabezados);
			celdaLeyendaFiscal.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdaLeyendaFiscal.setVerticalAlignment(Element.ALIGN_MIDDLE);
			celdaLeyendaFiscal.setColspan(3);
			celdaLeyendaFiscal.setPadding(1.0f);
			tablaInfoLaboral.addCell(celdaLeyendaFiscal);
		//}
		
		String leyendaRecibo = "";
		if (nomina.getReceptor().getTipoContrato().equals(C_TipoContrato.VALUE_9) 
				|| nomina.getReceptor().getTipoRegimen().equals(C_TipoRegimen.VALUE_8)) {
			leyendaRecibo = "RECIBO DE ASIMILADO";
		} else {
			leyendaRecibo = "RECIBO DE NOMINA";
		}
		PdfPCell celdaLeyendaRecibo = new PdfPCell(new Paragraph(leyendaRecibo, fontBoldWhiteBig));
		celdaLeyendaRecibo.setBorderWidth(0.5F);
		celdaLeyendaRecibo.setBorderColor(BaseColor.WHITE);
		celdaLeyendaRecibo.setBackgroundColor(this.colorEncabezados);
		celdaLeyendaRecibo.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaLeyendaRecibo.setVerticalAlignment(Element.ALIGN_MIDDLE);
		celdaLeyendaRecibo.setColspan(3);
		celdaLeyendaRecibo.setPadding(1.0F);
		tablaInfoLaboral.addCell(celdaLeyendaRecibo);
		
		PdfPTable subTablaTrabajador = new PdfPTable(3);
		subTablaTrabajador.setWidths(new int[] {45, 23, 32});
		
		PdfPCell celdaNombreTrabajador = new PdfPCell(new Paragraph(cfdi.getReceptor().getNombre(), this.fontBoldBig));
//		celdaNombreTrabajador.setColspan(1);
		celdaNombreTrabajador.setBorderWidth(0);
		//celda2tabla1.setBackgroundColor(otroGris);
		subTablaTrabajador.addCell(celdaNombreTrabajador);
		
		PdfPCell celdaRFCTrabajador = new PdfPCell(new Paragraph("RFC: " + cfdi.getReceptor().getRfc(), this.fontBoldBig) );
//		celdaRFCTrabajador.setColspan(1);
		celdaRFCTrabajador.setBorderWidth(0);
		subTablaTrabajador.addCell(celdaRFCTrabajador);
		
		PdfPCell celdaCURPTrabajador = new PdfPCell(new Paragraph("CURP: " + nomina.getReceptor().getCurp(), this.fontBoldBig));
//		celdaCURPTrabajador.setColspan(1);
		celdaCURPTrabajador.setBorderWidth(0);
		//celda10tabla1.setBackgroundColor(otroGris);
		subTablaTrabajador.addCell(celdaCURPTrabajador);
		
		PdfPCell celdaTablaTrabajador = new PdfPCell(subTablaTrabajador);
		celdaTablaTrabajador.setColspan(3);
		celdaTablaTrabajador.setBorderWidth(0);
		tablaInfoLaboral.addCell(celdaTablaTrabajador);
		
		String periodicidadDePagoDesc = Util.getSerializedName( nomina.getReceptor().getPeriodicidadPago() );
		PdfPCell celda5tabla1 = new PdfPCell(new Paragraph("PeriocidadPago: " + nomina.getReceptor().getPeriodicidadPago().value() + "-" + periodicidadDePagoDesc, this.fontNormal));
		celda5tabla1.setBorderWidth(0);
		//celda5tabla1.setBackgroundColor(otroGris);
		tablaInfoLaboral.addCell(celda5tabla1);
		
		PdfPCell celda6tabla1 = new PdfPCell(new Paragraph("DiasNomina: " + nomina.getNumDiasPagados(), this.fontNormal));
		celda6tabla1.setBorderWidth(0);
		//celda6tabla1.setBackgroundColor(otroGris);
		tablaInfoLaboral.addCell(celda6tabla1);
		
		PdfPCell celda8tabla1 = new PdfPCell(new Paragraph("FechaInicialPago: " + nomina.getFechaInicialPago().toString() , this.fontNormal));
		celda8tabla1.setBorderWidth(0);
		//celda8tabla1.setBackgroundColor(otroGris);
		tablaInfoLaboral.addCell(celda8tabla1);
		
		PdfPCell celda9tabla1 = new PdfPCell(new Paragraph("FechaFinalPago: " + nomina.getFechaFinalPago().toString(), this.fontNormal));
		celda9tabla1.setBorderWidth(0);
		//celda9tabla1.setBackgroundColor(otroGris);
		tablaInfoLaboral.addCell(celda9tabla1);
		
		PdfPCell celda11tabla1 = new PdfPCell(new Paragraph("NumEmpleado: " + nomina.getReceptor().getNumEmpleado(), this.fontNormal));
		celda11tabla1.setBorderWidth(0);
		//celda11tabla1.setBackgroundColor(otroGris);
		tablaInfoLaboral.addCell(celda11tabla1);
		
		PdfPCell celda12tabla1 = new PdfPCell(new Paragraph("TipoRegimen: " + nomina.getReceptor().getTipoRegimen().value(), this.fontNormal));
		celda12tabla1.setBorderWidth(0);
		//celda12tabla1.setBackgroundColor(otroGris);
		tablaInfoLaboral.addCell(celda12tabla1);
		
		PdfPCell celda19tabla1 = new PdfPCell(new Paragraph("TipoContrato: " + nomina.getReceptor().getTipoContrato().value(), this.fontNormal));
		celda19tabla1.setBorderWidth(0);
		//celda19tabla1.setBackgroundColor(otroGris);
		tablaInfoLaboral.addCell(celda19tabla1);
		
		PdfPCell celda22tabla1 = new PdfPCell(new Paragraph("NumDiasPagados: " + nomina.getNumDiasPagados(), this.fontNormal));
		celda22tabla1.setBorderWidth(0);
		//celda22tabla1.setBackgroundColor(otroGris);
		tablaInfoLaboral.addCell(celda22tabla1);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fechaPago = sdf.format(Util.xmlGregorianAFecha(nomina.getFechaPago(),true));
		PdfPCell celda23tabla1 = new PdfPCell(new Paragraph("Fecha Pago: " + fechaPago, this.fontNormal));
		celda23tabla1.setBorderWidth(0);
		//celda23tabla1.setBackgroundColor(otroGris);
		tablaInfoLaboral.addCell(celda23tabla1);
		
		PdfPCell celda26tabla1 = new PdfPCell(new Paragraph("Tipo: " + nomina.getTipoNomina() + "-" + nomina.getTipoNomina().getDescripcion(), this.fontNormal));
		celda26tabla1.setBorderWidth(0);
		//celda26tabla1.setBackgroundColor(otroGris);
		tablaInfoLaboral.addCell(celda26tabla1);
		
		PdfPCell celda31tabla1 = new PdfPCell(new Paragraph("Ent Fed: " + nomina.getReceptor().getClaveEntFed(), this.fontNormal));
		celda31tabla1.setBorderWidth(0);
		//celda31tabla1.setBackgroundColor(otroGris);
		tablaInfoLaboral.addCell(celda31tabla1);
		PdfPCell celda32tabla1 = new PdfPCell(new Paragraph("Uso de CFDI: " + cfdi.getReceptor().getUsoCFDI().getValor(), this.fontNormal));
		celda32tabla1.setBorderWidth(0);
		//celda31tabla1.setBackgroundColor(otroGris);
		tablaInfoLaboral.addCell(celda32tabla1);
		
		
		if (!nomina.getReceptor().getTipoContrato().equals(C_TipoContrato.VALUE_9) 
				|| !nomina.getReceptor().getTipoRegimen().equals(C_TipoRegimen.VALUE_8)) {
			
			PdfPCell celda3tabla1 = new PdfPCell(new Paragraph("CodigoNomina: ", this.fontNormal));
			celda3tabla1.setBorderWidth(0);
			//celda3tabla1.setBackgroundColor(otroGris);
			tablaInfoLaboral.addCell(celda3tabla1);
			
			PdfPCell celda4tabla1 = new PdfPCell(new Paragraph("RegistroPatronal: ", this.fontNormal));
			celda4tabla1.setBorderWidth(0);
			//celda4tabla1.setBackgroundColor(otroGris);
			tablaInfoLaboral.addCell(celda4tabla1);
			
			PdfPCell celda7tabla1 = new PdfPCell(new Paragraph("RiesgoPuesto: ", this.fontNormal));
			celda7tabla1.setBorderWidth(0);
			//celda7tabla1.setBackgroundColor(otroGris);
			tablaInfoLaboral.addCell(celda7tabla1);
			
			String numSeguridadSocial = nomina.getReceptor().getNumSeguridadSocial();
			if (numSeguridadSocial == null) {
				numSeguridadSocial = "";
			}
			PdfPCell celda13tabla1 = new PdfPCell(new Paragraph("NumSegSocial: " + numSeguridadSocial, this.fontNormal));
			celda13tabla1.setBorderWidth(0);
			//celda13tabla1.setBackgroundColor(otroGris);
			tablaInfoLaboral.addCell(celda13tabla1);
			
			String departamento = nomina.getReceptor().getDepartamento();
			if (departamento == null) {
				departamento = "";
			}
			PdfPCell celda14tabla1 = new PdfPCell(new Paragraph("Departamento: " + departamento, this.fontNormal));
			celda14tabla1.setBorderWidth(0);
			//celda14tabla1.setBackgroundColor(otroGris);
			tablaInfoLaboral.addCell(celda14tabla1);
			
			PdfPCell celda15tabla1 = new PdfPCell(new Paragraph("CLABE: ", this.fontNormal));
			celda15tabla1.setBorderWidth(0);
			//celda15tabla1.setBackgroundColor(otroGris);
			tablaInfoLaboral.addCell(celda15tabla1);
			
			PdfPCell celda16tabla1 = new PdfPCell(new Paragraph("Banco: ", this.fontNormal));
			celda16tabla1.setBorderWidth(0);
			//celda16tabla1.setBackgroundColor(otroGris);
			tablaInfoLaboral.addCell(celda16tabla1);
			
			String fechaIngreso = "";
			if (nomina.getReceptor().getFechaInicioRelLaboral() != null) {
				fechaIngreso = nomina.getReceptor().getFechaInicioRelLaboral().toString();
			}
			PdfPCell celda17tabla1 = new PdfPCell(new Paragraph("FechaIniRelLab: " + fechaIngreso, this.fontNormal));
			celda17tabla1.setBorderWidth(0);
			//celda17tabla1.setBackgroundColor(otroGris);
			tablaInfoLaboral.addCell(celda17tabla1); 
			
			String puesto = nomina.getReceptor().getPuesto();
			if (puesto == null) {
				puesto = "";
			}
			PdfPCell celda18tabla1 = new PdfPCell(new Paragraph("Puesto: " + puesto, this.fontNormal));
			celda18tabla1.setBorderWidth(0);
			//celda18tabla1.setBackgroundColor(otroGris);
			tablaInfoLaboral.addCell(celda18tabla1);
			
			PdfPCell celda20tabla1 = new PdfPCell(new Paragraph("TipoJornada: ", this.fontNormal));
			celda20tabla1.setBorderWidth(0);
			//celda20tabla1.setBackgroundColor(otroGris);
			tablaInfoLaboral.addCell(celda20tabla1);
			
			PdfPCell celda21tabla1 = null;
			BigDecimal salarioBaseCotApor = nomina.getReceptor().getSalarioBaseCotApor();
			if (salarioBaseCotApor == null) {
				celda21tabla1 = new PdfPCell(new Paragraph("SDI IMSS: " , this.fontNormal));
			} else {
				celda21tabla1 = new PdfPCell(new Paragraph("SDI IMSS: " + salarioBaseCotApor, this.fontNormal));
			}
			celda21tabla1.setBorderWidth(0);
			//celda21tabla1.setBackgroundColor(otroGris);
			tablaInfoLaboral.addCell(celda21tabla1);
			
			PdfPCell celda24tabla1 = new PdfPCell(new Paragraph("SDI Indemnización: ", this.fontNormal));
			celda24tabla1.setBorderWidth(0);
			//celda24tabla1.setBackgroundColor(otroGris);
			tablaInfoLaboral.addCell(celda24tabla1);
			
			PdfPCell celda25tabla1 = new PdfPCell(new Paragraph("CURP Emisor: ", this.fontNormal));
			celda25tabla1.setBorderWidth(0);
			//celda25tabla1.setBackgroundColor(otroGris);
			tablaInfoLaboral.addCell(celda25tabla1);
			
			PdfPCell celda27tabla1 = new PdfPCell(new Paragraph("Imp Hrs Simples: ", this.fontNormal));
			celda27tabla1.setBorderWidth(0);
			//celda27tabla1.setBackgroundColor(otroGris);
			tablaInfoLaboral.addCell(celda27tabla1);
			
			PdfPCell celda28tabla1 = new PdfPCell(new Paragraph("Imp Hrs Dobles: ", this.fontNormal));
			celda28tabla1.setBorderWidth(0);
			//celda28tabla1.setBackgroundColor(otroGris);
			tablaInfoLaboral.addCell(celda28tabla1);
			
			PdfPCell celda29tabla1 = new PdfPCell(new Paragraph("Imp Hrs Triples: ", this.fontNormal));
			celda29tabla1.setBorderWidth(0);
			//celda29tabla1.setBackgroundColor(otroGris);
			tablaInfoLaboral.addCell(celda29tabla1);
			
			PdfPCell celda30tabla1 = new PdfPCell(new Paragraph("Antiguedad: ", this.fontNormal));
			celda30tabla1.setBorderWidth(0);
			//celda30tabla1.setBackgroundColor(otroGris);
			tablaInfoLaboral.addCell(celda30tabla1);
			
			PdfPCell celdaVaciaTabla1 = new PdfPCell(new Paragraph(" "));
			celdaVaciaTabla1.setBorderWidth(0);
			//celdaVaciaTabla1.setBackgroundColor(otroGris);
			tablaInfoLaboral.addCell(celdaVaciaTabla1);
			
		} else {
			//Para que aparezaca la Clave de entidad federativa y el Tipo de Nómina
			tablaInfoLaboral.addCell(emptyCell);
		}
		tablaInfoLaboral.setSpacingAfter(1.5F);
		
		document.add(tablaInfoLaboral);
	}
	
	
	private void contruirTablaPercepcionesYDeducciones() throws DocumentException {
		PdfPTable table4 = new PdfPTable(2);
		table4.setWidthPercentage(99.5F);
		table4.setWidths(new int[] { 55, 45 });

		PdfPCell cell1table4 = new PdfPCell(new Paragraph("PERCEPCIONES", this.fontBoldWhite));
		cell1table4.setBackgroundColor(this.colorEncabezados);
		cell1table4.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell1table4.setBorderWidth(1);
		cell1table4.setBorderColor(BaseColor.LIGHT_GRAY);
		PdfPCell cell2table4 = new PdfPCell(new Paragraph("DEDUCCIONES", this.fontBoldWhite));
		cell2table4.setBackgroundColor(this.colorEncabezados);
		cell2table4.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell2table4.setBorderWidth(1);
		cell2table4.setBorderColor(BaseColor.LIGHT_GRAY);

		table4.addCell(cell1table4);
		table4.addCell(cell2table4);

		document.add(table4);

		PdfPTable table5 = new PdfPTable(7);
		table5.setWidthPercentage(99.5F);
		table5.setWidths(new int[] { 7, 26, 12, 10, 7, 27, 11 });

		PdfPCell cell1table5 = new PdfPCell(new Paragraph("Tipo", this.fontBoldWhite));
		cell1table5.setBackgroundColor(this.colorEncabezados);
		cell1table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell1table5.setBorderWidth(1);
		cell1table5.setBorderColor(BaseColor.LIGHT_GRAY);
		PdfPCell cell2table5 = new PdfPCell(new Paragraph("Clave/Descripción",  this.fontBoldWhite));
		cell2table5.setBackgroundColor(this.colorEncabezados);
		cell2table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell2table5.setBorderWidth(1);
		cell2table5.setBorderColor(BaseColor.LIGHT_GRAY);
		PdfPCell cell3table5 = new PdfPCell(new Paragraph("Importe Gravado",  this.fontBoldWhite));
		cell3table5.setBackgroundColor(this.colorEncabezados);
		cell3table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell3table5.setBorderWidth(1);
		cell3table5.setBorderColor(BaseColor.LIGHT_GRAY);
		PdfPCell celdaEtqImporteExento = new PdfPCell(new Paragraph("Importe Exento",  this.fontBoldWhite));
		celdaEtqImporteExento.setBackgroundColor(this.colorEncabezados);
		celdaEtqImporteExento.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdaEtqImporteExento.setBorderWidth(1);
		celdaEtqImporteExento.setBorderColor(BaseColor.LIGHT_GRAY);
		PdfPCell cell4table5 = new PdfPCell(new Paragraph("Tipo",  this.fontBoldWhite));
		cell4table5.setBackgroundColor(this.colorEncabezados);
		cell4table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell4table5.setBorderWidth(1);
		cell4table5.setBorderColor(BaseColor.LIGHT_GRAY);
		PdfPCell cell5table5 = new PdfPCell(new Paragraph("Clave/Descripción",  this.fontBoldWhite));
		cell5table5.setBackgroundColor(this.colorEncabezados);
		cell5table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell5table5.setBorderWidth(1);
		cell5table5.setBorderColor(BaseColor.LIGHT_GRAY);
		PdfPCell cell6table5 = new PdfPCell(new Paragraph("Importe",  this.fontBoldWhite));
		cell6table5.setBackgroundColor(this.colorEncabezados);
		cell6table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell6table5.setBorderWidth(1);
		cell6table5.setBorderColor(BaseColor.LIGHT_GRAY);

		///////////////////////////////////////////////////////////////////////////////////////////////////

		PdfPCell dumbCell = new PdfPCell(new Paragraph(" "));
		dumbCell.setBorderWidthTop(0);
		dumbCell.setBorderWidthBottom(0);

		///////////////////////////////////////////////////////////////////////////////////////////////////

		table5.addCell(cell1table5);
		table5.addCell(cell2table5);
		table5.addCell(cell3table5);
		table5.addCell(celdaEtqImporteExento);
		table5.addCell(cell4table5);
		table5.addCell(cell5table5);
		table5.addCell(cell6table5);

		document.add(table5);

	}
	
	
	private void construirContenidoTablaPerYDed(NominaElement nomina, BigDecimal total) throws DocumentException {
		// Tabla de percepciones y de deducciones
		PdfPTable tablota = new PdfPTable(2);
		tablota.setWidthPercentage(99.5F);
		tablota.setWidths(new int[] { 55, 45 });

		// tabla de percepciones
		PdfPTable tablaPercepciones = new PdfPTable(4);
		tablaPercepciones.setWidths(new int[] { 7, 26, 12, 10 });

		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		

		double totalPercepciones = 0;
		for (NominaElement.Percepciones.Percepcion per : nomina.getPercepciones().getPercepcion()) {
			PdfPCell tipoPerCell = new PdfPCell(new Paragraph(per.getTipoPercepcion().value(), this.fontNormal));
			tipoPerCell.setBorderWidthTop(0);
			tipoPerCell.setBorderWidthBottom(0);
			tipoPerCell.setBorderColor(BaseColor.LIGHT_GRAY);
			tipoPerCell.setBorderWidth(0.5F);
			tablaPercepciones.addCell(tipoPerCell);

			PdfPCell desPerCell = new PdfPCell(new Paragraph(per.getClave() + " - " + per.getConcepto(), this.fontNormal));
			desPerCell.setBorderWidthTop(0);
			desPerCell.setBorderWidthBottom(0);
			desPerCell.setBorderColor(BaseColor.LIGHT_GRAY);
			desPerCell.setBorderWidth(0.5F);
			tablaPercepciones.addCell(desPerCell);

			PdfPCell impGravadoPerCell = new PdfPCell(new Paragraph(formatter.format(per.getImporteGravado()), this.fontNormal));
			impGravadoPerCell.setBorderWidthTop(0);
			impGravadoPerCell.setBorderWidthBottom(0);
			impGravadoPerCell.setBorderColor(BaseColor.LIGHT_GRAY);
			impGravadoPerCell.setBorderWidth(0.5F);
			impGravadoPerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tablaPercepciones.addCell(impGravadoPerCell);
			totalPercepciones += per.getImporteGravado().doubleValue();
			
			PdfPCell impExentoPerCell = new PdfPCell(new Paragraph(formatter.format(per.getImporteExento()), this.fontNormal));
			impExentoPerCell.setBorderWidthTop(0);
			impExentoPerCell.setBorderWidthBottom(0);
			impExentoPerCell.setBorderColor(BaseColor.LIGHT_GRAY);
			impExentoPerCell.setBorderWidth(0.5F);
			impExentoPerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tablaPercepciones.addCell(impExentoPerCell);
			totalPercepciones += per.getImporteExento().doubleValue();
		}

		PdfPCell emptyCellDos = new PdfPCell(new Paragraph(" "));
		emptyCellDos.setBorderWidthTop(0);
		emptyCellDos.setBorderWidthBottom(0);
		emptyCellDos.setBorderColor(BaseColor.LIGHT_GRAY);

		for (int i = 0; i < (5 - (nomina.getPercepciones().getPercepcion().size() * 3)); i++) {
			tablaPercepciones.addCell(emptyCellDos);
		}

		PdfPTable tablaDeducciones = new PdfPTable(3);
		tablaDeducciones.setWidths(new int[] { 7, 27, 11 });

		double totalDeducciones = 0;
		for (NominaElement.Deducciones.Deduccion ded : nomina.getDeducciones().getDeduccion()) {
			PdfPCell tipoDedCell = new PdfPCell(new Paragraph(ded.getTipoDeduccion().value(), this.fontNormal));
			tipoDedCell.setBorderWidthTop(0);
			tipoDedCell.setBorderWidthBottom(0);
			tipoDedCell.setBorderColor(BaseColor.LIGHT_GRAY);
			tipoDedCell.setBorderWidth(0.5F);
			tablaDeducciones.addCell(tipoDedCell);

			PdfPCell desDedCell = new PdfPCell(new Paragraph(ded.getClave() + " - " + ded.getConcepto(), this.fontNormal));
			desDedCell.setBorderWidthTop(0);
			desDedCell.setBorderWidthBottom(0);
			desDedCell.setBorderColor(BaseColor.LIGHT_GRAY);
			desDedCell.setBorderWidth(0.5F);
			tablaDeducciones.addCell(desDedCell);

			PdfPCell impDedCell = new PdfPCell(new Paragraph(formatter.format(ded.getImporte()), this.fontNormal));
			impDedCell.setBorderWidthTop(0);
			impDedCell.setBorderWidthBottom(0);
			impDedCell.setBorderColor(BaseColor.LIGHT_GRAY);
			impDedCell.setBorderWidth(0.5F);
			impDedCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tablaDeducciones.addCell(impDedCell);

			totalDeducciones += ded.getImporte().doubleValue();
		}

		for (int i = 0; i < (5 - (nomina.getDeducciones().getDeduccion().size() * 3)); i++) {
			tablaDeducciones.addCell(emptyCellDos);
		}

		PdfPCell perCell = new PdfPCell(tablaPercepciones);
		perCell.setBorderWidthTop(0);
		perCell.setBorderWidthLeft(0);
		perCell.setBorderWidthRight(0);
		perCell.setBorderColor(BaseColor.LIGHT_GRAY);
		tablota.addCell(perCell);

		PdfPCell dedCell = new PdfPCell(tablaDeducciones);
		dedCell.setBorderWidthTop(0);
		dedCell.setBorderWidthLeft(0);
		dedCell.setBorderWidthRight(0);
		dedCell.setBorderColor(BaseColor.LIGHT_GRAY);
		tablota.addCell(dedCell);

		document.add(tablota);

		PdfPTable table6 = new PdfPTable(6);
		table6.setWidthPercentage(99.5F);
		table6.setWidths(new int[] { 39, 6, 10, 26, 8, 11 });

		PdfPCell cell1table6 = new PdfPCell(new Paragraph("Total: ", this.fontBoldMedium));
		cell1table6.setBorderColor(BaseColor.LIGHT_GRAY);
		cell1table6.setBorderWidth(1);
		//cell1table6.setBorderWidth(0);
		PdfPCell cell2table6 = new PdfPCell(new Paragraph(formatter.format(totalPercepciones), this.fontBoldMedium));
		cell2table6.setBorderColor(BaseColor.LIGHT_GRAY);
		cell2table6.setBorderWidth(1);
		cell2table6.setHorizontalAlignment(Element.ALIGN_RIGHT);
		//cell2table6.setBorderWidth(0);
		PdfPCell cell3table6 = new PdfPCell(new Paragraph("Total: ", this.fontBoldMedium));
		cell3table6.setBorderColor(BaseColor.LIGHT_GRAY);
		cell3table6.setBorderWidth(1);
		//cell3table6.setBorderWidth(0);
		PdfPCell cell4table6 = new PdfPCell(new Paragraph(formatter.format(totalDeducciones), this.fontBoldMedium));
		cell4table6.setBorderColor(BaseColor.LIGHT_GRAY);
		cell4table6.setBorderWidth(1);
		cell4table6.setHorizontalAlignment(Element.ALIGN_RIGHT);
		//cell4table6.setBorderWidth(0);
		PdfPCell cell5table6 = new PdfPCell(new Paragraph("PAGAR: ", this.fontBoldMedium));
		cell5table6.setBorderColor(BaseColor.LIGHT_GRAY);
		cell5table6.setBorderWidth(1);
		//cell5table6.setBorderWidth(0);
		PdfPCell cell6table6 = new PdfPCell(new Paragraph(formatter.format(total), this.fontBoldMedium));
		cell6table6.setBorderColor(BaseColor.LIGHT_GRAY);
		cell6table6.setBorderWidth(1);
		cell6table6.setHorizontalAlignment(Element.ALIGN_RIGHT);
		//cell6table6.setBorderWidth(0);

		table6.addCell(emptyCell);
		table6.addCell(cell1table6);
		table6.addCell(cell2table6);
		table6.addCell(emptyCell);
		table6.addCell(cell3table6);
		table6.addCell(cell4table6);
		
		table6.addCell(emptyCell);
		table6.addCell(emptyCell);
		table6.addCell(emptyCell);
		table6.addCell(emptyCell);
		table6.addCell(cell5table6);
		table6.addCell(cell6table6);

		table6.setSpacingAfter(2.0F);
		document.add(table6);
	}
	
	
	private void construirTimbre(TimbreFiscalDigital tfd, byte[] codigoQR, String selloDigital, com.tikal.cacao.sat.cfd33.Comprobante cfdi, Estatus estatus, String selloCancelacion, Date fechaCancelacion) throws DocumentException, MalformedURLException, IOException {
		PdfPTable tablaTimbreFiscalDigital = new PdfPTable(3);
		tablaTimbreFiscalDigital.setWidthPercentage(100);
		tablaTimbreFiscalDigital.setWidths(new int[] { 20, 65, 15 });
		
		PdfPCell celdaTablaQRCode = new PdfPCell();
		celdaTablaQRCode.setBorder(PdfPCell.NO_BORDER);
		PdfPTable tablaQRCode = new PdfPTable(1);
		Image imgQRCode = Image.getInstance(codigoQR);
		Chunk chunkQRCode = new Chunk(imgQRCode, 1.5F, -68F);
		Phrase fraseQRCode = new Phrase();
//		fraseQRCode.add(Chunk.NEWLINE);
		fraseQRCode.add(chunkQRCode);
		PdfPCell celdaQRCode = new PdfPCell();
		celdaQRCode.addElement(fraseQRCode);
		celdaQRCode.setBorder(PdfPCell.NO_BORDER);
		tablaQRCode.addCell(celdaQRCode);
		celdaTablaQRCode.addElement(tablaQRCode);
		
		PdfPCell cell1table7 = new PdfPCell();
		cell1table7.setBorderWidth(0);

		Phrase line1footer = new Phrase();
//		Chunk line1part1 = new Chunk("Num. certificado emisor ", this.fontBoldMedium);
//		Chunk line1part2 = new Chunk(cfdi.getNoCertificado(), this.fontNormal);
		Chunk line1part3 = new Chunk("Num. certificado SAT ", this.fontBoldMedium);
		Chunk line1part4 = new Chunk(tfd.getNoCertificadoSAT(), this.fontNormal);
		Chunk line2part3 = new Chunk("Fecha de Certificación: ", this.fontBoldMedium);
		Chunk line2part4 = new Chunk(tfd.getFechaTimbrado().toString(), this.fontNormal);
		
//		line1footer.add(line1part1);
//		line1footer.add(line1part2);
		line1footer.add(line1part3);
		line1footer.add(line1part4);
		line1footer.add(Chunk.NEWLINE);
		line1footer.add(line2part3);
		line1footer.add(line2part4);

		if(estatus.equals(Estatus.CANCELADO) && fechaCancelacion!=null){
			Chunk line3part3 = new Chunk("\tFecha de Cancelación: ", this.fontBoldMedium);
			Chunk line3part4 = new Chunk(Util.getXMLDate(fechaCancelacion, FormatoFecha.COMPROBANTE).toString(), this.fontNormal);
			line1footer.add(line3part3);
			line1footer.add(line3part4);
		}
		
		PdfPCell celdaNumCertificados = new PdfPCell(line1footer);
		celdaNumCertificados.setBorderWidth(0);
		/*
		Phrase line2footer = new Phrase();
//		Chunk line2part1 = new Chunk("Folio Fiscal: ", this.fontBoldMedium);
//		Chunk line2part2 = new Chunk(tfd.getUUID(), this.fontNormal);
		Chunk line2part3 = new Chunk("Fecha de Certificación: ", this.fontBoldSellos);
		Chunk line2part4 = new Chunk(tfd.getFechaTimbrado() + " \n\n", this.fontSellos);
//		line2footer.add(line2part1);
//		line2footer.add(line2part2);
		line2footer.add(line2part3);
		line2footer.add(line2part4);*/

//		PdfPCell celdaFolioFiscalYFecha = new PdfPCell(line2footer);
//		celdaFolioFiscalYFecha.setBorderWidth(0);

		Phrase line3footer = new Phrase();
		Chunk line3part1 = new Chunk("Sello del emisor ", this.fontBoldSellos);
		Chunk line3part2 = new Chunk(tfd.getSelloCFD(),this.fontSellos);
		line3footer.add(line3part1);
		line3footer.add(line3part2);
		
		PdfPCell celdaSelloEmisor = new PdfPCell(line3footer);
		celdaSelloEmisor.setBorderWidth(0);
		
		Phrase line4footer = new Phrase();
		Chunk line4part1 = new Chunk("Sello del SAT ", this.fontBoldSellos);
		Chunk line4part2 = new Chunk(tfd.getSelloSAT(),
				this.fontSellos);
		line4footer.add(line4part1);
		line4footer.add(line4part2);
		
		PdfPCell celdaSelloSAT = new PdfPCell(line4footer);
		celdaSelloSAT.setBorderWidth(0);
		
		Phrase line5footer = new Phrase();
		Chunk line5part1 = new Chunk("Cadena Original TFD", this.fontBoldSellos);
		Chunk line5part2 = new Chunk(selloDigital, this.fontSellos);
		line5footer.add(line5part1);
		line5footer.add(line5part2);
		
		PdfPCell celdaCadenaOriginal = new PdfPCell(line5footer);
		celdaCadenaOriginal.setBorderWidth(0);
		
		tablaTimbreFiscalDigital.addCell(celdaTablaQRCode);
		tablaTimbreFiscalDigital.addCell(celdaNumCertificados);
		tablaTimbreFiscalDigital.addCell(cell1table7);

//		tablaTimbreFiscalDigital.addCell(cell1table7);
//		tablaTimbreFiscalDigital.addCell(celdaFolioFiscalYFecha);
//		tablaTimbreFiscalDigital.addCell(cell1table7);
		
		tablaTimbreFiscalDigital.addCell(cell1table7);
		tablaTimbreFiscalDigital.addCell(celdaSelloEmisor);
		
		PdfPCell firmaCell = new PdfPCell(new Paragraph("Firma del Empleado", this.fontNormal));
		firmaCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		firmaCell.setBorderWidthBottom(0);
		firmaCell.setBorderWidthLeft(0);
		firmaCell.setBorderWidthRight(0);
		tablaTimbreFiscalDigital.addCell(firmaCell);
		
		tablaTimbreFiscalDigital.addCell(cell1table7);
		tablaTimbreFiscalDigital.addCell(celdaSelloSAT);
		tablaTimbreFiscalDigital.addCell(cell1table7);
		
		if(estatus.equals(Estatus.CANCELADO)){
			Phrase line6footer = new Phrase();
			Chunk line6part1 = new Chunk("Sello de Cancelación", this.fontBoldSellos);
			Chunk line6part2 = new Chunk(selloCancelacion, this.fontSellos);
			line6footer.add(line6part1);
			line6footer.add(line6part2);
			
			PdfPCell celdaSelloCancelacion = new PdfPCell(line6footer);
			celdaSelloCancelacion.setBorderWidth(0);
			
			tablaTimbreFiscalDigital.addCell(cell1table7);
			tablaTimbreFiscalDigital.addCell(celdaSelloCancelacion);
			tablaTimbreFiscalDigital.addCell(cell1table7);
		}
		
		tablaTimbreFiscalDigital.addCell(cell1table7);
		tablaTimbreFiscalDigital.addCell(celdaCadenaOriginal);
		tablaTimbreFiscalDigital.addCell(cell1table7);
		
		
		document.add(tablaTimbreFiscalDigital);
	}
	

	public Document construirPdf(Comprobante cfdi, String selloDigital, byte[] codigoQR) throws DocumentException, ParseException, IOException {
		
		TimbreFiscalDigital tfd = null;
		NominaElement nomina = null;
		List<Object> complemento = cfdi.getComplemento().getAny();
		if (complemento.size() > 0) {
			for (Object object : complemento) {
				if (object instanceof TimbreFiscalDigital) {
					tfd = (TimbreFiscalDigital) object;
				} 
				else if (object instanceof NominaElement) {
					nomina = (NominaElement) object;
				}
			}
		}
		//this.obtenerComplementos(cfdi, tfd, nomina);
		
		Font font1 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
		Font font2 = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
		Font font3 = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
		
		PdfPCell emptyCell = new PdfPCell();
		emptyCell.setBorderWidth(0);
		
		BaseColor gris = new BaseColor(153, 153, 102);
		BaseColor otroGris = new BaseColor(235, 235, 224);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fechaPago = sdf.format(Util.xmlGregorianAFecha(nomina.getFechaPago(),true));
//		String date2=nomina.getReceptor().getFechaInicioRelLaboral().toString();
//		Date datin;
//		try{
//			datin = sdf.parse(date2);
//		}catch(ParseException exc){
//			sdf = new SimpleDateFormat("yyyy-MM-dd");
//			date2=date2.substring(0,date2.indexOf("T"));
//			datin =sdf.parse(date2);
//		}
//		
//		String fechaIngreso = sdf.format(datin);
		String fechaIngreso = "";
		if (nomina.getReceptor().getFechaInicioRelLaboral() != null) {
			fechaIngreso = nomina.getReceptor().getFechaInicioRelLaboral().toString();
		}
		
		
		// ENCABEZADO DEL RECIBO
		PdfPTable tablaEncabezado = new PdfPTable(2);
		tablaEncabezado.setWidthPercentage(100);
		
		PdfPCell celdaNombreEmpresa = new PdfPCell(new Paragraph(cfdi.getEmisor().getNombre(), font2));
		celdaNombreEmpresa.setBorderWidth(0);
		celdaNombreEmpresa.setBackgroundColor(otroGris);
		tablaEncabezado.addCell(celdaNombreEmpresa);
		
		
		PdfPCell celdaEtqFolioFiscal = new PdfPCell(new Paragraph("Folio Fiscal", font2));
		celdaEtqFolioFiscal.setBorderWidth(0);
		celdaEtqFolioFiscal.setBackgroundColor(otroGris);
		tablaEncabezado.addCell(celdaEtqFolioFiscal);
		
		PdfPCell celdaRFCEmpresa = new PdfPCell(new Paragraph(cfdi.getEmisor().getRfc(), font3));
		celdaRFCEmpresa.setBorderWidth(0);
		tablaEncabezado.addCell(celdaRFCEmpresa);
		
		PdfPCell celdaFolioFiscal = new PdfPCell(new Paragraph(tfd.getUUID(), font3)); ////////////////////////////Folio Fiscal
		celdaFolioFiscal.setBorderWidth(0);
		tablaEncabezado.addCell(celdaFolioFiscal);
		
		tablaEncabezado.addCell(emptyCell);
		
		PdfPCell celdaEtqNoCertificado = new PdfPCell(new Paragraph("No. de Serie del Certificado del CSD", font2));
		celdaEtqNoCertificado.setBorderWidth(0);
		celdaEtqNoCertificado.setBackgroundColor(otroGris);
		tablaEncabezado.addCell(celdaEtqNoCertificado);
		
		tablaEncabezado.addCell(emptyCell);
		
		PdfPCell celdaNoCertificado = new PdfPCell(new Paragraph(cfdi.getNoCertificado(), font3)); //////////////////////No. de Serie del Certificado del CSD
		celdaNoCertificado.setBorderWidth(0);
		tablaEncabezado.addCell(celdaNoCertificado);
		
		
		PdfPCell celdaEtqRegimenFiscal = new PdfPCell(new Paragraph("Régimen Fiscal", font2));
		celdaEtqRegimenFiscal.setBorderWidth(0);
		celdaEtqRegimenFiscal.setBackgroundColor(otroGris);
		tablaEncabezado.addCell(celdaEtqRegimenFiscal);
		
		
		PdfPCell celdaEtqLugarFechaEmision = new PdfPCell(new Paragraph("Lugar, fecha y hora de emisión", font2));
		celdaEtqLugarFechaEmision.setBorderWidth(0);
		celdaEtqLugarFechaEmision.setBackgroundColor(otroGris);
		tablaEncabezado.addCell(celdaEtqLugarFechaEmision);
		
		//TODO agregar la descripción del regimen fiscal
		PdfPCell celdaRegimenFiscal = new PdfPCell(new Paragraph("601 General de Ley Personas Morales", font3)); 
		celdaRegimenFiscal.setBorderWidth(0);
		tablaEncabezado.addCell(celdaRegimenFiscal);
		
		String lugarFechaEmiHoraCert = "";
		lugarFechaEmiHoraCert = cfdi.getLugarExpedicion().concat(" a ")
				.concat(cfdi.getFecha().toString().concat(" / ").concat(tfd.getFechaTimbrado().toString()));
		
//		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
//	    Date now = new Date();
//	    String strDate = sdfDate.format(now);
		
		PdfPCell celdaLugarFechaEmision = new PdfPCell(new Paragraph(lugarFechaEmiHoraCert, font3)); 
		celdaLugarFechaEmision.setBorderWidth(0);
		
		
		
		
		
		tablaEncabezado.addCell(celdaLugarFechaEmision);
		
		tablaEncabezado.addCell(emptyCell);
		tablaEncabezado.addCell(emptyCell);
		
		document.add(tablaEncabezado);
		
		//INFORMACIÓN LABORAL
		PdfPTable tabla1= new PdfPTable(3);
		tabla1.setWidthPercentage(100);
		
		PdfPCell celdatabla1 = new PdfPCell(new Paragraph("ESTE DOCUMENTO ES UNA REPRESENTACIÓN IMPRESA DE UN CFDI", font2));
		celdatabla1.setBorderWidth(0);
		celdatabla1.setBackgroundColor(otroGris);
		celdatabla1.setHorizontalAlignment(Element.ALIGN_CENTER);
		celdatabla1.setColspan(3);
		tabla1.addCell(celdatabla1);
		
		PdfPCell celda1tabla1 = new PdfPCell(new Paragraph("RECIBO DE NOMINA", font1));
		celda1tabla1.setBorderWidth(0);
		celda1tabla1.setBackgroundColor(otroGris);
		celda1tabla1.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda1tabla1.setColspan(3);
		tabla1.addCell(celda1tabla1);
		
		PdfPCell celda2tabla1 = new PdfPCell(new Paragraph("Nombre: "+ cfdi.getReceptor().getNombre(), font3));
		celda2tabla1.setColspan(3);
		celda2tabla1.setBorderWidth(0);
		//celda2tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda2tabla1);
		
		
		PdfPCell celda3tabla1 = new PdfPCell(new Paragraph("CodigoNomina: ", font3));
		celda3tabla1.setBorderWidth(0);
		//celda3tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda3tabla1);
		
		
		PdfPCell celda4tabla1 = new PdfPCell(new Paragraph("RegistroPatronal: ", font3));
		celda4tabla1.setBorderWidth(0);
		//celda4tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda4tabla1);
		
		
		PdfPCell celda5tabla1 = new PdfPCell(new Paragraph("PeriocidadPago: " + nomina.getReceptor().getPeriodicidadPago().value(), font3));
		celda5tabla1.setBorderWidth(0);
		//celda5tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda5tabla1);
		
		PdfPCell celda6tabla1 = new PdfPCell(new Paragraph("DiasNomina: " + nomina.getNumDiasPagados(), font3));
		celda6tabla1.setBorderWidth(0);
		//celda6tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda6tabla1);
		
		PdfPCell celda7tabla1 = new PdfPCell(new Paragraph("RiesgoPuesto: ", font3));
		celda7tabla1.setBorderWidth(0);
		//celda7tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda7tabla1);
		
		PdfPCell celda8tabla1 = new PdfPCell(new Paragraph("FechaInicial: " + nomina.getFechaInicialPago().toString() , font3));
		celda8tabla1.setBorderWidth(0);
		//celda8tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda8tabla1);
		
		PdfPCell celda9tabla1 = new PdfPCell(new Paragraph("FechaFinal: " + nomina.getFechaFinalPago().toString(), font3));
		celda9tabla1.setBorderWidth(0);
		//celda9tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda9tabla1);
		
		PdfPCell celda10tabla1 = new PdfPCell(new Paragraph("CURP: " + nomina.getReceptor().getCurp(), font3));
		celda10tabla1.setBorderWidth(0);
		//celda10tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda10tabla1);
		
		PdfPCell celda11tabla1 = new PdfPCell(new Paragraph("NumEmpleado: " + nomina.getReceptor().getNumEmpleado(), font3));
		celda11tabla1.setBorderWidth(0);
		//celda11tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda11tabla1);
		
		PdfPCell celda12tabla1 = new PdfPCell(new Paragraph("TipoRegimen: " + nomina.getReceptor().getTipoRegimen().value(), font3));
		celda12tabla1.setBorderWidth(0);
		//celda12tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda12tabla1);
		
		String numSeguridadSocial = nomina.getReceptor().getNumSeguridadSocial();
		if (numSeguridadSocial == null) {
			numSeguridadSocial = "";
		}
		PdfPCell celda13tabla1 = new PdfPCell(new Paragraph("NumSegSocial: " + numSeguridadSocial, font3));
		celda13tabla1.setBorderWidth(0);
		//celda13tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda13tabla1);
		
		String departamento = nomina.getReceptor().getDepartamento();
		if (departamento == null) {
			departamento = "";
		}
		PdfPCell celda14tabla1 = new PdfPCell(new Paragraph("Departamento: " + departamento, font3));
		celda14tabla1.setBorderWidth(0);
		//celda14tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda14tabla1);
		
		PdfPCell celda15tabla1 = new PdfPCell(new Paragraph("CLABE: ", font3));
		celda15tabla1.setBorderWidth(0);
		//celda15tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda15tabla1);
		
		PdfPCell celda16tabla1 = new PdfPCell(new Paragraph("Banco: ", font3));
		celda16tabla1.setBorderWidth(0);
		//celda16tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda16tabla1);
		
		PdfPCell celda17tabla1 = new PdfPCell(new Paragraph("FechaIniRelLab: " + fechaIngreso, font3));
		celda17tabla1.setBorderWidth(0);
		//celda17tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda17tabla1); 
		
		String puesto = nomina.getReceptor().getPuesto();
		if (puesto == null) {
			puesto = "";
		}
		PdfPCell celda18tabla1 = new PdfPCell(new Paragraph("Puesto: " + puesto, font3));
		celda18tabla1.setBorderWidth(0);
		//celda18tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda18tabla1);
		
		PdfPCell celda19tabla1 = new PdfPCell(new Paragraph("TipoContrato: " + nomina.getReceptor().getTipoContrato().value(), font3));
		celda19tabla1.setBorderWidth(0);
		//celda19tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda19tabla1);
		
		PdfPCell celda20tabla1 = new PdfPCell(new Paragraph("TipoJornada: ", font3));
		celda20tabla1.setBorderWidth(0);
		//celda20tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda20tabla1);
		
		PdfPCell celda21tabla1 = null;
		BigDecimal salarioBaseCotApor = nomina.getReceptor().getSalarioBaseCotApor();
		if (salarioBaseCotApor == null) {
			celda21tabla1 = new PdfPCell(new Paragraph("SDI IMSS: " , font3));
		} else {
			celda21tabla1 = new PdfPCell(new Paragraph("SDI IMSS: " + salarioBaseCotApor, font3));
		}
		celda21tabla1.setBorderWidth(0);
		//celda21tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda21tabla1);
		
		PdfPCell celda22tabla1 = new PdfPCell(new Paragraph("NumDiasPagados: " + nomina.getNumDiasPagados(), font3));
		celda22tabla1.setBorderWidth(0);
		//celda22tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda22tabla1);
		
		PdfPCell celda23tabla1 = new PdfPCell(new Paragraph("FechaPago: " + fechaPago, font3));
		celda23tabla1.setBorderWidth(0);
		//celda23tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda23tabla1);
		
		PdfPCell celda24tabla1 = new PdfPCell(new Paragraph("SDI Indemnización: ", font3));
		celda24tabla1.setBorderWidth(0);
		//celda24tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda24tabla1);
		
		PdfPCell celda25tabla1 = new PdfPCell(new Paragraph("CURP Emisor: ", font3));
		celda25tabla1.setBorderWidth(0);
		//celda25tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda25tabla1);
		
		PdfPCell celda26tabla1 = new PdfPCell(new Paragraph("Tipo: " + nomina.getTipoNomina(), font3));
		celda26tabla1.setBorderWidth(0);
		//celda26tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda26tabla1);
		
		PdfPCell celda27tabla1 = new PdfPCell(new Paragraph("Imp Hrs Simples: ", font3));
		celda27tabla1.setBorderWidth(0);
		//celda27tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda27tabla1);
		
		PdfPCell celda28tabla1 = new PdfPCell(new Paragraph("Imp Hrs Dobles: ", font3));
		celda28tabla1.setBorderWidth(0);
		//celda28tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda28tabla1);
		
		PdfPCell celda29tabla1 = new PdfPCell(new Paragraph("Imp Hrs Triples: ", font3));
		celda29tabla1.setBorderWidth(0);
		//celda29tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda29tabla1);
		
		PdfPCell celda30tabla1 = new PdfPCell(new Paragraph("Antiguedad: ", font3));
		celda30tabla1.setBorderWidth(0);
		//celda30tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda30tabla1);
		
		PdfPCell celda31tabla1 = new PdfPCell(new Paragraph("Ent Fed: " + nomina.getReceptor().getClaveEntFed(), font3));
		celda31tabla1.setBorderWidth(0);
		//celda31tabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celda31tabla1);
		
		PdfPCell celdaVaciaTabla1 = new PdfPCell(new Paragraph(" "));
		celdaVaciaTabla1.setBorderWidth(0);
		//celdaVaciaTabla1.setBackgroundColor(otroGris);
		tabla1.addCell(celdaVaciaTabla1);
		
		document.add(tabla1); /////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		PdfPTable table4 = new PdfPTable(2);
		table4.setWidthPercentage(100);
		table4.setWidths(new int[] { 55, 45 });

		PdfPCell cell1table4 = new PdfPCell(new Paragraph("PERCEPCIONES", font3));
		cell1table4.setBackgroundColor(otroGris);
		cell1table4.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell2table4 = new PdfPCell(new Paragraph("DEDUCCIONES", font3));
		cell2table4.setBackgroundColor(otroGris);
		cell2table4.setHorizontalAlignment(Element.ALIGN_CENTER);

		table4.addCell(cell1table4);
		table4.addCell(cell2table4);

		document.add(table4);

		PdfPTable table5 = new PdfPTable(7);
		table5.setWidthPercentage(100);
		table5.setWidths(new int[] { 7, 30, 9, 9, 7, 28, 10 });

		PdfPCell cell1table5 = new PdfPCell(new Paragraph("Tipo", font3));
		cell1table5.setBackgroundColor(otroGris);
		cell1table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell2table5 = new PdfPCell(new Paragraph("Clave/Descripción", font3));
		cell2table5.setBackgroundColor(otroGris);
		cell2table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell3table5 = new PdfPCell(new Paragraph("Importe Gravado", font3));
		cell3table5.setBackgroundColor(otroGris);
		cell3table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell celdaEtqImporteExento = new PdfPCell(new Paragraph("Importe Exento", font3));
		celdaEtqImporteExento.setBackgroundColor(otroGris);
		celdaEtqImporteExento.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell4table5 = new PdfPCell(new Paragraph("Tipo", font3));
		cell4table5.setBackgroundColor(otroGris);
		cell4table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell5table5 = new PdfPCell(new Paragraph("Clave/Descripción", font3));
		cell5table5.setBackgroundColor(otroGris);
		cell5table5.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell6table5 = new PdfPCell(new Paragraph("Importe", font3));
		cell6table5.setBackgroundColor(otroGris);
		cell6table5.setHorizontalAlignment(Element.ALIGN_CENTER);

		///////////////////////////////////////////////////////////////////////////////////////////////////

		PdfPCell dumbCell = new PdfPCell(new Paragraph(" "));
		dumbCell.setBorderWidthTop(0);
		dumbCell.setBorderWidthBottom(0);

		///////////////////////////////////////////////////////////////////////////////////////////////////

		table5.addCell(cell1table5);
		table5.addCell(cell2table5);
		table5.addCell(cell3table5);
		table5.addCell(celdaEtqImporteExento);
		table5.addCell(cell4table5);
		table5.addCell(cell5table5);
		table5.addCell(cell6table5);

		document.add(table5);

		// for(int i = 0; i < 30; i ++){
		// table5.addCell(dumbCell);
		// }
		//
		// PdfPCell dumbCellTwo = new PdfPCell(new PdfPCell(new Paragraph("
		// ")));
		// dumbCellTwo.setBorderWidthTop(0);
		//
		// for(int i = 0; i < 6; i ++){
		// table5.addCell(dumbCellTwo);
		// }

		// Tabla de percepciones y de deducciones
		PdfPTable tablota = new PdfPTable(2);
		tablota.setWidthPercentage(100);
		tablota.setWidths(new int[] { 55, 45 });

		// tabla de percepciones
		PdfPTable tablaPercepciones = new PdfPTable(4);
		tablaPercepciones.setWidths(new int[] { 7, 30, 9, 9 });

		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		

		double totalPercepciones = 0;
		for (NominaElement.Percepciones.Percepcion per : nomina.getPercepciones().getPercepcion()) {
			PdfPCell tipoPerCell = new PdfPCell(new Paragraph(per.getTipoPercepcion().value(), font3));
			tipoPerCell.setBorderWidthTop(0);
			tipoPerCell.setBorderWidthBottom(0);
			tablaPercepciones.addCell(tipoPerCell);

			PdfPCell desPerCell = new PdfPCell(new Paragraph(per.getClave() + " - " + per.getConcepto(), font3));
			desPerCell.setBorderWidthTop(0);
			desPerCell.setBorderWidthBottom(0);
			tablaPercepciones.addCell(desPerCell);

			PdfPCell impGravadoPerCell = new PdfPCell(new Paragraph(formatter.format(per.getImporteGravado()), font3));
			impGravadoPerCell.setBorderWidthTop(0);
			impGravadoPerCell.setBorderWidthBottom(0);
			tablaPercepciones.addCell(impGravadoPerCell);
			totalPercepciones += per.getImporteGravado().doubleValue();
			
			PdfPCell impExentoPerCell = new PdfPCell(new Paragraph(formatter.format(per.getImporteExento()), font3));
			impExentoPerCell.setBorderWidthTop(0);
			impExentoPerCell.setBorderWidthBottom(0);
			tablaPercepciones.addCell(impExentoPerCell);
			totalPercepciones += per.getImporteExento().doubleValue();
		}

		PdfPCell emptyCellDos = new PdfPCell(new Paragraph(" "));
		emptyCellDos.setBorderWidthTop(0);
		emptyCellDos.setBorderWidthBottom(0);

		for (int i = 0; i < (10 - (nomina.getPercepciones().getPercepcion().size() * 3)); i++) {
			tablaPercepciones.addCell(emptyCellDos);
		}

		PdfPTable tablaDeducciones = new PdfPTable(3);
		tablaDeducciones.setWidths(new int[] { 7, 28, 10 });

		double totalDeducciones = 0;
		for (NominaElement.Deducciones.Deduccion ded : nomina.getDeducciones().getDeduccion()) {
			PdfPCell tipoPerCell = new PdfPCell(new Paragraph(ded.getTipoDeduccion().value(), font3));
			tipoPerCell.setBorderWidthTop(0);
			tipoPerCell.setBorderWidthBottom(0);
			tablaDeducciones.addCell(tipoPerCell);

			PdfPCell desPerCell = new PdfPCell(new Paragraph(ded.getClave() + " - " + ded.getConcepto(), font3));
			desPerCell.setBorderWidthTop(0);
			desPerCell.setBorderWidthBottom(0);
			tablaDeducciones.addCell(desPerCell);

			PdfPCell impPerCell = new PdfPCell(new Paragraph(formatter.format(ded.getImporte()), font3));
			impPerCell.setBorderWidthTop(0);
			impPerCell.setBorderWidthBottom(0);
			tablaDeducciones.addCell(impPerCell);

			totalDeducciones += ded.getImporte().doubleValue();
		}

		for (int i = 0; i < (30 - (nomina.getDeducciones().getDeduccion().size() * 3)); i++) {
			tablaDeducciones.addCell(emptyCellDos);
		}

		PdfPCell perCell = new PdfPCell(tablaPercepciones);
		perCell.setBorderWidthTop(0);
		perCell.setBorderWidthLeft(0);
		perCell.setBorderWidthRight(0);
		tablota.addCell(perCell);

		PdfPCell dedCell = new PdfPCell(tablaDeducciones);
		dedCell.setBorderWidthTop(0);
		dedCell.setBorderWidthLeft(0);
		dedCell.setBorderWidthRight(0);
		tablota.addCell(dedCell);

		document.add(tablota);

		PdfPTable table6 = new PdfPTable(6);
		table6.setWidthPercentage(100);
		table6.setWidths(new int[] { 40, 6, 9, 29, 6, 10 });

		PdfPCell cell1table6 = new PdfPCell(new Paragraph("Total: ", font2));
		//cell1table6.setBorderWidth(0);
		PdfPCell cell2table6 = new PdfPCell(new Paragraph(formatter.format(totalPercepciones), font2));
		//cell2table6.setBorderWidth(0);
		PdfPCell cell3table6 = new PdfPCell(new Paragraph("Total: ", font2));
		//cell3table6.setBorderWidth(0);
		PdfPCell cell4table6 = new PdfPCell(new Paragraph(formatter.format(totalDeducciones), font2));
		//cell4table6.setBorderWidth(0);
		PdfPCell cell5table6 = new PdfPCell(new Paragraph("PAGAR: ", font2));
		//cell5table6.setBorderWidth(0);
		PdfPCell cell6table6 = new PdfPCell(new Paragraph(formatter.format(cfdi.getTotal()), font2));
		//cell6table6.setBorderWidth(0);

		table6.addCell(emptyCell);
		table6.addCell(cell1table6);
		table6.addCell(cell2table6);
		table6.addCell(emptyCell);
		table6.addCell(cell3table6);
		table6.addCell(cell4table6);
		table6.addCell(emptyCell);
		table6.addCell(emptyCell);
		table6.addCell(emptyCell);
		table6.addCell(emptyCell);
		table6.addCell(cell5table6);
		table6.addCell(cell6table6);

		document.add(table6);
		
		// Timbre Fiscal Digital
		PdfPTable tablaTimbreFiscalDigital = new PdfPTable(3);
		tablaTimbreFiscalDigital.setWidthPercentage(100);
		tablaTimbreFiscalDigital.setWidths(new int[] { 25, 60, 15 });
		
		PdfPCell celdaTablaQRCode = new PdfPCell();
		celdaTablaQRCode.setBorder(PdfPCell.NO_BORDER);
		PdfPTable tablaQRCode = new PdfPTable(1);
		Image imgQRCode = Image.getInstance(codigoQR);
		Chunk chunkQRCode = new Chunk(imgQRCode, 1.5F, -78F);
		Phrase fraseQRCode = new Phrase();
//		fraseQRCode.add(Chunk.NEWLINE);
		fraseQRCode.add(chunkQRCode);
		PdfPCell celdaQRCode = new PdfPCell();
		celdaQRCode.addElement(fraseQRCode);
		celdaQRCode.setBorder(PdfPCell.NO_BORDER);
		tablaQRCode.addCell(celdaQRCode);
		celdaTablaQRCode.addElement(tablaQRCode);
		
		PdfPCell cell1table7 = new PdfPCell();
		cell1table7.setBorderWidth(0);

		Phrase line1footer = new Phrase();
		Chunk line1part1 = new Chunk("Num. certificado emisor ", font2);
		Chunk line1part2 = new Chunk(cfdi.getNoCertificado(), font3);
		Chunk line1part3 = new Chunk("  Num. certificado SAT ", font2);
		Chunk line1part4 = new Chunk(tfd.getNoCertificadoSAT(), font3);
		line1footer.add(line1part1);
		line1footer.add(line1part2);
		line1footer.add(line1part3);
		line1footer.add(line1part4);

		PdfPCell celdaNumCertificados = new PdfPCell(line1footer);
		celdaNumCertificados.setBorderWidth(0);

		Phrase line2footer = new Phrase();
		Chunk line2part1 = new Chunk("Folio Fiscal: ", font2);
		Chunk line2part2 = new Chunk(tfd.getUUID(), font3);
		Chunk line2part3 = new Chunk("  Fecha de Certificación: ", font2);
		Chunk line2part4 = new Chunk(tfd.getFechaTimbrado() + " \n\n", font3);
		line2footer.add(line2part1);
		line2footer.add(line2part2);
		line2footer.add(line2part3);
		line2footer.add(line2part4);

		PdfPCell celdaFolioFiscalYFecha = new PdfPCell(line2footer);
		celdaFolioFiscalYFecha.setBorderWidth(0);

		Phrase line3footer = new Phrase();
		Chunk line3part1 = new Chunk("Sello del emisor ", font2);
		Chunk line3part2 = new Chunk(tfd.getSelloCFD(),font3);
		line3footer.add(line3part1);
		line3footer.add(line3part2);
		
		PdfPCell celdaSelloEmisor = new PdfPCell(line3footer);
		celdaSelloEmisor.setBorderWidth(0);
		
		Phrase line4footer = new Phrase();
		Chunk line4part1 = new Chunk("Sello del SAT ", font2);
		Chunk line4part2 = new Chunk(tfd.getSelloSAT(),
				font3);
		line4footer.add(line4part1);
		line4footer.add(line4part2);
		
		PdfPCell celdaSelloSAT = new PdfPCell(line4footer);
		celdaSelloSAT.setBorderWidth(0);
		
		Phrase line5footer = new Phrase();
		Chunk line5part1 = new Chunk("Cadena Original TFD", font2);
		Chunk line5part2 = new Chunk(selloDigital, font3);
		line5footer.add(line5part1);
		line5footer.add(line5part2);
		
		PdfPCell celdaCadenaOriginal = new PdfPCell(line5footer);
		celdaCadenaOriginal.setBorderWidth(0);


		tablaTimbreFiscalDigital.addCell(celdaTablaQRCode);
		tablaTimbreFiscalDigital.addCell(celdaNumCertificados);
		tablaTimbreFiscalDigital.addCell(cell1table7);

		tablaTimbreFiscalDigital.addCell(cell1table7);
		tablaTimbreFiscalDigital.addCell(celdaFolioFiscalYFecha);
		tablaTimbreFiscalDigital.addCell(cell1table7);
	
		tablaTimbreFiscalDigital.addCell(cell1table7);
		tablaTimbreFiscalDigital.addCell(celdaSelloEmisor);
		
		PdfPCell firmaCell = new PdfPCell(new Paragraph("Firma del Empleado", font3));
		firmaCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		firmaCell.setBorderWidthBottom(0);
		firmaCell.setBorderWidthLeft(0);
		firmaCell.setBorderWidthRight(0);
		tablaTimbreFiscalDigital.addCell(firmaCell);
		
		tablaTimbreFiscalDigital.addCell(cell1table7);
		tablaTimbreFiscalDigital.addCell(celdaSelloSAT);
		tablaTimbreFiscalDigital.addCell(cell1table7);
		
		tablaTimbreFiscalDigital.addCell(cell1table7);
		tablaTimbreFiscalDigital.addCell(celdaCadenaOriginal);
		tablaTimbreFiscalDigital.addCell(cell1table7);

		document.add(tablaTimbreFiscalDigital);
	
		return document;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public ListaPagosVO getLista() {
		return lista;
	}

	public void setLista(ListaPagosVO lista) {
		this.lista = lista;
	}

	
	public void crearMarcaDeAgua(String contenido, PdfWriter writer) {
		PdfContentByte fondo = writer.getDirectContent();
		Font fuente = new Font(FontFamily.HELVETICA, 45);
		Phrase frase = new Phrase(contenido, fuente);
		fondo.saveState();
		PdfGState gs1 = new PdfGState();
		gs1.setFillOpacity(0.5f);
		fondo.setGState(gs1);
		ColumnText.showTextAligned(fondo, Element.ALIGN_CENTER, frase, 297, 250, 45);
		
		ColumnText.showTextAligned(fondo, Element.ALIGN_CENTER, frase, 297, 650, 45);
		
		fondo.restoreState();
		
	}
}
