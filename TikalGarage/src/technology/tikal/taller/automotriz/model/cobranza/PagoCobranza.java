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
package technology.tikal.taller.automotriz.model.cobranza;

import java.util.Date;

import technology.tikal.taller.automotriz.model.servicio.moneda.Moneda;

/**
 *
 * @author Nekorp
 */
public class PagoCobranza {
    private Date fecha;
    private String responsable;
    private boolean facturado;
    private String numeroFactura;
    private String tipo;
    private String otroTipo;
    private String detalle;
    private Moneda monto;
    private String uuid;
    public PagoCobranza() {
        fecha = new Date();
        responsable = "";
        facturado = false;
        numeroFactura = "";
        tipo = "Efectivo";
        otroTipo = "";
        detalle = "";
        monto = new Moneda();
    }
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Moneda getMonto() {
        return monto;
    }

    public void setMonto(Moneda monto) {
        this.monto = monto;
    }
    public boolean isFacturado() {
        return facturado;
    }
    public void setFacturado(boolean facturado) {
        this.facturado = facturado;
    }
    public String getNumeroFactura() {
        return numeroFactura;
    }
    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getOtroTipo() {
        return otroTipo;
    }
    public void setOtroTipo(String otroTipo) {
        this.otroTipo = otroTipo;
    }
    public void setUuid(String uuid){
        this.uuid=uuid;
    }
    public String getUuid(){
        return this.uuid;
    }
}
