/**
 * 
 */
package com.tikal.cacao.model;

/**
 * @author Tikal
 *
 */
public class Percepcion {
	
	/**
	 * 
	 */
	private TipoPercepcion tipo;
	
	/**
	 * 
	 */
	private double cantidad;

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		if(tipo==null){
			return "";
		}
		return tipo.toString();
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = TipoPercepcion.valueOf(tipo.toUpperCase().replaceAll(" ", "_"));
	}

	/**
	 * @return the cantidad
	 */
	public double getCantidad() {	
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	
}
