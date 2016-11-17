/**
 * 
 */
package com.tikal.cacao.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author Tikal
 *
 */
@Entity
public class Empleado {

	/**
	 * 
	 */
	@Id
	private Long numEmpleado; // key
	
	/**
	 * 
	 */
	private String RFC;
	
	/**
	 * 
	 */
	private NombrePersona nombre;
	
	/**
	 * 
	 */
	private String curp;
	
	/**
	 * 
	 */
	private Direccion direccion;
	
	/**
	 * 
	 */
	private String telefono;
	
	/**
	 * 
	 */
	private String email;
	
	/**
	 * 
	 */
	private String numSeguroSocial;
	
	/**
	 * 
	 */
	private boolean activo = true;
	

	/**
	 * @return the numEmpleado
	 */
	public Long getNumEmpleado() {
		return numEmpleado;
	}

	/**
	 * @param numEmpleado the numEmpleado to set
	 */
	public void setNumEmpleado(Long numEmpleado) {
		this.numEmpleado = numEmpleado;
	}

	
	
	/**
	 * @return the rFC
	 */
	public String getRFC() {
		return RFC;
	}

	/**
	 * @param rFC the rFC to set
	 */
	public void setRFC(String rFC) {
		RFC = rFC;
	}

	/**
	 * @return the nombre
	 */
	public NombrePersona getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(NombrePersona nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the curp
	 */
	public String getCurp() {
		return curp;
	}

	/**
	 * @param curp the curp to set
	 */
	public void setCurp(String curp) {
		this.curp = curp;
	}

	/**
	 * @return the direccion
	 */
	public Direccion getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the numSeguroSocial
	 */
	public String getNumSeguroSocial() {
		return numSeguroSocial;
	}

	/**
	 * @param numSeguroSocial the numSeguroSocial to set
	 */
	public void setNumSeguroSocial(String numSeguroSocial) {
		this.numSeguroSocial = numSeguroSocial;
	}

	/**
	 * @return the estaActivo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param estaActivo the estaActivo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
	
}