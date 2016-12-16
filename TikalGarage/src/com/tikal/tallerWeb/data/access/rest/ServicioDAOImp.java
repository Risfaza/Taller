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

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.googlecode.objectify.ObjectifyService;
import com.tikal.tallerWeb.data.access.ServicioDAO;
import com.tikal.tallerWeb.modelo.entity.PresupuestoEntity;
import com.tikal.tallerWeb.modelo.entity.ServicioEntity;
import com.tikal.tallerWeb.rest.util.AsyncRestCall;
import com.tikal.tallerWeb.rest.util.Callback;
//import com.tikal.tallerWeb.rest.util.RestTemplateFactory;

import technology.tikal.taller.automotriz.model.index.servicio.ServicioIndex;
import technology.tikal.taller.automotriz.model.servicio.metadata.ServicioMetadata;
import technology.tikal.taller.automotriz.model.servicio.moneda.Moneda;

/**
 * @author Nekorp
 */
@Service
public class ServicioDAOImp implements ServicioDAO {

	// @Autowired
	// @Qualifier("taller-RestTemplateFactory")
	// private RestTemplateFactory factory;
	//
	@Override
	public void guardar(ServicioEntity dato) {
		Date d = new Date();

		if (dato.getMetadata() == null) {
			ServicioMetadata sm = new ServicioMetadata();
			sm.setFechaInicio(d);
			sm.setStatus("Diagnositco");
			dato.setMetadata(sm);
		}
		ObjectifyService.ofy().save().entity(dato).now();
		if (dato.getId() == null) {
			// URI resource =
			// factory.getTemplate().postForLocation(factory.getRootUlr() +
			// "/servicios", dato);
			// String[] uri = StringUtils.split(resource.toString(), '/');
			// String id = uri[uri.length - 1];
			// dato.setId(Long.valueOf(id));
			// dato.setId(Long.valueOf(ObjectifyService.ofy().load().type(Servicio.class).list().size()-1));
		} else {
			// Map<String, Object> map = new HashMap<>();
			// map.put("id", dato.getId());
			// factory.getTemplate().postForLocation(factory.getRootUlr() +
			// "/servicios/{id}", dato, map);
		}

	}

	@Override
	public ServicioEntity cargar(Long id) {
		return ObjectifyService.ofy().load().type(ServicioEntity.class).id(id).now();
	}

	@Override
	public List<ServicioIndex> getIndiceServicios() {
		return ObjectifyService.ofy().load().type(ServicioIndex.class).list();
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
		// PaginaServicioIndex r =
		// factory.getTemplate().getForObject(factory.getRootUlr() +
		// "/index/servicio?sinceId={idServicio}", PaginaServicioIndex.class,
		// map);
		return null;
	}

	@Override
	public List<ServicioIndex> getIndiceServiciosMismoAuto(String numeroSerie) {
		Map<String, Object> map = new HashMap<>();
		map.put("numeroSerieAuto", numeroSerie);
		List<ServicioIndex> servicios = ObjectifyService.ofy().load().type(ServicioIndex.class)
				.filter("idAuto", numeroSerie).list();
		return servicios;
	}

	@Override
	public List<ServicioIndex> getIndiceServiciosPorStatus(String status) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", status);
		List<ServicioEntity> servicios = ObjectifyService.ofy().load().type(ServicioEntity.class).list();
		List<ServicioIndex> ret = new ArrayList<ServicioIndex>();
		for (ServicioEntity s : servicios) {
			System.out.println(s.getMetadata().getStatus());
			if (s.getMetadata().getStatus().compareTo("Finalizado") != 0) {
				ServicioIndex si = new ServicioIndex();
				si.setCobranza(s.getCobranza());
				si.setDescripcion(s.getDescripcion());
				si.setFechaInicio(s.getMetadata().getFechaInicio());
				si.setId(s.getIdServicio());
				si.setIdAuto(s.getIdAuto());
				si.setIdCliente(s.getIdCliente());
				si.setStatus(s.getMetadata().getStatus());
				List<PresupuestoEntity> presupuesto = ofy().load().type(PresupuestoEntity.class)
						.filter("id", si.getId()).list();
				float total = 0;
				for (PresupuestoEntity pr : presupuesto) {
					total += Float.parseFloat(pr.getPrecioCliente().getValue()) * pr.getCantidad();
				}
				si.setCostoTotal(new Moneda());
				si.getCostoTotal().setValue(total + "");
				ret.add(si);
			}
		}
		// PaginaServicioIndex r =
		// factory.getTemplate().getForObject(factory.getRootUlr() +
		// "/index/servicio?statusServicio={status}", PaginaServicioIndex.class,
		// map);
		// return r.getItems();
		return ret;
	}

	@Override
	public List<ServicioEntity> getByDate(DateTime fechaInicial, DateTime fechaFinal) {

		List<ServicioEntity> servicios = ObjectifyService.ofy().load().type(ServicioEntity.class).list();
		List<ServicioEntity> res = new ArrayList<ServicioEntity>();
		Date fi = fechaInicial.toDate();
		Date ff = fechaFinal.toDate();
		for (ServicioEntity servicio : servicios) {
			if (servicio.getMetadata().getFechaInicio().getDate() >= fi.getDate()
					&& servicio.getMetadata().getFechaInicio().getMonth() >= fi.getMonth()
					&& servicio.getMetadata().getFechaInicio().getYear() >= fi.getYear()
					&& servicio.getMetadata().getFechaInicio().getDate() <= ff.getDate()
					&& servicio.getMetadata().getFechaInicio().getMonth() <= ff.getMonth()
					&& servicio.getMetadata().getFechaInicio().getYear() <= ff.getYear()) {
				res.add(servicio);
			}
		}
		return res;
	}

	@Override
	public List<ServicioIndex> getServiciosHoy() {
		// TODO Auto-generated method stub
		return null;
	}

}
