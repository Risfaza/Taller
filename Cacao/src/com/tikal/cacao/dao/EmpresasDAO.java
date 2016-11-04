/**
 * 
 */
package com.tikal.cacao.dao;

import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.Regimen;

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
	
	/**
	 * Applies a <code>Regimen</code> into the <code>Empresa</code> 
	 * @param r The <code>Regimen</code>  to be applied
	 * @param e The <code>Empresa</code> in which the <code>Regimen</code> will be applied
	 */
	public void aplicarUnRegimen(Regimen r, Empresa e);

}
