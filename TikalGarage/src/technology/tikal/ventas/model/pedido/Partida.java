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
package technology.tikal.ventas.model.pedido;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import technology.tikal.ventas.model.pedido.ofy.intermediario.PartidaIntermediario;
import technology.tikal.ventas.model.pedido.ofy.intermediario.PartidaIntermediarioTransient;
import technology.tikal.ventas.model.producto.Producto;

/**
 * 
 * @author Nekorp
 *
 */
@JsonSubTypes({
    @JsonSubTypes.Type(value = GrupoPartida.class),
    @JsonSubTypes.Type(value = PartidaIntermediarioTransient.class),
    @JsonSubTypes.Type(value = PartidaIntermediario.class)
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public interface Partida {
    Long getId();
    Long getPedidoId();
    Producto getProducto();
    void setProducto(Producto producto);
    Long getCantidad();
    void setCantidad(Long cantidad);
}
