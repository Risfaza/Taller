/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.util.Date;

import com.tikal.cacao.model.Deduccion;
import com.tikal.cacao.model.PeriodosDePago;

/**
 * @author Tikal
 *
 */
public class ProcesadorDeduccionISR extends ProcesadorDeduccion {

	/**
	 * @see com.tikal.cacao.sat.calculos.ProcesadorDeduccion#ejecutar(com.tikal.cacao.model.Deduccion, com.tikal.cacao.model.PeriodosDePago, java.util.Date)
	 */
	@Override
	void ejecutar(Deduccion deduccion, PeriodosDePago periodo, Date fechaContratacion) {
		this.setTarifa(CalculadoraNomina.obtenerTarifa(getIngresoGravable(), periodo));
		double isr = CalculadoraNomina.calcularISR(this.getIngresoGravable(), this.getTarifa(), false);
		if (isr > 0)
			this.setTotalAPagar(this.getTotalAPagar() - CalculadoraNomina.aplicarDeduccion(deduccion, isr));
		else 
			this.setSubsidioAlEmpleo(isr * (-1));;//TODO agregar un atributo al mapa de recursos para establecer la cantidad de subsidio al empleo
	}

}
