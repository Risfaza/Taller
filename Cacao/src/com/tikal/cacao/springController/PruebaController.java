package com.tikal.cacao.springController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tikal.cacao.dao.EmpresasDAO;
import com.tikal.cacao.dao.impl.EmpresasDAOImpl;
import com.tikal.cacao.model.Deduccion;
import com.tikal.cacao.model.Direccion;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.Estado;
import com.tikal.cacao.model.Percepcion;
import com.tikal.cacao.model.Regimen;
import com.tikal.cacao.model.TipoDeduccion;
import com.tikal.cacao.model.TipoPercepcion;

@Controller
@RequestMapping(value={"/prueba"})
public class PruebaController {
	

	@RequestMapping(value={"/prueba"},method= RequestMethod.GET)
	public void prueba(HttpServletResponse re) throws IOException{
		re.getWriter().println("prueba exitosa");
	}
	
	@RequestMapping(value={"/pruebaPersistencia"}, method = RequestMethod.GET)
	public void pruebaPersistenciaEmpyReg(HttpServletResponse re) throws IOException {
		Empresa empresa = new Empresa();
		empresa.setNombre("Tikal");
		empresa.setRazonSocial("TIKAL TECNOLOGÍAS DE LA INFORMACIÓN SA DE CV");
		empresa.setRFC("TIKL150201ABC");
		
		Direccion direccion = new Direccion();
		direccion.setCalle("calle");
		direccion.setCodigoPostal("50000");
		direccion.setColonia("colonia");
		direccion.setEstado(Estado.ESTADO_DE_MEXICO);
		direccion.setLocalidad("localidad");
		direccion.setNumInterior(20);
		direccion.setNumExterior(30);
		empresa.setDireccion(direccion);
		
		
		Regimen reg1 = new Regimen();
		reg1.setNombre("Regimen 1");
		reg1.setId(1);
		
		List<Percepcion> percepciones = new ArrayList<Percepcion>();
		
		Percepcion percepcion1 = new Percepcion();
		percepcion1.setTipo(TipoPercepcion.SUELDOS_SALARIOS_RAYAS_JORNALES);
		percepcion1.setCantidad(10000);
		
		Percepcion percepcion2 = new Percepcion();
		percepcion2.setTipo(TipoPercepcion.COMISIONES);
		percepcion2.setCantidad(5000);
		
		percepciones.add(percepcion1);
		percepciones.add(percepcion2);
		
		reg1.setPercepciones(percepciones);
		
		List<Deduccion> deducciones = new ArrayList<Deduccion>();
		
		Deduccion deduccion1 = new Deduccion();
		deduccion1.setTipo(TipoDeduccion.ISR);
		deduccion1.setDescuento(0.3);
		
		Deduccion deduccion2 = new Deduccion();
		deduccion2.setTipo(TipoDeduccion.OTROS);
		deduccion2.setDescuento(0.01);
		
		deducciones.add(deduccion1);
		deducciones.add(deduccion2);
		
		reg1.setDeducciones(deducciones);
		
		List<Regimen> regimenes = new ArrayList<Regimen>();
		
		empresa.setRegimenes(regimenes);
		
		EmpresasDAO dao = new EmpresasDAOImpl();
		dao.crear(empresa);
		Empresa empresaGuardada = dao.consultar("TIKL150201ABC");
		
		PrintWriter pw = re.getWriter();
	    pw.println(empresaGuardada.getRFC());
	    pw.println(empresaGuardada.getRegimenes().get(0).getId());
	    pw.println(empresaGuardada.getRegimenes().get(0).getPercepciones().get(0).getTipo());
	    pw.println(empresaGuardada.getRegimenes().get(0).getPercepciones().get(1).getTipo());
	    pw.println(empresaGuardada.getRegimenes().get(0).getDeducciones().get(0).getTipo());
	    pw.println(empresaGuardada.getRegimenes().get(0).getDeducciones().get(1).getTipo());
		
	}
}
