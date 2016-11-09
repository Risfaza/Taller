package com.tikal.cacao.springController;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.cacao.dao.EmpresasDAO;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.springController.viewObjects.RegimenEmpresa;
import com.tikal.cacao.util.JsonConvertidor;

@Controller
@RequestMapping(value = { "/esquemas" })
public class EsquemasController {

	@Autowired
	EmpresasDAO empresasdao;

	@RequestMapping(value = {
			"/add" }, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public void addEmpresa(HttpServletResponse response, HttpServletRequest request, @RequestBody String json)
			throws IOException {

		RegimenEmpresa e = (RegimenEmpresa) JsonConvertidor.fromJson(json, RegimenEmpresa.class);
		
		empresasdao.aplicarUnRegimen(e.getRegimen(), empresasdao.consultar(e.getEmpresa()));;
	}

}
