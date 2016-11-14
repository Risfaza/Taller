/**
 * 
 */
package com.tikal.cacao.model;

/**
 * @author Tikal
 *
 */
public enum TipoDeduccion {
	SEGURIDAD_SOCIAL("Seguridad social"),
	ISR("ISR"),
	APORTACIONES_A_RETIRO_CESANTIA_EN_EDAD_AVANZADA_Y_VEJEZ("Aportaciones a retiro cesantia en edad avanzada y vejez"),
	OTROS("Otros"),
	APORTACIONES_A_FONDO_DE_VIVIENDA("Aportaciones a Fondo de vivienda"),
	DESCUENTO_POR_INCAPACIDAD("Descuento por incapacidad"),
	PENSION_ALIMENTICIA("Pension alimenticia"),
	RENTA("Renta"),
	PRESTAMOS_PROVENIENTES_DEL_FONDO_NACIONAL_DE_LA_VIVIENDA_PARA_LOS_TRABAJADORES("Prestamos provenientes del Fondo Nacional de la Vivienda para los Trabajadores"),
	PAGO_POR_CREDITO_DE_VIVIENDA("Pago por credito de vivienda"),
	PAGO_DE_ABONOS_INFONACOT("Pago de abonos INFONACOT"),
	ANTICIPO_DE_SALARIOS("Anticipo de salarios"),
	PAGOS_HECHOS_CON_EXCESO_AL_TRABAJADOR("Pagos hechos con exceso al trabajador"),
	ERRORES("Errores"),
	PERDIDAS("Perdidas"),
	AVERIAS("Averias"),
	ADQUISICION_DE_ARTICULOS_PRODUCIDOS_POR_LA_EMPRESA_O_ESTABLECIMIENTO("Adquisicion de articulos producidos por la empresa o establecimiento"),
	CUOTAS_PARA_LA_CONSTITUCION_Y_FOMENTO_DE_SOCIEDADES_COOPERATIVAS_Y_DE_CAJAS_DE_AHORRO("Cuotas para la constitucion y fomento de sociedades cooperativas y de cajas de ahorro"),
	CUOTAS_SINDICALES("Cuotas sindicales"),
	AUSENCIA("Ausencia"),
	CUOTAS_OBRERO_PATRONALES("Cuotas obrero patronales");
	
	
	private String brandname;

	/**
	 * @param brandname
	 */
	private TipoDeduccion(String brandname) {
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
