package com.tikal.tallerWeb.control.restControllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfWriter;
import com.tikal.cacao.factura.Estatus;
import com.tikal.cacao.factura.ws.WSClient;
import com.tikal.cacao.model.Imagen;
import com.tikal.cacao.sat.cfd.Comprobante;
import com.tikal.cacao.sat.cfd.Comprobante.Conceptos;
import com.tikal.cacao.sat.cfd.Comprobante.Conceptos.Concepto;
import com.tikal.cacao.sat.cfd.Comprobante.Impuestos;
import com.tikal.cacao.sat.cfd.Comprobante.Impuestos.Traslados;
import com.tikal.cacao.sat.cfd.Comprobante.Impuestos.Traslados.Traslado;
import com.tikal.cacao.sat.cfd.ObjectFactoryComprobante;
import com.tikal.cacao.sat.timbrefiscaldigital.TimbreFiscalDigital;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.tallerWeb.control.restControllers.VO.FacturaVO;
import com.tikal.tallerWeb.data.access.FacturaDAO;
import com.tikal.tallerWeb.data.access.FolioDAO;
import com.tikal.tallerWeb.data.access.rest.CostoDAOImp;
import com.tikal.tallerWeb.modelo.entity.Factura;
import com.tikal.tallerWeb.modelo.entity.PresupuestoEntity;
import com.tikal.tallerWeb.util.EmailSender;
import com.tikal.tallerWeb.util.JsonConvertidor;
import com.tikal.tallerWeb.util.PDFFactura;
import com.tikal.tallerWeb.util.Util;

import localhost.TimbraCFDIResponse;

@Controller
@RequestMapping(value={"/facturar"})
public class FacturaController {
	
	//@Autowired
	FolioDAO foliodao;
	
	//@Autowired
	FacturaDAO facturaDAO;
	
	//@Autowired
	CostoDAOImp costodao;
	
	@Autowired
	@Qualifier("testClient")
	WSClient client;
	
	private static final String urlVerifyCaptcha = "https://www.google.com/recaptcha/api/siteverify";
	
	private static final String serverK = "6Ld4bikUAAAAAGtL91J0j65RuFxDWO-xKx06lPoy";
	
	@PostConstruct
	public void init() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package with the WSDL java classes
		marshaller.setContextPath("localhost");

