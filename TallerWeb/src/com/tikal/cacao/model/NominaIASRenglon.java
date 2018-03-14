package com.tikal.cacao.model;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.tikal.cacao.factura.Estatus;

@Entity
public class NominaIASRenglon {
	
	@Id
	private String id;
	
	@Index
	private String rfcEmisor;
	
	private String rfcTrabajadorIAS;
	
	private String curp;
	
	private String nombreTrabajadorIAS;
	
//	private String claveEntFed;
	
	private String importeAsimilados;
	
	private String importeISR;
	
	private String serie;
	
	private String grupo;
	
	private String fechaInicio;
	
	private String fechaFin;
	
	private String folio;
	
	@Index
	private Date fechaPago;
	
	@Index
	private Long idConjunto;
	
	@Index
	private Estatus estatus;
//	private String codigoPostalLugarExpedicion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRfcEmisor() {
		return rfcEmisor;
	}
	
	public void setRfcEmisor(String rfcEmisor) {
		this.rfcEmisor = rfcEmisor;
	}
	
	public String getRfcTrabajadorIAS() {
		return rfcTrabajadorIAS;
	}

	public void setRfcTrabajadorIAS(String rfcTrabajadorIAS) {
		this.rfcTrabajadorIAS = rfcTrabajadorIAS;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getNombreTrabajadorIAS() {
		return nombreTrabajadorIAS;
	}

	public void setNombreTrabajadorIAS(String nombreTrabajadorIAS) {
		this.nombreTrabajadorIAS = nombreTrabajadorIAS;
	}

//	public String getClaveEntFed() {
//		return claveEntFed;
//	}
//
//	public void setClaveEntFed(String claveEntFed) {
//		this.claveEntFed = claveEntFed;
//	}

	public String getImporteAsimilados() {
		return importeAsimilados;
	}

	public void setImporteAsimilados(String importeAsimilados) {
		this.importeAsimilados = importeAsimilados;
	}

	public String getImporteISR() {
		return importeISR;
	}

	public void setImporteISR(String importeISR) {
		this.importeISR = importeISR;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Estatus getEstatus() {
		return estatus;
	}

	public void setEstatus(Estatus estatus) {
		this.estatus = estatus;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public Long getIdConjunto() {
		return idConjunto;
	}

	public void setIdConjunto(Long idConjunto) {
		this.idConjunto = idConjunto;
	}


//	public String getCodigoPostalLugarExpedicion() {
//		return codigoPostalLugarExpedicion;
//	}
//
//	public void setCodigoPostalLugarExpedicion(String codigoPostalLugarExpedicion) {
//		this.codigoPostalLugarExpedicion = codigoPostalLugarExpedicion;
//	}
	
	
}
