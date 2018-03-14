/**
 * 
 */
package com.tikal.cacao.sat.calculos;

import java.util.Date;

import com.tikal.cacao.model.Percepcion;
import com.tikal.cacao.model.PercepcionSubsidiosIncapacidad;
import com.tikal.cacao.model.PeriodosDePago;

/**
 * @author Tikal
 *
 */
public class ProcesadorPercepcionSubsidiosIncapacidad extends ProcesadorPercepcion {

	/**
	 * @see com.tikal.cacao.sat.calculos.ProcesadorPercepcion#ejecutar(com.tikal.cacao.model.Percepcion, com.tikal.cacao.model.PeriodosDePago, java.util.Date)
	 */
	@Override
	protected void ejecutar(Percepcion percepcion, PeriodosDePago periodo, Date fechaContratacion) {
		// TODO aqui se utiliza el recurso 'boolean asegurado'
		PercepcionSubsidiosIncapacidad pSubsidiosIncapacidad = (PercepcionSubsidiosIncapacidad) percepcion;
		int diasIncapacidad = pSubsidiosIncapacidad.getDiasIncapacidad();
		if (this.isAsegurado()) {
			switch (pSubsidiosIncapacidad.getTipoIncapacidad()) {
			case ENFERMEDAD_EN_GENERAL:
				if (diasIncapacidad >= 4) {
					if (periodo.equals(PeriodosDePago.SEMANAL)) {
						int diasSubsidio = IMSS.calcularDiasCotizados(periodo) - diasIncapacidad;
						pSubsidiosIncapacidad.setCantidad(this.getSueldo() * 0.6 * (diasIncapacidad - 3) );
					} // else implementar un pago semanal si los días de incapacidad son mayores a 7
					
				}
				break;
			case RIESGO_DE_TRABAJO:
				if (periodo.equals(PeriodosDePago.SEMANAL)) {
					pSubsidiosIncapacidad.setCantidad(this.getSueldo() * (diasIncapacidad));
				}
				break;
			case MATERNIDAD:
				/*TODO separar a la empleada que pida licencia de maternidad del regimen
				  al que pertenece y a otro regimen que solo se encargue de generar los
				  pagos por subsidio de incapacidad por maternidad*/
				default:
				break;
			}
		} else {
			switch (pSubsidiosIncapacidad.getTipoIncapacidad()) {
			case ENFERMEDAD_EN_GENERAL:
				// por defecto no hay subsidio
				break;
			case RIESGO_DE_TRABAJO: // el subsidio es cubierto por el patrón
				pSubsidiosIncapacidad.setCantidad(this.getSueldo() * (diasIncapacidad));
				break;
			case MATERNIDAD: // el subsidio es cubierto por el patrón
				//TODO separar a la empleada que pida licencia de maternidad...
				break;
			default:
				break;
			}
		}

	}

}
