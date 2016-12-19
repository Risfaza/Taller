package com.tikal.tallerWeb.control.restControllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tikal.tallerWeb.control.restControllers.VO.DatosPresupuestoVO;
import com.tikal.tallerWeb.control.restControllers.VO.DatosServicioVO;
import com.tikal.tallerWeb.control.restControllers.VO.GruposCosto;
import com.tikal.tallerWeb.data.access.AutoDAO;
import com.tikal.tallerWeb.data.access.ClienteDAO;
import com.tikal.tallerWeb.data.access.CostoDAO;
import com.tikal.tallerWeb.data.access.ServicioDAO;
import com.tikal.tallerWeb.modelo.entity.AutoEntity;
import com.tikal.tallerWeb.modelo.entity.ClienteEntity;
import com.tikal.tallerWeb.modelo.entity.ServicioEntity;
import com.tikal.tallerWeb.rest.util.NewServiceObject;

@Controller
@RequestMapping(value= { "/reporte" })
public class ReportePresupuesto {
	
	@Autowired
	ServicioDAO servdao;
	
	@Autowired
	AutoDAO autodao;
	
	@Autowired
	ClienteDAO clientedao;
	
	@Autowired
	CostoDAO costodao;
	
	@RequestMapping(value={"/presupuestoPDF/{id}"}, method = RequestMethod.GET,produces="application/pdf")
	public ModelAndView generaReporte(HttpServletRequest request, HttpServletResponse response, @PathVariable String id){
//	@RequestMapping(value={"/presupuestoPDF"}, method = RequestMethod.GET)	
//	public ModelAndView generaReporte(HttpServletRequest request, HttpServletResponse response){

		NewServiceObject servicio = new NewServiceObject();
		servicio.setServicio(servdao.cargar(Long.parseLong(id)));
		servicio.setAuto(autodao.cargar(Long.parseLong(servicio.getServicio().getIdAuto())));
		servicio.setCliente(clientedao.cargar(servicio.getServicio().getIdCliente()));
		List<GruposCosto> grupos = costodao.cargar(servicio.getServicio().getIdServicio());
		DatosServicioVO datosin = new DatosServicioVO();
		datosin.setServicio(servicio);
		datosin.setPresupuesto(grupos);
		
		NewServiceObject interfacin = datosin.getServicio();
		List<GruposCosto> presupuestin = datosin.getPresupuesto();
		AutoEntity auto = interfacin.getAuto();
		ClienteEntity cliente = interfacin.getCliente();
		ServicioEntity servicin = interfacin.getServicio();
		
		String domicilio = cliente.getDomicilio().getCalle() + "," +cliente.getDomicilio().getColonia() + "," +cliente.getDomicilio().getCiudad();
				
		DatosPresupuestoVO datos = new DatosPresupuestoVO();
		datos.setNombre(cliente.getNombre());
		datos.setDireccion(domicilio);
		datos.setEmail(cliente.getEmail());
		datos.setTelefono(cliente.getTelefonoContacto().toString());
		datos.setAsesor("S/D");
		datos.setMarca(auto.getMarca());
		datos.setTipo(auto.getTipo());
		datos.setModelo(auto.getModelo());
		datos.setColor(auto.getColor());
		datos.setPlacas(auto.getPlacas());
		datos.setKilometros(servicin.getDatosAuto().getKilometraje());
		datos.setSerie(auto.getNumeroSerie());
		datos.setServicio(servicin.getDescripcion());
		datos.setNivelCombustible(servicin.getDatosAuto().getCombustible());
		datos.setObservaciones("Sin Obervaciones");
		datos.setListaServicios(presupuestin);
		
		
		return new ModelAndView("PDFViewer","aquinoseporquevaesto", datos);
	}
	
	

}
