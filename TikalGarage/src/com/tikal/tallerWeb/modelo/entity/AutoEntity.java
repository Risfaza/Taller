package com.tikal.tallerWeb.modelo.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import technology.tikal.taller.automotriz.model.auto.Auto;

@Entity
public class AutoEntity extends Auto{
	@Id public Long id;

	public AutoEntity(){}
	
	public Long getId() {
		return id;
	}

}
