package com.tikal.tallerWeb.control.restControllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.googlecode.objectify.ObjectifyService;
import com.tikal.tallerWeb.data.access.AutoDAO;
import com.tikal.tallerWeb.data.access.BitacoraDAO;
import com.tikal.tallerWeb.data.access.ClienteDAO;
import com.tikal.tallerWeb.data.access.ServicioDAO;
import com.tikal.tallerWeb.modelo.entity.AutoEntity;
import com.tikal.tallerWeb.modelo.entity.ClienteEntity;
import com.tikal.tallerWeb.modelo.entity.EventoEntity;
import com.tikal.tallerWeb.modelo.entity.ServicioEntity;
import com.tikal.tallerWeb.rest.util.NewServiceObject;
import com.tikal.tallerWeb.server.BlobServicio;
import com.tikal.tallerWeb.util.JsonConvertidor;
import com.tikal.tallerWeb.util.UploadUrl;

import technology.tikal.taller.automotriz.model.index.servicio.ServicioIndex;
import technology.tikal.taller.automotriz.model.servicio.bitacora.Evidencia;

@Controller
@RequestMapping(value = { "/servicio" })
public class ServicioControl {

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	@Autowired
	ServicioDAO servdao;
	@Autowired
	AutoDAO autodao;
	@Autowired
	ClienteDAO clientedao;

	@Autowired
	BitacoraDAO bitacora;

	@Autowired
	ServletContext context;

	public ServicioControl() {

	}

