/**
 * 
 */
package com.tikal.cacao.model;

/**
 * @author Tikal
 *
 */
public enum RegimenContratacion {
	SUELDOS_Y_SALARIOS("Sueldos y salarios"),
	JUBILADOS("Salarios"),
	PENSIONADOS("Pensionados"),
	ASIMILADOS_A_SALARIOS__MIEMBROS_DE_LAS_SOCIEDADES_COOPERATIVAS_DE_PRODUCCION("Asimilados a salarios, Miembros de "),
	ASIMILADOS_A_SALARIOS_INTEGRANTES_DE_SOCIEDADES_Y_ASOCIACIONES_CIVILES("Pensionados"),
	ASIMILADOS_A_SALARIOS_MIEMBROS_DE_CONSEJOS_DIRECTIVOS_DE_VIGILANCIA_CONSULTIVOS_HONORARIOS_A_ADMINISTRADORES_COMISARIOS_Y_GERENTES_GENERALES("Pensionados"),
	ASIMILADOS_A_SALARIOS_ACTIVIDAD_EMPRESARIAL_COMISIONISTAS("Pensionados"),
	ASIMILADOS_A_SALARIOS_HONORARIOS_ASIMILADOS_A_SALARIOS("Pensionados"),
	ASIMILADOS_A_SALARIOS_INGRESOS_ACCIONES_O_TITULOS_VALOR("Pensionados");
	
	private String brandname; 
    private RegimenContratacion(String brand) { 
        this.brandname = brand; 
    } 
    
    @Override 
    public String toString(){ 
        return brandname; 
    } 

}
