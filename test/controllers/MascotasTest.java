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

import org.junit.Test;

import play.mvc.Result;
import play.mvc.Http.RequestBuilder;

public class MascotasTest {
	
	public void llamadaMascota() {
		Historial historial = new Historial();
		ClienteTest cT = new ClienteTest();
		MascotaTest mT = new MascotaTest();
		Mascota m = mT.insertMascota("145L421P-PET", "apodo", "especie", "raza", "fechaNac", "pesoMedio", "pesoActual", historial);
		Cliente c = cT.insertCliente("codigoC", "apellido", "telefono", "pago");
		m.setCliente(c);
		m.save();
	}
	
	/***
	 * Mascotas (especie) by pagina
	 */
	@Test
	public void indexMascotasByEspecieJson() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaMascota();
			Result result = route(fakeRequest(GET, "/mascotas/0?especie=especie"));
			assertNotNull(result);
			assertEquals("application/json", result.contentType());
		});
	}

	@Test
	public void indexMascotasByEspecieWithApplicationXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaMascota();
			RequestBuilder request = fakeRequest(GET, "/mascotas/0?especie=especie").header("Accept", "application/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

	@Test
	public void indexMascotasByEspeciesWithTextXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaMascota();
			RequestBuilder request = fakeRequest(GET, "/mascotas/0?especie=especie").header("Accept", "text/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

	/***
	 * Mascotas by id cliente
	 */
	@Test
	public void indexMascotasClienteEspecificoJson() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaMascota();
			Result result = route(fakeRequest(GET, "/cliente/1/mascotas"));
			assertNotNull(result);
			assertEquals("application/json", result.contentType());
		});
	}

	@Test
	public void indexMascotasClienteEspecificoWithApplicationXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaMascota();
			RequestBuilder request = fakeRequest(GET, "/cliente/1/mascotas").header("Accept", "application/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

	@Test
	public void indexMascotasClienteEspecificoWithTextXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaMascota();
			RequestBuilder request = fakeRequest(GET, "/cliente/1/mascotas").header("Accept", "text/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

	/***
	 * Mascota by codigo mascota
	 */
	@Test
	public void indexMascotaEspecificaByCodePetJson() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaMascota();
			Result result = route(fakeRequest(GET, "/mascota/code/145L421P-PET"));
			assertNotNull(result);
			assertEquals("application/json", result.contentType());
		});
	}

	@Test
	public void indexMascotaEspecificaByCodePetWithApplicationXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaMascota();
			RequestBuilder request = fakeRequest(GET, "/mascota/code/145L421P-PET").header("Accept", "application/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

	@Test
	public void indexMascotaEspecificaByCodePetWithTextXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaMascota();
			RequestBuilder request = fakeRequest(GET, "/mascota/code/145L421P-PET").header("Accept", "text/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

	/***
	 * Historial mascota by id mascota
	 */
	@Test
	public void indexHistorialMascotaEspecificaJson() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaMascota();
			Result result = route(fakeRequest(GET, "/mascota/1/historiales"));
			assertNotNull(result);
			assertEquals("application/json", result.contentType());
		});
	}

	@Test
	public void indexHistorialMascotaEspecificaWithApplicationXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaMascota();
			RequestBuilder request = fakeRequest(GET, "/mascota/1/historiales").header("Accept", "application/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

	@Test
	public void indexHistorialMascotaEspecificaWithTextXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaMascota();
			RequestBuilder request = fakeRequest(GET, "/mascota/1/historiales").header("Accept", "text/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}
}
