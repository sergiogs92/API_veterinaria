package controllers;

import static play.test.Helpers.GET;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;
import static play.test.Helpers.running;
import static org.junit.Assert.*;
import models.Cliente;
import models.ClienteTest;
import models.Persona;
import models.PersonaTest;

import org.junit.Test;

import play.mvc.Http.RequestBuilder;
import play.mvc.Result;

public class PersonasTest {

	public void llamadaPersona() {
		ClienteTest cT = new ClienteTest();
		PersonaTest pT = new PersonaTest();
		Persona p = pT.insertPersona("nombre", "apellidos", "70854120C");
		Cliente c = cT.insertCliente("codigoC", "apellido", "telefono", "pago");
		p.setCliente(c);
		p.save();
	}

	/***
	 * Personas by id cliente
	 */
	@Test
	public void indexPersonasClienteEspecificoJson() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaPersona();
			Result result = route(fakeRequest(GET, "/cliente/1/personas"));
			assertNotNull(result);
			assertEquals("application/json", result.contentType());
		});
	}

	@Test
	public void indexPersonasClienteEspecificoWithApplicationXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaPersona();
			RequestBuilder request = fakeRequest(GET, "/cliente/1/personas").header("Accept", "application/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

	@Test
	public void indexPersonasClienteEspecificoWithTextXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaPersona();
			RequestBuilder request = fakeRequest(GET, "/cliente/1/personas").header("Accept", "text/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

	/***
	 * Cliente by dni persona
	 */
	@Test
	public void indexClienteEspecificoByDniPersonaJson() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaPersona();
			Result result = route(fakeRequest(GET, "/persona/dni/70854120C"));
			assertNotNull(result);
			assertEquals("application/json", result.contentType());
		});
	}

	@Test
	public void indexClienteEspecificoByDniPersonaWithApplicationXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaPersona();
			RequestBuilder request = fakeRequest(GET, "/persona/dni/70854120C").header("Accept", "application/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

	@Test
	public void indexClienteEspecificoByDniPersonaWithTextXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaPersona();
			RequestBuilder request = fakeRequest(GET, "/persona/dni/70854120C").header("Accept", "text/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

}
