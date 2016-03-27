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

public class VacunaTest {

	@Test
	public void tipoEsRequerido() {
		running(fakeApplication(inMemoryDatabase()), () -> {

			Vacuna vacuna = new Vacuna();
			vacuna.setDosis("la dosis");
			vacuna.setDetalle("el detalle");

			Form<Vacuna> form = Form.form(Vacuna.class).bind(Json.toJson(vacuna));

			assertTrue(form.hasErrors());
			assertEquals(1, form.field("tipo").errors().size());
			assertTrue(form.field("dosis").errors().isEmpty());
			assertTrue(form.field("detalle").errors().isEmpty());
		});
	}

	@Test
	public void dosisEsRequerido() {
		running(fakeApplication(inMemoryDatabase()), () -> {

			Vacuna vacuna = new Vacuna();
			vacuna.setTipo("el tipo");
			vacuna.setDetalle("el detalle");

			Form<Vacuna> form = Form.form(Vacuna.class).bind(Json.toJson(vacuna));

			assertTrue(form.hasErrors());
			assertTrue(form.field("tipo").errors().isEmpty());
			assertEquals(1, form.field("dosis").errors().size());
			assertTrue(form.field("detalle").errors().isEmpty());
		});
	}

	@Test
	public void detalleEsRequerido() {
		running(fakeApplication(inMemoryDatabase()), () -> {

			Vacuna vacuna = new Vacuna();
			vacuna.setTipo("el tipo");
			vacuna.setDosis("la dosis");

			Form<Vacuna> form = Form.form(Vacuna.class).bind(Json.toJson(vacuna));

			assertTrue(form.hasErrors());
			assertTrue(form.field("tipo").errors().isEmpty());
			assertTrue(form.field("dosis").errors().isEmpty());
			assertEquals(1, form.field("detalle").errors().size());
		});
	}

	@Test
	public void guardaVacuna() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Vacuna vacuna = insertVacuna("el tipo", "la dosis", "el detalle");

			Vacuna vacunaGuardada = Vacuna.find.byId(vacuna.getId());

			assertEquals(vacuna.getTipo(), vacunaGuardada.getTipo());
			assertEquals(vacuna.getDosis(), vacunaGuardada.getDosis());
			assertEquals(vacuna.getDetalle(), vacunaGuardada.getDetalle());
		});
	}

	@Test
	public void borraVacuna() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Vacuna vacuna = insertVacuna("el tipo", "la dosis", "el detalle");

			Vacuna vacunaGuardada = Vacuna.find.byId(vacuna.getId());
			vacunaGuardada.delete();

			assertNull(Vacuna.find.byId(vacuna.getId()));
		});
	}

	@Test
	public void actualizaVacuna() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Vacuna vacuna = insertVacuna("el tipo", "la dosis", "el detalle");

			Vacuna vacunaGuardada = Vacuna.find.byId(vacuna.getId());
			vacunaGuardada.setTipo("otro tipo");
			vacunaGuardada.setDosis("otra dosis");
			vacunaGuardada.setDetalle("otro detalle");

			boolean changed = vacuna.changeData(vacunaGuardada);
			vacuna.save();

			assertTrue(changed);

			Vacuna vacunaActualizada = Vacuna.find.byId(vacuna.getId());
			assertEquals(vacunaGuardada.getTipo(), vacunaActualizada.getTipo());
			assertEquals(vacunaGuardada.getDosis(), vacunaActualizada.getDosis());
			assertEquals(vacunaGuardada.getDetalle(), vacunaActualizada.getDetalle());
		});
	}

	public Vacuna insertVacuna(String tipo, String dosis, String detalle) {
		Vacuna v = new Vacuna();
		v.setTipo(tipo);
		v.setDosis(dosis);
		v.setDetalle(detalle);
		v.save();

		return v;
	}
}
