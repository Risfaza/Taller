package com.tikal.cacao.springController.viewObjects;

public class DatosNominaVO {
	
	private String lugarDeExpedicion;
	
	private String rfcEmisor;
	
	private String nombreEmisor;

	public DatosNominaVO(String lugarDeExpedicion, String rfcEmisor, String nombreEmisor) {
		super();
		this.lugarDeExpedicion = lugarDeExpedicion;
		this.rfcEmisor = rfcEmisor;
		this.nombreEmisor = nombreEmisor;
	}

	public String getLugarDeExpedicion() {
		return lugarDeExpedicion;
	}

	public void setLugarDeExpedicion(String lugarDeExpedicion) {
		this.lugarDeExpedicion = lugarDeExpedicion;
	}

	public String getRfcEmisor() {
		return rfcEmisor;
	}

	public void setRfcEmisor(String rfcEmisor) {
		this.rfcEmisor = rfcEmisor;
	}

	public String getNombreEmisor() {
		return nombreEmisor;
	}

	public void setNombreEmisor(String nombreEmisor) {
		this.nombreEmisor = nombreEmisor;
	}

}
