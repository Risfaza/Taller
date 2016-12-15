package com.tikal.tallerWeb.modelo.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class CotizacionEntity {
	@Id private Long id;
	@Index private String tipo;
	@Index private int modelo;
	@Index private String proveedor;
	@Index private String concepto;
	String precio;
	String tiempo;
	
	
	
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getModelo() {
		return modelo;
	}
	public void setModelo(int modelo) {
		this.modelo = modelo;
	}
	public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	public String getTiempo() {
		return tiempo;
	}
	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}
	
	public boolean guardable(){
		if(this.getConcepto()==null){
			return false;
		}
		if(this.getPrecio()==null){
			return false;
		}
		if(this.getProveedor()==null){
			return false;
		}
		if(this.getTiempo()==null){
			return false;
		}
		if(this.getTipo()==null){
			return false;
		}
		if(this.getConcepto().length()==0||this.getPrecio().length()==0||this.getProveedor().length()==0||this.getTiempo().length()==0||this.getTipo().length()==0){
			return false;
		}
		return true;
	}
	
}
