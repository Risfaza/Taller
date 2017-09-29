package com.tikal.tallerWeb.data.access.rest;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.tikal.tallerWeb.data.access.FolioDAO;
import com.tikal.tallerWeb.modelo.entity.Folio;


public class FolioDAOImp implements FolioDAO{

	@Override
	public void crear() {
		
		List<Folio> l=ofy().load().type(Folio.class).list();
		if(l.size()==0){
			Folio f = new Folio();
			f.setFolio(1);
			ofy().save().entity(f).now();
		}
	}

	@Override
	public int getFolio() {
		List<Folio> l=ofy().load().type(Folio.class).list();
		if(l.size()<1){
			this.crear();
			return 1;
		}
		return l.get(0).getFolio();
	}

	@Override
	public void incrementa() {
		List<Folio> l=ofy().load().type(Folio.class).list();
		Folio f= l.get(0);
		f.setFolio(f.getFolio()+1);
		ofy().save().entity(f).now();
	}

}
