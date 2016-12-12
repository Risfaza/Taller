package com.tikal.tallerWeb.control.restControllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.tallerWeb.data.access.rest.UsuarioDAOImp;
import com.tikal.tallerWeb.modelo.usuario.Usuario;
import com.tikal.tallerWeb.util.JsonConvertidor;


@Controller
@RequestMapping(value={" /usuario "})
public class UsariosControl {
	
	@Autowired
	UsuarioDAOImp usuarioImp;
	
	@RequestMapping(value={"/registro"}, method = RequestMethod.POST, consumes = "Application/Json")
	public void crearUsuario(HttpServletRequest request, HttpServletResponse response, @RequestBody String json){
		Usuario usuario = (Usuario) JsonConvertidor.fromJson(json, Usuario.class);
		usuarioImp.crearUsuario(usuario);
		
	}

}
