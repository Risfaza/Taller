/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.util.Date;

import com.tikal.cacao.model.Deduccion;
import com.tikal.cacao.model.DeduccionInfonavit;
import com.tikal.cacao.model.PeriodosDePago;

/**
 * @author Tikal
 *
 */
public class ProcesadorDeduccionPagoCreditoVivienda extends ProcesadorDeduccion {

	/* (non-Javadoc)
	 * @see com.tikal.cacao.sat.calculos.ProcesadorDeduccion#ejecutar(com.tikal.cacao.model.Deduccion, com.tikal.cacao.model.PeriodosDePago, java.util.Date)
	 */
	@Override
	void ejecutar(Deduccion deduccion, PeriodosDePago periodo, Date fechaContratacion) {
		DeduccionInfonavit dInfonavit = (DeduccionInfonavit) deduccion;
		especificarSBC(periodo, fechaContratacion);
		double pagoCredito = Infonavit.calcularPagoCredito(this.getSBC(), periodo, dInfonavit.getModalidad(), dInfonavit.getValorDeCredito());
		this.setTotalAPagar(this.getTotalAPagar() - 
			CalculadoraNomina.aplicarDeduccion(dInfonavit, pagoCredito));
	}

}
