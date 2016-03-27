package controllers;

import static play.test.Helpers.GET;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;
import static play.test.Helpers.running;
import static org.junit.Assert.*;
import models.ClienteTest;

import org.junit.Test;

import play.mvc.Http.RequestBuilder;
import play.mvc.Result;

public class ClientesTest {
	/***
	 * Clientes by pagina
	 */
	@Test
	public void indexClientesJson() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Result result = route(fakeRequest(GET, "/clientes/0"));
			assertNotNull(result);
			assertEquals("application/json", result.contentType());
		});
	}

	@Test
	public void indexClientesWithApplicationXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			RequestBuilder request = fakeRequest(GET, "/clientes/0").header("Accept", "application/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

	@Test
	public void indexClientesWithTextXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			RequestBuilder request = fakeRequest(GET, "/clientes/0").header("Accept", "text/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

	public void llamadaCliente() {
		ClienteTest cT = new ClienteTest();
		cT.insertCliente("Pf124dR1-CLI", "apellido", "telefono", "pago");
	}

	/***
	 * Cliente by id
	 */
	@Test
	public void indexClienteEspecificoByIdJson() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaCliente();
			Result result = route(fakeRequest(GET, "/cliente/1"));
			assertNotNull(result);
			assertEquals("application/json", result.contentType());
		});
	}

	@Test
	public void indexClienteEspecificoByIdWithApplicationXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaCliente();
			RequestBuilder request = fakeRequest(GET, "/cliente/1").header("Accept", "application/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

	@Test
	public void indexClienteEspecificoByIdWithTextXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaCliente();
			RequestBuilder request = fakeRequest(GET, "/cliente/1").header("Accept", "text/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

	/***
	 * Cliente by código cliente
	 */
	@Test
	public void indexClienteEspecificoByCodeClientJson() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaCliente();
			Result result = route(fakeRequest(GET, "/cliente/code/Pf124dR1-CLI"));
			assertNotNull(result);
			assertEquals("application/json", result.contentType());
		});
	}

	@Test
	public void indexClienteEspecificoByCodeClientWithApplicationXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaCliente();
			RequestBuilder request = fakeRequest(GET, "/cliente/code/Pf124dR1-CLI").header("Accept", "application/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

	@Test
	public void indexClienteEspecificoByCodeClientWithTextXml() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			llamadaCliente();
			RequestBuilder request = fakeRequest(GET, "/cliente/code/Pf124dR1-CLI").header("Accept", "text/xml");
			Result result = route(request);
			assertNotNull(result);
			assertEquals("application/xml", result.contentType());
		});
	}

}
