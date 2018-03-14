package com.tikal.cacao.model;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.tikal.cacao.factura.Estatus;

@Entity
public class CFDINominaAsimilado {
	
	@Id
	private String uuid;
	
	@Index
	private Date fechaDePago;
	
	private Date fechaTimbrado;
	
	@Index
	private String rfcEmisor;
	
	@Index
	private String nombreEmisor;
	
	private double cantidadAPagar;
	
	@Index
	/* Clasificación para los recibos de nómina IAS*/
	private String grupo;
	
	@Index
	private String nombreTrabajadorIAS;
	
	@Index
	private String rfcTrabajadorIAS;
	
	private String curpTrabajadorIAS;
	
	private String xmlCFDI;
	
	/**/
	private String selloDigital;
	
	private byte[] codigoQR;
	
	private Estatus estatus;
	
	@Index
	private String serie;
	
	@Index
	private String folio;
	
	private String acuseCancelacionXML;
	
	private Date fechaCancelacion;
	
	private String selloCancelacion;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getFechaDePago() {
		return fechaDePago;
	}

	public void setFechaDePago(Date fechaDePago) {
		this.fechaDePago = fechaDePago;
	}
	
	public Date getFechaTimbrado() {
		return fechaTimbrado;
	}

	public void setFechaTimbrado(Date fechaTimbrado) {
		this.fechaTimbrado = fechaTimbrado;
	}

	public String getRfcEmisor() {
		return rfcEmisor;
	}

	public void setRfcEmisor(String rfcEmisor) {
		this.rfcEmisor = rfcEmisor;
	}

	public String getNombreEmisor() {
		return nombreEmisor;
	}
	
	public void setNombreEmisor(String nombreEmisor) {
		this.nombreEmisor = nombreEmisor;
	}
	
	public double getCantidadAPagar() {
		return cantidadAPagar;
	}

	public void setCantidadAPagar(double cantidadAPagar) {
		this.cantidadAPagar = cantidadAPagar;
	}
	
	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getNombreTrabajadorIAS() {
		return nombreTrabajadorIAS;
	}

	public void setNombreTrabajadorIAS(String nombreTrabajadorIAS) {
		this.nombreTrabajadorIAS = nombreTrabajadorIAS;
	}

	public String getRfcTrabajadorIAS() {
		return rfcTrabajadorIAS;
	}

	public void setRfcTrabajadorIAS(String rfcTrabajadorIAS) {
		this.rfcTrabajadorIAS = rfcTrabajadorIAS;
	}
	
	public String getCurpTrabajadorIAS() {
		return curpTrabajadorIAS;
	}
	
	public void setCurpTrabajadorIAS(String curpTrabajadorIAS) {
		this.curpTrabajadorIAS = curpTrabajadorIAS;
	}

	public String getXmlCFDI() {
		return xmlCFDI;
	}

	public void setXmlCFDI(String xmlCFDI) {
		this.xmlCFDI = xmlCFDI;
	}

	public String getSelloDigital() {
		return selloDigital;
	}

	public void setSelloDigital(String selloDigital) {
		this.selloDigital = selloDigital;
	}

	public byte[] getCodigoQR() {
		return codigoQR;
	}

	public void setCodigoQR(byte[] codigoQR) {
		this.codigoQR = codigoQR;
	}

	public Estatus getEstatus() {
		return estatus;
	}

	public void setEstatus(Estatus estatus) {
		this.estatus = estatus;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getAcuseCancelacionXML() {
		return acuseCancelacionXML;
	}

	public void setAcuseCancelacionXML(String acuseCancelacionXML) {
		this.acuseCancelacionXML = acuseCancelacionXML;
	}

	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}

	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}

	public String getSelloCancelacion() {
		return selloCancelacion;
	}

	public void setSelloCancelacion(String selloCancelacion) {
		this.selloCancelacion = selloCancelacion;
	}
	
}
