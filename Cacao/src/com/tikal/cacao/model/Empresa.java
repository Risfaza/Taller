/**
 * 
 */
package com.tikal.cacao.model;

import java.util.List;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

/**
 * @author Tikal
 *
 */
@Entity
public class Empresa {
	/**
	 * 
	 */
	@Id
	private String RFC; // key
	
	/**
	 * 
	 */
	private String nombre;
	
	/**
	 * 
	 */
	private String razonSocial;
	
	/**
	 * 
	 */
	private Direccion direccion;
	
	/**
	 * 
	 */
	@Load private List<Ref<Regimen>> regimenes;

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
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
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
	 * @return the regimenes
	 */
	public List<Ref<Regimen>> getRegimenes() {
		return regimenes;
	}

	/**
	 * @param regimenes the regimenes to set
	 */
	public void setRegimenes(List<Ref<Regimen>> regimenes) {
		this.regimenes = regimenes;
	}
	
	
	
	
}
