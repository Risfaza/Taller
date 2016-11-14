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
import com.tikal.cacao.tarifas.TarifaISRMensual;

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
		ObjectifyService.register(TarifaISRMensual.class);
		
	}

}
