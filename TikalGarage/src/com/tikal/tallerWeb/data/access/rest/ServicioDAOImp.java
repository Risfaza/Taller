/**
 *   Copyright 2013-2015 TIKAL-TECHNOLOGY
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.tikal.tallerWeb.data.access.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.tallerWeb.data.access.ServicioDAO;
import com.tikal.tallerWeb.rest.util.AsyncRestCall;
import com.tikal.tallerWeb.rest.util.Callback;
//import com.tikal.tallerWeb.rest.util.RestTemplateFactory;

import technology.tikal.taller.automotriz.model.index.servicio.ServicioIndex;
import technology.tikal.taller.automotriz.model.servicio.Servicio;

/**
 * @author Nekorp
 */
@Service
public class ServicioDAOImp implements ServicioDAO {

//    @Autowired
//    @Qualifier("taller-RestTemplateFactory")
//    private RestTemplateFactory factory;
//    
    @Override
    public void guardar(Servicio dato) {
    	ObjectifyService.ofy().save().entity(dato);
        if (dato.getId() == null) {
//            URI resource = factory.getTemplate().postForLocation(factory.getRootUlr() + "/servicios", dato);
//            String[] uri = StringUtils.split(resource.toString(), '/');
//            String id = uri[uri.length - 1];
//            dato.setId(Long.valueOf(id));
//        	dato.setId(Long.valueOf(ObjectifyService.ofy().load().type(Servicio.class).list().size()-1));
        } else {
//            Map<String, Object> map = new HashMap<>();
//            map.put("id", dato.getId());
//            factory.getTemplate().postForLocation(factory.getRootUlr() + "/servicios/{id}", dato, map);
        }
        
    }

    @Override
    public Servicio cargar(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
//        Servicio r = factory.getTemplate().getForObject(factory.getRootUlr() + "/servicios/{id}", Servicio.class, map);
//        return r;
        return ObjectifyService.ofy().load().type(Servicio.class).filter("id",id).list().get(0);
    }

    @Override
    public List<ServicioIndex> getIndiceServicios() {
//        PaginaServicioIndex r = factory.getTemplate().getForObject(factory.getRootUlr() + "/index/servicio", PaginaServicioIndex.class);
    	return ObjectifyService.ofy().load().type(ServicioIndex.class).list();
//        List<ServicioIndex> respuesta = new LinkedList<>();
//        for (int i = r.size() - 1; i >= 0 ; i--) {
//        	ServicioIndex s= new ServicioIndex();
//        	s.setCobranza(r.get(i).getCobranza());
//        	s.setDescripcion(r.get(i).getDescripcion());
//        	
//        	
//        	respuesta.add(s);
//        }
//        return respuesta;
    	
//    	return null;
    }
    
    @Override
    public void getIndiceServicios(final Callback<List<ServicioIndex>> cmd) {
        Thread task = new AsyncRestCall<List<ServicioIndex>>() {
            @Override
            public List<ServicioIndex> executeCall() {
                return getIndiceServicios();
            }

            @Override
            public Callback getCallBack() {
                return cmd;
            }
        };
        task.start();
    }
    
    @Override
    public List<ServicioIndex> getIndiceServicios(Long sinceId) {
        Map<String, Object> map = new HashMap<>();
        map.put("idServicio", sinceId);
//        PaginaServicioIndex r = factory.getTemplate().getForObject(factory.getRootUlr() + "/index/servicio?sinceId={idServicio}", PaginaServicioIndex.class, map);
//        return r.getItems();
        return null;
    }

    @Override
    public List<ServicioIndex> getIndiceServiciosMismoAuto(String numeroSerie) {
        Map<String, Object> map = new HashMap<>();
        map.put("numeroSerieAuto", numeroSerie);
        List<ServicioIndex> servicios= ObjectifyService.ofy().load().type(ServicioIndex.class).filter("idAuto",numeroSerie).list();
        return servicios;
    }

    @Override
    public List<ServicioIndex> getIndiceServiciosPorStatus(String status) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        List<ServicioIndex> servicios= ObjectifyService.ofy().load().type(ServicioIndex.class).filter("status",status).list();
//        PaginaServicioIndex r = factory.getTemplate().getForObject(factory.getRootUlr() + "/index/servicio?statusServicio={status}", PaginaServicioIndex.class, map);
//        return r.getItems();
        return servicios;
    }

    @Override
    public List<Servicio> getByDate(DateTime fechaInicial, DateTime fechaFinal) {
        Map<String, Object> map = new HashMap<>();
        
        map.put("fechaInicial", fechaInicial);
        map.put("fechaFinal", fechaFinal);
        List<Servicio> servicios= ObjectifyService.ofy().load().type(Servicio.class).filter("fechaInicio >=",fechaInicial).filter("fechaInicio <=",fechaFinal).list();
        
//        PaginaServicio r = factory.getTemplate().getForObject(factory.getRootUlr() + "/servicios?fechaInicial={fechaInicial}&fechaFinal={fechaFinal}", PaginaServicio.class, map);
//        return r.getItems();
        return servicios;
    }

}
