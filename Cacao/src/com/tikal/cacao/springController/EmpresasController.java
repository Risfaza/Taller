
package com.tikal.cacao.springController;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.cacao.dao.EmpresasDAO;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.util.JsonConvertidor;

@Controller
@RequestMapping(value={"/empresas"})
public class EmpresasController {

	@Autowired
	@Qualifier("empresasdao")
	EmpresasDAO empresasdao;
	
	@RequestMapping(value={"/add"},method= RequestMethod.POST,produces="application/json",consumes="application/json")
	public void addEmpresa(HttpServletResponse response,HttpServletRequest request,@RequestBody String json) throws IOException{
		Empresa e= (Empresa) JsonConvertidor.fromJson(json, Empresa.class);
		empresasdao.crear(e);
		response.getWriter().println(JsonConvertidor.toJson(empresasdao.consultar(e.getRFC())));
	}
	
	@RequestMapping(value={"/find/{rfc}"},method= RequestMethod.GET,produces="application/json")
	public void findEmpresa(HttpServletResponse response,HttpServletRequest request,@RequestBody String json,@PathVariable String rfc) throws IOException{
//		Empresa e= (Empresa) JsonConvertidor.fromJson(json, Empresa.class);
//		empresasdao.crear(e);
		response.getWriter().println(JsonConvertidor.toJson(empresasdao.consultar(rfc)));
	}
	
	@RequestMapping(value={"/update"},method= RequestMethod.GET,produces="application/json",consumes="application/json")
	public void updateEmpresa(HttpServletResponse response,HttpServletRequest request,@RequestBody String json) throws IOException{
		Empresa e= (Empresa) JsonConvertidor.fromJson(json, Empresa.class);
		empresasdao.actualizar(e);
		response.getWriter().println(JsonConvertidor.toJson(empresasdao.consultar(e.getRFC())));
	}
	
	@RequestMapping(value={"/delete/{rfc}"},method= RequestMethod.GET,produces="application/json",consumes="application/json")
	public void deleteEmpresa(HttpServletResponse response,HttpServletRequest request,@RequestBody String json,@PathVariable String rfc) throws IOException{
		empresasdao.eliminar(empresasdao.consultar(rfc));
	}
	
	
}
