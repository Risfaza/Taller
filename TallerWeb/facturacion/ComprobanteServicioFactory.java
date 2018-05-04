package com.tikal.tallerWeb.facturacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tikal.cacao.sat.cfd.Comprobante;
import com.tikal.tallerWeb.data.access.ClienteDAO;
import com.tikal.tallerWeb.data.access.CostoDAO;
import com.tikal.tallerWeb.data.access.ServicioDAO;
import com.tikal.tallerWeb.modelo.entity.ServicioEntity;
import com.tikal.cacao.sat.cfd.Comprobante.Emisor;
import com.tikal.cacao.sat.cfd.Comprobante.Emisor.RegimenFiscal;

@Service
public class ComprobanteServicioFactory {
	
	@Autowired
	private CostoDAO costoDAO;
	
	@Autowired
	private ServicioDAO servicioDAO;
	
	@Autowired
	private ClienteDAO clienteDAO;

	public Comprobante construirCFDI(Long idServicio) {
		return null;
	}
	
	
	private Comprobante init() {
		Comprobante c = new Comprobante();
		c.setVersion("3.2");
		c.setTipoDeComprobante("ingreso");
		c.setMoneda("MXN");
		
		return c;
	}
	
	private Emisor construirEmisor() {
		Emisor emisor = new Emisor();
		emisor.setRfc("AAA010101AAA");
		emisor.setNombre("Emisor de Prueba");
		RegimenFiscal regimenFiscal = new RegimenFiscal();
		regimenFiscal.setRegimen("General de Ley Personas Morales");
		emisor.getRegimenFiscal().add(regimenFiscal);
		return emisor;
	}
}
