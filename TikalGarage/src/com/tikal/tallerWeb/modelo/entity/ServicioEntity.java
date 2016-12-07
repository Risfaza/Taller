package com.tikal.tallerWeb.modelo.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import technology.tikal.taller.automotriz.model.servicio.Servicio;

@Entity
public class ServicioEntity extends Servicio {

	@Id
	Long idServicio;
	
	
	public long getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(long idServicio) {
		this.idServicio = idServicio;
	}


	public ServicioEntity(){
	}
	

}
