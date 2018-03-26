/**
 *   Copyright 2015 TIKAL-TECHNOLOGY
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

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tikal.tallerWeb.control.MensajesControl;
import com.tikal.tallerWeb.data.access.CustomerDAO;
import com.tikal.tallerWeb.modelo.pagination.PaginaCustomer;
import com.tikal.tallerWeb.rest.util.AsyncRestCall;
import com.tikal.tallerWeb.rest.util.Callback;
//import com.tikal.tallerWeb.rest.util.RestTemplateFactory;

import technology.tikal.customers.model.ClienteMx;
import technology.tikal.customers.model.Customer;
import technology.tikal.customers.model.CustomerPojo;
import technology.tikal.customers.model.name.OrganizationName;
import technology.tikal.gae.error.model.BasicErrorMessage;
import technology.tikal.string.util.StringNormalizer;

/**
 *
 * @author Nekorp
 */
@Service
public class CustomerDAOImp implements CustomerDAO {
    private static final Logger LOGGER = Logger.getLogger(CustomerDAOImp.class);
//    @Autowired
//    @Qualifier("customer-RestTemplateFactory")
//    private RestTemplateFactory factory;
  //  @Autowired
    private MensajesControl mensajesControl;
 //   @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void guardar(Customer dato) {
        try {
            if (dato.getId() == null) {
//                URI resource = factory.getTemplate().postForLocation(factory.getRootUlr() + "/customer", dato);
//                String[] uri = StringUtils.split(resource.toString(), '/');
//                String id = uri[uri.length - 1];
//                CustomerPojo datoPjo = (CustomerPojo) dato;
//                datoPjo.setId(Long.valueOf(id));
            } else {
//                Map<String, Object> map = new HashMap<>();
//                map.put("id", dato.getId());
//                factory.getTemplate().postForLocation(factory.getRootUlr() + "/customer/{id}", dato, map);
            }
        }catch (HttpStatusCodeException ex) {
            try {
                BasicErrorMessage errorObj = objectMapper.readValue(ex.getResponseBodyAsString(), BasicErrorMessage.class);
                CustomerDAOImp.LOGGER.error("error msg: " + Arrays.toString(errorObj.getMessage()));
            } catch (Exception exd) {
                CustomerDAOImp.LOGGER.error(exd);
            }
            throw ex;
        }
    }

    @Override
    public void buscar(final String name, final Callback<Customer[]> cmd) {
        Thread task = new AsyncRestCall<Customer[]>() {
            @Override
            public Customer[] executeCall() {
                try {
                    String searchParam = StringNormalizer.normalize(name);
                    if (StringUtils.isEmpty(searchParam)) {
                        return new Customer[0];
                    }
                    //se busca todo y no se usa la busqueda del lado del server para aprovechar el cache del lado del cliente
//                    PaginaCustomer r = factory.getTemplate().getForObject(factory.getRootUlr() + "/customer", PaginaCustomer.class);
//                    LinkedList<Customer> filtrado = new LinkedList<>();
//                    for(Customer x: r.getItems()) {
//                        if (x instanceof ClienteMx && x.getName() instanceof OrganizationName) {
//                            OrganizationName name = (OrganizationName) x.getName();
//                            if (StringUtils.contains(StringNormalizer.normalize(name.getName()), searchParam)) {
//                                filtrado.add(x);
//                            }
//                        }
//                    }
//                    return filtrado.toArray(new Customer[filtrado.size()]);
                    return null;
                } catch(ResourceAccessException e) {
                    mensajesControl.reportaError("Error de comunicacion con el servidor");
                    return new Customer[0];
                }
            }

            @Override
            public Callback getCallBack() {
                return cmd;
            }
        };
        task.start();
    }

    @Override
    public Customer cargar(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
//        Customer r = factory.getTemplate().getForObject(factory.getRootUlr() + "/customer/{id}", Customer.class, map);
//        return r;
        return null;
    }

    @Override
    public Customer[] consultaTodos() {
//        PaginaCustomer r = factory.getTemplate().getForObject(factory.getRootUlr() + "/customer", PaginaCustomer.class);
//        LinkedList<Customer> filtrado = new LinkedList<>();
//        for(Customer x: r.getItems()) {
//            if (x instanceof ClienteMx) {
//                filtrado.add(x);
//            }
//        }
//        return filtrado.toArray(new Customer[filtrado.size()]);
    	return null;
    }

    @Override
    public void consultaTodos(final Callback<Customer[]> cmd) {
        Thread task = new AsyncRestCall<Customer[]>() {
            @Override
            public Customer[] executeCall() {
                return consultaTodos();
            }

            @Override
            public Callback getCallBack() {
                return cmd;
            }
        };
        task.start();
    }
    
}
