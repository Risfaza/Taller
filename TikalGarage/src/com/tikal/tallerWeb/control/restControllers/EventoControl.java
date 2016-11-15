package com.tikal.tallerWeb.control.restControllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.tallerWeb.data.access.BitacoraDAO;
import com.tikal.tallerWeb.modelo.entity.EventoEntity;
import com.tikal.tallerWeb.util.JsonConvertidor;

@Controller
@RequestMapping(value={"/eventos"})
public class EventoControl {

	@Autowired
	BitacoraDAO bitacora;
	
	@RequestMapping(value={"/add"}, method= RequestMethod.POST, consumes="application/json")
	public void add(HttpServletRequest request, HttpServletResponse response, @RequestBody String json){
		EventoEntity e= (EventoEntity)JsonConvertidor.fromJson(json, EventoEntity.class);
				
	}
	
}
