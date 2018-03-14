/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.util.Date;

import com.tikal.cacao.model.Deduccion;
import com.tikal.cacao.model.DeduccionPensionAlimenticia;
import com.tikal.cacao.model.PeriodosDePago;

/**
 * @author Tikal
 *
 */
public class ProcesadorDeduccionPensionAlimenticia extends ProcesadorDeduccion {

	/* (non-Javadoc)
	 * @see com.tikal.cacao.sat.calculos.ProcesadorDeduccion#ejecutar(com.tikal.cacao.model.Deduccion, com.tikal.cacao.model.PeriodosDePago, java.util.Date)
	 */
	@Override
	void ejecutar(Deduccion deduccion, PeriodosDePago periodo, Date fechaContratacion) {
		DeduccionPensionAlimenticia dPensionAlimenticia = (DeduccionPensionAlimenticia) deduccion;
		double totalAPagarAnterior = this.getTotalAPagar();
		this.setTotalAPagar( totalAPagarAnterior - CalculadoraNomina.calcularPensionAlimenticia( totalAPagarAnterior, dPensionAlimenticia.getPorcentajePension() ) );

	}

}
