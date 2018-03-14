package com.tikal.cacao.model;

public class Concepto {

	private String noIdentificacion;
	
	private String unidad;
	
	private String descripcion;
	
	private float valorUnitario;
	
	private String descripcionSAT;
	
	private String claveProdServ;
	
	private String claveUnidad;
	
	private String descripcionUnidadSAT;

	public String getNoIdentificacion() {
		return noIdentificacion;
	}

	public void setNoIdentificacion(String noIdentificacion) {
		this.noIdentificacion = noIdentificacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(float valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getClaveProdServ() {
		return claveProdServ;
	}

	public void setClaveProdServ(String claveProdServ) {
		this.claveProdServ = claveProdServ;
	}

	public String getDescripcionSAT() {
		return descripcionSAT;
	}

	public void setDescripcionSAT(String descripcionSAT) {
		this.descripcionSAT = descripcionSAT;
	}
	
	public String getClaveUnidad() {
		return claveUnidad;
	}
	
	public void setClaveUnidad(String claveUnidad) {
		this.claveUnidad = claveUnidad;
	}

	public String getDescripcionUnidadSAT() {
		return descripcionUnidadSAT;
	}

	public void setDescripcionUnidadSAT(String descripcionUnidadSAT) {
		this.descripcionUnidadSAT = descripcionUnidadSAT;
	}

	
}
