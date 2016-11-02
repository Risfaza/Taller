/**
 * 
 */
package com.tikal.cacao.model;

import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author Tikal
 *
 */
@Entity
public class Regimen {
	
	/**
	 * 
	 */
	@Id
	private long id; // key
	
	/**
	 * 
	 */
	private String nombre;

	/**
	 * 
	 */
	private List<Percepcion> percepciones;
	
	/**
	 * 
	 */
	private List<Deduccion> deducciones;
	
	/**
	 * 
	 */
	private List<Long> idEmpleados;
	

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
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
	 * @return the percepciones
	 */
	public List<Percepcion> getPercepciones() {
		return percepciones;
	}

	/**
	 * @param percepciones the percepciones to set
	 */
	public void setPercepciones(List<Percepcion> percepciones) {
		this.percepciones = percepciones;
	}

	/**
	 * @return the deducciones
	 */
	public List<Deduccion> getDeducciones() {
		return deducciones;
	}

	/**
	 * @param deducciones the deducciones to set
	 */
	public void setDeducciones(List<Deduccion> deducciones) {
		this.deducciones = deducciones;
	}

	/**
	 * @return the idEmpleados
	 */
	public List<Long> getIdEmpleados() {
		return idEmpleados;
	}

	/**
	 * @param idEmpleados the idEmpleados to set
	 */
	public void setIdEmpleados(List<Long> idEmpleados) {
		this.idEmpleados = idEmpleados;
	}
	
	
}
