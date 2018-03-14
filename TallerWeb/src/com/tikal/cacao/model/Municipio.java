package com.tikal.cacao.model;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Municipio {

	@Id
	private String estado;
	
	private List<String> municipios;
	
	public Municipio(){
		this.municipios= new ArrayList<String>();
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<String> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<String> municipios) {
		this.municipios = municipios;
	}
	
	
}
