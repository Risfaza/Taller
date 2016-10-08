package com.tikal.tallerWeb.modelo.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import technology.tikal.taller.automotriz.model.servicio.bitacora.Evento;

@Entity
public class EventoEntity extends Evento{
	
	@Id public Long id;
	
	public EventoEntity(){}

	public Long getId() {
		return id;
	}

}
