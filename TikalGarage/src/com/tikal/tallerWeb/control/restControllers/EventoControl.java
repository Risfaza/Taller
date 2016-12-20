package com.tikal.tallerWeb.control.restControllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.tallerWeb.control.restControllers.VO.BitacoraVO;
import com.tikal.tallerWeb.control.restControllers.VO.EventoVO;
import com.tikal.tallerWeb.data.access.BitacoraDAO;
import com.tikal.tallerWeb.modelo.entity.EventoEntity;
import com.tikal.tallerWeb.util.JsonConvertidor;

@Controller
@RequestMapping(value = { "/eventos" })
public class EventoControl {

	@Autowired
	BitacoraDAO bitacora;

	@RequestMapping(value = {
			"/add" }, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void add(HttpServletRequest request, HttpServletResponse response, @RequestBody String json)
			throws IOException {
		EventoVO vo = (EventoVO) JsonConvertidor.fromJson(json, EventoVO.class);
		EventoEntity e = vo.getEvento();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			e.setFecha(df.parse(vo.getFecha()));
			e.setFechaCreacion(new Date());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		response.getWriter().println(JsonConvertidor.toJson(bitacora.agregar(e.getId(), e)));
	}

	@RequestMapping(value = { "/getBitacora/{id}" }, method = RequestMethod.GET, produces = "application/json")
	public void get(HttpServletRequest request, HttpServletResponse response, @PathVariable String id)
			throws IOException {
		response.getWriter().println(JsonConvertidor.toJson(bitacora.cargar(Long.parseLong(id))));
	}

	@RequestMapping(value = { "/remove/{id}" }, method = RequestMethod.POST)
	public void remove(HttpServletRequest request, HttpServletResponse response, @PathVariable String id)
			throws IOException {
		EventoEntity e = bitacora.cargarEvento(Long.parseLong(id));
		if (e != null) {
			bitacora.borrarEvento(e.getIdEvento());
		}
	}
	
	@RequestMapping(value = { "/update/" }, method = RequestMethod.POST, consumes="application/json")
	public void update(HttpServletRequest request, HttpServletResponse response, @RequestBody String json)
			throws IOException {
		BitacoraVO bit= (BitacoraVO) JsonConvertidor.fromJson(json, BitacoraVO.class);
		bitacora.guardar(Long.parseLong(bit.getId()), bit.getEventos());
		response.getWriter().println(JsonConvertidor.toJson(bit.getEventos()));
	}

	private String findStatus(String tipo){
		if(tipo.compareToIgnoreCase("Entrada de Auto")==0){
			return "Diagnóstico";
		}
		if(tipo.compareToIgnoreCase("Diagnóstico")==0){
			return "Cotización";
		}
		if(tipo.compareToIgnoreCase("Cotización")==0){
			return "Autorización";
		}
		if(tipo.compareToIgnoreCase("Autorización")==0){
			return "Confirmación";
		}
		if(tipo.compareToIgnoreCase("Confirmación")==0){
			return "Surtido";
		}
		if(tipo.compareToIgnoreCase("Surtido")==0){
			return "Reparación";
		}
		
		return tipo;
	}
}
