package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.GET;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;
import static play.test.Helpers.running;

import org.junit.Test;

import play.mvc.Result;
import play.mvc.Http.RequestBuilder;
import models.Cliente;
import models.ClienteTest;
import models.Enfermedad;
import models.EnfermedadTest;
import models.Historial;
import models.Mascota;
import models.MascotaTest;

public class EnfermedadesTest {

	public void llamadaEnfermedad() {
		Historial historial = new Historial();
		ClienteTest cT = new ClienteTest();
		MascotaTest mT = new MascotaTest();
		EnfermedadTest eT = new EnfermedadTest();
		Mascota m = mT.insertMascota("codigoM", "apodo", "especie", "raza", "fechaNac", "pesoMedio", "pesoActual", historial);
		Cliente c = cT.insertCliente("codigoC", "apellido", "telefono", "pago");
		Enfermedad e = eT.insertEnfermedad("tipo", "nombre", "motivo", "datacion", "estado");
		m.setCliente(c);
		e.setHistorial(historial);
		m.save();
		e.save();
	}

	/***
	 * Enfermedad mascota by id historial
	 */
	@Test
	public void indexEnfermedadMascotaEspecificaJson() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaEnfermedad();
			Result result = route(fakeRequest(GET, "/historial/1/enfermedades"));
			assertNotNull(result);
			assertEquals("application/json", result.contentType());
		});
	}

	@Test
	public void indexEnfermedadMascotaEspecificaWithApplicationXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaEnfermedad();
			RequestBuilder request = fakeRequest(GET, "/historial/1/enfermedades").header("Accept", "application/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

	@Test
	public void indexEnfermedadMascotaEspecificaWithTextXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaEnfermedad();
			RequestBuilder request = fakeRequest(GET, "/historial/1/enfermedades").header("Accept", "text/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

}
