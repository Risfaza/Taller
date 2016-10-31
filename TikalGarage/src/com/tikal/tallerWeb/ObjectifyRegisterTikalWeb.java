package com.tikal.tallerWeb;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.tallerWeb.modelo.servicio.Person;

public class ObjectifyRegisterTikalWeb {
	static {
        ObjectifyService.register(Person.class);
    }
}
