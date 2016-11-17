/**
 * 
 */
package com.tikal.cacao.model;

/**
 * @author Tikal
 *
 */
public class Deduccion {

	/**
	 * 
	 */
	private TipoDeduccion tipo;

	/**
	 * 
	 */
	private double descuento;

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		if (tipo == null) {
			return "";
		}
		
		return tipo.toString();
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = TipoDeduccion.valueOf(tipo.toUpperCase().replaceAll(" ", "_"));
	}

	/**
	 * @return the descuento
	 */
	public double getDescuento() {
		return descuento;
	}

	/**
	 * @param descuento
	 *            the descuento to set
	 */
	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

}
