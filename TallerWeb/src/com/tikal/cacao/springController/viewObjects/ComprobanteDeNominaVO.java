package com.tikal.cacao.springController.viewObjects;

import com.tikal.cacao.sat.cfd33.Comprobante;
import com.tikal.cacao.sat.cfd.nomina.NominaElement;

public class ComprobanteDeNominaVO {
	
	private Comprobante comprobante;
	
	private NominaElement nomina;

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public NominaElement getNomina() {
		return nomina;
	}

	public void setNomina(NominaElement nomina) {
		this.nomina = nomina;
	}
	

}
