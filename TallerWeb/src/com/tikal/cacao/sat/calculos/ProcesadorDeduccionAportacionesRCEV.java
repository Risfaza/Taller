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
public class ProcesadorDeduccionAportacionesRCEV extends ProcesadorDeduccion {

	/* 
	 * @see com.tikal.cacao.sat.calculos.ProcesadorDeduccion#ejecutar(com.tikal.cacao.model.Deduccion, com.tikal.cacao.model.PeriodosDePago, java.util.Date)
	 */
	@Override
	void ejecutar(Deduccion deduccion, PeriodosDePago periodo, Date fechaContratacion) {
		//TODO especificar si esta deduccion es una aportacion voluntaria o la cuota correspondiente a RCEV
		especificarSBC(periodo, fechaContratacion);
		this.setTotalAPagar(this.getTotalAPagar() - CalculadoraNomina.aplicarDeduccion(deduccion, IMSS.calcularAportacionesRCV(this.getSBC(), periodo)));
	}

}
