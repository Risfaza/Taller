package com.tikal.tallerWeb.control;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.tallerWeb.control.restControllers.VO.DatosServicioVO;
import com.tikal.tallerWeb.data.access.ItemProveedorDAO;
import com.tikal.tallerWeb.data.access.ServicioDAO;
import com.tikal.tallerWeb.util.AsignadorDeCharset;
import com.tikal.tallerWeb.util.JsonConvertidor;

@Controller
@RequestMapping(value = "/item")
public class ItemController {
	
	
	@Autowired
	ServicioDAO servdao;
	
	@Autowired
	ItemProveedorDAO itemProveedorDao;
	
	 @RequestMapping(value={"/prueba"},method = RequestMethod.GET)	    
	    public void prueba(HttpServletResponse response, HttpServletRequest request) throws IOException {
	 	   response.getWriter().println("Prueba del mètodo Session");

	    }
	 
	 @RequestMapping(value = {"/add" }, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	 public void add(HttpServletRequest request, HttpServletResponse response, @RequestBody String json)
		throws IOException {
		 System.out.println("objeto de front para item"+json);
		 AsignadorDeCharset.asignar(request, response);
		 DatosServicioVO datos = (DatosServicioVO) JsonConvertidor.fromJson(json, DatosServicioVO.class);
		// datos.g
	 }
}