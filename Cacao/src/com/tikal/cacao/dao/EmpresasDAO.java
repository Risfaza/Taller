/**
 * 
 */
package com.tikal.cacao.dao;

import com.tikal.cacao.model.Empresa;

/**
 * @author Tikal
 *
 */
public interface EmpresasDAO {
	/**
	 * 
	 * @param e
	 */
	public void crear(Empresa e);
	
	/**
	 * 
	 * @param e
	 */
	public void actualizar(Empresa e);
	
	/**
	 * 
	 * @param rfc
	 */
	public Empresa consultar(String rfc);
	
	/**
	 * 
	 * @param e
	 */
	public void eliminar(Empresa e);

}
