package controllers;

import helpers.ControllerHelper;
import play.i18n.Messages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Cliente;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;


public class Clientes extends Controller {
	
	/**
	 * Action method para GET /clientes/<pag>.
	 * Opcionalmente se puede pasar el parámetro size para indicar el tamaño de página.
	 * Si no se pasa tamaño de página, se usará 10
	 * 
	 * @param pag número de página a recuperar.
	 */
	public Result index(Integer pag) {
		Result res;
		String paginaSize = request().getQueryString("size");

		if (paginaSize == null) {
			paginaSize = "10";
		}

		List<Cliente> lista = Cliente.findPagina(pag, Integer.valueOf(paginaSize));
		Integer count = Cliente.find.findRowCount();

		if (ControllerHelper.acceptsJson(request())) {
			Map<String, Object> result = new HashMap<String, Object>();

			result.put("count", count);
			result.put("pagina", lista);

			res = ok(Json.toJson(result));
		} else if (ControllerHelper.acceptsXml(request())) {
			res = ok(views.xml.clientes.render(lista, count));
		} else {
			res = badRequest(ControllerHelper.errorJson(1, "unsupported_format", null));
		}

		return res;
	}
	
	/**
	 * Action method para GET /cliente/code/<codeCliente>.
	 * 
	 * @param codeCliente código identificativo del cliente
	 */
	public Result retrieveByCodigoCliente(String codeCliente){	
		Cliente cliente = Cliente.findByUniqueCodigo(codeCliente);
		return retrieve(cliente);		
	}
	
	/**
	 * Action method para GET /cliente/<uId>
	 * 
	 * @param uId identificador del cliente
	 */
	public Result retrieveById(Long uId) {
		Cliente cliente = Cliente.find.byId(uId);
		return retrieve(cliente);
	}
	
	public Result retrieve(Cliente cliente) {
		Result res;
		if (cliente == null) {
			res = notFound();
		} else {
			if (ControllerHelper.acceptsJson(request())) {
				res = ok(Json.toJson(cliente));
			} else if (ControllerHelper.acceptsXml(request())) {
				res = ok(views.xml._cliente.render(cliente));
			} else {
				res = badRequest(ControllerHelper.errorJson(1, "unsupported_format", null));
			}
		}

		return res;
	}
	
	/**
	 * Action method para POST /cliente
	 * Se deben pasar los atributos del cliente en el body de la petición. 
	 */
	public Result create() {
		Form<Cliente> form = Form.form(Cliente.class).bindFromRequest();

		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(2, "invalid_cliente", form.errorsAsJson()));
		}
		Cliente futuroCliente = form.get();
		Cliente cliente = Cliente.findByUniqueCodigo(futuroCliente.getCodigoCliente());

		if (cliente == null) {
			futuroCliente.save();
			return created();
		} else {
			return Results.status(CONFLICT, Messages.get("unsupported_create_cliente"));
		}
	}

	/**
	 * Action method para PUT /cliente/<uId>
	 * Se deben pasar los atributos a modificar en el body de la petición. 
	 * 
	 * @param uId identificador del cliente a modificar
	 */
	public Result updateById(Long uId) {
		Cliente cliente = Cliente.find.byId(uId);
		return update(cliente);
	}
	
	/**
	 * Action method para PUT /cliente/<codeCliente>
	 * Se deben pasar los atributos a modificar en el body de la petición. 
	 * 
	 * @param codeCliente identificador del cliente a modificar
	 */
	public Result updateByCodeCliente(String codeCliente) {
		Cliente cliente = Cliente.findByUniqueCodigo(codeCliente);
		return update(cliente);
	}
	
	public Result update(Cliente cliente) {
		Result res;
		if (cliente == null) {
			return notFound();
		}

		Form<Cliente> form = Form.form(Cliente.class).bindFromRequest();
		Cliente clienteForm = form.get();

		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(1, "invalid_cliente", form.errorsAsJson()));
		}

		Cliente clienteBusqueda = Cliente.findByUniqueCodigo(clienteForm.getCodigoCliente());

		if (clienteBusqueda == null || cliente.getCodigoCliente().equals(clienteBusqueda.getCodigoCliente())) {
			if (cliente.changeData(form.get())) {
				cliente.save();
				res = ok();
			} else {
				res = status(NOT_MODIFIED);
			}
		} else {
			res = Results.status(CONFLICT, Messages.get("unsupported_create_cliente"));
		}

		return res;
	}
	
	/**
	 * Action method para DELETE /cliente/<uId>
	 * 
	 * @param uId identificador del cliente a borrar
	 */
	public Result deleteById(Long uId) {
		Cliente cliente = Cliente.find.byId(uId);
		if (cliente == null) {
			return notFound();
		}
		if (!cliente.getId().equals(uId)) {
			return badRequest(ControllerHelper.errorJson(2, "invalid_cliente", null));
		}
		return delete(cliente);
	}
	
	/**
	 * Action method para DELETE /cliente/code/<codeCliente>
	 * 
	 * @param codeCliente código identificativo del cliente a borrar
	 */
	public Result deleteByCodeCliente(String codeCliente) {
		Cliente cliente = Cliente.findByUniqueCodigo(codeCliente);
		if (cliente == null) {
			return notFound();
		}
		return delete(cliente);
	}

	public Result delete(Cliente cliente) {
		cliente.delete();
		return ok();
	}
}
