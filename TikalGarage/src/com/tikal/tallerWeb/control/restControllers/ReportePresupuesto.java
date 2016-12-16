package com.tikal.tallerWeb.control.restControllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tikal.tallerWeb.control.restControllers.VO.DatosPresupuestoVO;
import com.tikal.tallerWeb.control.restControllers.VO.DatosServicioVO;
import com.tikal.tallerWeb.control.restControllers.VO.GruposCosto;
import com.tikal.tallerWeb.modelo.entity.AutoEntity;
import com.tikal.tallerWeb.modelo.entity.ClienteEntity;
import com.tikal.tallerWeb.modelo.entity.ServicioEntity;
import com.tikal.tallerWeb.rest.util.NewServiceObject;
import com.tikal.tallerWeb.util.JsonConvertidor;

@Controller
@RequestMapping(value= { "/reporte" })
public class ReportePresupuesto {
	
	
	@RequestMapping(value={"/presupuestoPDF"}, method = RequestMethod.POST, consumes="Application/Json")
	public ModelAndView generaReporte(HttpServletRequest request, HttpServletResponse response, @RequestBody String json){
//	@RequestMapping(value={"/presupuestoPDF"}, method = RequestMethod.GET)	
//	public ModelAndView generaReporte(HttpServletRequest request, HttpServletResponse response){

		DatosServicioVO holis = (DatosServicioVO) JsonConvertidor.fromJson(json, DatosServicioVO.class);
		NewServiceObject interfacin = holis.getServicio();
		List<GruposCosto> presupuestin = holis.getPresupuesto();
		AutoEntity auto = interfacin.getAuto();
		ClienteEntity cliente = interfacin.getCliente();
		ServicioEntity servicio = interfacin.getServicio();
		
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
		datos.setKilometros(servicio.getDatosAuto().getKilometraje());
		datos.setSerie(auto.getNumeroSerie());
		datos.setServicio(servicio.getDescripcion());
		datos.setNivelCombustible(servicio.getDatosAuto().getCombustible());
		datos.setObservaciones("Sin Obervaciones");
		datos.setListaServicios(presupuestin);
		
		
		
		
		return new ModelAndView("PDFViewer","aquinoseporquevaesto", datos);
	}
	
	

}
