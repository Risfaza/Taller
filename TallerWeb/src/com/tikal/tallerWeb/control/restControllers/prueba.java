package com.tikal.tallerWeb.control.restControllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value="/prueba")
public class prueba {

		
	@RequestMapping(value={"/prueba"},method = RequestMethod.GET)
	
	public prueba(HttpServletResponse response, HttpServletRequest request) throws IOException {
		   response.getWriter().println("Prueba del mètodo Perfil");

	    }
}
