package com.tikal.tallerWeb.control.restControllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.tallerWeb.control.restControllers.VO.BusquedaVO;
import com.tikal.tallerWeb.data.access.AutoDAO;
import com.tikal.tallerWeb.data.access.ClienteDAO;
import com.tikal.tallerWeb.data.access.ServicioDAO;
import com.tikal.tallerWeb.modelo.entity.AutoEntity;
import com.tikal.tallerWeb.modelo.entity.ClienteEntity;
import com.tikal.tallerWeb.util.JsonConvertidor;

@Controller
@RequestMapping(value={"/search"})
public class SearchController {

	@Autowired
	ServicioDAO servdao;
	@Autowired
	AutoDAO autodao;
	@Autowired
	ClienteDAO clientedao;

	@RequestMapping(value = "/general/{busca}", method = RequestMethod.GET)
	public void buscarGeneral(HttpServletResponse resp, HttpServletRequest req, @PathVariable String busca)
			throws IOException {
		List<ClienteEntity> clientes= clientedao.buscarClientes(busca);
		List<AutoEntity> autos= autodao.buscar(busca);
		BusquedaVO vo= new BusquedaVO();
		for(ClienteEntity cli:clientes){
			vo.getNombres().add(cli.getNombre());
			vo.getTipos().add("cliente");
		}
		for(AutoEntity a: autos){
			vo.getNombres().add(a.getNumeroSerie());
			vo.getTipos().add("serie");
		}
		autos= autodao.buscarPlacas(busca);
		for(AutoEntity a: autos){
			vo.getNombres().add(a.getPlacas());
			vo.getTipos().add("placas");
		}
		resp.getWriter().println(JsonConvertidor.toJson(vo));
		
	}

	
}
