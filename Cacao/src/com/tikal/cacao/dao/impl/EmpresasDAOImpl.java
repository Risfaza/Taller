/**
 * 
 */
package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.tikal.cacao.dao.EmpresasDAO;
import com.tikal.cacao.model.Empresa;

/**
 * @author Tikal
 *
 */
public class EmpresasDAOImpl implements EmpresasDAO {

	/* (non-Javadoc)
	 * @see com.tikal.cacao.dao.EmpresasDAO#crear(com.tikal.cacao.model.Empresa)
	 */
	public EmpresasDAOImpl(){
		
	}
	
	@Override
	public void crear(Empresa e) {
		ofy().save().entity(e);

	}

	/* (non-Javadoc)
	 * @see com.tikal.cacao.dao.EmpresasDAO#actualizar(com.tikal.cacao.model.Empresa)
	 */
	@Override
	public void actualizar(Empresa e) {
		ofy().save().entity(e); 

	}

	/* (non-Javadoc)
	 * @see com.tikal.cacao.dao.EmpresasDAO#consultar(java.lang.String)
	 */
	@Override
	public Empresa consultar(String rfc) {
		return ofy().load().type(Empresa.class).id(rfc).now();
	}

	/* (non-Javadoc)
	 * @see com.tikal.cacao.dao.EmpresasDAO#eliminar(com.tikal.cacao.model.Empresa)
	 */
	@Override
	public void eliminar(Empresa e) {
		ofy().delete().entity(e);

	}

}
