package com.tikal.cacao.springController;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value={"/prueba"})
public class PruebaController {
	

	@RequestMapping(value={"/prueba"},method= RequestMethod.GET)
	public void prueba(HttpServletResponse re) throws IOException{
		re.getWriter().println("prueba exitosa");
	}
}
