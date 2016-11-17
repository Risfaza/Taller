package com.tikal.cacao.springController.viewObjects;

import com.tikal.cacao.model.Empleado;

public class EmpleadoVO {
	private Empleado empleado;
	private String idEmpresa;
	public Empleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	public String getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	
	

}