		client.setDefaultUri("http://www.timbracfdipruebas.mx/serviciointegracionpruebas/Timbrado.asmx");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);

	}
	
	@RequestMapping(value = {
	"/facturar" }, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void add(HttpServletRequest request, HttpServletResponse res, @RequestBody String json) throws IOException{
		FacturaVO f= (FacturaVO) JsonConvertidor.fromJson(json, FacturaVO.class);
		ObjectFactoryComprobante ofComprobante = new ObjectFactoryComprobante();
		Comprobante comprobante = ofComprobante.createComprobante();
		comprobante.setFecha(com.tikal.tallerWeb.util.Util.getXMLDate( new Date() ) );
		comprobante.setLugarExpedicion("MÉXICO");
		comprobante.setMoneda("MXN");
		comprobante.setTipoDeComprobante("ingreso");
		comprobante.setFormaDePago("Pago en una sola exhibición");
		comprobante.setMetodoDePago(f.getMetodo());
		//comprobante.setSubTotal(new BigDecimal(200));
		//comprobante.setTotal(new BigDecimal(200.0));
		comprobante.setVersion("3.2");

		Comprobante.Emisor emisor = ofComprobante.createComprobanteEmisor();
		//emisor.setRfc("AAA010101AAA");
		emisor.setNombre("ACE México S.A. de C.V.");
		emisor.setRfc("AAA010101AAA");
		Comprobante.Emisor.RegimenFiscal regimenFiscalEmisor = ofComprobante.createComprobanteEmisorRegimenFiscal();
		regimenFiscalEmisor.setRegimen("General de Ley Personas Morales");
		emisor.getRegimenFiscal().add(regimenFiscalEmisor);
		comprobante.setEmisor(emisor);
		comprobante.setFolio(foliodao.getFolio()+"");
		comprobante.setSerie("FK");
		
		Conceptos con= new Conceptos();
		float subtotal=0;
		for(PresupuestoEntity p:f.getConceptos()){
			Concepto c=new Concepto();
			c.setCantidad(new BigDecimal(p.getCantidad()));
			c.setUnidad(this.getUnidad(p.getTipo()));
			c.setDescripcion(p.getConcepto());
			c.setValorUnitario(new BigDecimal(p.getPrecioCliente().getValue()));
			float vu=Float.parseFloat(p.getPrecioCliente().getValue());
			c.setImporte(new BigDecimal(vu* p.getCantidad()).setScale(2,RoundingMode.HALF_UP));
			subtotal+= Float.parseFloat(p.getPrecioCliente().getValue())*p.getCantidad();
			con.getConcepto().add(c);
		}
		
		
		comprobante.setConceptos(con);
		
		comprobante.setSubTotal(new BigDecimal(subtotal).setScale(2,RoundingMode.HALF_UP));
		Impuestos impuestos=new Impuestos();
		Traslado traslado= new Traslado();
		
		float iva=subtotal*0.16f;
		traslado.setImporte(new BigDecimal(iva).setScale(2,RoundingMode.HALF_UP));
		traslado.setImpuesto("IVA");
		traslado.setTasa(new BigDecimal(0.16f).setScale(2,RoundingMode.HALF_UP));
		Traslados traslados= new Traslados();
		traslados.getTraslado().add(traslado);
		impuestos.setTraslados(traslados);
		impuestos.setTotalImpuestosTrasladados(new BigDecimal(iva).setScale(2,RoundingMode.HALF_UP));
		comprobante.setImpuestos(impuestos);
		comprobante.setTotal(new BigDecimal(subtotal+iva).setScale(2,RoundingMode.HALF_UP));
		comprobante.setReceptor(f.getReceptor());
		
		String cadenaComprobante = Util.marshallComprobante(comprobante);
		TimbraCFDIResponse timbraResponse = client.getTimbraCFDIResponse(cadenaComprobante);
		List<Object> respuesta = timbraResponse.getTimbraCFDIResult().getAnyType();
		int codigoError = (int) respuesta.get(1);
		
		PrintWriter writer = res.getWriter();
		if (codigoError == 0) {
			String cfdiXML = (String) respuesta.get(3);
			Comprobante cfdi = Util.unmarshallXML(cfdiXML);
			String selloDigital = (String) respuesta.get(5);
			byte[] bytesQRCode = (byte[]) respuesta.get(4);
			TimbreFiscalDigital timbreFD = (TimbreFiscalDigital) cfdi.getComplemento().getAny().get(0);
			Date fechaCertificacion = timbreFD.getFechaTimbrado().toGregorianCalendar().getTime();
			Factura factura = new Factura(timbreFD.getUUID(), cfdiXML, cfdi.getEmisor().getRfc(),
					cfdi.getReceptor().getNombre(), fechaCertificacion, selloDigital, bytesQRCode);
			facturaDAO.guardar(factura);

			//crearReporteRenglon(factura);
			Imagen imagen = new Imagen();
			imagen.setImage("images/FIdit.png");
			EmailSender mailero = new EmailSender();
			//Imagen imagen = imagenDAO.get(cfdi.getEmisor().getRfc());
//			mailero.enviaFactura(servicioVO.getEmail(), factura, "", cfdi.getComplemento().getAny().get(0).toString(),
//					imagen);
			
//			compra.setUuid(timbreFD.getUUID());
//			compradao.save(compra);
			String confac="";
			for(PresupuestoEntity p:f.getConceptos()){
				confac+="\n"+p.getCantidad()+" "+p.getConcepto();
				p.setDone(true);
			}
			costodao.actualiza(f.getConceptos());
			foliodao.incrementa();
			List<String> datos=new ArrayList<String>();
			datos.add(factura.getUuid());
			datos.add(cfdi.getTotal()+"");
			datos.add(confac);
			writer.print(JsonConvertidor.toJson(datos));
		} else {
			
			String mensajeError = (String) respuesta.get(2);
			System.out.println(mensajeError);
			writer.print(mensajeError);
		}
	}
	
	@RequestMapping(value = "/obtenerPDF/{uuid}", method = RequestMethod.GET, produces = "application/pdf")
	public void consultar(HttpServletRequest req, HttpServletResponse res, @PathVariable String uuid) {
		Factura factura = facturaDAO.consultar(uuid);
		if (factura != null) {
			try {
				res.setContentType("Application/Pdf");
				Comprobante cfdi = Util.unmarshallXML(factura.getCfdiXML());
				Imagen imagen = new Imagen();
				imagen.setImage("images/FIdit.png");

				PDFFactura pdfFactura = new PDFFactura();
				PdfWriter writer = PdfWriter.getInstance(pdfFactura.getDocument(), res.getOutputStream());
				pdfFactura.getPieDePagina().setUuid(uuid);
				if (factura.getEstatus().equals(Estatus.CANCELADO)) {
					pdfFactura.getPieDePagina().setFechaCancel(factura.getFechaCancelacion());
					pdfFactura.getPieDePagina().setSelloCancel(factura.getSelloCancelacion());
					;
				}
				writer.setPageEvent(pdfFactura.getPieDePagina());

				pdfFactura.getDocument().open();
				if (factura.getEstatus().equals(Estatus.TIMBRADO))
					pdfFactura.construirPdf(cfdi, factura.getSelloDigital(), factura.getCodigoQR(), imagen,
							factura.getEstatus());
				else if (factura.getEstatus().equals(Estatus.GENERADO)) {
					pdfFactura.construirPdf(cfdi, imagen, factura.getEstatus());

					PdfContentByte fondo = writer.getDirectContent();
					Font fuente = new Font(FontFamily.HELVETICA, 45);
					Phrase frase = new Phrase("Pre-factura", fuente);
					fondo.saveState();
					PdfGState gs1 = new PdfGState();
					gs1.setFillOpacity(0.5f);
					fondo.setGState(gs1);
					ColumnText.showTextAligned(fondo, Element.ALIGN_CENTER, frase, 297, 650, 45);
					fondo.restoreState();
				}

				else if (factura.getEstatus().equals(Estatus.CANCELADO)) {
					pdfFactura.construirPdfCancelado(cfdi, factura.getSelloDigital(), factura.getCodigoQR(), imagen,
							factura.getEstatus(), factura.getSelloCancelacion(), factura.getFechaCancelacion());

					pdfFactura.crearMarcaDeAgua("CANCELADO", writer);
				}

				pdfFactura.getDocument().close();
				res.getOutputStream().flush();
				res.getOutputStream().close();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		} else {
			try {
				AsignadorDeCharset.asignar(req, res);
				PrintWriter writer = res.getWriter();
				writer.println(
						"El Número de Folio Fiscal (UUID): ".concat(uuid).concat(" no pertenece a ninguna factura"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value = "/obtenerXML/{uuid}", method = RequestMethod.GET, produces = "text/xml")
	public void obtenerXML(HttpServletRequest req, HttpServletResponse res, @PathVariable String uuid) {
		try {
			AsignadorDeCharset.asignar(req, res);
			Factura factura = facturaDAO.consultar(uuid);
			PrintWriter writer = res.getWriter();
			if (factura != null) {
				res.setContentType("text/xml");
				writer.println(factura.getCfdiXML());
			} else {
				writer.println("La factuca con el folio fiscal (uuid) ".concat(uuid).concat(" no existe"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getUnidad(String tipo){
		if(tipo.compareTo("Refacciones")==0){
			return "Pieza";
		}
		if(tipo.compareTo("Insumo")==0){
			return "Pieza";
		}
		return "No Aplica";
	}
}
