package com.tikal.tallerWeb.modelo.entity;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import technology.tikal.taller.automotriz.model.servicio.Servicio;
import technology.tikal.taller.automotriz.model.servicio.bitacora.Evento;

@Entity
public class ServicioEntity extends Servicio {

	@Id
	long id;
		
	
	public ServicioEntity(){
	}
	
	public Long getId() {
	
		return id;
	}

}
