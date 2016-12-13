package com.tikal.tallerWeb.data.access;

import java.util.List;

import com.tikal.tallerWeb.modelo.entity.CotizacionEntity;

public interface CotizacionDAO {

	public boolean guarda(CotizacionEntity c);
	public boolean guarda(List<CotizacionEntity> lista);
	public List<CotizacionEntity> consultar(String tipo, int modelo);
}
