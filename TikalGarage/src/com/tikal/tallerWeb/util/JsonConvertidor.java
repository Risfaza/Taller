package com.tikal.tallerWeb.util;

import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;

public class JsonConvertidor {
	public static Object fromJson(String json, Class clase){
		Gson g= new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		
		return g.fromJson(json,clase);
	}
	
	public static String toJson(Object o){
		Gson g= new GsonBuilder().setDateFormat("dd/MM/yyyy").create();;
		return g.toJson(o);
	}
}
