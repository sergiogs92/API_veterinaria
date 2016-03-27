package controllers;

import helpers.ControllerHelper;
import models.Enfermedad;
import models.Historial;
import models.Mascota;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Enfermedades extends Controller  {
	/**
	 * Action method para GET /historial/<uId>/enfermedades
	 * 
	 * @param uId identificador del historial
	 */
	public Result index(Long uId) {
		Result res;

		Historial historial = Historial.find.byId(uId);
		if (historial == null) {
			return notFound();
		}

		if (ControllerHelper.acceptsJson(request())) {
			res = ok(Json.toJson(historial.getEnfermedades()));
		} else if (ControllerHelper.acceptsXml(request())) {
			res = ok(views.xml.enfermedades.render(historial.getEnfermedades()));
		} else {
			res = badRequest(ControllerHelper.errorJson(1, "unsupported_format", null));
		}

		return res;
	}
	
	/**
	 * Action method para POST /historial/<uId>/enfermedades
	 * Se deben pasar los atributos de la enfermedad en el body de la petición. 
	 * 
	 * @param uId identificador del historial
	 */
	public Result createById(Long uId) {
		Form<Enfermedad> form = Form.form(Enfermedad.class).bindFromRequest();

		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(2, "invalid_enfermedad", form.errorsAsJson()));
		}
		
		Historial historial = Historial.find.byId(uId);		
		if (historial == null) {
			return notFound();
		}

		Enfermedad enfermedad = form.get();
		enfermedad.setHistorial(historial);
		enfermedad.save();
		
		return created();
	}
	
	/**
	 * Action method para POST /mascota/code/<codeMascota>/enfermedades
	 * Se deben pasar los atributos de la enfermedad en el body de la petición. 
	 * 
	 * @param codeMascota código identificativo de la mascota
	 */
	public Result createByCodeMascota(String codeMascota) {
		Form<Enfermedad> form = Form.form(Enfermedad.class).bindFromRequest();

		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(2, "invalid_enfermedad", form.errorsAsJson()));
		}
		Mascota mascota = Mascota.findByUniqueCodigo(codeMascota);
		Historial historial = mascota.getHistorial();		
		if (historial == null) {
			return notFound();
		}

		Enfermedad enfermedad = form.get();
		enfermedad.setHistorial(historial);
		enfermedad.save();
		
		return created();
	}
	
	/**
	 * Action method para PUT /historial/<uId>/enfermedades/<tId> Se deben pasar los
	 * atributos a modificar en el body de la petición.
	 * 
	 * @param uId: identificador del historial
	 * @param tId: identificador de la enfermedad a modificar
	 */
	public Result update(Long uId, Long tId) {
		Result res;
		Form<Enfermedad> form = Form.form(Enfermedad.class).bindFromRequest();

		Enfermedad enfermedad = Enfermedad.find.byId(tId);
		if (enfermedad == null) {
			return notFound();
		}

		if (!enfermedad.getHistorial().getId().equals(uId)) {
			return badRequest(ControllerHelper.errorJson(2, "invalid_enfermedad", null));
		}

		Historial historial = enfermedad.getHistorial();

		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(1, "invalid_enfermedad", form.errorsAsJson()));
		}

		if (enfermedad.changeData(form.get())) {
			enfermedad.setHistorial(historial);
			enfermedad.save();
			res = ok();
		} else {
			res = status(NOT_MODIFIED);
		}

		return res;
	}

	/**
	 * Action method para DELETE /historial/<uId>/enfermedades/<tId>
	 * 
	 * @param uId identificador del historial
	 * @param tId identificador de la enfermedad a borrar
	 */
	public Result delete(Long uId, Long tId) {
		Enfermedad enfermedad = Enfermedad.find.byId(tId);
		if (enfermedad == null) {
			return notFound();
		}

		if (!enfermedad.getHistorial().getId().equals(uId)) {
			return badRequest(ControllerHelper.errorJson(2, "invalid_enfermedad", null));
		}

		enfermedad.delete();

		return ok();
	}
}
