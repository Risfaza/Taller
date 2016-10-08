package com.tikal.tallerWeb.modelo.entity;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import technology.tikal.taller.automotriz.model.servicio.costo.RegistroCosto;

@Entity
public class PresupuestoEntity extends RegistroCosto {
	
	@Id public Long id;
	
	public PresupuestoEntity(){}

	public Long getId() {
		return id;
	}

}
