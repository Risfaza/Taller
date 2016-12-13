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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tikal.tallerWeb.control.restControllers.VO.GruposCosto;
import com.tikal.tallerWeb.data.access.CostoDAO;
//import com.tikal.tallerWeb.rest.util.RestTemplateFactory;
import com.tikal.tallerWeb.modelo.entity.PresupuestoEntity;

/**
 * @author Nekorp
 */
@Service
public class CostoDAOImp implements CostoDAO {

//    @Autowired
//    @Qualifier("taller-RestTemplateFactory")
//    private RestTemplateFactory factory;
    
    @Override
    public List<GruposCosto> cargar(Long idServicio) {
    	List<PresupuestoEntity> lista= ofy().load().type(PresupuestoEntity.class).list();
    	Map<String,List<PresupuestoEntity>> mapa= new HashMap<String,List<PresupuestoEntity>>();
    	
    	List<GruposCosto> res= new ArrayList<GruposCosto>();
    	for(PresupuestoEntity p: lista){
    		if(p.getId()==null){
    			ofy().delete().entity(p).now();
    			continue;
    		}
    		if(p.getId().compareTo(idServicio)==0){
    			if(mapa.get(p.getGrupo())!=null){
    				mapa.get(p.getGrupo()).add(p);
    			}
    			else{
    				List<PresupuestoEntity> l= new ArrayList<PresupuestoEntity>();
    				l.add(p);
    				mapa.put(p.getGrupo(), l);
    			}
    		}
    	}
    	List<GruposCosto> grupos= new ArrayList<GruposCosto>();
    	for (String key : mapa.keySet()) {
    		GruposCosto grupo= new GruposCosto();
    		grupo.setNombre(key);
    		grupo.setPresupuestos(mapa.get(key));
    		grupo.setTipo(grupo.getPresupuestos().get(0).getTipo());
    		grupos.add(grupo);
    	}
        return grupos;
    }

    @Override
    public List<PresupuestoEntity> guardar(Long idServicio, List<PresupuestoEntity> datos) {
    	ofy().save().entities(datos).now();
//        RegistroCosto[] r = factory.getTemplate().postForObject(factory.getRootUlr() + "/servicios/{idServicio}/costo", datos, RegistroCosto[].class, map);
//        return Arrays.asList(r);
        return datos;
    }

}
