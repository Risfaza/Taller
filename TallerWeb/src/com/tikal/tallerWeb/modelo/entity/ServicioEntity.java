package com.tikal.tallerWeb.modelo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import technology.tikal.taller.automotriz.model.servicio.Servicio;
import technology.tikal.taller.automotriz.model.servicio.metadata.ServicioMetadata;

@Entity
public class ServicioEntity extends Servicio {

	@Id
	Long idServicio;
	private List<String> proveedores;
	@Index private String asesor; 
	@Index private Date fechaInicio;
	@Index private String aseguradora;
	@Index private String numeroDeSiniestro;
	private Date fechafin;
	
	public Long getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(long idServicio) {
		this.idServicio = idServicio;
	}


	public ServicioEntity(){
		this.proveedores= new ArrayList<String>();
		ServicioMetadata sm= new ServicioMetadata();
		sm.setStatus("Activo");
	}

	public List<String> getProveedores() {
		return proveedores;
	}

	public void setProveedores(List<String> proveedores) {
		this.proveedores = proveedores;
	}

	public String getAsesor() {
		return asesor;
	}

	public void setAsesor(String asesor) {
		this.asesor = asesor;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getAseguradora() {
		return aseguradora;
	}

	public void setAseguradora(String aseguradora) {
		this.aseguradora = aseguradora;
	}

	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
	}

	public String getNumeroDeSiniestro() {
		return numeroDeSiniestro;
	}

	public void setNumeroDeSiniestro(String numeroDeSiniestro) {
		this.numeroDeSiniestro = numeroDeSiniestro;
	}

	public Date getFechafin() {
		return fechafin;
	}

	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}
	
	
}
