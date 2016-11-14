package com.tikal.cacao.springController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.googlecode.objectify.Ref;
import com.tikal.cacao.dao.EmpleadosDAO;
import com.tikal.cacao.dao.EmpresasDAO;
import com.tikal.cacao.dao.RegimenesDAO;
import com.tikal.cacao.dao.impl.EmpleadosDAOImpl;
import com.tikal.cacao.dao.impl.EmpresasDAOImpl;
import com.tikal.cacao.dao.impl.RegimenesDAOImpl;
import com.tikal.cacao.model.Deduccion;
import com.tikal.cacao.model.Direccion;
import com.tikal.cacao.model.Empleado;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.Estado;
import com.tikal.cacao.model.NombrePersona;
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
		reg1.setId(1L);
		
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
		
		List<Ref<Regimen>> regimenes = new ArrayList<Ref<Regimen>>();
		
		
		
		//regimenes.add(Ref.create(reg1));
		
		empresa.setRegimenes(regimenes);
		
//		empresa.getRegimenes().add(Ref.create(reg1));  // cómo generar esta 'Ref (llave) en tiempo de ejecución'
		
		EmpresasDAO dao = new EmpresasDAOImpl();
		dao.crear(empresa);
		Empresa empresaGuardada = dao.consultar("TIKL150201ABC");
		
		RegimenesDAO regdao = new RegimenesDAOImpl();
		regdao.crear(reg1);
		Regimen r = regdao.consultar(1);
		
//		Regimen regFetched = empresaGuardada.getRegimenes().get(0).get();
		
//		PrintWriter pw = re.getWriter();
//	    pw.println(empresaGuardada.getRFC());
//	    pw.println(regFetched.getNombre());
//	    pw.println(regFetched.getPercepciones().get(0).getTipo());
//	    pw.println(regFetched.getPercepciones().get(1).getTipo());
//	    pw.println(regFetched.getDeducciones().get(0).getTipo());
//	    pw.println(regFetched.getDeducciones().get(1).getTipo());
////	    
//	    pw.println(r.getId());
//	    pw.println(r.getNombre());
	}
	
	/*@RequestMapping(value={"/pruebaEmpleados"}, method = RequestMethod.GET)
	public void pruebaEmpleadosyRegimenes(HttpServletResponse re) throws IOException {
		EmpleadosDAO empDAO = new EmpleadosDAOImpl();
		RegimenesDAO regdao = new RegimenesDAOImpl();
		
		Empleado emp1 = new Empleado();
		Empleado emp2 = new Empleado();
		
		emp1.setNombre(new NombrePersona("Juan", "Lopez", "Marin"));
		emp1.setCurp("JLOM800101HMCLS00");
		emp1.setDireccion(new Direccion("Calle emp1", 100, 200, "1000", "Colonia emp1", "Localidad emp1", Estado.AGUASCALIENTES));
		emp1.setEmail("emp1@mail.com");
		emp1.setNumSeguroSocial("123456789");
		emp1.setTelefono("7225678910");
		
		emp2.setNombre(new NombrePersona("Dolores", "Perez", "De la O"));
		emp2.setCurp("DPED800101HMCLS00");
		emp2.setDireccion(new Direccion("Calle emp2", 300, 400, "2000", "Colonia emp2", "Localidad emp2", Estado.QUERETARO));
		emp2.setEmail("emp2@mail.com");
		emp2.setNumSeguroSocial("987654321");
		emp2.setTelefono("7225109876");
		
		// Guardar los empleados en el DataStore
		empDAO.crear(emp1);
		empDAO.crear(emp2);
		
		
		
		Regimen reg = regdao.consultar(1);
		
		List<Long> empleados = new ArrayList<Long>();
		
		
		// reg.setIdEmpleados(idEmpleados);
		
		
	}*/
}
