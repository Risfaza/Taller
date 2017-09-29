package com.tikal.tallerWeb.control.restControllers.VO;

import java.util.List;

import com.tikal.cacao.sat.cfd.Comprobante.Receptor;
import com.tikal.tallerWeb.modelo.entity.PresupuestoEntity;

public class FacturaVO {
	private List<PresupuestoEntity> conceptos;
	private Receptor receptor;
	private String metodo;
	
	public List<PresupuestoEntity> getConceptos() {
		return conceptos;
	}
	public void setConceptos(List<PresupuestoEntity> conceptos) {
		this.conceptos = conceptos;
	}
	public Receptor getReceptor() {
		return receptor;
	}
	public void setReceptor(Receptor receptor) {
		this.receptor = receptor;
	}
	public String getMetodo() {
		return metodo;
	}
	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}
	
	
	
}
