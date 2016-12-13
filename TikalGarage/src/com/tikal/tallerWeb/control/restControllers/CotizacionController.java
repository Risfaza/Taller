package com.tikal.tallerWeb.control.restControllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.tallerWeb.data.access.CotizacionDAO;
import com.tikal.tallerWeb.modelo.usuario.Usuario;
import com.tikal.tallerWeb.util.JsonConvertidor;

@Controller
@RequestMapping(value={"/cotizacion"})
public class CotizacionController {

	@Autowired 
	CotizacionDAO cotizaciondao;
	
	@RequestMapping(value={"/get"}, method = RequestMethod.POST, produces = "Application/Json")
	public void get(HttpServletRequest request, HttpServletResponse response){
		
	}
	
}
