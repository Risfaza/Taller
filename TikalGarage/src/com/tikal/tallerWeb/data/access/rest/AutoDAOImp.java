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
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.tallerWeb.data.access.AutoDAO;
import com.tikal.tallerWeb.modelo.entity.AutoEntity;

import technology.tikal.taller.automotriz.model.auto.Auto;
import technology.tikal.taller.automotriz.model.index.servicio.ServicioIndexAutoData;

/**
 * @author Nekorp
 */
@Service
public class AutoDAOImp  implements AutoDAO
{

	// @Autowired
	// @Qualifier("taller-RestTemplateFactory")
	// private RestTemplateFactory factory;
	//
	public void guardar(AutoEntity dato) {
		// como no tengo idea si es nuevo o no primero se intenta como si ya
		// existiera, si regresa 404 se crea.
		ObjectifyService.ofy().save().entity(dato).now();

//		try {
//			Map<String, Object> map = new HashMap<>();
//			map.put("numeroSerie", dato.getNumeroSerie());
//			// factory.getTemplate().postForLocation(factory.getRootUlr() +
//			// "/autos/{numeroSerie}", dato, map);
//			Key customerKey = KeyFactory.createKey("Auto", dato.getNumeroSerie());
//			Entity car = new Entity("Auto", customerKey);
//			car.setProperty("color", dato.getColor());
//			car.setProperty("equipamiento", dato.getEquipamiento());
//			car.setProperty("marca", dato.getMarca());
//			car.setProperty("modelo", dato.getModelo());
//			car.setProperty("placas", dato.getPlacas());
//			car.setProperty("tipo", dato.getTipo());
//			car.setProperty("version", dato.getVersion());
//
//			DataStoreClass.datastore.put(car);
//
//		} catch (HttpClientErrorException ex) {
//
//		}
	}

	public List<ServicioIndexAutoData> buscar(final String numeroSerie, final List<ServicioIndexAutoData> cmd) {
		List<ServicioIndexAutoData> respuesta = new LinkedList<>();
		List<Auto> lista=ObjectifyService.ofy().load().type(Auto.class).filter("numeroSerie",numeroSerie).list();
		for(Auto auto:lista){
			ServicioIndexAutoData sia= new ServicioIndexAutoData();
			sia.setNumeroSerie(auto.getNumeroSerie());
			sia.setPlacas(auto.getPlacas());
			sia.setTipo(auto.getTipo());
			respuesta.add(sia);
		}
		
		return respuesta;
		// PaginaIndexAuto indice =
		// factory.getTemplate().getForObject(factory.getRootUlr() +
		// "/index/auto", PaginaIndexAuto.class);
//		List<ServicioIndexAutoData> respuesta = new LinkedList<>();
//		Query query = new Query("Auto");
//		query.addFilter("numeroSerie", FilterOperator.EQUAL, numeroSerie);
//		PreparedQuery pq = DataStoreClass.datastore.prepare(query);
//
//		Entity e = pq.asSingleEntity();
//		if (e != null) {
//			sia.setNumeroSerie((String) e.getProperty("numeroSerie"));
//			sia.setPlacas((String) e.getProperty("placas"));
//			sia.setTipo((String) e.getProperty("tipo"));
//			respuesta.add(sia);
//			return respuesta;
//		}
//		return null;
//		// for(ServicioIndexAutoData x: indice.getItems()) {
//		// if(x.getNumeroSerie().startsWith(StringUtils.upperCase(numeroSerie)))
//		// {
//		// respuesta.add(x);
//		// }
//		// }
	}

	public AutoEntity cargar(String numeroSerie) {
		if (StringUtils.isEmpty(numeroSerie)) {
			return null;
		}
		List<AutoEntity> lista=ObjectifyService.ofy().load().type(AutoEntity.class).filter("numeroSerie = ",numeroSerie).list();
		for(AutoEntity auto:lista){
			return auto;
		}
		return null;
	}

	public List<ServicioIndexAutoData> getIndiceAutos(){
		List<ServicioIndexAutoData> respuesta = new LinkedList<>();
		List<AutoEntity> lista=ObjectifyService.ofy().load().type(AutoEntity.class).list();
		for(AutoEntity auto:lista){
			ServicioIndexAutoData sia= new ServicioIndexAutoData();
			sia.setNumeroSerie(auto.getNumeroSerie());
			sia.setPlacas(auto.getPlacas());
			sia.setTipo(auto.getTipo());
			respuesta.add(sia);
		}
		
		return respuesta;
	}

	@Override
	public AutoEntity cargar(long id) {
		// TODO Auto-generated method stub
		return ObjectifyService.ofy().load().type(AutoEntity.class).id(id).now();
	}

	@Override
	public List<AutoEntity> buscar(String numeroSerie) {
		List<AutoEntity> lista= ObjectifyService.ofy().load().type(AutoEntity.class).list();
		List<AutoEntity> result= new ArrayList<AutoEntity>();
		for(AutoEntity auto:lista){
			if(auto.getNumeroSerie()!=null){
			if(auto.getNumeroSerie().contains(numeroSerie)){
				result.add(auto);
			}
			}
		}
		
		return result;
	}

	@Override
	public List<AutoEntity> buscarPlacas(String numeroSerie) {
		List<AutoEntity> lista= ObjectifyService.ofy().load().type(AutoEntity.class).list();
		List<AutoEntity> result= new ArrayList<AutoEntity>();
		for(AutoEntity auto:lista){
			if(auto.getPlacas().toLowerCase().contains(numeroSerie.toLowerCase())){
				result.add(auto);
			}
		}
		
		return result;
	}
}
