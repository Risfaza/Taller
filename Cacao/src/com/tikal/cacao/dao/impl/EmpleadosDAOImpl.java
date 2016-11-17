/**
 * 
 */
package com.tikal.cacao.dao.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.tikal.cacao.dao.EmpleadosDAO;
import com.tikal.cacao.dao.EmpresasDAO;
import com.tikal.cacao.dao.RegimenesDAO;
import com.tikal.cacao.model.Empleado;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.Regimen;

/**
 * @author Tikal
 *
 */
@Repository
public class EmpleadosDAOImpl implements EmpleadosDAO {
	
	@Autowired
	@Qualifier("regimenesdao")
	private RegimenesDAO regDAO;
	
	@Autowired
	@Qualifier(value="empresasdao")
	private EmpresasDAO empresasDAO;

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
		empleado.setActivo(false);
		actualizar(empleado);

	}

	@Override
	public List<Empleado> consultaPorEmpresa(String rfc) {
		Empresa empresa = empresasDAO.consultar(rfc);
		List<Regimen> regimenes = empresa.getRegimenes();
		List<Empleado> empleados = new ArrayList<Empleado>();
		for (Regimen regimen : regimenes) {
			empleados.addAll(consultaPorRegimen(regimen.getId()));
		}
		return empleados;
	}

	@Override
	public List<Empleado> consultaPorRegimen(Long id) {
		Regimen regimen = regDAO.consultar(id);
		Collection<Empleado> colecionEmpl = ofy().load().type(Empleado.class).ids(regimen.getIdEmpleados()).values();   
		return new ArrayList<Empleado>(colecionEmpl);
	}

}
