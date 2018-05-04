package com.tikal.tallerWeb.data.access.rest.local;

import java.util.List;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.tallerWeb.data.access.rest.ClienteDAOImp;
import com.tikal.tallerWeb.modelo.entity.ClienteEntity;

public class ClienteDAOImpLocal extends ClienteDAOImp{
	@Override
	public ClienteEntity buscarCliente(String nombre) {
		List<ClienteEntity> lista=ObjectifyService.ofy().load().type(ClienteEntity.class).list();
		for(ClienteEntity cliente:lista){
			if(cliente.getNombre().toUpperCase().contains(nombre.toUpperCase())){
				return cliente;
			}
		}
		return null;
	}
}
