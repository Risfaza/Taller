package com.tikal.cacao.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class TrabajadorIAS {
	
	@Id
	private String curp;
	
	private String rfc;
	
	@Index
	private String rfcEmpresa;
	
	private String nombre;
	
	private String email;

	public TrabajadorIAS() {}
	
	public TrabajadorIAS(String curp, String rfc, String rfcEmpresa, String nombre, String email) {
		this.curp = curp;
		this.rfc = rfc;
		this.rfcEmpresa = rfcEmpresa;
		this.nombre = nombre;
		this.email= email;
	}
	
	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getRfcEmpresa() {
		return rfcEmpresa;
	}

	public void setRfcEmpresa(String rfcEmpresa) {
		this.rfcEmpresa = rfcEmpresa;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
