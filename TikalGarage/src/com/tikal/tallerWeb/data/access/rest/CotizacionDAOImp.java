package com.tikal.tallerWeb.data.access.rest;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.tikal.tallerWeb.data.access.CotizacionDAO;
import com.tikal.tallerWeb.modelo.entity.CotizacionEntity;

public class CotizacionDAOImp implements CotizacionDAO {

	@Override
	public boolean guarda(CotizacionEntity c) {
		ofy().save().entity(c).now();
		return true;
	}

	@Override
	public boolean guarda(List<CotizacionEntity> lista) {
		ofy().save().entities(lista).now();
		return true;
	}

	@Override
	public List<CotizacionEntity> consultar(String tipo, int modelo) {
		int modeloi = modelo - 2;
		int modelof = modelo + 2;
		List<CotizacionEntity> lista = ofy().load().type(CotizacionEntity.class).filter("tipo", tipo)
				.filter("modelo <=", modelof).filter("modelo >=", modeloi).list();
		if (lista.size() == 0) {
			return new ArrayList<CotizacionEntity>();
		}
		return lista;
	}

}
