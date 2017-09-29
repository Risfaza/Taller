package com.tikal.tallerWeb.data.access.rest;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Repository;

import com.tikal.cacao.factura.Estatus;
import com.tikal.tallerWeb.data.access.FacturaDAO;
import com.tikal.tallerWeb.modelo.entity.Factura;

/**
 * 
 * @author Tikal
 */
@Repository

public class FacturaDAOImpl implements FacturaDAO{

	@Override
	public void guardar(Factura f) {
		ofy().save().entity(f).now();
		
	}

	@Override
	public Factura consultar(String uuid) {
		return ofy().load().type(Factura.class).id(uuid).now();
	}

	@Override
	public List<Factura> consutarTodas(String rfcEmisor) {
		List<Factura> facturas = ofy().load().type(Factura.class).filter("rfcEmisor", rfcEmisor).order("- fechaCertificacion").list();
		if (facturas == null)
			return new ArrayList<>();
		else
			return facturas;
	}

	@Override
	public boolean eliminar(Factura f) {
		if (f.getEstatus().equals(Estatus.GENERADO)) {
			ofy().delete().entity(f).now();
			return true;
		}
		return false;
	}

	@Override
	public void eliminar(String uuid) {
		ofy().delete().type(Factura.class).id(uuid).now();
	}

	@Override
	public List<Factura> buscar(Date fi, Date ff, String rfc) {
		return ofy().load().type(Factura.class).filter("rfcEmisor",rfc).filter("fechaCertificacion >=",fi).filter("fechaCertificacion <=",ff).list();
	}

	@Override
	public List<Factura> consutarTodas(String rfcEmisor, int page) {
		List<Factura> facturas = ofy().load().type(Factura.class).filter("rfcEmisor", rfcEmisor).order("- fechaCertificacion").offset(25*(page-1)).limit(25).list();
		if (facturas == null){
			return new ArrayList<>();
		}
		else{
			return facturas;
		}
	}

	@Override
	public int getPaginas(String rfc) {
		return ((ofy().load().type(Factura.class).filter("rfcEmisor",rfc).count()-1)/25)+1;
	}

	@Override
	public List<Factura> buscarIds(String[] ids) {
		
		Map<String, Factura> mapa = ofy().load().type(Factura.class).ids(ids);
		List<Factura> lista = new ArrayList<Factura>();
		Iterator it = mapa.entrySet().iterator();

	    for (Entry<String, Factura> e: mapa.entrySet()) {
	        lista.add(e.getValue());
	    }

		return lista;
	}
	
	

}
