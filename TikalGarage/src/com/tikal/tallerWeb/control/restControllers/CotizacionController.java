package com.tikal.tallerWeb.control.restControllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.tallerWeb.control.restControllers.VO.CotizacionVO;
import com.tikal.tallerWeb.data.access.CotizacionDAO;
import com.tikal.tallerWeb.modelo.entity.CotizacionEntity;
import com.tikal.tallerWeb.util.JsonConvertidor;

@Controller
@RequestMapping(value={"/cotizacion"})
public class CotizacionController {

	@Autowired 
	CotizacionDAO cotizaciondao;
	
	@RequestMapping(value={"/get"}, method = RequestMethod.GET, produces = "Application/Json")
	public void get(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, IOException{
		String tipo = request.getParameter("tipo");
		String anio= request.getParameter("modelo");
		System.out.println("Tipo "+ tipo+" Modelo "+anio);
		response.getWriter().println(JsonConvertidor.toJson(cotizaciondao.consultar(tipo, Integer.parseInt(anio))));
	}
	
	@RequestMapping(value={"/save"}, method = RequestMethod.POST, consumes = "Application/Json")
	public void save(HttpServletRequest request, HttpServletResponse response, @RequestBody String json) throws NumberFormatException, IOException{
		CotizacionVO lista= (CotizacionVO) JsonConvertidor.fromJson(json, CotizacionVO.class);
		List<CotizacionEntity> guardar= new ArrayList<CotizacionEntity>();
		for(CotizacionEntity o:lista.getListcotizaciones()){
			if(o.getId()==null){
				o.setModelo(Integer.parseInt(lista.getModelo()));
				o.setTipo(lista.getTipo());
			}
			if(o.guardable()){
				guardar.add(o);
			}
		}
		cotizaciondao.guarda(guardar);
		
	}
	
}
