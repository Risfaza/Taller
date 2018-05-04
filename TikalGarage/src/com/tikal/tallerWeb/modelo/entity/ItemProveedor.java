package com.tikal.tallerWeb.modelo.entity;

import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class ItemProveedor {

	@Id private Long id;
	private Long idPresupuesto;
	private List<ProveedorPrecio> provedores;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdPresupuesto() {
		return idPresupuesto;
	}
	public void setIdPresupuesto(Long idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}
	public List<ProveedorPrecio> getProvedores() {
		return provedores;
	}
	public void setProvedores(List<ProveedorPrecio> provedores) {
		this.provedores = provedores;
	}
	
	
}
