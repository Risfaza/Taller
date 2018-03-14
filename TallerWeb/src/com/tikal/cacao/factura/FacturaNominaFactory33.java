package com.tikal.cacao.factura;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tikal.cacao.dao.EmpresasDAO;
import com.tikal.cacao.dao.SerialDAO;
import com.tikal.cacao.model.Deduccion;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.NominaIASRenglon;
import com.tikal.cacao.model.Percepcion;
import com.tikal.cacao.model.PercepcionHorasExtra;
import com.tikal.cacao.model.Periodo;
import com.tikal.cacao.model.PeriodosDePago;
import com.tikal.cacao.model.Serial;
import com.tikal.cacao.sat.cfd.catalogos.C_Estado;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_ClaveUnidad;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_CodigoPostal;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_FormaDePago;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_MetodoDePago;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_Moneda;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_RegimenFiscal;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_TipoDeComprobante;
import com.tikal.cacao.sat.cfd.catalogos.dyn.C_UsoCFDI;
import com.tikal.cacao.sat.cfd.nomina.C_Banco;
import com.tikal.cacao.sat.cfd.nomina.C_PeriodicidadPago;
import com.tikal.cacao.sat.cfd.nomina.C_RiesgoPuesto;
import com.tikal.cacao.sat.cfd.nomina.C_TipoContrato;
import com.tikal.cacao.sat.cfd.nomina.C_TipoDeduccion;
import com.tikal.cacao.sat.cfd.nomina.C_TipoHoras;
import com.tikal.cacao.sat.cfd.nomina.C_TipoJornada;
import com.tikal.cacao.sat.cfd.nomina.C_TipoNomina;
import com.tikal.cacao.sat.cfd.nomina.C_TipoPercepcion;
import com.tikal.cacao.sat.cfd.nomina.C_TipoRegimen;
import com.tikal.cacao.sat.cfd.nomina.NominaElement;
import com.tikal.cacao.sat.cfd.nomina.NominaElement.Percepciones;
import com.tikal.cacao.sat.cfd.nomina.ObjectFactory;
import com.tikal.cacao.sat.cfd33.Comprobante;
import com.tikal.cacao.sat.cfd33.ObjectFactoryComprobante33;
import com.tikal.cacao.springController.viewObjects.PagoVO;
import com.tikal.cacao.util.GeneradorPeriodos;
import com.tikal.cacao.util.Util;

/**
 * Esta clase crea un CFDI en la versi&oacute;n 3.3 con el complemento para n&oacute;minas
 * en la versi&oacute;n 1.2.
 * @author Tikal
 *
 */
@Service
public class FacturaNominaFactory33 {
	
	private ObjectFactoryComprobante33 comprobanteOF = new ObjectFactoryComprobante33();
	
	private ObjectFactory nominaOF = new ObjectFactory();
	
	@Autowired
	private SerialDAO serialDAO;
	
