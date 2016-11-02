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
public class Usuario {

	/**
	 * 
	 */
	@Id
	private String id; // key
	
	
	/**
	 * 
	 */
	private NombrePersona nombre;
	
	
	/**
	 * 
	 */
	private String contrasena;


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the contrasena
	 */
	public String getContrasena() {
		return contrasena;
	}


	/**
	 * @param contrasena the contrasena to set
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	
	
}
