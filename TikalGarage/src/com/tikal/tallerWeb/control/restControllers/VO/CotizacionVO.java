package com.tikal.tallerWeb.control.restControllers.VO;

import java.util.List;

import com.tikal.tallerWeb.modelo.entity.CotizacionEntity;

public class CotizacionVO {
	List<CotizacionEntity> listcotizaciones;
	String tipo;
	String modelo;

	public List<CotizacionEntity> getListcotizaciones() {
		return listcotizaciones;
	}

	public void setListcotizaciones(List<CotizacionEntity> listcotizaciones) {
		this.listcotizaciones = listcotizaciones;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	
	
	
}
