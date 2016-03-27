package controllers;

import java.util.List;

import helpers.ControllerHelper;
import models.Enfermedad;
import models.Historial;
import models.Mascota;
import models.Vacuna;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Historiales extends Controller {
	/**
	 * Action method para GET /mascota/<uId>/historiales
	 * 
	 * @param uId identificador de la mascota
	 */
	public Result index(Long uId) {
		Result res;

		Mascota mascota = Mascota.find.byId(uId);
		if (mascota == null) {
			return notFound();
		}

		if (ControllerHelper.acceptsJson(request())) {
			res = ok(Json.toJson(mascota.getHistorial()));
		} else if (ControllerHelper.acceptsXml(request())) {
			res = ok(views.xml._historial.render(mascota.getHistorial()));
		} else {
			res = badRequest(ControllerHelper.errorJson(1, "unsupported_format", null));
		}

		return res;
	}
	
/**
 * La creación del historial se incluye como "atributo" requerido en la creación de la mascota*/
	
	/**
	 * Action method para DELETE /mascota/<uId>/historiales/<tId>
	 * 
	 * @param uId identificador de la mascota
	 * @param tId identificador del historial a limpiar
	 */
	public Result cleanById(Long uId, Long tId) {
		Historial historial = Historial.find.byId(tId);
		return clean(historial.getMascota());
	}
	
	/**
	 * Action method para DELETE /mascota/code/<codeMascota>/historiales
	 * 
	 * @param codeMascota identificador de la mascota
	 */
	public Result cleanByCodeMascota(String codeMascota) {
		Mascota mascota = Mascota.findByUniqueCodigo(codeMascota);
		return clean(mascota);
	}

	public Result clean(Mascota mascota) {
		Historial historial = mascota.getHistorial();

		if (historial == null) {
			return notFound();
		}

		if (!historial.getMascota().getId().equals(mascota.getId())) {
			return badRequest(ControllerHelper.errorJson(2, "invalid_historial", null));
		}

		List<Enfermedad> enfermedad = historial.getEnfermedades();
		int tamEnfermedad = enfermedad.size();

		if (tamEnfermedad > 0) {
			for (int i = 0; i < tamEnfermedad; i++) {
				enfermedad.get(i).delete();
			}
		}

		List<Vacuna> vacuna = historial.getVacunas();
		int tamVacunas = vacuna.size();

		if (tamVacunas > 0) {
			for (int i = 0; i < tamVacunas; i++) {
				vacuna.get(i).delete();
			}
		}
		return ok();
	}
}
