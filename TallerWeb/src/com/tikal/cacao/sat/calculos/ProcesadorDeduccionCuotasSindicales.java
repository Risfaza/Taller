/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.util.Date;

import com.tikal.cacao.model.Deduccion;
import com.tikal.cacao.model.DeduccionCuotasSindicales;
import com.tikal.cacao.model.PeriodosDePago;

/**
 * @author Tikal
 *
 */
public class ProcesadorDeduccionCuotasSindicales extends ProcesadorDeduccion {

	/* (non-Javadoc)
	 * @see com.tikal.cacao.sat.calculos.ProcesadorDeduccion#ejecutar(com.tikal.cacao.model.Deduccion, com.tikal.cacao.model.PeriodosDePago, java.util.Date)
	 */
	@Override
	void ejecutar(Deduccion deduccion, PeriodosDePago periodo, Date fechaContratacion) {
		DeduccionCuotasSindicales dCuotasSindicales = (DeduccionCuotasSindicales) deduccion;
		this.setTotalAPagar(this.getTotalAPagar() - 
			CalculadoraNomina.aplicarDeduccion( dCuotasSindicales, 
				CalculadoraNomina.calcularCuotaSindical(this.getSueldo(), dCuotasSindicales.getPorcentajeCuota() ) ) );
	}

}
