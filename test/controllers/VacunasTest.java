package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.GET;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;
import static play.test.Helpers.running;
import models.Cliente;
import models.ClienteTest;
import models.Historial;
import models.Mascota;
import models.MascotaTest;
import models.Vacuna;
import models.VacunaTest;

import org.junit.Test;

import play.mvc.Result;
import play.mvc.Http.RequestBuilder;

public class VacunasTest {

	public void llamadaVacuna() {
		Historial historial = new Historial();
		ClienteTest cT = new ClienteTest();
		MascotaTest mT = new MascotaTest();
		VacunaTest vT = new VacunaTest();
		Mascota m = mT.insertMascota("codigoM", "apodo", "especie", "raza", "fechaNac", "pesoMedio", "pesoActual", historial);
		Cliente c = cT.insertCliente("codigoC", "apellido", "telefono", "pago");
		Vacuna v = vT.insertVacuna("tipo", "dosis", "detalle");
		m.setCliente(c);
		v.setHistorial(historial);
		m.save();
		v.save();
	}

	/***
	 * Vacuna mascota by id historial
	 */
	@Test
	public void indexVacunaMascotaEspecificaJson() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaVacuna();
			Result result = route(fakeRequest(GET, "/historial/1/vacunas"));
			assertNotNull(result);
			assertEquals("application/json", result.contentType());
		});
	}

	@Test
	public void indexVacunaMascotaEspecificaWithApplicationXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaVacuna();
			RequestBuilder request = fakeRequest(GET, "/historial/1/vacunas").header("Accept", "application/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

	@Test
	public void indexVacunaMascotaEspecificaWithTextXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaVacuna();
			RequestBuilder request = fakeRequest(GET, "/historial/1/vacunas").header("Accept", "text/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

}