	@RequestMapping(value = {
			"/add" }, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void add(HttpServletRequest request, HttpServletResponse response, @RequestBody String json)
			throws IOException {
		NewServiceObject s = (NewServiceObject) JsonConvertidor.fromJson(json, NewServiceObject.class);
		AutoEntity auto = s.getAuto();
		AutoEntity a = (AutoEntity) autodao.cargar(s.getAuto().getNumeroSerie());
		if (a == null) {
			autodao.guardar(s.getAuto());
		} else {
			s.setAuto(a);
		}
		ClienteEntity c = clientedao.buscarCliente(s.getCliente().getNombre());
		if (c == null) {
			clientedao.guardar(s.getCliente());
		} else {
			s.setCliente(c);
		}
		servdao.guardar(s.getServicio());
		ServicioEntity ser = s.getServicio();
		ser.setIdAuto(Long.toString(s.getAuto().getIdAuto()));
		ser.setIdCliente(s.getCliente().getIdCliente());
		servdao.guardar(ser);
		// List<AutoEntity> ae =
		// ObjectifyService.ofy().load().type(AutoEntity.class)
		// .filter("numeroSerie", s.getAuto().getNumeroSerie()).list();
		// if (ae.size() == 0) {
		// a = new AutoEntity(s.getAuto());
		// ObjectifyService.ofy().save().entities(a).now();
		// }

		response.getWriter().write(JsonConvertidor.toJson(s));
	}

	@RequestMapping(value = {
			"/update" }, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void update(HttpServletRequest request, HttpServletResponse response, @RequestBody String json)
			throws IOException {
		NewServiceObject s = (NewServiceObject) JsonConvertidor.fromJson(json, NewServiceObject.class);

		AutoEntity a = new AutoEntity();
		ServicioEntity ser = new ServicioEntity();
		EventoEntity ev = new EventoEntity();

		technology.tikal.taller.automotriz.model.servicio.Servicio serv = new technology.tikal.taller.automotriz.model.servicio.Servicio();
		technology.tikal.taller.automotriz.model.index.servicio.ServicioIndexAutoData si = new technology.tikal.taller.automotriz.model.index.servicio.ServicioIndexAutoData();
		List<AutoEntity> ae = ObjectifyService.ofy().load().type(AutoEntity.class)
				.filter("numeroSerie", s.getAuto().getNumeroSerie()).list();
		if (ae.size() == 0) {
			a = s.getAuto();
			ObjectifyService.ofy().save().entities(a).now();
		}
		System.out.println(json);
		response.getWriter().write(JsonConvertidor.toJson(a));
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = "application/json")
	public void handleFileUpload(HttpServletRequest req,
			HttpServletResponse res /*
									 * ,
									 * 
									 * @RequestParam("file") MultipartFile file
									 */) throws IOException {
		String a = "empieza el show";
		System.out.println(a);
		@SuppressWarnings("deprecation")
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
		int len = Integer.parseInt(req.getParameter("length"));
		List<BlobKey> lista = new ArrayList<BlobKey>();
		long id = Long.parseLong(req.getParameter("idEvento"));
		EventoEntity evento = bitacora.cargarEvento(id);
		if (evento.getEvidencia() == null) {
			evento.setEvidencia(new ArrayList<Evidencia>());
		}
		for (int i = 0; i < len; i++) {
			BlobKey blobKey = blobs.get("files" + i);
			lista.add(blobKey);
			Evidencia e = new Evidencia();
			e.setImage(blobKey.getKeyString());
			evento.getEvidencia().add(e);
		}
		bitacora.agregar(evento.getId(), evento);

		// System.out.println("Key: "+ blobKey.toString());
		res.getWriter().println(JsonConvertidor.toJson(evento));

		// InputStream istr=file.getInputStream();
		// ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		// Streams.copy(istr, bytes, true /* close stream after copy */);
		// Blob blob = new Blob(bytes.toByteArray());
		//
		// if (!file.isEmpty()) {
		// try {
		// // String uploadsDir = "/uploads/";
		// // String realPathtoUploads =
		// // request.getContextPath()+"/uploads/";
		// // if(! new File(realPathtoUploads).exists())
		// // {
		// // new File(realPathtoUploads).mkdir();
		// // }
		// //
		// //
		// //
		// // String orgName = file.getOriginalFilename();
		// // String filePath = realPathtoUploads + orgName;
		// // File dest = new File(filePath);
		// // file.transferTo(dest);
		// } catch (Exception e) {
		// }
		// }
	}

	@RequestMapping(value = "/findCar/{numSerie}", method = RequestMethod.GET, produces = "application/json")
	public void findCar(HttpServletResponse resp, HttpServletRequest req, @PathVariable String numSerie)
			throws IOException {
		AutoEntity car = autodao.cargar(numSerie);
		resp.getWriter().println(JsonConvertidor.toJson(car));
	}

	@RequestMapping(value = "/image/{blobid}", method = RequestMethod.GET)
	public void getImageAsByteArray(HttpServletResponse resp, HttpServletRequest req, @PathVariable String blobid)
			throws IOException {
		BlobKey blobKey = new BlobKey(blobid);
		blobstoreService.serve(blobKey, resp);

	}

	@RequestMapping(value = "/findServicio/{blobid}", method = RequestMethod.GET)
	public void getServicio(HttpServletResponse resp, HttpServletRequest req, @PathVariable String blobid)
			throws IOException {
		NewServiceObject servicio = new NewServiceObject();
		servicio.setServicio(servdao.cargar(Long.parseLong(blobid)));
		if (servicio.getServicio() != null) {
			servicio.setAuto(autodao.cargar(Long.parseLong(servicio.getServicio().getIdAuto())));
			servicio.setCliente(clientedao.cargar(servicio.getServicio().getIdCliente()));
			resp.getWriter().println(JsonConvertidor.toJson(servicio));
		}
	}

	@RequestMapping(value = "/getUpldUrl", method = RequestMethod.GET)
	public void getUploadUrl(HttpServletResponse resp, HttpServletRequest req) throws IOException {
		UploadUrl ur = new UploadUrl();
		ur.setUrl(BlobServicio.urlUpld);
		resp.getWriter().println(JsonConvertidor.toJson(ur));

	}

	@RequestMapping(value = "/serviciosHoy", method = RequestMethod.GET)
	public void getHoy(HttpServletResponse resp, HttpServletRequest req) throws IOException {
		List<ServicioEntity> a = servdao.getByDate(new DateTime(), new DateTime());
		List<NewServiceObject> ret = new ArrayList<NewServiceObject>();

		for (ServicioEntity s : a) {
			NewServiceObject servicio = new NewServiceObject();
			servicio.setServicio(s);
			
			servicio.setAuto(autodao.cargar(Long.parseLong(servicio.getServicio().getIdAuto())));
			servicio.setCliente(clientedao.cargar(servicio.getServicio().getIdCliente()));
			ret.add(servicio);
		}
		resp.getWriter().println(JsonConvertidor.toJson(ret));
	}

	@RequestMapping(value = "/status/{status}", method = RequestMethod.GET)
	public void getStatus(HttpServletResponse resp, HttpServletRequest req, @PathVariable String status) throws IOException {
		List<ServicioIndex> lista=servdao.getIndiceServiciosPorStatus(status);
		
	
		resp.getWriter().println(JsonConvertidor.toJson(lista));
	}
}
