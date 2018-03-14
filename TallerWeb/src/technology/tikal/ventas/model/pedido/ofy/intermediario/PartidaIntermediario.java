/**
 *   Copyright 2015 Tikal-Technology
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
package technology.tikal.ventas.model.pedido.ofy.intermediario;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.googlecode.objectify.annotation.Subclass;

import technology.tikal.ventas.model.pedido.Pedido;
import technology.tikal.ventas.model.pedido.ofy.PartidaOfy;

/**
 * 
 * @author Nekorp
 *
 */
@JsonTypeName("PartidaIntermediario")
@Subclass(index=true)
public class PartidaIntermediario extends PartidaOfy {

    protected PartidaIntermediario() {
        super();
    }
    
    public PartidaIntermediario(PartidaIntermediario base) {
        super(base);
    }

    public PartidaIntermediario(Pedido owner) {
        super(owner);
    }
}
