/**
 * 
 */
package com.tikal.cacao.dao;

import java.util.List;

import com.tikal.cacao.model.Empleado;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.Regimen;

/**
 * @author Tikal
 *
 */
public interface EmpleadosDAO {
	
	/**
	 * 
	 * @param empleado
	 */
	public void crear(Empleado empleado);
	
	/**
	 * 
	 * @param empleado
	 */
	public void actualizar(Empleado empleado);
	
	/**
	 * 
	 * @param numEmpleado
	 * @return
	 */
	public Empleado consultar(long numEmpleado);
	
	/**
	 * 
	 * @param empleado
	 */
	public void eliminar(Empleado empleado);
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	public List<Empleado> consultaPorEmpresa(Empresa e);
	
	/**
	 * 
	 * @param r
	 * @return
	 */
	public List<Empleado> consultaPorRegimen(Regimen r);

}
