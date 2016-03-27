package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import org.junit.Test;

import play.data.Form;
import play.libs.Json;

public class PersonaTest {

	@Test
	public void nombreEsRequerido() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Persona persona = new Persona();
			persona.setApellidos("los apellidos");
			persona.setDni("70524125L");

			Form<Persona> form = Form.form(Persona.class).bind(Json.toJson(persona));

			assertTrue(form.hasErrors());
			assertEquals(1, form.field("nombre").errors().size());
			assertTrue(form.field("apellidos").errors().isEmpty());
			assertTrue(form.field("dni").errors().isEmpty());
		});
	}

	@Test
	public void apellidoEsRequerido() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Persona persona = new Persona();
			persona.setNombre("el nombre");
			persona.setDni("70524125L");

			Form<Persona> form = Form.form(Persona.class).bind(Json.toJson(persona));

			assertTrue(form.hasErrors());
			assertTrue(form.field("nombre").errors().isEmpty());
			assertEquals(2, form.field("apellidos").errors().size());
			assertTrue(form.field("dni").errors().isEmpty());
		});
	}

	@Test
	public void dniEsRequerido() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Persona persona = new Persona();
			persona.setNombre("el nombre");
			persona.setApellidos("los apellidos");

			Form<Persona> form = Form.form(Persona.class).bind(Json.toJson(persona));

			assertTrue(form.hasErrors());
			assertTrue(form.field("nombre").errors().isEmpty());
			assertEquals(2, form.field("dni").errors().size());
			assertTrue(form.field("apellidos").errors().isEmpty());
		});
	}

	@Test
	public void guardaPersona() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Persona persona = insertPersona("el nombre", "los apellidos", "el dni");

			Persona personaGuardada = Persona.find.byId(persona.getId());

			assertEquals(persona.getNombre(), personaGuardada.getNombre());
			assertEquals(persona.getApellidos(), personaGuardada.getApellidos());
			assertEquals(persona.getDni(), personaGuardada.getDni());
		});
	}

	@Test
	public void borraPersona() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Persona persona = insertPersona("el nombre", "los apellidos", "el dni");

			Persona personaGuardada = Persona.find.byId(persona.getId());
			personaGuardada.delete();

			assertNull(Persona.find.byId(persona.getId()));
		});
	}

	@Test
	public void actualizaPersona() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Persona persona = insertPersona("el nombre", "los apellidos", "el dni");

			Persona personaGuardada = Persona.find.byId(persona.getId());
			personaGuardada.setNombre("otro nombre");
			personaGuardada.setApellidos("otros apellidos");
			personaGuardada.setDni("otro dni");
			boolean changed = persona.changeData(personaGuardada);
			persona.save();

			assertTrue(changed);

			Persona personaActualizada = Persona.find.byId(persona.getId());
			assertEquals(personaGuardada.getNombre(), personaActualizada.getNombre());
			assertEquals(personaGuardada.getApellidos(), personaActualizada.getApellidos());
			assertEquals(personaGuardada.getDni(), personaActualizada.getDni());
		});
	}

	public Persona insertPersona(String nombre, String apellidos, String dni) {
		Persona p = new Persona();
		p.setNombre(nombre);
		p.setApellidos(apellidos);
		p.setDni(dni);
		p.save();

		return p;
	}
}
