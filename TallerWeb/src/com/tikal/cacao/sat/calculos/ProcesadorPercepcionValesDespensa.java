/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.util.Date;

import com.tikal.cacao.model.Percepcion;
import com.tikal.cacao.model.PeriodosDePago;

/**
 * @author Tikal
 *
 */
public class ProcesadorPercepcionValesDespensa extends ProcesadorPercepcion {

	/**
	 * @see com.tikal.cacao.sat.calculos.ProcesadorPercepcion#ejecutar(com.tikal.cacao.model.Percepcion, com.tikal.cacao.model.PeriodosDePago, java.util.Date)
	 */
	@Override
	protected void ejecutar(Percepcion percepcion, PeriodosDePago periodo, Date fechaContratacion) {
		double cantidad = percepcion.getCantidad();
		this.setMontoPrevisionSocial(this.getMontoPrevisionSocial() + cantidad);
		this.setTotalAPagar(this.getTotalAPagar() + cantidad);
//		Si esta percepcion se entrega en efectivo se realizan las siguientes operaciones
//		if (!percepcion.isPrevisionSocial()) {
//			this.setIngresoGravable(this.getIngresoGravable() + cantidad);
//			this.setTotalAPagar(this.getTotalAPagar() + cantidad);
//		}
	}

}
