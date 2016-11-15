package com.tikal.cacao.springController;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
import com.tikal.cacao.model.RegimenContratacion;
import com.tikal.cacao.model.TipoDeduccion;
import com.tikal.cacao.model.TipoPercepcion;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaDecenal;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaMensual;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaQuincenal;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaSemanal;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaTrabajoRealizado;
import com.tikal.cacao.util.ExcelDataExtractor;
import com.tikal.cacao.util.JsonConvertidor;

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
		
		//empresa.getRegimenes().add(Ref.create(reg1));  // cómo generar esta 'Ref (llave) en tiempo de ejecución'
		
		EmpresasDAO dao = new EmpresasDAOImpl();
		dao.crear(empresa);
		Empresa empresaGuardada = dao.consultar("TIKL150201ABC");
		
		RegimenesDAO regdao = new RegimenesDAOImpl();
		regdao.crear(reg1);
		Regimen r = regdao.consultar(1);
		
		Regimen regFetched = empresaGuardada.getRegimenes().get(0);
		
		PrintWriter pw = re.getWriter();
	    pw.println(empresaGuardada.getRFC());
	    pw.println(regFetched.getNombre());
	    pw.println(regFetched.getPercepciones().get(0).getTipo());
	    pw.println(regFetched.getPercepciones().get(1).getTipo());
	    pw.println(regFetched.getDeducciones().get(0).getTipo());
	    pw.println(regFetched.getDeducciones().get(1).getTipo());
	    
	    pw.println(r.getId());
	    pw.println(r.getNombre());
	}
	
	
	@RequestMapping(value={"/pruebaEmpleadosEmpresas"}, method = RequestMethod.GET)
	public void pruebaEmpleadosEmpresas(HttpServletResponse re) throws IOException {
		EmpresasDAO empresasDAO = new EmpresasDAOImpl(); 
		RegimenesDAO regimenesDAO = new RegimenesDAOImpl();
		EmpleadosDAO empleadoDAO = new EmpleadosDAOImpl();
		
		
		Empresa[] empresas = crearEmpresas();
		Regimen[] regimenes = crearRegimenes();
		Empleado[] empleados = crearEmpleados();
		
		
		// Crear las listas de empleados y asignarlas a los regímenes
		regimenes[0].setIdEmpleados(crearListaDeIDsDeEmpleados(1L, 5L));
		regimenes[1].setIdEmpleados(crearListaDeIDsDeEmpleados(2L, 4L));
		regimenes[2].setIdEmpleados(crearListaDeIDsDeEmpleados(3L,5L));
		
		
		// Registrar a los empreados en las empresas por medio de los regímenes
		empresas[0].setRegimenes(crearListaRefDeRegimenes(regimenes[0], regimenes[1]));
		empresas[1].setRegimenes(crearListaRefDeRegimenes(regimenes[2]));
		
		// Persistir los entities en el Datastore
		empresasDAO.crear(empresas[0]);
		empresasDAO.crear(empresas[1]);
		
		regimenesDAO.crear(regimenes[0]);
		regimenesDAO.crear(regimenes[1]);
		regimenesDAO.crear(regimenes[2]);
		
		empleadoDAO.crear(empleados[0]);
		empleadoDAO.crear(empleados[1]);
		empleadoDAO.crear(empleados[2]);
		empleadoDAO.crear(empleados[3]);
		empleadoDAO.crear(empleados[4]);
		
		// recuperar los entities del Datastore
		Empresa empRecup1 = empresasDAO.consultar("rfc1");
		Empresa empRecup2 = empresasDAO.consultar("rfc2");
		
		Regimen regRecup1 = empRecup1.getRegimenes().get(0);
		Regimen regRecup2 = empRecup1.getRegimenes().get(1);
		Regimen regRecup3 = empRecup2.getRegimenes().get(0);
		
		Map<Long,Empleado> mapEmpleadosReg1 = 
				ofy().load().type(Empleado.class).ids(regRecup1.getIdEmpleados());
		
		Map<Long,Empleado> mapEmpleadosReg2 = 
				ofy().load().type(Empleado.class).ids(regRecup2.getIdEmpleados());
		
		Map<Long,Empleado> mapEmpleadosReg3 = 
				ofy().load().type(Empleado.class).ids(regRecup3.getIdEmpleados());
		
		List<Empleado> empleadosReg1 = getEmpleadosFromMap(mapEmpleadosReg1);
		List<Empleado> empleadosReg2 = getEmpleadosFromMap(mapEmpleadosReg2);
		List<Empleado> empleadosReg3 = getEmpleadosFromMap(mapEmpleadosReg3);
		
		//Empleado emplRecup1 = empleadoDAO.co
		
		PrintWriter pw = re.getWriter();
		pw.println("EMPRESAS");
		pw.println(empRecup1.getNombre());
		pw.println(empRecup1.getRazonSocial());
		pw.println(empRecup2.getNombre());
		pw.println(empRecup2.getRazonSocial());
		
		pw.println();
		pw.println();
		pw.println("ESQUEMAS DE LA EMPRESA 1");
		pw.println();
		pw.println("Nombre: " + regRecup1.getNombre());
		pw.println("ID: " + regRecup1.getId());
		pw.println("Tipo de Régimen: " + regRecup1.getTipoRegimen());
		pw.println();
		pw.println("Nombre: " + regRecup2.getNombre());
		pw.println("ID: " + regRecup2.getId());
		pw.println("Tipo de Régimen: " + regRecup2.getTipoRegimen());
		
		pw.println();
		pw.println();
		pw.println("ESQUEMAS DE LA EMPRESA 2");
		pw.println();
		pw.println("Nombre: " + regRecup3.getNombre());
		pw.println("ID: " + regRecup3.getId());
		pw.println("Tipo de Régimen: " + regRecup3.getTipoRegimen());
		
		pw.println();
		pw.println();
		pw.println("EMPLEADOS DE LA EMPRESA 1");
		displayEmpleadosFromList(empleadosReg1, pw);
		displayEmpleadosFromList(empleadosReg2, pw);
		
		pw.println();
		pw.println();
		pw.println("EMPLEADOS DE LA EMPRESA 2");
		displayEmpleadosFromList(empleadosReg3, pw);
		
		
		
	}
	
	@RequestMapping(value={"/pruebaGuardarTarifa/{nombreTarifa}"},method= RequestMethod.GET)
	public void pruebaGuardarTarifa(HttpServletResponse re, @PathVariable String nombreTarifa) {
		ExcelDataExtractor.guardarTarifa(nombreTarifa);
	}
	
	@RequestMapping(value={"/pruebaCargarTarifaT/{id}"},method= RequestMethod.GET, produces = "application/json")
	public void pruebaCargarTarifaT(HttpServletResponse re, @PathVariable long id) throws IOException {
		TarifaTrabajoRealizado tarifa = ExcelDataExtractor.recuperarT(id);
		PrintWriter pw = re.getWriter();
		pw.println(JsonConvertidor.toJson(tarifa));
	}
	
	@RequestMapping(value={"/pruebaCargarTarifaS/{id}"},method= RequestMethod.GET, produces = "application/json")
	public void pruebaCargarTarifaS(HttpServletResponse re, @PathVariable long id) throws IOException {
		TarifaSemanal tarifa = ExcelDataExtractor.recuperarS(id);
		PrintWriter pw = re.getWriter();
		pw.println(JsonConvertidor.toJson(tarifa));
	}
	
	@RequestMapping(value={"/pruebaCargarTarifaD/{id}"},method= RequestMethod.GET, produces = "application/json")
	public void pruebaCargarTarifaD(HttpServletResponse re, @PathVariable long id) throws IOException {
		TarifaDecenal tarifa = ExcelDataExtractor.recuperarD(id);
		PrintWriter pw = re.getWriter();
		pw.println(JsonConvertidor.toJson(tarifa));
	}
	
	@RequestMapping(value={"/pruebaCargarTarifaQ/{id}"},method= RequestMethod.GET, produces = "application/json")
	public void pruebaCargarTarifaQ(HttpServletResponse re, @PathVariable long id) throws IOException {
		TarifaQuincenal tarifa = ExcelDataExtractor.recuperarQ(id);
		PrintWriter pw = re.getWriter();
		pw.println(JsonConvertidor.toJson(tarifa));
	}
	
	@RequestMapping(value={"/pruebaCargarTarifaM/{id}"},method= RequestMethod.GET, produces = "application/json")
	public void pruebaCargarTarifaM(HttpServletResponse re, @PathVariable long id) throws IOException {
		TarifaMensual tarifa = ExcelDataExtractor.recuperarM(id);
		PrintWriter pw = re.getWriter();
		pw.println(JsonConvertidor.toJson(tarifa));
	}
	
	private Empresa[] crearEmpresas() {
		Direccion dir1 = new Direccion("Calle 1", 10, 0, "50000", "Colonia 1", "Localidad 1", Estado.AGUASCALIENTES);
		Direccion dir2 = new Direccion("Calle 2", 20, 2, "1000", "Colonia 2", "Localidad 2", Estado.BAJA_CALIFORNIA);
		
		Empresa empresa1 = new Empresa("rfc1", "Empresa1", "Empresa 1 SA de CV", dir1, null, true);
		Empresa empresa2 = new Empresa("rfc2", "Empresa2", "Empresa 2 SA de CV", dir2, null, true);
		
		return new Empresa[]{empresa1,empresa2};
	}
	
	private Regimen[] crearRegimenes() {
		Regimen reg1 = new Regimen(1L, "Regimen 1", null, null, null, true, RegimenContratacion.SUELDOS_Y_SALARIOS);
		Regimen reg2 = new Regimen(2L, "Regimen 2", null, null, null, true, RegimenContratacion.SUELDOS_Y_SALARIOS);
		Regimen reg3 = new Regimen(3L, "Regimen 3", null, null, null, true, RegimenContratacion.SUELDOS_Y_SALARIOS);
		
		return new Regimen[] {reg1,reg2,reg3};
	}
	
	private Empleado[] crearEmpleados() {
		Empleado empleado1, empleado2, empleado3, empleado4, empleado5;
		empleado1 = new Empleado(
				1L, new NombrePersona("Juan", "Lopez", "Perez"),
				"JLOP800101ABCDL00", null, "0123456789", null, null, true);
		empleado2 = new Empleado(
				2L, new NombrePersona("John", "Snow", null),
				"JSOG800101ABCDL00", null, "9876543210", null, null, true);
		empleado3 = new Empleado(
				3L, new NombrePersona("Tiryon", "Lannister", null),
				"TLAG800101ABCDL00", null, "1011121314", null, null, true);
		empleado4 = new Empleado(
				4L, new NombrePersona("Arya", "Stark", null),
				"ASTG800101ABCDL00", null, "1516171819", null, null, true);
		empleado5 = new Empleado(
				5L, new NombrePersona("Daenerys", "Targaryen", null),
				"DTAG800101ABCDL00", null, "2021222324", null, null, true);
		
		
		return new Empleado[] {empleado1, empleado2, empleado3, empleado4, empleado5};
	}
	
	private List<Long> crearListaDeIDsDeEmpleados(Long... ids) {
		List<Long> lista = new ArrayList<Long>();
		for(int i = 0; i < ids.length; i++) {
			lista.add(ids[i]);
		}
		return lista;
	}
	
	private List<Ref<Regimen>> crearListaRefDeRegimenes(Regimen... regs) {
		List<Ref<Regimen>> lista = new ArrayList<Ref<Regimen>>();
		for (int i = 0; i < regs.length; i++) {
			lista.add(Ref.create(regs[i]));
		}
		return lista;
		
	}
	
	private List<Empleado> getEmpleadosFromMap(Map<Long,Empleado> mapa) {
		List<Empleado> lista = new ArrayList<Empleado>(mapa.values());
		return lista;
	}
	
	private void displayEmpleadosFromList(List<Empleado> lista, PrintWriter pw) {
		for (Empleado empleado : lista) {
			pw.println("Numero de empleado: " + empleado.getNumEmpleado());
			pw.println("Nombre del empleado: " 
					+ empleado.getNombre().getNombresDePila() 
					+ empleado.getNombre().getApellidoPaterno());
		}
	}
	
	

	
}
