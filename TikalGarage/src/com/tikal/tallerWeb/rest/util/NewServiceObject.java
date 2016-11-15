package com.tikal.tallerWeb.rest.util;

import technology.tikal.taller.automotriz.model.auto.Auto;
import technology.tikal.taller.automotriz.model.cliente.Cliente;
import technology.tikal.taller.automotriz.model.servicio.Servicio;

public class NewServiceObject {
	public Auto auto;
	public Cliente cliente;
	public Servicio servicio;
	
	public NewServiceObject(){
		
	}
	
	public Auto getAuto() {
		return auto;
	}
	public void setAuto(Auto auto) {
		this.auto = auto;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Servicio getServicio() {
		return servicio;
	}
	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
	
	
}
