package com.tikal.cacao.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.google.apphosting.api.ApiProxy.OverQuotaException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.tikal.cacao.factura.Estatus;
import com.tikal.cacao.model.CFDINominaAsimilado;
import com.tikal.cacao.model.Factura;
import com.tikal.cacao.model.FacturaVTT;
import com.tikal.cacao.model.Imagen;
import com.tikal.cacao.sat.cfd.Comprobante;

public class EmailSender {
	
	private String descripcionUsoDeCFDI;
	private String descripcionRegimenFiscal;
	private String descripcionFormaDePago;
	private String descripcionTipoDeComprobante;
	
	public EmailSender() {
	}
	
	public EmailSender(String descripcionUsoDeCFDI, String descripcionRegimenFiscal, String descripcionFormaDePago, String descripcionTipoDeComprobante) {
		this.descripcionUsoDeCFDI = descripcionUsoDeCFDI;
		this.descripcionRegimenFiscal = descripcionRegimenFiscal;
		this.descripcionFormaDePago = descripcionFormaDePago;
		this.descripcionTipoDeComprobante = descripcionTipoDeComprobante;
	}

	public void enviaEmail(String emailReceptor, String nombreReceptor, String pass) throws UnsupportedEncodingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		String mensaje = "Su nueva contrase�a es: " + pass;

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("no.reply.fcon@gmail.com", "Password Reset"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceptor, nombreReceptor));
			msg.setSubject("Contrase�a Nueva");
			msg.setText(mensaje);
			Transport.send(msg);

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	
	public void enviaFactura(String emailReceptor, Factura factura,String text, String filename, Imagen urlImg){
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		
		try {
			Message msg = new MimeMessage(session);
			
			//append PDF
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setContent("<h1>Factura timbrada</h1><p>Este correo es generado de forma autom�tica. Favor de no respoder a este.</p>","text/html");
			mp.addBodyPart(mbp);
			ByteArrayOutputStream os= new ByteArrayOutputStream();
			Comprobante cfdi = Util.unmarshallXML(factura.getCfdiXML());
			PDFFactura pdfFactura = new PDFFactura();
			PdfWriter writer= PdfWriter.getInstance(pdfFactura.getDocument(), os);
			pdfFactura.getDocument().open();
			if (factura.getEstatus().equals(Estatus.TIMBRADO)){
				pdfFactura.construirPdf(cfdi, factura.getSelloDigital(), factura.getCodigoQR(),urlImg, factura.getEstatus());
			} 
			else{ 
				if (factura.getEstatus().equals(Estatus.GENERADO)){
					pdfFactura.construirPdf(cfdi, urlImg, factura.getEstatus());
				}
			}
			pdfFactura.getDocument().close();
			byte[] datap= os.toByteArray();
			MimeBodyPart attachmentp = new MimeBodyPart();
			InputStream attachmentDataStreamp = new ByteArrayInputStream(datap);
			attachmentp.setFileName(cfdi.getSerie()+cfdi.getFolio()+".pdf");
			attachmentp.setContent(attachmentDataStreamp, "application/pdf");
			mp.addBodyPart(attachmentp);
			
			//append XML file
			MimeBodyPart attachmentx= new MimeBodyPart();
			InputStream attachmentDataStreamx= new ByteArrayInputStream(factura.getCfdiXML().getBytes());
			attachmentx.setFileName(cfdi.getSerie()+cfdi.getFolio()+".xml");
			attachmentx.setContent(attachmentDataStreamx,"text/xml");
			mp.addBodyPart(attachmentx);
//			DataHandler handler;
//			mbp.setDataHandler(handler);
//			DataSource s= new DataSource();
			
//			
//			msg.setFrom(new InternetAddress("facturacion@tikal.mx", "Factura "+filename));
//			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceptor, ""));
//			msg.setSubject("Factura "+filename);
////			
//			msg.setText("correo de prueba");
			
			msg.setFrom(new InternetAddress("no.reply.fcon@gmail.com", "Facturaci�n"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceptor, "Empresa"));
			msg.setSubject("Factura "+factura.getUuid());
//			msg.setText("Prueba de correo 2");
			msg.setContent(mp);
			Transport.send(msg);

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(OverQuotaException e){
			System.out.println("Se alcanz�");
		}
	}
	
	public void enviaFactura(String emailReceptor, FacturaVTT factura, String text, Imagen urlImg, com.tikal.cacao.sat.cfd33.Comprobante cfdi) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		
		try {
			Message msg = new MimeMessage(session);
			
			//append PDF
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setContent("<h1>Factura timbrada</h1><p>Este correo es generado de forma autom�tica. Favor de no respoder a este.</p>","text/html");
			mp.addBodyPart(mbp);
			ByteArrayOutputStream os= new ByteArrayOutputStream();
			//com.tikal.cacao.sat.cfd33.Comprobante cfdi = Util.unmarshallCFDI33XML(factura.getCfdiXML());
			
			PDFFacturaV33 pdfFactura = new PDFFacturaV33(this.descripcionUsoDeCFDI, this.descripcionRegimenFiscal, this.descripcionFormaDePago, this.descripcionTipoDeComprobante);
			PdfWriter writer= PdfWriter.getInstance(pdfFactura.getDocument(), os);
			pdfFactura.getPieDePagina().setUuid(factura.getUuid());
			
			if (factura.getEstatus().equals(Estatus.CANCELADO)) {
				pdfFactura.getPieDePagina().setFechaCancel(factura.getFechaCancelacion());
				pdfFactura.getPieDePagina().setSelloCancel(factura.getSelloCancelacion());
			}
			writer.setPageEvent(pdfFactura.getPieDePagina());
			pdfFactura.getDocument().open();
			
			if (factura.getEstatus().equals(Estatus.TIMBRADO)){
				pdfFactura.construirPdf(cfdi, factura.getSelloDigital(), factura.getCodigoQR(),urlImg, factura.getEstatus(), factura.getComentarios());
			} 
			else if (factura.getEstatus().equals(Estatus.GENERADO)){
				pdfFactura.construirPdf(cfdi, urlImg, factura.getEstatus(), factura.getComentarios());
			}
			else if (factura.getEstatus().equals(Estatus.CANCELADO)) {
				pdfFactura.construirPdfCancelado(cfdi, factura.getSelloDigital(), factura.getCodigoQR(), urlImg, factura.getEstatus(),
						factura.getSelloCancelacion(), factura.getFechaCancelacion(), factura.getComentarios());
				pdfFactura.crearMarcaDeAgua("CANCELADO", writer);
			}
			pdfFactura.getDocument().close();
			byte[] datap= os.toByteArray();
			MimeBodyPart attachmentp = new MimeBodyPart();
			InputStream attachmentDataStreamp = new ByteArrayInputStream(datap);
			attachmentp.setFileName(cfdi.getSerie()+cfdi.getFolio()+".pdf");
			attachmentp.setContent(attachmentDataStreamp, "application/pdf");
			mp.addBodyPart(attachmentp);
			
			//append XML file
			MimeBodyPart attachmentx= new MimeBodyPart();
			InputStream attachmentDataStreamx= new ByteArrayInputStream(factura.getCfdiXML().getBytes());
			attachmentx.setFileName(cfdi.getSerie()+cfdi.getFolio()+".xml");
			attachmentx.setContent(attachmentDataStreamx,"text/xml");
			mp.addBodyPart(attachmentx);
			
			msg.setFrom(new InternetAddress("no.reply.fcon@gmail.com", "Facturaci�n"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceptor, "Empresa"));
			msg.setSubject("Factura "+factura.getUuid());
//			msg.setText("Prueba de correo 2");
			msg.setContent(mp);
			Transport.send(msg);

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(OverQuotaException e){
			System.out.println("Se alcanz�");
		}
	}
	
	public void enviarPagoIAS(String email, CFDINominaAsimilado cfdiNominaIAS) throws DocumentException, MalformedURLException, IOException{
		
		try{
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);
		PdfMakerV2 nuevo = new PdfMakerV2();
		//		nuevo.setLista(lista);
		
		Multipart mp = new MimeMultipart();
		MimeBodyPart mbp = new MimeBodyPart();
		mbp.setContent("<h1>Factura timbrada</h1><p>Este correo es generado de forma autom�tica. Favor de no respoder a este.</p>","text/html");
		mp.addBodyPart(mbp);
		
		ByteArrayOutputStream os= new ByteArrayOutputStream();
		PdfWriter writer= PdfWriter.getInstance(nuevo.getDocument(),os );
		com.tikal.cacao.sat.cfd33.Comprobante comprobante = Util.unmarshallCFDI33XML(cfdiNominaIAS.getXmlCFDI());
		
		nuevo.getDocument().open();
		//nuevo.construirPDF(comprobante, cfdiNominaIAS.getEstatus(),  cfdiNominaIAS.getSelloDigital(), cfdiNominaIAS.getCodigoQR());
		//nuevo.construirPdf(comprobante, cfdiNominaIAS.getSelloDigital(), cfdiNominaIAS.getCodigoQR()); //antes pago
		nuevo.getDocument().close();
		
		byte[] datap= os.toByteArray();
		MimeBodyPart attachmentp = new MimeBodyPart();
		InputStream attachmentDataStreamp = new ByteArrayInputStream(datap);
		attachmentp.setFileName(cfdiNominaIAS.getNombreTrabajadorIAS()+"_"+cfdiNominaIAS.getSerie()+cfdiNominaIAS.getFolio()+".pdf");
		attachmentp.setContent(attachmentDataStreamp, "application/pdf");
		mp.addBodyPart(attachmentp);
		
		//append XML file
		if(cfdiNominaIAS.getEstatus() == Estatus.TIMBRADO){
//			MimeBodyPart attachmentx= new MimeBodyPart();
//			InputStream attachmentDataStreamx= new ByteArrayInputStream(cfdiNominaIAS.getXmlCFDI().getBytes());
//			attachmentx.setFileName(cfdiNominaIAS.getSerie()+cfdiNominaIAS.getFolio()+".xml");
//			attachmentx.setContent(attachmentDataStreamx,"text/xml");
//			mp.addBodyPart(attachmentx);
		}
		
		msg.setFrom(new InternetAddress("no.reply.fcon@gmail.com", "Facturaci�n"));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email, "Empresa"));
		msg.setSubject("Factura "+cfdiNominaIAS.getUuid());
//		msg.setText("Prueba de correo 2");
		msg.setContent(mp);
		Transport.send(msg);
		}catch(Exception e){
			System.out.println("No se envi� el mail");
		}
		
	}
}
