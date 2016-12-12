package com.tikal.tallerWeb.control.restControllers;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.tallerWeb.data.access.rest.UsuarioDAOImp;
import com.tikal.tallerWeb.util.JsonConvertidor;

@Controller
public class ServicioSesion {
	
	@Autowired
	UsuarioDAOImp usuariodao;
	
	@RequestMapping(value={"/user"},method=RequestMethod.GET,produces="application/json")
	  public void user(HttpServletResponse res,Principal u) throws IOException {
		
		res.getWriter().println(JsonConvertidor.toJson(u));
	}

}
