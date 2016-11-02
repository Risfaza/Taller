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
	private List<Percepciones> percepciones;
	
	/**
	 * 
	 */
	private List<Deducciones> deducciones;
	
	

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
	 * @return the percepciones
	 */
	public List<Percepciones> getPercepciones() {
		return percepciones;
	}

	/**
	 * @param percepciones the percepciones to set
	 */
	public void setPercepciones(List<Percepciones> percepciones) {
		this.percepciones = percepciones;
	}

	/**
	 * @return the deducciones
	 */
	public List<Deducciones> getDeducciones() {
		return deducciones;
	}

	/**
	 * @param deducciones the deducciones to set
	 */
	public void setDeducciones(List<Deducciones> deducciones) {
		this.deducciones = deducciones;
	}
	
	
}
