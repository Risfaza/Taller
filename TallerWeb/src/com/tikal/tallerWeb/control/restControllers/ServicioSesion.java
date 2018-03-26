package com.tikal.tallerWeb.control.restControllers;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.tallerWeb.data.access.rest.UsuarioDAOImp;
import com.tikal.tallerWeb.modelo.usuario.Usuario;
import com.tikal.tallerWeb.util.JsonConvertidor;

@Controller
public class ServicioSesion {
	
	@Autowired
	UsuarioDAOImp usuariodao;
	
	@RequestMapping(value={"/user"},method=RequestMethod.GET,produces="application/json")
	 //public void user(HttpServletResponse res, HttpServletRequest req,Principal u) throws IOException {
	 public void user(HttpServletResponse res, HttpServletRequest req) throws IOException {
		System.out.println("aquiiiiiiiiiiiiiii");
		
		//usuariodao.crearUsuario(user);
		//req.getSession().setAttribute("userName","root");
		///System.out.println("session"+req.getSession().getAttribute("username"));
		//res.getWriter().println(JsonConvertidor.toJson(u));
		res.getWriter().println("ok");
	}

	//currentSession
	
	@RequestMapping(value={"/currentSession"},method=RequestMethod.GET,produces="application/json")
	  public void currentUser(HttpServletResponse res, HttpServletRequest req) throws IOException {
		HttpSession s= req.getSession();
		String n= (String) s.getAttribute("userName");
		/*if(n==null){
			res.sendError(400);
		}*/
	}
	
	@RequestMapping(value={"/cerrarSession"},method=RequestMethod.GET,produces="application/json")
	  public void close(HttpServletResponse res, HttpServletRequest req) throws IOException {
		req.getSession().invalidate();
	}
}
