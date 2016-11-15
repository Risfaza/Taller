package com.tikal.tallerWeb.modelo.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import technology.tikal.taller.automotriz.model.auto.Auto;

@Entity
public class AutoEntity extends Auto{
	@Id public Long id;

	public AutoEntity(){}
	
	public AutoEntity(Auto a){
		this.setColor(a.getColor());
		this.setEquipamiento(a.getEquipamiento());
		this.setMarca(a.getMarca());
		this.setModelo(a.getModelo());
		this.setNumeroSerie(a.getNumeroSerie());
		this.setPlacas(a.getPlacas());
		this.setTipo(a.getTipo());
		this.setVersion(a.getVersion());
		
	}
	public Long getId() {
		return id;
	}

}
