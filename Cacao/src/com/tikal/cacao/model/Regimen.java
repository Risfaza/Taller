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
	private Long id; // key
	
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
	 * 
	 */
	private boolean activo = true;
	
	/**
	 * 
	 */
	private RegimenContratacion tipoRegimen;
	

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
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

	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the tipoRegimen
	 */
	public String getTipoRegimen() {
		if(this.tipoRegimen==null){
			return "";
		}
		return tipoRegimen.toString();
	}

	/**
	 * @param tipoRegimen the tipoRegimen to set
	 */
	public void setTipoRegimen(String tipoRegimen) {
		this.tipoRegimen =RegimenContratacion.valueOf(tipoRegimen.toUpperCase().replaceAll(" ","_"));
	}
	
	
	
}
