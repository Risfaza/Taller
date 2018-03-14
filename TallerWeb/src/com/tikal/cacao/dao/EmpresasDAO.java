/**
 * 
 */
package com.tikal.cacao.dao;

import java.util.List;

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
	
	public String[] consultarEmails(String rfc);
	
	/**
	 * 
	 * 
	 * @param e
	 */
	public void eliminar(Empresa e);
	
	public List<Empresa> consultarTodos();
	/**
	 * Applies a <code>Regimen</code> into the <code>Empresa</code> 
	 * @param r The <code>Regimen</code>  to be applied
	 * @param e The <code>Empresa</code> in which the <code>Regimen</code> will be applied
	 */
	public void aplicarUnRegimen(Regimen r, Empresa e);
	
	/**
	 * <p>Este m&eacute;todo esta a  prueba y es temporal.</p>
	 * <p>Cifra los datos de una {@code Empresa} antes de que se almacenen en el
	 * mecanismo de persistencia.</p>
	 */
	public void cifrar(Empresa e);
	
	/**
	 * <p>Este m&eacute;todo esta a  prueba y es temporal.</p>
	 * <p>Descifra los datos de una {@code Empresa} antes de mostrarlos en la capa de presentación.</p>
	 */
	public Empresa descifrar(String rfc);
	
}
