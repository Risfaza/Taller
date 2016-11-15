package com.tikal.tallerWeb.control.restControllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.tallerWeb.data.access.AutoDAO;
import com.tikal.tallerWeb.data.access.ClienteDAO;
import com.tikal.tallerWeb.data.access.ServicioDAO;
import com.tikal.tallerWeb.modelo.entity.AutoEntity;
import com.tikal.tallerWeb.modelo.entity.EventoEntity;
import com.tikal.tallerWeb.modelo.entity.ServicioEntity;
import com.tikal.tallerWeb.rest.util.NewServiceObject;
import com.tikal.tallerWeb.util.JsonConvertidor;

@Controller
@RequestMapping(value={"/servicio"})
public class ServicioControl {

	@Autowired
	ServicioDAO servdao;
	@Autowired
	AutoDAO autodao;
	@Autowired
	ClienteDAO clientedao;
	
	
	@RequestMapping(value = { "/add" }, method = RequestMethod.POST,consumes="application/json",produces="application/json")
	public void add(HttpServletRequest request, HttpServletResponse response,@RequestBody String json) throws IOException {
		NewServiceObject s = (NewServiceObject)	JsonConvertidor.fromJson(json, NewServiceObject.class);
		
		AutoEntity a= new AutoEntity();
		
		List<AutoEntity> ae=ObjectifyService.ofy().load().type(AutoEntity.class).filter("numeroSerie",s.getAuto().getNumeroSerie()).list();
		if(ae.size()==0){
			a=  new AutoEntity(s.getAuto());
			ObjectifyService.ofy().save().entities(a).now();
		}
		System.out.println(json);
		response.getWriter().write(JsonConvertidor.toJson(a));
	}
	
	@RequestMapping(value = { "/update" }, method = RequestMethod.POST,consumes="application/json",produces="application/json")
	public void update(HttpServletRequest request, HttpServletResponse response,@RequestBody String json) throws IOException {
		NewServiceObject s = (NewServiceObject)	JsonConvertidor.fromJson(json, NewServiceObject.class);
		
		AutoEntity a= new AutoEntity();
		ServicioEntity ser = new ServicioEntity();
		EventoEntity ev= new EventoEntity();

		technology.tikal.taller.automotriz.model.servicio.Servicio serv= new technology.tikal.taller.automotriz.model.servicio.Servicio();
		technology.tikal.taller.automotriz.model.index.servicio.ServicioIndexAutoData si= new technology.tikal.taller.automotriz.model.index.servicio.ServicioIndexAutoData();
		
		List<AutoEntity> ae=ObjectifyService.ofy().load().type(AutoEntity.class).filter("numeroSerie",s.getAuto().getNumeroSerie()).list();
		if(ae.size()==0){
			a=  new AutoEntity(s.getAuto());
			ObjectifyService.ofy().save().entities(a).now();
		}
		System.out.println(json);
		response.getWriter().write(JsonConvertidor.toJson(a));
	}
	
}
