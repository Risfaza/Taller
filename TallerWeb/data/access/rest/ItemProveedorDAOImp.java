package com.tikal.tallerWeb.data.access.rest;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Service;
import com.tikal.tallerWeb.data.access.ItemProveedorDAO;
import com.tikal.tallerWeb.modelo.entity.ItemProveedor;
import static com.googlecode.objectify.ObjectifyService.ofy;





public class ItemProveedorDAOImp implements ItemProveedorDAO {

	@Override
	public void save(ItemProveedor ip) {
		// TODO Auto-generated method stub
		  ofy().save().entity(ip).now();
		
	}

	@Override
	public void delete(ItemProveedor ip) {
		// TODO Auto-generated method stub
		 System.out.println("si esta en daoimpl eliminando itemproveedor"+ip);
	        ofy().delete().entity(ip).now();
	        System.out.println("eliminando...");
		
	}

	@Override
	public List<ItemProveedor> getByPresupuesto(Long idPresupuesto) {
		// TODO Auto-generated method stub
		return null;
	}

}
