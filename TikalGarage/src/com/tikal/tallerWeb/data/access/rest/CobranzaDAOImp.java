package com.tikal.tallerWeb.data.access.rest;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.tikal.tallerWeb.data.access.CobranzaDAO;
import com.tikal.tallerWeb.modelo.entity.ServicioEntity;

import technology.tikal.taller.automotriz.model.cobranza.PagoCobranza;

public class CobranzaDAOImp implements CobranzaDAO{

	@Override
	public void addPago(PagoCobranza pago, Long id) {
		// 
		ServicioEntity s=ofy().load().type(ServicioEntity.class).id(id).now();
		s.getCobranza().getPagos().add(pago);
		ofy().save().entity(s);
	}

}
