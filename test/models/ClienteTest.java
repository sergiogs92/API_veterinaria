package models;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import play.data.Form;
import play.libs.Json;

public class ClienteTest {

	@Test
	public void codigoClienteEsRequerido() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Cliente cliente = new Cliente();
			cliente.setApellidoFamilia("el apellidoFamilia");
			cliente.setTelefono("923542103");
			cliente.setTipoPago("el tipoPago");

			Form<Cliente> form = Form.form(Cliente.class).bind(Json.toJson(cliente));

			assertTrue(form.hasErrors());
			assertEquals(2, form.field("codigoCliente").errors().size());
			assertTrue(form.field("apellidoFamilia").errors().isEmpty());
			assertTrue(form.field("telefono").errors().isEmpty());
			assertTrue(form.field("tipoPago").errors().isEmpty());
		});
	}

	@Test
	public void apellidoFamiliaEsRequerido() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Cliente cliente = new Cliente();
			cliente.setCodigoCliente("Pf164dR1-CLI");
			cliente.setTelefono("923542103");
			cliente.setTipoPago("el tipoPago");

			Form<Cliente> form = Form.form(Cliente.class).bind(Json.toJson(cliente));

			assertTrue(form.hasErrors());
			assertTrue(form.field("codigoCliente").errors().isEmpty());
			assertEquals(2, form.field("apellidoFamilia").errors().size());
			assertTrue(form.field("telefono").errors().isEmpty());
			assertTrue(form.field("tipoPago").errors().isEmpty());
		});
	}

	@Test
	public void telefonoEsRequerido() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Cliente cliente = new Cliente();
			cliente.setCodigoCliente("Pf164dR1-CLI");
			cliente.setApellidoFamilia("el apellidoFamilia");
			cliente.setTipoPago("el tipoPago");

			Form<Cliente> form = Form.form(Cliente.class).bind(Json.toJson(cliente));

			assertTrue(form.hasErrors());
			assertTrue(form.field("codigoCliente").errors().isEmpty());
			assertTrue(form.field("apellidoFamilia").errors().isEmpty());
			assertEquals(2, form.field("telefono").errors().size());
			assertTrue(form.field("tipoPago").errors().isEmpty());
		});
	}

	@Test
	public void tipoPagoEsRequerido() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Cliente cliente = new Cliente();
			cliente.setCodigoCliente("Pf164dR1-CLI");
			cliente.setTelefono("923542103");
			cliente.setApellidoFamilia("el apellidoFamilia");

			Form<Cliente> form = Form.form(Cliente.class).bind(Json.toJson(cliente));

			assertTrue(form.hasErrors());
			assertTrue(form.field("codigoCliente").errors().isEmpty());
			assertTrue(form.field("apellidoFamilia").errors().isEmpty());
			assertTrue(form.field("telefono").errors().isEmpty());
			assertEquals(1, form.field("tipoPago").errors().size());
		});
	}

	@Test
	public void guardaCliente() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Cliente cliente = insertCliente("el códigoCliente", "el apellidoFamilia", "el telefono", "el tipoPago");

			Cliente clienteGuardado = Cliente.find.byId(cliente.getId());

			assertEquals(cliente.getCodigoCliente(), clienteGuardado.getCodigoCliente());
			assertEquals(cliente.getApellidoFamilia(), clienteGuardado.getApellidoFamilia());
			assertEquals(cliente.getTelefono(), clienteGuardado.getTelefono());
			assertEquals(cliente.getTipoPago(), clienteGuardado.getTipoPago());
		});
	}

	@Test
	public void borraCliente() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Cliente cliente = insertCliente("el códigoCliente", "el apellidoFamilia", "el telefono", "el tipoPago");

			Cliente clienteGuardado = Cliente.find.byId(cliente.getId());
			clienteGuardado.delete();

			assertNull(Cliente.find.byId(cliente.getId()));
		});
	}

	@Test
	public void actualizaCliente() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Cliente cliente = insertCliente("el códigoCliente", "el apellidoFamilia", "el telefono", "el tipoPago");

			Cliente clienteGuardado = Cliente.find.byId(cliente.getId());
			clienteGuardado.setCodigoCliente("otro codigoCliente");
			clienteGuardado.setApellidoFamilia("otro apellidoFamilia");
			clienteGuardado.setTelefono("otroTelefono");
			clienteGuardado.setTipoPago("otroPago");
			boolean changed = cliente.changeData(clienteGuardado);
			cliente.save();

			assertTrue(changed);

			Cliente clienteActualizado = Cliente.find.byId(cliente.getId());
			assertEquals(clienteGuardado.getCodigoCliente(), clienteActualizado.getCodigoCliente());
			assertEquals(clienteGuardado.getApellidoFamilia(), clienteActualizado.getApellidoFamilia());
			assertEquals(clienteGuardado.getTelefono(), clienteActualizado.getTelefono());
			assertEquals(clienteGuardado.getTipoPago(), clienteActualizado.getTipoPago());

		});
	}

	@Test
	public void findPagina() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Cliente c1 = insertCliente("c1", "aF1", "t1", "tP1");
			Cliente c2 = insertCliente("c2", "aF2", "t2", "tP2");
			Cliente c3 = insertCliente("c3", "aF3", "t3", "tP3");

			// Primera página de 1 registro
				List<Cliente> pagina = Cliente.findPagina(0, 1);
				assertEquals(1, pagina.size());
				assertEquals(c1.getId(), pagina.get(0).getId());

				// Primera página de 2 registros
				pagina = Cliente.findPagina(0, 2);
				assertEquals(2, pagina.size());
				assertEquals(c1.getId(), pagina.get(0).getId());
				assertEquals(c2.getId(), pagina.get(1).getId());

				// Segunda página de 2 registros
				pagina = Cliente.findPagina(1, 2);
				assertEquals(1, pagina.size());
				assertEquals(c3.getId(), pagina.get(0).getId());
			});
	}

	public Cliente insertCliente(String codigoCliente, String apellidoFamilia, String telefono, String tipoPago) {
		Cliente c = new Cliente();
		c.setCodigoCliente(codigoCliente);
		c.setApellidoFamilia(apellidoFamilia);
		c.setTelefono(telefono);
		c.setTipoPago(tipoPago);
		c.save();

		return c;
	}

}
