package com.tikal.tallerWeb.control.restControllers.VO;

import java.util.Date;

import com.tikal.tallerWeb.modelo.entity.AutoEntity;
import com.tikal.tallerWeb.modelo.entity.ClienteEntity;

import technology.tikal.taller.automotriz.model.cobranza.PagoCobranza;
import technology.tikal.taller.automotriz.model.index.servicio.ServicioIndex;
import technology.tikal.taller.automotriz.model.servicio.moneda.Moneda;

public class ServicioListVO {
	private Long id;
	private String status;
	private String nombreCliente;
	private String placas;
	private String fechaInicio;
	private String descripcion;
	private String cobranza;
	private String dias;
	private String saldo;
	private String coche;

	public ServicioListVO() {

	}

	public ServicioListVO(ServicioIndex si, AutoEntity a, ClienteEntity c) {
		this.setStatus(si.getStatus());
		this.setDescripcion(si.getDescripcion());
		this.id=si.getId();
		Date fi = si.getFechaInicio();
		Date hoy = new Date();
//		hoy = new Date(hoy.compareTo(fi));
		this.setDias(this.calcularDias(hoy,fi)+"");
		this.setFechaInicio(fi.getDate() + "-" + (fi.getMonth()+1)+"-" + (1900+fi.getYear()));
		if(c!=null){
			this.setNombreCliente(c.getNombre());
		}
		this.setPlacas(a.getPlacas());
		Moneda ct = si.getCostoTotal();
		if (ct != null) {
			float saldo= Float.parseFloat(ct.getValue());
			for(PagoCobranza pago:si.getCobranza().getPagos()){
				saldo= saldo - Float.parseFloat(pago.getMonto().getValue());
			}
			this.setSaldo(saldo+"");
		}
		this.setStatus(si.getStatus());
		this.setCoche(a.getTipo()+" "+a.getColor());
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCoche() {
		return coche;
	}

	public void setCoche(String coche) {
		this.coche = coche;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getPlacas() {
		return placas;
	}

	public void setPlacas(String placas) {
		this.placas = placas;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCobranza() {
		return cobranza;
	}

	public void setCobranza(String cobranza) {
		this.cobranza = cobranza;
	}

	public String getDias() {
		return dias;
	}

	public void setDias(String dias) {
		this.dias = dias;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}
	
	private int calcularDias(Date hoy, Date fecha){
		final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día 
		Long diferencia = ( hoy.getTime() - fecha.getTime() )/MILLSECS_PER_DAY; 
		return diferencia.intValue();
	}
	

}