	@Autowired
	private EmpresasDAO empresasDAO;
	
	
	/**
	 * Genera un objeto {@code Comprobante} con el complemento de Recibo de N&oacute;mina
	 * {@code NominaElement} utilizando los datos del objeto {@code PagoVO} especificado 
	 * @param pagoVO
	 * @return
	 */
	public Comprobante generarFactura(PagoVO pagoVO) {
		Comprobante comprobante = construirComprobante();
		comprobante.setEmisor(construirEmisor(pagoVO.getRfcEmpresa(), pagoVO.getRazonSocial(), "601")); //TODO agregar RegimenFiscal din�micamente
		comprobante.setReceptor(construirReceptor(pagoVO.getRfc(), pagoVO.getNombre()));
		
		NominaElement nominaElm = construirNodoNomina(pagoVO);
		comprobante.setConceptos(construirNodoConceptos(nominaElm));
		comprobante.getComplemento().add(construirComplementoNomina(nominaElm));
		comprobante.setSubTotal(nominaElm.getTotalPercepciones()); // m�s TotalOtrosPagos
    	comprobante.setDescuento(nominaElm.getTotalDeducciones());
    	comprobante.setTotal(nominaElm.getTotalPercepciones().
    			subtract(nominaElm.getTotalDeducciones())); // m�s TotalOtrosPagos
		return comprobante;
	}
	
	
	public Comprobante generarComprobanteNominaIAS(NominaIASRenglon datosNominaIAS) {
		Empresa empresa = empresasDAO.consultar(datosNominaIAS.getRfcEmisor());
		Comprobante comprobanteNominaIAS = construirComprobante();
		comprobanteNominaIAS.setMetodoPago(new C_MetodoDePago("PUE"));
		comprobanteNominaIAS.setLugarExpedicion( new C_CodigoPostal( empresa.getDireccion().getCodigoPostal() ) );
		Serial serial = serialDAO.consultar(datosNominaIAS.getRfcEmisor(), datosNominaIAS.getSerie());
		comprobanteNominaIAS.setSerie( serial.getSerie() );
		comprobanteNominaIAS.setFolio( String.valueOf( serial.getFolio() ) );
		
		comprobanteNominaIAS.setEmisor(construirEmisor(empresa.getRFC(), empresa.getRazonSocial(), "601"));
		comprobanteNominaIAS.setReceptor(construirReceptor(datosNominaIAS.getRfcTrabajadorIAS(), datosNominaIAS.getNombreTrabajadorIAS()));
		
		NominaElement nominaIAS = construirNodoNominaIAS(datosNominaIAS, empresa.getDireccion().getEstado());
		comprobanteNominaIAS.setFecha(nominaIAS.getFechaPago());
		comprobanteNominaIAS.getComplemento().add( construirComplementoNomina(nominaIAS) );
		
		comprobanteNominaIAS.setConceptos(construirNodoConceptos(nominaIAS));
		
		//Util.re
		BigDecimal descuento = new BigDecimal( Double.valueOf( datosNominaIAS.getImporteISR() ) );
		comprobanteNominaIAS.setDescuento( Util.redondearBigD( descuento, 2 ) );
		
		BigDecimal subtotal = new BigDecimal( Double.valueOf( datosNominaIAS.getImporteAsimilados() ) );
		comprobanteNominaIAS.setSubTotal( Util.redondearBigD( subtotal, 2 ) );
		
		comprobanteNominaIAS.setTotal(
				comprobanteNominaIAS.getSubTotal().subtract(comprobanteNominaIAS.getDescuento()));
		
		return comprobanteNominaIAS;
	}
	
	Comprobante construirComprobante() {
		Comprobante comprobante = comprobanteOF.createComprobante();
		comprobante.setVersion("3.3");
		if (Util.detectarAmbienteProductivo()) {
			Calendar calendarMexico = Calendar.getInstance();
			calendarMexico.add(Calendar.HOUR_OF_DAY, -5);
			comprobante.setFecha(Util.getXMLDate(calendarMexico.getTime(), FormatoFecha.COMPROBANTE));
		} else {
			comprobante.setFecha(Util.getXMLDate(new Date(), FormatoFecha.COMPROBANTE));
		}
		comprobante.setMoneda( new C_Moneda("MXN"));
		comprobante.setFormaPago(new C_FormaDePago("99")); //99 - Por definir
		comprobante.setTipoDeComprobante(new C_TipoDeComprobante("N"));
		return comprobante;
	}
	
	Comprobante.Emisor construirEmisor(String rfc, String nombre, String regimenFiscal) { 
		Comprobante.Emisor comprobanteEmisor = comprobanteOF.createComprobanteEmisor();
		comprobanteEmisor.setRfc(rfc);
		comprobanteEmisor.setNombre(nombre);
		comprobanteEmisor.setRegimenFiscal(new C_RegimenFiscal(regimenFiscal));
		return comprobanteEmisor;
	}
	
	Comprobante.Receptor construirReceptor(String rfc, String nombre) {
		Comprobante.Receptor comprobanteReceptor = comprobanteOF.createComprobanteReceptor();
		comprobanteReceptor.setRfc(rfc);
		comprobanteReceptor.setNombre(nombre);
		comprobanteReceptor.setUsoCFDI(new C_UsoCFDI("P01")); //TODO agregar din�micamente
		return comprobanteReceptor;
	}
	
