package com.tikal.tallerWeb.data.access;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tikal.tallerWeb.modelo.entity.ItemProveedor;


@Service
public interface ItemProveedorDAO {
	
	  void save (ItemProveedor ip);
	  
	  void delete (ItemProveedor ip);
	  
	  List<ItemProveedor> getByPresupuesto (Long idPresupuesto);

}
