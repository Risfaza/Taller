package com.tikal.tallerWeb.control.restControllers.VO;

import java.util.List;

import com.tikal.tallerWeb.rest.util.NewServiceObject;

public class DatosServicioVO {
	NewServiceObject servicio;
	List<GruposCosto> presupuesto;
	List<String> imagenes;
	public NewServiceObject getServicio() {
		return servicio;
	}
	public void setServicio(NewServiceObject servicio) {
		this.servicio = servicio;
	}
	public List<GruposCosto> getPresupuesto() {
		return presupuesto;
	}
	public void setPresupuesto(List<GruposCosto> presupuesto) {
		this.presupuesto = presupuesto;
	}
	
	
}