	Comprobante.Conceptos construirNodoConceptos(NominaElement nominaElm) {
		Comprobante.Conceptos conceptos = comprobanteOF.createComprobanteConceptos();
		Comprobante.Conceptos.Concepto concepto = comprobanteOF.createComprobanteConceptosConcepto();
		concepto.setClaveProdServ("84111505");
		concepto.setCantidad(new BigDecimal(1));
		concepto.setClaveUnidad(new C_ClaveUnidad("ACT")); //ACT
		concepto.setDescripcion("Pago de n�mina");
		concepto.setValorUnitario(nominaElm.getTotalPercepciones().add(nominaElm.getTotalOtrosPagos()));
		concepto.setImporte(concepto.getValorUnitario());
		concepto.setDescuento(nominaElm.getTotalDeducciones());
		conceptos.getConcepto().add(concepto);
		return conceptos;
	}
	
	
	Comprobante.Complemento construirComplementoNomina(NominaElement nominaElm) {
		Comprobante.Complemento complementoNomina = comprobanteOF.createComprobanteComplemento();
		complementoNomina.getAny().add(nominaElm);
		return complementoNomina;
	}
	
	NominaElement construirNodoNomina(PagoVO pagoVO) {
		NominaElement nominaElm = nominaOF.createNominaElement();
		nominaElm.setVersion("1.2");
        nominaElm.setTipoNomina(C_TipoNomina.O);
        
        Date fechaDePago = pagoVO.getFechaDePago();
        Date primerDiadel2017 = new Date(1_483_250_400_000L);
        nominaElm.setFechaPago(Util.getXMLDate(fechaDePago, FormatoFecha.NOMINA));
        PeriodosDePago periodicidad = PeriodosDePago.valueOf(pagoVO.getFormaPago());
        Periodo periodo = GeneradorPeriodos.obtenerPeriodo(
        		GeneradorPeriodos.generarPeriodos(primerDiadel2017, periodicidad, 2017), fechaDePago);
		nominaElm.setFechaInicialPago(Util.getXMLDate(periodo.getFechaInicial(), FormatoFecha.NOMINA));
		nominaElm.setFechaFinalPago(Util.getXMLDate(periodo.getFechaFinal(), FormatoFecha.NOMINA));
		nominaElm.setNumDiasPagados( BigDecimal.valueOf( Double.parseDouble( pagoVO.getDiasPagados() ) ) );
        
		nominaElm.setEmisor(construirEmisor(pagoVO.getRegistroPatronal()));
		nominaElm.setReceptor(construirReceptor(pagoVO, nominaElm.getFechaFinalPago().toGregorianCalendar().getTime()));
		
		nominaElm.setPercepciones(construirPercepciones(pagoVO.getPercepciones()));
		nominaElm.setDeducciones(construirDeducciones(pagoVO.getDeducciones()));
		
		nominaElm.setTotalPercepciones(nominaElm.getPercepciones().getTotalSueldos().
				setScale(2, RoundingMode.HALF_UP)); //TODO + TotalSeparacionIndemnizacion + TotalJubilacionPensionRetiro
		
		nominaElm.setTotalDeducciones(nominaElm.getDeducciones().getTotalImpuestosRetenidos().
				add(nominaElm.getDeducciones().getTotalOtrasDeducciones()).
				setScale(2, RoundingMode.HALF_UP));
		
		return nominaElm;
	}
	
