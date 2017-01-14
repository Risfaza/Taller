package com.tikal.tallerWeb.control.restControllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.tallerWeb.control.restControllers.VO.BusquedaVO;
import com.tikal.tallerWeb.control.restControllers.VO.ServicioListVO;
import com.tikal.tallerWeb.data.access.AutoDAO;
import com.tikal.tallerWeb.data.access.ClienteDAO;
import com.tikal.tallerWeb.data.access.ServicioDAO;
import com.tikal.tallerWeb.modelo.entity.AutoEntity;
import com.tikal.tallerWeb.modelo.entity.ClienteEntity;
import com.tikal.tallerWeb.util.JsonConvertidor;

import technology.tikal.taller.automotriz.model.index.servicio.ServicioIndex;

@Controller
@RequestMapping(value = { "/search" })
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
		List<ClienteEntity> clientes = clientedao.buscarClientes(busca);
		List<AutoEntity> autos = autodao.buscar(busca);
		BusquedaVO vo = new BusquedaVO();
		for (ClienteEntity cli : clientes) {
			vo.getNombres().add(cli.getNombre());
			vo.getTipos().add("cliente");
		}
		for (AutoEntity a : autos) {
			vo.getNombres().add(a.getNumeroSerie());
			vo.getTipos().add("serie");
		}
		autos = autodao.buscarPlacas(busca);
		for (AutoEntity a : autos) {
			vo.getNombres().add(a.getPlacas());
			vo.getTipos().add("placas");
		}
		resp.getWriter().println(JsonConvertidor.toJson(vo));

	}
	
	@RequestMapping(value = "/cliente/{busca}", method = RequestMethod.GET)
	public void buscarCliente(HttpServletResponse resp, HttpServletRequest req, @PathVariable String busca)
			throws IOException {
		List<ClienteEntity> clientes = clientedao.buscarClientes(busca);
		
		resp.getWriter().println(JsonConvertidor.toJson(clientes));

	}

	@RequestMapping(value = "/filtra/{busca}/{tipo}", method = RequestMethod.GET)
	public void buscarTipo(HttpServletResponse resp, HttpServletRequest req, @PathVariable String busca,
			@PathVariable String tipo) throws IOException {
		List<ServicioIndex> lista = servdao.getIndiceServicios();
		List<ServicioIndex> result = new ArrayList<ServicioIndex>();
		if (tipo.compareTo("cliente") == 0) {
			ClienteEntity cli = clientedao.buscarCliente(busca);
			for (ServicioIndex sind : lista) {
				if (sind.getIdCliente() != null) {
					if (sind.getIdCliente().compareTo(cli.getIdCliente()) == 0) {
						result.add(sind);
					}
				}
			}
		}

		if (tipo.compareTo("serie") == 0) {
			AutoEntity cli = autodao.cargar(busca);
			for (ServicioIndex sind : lista) {
				if (sind.getIdAuto().compareToIgnoreCase(busca) == 0) {
					result.add(sind);
				}
			}
		}

		if (tipo.compareTo("placas") == 0) {
			AutoEntity cli = autodao.buscarPlacas(busca).get(0);
			for (ServicioIndex sind : lista) {
				if (sind.getIdAuto().compareToIgnoreCase(busca) == 0) {
					result.add(sind);
				}
			}
		}

		List<ServicioListVO> ret = new ArrayList<ServicioListVO>();
		for (ServicioIndex si : result) {
			AutoEntity auto = new AutoEntity();
			ClienteEntity cliente = new ClienteEntity();
			try {
				auto = autodao.cargar(Long.parseLong(si.getIdAuto()));
				cliente = clientedao.cargar(si.getIdCliente());
			} catch (Exception e) {

			}
			ServicioListVO svo = new ServicioListVO(si, auto, cliente);
			ret.add(svo);
		}
		resp.getWriter().println(JsonConvertidor.toJson(ret));

	}

}
