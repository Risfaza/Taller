/**
 * 
 */
package com.tikal.cacao.util;

import org.springframework.stereotype.Component;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.cacao.model.Empleado;
import com.tikal.cacao.model.Empresa;
import com.tikal.cacao.model.Regimen;
import com.tikal.cacao.model.Usuario;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaDecenal;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaMensual;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaQuincenal;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaSemanal;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaSubsidio;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaTrabajoRealizado;

/**
 * @author Tikal
 *
 */
@Component
public class EntityRegister {
	
	/**
	 * This constructor register all entities used in the application
	 */
	public EntityRegister() {
		ObjectifyService.register(Empleado.class);
		ObjectifyService.register(Empresa.class);
		ObjectifyService.register(Regimen.class);
		ObjectifyService.register(Usuario.class);
		ObjectifyService.register(TarifaSubsidio.class);
		ObjectifyService.register(TarifaTrabajoRealizado.class);
		ObjectifyService.register(TarifaSemanal.class);
		ObjectifyService.register(TarifaDecenal.class);
		ObjectifyService.register(TarifaQuincenal.class);
		ObjectifyService.register(TarifaMensual.class);
	}

}