	NominaElement construirNodoNominaIAS(NominaIASRenglon datosNominaIAS, String estado) {
		NominaElement nominaElmIAS = nominaOF.createNominaElement();
		nominaElmIAS.setVersion("1.2");
        nominaElmIAS.setTipoNomina(C_TipoNomina.O);
        
//        if (Util.detectarAmbienteProductivo()) {
//			nominaElmIAS.setFechaPago(Util.getXMLDate(new Date(), FormatoFecha.NOMINA));
//		} else {
			nominaElmIAS.setFechaPago(Util.getXMLDate(new Date(), FormatoFecha.NOMINA));
//		}
			
		
        Date primerDiadelano = new Date(1_514_786_400_000L);
        //TODO cambiar primerDiadel2017 por primerDiadel2018 cuando llegue el cambio de a�o
        //Date primerDiadel2018 = new Date(1_514_786_400_000L);
        Periodo periodo = GeneradorPeriodos.obtenerPeriodo(
        		GeneradorPeriodos.generarPeriodos(primerDiadelano, PeriodosDePago.MENSUAL, 2018), //TODO cambiar por 2018
        		nominaElmIAS.getFechaPago().toGregorianCalendar().getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if(datosNominaIAS.getFechaFin()==null){
        	nominaElmIAS.setFechaInicialPago(Util.getXMLDate(periodo.getFechaInicial(), FormatoFecha.NOMINA));
	        nominaElmIAS.setFechaFinalPago(Util.getXMLDate(periodo.getFechaFinal(), FormatoFecha.NOMINA));
        }else{
	        try {
				Date fechai= formatter.parse(datosNominaIAS.getFechaInicio());
				String[] fechais= datosNominaIAS.getFechaInicio().split("/");
				String[] fechafs= datosNominaIAS.getFechaFin().split("/");
				GregorianCalendar cal= new GregorianCalendar();
				cal.set(Integer.parseInt(fechais[2]), Integer.parseInt(fechais[1])-1, Integer.parseInt(fechais[0]));
				cal.add(GregorianCalendar.HOUR_OF_DAY, 6);
				fechai=cal.getTime();
				cal.set(Integer.parseInt(fechafs[2]), Integer.parseInt(fechafs[1])-1, Integer.parseInt(fechafs[0]));
//				fechai.setTime(fechai.getTime()+1);
				cal.add(GregorianCalendar.HOUR_OF_DAY, 6);
				Date fechaf= cal.getTime();
//				fechaf.setTime(fechaf.getTime()+1);
				int dias=(int) ((fechaf.getTime()-fechai.getTime())/86400000);
				dias++;
				nominaElmIAS.setFechaInicialPago(Util.getXMLDate(fechai, FormatoFecha.NOMINA));
				nominaElmIAS.setFechaFinalPago(Util.getXMLDate(fechaf, FormatoFecha.NOMINA));
				nominaElmIAS.setFechaPago(Util.getXMLDate(fechaf, FormatoFecha.NOMINA));
				nominaElmIAS.setNumDiasPagados(new BigDecimal(dias));
			} catch (ParseException e) {
			    nominaElmIAS.setFechaInicialPago(Util.getXMLDate(periodo.getFechaInicial(), FormatoFecha.NOMINA));
		        nominaElmIAS.setFechaFinalPago(Util.getXMLDate(periodo.getFechaFinal(), FormatoFecha.NOMINA));
		        nominaElmIAS.setNumDiasPagados(new BigDecimal(30));
			}
        }
        
        nominaElmIAS.setReceptor(construirReceptorIAS(datosNominaIAS, estado));
        nominaElmIAS.setPercepciones( construirPercepcionesIAS(
        		Util.redondear( Double.valueOf( datosNominaIAS.getImporteAsimilados() ) ) ) );
        nominaElmIAS.setDeducciones( construirDeduccionesIAS(
        		Util.redondear( Double.valueOf( datosNominaIAS.getImporteISR() ) ) ) );
        
        nominaElmIAS.setTotalPercepciones(nominaElmIAS.getPercepciones().getTotalSueldos());
        //nominaElmIAS.setTotalOtrosPagos(new BigDecimal(0)); causa error NOM160
        nominaElmIAS.setTotalDeducciones(nominaElmIAS.getDeducciones().getTotalImpuestosRetenidos());
        
        return nominaElmIAS;
	}
	
	NominaElement.Emisor construirEmisor(String registroPatronal) {
		NominaElement.Emisor nominaEmisor = nominaOF.createNominaElementEmisor();
		//nominaEmisor.setRfcPatronOrigen("TIKA070401AZ");
		nominaEmisor.setRegistroPatronal(registroPatronal);
		return nominaEmisor;
	}
	
	NominaElement.Receptor construirReceptor(PagoVO pagoVO, Date fechaFinalPago) {
		NominaElement.Receptor nominaReceptor = nominaOF.createNominaElementReceptor();
		nominaReceptor.setCurp(pagoVO.getCurp());
		nominaReceptor.setTipoContrato(C_TipoContrato.VALUE_1); //Contrato de trabajo por tiempo indeterminado
		nominaReceptor.setTipoJornada(C_TipoJornada.VALUE_1); //Diurna
		nominaReceptor.setFechaInicioRelLaboral(Util.getXMLDate(Util.obtenerFecha(pagoVO.getFechaIngreso()), FormatoFecha.NOMINA));
		
		int semanasAntiguedad = Util.obtenerDiasEntre(nominaReceptor.getFechaInicioRelLaboral().toGregorianCalendar().getTime(), fechaFinalPago) / 7;
		nominaReceptor.setAntiguedad("P".concat(String.valueOf(semanasAntiguedad)).concat("W"));
		
		nominaReceptor.setTipoRegimen(C_TipoRegimen.fromValue(pagoVO.getTipoRegimen()));
		nominaReceptor.setRiesgoPuesto(C_RiesgoPuesto.VALUE_1);//Clase I
		nominaReceptor.setPeriodicidadPago( Util.convertir( PeriodosDePago.valueOf( pagoVO.getFormaPago() ) ) );//Quincenal
		nominaReceptor.setBanco(C_Banco.VALUE_7);//HSBC
		nominaReceptor.setCuentaBancaria(BigInteger.valueOf(12345678910L));;
		nominaReceptor.setClaveEntFed(C_Estado.MEX);//Estado de M�xico
		nominaReceptor.setNumSeguridadSocial(pagoVO.getNss());
		nominaReceptor.setNumEmpleado( String.valueOf( pagoVO.getIdEmpleado() ) );
		nominaReceptor.setSalarioBaseCotApor(BigDecimal.valueOf(pagoVO.getSalarioDiarioIntegrado()));
		return nominaReceptor;
	}
	
	NominaElement.Receptor construirReceptorIAS(NominaIASRenglon datosNominaIAS, String estado) {
		NominaElement.Receptor nominaIASReceptor = nominaOF.createNominaElementReceptor();
		nominaIASReceptor.setCurp(datosNominaIAS.getCurp());
		//TODO PROBAR M�TODO C_Estado.convertir( estado )
		nominaIASReceptor.setClaveEntFed( C_Estado.convertir( estado ) );
		
		// Modalidad de contrataci�n  donde no existe relaci&oacute;n de trabajo
		nominaIASReceptor.setTipoContrato(C_TipoContrato.VALUE_9);
		//Asimilados a honrarios
		nominaIASReceptor.setTipoRegimen(C_TipoRegimen.VALUE_8); 
		
		nominaIASReceptor.setNumEmpleado(datosNominaIAS.getRfcTrabajadorIAS());
		nominaIASReceptor.setPeriodicidadPago(C_PeriodicidadPago.VALUE_5); //Mensual
		
		return nominaIASReceptor;
	}
	
	NominaElement.Percepciones construirPercepciones(List<Percepcion> listaP) {
		NominaElement.Percepciones percepciones = nominaOF.createNominaElementPercepciones();
		NominaElement.Percepciones.Percepcion percepcion = null;
		for (Percepcion p : listaP) {
			percepcion = nominaOF.createNominaElementPercepcionesPercepcion();
			percepcion.setTipoPercepcion(C_TipoPercepcion.fromValue(p.getTipo()));
			percepcion.setClave(p.getClave());
			percepcion.setConcepto(p.getDescripcion());
			percepcion.setImporteExento(BigDecimal.valueOf(p.getImporteExento()));
			percepcion.setImporteGravado(BigDecimal.valueOf(p.getImporteGravado()));
			
			if (p instanceof PercepcionHorasExtra) {
				construirNodoHorasExtra(percepcion, (PercepcionHorasExtra)p);
			}
			
			percepciones.getPercepcion().add(percepcion);
		}
		
		double totalExento = 0, totalGravado = 0;
		for (NominaElement.Percepciones.Percepcion p : percepciones.getPercepcion()) {
			totalExento += p.getImporteExento().doubleValue();
			totalGravado += p.getImporteGravado().doubleValue();
		}
		percepciones.setTotalExento(new BigDecimal(totalExento));
		percepciones.setTotalGravado(new BigDecimal(totalGravado));
		percepciones.setTotalSueldos(percepciones.getTotalExento().add(percepciones.getTotalGravado()));
		return percepciones;
	}
	
	void construirNodoHorasExtra(Percepciones.Percepcion percepcion, PercepcionHorasExtra pHorasExtra) {
		int tercerDia = 0;
		int horasDobles = pHorasExtra.getHorasDobles();
		int horasTriples = pHorasExtra.getHorasTriples();
		double montoHorasTriples = pHorasExtra.getMontoHorasTriples();
		Percepciones.Percepcion.HorasExtra nodoHE = nominaOF.createNominaElementPercepcionesPercepcionHorasExtra();
		if (horasDobles > 0) {
			nodoHE.setTipoHoras(C_TipoHoras.VALUE_1);
			nodoHE.setHorasExtra(horasDobles);
			nodoHE.setImportePagado(percepcion.getImporteExento().
					add(percepcion.getImporteGravado().
					subtract(BigDecimal.valueOf(montoHorasTriples))));
			
			if (horasDobles % 3 >= 1) {
				tercerDia = 1;
			}
			nodoHE.setDias(horasDobles / 3 + tercerDia);
			percepcion.getHorasExtra().add(nodoHE);
		}
		
		if (horasTriples > 0) {
			nodoHE = nominaOF.createNominaElementPercepcionesPercepcionHorasExtra();
			nodoHE.setTipoHoras(C_TipoHoras.VALUE_2);
			nodoHE.setHorasExtra(horasTriples);
			nodoHE.setImportePagado(BigDecimal.valueOf(montoHorasTriples));
			
			if (horasTriples % 3 >= 1) {
				tercerDia = 1;
			}
			nodoHE.setDias(horasTriples / 3 + tercerDia);
			percepcion.getHorasExtra().add(nodoHE);
		}
		
		//TODO agregar caso de horas Simples C_TipoHoras.VALUE_3
	}
	
	
	NominaElement.Percepciones construirPercepcionesIAS(double ingresoAsimilado) {
		NominaElement.Percepciones percepciones = nominaOF.createNominaElementPercepciones();
		NominaElement.Percepciones.Percepcion percepcionIAS = nominaOF.createNominaElementPercepcionesPercepcion();
		// Ingresos asimilados a salarios
		percepcionIAS.setTipoPercepcion(C_TipoPercepcion.VALUE_37);
		
		percepcionIAS.setClave("046");
		percepcionIAS.setConcepto("ASIMILADOS");
		percepcionIAS.setImporteGravado( Util.redondearBigD( new BigDecimal(ingresoAsimilado) , 2 ) );
		percepcionIAS.setImporteExento(new BigDecimal(0));
		
		percepciones.getPercepcion().add(percepcionIAS);
		percepciones.setTotalGravado(percepcionIAS.getImporteGravado());
		percepciones.setTotalExento(percepcionIAS.getImporteExento());
		percepciones.setTotalSueldos( percepciones.getTotalGravado().add( percepciones.getTotalExento() ) );
		
		return percepciones;
	}
	
	NominaElement.Deducciones construirDeducciones(List<Deduccion> listaD) {
		NominaElement.Deducciones deducciones = nominaOF.createNominaElementDeducciones();
		NominaElement.Deducciones.Deduccion deduccion = null;
		for (Deduccion d : listaD) {
			deduccion = nominaOF.createNominaElementDeduccionesDeduccion();
			deduccion.setTipoDeduccion(C_TipoDeduccion.fromValue(d.getTipo()));
			deduccion.setClave(d.getClave());
			deduccion.setConcepto(d.getDescripcion());
			deduccion.setImporte( Util.redondearBigD( d.getDescuento() ));
			deducciones.getDeduccion().add(deduccion);
		}
		
		double totalImporte = 0;
		double totalImpuestos = 0;
		for (NominaElement.Deducciones.Deduccion d : deducciones.getDeduccion()) {
			if (!d.getTipoDeduccion().equals(C_TipoDeduccion.VALUE_2))
				totalImporte += d.getImporte().doubleValue();
			else 
				totalImpuestos += d.getImporte().doubleValue();
		}
		deducciones.setTotalOtrasDeducciones(Util.redondearBigD(totalImporte));
		deducciones.setTotalImpuestosRetenidos(Util.redondearBigD(totalImpuestos));
		return deducciones;
	}

	NominaElement.Deducciones construirDeduccionesIAS(double isrAsimilado) {
		NominaElement.Deducciones deducciones = nominaOF.createNominaElementDeducciones();
		NominaElement.Deducciones.Deduccion deduccionISR = nominaOF.createNominaElementDeduccionesDeduccion();
		
		deduccionISR.setTipoDeduccion(C_TipoDeduccion.VALUE_2);
		deduccionISR.setClave("002");
		deduccionISR.setConcepto("ISR");
		deduccionISR.setImporte( Util.redondearBigD( new BigDecimal(isrAsimilado) , 2 ) );
		
		deducciones.getDeduccion().add(deduccionISR);
		deducciones.setTotalImpuestosRetenidos(deduccionISR.getImporte());
		return deducciones;
	}
}
