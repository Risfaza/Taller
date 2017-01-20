package com.tikal.tallerWeb.modelo.entity;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import technology.tikal.taller.automotriz.model.servicio.Servicio;

@Entity
public class ServicioEntity extends Servicio {

	@Id
	Long idServicio;
	private List<String> proveedores;
	
	public long getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(long idServicio) {
		this.idServicio = idServicio;
	}


	public ServicioEntity(){
		this.proveedores= new ArrayList<String>();
	}

	public List<String> getProveedores() {
		return proveedores;
	}

	public void setProveedores(List<String> proveedores) {
		this.proveedores = proveedores;
	}
	

}
