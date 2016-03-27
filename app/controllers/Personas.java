package controllers;

import models.Cliente;
import models.Persona;
import helpers.ControllerHelper;
import play.data.Form;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

public class Personas extends Controller {
	/**
	 * Action method para GET /cliente/<uId>/personas
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
			res = ok(Json.toJson(cliente.getPersonas()));
		} else if (ControllerHelper.acceptsXml(request())) {
			res = ok(views.xml.personas.render(cliente.getPersonas()));
		} else {
			res = badRequest(ControllerHelper.errorJson(1, "unsupported_format", null));
		}

		return res;
	}
	
	/**
	 * Action method para GET /persona/dni/<dniPersona>
	 * 
	 * @param dniPersona código identificativo de la persona
	 */
	public Result retrieveByDni(String dniPersona) {
		Result res;
		Persona persona = Persona.findByUniqueDni(dniPersona);

		if (ControllerHelper.acceptsJson(request())) {
			res = ok(Json.toJson(persona.getCliente()));
		} else if (ControllerHelper.acceptsXml(request())) {
			res = ok(views.xml._cliente.render(persona.getCliente()));
		} else {
			res = badRequest(ControllerHelper.errorJson(1, "unsupported_format", null));
		}

		return res;
	}
	
	/**
	 * Action method para POST /cliente/<uId>/personas
	 * Se deben pasar los atributos de la persona en el body de la petición. 
	 * 
	 * @param uId identificador del cliente
	 */
	public Result createById(Long uId) {
		Form<Persona> form = Form.form(Persona.class).bindFromRequest();

		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(2, "invalid_persona", form.errorsAsJson()));
		}
		
		Cliente cliente = Cliente.find.byId(uId);		
		return create(cliente, form);
	}

	
	/**
	 * Action method para POST /cliente/code/<codeCliente>/personas
	 * Se deben pasar los atributos de la persona en el body de la petición. 
	 * 
	 * @param codeCliente código identificativo del cliente
	 */
	public Result createByCodeCliente(String codeCliente) {
		Form<Persona> form = Form.form(Persona.class).bindFromRequest();

		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(2, "invalid_persona", form.errorsAsJson()));
		}

		Cliente cliente = Cliente.findByUniqueCodigo(codeCliente);
		return create(cliente, form);
	}

	public Result create(Cliente cliente, Form<Persona> form) {
		if (cliente == null) {
			return notFound();
		}

		Persona futuraPersona = form.get();
		Persona persona = Persona.findByUniqueDni(futuraPersona.getDni());

		if (persona == null) {
			futuraPersona.setCliente(cliente);
			futuraPersona.save();
			return created();
		} else {
			return Results.status(CONFLICT, Messages.get("unsupported_create_persona"));
		}
	}

	
	/**
	 * Action method para PUT /cliente/<uId>/personas/<tId> 
	 * Se deben pasar los atributos a modificar en el body de la petición.
	 * 
	 * @param uId: identificador del cliente
	 * @param tId: identificador de la persona a modificar
	 */
	public Result updateById(Long uId, Long tId) {
		Form<Persona> form = Form.form(Persona.class).bindFromRequest();

		Persona persona = Persona.find.byId(tId);
		if (persona == null) {
			return notFound();
		}

		if (!persona.getCliente().getId().equals(uId)) {
			return badRequest(ControllerHelper.errorJson(2, "invalid_persona", null));
		}
		return update(persona, form);
	}
	
	/**
	 * Action method para PUT /persona/dni/<dniPersona>
	 * Se deben pasar los atributos a modificar en el body de la petición.
	 * 
	 * @param uId: identificador del cliente
	 * @param tId: identificador de la persona a modificar
	 */
	public Result updateByDni(String dniPersona) {
		Form<Persona> form = Form.form(Persona.class).bindFromRequest();

		Persona persona = Persona.findByUniqueDni(dniPersona);
		if (persona == null) {
			return notFound();
		}
		return update(persona, form);
	}

	public Result update(Persona persona, Form<Persona> form) {
		Result res;
		Cliente cliente = persona.getCliente();
		Persona personaForm = form.get();

		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(1, "invalid_persona", form.errorsAsJson()));
		}

		Persona personaBusqueda = Persona.findByUniqueDni(personaForm.getDni());

		if (personaBusqueda == null || persona.getDni().equals(personaForm.getDni())) {
			if (persona.changeData(form.get())) {
				persona.setCliente(cliente);
				persona.save();
				res = ok();
			} else {
				res = status(NOT_MODIFIED);
			}
		} else {
			res = Results.status(CONFLICT, Messages.get("unsupported_create_persona"));
		}
		return res;
	}
	
	/**
	 * Action method para DELETE /cliente/<uId>/personas/<tId>
	 * 
	 * @param uId identificador del cliente
	 * @param tId identificador de la persona a borrar
	 */
	public Result deleteById(Long uId, Long tId) {
		Persona persona = Persona.find.byId(tId);
		if (persona == null) {
			return notFound();
		}
		if (!persona.getCliente().getId().equals(uId)) {
			return badRequest(ControllerHelper.errorJson(2, "invalid_persona", null));
		}
		return delete(persona);
	}
	
	/**
	 * Action method para DELETE /persona/dni/<dniPersona>
	 * 
	 * @param dniPersona código identificativo de la persona a borrar
	 */
	public Result deleteByDni(String dniPersona) {
		Persona persona = Persona.findByUniqueDni(dniPersona);
		if (persona == null) {
			return notFound();
		}
		return delete(persona);
	}

	public Result delete(Persona persona) {
		persona.delete();
		return ok();
	}
}
