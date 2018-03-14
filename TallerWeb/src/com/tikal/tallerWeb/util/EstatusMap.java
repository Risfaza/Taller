package com.tikal.tallerWeb.util;

import java.util.HashMap;
import java.util.Map;

public class EstatusMap {
	
	public static String getStatus(String evento){
		Map<String,String> mapa= new HashMap<String,String>();
		mapa.put("Entrada de Auto", "Diagnóstico");
		mapa.put("Diagnóstico", "Cotización");
		mapa.put("Salida de Auto", "Finalizado");
		mapa.put("Cancelación", "Cancelado");
		mapa.put("Entrada de Auto", "Diagnóstico");
		mapa.put("Termino de servicio", "Terminado");
		
		return mapa.get(evento);
	}
}
