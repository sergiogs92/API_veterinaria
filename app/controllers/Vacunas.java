package controllers;

import helpers.ControllerHelper;
import models.Historial;
import models.Mascota;
import models.Vacuna;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Vacunas extends Controller {
	/**
	 * Action method para GET /historial/<uId>/vacunas
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
			res = ok(Json.toJson(historial.getVacunas()));
		} else if (ControllerHelper.acceptsXml(request())) {
			res = ok(views.xml.vacunas.render(historial.getVacunas()));
		} else {
			res = badRequest(ControllerHelper.errorJson(1, "unsupported_format", null));
		}

		return res;
	}
	
	/**
	 * Action method para POST /historial/<uId>/vacunas
	 * Se deben pasar los atributos de la vacuna en el body de la petición. 
	 * 
	 * @param uId identificador del historial
	 */
	public Result createById(Long uId) {
		Form<Vacuna> form = Form.form(Vacuna.class).bindFromRequest();

		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(2, "invalid_vacuna", form.errorsAsJson()));
		}

		Historial historial = Historial.find.byId(uId);
		return create(historial, form);
	}
	
	/**
	 * Action method para POST /mascota/code/<codeMascota>/vacunas
	 * Se deben pasar los atributos de la vacuna en el body de la petición. 
	 * 
	 * @param codeMascota código identificativo de la mascota
	 */
	public Result createByCodeMascota(String codeMascota) {
		Form<Vacuna> form = Form.form(Vacuna.class).bindFromRequest();

		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(2, "invalid_vacuna", form.errorsAsJson()));
		}

		Mascota mascota = Mascota.findByUniqueCodigo(codeMascota);
		return create(mascota.getHistorial(), form);
	}

	public Result create(Historial historial, Form<Vacuna> form) {
		if (historial == null) {
			return notFound();
		}

		Vacuna vacuna = form.get();
		vacuna.setHistorial(historial);
		vacuna.save();

		return created();
	}
	
	/**
	 * Action method para PUT /historial/<uId>/vacunas/<tId> 
	 * Se deben pasar los atributos a modificar en el body de la petición.
	 * 
	 * @param uId: identificador del historial
	 * @param tId: identificador de la vacuna a modificar
	 */
	public Result update(Long uId, Long tId) {
		Result res;
		Form<Vacuna> form = Form.form(Vacuna.class).bindFromRequest();

		Vacuna vacuna = Vacuna.find.byId(tId);
		if (vacuna == null) {
			return notFound();
		}

		if (!vacuna.getHistorial().getId().equals(uId)) {
			return badRequest(ControllerHelper.errorJson(2, "invalid_vacuna", null));
		}

		Historial historial = vacuna.getHistorial();

		if (form.hasErrors()) {
			return badRequest(ControllerHelper.errorJson(1, "invalid_vacuna", form.errorsAsJson()));
		}

		if (vacuna.changeData(form.get())) {
			vacuna.setHistorial(historial);
			vacuna.save();
			res = ok();
		} else {
			res = status(NOT_MODIFIED);
		}

		return res;
	}

	/**
	 * Action method para DELETE /historial/<uId>/vacunas/<tId>
	 * 
	 * @param uId identificador del historial
	 * @param tId identificador de la vacuna a borrar
	 */
	public Result delete(Long uId, Long tId) {
		Vacuna vacuna = Vacuna.find.byId(tId);
		if (vacuna == null) {
			return notFound();
		}

		if (!vacuna.getHistorial().getId().equals(uId)) {
			return badRequest(ControllerHelper.errorJson(2, "invalid_vacuna", null));
		}

		vacuna.delete();

		return ok();
	}
}
