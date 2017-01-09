package com.tikal.tallerWeb.control.restControllers.VO;

import java.util.ArrayList;
import java.util.List;

public class BusquedaVO {
	List<String> nombres;
	List<String> tipos;
	
	public BusquedaVO(){
		nombres= new ArrayList<String>();
		tipos= new ArrayList<String>();
	}

	public List<String> getNombres() {
		return nombres;
	}

	public void setNombres(List<String> nombres) {
		this.nombres = nombres;
	}

	public List<String> getTipos() {
		return tipos;
	}

	public void setTipos(List<String> tipos) {
		this.tipos = tipos;
	}
	
	
}
