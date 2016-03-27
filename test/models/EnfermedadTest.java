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

public class EnfermedadTest {

	@Test
	public void tipoEsRequerido() {
		running(fakeApplication(inMemoryDatabase()), () -> {

			Enfermedad enfermedad = new Enfermedad();
			enfermedad.setNombre("el nombre");
			enfermedad.setMotivo("el motivo");
			enfermedad.setDatacion("la datacion");
			enfermedad.setEstado("el estado");

			Form<Enfermedad> form = Form.form(Enfermedad.class).bind(Json.toJson(enfermedad));

			assertTrue(form.hasErrors());
			assertEquals(1, form.field("tipo").errors().size());
			assertTrue(form.field("nombre").errors().isEmpty());
			assertTrue(form.field("motivo").errors().isEmpty());
			assertTrue(form.field("datacion").errors().isEmpty());
			assertTrue(form.field("estado").errors().isEmpty());
		});
	}

	@Test
	public void nombreEsRequerido() {
		running(fakeApplication(inMemoryDatabase()), () -> {

			Enfermedad enfermedad = new Enfermedad();
			enfermedad.setTipo("el tipo");
			enfermedad.setMotivo("el motivo");
			enfermedad.setDatacion("la datacion");
			enfermedad.setEstado("el estado");

			Form<Enfermedad> form = Form.form(Enfermedad.class).bind(Json.toJson(enfermedad));

			assertTrue(form.hasErrors());
			assertTrue(form.field("tipo").errors().isEmpty());
			assertEquals(1, form.field("nombre").errors().size());
			assertTrue(form.field("motivo").errors().isEmpty());
			assertTrue(form.field("datacion").errors().isEmpty());
			assertTrue(form.field("estado").errors().isEmpty());
		});
	}

	@Test
	public void motivoEsRequerido() {
		running(fakeApplication(inMemoryDatabase()), () -> {

			Enfermedad enfermedad = new Enfermedad();
			enfermedad.setTipo("el tipo");
			enfermedad.setNombre("el nombre");
			enfermedad.setDatacion("la datacion");
			enfermedad.setEstado("el estado");

			Form<Enfermedad> form = Form.form(Enfermedad.class).bind(Json.toJson(enfermedad));

			assertTrue(form.hasErrors());
			assertTrue(form.field("tipo").errors().isEmpty());
			assertTrue(form.field("nombre").errors().isEmpty());
			assertEquals(1, form.field("motivo").errors().size());
			assertTrue(form.field("datacion").errors().isEmpty());
			assertTrue(form.field("estado").errors().isEmpty());
		});
	}

	@Test
	public void datacionEsRequerido() {
		running(fakeApplication(inMemoryDatabase()), () -> {

			Enfermedad enfermedad = new Enfermedad();
			enfermedad.setTipo("el tipo");
			enfermedad.setNombre("el nombre");
			enfermedad.setMotivo("el motivo");
			enfermedad.setEstado("el estado");

			Form<Enfermedad> form = Form.form(Enfermedad.class).bind(Json.toJson(enfermedad));

			assertTrue(form.hasErrors());
			assertTrue(form.field("tipo").errors().isEmpty());
			assertTrue(form.field("nombre").errors().isEmpty());
			assertTrue(form.field("motivo").errors().isEmpty());
			assertEquals(1, form.field("datacion").errors().size());
			assertTrue(form.field("estado").errors().isEmpty());
		});
	}

	@Test
	public void estadoEsRequerido() {
		running(fakeApplication(inMemoryDatabase()), () -> {

			Enfermedad enfermedad = new Enfermedad();
			enfermedad.setTipo("el tipo");
			enfermedad.setNombre("el nombre");
			enfermedad.setMotivo("el motivo");
			enfermedad.setDatacion("el datacion");

			Form<Enfermedad> form = Form.form(Enfermedad.class).bind(Json.toJson(enfermedad));

			assertTrue(form.hasErrors());
			assertTrue(form.field("tipo").errors().isEmpty());
			assertTrue(form.field("nombre").errors().isEmpty());
			assertTrue(form.field("motivo").errors().isEmpty());
			assertTrue(form.field("datacion").errors().isEmpty());
			assertEquals(1, form.field("estado").errors().size());
		});
	}

	@Test
	public void guardaEnfermedad() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Enfermedad enfermedad = insertEnfermedad("el tipo", "el nombre", "el motivo", "la datacion", "el estado");

			Enfermedad enfermedadGuardada = Enfermedad.find.byId(enfermedad.getId());

			assertEquals(enfermedad.getTipo(), enfermedadGuardada.getTipo());
			assertEquals(enfermedad.getNombre(), enfermedadGuardada.getNombre());
			assertEquals(enfermedad.getMotivo(), enfermedadGuardada.getMotivo());
			assertEquals(enfermedad.getDatacion(), enfermedadGuardada.getDatacion());
			assertEquals(enfermedad.getEstado(), enfermedadGuardada.getEstado());
		});
	}

	@Test
	public void borraEnfermedad() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Enfermedad enfermedad = insertEnfermedad("el tipo", "el nombre", "el motivo", "la datacion", "el estado");

			Enfermedad enfermedadGuardada = Enfermedad.find.byId(enfermedad.getId());
			enfermedadGuardada.delete();

			assertNull(Enfermedad.find.byId(enfermedad.getId()));
		});
	}

	@Test
	public void actualizaEnfermedad() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Enfermedad enfermedad = insertEnfermedad("el tipo", "el nombre", "el motivo", "la datacion", "el estado");

			Enfermedad enfermedadGuardada = Enfermedad.find.byId(enfermedad.getId());
			enfermedadGuardada.setTipo("otro tipo");
			enfermedadGuardada.setNombre("otro nombre");
			enfermedadGuardada.setMotivo("otro motivo");
			enfermedadGuardada.setDatacion("otra datacion");
			enfermedadGuardada.setEstado("otro estado");

			boolean changed = enfermedad.changeData(enfermedadGuardada);
			enfermedad.save();

			assertTrue(changed);

			Enfermedad enfermedadActualizada = Enfermedad.find.byId(enfermedad.getId());
			assertEquals(enfermedadGuardada.getTipo(), enfermedadActualizada.getTipo());
			assertEquals(enfermedadGuardada.getNombre(), enfermedadActualizada.getNombre());
			assertEquals(enfermedadGuardada.getMotivo(), enfermedadActualizada.getMotivo());
			assertEquals(enfermedadGuardada.getDatacion(), enfermedadActualizada.getDatacion());
			assertEquals(enfermedadGuardada.getEstado(), enfermedadActualizada.getEstado());
		});
	}

	public Enfermedad insertEnfermedad(String tipo, String nombre, String motivo, String datacion, String estado) {
		Enfermedad e = new Enfermedad();
		e.setTipo(tipo);
		e.setNombre(nombre);
		e.setMotivo(motivo);
		e.setDatacion(datacion);
		e.setEstado(estado);
		e.save();

		return e;
	}
}
