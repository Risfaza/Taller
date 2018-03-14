package com.tikal.cacao.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * @author Tikal
 *
 */
@Entity
public class FacturaRenglon {

	@Id
	private Long id;
	
	@Index
	private String rfcEmisor;
	
	private String rfcReceptor;
	
	private String cantidad;
	
	private String unidad;
	
	private String clave;
	
	private String descripcion;
	
	private String valorUnitario;
	
	private String ivaIncluido;
	
	private String serie;
	
	private String tipoComprobante;
	
	private String formaPago;
	
	private String metodoPago;
	
	private String regimen;
	
	private String cveUnidad;
	
	private String cveProdServ;
	
	private String usoCFDI;

	private String cuenta;
	
	private String error;
	
	@Index
	private boolean failed;
	
	@Index
	private int pos;
	
	@Index
	private boolean procesada;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRfcEmisor() {
		return rfcEmisor;
	}

	public void setRfcEmisor(String rfcEmisor) {
		this.rfcEmisor = rfcEmisor;
	}

	public String getRfcReceptor() {
		return rfcReceptor;
	}

	public void setRfcReceptor(String rfcReceptor) {
		this.rfcReceptor = rfcReceptor;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(String valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public String getIvaIncluido() {
		return ivaIncluido;
	}

	public void setIvaIncluido(String ivaIncluido) {
		this.ivaIncluido = ivaIncluido;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public String getRegimen() {
		return regimen;
	}

	public void setRegimen(String regimen) {
		this.regimen = regimen;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public boolean isProcesada() {
		return procesada;
	}

	public void setProcesada(boolean procesada) {
		this.procesada = procesada;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public String getCveUnidad() {
		return cveUnidad;
	}

	public void setCveUnidad(String cveUnidad) {
		this.cveUnidad = cveUnidad;
	}

	public String getCveProdServ() {
		return cveProdServ;
	}

	public void setCveProdServ(String cveProdServ) {
		this.cveProdServ = cveProdServ;
	}

	public String getUsoCFDI() {
		return usoCFDI;
	}

	public void setUsoCFDI(String usoCFDI) {
		this.usoCFDI = usoCFDI;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	
	public boolean valido(){
		boolean valido=this.validaCampo(cantidad);
		valido=valido&&this.validaCampo(clave);
		valido=valido&&this.validaCampo(this.cveProdServ);
		valido=valido&&this.validaCampo(this.cveUnidad);
		valido=valido&&this.validaCampo(this.descripcion);
		valido=valido&&this.validaCampo(this.formaPago);
		valido=valido&&this.validaCampo(this.ivaIncluido);
		valido=valido&&this.validaCampo(this.metodoPago);
		valido=valido&&this.validaCampo(this.regimen);
		valido=valido&&this.validaCampo(this.rfcReceptor);
		valido=valido&&this.validaCampo(this.serie);
		valido=valido&&this.validaCampo(this.tipoComprobante);
		valido=valido&&this.validaCampo(this.unidad);
		valido=valido&&this.validaCampo(this.usoCFDI);
		valido=valido&&this.validaCampo(this.valorUnitario);
		return valido;
	}
	
	private boolean validaCampo(String campo){
		if(campo ==null){
			return false;
		}
		if(campo.length()==0){
			return false;
		}
		return true;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean isFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	}
	
}