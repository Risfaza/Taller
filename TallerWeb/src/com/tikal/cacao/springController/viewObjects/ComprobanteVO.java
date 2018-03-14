package com.tikal.cacao.springController.viewObjects;

import com.tikal.cacao.sat.cfd.Comprobante;

public class ComprobanteVO {
	
	private String email;
	
	private Comprobante comprobante;

	/**
	 * @return the emails
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param emails the emails to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the comprobante
	 */
	public Comprobante getComprobante() {
		return comprobante;
	}

	/**
	 * @param comprobante the comprobante to set
	 */
	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}
	
	
}