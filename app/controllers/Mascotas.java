package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import helpers.ControllerHelper;
import models.Cliente;
import models.Mascota;
import play.data.Form;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

public class Mascotas extends Controller  {

	/**
	 * Action method para GET /mascotas/<pag>?especie="x".
	 * Opcionalmente se puede pasar el parámetro size para indicar el tamaño de página.
	 * Si no se pasa tamaño de página, se usará 10
	 * 
	 * @param pag número de página a recuperar.
	 */
	public Result indexByEspecie(Integer pag) {
		Result res;
		String paginaSize = request().getQueryString("size");
		String especie = request().getQueryString("especie");

		if (paginaSize == null) {
			paginaSize = "10";
		}

		if (especie != null) {
			List<Mascota> lista = Mascota.findPagina(pag, Integer.valueOf(paginaSize), especie);
			Integer count = Mascota.find.findRowCount();
			Integer members = lista.size();

			if (ControllerHelper.acceptsJson(request())) {
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("count", count);
				result.put("members", members);
				result.put("pagina", lista);
				
				res = ok(Json.toJson(result));
				
			} else if (ControllerHelper.acceptsXml(request())) {
				res = ok(views.xml.mascotas.render(lista, count, members));
			} else {
				res = badRequest(ControllerHelper.errorJson(1, "unsupported_format", null));
			}
			return res;
		} else {
			return Results.status(BAD_REQUEST, Messages.get("unsupported_format_especie"));
		}
	}
	
	/**
	 * Action method para GET /cliente/<uId>/mascotas
	 * 
	 * @param uId identificador del cliente
	 */
	public Result index(Long uId) {
		Result res;
		Cliente cliente = Cliente.find.byId(uId);

		if (cliente == null) {
			return notFound();
		}

		if (ControllerHelper.acceptsJson(request())) {
			res = ok(Json.toJson(cliente.getMascotas()));
		} else if (ControllerHelper.acceptsXml(request())) {
			res = ok(views.xml.mascotas.render(cliente.getMascotas(), null, null));
		} else {
			res = badRequest(ControllerHelper.errorJson(1, "unsupported_format", null));
		}

		return res;
	}
	
	/**
	 * Action method para GET /mascota/code/<codeMascota>
	 * 
	 * @param codeMascota código identificativo de la mascota a borrar
	 */
	public Result retrieveByCodigoMascota(String codeMascota) {
		Result res;
		Mascota mascota = Mascota.findByUniqueCodigo(codeMascota);

		if (ControllerHelper.acceptsJson(request())) {
			res = ok(Json.toJson(mascota));
		} else if (ControllerHelper.acceptsXml(request())) {
			res = ok(views.xml._mascota.render(mascota));
		} else {
			res = badRequest(ControllerHelper.errorJson(1, "unsupported_format", null));
		}

		return res;
	}
	
	/**
	 * Action method para POST /cliente/<uId>/mascotas
	 * Se deben pasar los atributos de la mascota en el body de la petición. 
	 * 
	 * @param uId identificador del cliente
	 */
	public Result createById(Long uId) {
		Form<Mascota> form = Form.form(Mascota.class).bindFromRequest();

		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(2, "invalid_mascota", form.errorsAsJson()));
		}

		Cliente cliente = Cliente.find.byId(uId);
		return create(cliente, form);
	}
	
	/**
	 * Action method para POST /cliente/code/<codeCliente>/mascotas
	 * Se deben pasar los atributos de la mascota en el body de la petición. 
	 * 
	 * @param codeCliente código identificativo del cliente
	 */
	public Result createByCodeCliente(String codeCliente) {
		Form<Mascota> form = Form.form(Mascota.class).bindFromRequest();

		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(2, "invalid_mascota", form.errorsAsJson()));
		}

		Cliente cliente = Cliente.findByUniqueCodigo(codeCliente);
		return create(cliente, form);
	}
	
	public Result create(Cliente cliente, Form<Mascota> form){
		Result res;
		if (cliente == null) {
			return notFound();
		}

		Mascota futuraMascota = form.get();
		Mascota mascota = Mascota.findByUniqueCodigo(futuraMascota.getCodigoMascota());

		if (mascota == null) {
			futuraMascota.setCliente(cliente);
			futuraMascota.save();
			res = created();
		} else {
			res = Results.status(CONFLICT, Messages.get("unsupported_create_mascota"));
		}
		return res;
	}
	
	/**
	 * Action method para PUT /cliente/<uId>/mascotas/<tId> 
	 * Se deben pasar los atributos a modificar en el body de la petición.
	 * 
	 * @param uId: identificador del cliente
	 * @param tId: identificador de la mascota a modificar
	 */
	public Result updateById(Long uId, Long tId) {
		Form<Mascota> form = Form.form(Mascota.class).bindFromRequest();

		Mascota mascota = Mascota.find.byId(tId);
		if (mascota == null) {
			return notFound();
		}
		if (!mascota.getCliente().getId().equals(uId)) {
			return badRequest(ControllerHelper.errorJson(2, "invalid_mascota", null));
		}
		return update(mascota, form);
	}
	
	/**
	 * Action method para PUT /mascota/code/<codeMascota> 
	 * Se deben pasar los atributos a modificar en el body de la petición.
	 * 
	 * @param codeMascota código identificativo de la mascota a modificar
	 */
	public Result updateByCodeMascota(String codeMascota) {
		Form<Mascota> form = Form.form(Mascota.class).bindFromRequest();

		Mascota mascota = Mascota.findByUniqueCodigo(codeMascota);
		if (mascota == null) {
			return notFound();
		}
		return update(mascota, form);
	}
	
	public Result update(Mascota mascota, Form<Mascota> form){
		Result res;
		Cliente cliente = mascota.getCliente();
		Mascota mascotaForm = form.get();

		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(1, "invalid_mascota", form.errorsAsJson()));
		}

		Mascota mascotaBusqueda = Mascota.findByUniqueCodigo(mascotaForm.getCodigoMascota());

		if (mascotaBusqueda == null || mascota.getCodigoMascota().equals(mascotaBusqueda.getCodigoMascota())) {
			if (mascota.changeData(form.get())) {
				mascota.setCliente(cliente);
				mascota.save();
				res = ok();
			} else {
				res = status(NOT_MODIFIED);
			}
		} else {
			res = Results.status(CONFLICT, Messages.get("unsupported_create_mascota"));
		}

		return res;		
	}
	
	/**
	 * Action method para DELETE /cliente/<uId>/mascotas/<tId>
	 * 
	 * @param uId identificador del cliente
	 * @param tId identificador de la mascota a borrar
	 */
	public Result deleteById(Long uId, Long tId) {
		Mascota mascota = Mascota.find.byId(tId);	
		if (mascota == null) {
			return notFound();
		}
		if (!mascota.getCliente().getId().equals(uId)) {
			return badRequest(ControllerHelper.errorJson(2, "invalid_mascota", null));
		}
		return delete(mascota);
	}
	
	/**
	 * Action method para DELETE /mascota/code/<codeMascota>/mascotas
	 * 
	 * @param codeMascota código identificativo de la mascota a borrar
	 */
	public Result deleteByCodeMascota(String codeMascota) {
		Mascota mascota = Mascota.findByUniqueCodigo(codeMascota);
		if (mascota == null) {
			return notFound();
		}
		return delete(mascota);
	}

	public Result delete(Mascota mascota) {
		mascota.delete();
		return ok();
	}
}
