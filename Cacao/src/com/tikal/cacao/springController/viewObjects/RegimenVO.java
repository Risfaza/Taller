package com.tikal.cacao.springController.viewObjects;

import com.tikal.cacao.model.Regimen;

public class RegimenVO extends Regimen {

	private String tipo;
	public RegimenVO() {

	}

	public RegimenVO(Regimen r) {
		this.setActivo(r.isActivo());
		this.setDeducciones(r.getDeducciones());
		this.setId(r.getId());
		this.setIdEmpleados(r.getIdEmpleados());
		this.setNombre(r.getNombre());
		this.setPercepciones(r.getPercepciones());
		this.setTipo(r.getTipoRegimen());
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
