/**
 * 
 */
package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tikal.cacao.dao.EmpleadosDAO;
import com.tikal.cacao.model.Empleado;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.Regimen;

/**
 * @author Tikal
 *
 */
@Repository
public class EmpleadosDAOImpl implements EmpleadosDAO {

	/* (non-Javadoc)
	 * @see com.tikal.cacao.dao.EmpleadosDAO#crear(com.tikal.cacao.model.Empleado)
	 */
	@Override
	public void crear(Empleado empleado) {
		ofy().save().entities(empleado);
	}

	/* (non-Javadoc)
	 * @see com.tikal.cacao.dao.EmpleadosDAO#actualizar(com.tikal.cacao.model.Empleado)
	 */
	@Override
	public void actualizar(Empleado empleado) {
		ofy().save().entities(empleado);

	}

	/* (non-Javadoc)
	 * @see com.tikal.cacao.dao.EmpleadosDAO#consultar(long)
	 */
	@Override
	public Empleado consultar(long numEmpleado) {
		return ofy().load().type(Empleado.class).id(numEmpleado).now();
	}

	/* (non-Javadoc)
	 * @see com.tikal.cacao.dao.EmpleadosDAO#eliminar(com.tikal.cacao.model.Empleado)
	 */
	@Override
	public void eliminar(Empleado empleado) {
		ofy().delete().entity(empleado);

	}

	@Override
	public List<Empleado> consultaPorEmpresa(Empresa e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Empleado> consultaPorRegimen(Regimen r) {
		// TODO Auto-generated method stub
		return null;
	}

}
