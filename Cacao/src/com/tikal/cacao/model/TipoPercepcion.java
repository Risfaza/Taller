/**
 * 
 */
package com.tikal.cacao.model;

/**
 * @author Tikal
 *
 */
public enum TipoPercepcion {
	SUELDOS_SALARIOS_RAYAS_JORNALES("Sueldos Salarios Rayas Jornales"),
	GRATIFICACION_ANUAL_AGUINALDO("Gratificacion Anual Aguinaldo"),
	PARTICIPACION_DE_LOS_TRABAJADORES_EN_LAS_UTILIDADES_PTU("Participacion de los Trabajadores en las Utilidades PTU"), 
	REEMBOLSO_GASTOS_MEDICOS_DENTALES_Y_HOSPITALARIOS("Reembolso Gastos Medicos Dentales y Hospitalarios"),
	FONDO_DE_AHORRO("Fondo de Ahorro"),
	CAJA_DE_AHORRO("Caja de Ahorro"),
	CONTRIBUCIONES_A_CARGO_DEL_TRABAJADOR_PAGADAS_POR_EL_PATRON("Contribuciones a Cargo del Trabajador Pagadas por el Patron"),
	PREMIOS_POR_PUNTUALIDAD("Premios por puntualidad"),
	PRIMA_DE_SEGURO_DE_VIDA("Prima de Seguro de vida"),
	SEGURO_DE_GASTOS_MEDICOS_MAYORES("Seguro de Gastos Medicos Mayores"),
	CUOTAS_SINDICALES_PAGADAS_POR_EL_PATRON("Cuotas Sindicales Pagadas por el Patron"),
	SUBSIDIOS_POR_INCAPACIDAD("Subsidios por incapacidad"),
	BECAS_PARA_TRABAJADORES_Y_O_HIJOS("Becas para trabajadores y o hijos"),
	OTROS("Otros"),
	SUBSIDIOS_PARA_EL_EMPLEO("Subsidios para el empleo"),
	HORAS_EXTRA("Horas extra"),
	PRIMA_DOMINICAL("Prima dominical"),
	PRIMA_VACACIONAL("Prima vacacional"),
	PRIMA_POR_ANTIGUEDAD("Prima por antiguedad"),
	PAGOS_POR_SEPARACION("Pagos por separacion"),
	SEGURO_DE_RETIRO("Seguro de retiro"),
	INDEMNIZACIONES("Indemnizaciones"),
	REEMBOLSO_POR_FUNERAL("Reembolso por funeral"),
	CUOTAS_DE_SEGURIDAD_SOCIAL_PAGADAS_POR_EL_PATRON("Cuotas de seguridad social pagadas por el patron"),
	COMISIONES("Comisiones"),
	VALES_DE_DESPENSA("Vales de despensa"),
	VALES_DE_RESTAURANTE("Vales de restaurante"),
	VALES_DE_GASOLINA("Vales_de_gasolina"),
	VALES_DE_ROPA("Vales_de_ropa"),
	AYUDA_PARA_RENTA("Ayuda para renta"),
	AYUDA_PARA_ARTICULOS_ESCOLARES("Ayuda para articulos escolares"),
	AYUDA_PARA_ANTEOJOS("Ayuda para anteojos"),
	AYUDA_PARA_TRANSPORTE("Ayuda para transporte"),
	AYUDA_PARA_GASTOS_DE_FUNERAL("Ayuda para gastos de funeral"),
	OTROS_INGRESOS_POR_SALARIOS("Otros ingresos por salarios"),
	JUBILACIONES_PENSIONES_O_HABERES_DE_RETIRO("Jubilaciones pensiones o haberes de retiro"),
	INGRESO_PAGADO_POR_ENTIDADES_FEDERATIVAS_MUNICIPIOS_O_DEMARCACIONES_TERRITORIALES_DEL_DISTRITO_FEDERAL_ORGANISMOS_AUTONOMOS_Y_ENTIDADES_PARAESTATALES_Y_PARAMUNICIPALES_CON_INGRESOS_PROPIOS(""
			+ "Ingreso pagado por Entidades federativas municipios o demarcaciones territoriales del Distrito Federal organismos autonomos y entidades paraestatales y paramunicipales con ingresos propios"),
	INGRESO_PAGADO_POR_ENTIDADES_FEDERATIVAS_MUNICIPIOS_O_DEMARCACIONES_TERRITORIALES_DEL_DISTRITO_FEDERAL_ORGANISMOS_AUTONOMOS_Y_ENTIDADES_PARAESTATALES_Y_PARAMUNICIPALES_CON_INGRESOS_FEDERAÑES(""
			+ "Ingreso pagado por Entidades federativas municipios o demarcaciones territoriales del Distrito Federal organismos autonomos y entidades paraestatales y paramunicipales con ingresos federales"),
	INGRESO_PAGADO_POR_ENTIDADES_FEDERATIVAS_MUNICIPIOS_O_DEMARCACIONES_TERRITORIALES_DEL_DISTRITO_FEDERAL_ORGANISMOS_AUTONOMOS_Y_ENTIDADES_PARAESTATALES_Y_PARAMUNICIPALES_CON_INGRESOS_PROPIOS_Y_FEDERALES(""
			+ "Ingreso pagado por Entidades federativas municipios o demarcaciones territoriales del Distrito Federal organismos autonomos y entidades paraestatales y paramunicipales con ingresos propios y federales");
	
	
	private String brandname;

	/**
	 * @param brandname
	 */
	private TipoPercepcion(String brandname) {
		this.brandname = brandname;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return brandname;
	} 
	
	
}
