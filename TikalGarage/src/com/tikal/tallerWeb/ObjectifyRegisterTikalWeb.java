package com.tikal.tallerWeb;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.tallerWeb.modelo.entity.AutoEntity;
import com.tikal.tallerWeb.modelo.servicio.Person;

public class ObjectifyRegisterTikalWeb {
	ObjectifyRegisterTikalWeb() {
        ObjectifyService.register(Person.class);
        ObjectifyService.register(AutoEntity.class);
    }
}
