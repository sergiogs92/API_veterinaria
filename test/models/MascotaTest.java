package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import models.Historial;
import models.Mascota;

import org.junit.Test;

import play.data.Form;
import play.libs.Json;

public class MascotaTest {

	@Test
	public void codigoMascotaEsRequerido() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Historial historial = new Historial();
			historial.setDescripcion("la descripcion");

			Mascota mascota = new Mascota();
			mascota.setApodo("el apodo");
			mascota.setEspecie("la especie");
			mascota.setRaza("la raza");
			mascota.setFechaNacimiento("10/02/2000");
			mascota.setPesoMedio("22.53");
			mascota.setPesoActual("24.32");
			mascota.setHistorial(historial);

			Form<Mascota> form = Form.form(Mascota.class).bind(Json.toJson(mascota));

			assertTrue(form.hasErrors());
			assertEquals(2, form.field("codigoMascota").errors().size());
			assertTrue(form.field("apodo").errors().isEmpty());
			assertTrue(form.field("especie").errors().isEmpty());
			assertTrue(form.field("raza").errors().isEmpty());
			assertTrue(form.field("fechaNacimiento").errors().isEmpty());
			assertTrue(form.field("pesoMedio").errors().isEmpty());
			assertTrue(form.field("pesoActual").errors().isEmpty());
			assertTrue(form.field(historial.getDescripcion()).errors().isEmpty());
		});
	}

	@Test
	public void apodoEsRequerido() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Historial historial = new Historial();
			historial.setDescripcion("la descripcion");

			Mascota mascota = new Mascota();
			mascota.setCodigoMascota("145L461P-PET");
			mascota.setEspecie("la especie");
			mascota.setRaza("la raza");
			mascota.setFechaNacimiento("10/02/2000");
			mascota.setPesoMedio("22.53");
			mascota.setPesoActual("24.32");
			mascota.setHistorial(historial);

			Form<Mascota> form = Form.form(Mascota.class).bind(Json.toJson(mascota));

			assertTrue(form.hasErrors());
			assertTrue(form.field("codigoMascota").errors().isEmpty());
			assertEquals(1, form.field("apodo").errors().size());
			assertTrue(form.field("especie").errors().isEmpty());
			assertTrue(form.field("raza").errors().isEmpty());
			assertTrue(form.field("fechaNacimiento").errors().isEmpty());
			assertTrue(form.field("pesoMedio").errors().isEmpty());
			assertTrue(form.field("pesoActual").errors().isEmpty());
			assertTrue(form.field(historial.getDescripcion()).errors().isEmpty());
		});
	}

	@Test
	public void especieEsRequerida() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Historial historial = new Historial();
			historial.setDescripcion("la descripcion");

			Mascota mascota = new Mascota();
			mascota.setCodigoMascota("145L461P-PET");
			mascota.setApodo("el apodo");
			mascota.setRaza("la raza");
			mascota.setFechaNacimiento("10/02/2000");
			mascota.setPesoMedio("22.53");
			mascota.setPesoActual("24.32");
			mascota.setHistorial(historial);

			Form<Mascota> form = Form.form(Mascota.class).bind(Json.toJson(mascota));

			assertTrue(form.hasErrors());
			assertTrue(form.field("codigoMascota").errors().isEmpty());
			assertTrue(form.field("apodo").errors().isEmpty());
			assertEquals(1, form.field("especie").errors().size());
			assertTrue(form.field("raza").errors().isEmpty());
			assertTrue(form.field("fechaNacimiento").errors().isEmpty());
			assertTrue(form.field("pesoMedio").errors().isEmpty());
			assertTrue(form.field("pesoActual").errors().isEmpty());
			assertTrue(form.field(historial.getDescripcion()).errors().isEmpty());
		});
	}

	@Test
	public void razaEsRequerida() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Historial historial = new Historial();
			historial.setDescripcion("la descripcion");

			Mascota mascota = new Mascota();
			mascota.setCodigoMascota("145L461P-PET");
			mascota.setApodo("el apodo");
			mascota.setEspecie("la especie");
			mascota.setFechaNacimiento("10/02/2000");
			mascota.setPesoMedio("22.53");
			mascota.setPesoActual("24.32");
			mascota.setHistorial(historial);

			Form<Mascota> form = Form.form(Mascota.class).bind(Json.toJson(mascota));

			assertTrue(form.hasErrors());
			assertTrue(form.field("codigoMascota").errors().isEmpty());
			assertTrue(form.field("apodo").errors().isEmpty());
			assertTrue(form.field("especie").errors().isEmpty());
			assertEquals(1, form.field("raza").errors().size());
			assertTrue(form.field("fechaNacimiento").errors().isEmpty());
			assertTrue(form.field("pesoMedio").errors().isEmpty());
			assertTrue(form.field("pesoActual").errors().isEmpty());
			assertTrue(form.field(historial.getDescripcion()).errors().isEmpty());
		});
	}

	@Test
	public void fechaEsRequerida() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Historial historial = new Historial();
			historial.setDescripcion("la descripcion");

			Mascota mascota = new Mascota();
			mascota.setCodigoMascota("145L461P-PET");
			mascota.setApodo("el apodo");
			mascota.setEspecie("la especie");
			mascota.setRaza("la raza");
			mascota.setPesoMedio("22.53");
			mascota.setPesoActual("24.32");
			mascota.setHistorial(historial);

			Form<Mascota> form = Form.form(Mascota.class).bind(Json.toJson(mascota));

			assertTrue(form.hasErrors());
			assertTrue(form.field("codigoMascota").errors().isEmpty());
			assertTrue(form.field("apodo").errors().isEmpty());
			assertTrue(form.field("especie").errors().isEmpty());
			assertTrue(form.field("raza").errors().isEmpty());
			assertEquals(2, form.field("fechaNacimiento").errors().size());
			assertTrue(form.field("pesoMedio").errors().isEmpty());
			assertTrue(form.field("pesoActual").errors().isEmpty());
			assertTrue(form.field(historial.getDescripcion()).errors().isEmpty());
		});
	}

	@Test
	public void pesoMedioEsRequerido() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Historial historial = new Historial();
			historial.setDescripcion("la descripcion");

			Mascota mascota = new Mascota();
			mascota.setCodigoMascota("145L461P-PET");
			mascota.setApodo("el apodo");
			mascota.setEspecie("la especie");
			mascota.setRaza("la raza");
			mascota.setFechaNacimiento("10/02/2000");
			mascota.setPesoActual("24.32");
			mascota.setHistorial(historial);

			Form<Mascota> form = Form.form(Mascota.class).bind(Json.toJson(mascota));

			assertTrue(form.hasErrors());
			assertTrue(form.field("codigoMascota").errors().isEmpty());
			assertTrue(form.field("apodo").errors().isEmpty());
			assertTrue(form.field("especie").errors().isEmpty());
			assertTrue(form.field("raza").errors().isEmpty());
			assertEquals(2, form.field("pesoMedio").errors().size());
			assertTrue(form.field("fechaNacimiento").errors().isEmpty());
			assertTrue(form.field("pesoActual").errors().isEmpty());
			assertTrue(form.field(historial.getDescripcion()).errors().isEmpty());
		});
	}

	@Test
	public void pesoActualEsRequerido() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Historial historial = new Historial();
			historial.setDescripcion("la descripcion");

			Mascota mascota = new Mascota();
			mascota.setCodigoMascota("145L461P-PET");
			mascota.setApodo("el apodo");
			mascota.setEspecie("la especie");
			mascota.setRaza("la raza");
			mascota.setFechaNacimiento("10/02/2000");
			mascota.setPesoMedio("22.53");
			mascota.setHistorial(historial);

			Form<Mascota> form = Form.form(Mascota.class).bind(Json.toJson(mascota));

			assertTrue(form.hasErrors());
			assertTrue(form.field("codigoMascota").errors().isEmpty());
			assertTrue(form.field("apodo").errors().isEmpty());
			assertTrue(form.field("especie").errors().isEmpty());
			assertTrue(form.field("raza").errors().isEmpty());
			assertTrue(form.field("fechaNacimiento").errors().isEmpty());
			assertTrue(form.field("pesoMedio").errors().isEmpty());
			assertEquals(2, form.field("pesoActual").errors().size());
			assertTrue(form.field(historial.getDescripcion()).errors().isEmpty());
		});
	}

	@Test
	public void historialEsRequerido() {
		running(fakeApplication(inMemoryDatabase()), () -> {
			Historial historial = new Historial();
			historial.setDescripcion("la descripcion");

			Mascota mascota = new Mascota();
			mascota.setCodigoMascota("145L461P-PET");
			mascota.setApodo("el apodo");
			mascota.setEspecie("la especie");
			mascota.setRaza("la raza");
			mascota.setFechaNacimiento("10/02/2000");
			mascota.setPesoMedio("22.53");
			mascota.setPesoActual("24.32");

			Form<Mascota> form = Form.form(Mascota.class).bind(Json.toJson(mascota));

			assertTrue(form.hasErrors());
			assertTrue(form.field("codigoMascota").errors().isEmpty());
			assertTrue(form.field("apodo").errors().isEmpty());
			assertTrue(form.field("especie").errors().isEmpty());
			assertTrue(form.field("raza").errors().isEmpty());
			assertTrue(form.field("fechaNacimiento").errors().isEmpty());
			assertTrue(form.field("pesoMedio").errors().isEmpty());
			assertTrue(form.field("pesoActual").errors().isEmpty());
			assertEquals(1, form.field("historial").errors().size());
		});
	}

	@Test
	public void guardaMascota() {
		running(fakeApplication(inMemoryDatabase()),
				() -> {
					Historial historial = new Historial();
					Mascota mascota = insertMascota("el codigo", "el apodo", "la especie", "la raza", "la fecha", "el pesoM",
							"el pesoAct", historial);

					Mascota mascotaGuardada = Mascota.find.byId(mascota.getId());

					assertEquals(mascota.getCodigoMascota(), mascotaGuardada.getCodigoMascota());
					assertEquals(mascota.getApodo(), mascotaGuardada.getApodo());
					assertEquals(mascota.getEspecie(), mascotaGuardada.getEspecie());
					assertEquals(mascota.getRaza(), mascotaGuardada.getRaza());
					assertEquals(mascota.getFechaNacimiento(), mascotaGuardada.getFechaNacimiento());
					assertEquals(mascota.getPesoMedio(), mascotaGuardada.getPesoMedio());
					assertEquals(mascota.getPesoActual(), mascotaGuardada.getPesoActual());
					assertEquals(mascota.getHistorial(), mascotaGuardada.getHistorial());
				});
	}

	@Test
	public void borraMascota() {
		running(fakeApplication(inMemoryDatabase()),
				() -> {
					Historial historial = new Historial();
					Mascota mascota = insertMascota("el codigo", "el apodo", "la especie", "la raza", "la fecha", "el pesoM",
							"el pesoAct", historial);

					Mascota mascotaGuardada = Mascota.find.byId(mascota.getId());
					mascotaGuardada.delete();

					assertNull(Mascota.find.byId(mascota.getId()));
				});
	}

	@Test
	public void actualizaMascota() {
		running(fakeApplication(inMemoryDatabase()),
				() -> {
					Historial historial = new Historial();
					Mascota mascota = insertMascota("el codigo", "el apodo", "la especie", "la raza", "la fecha", "el pesoM",
							"el pesoAct", historial);

					Mascota mascotaGuardada = Mascota.find.byId(mascota.getId());
					mascotaGuardada.setCodigoMascota("otro codigo");
					mascotaGuardada.setApodo("otro apodo");
					mascotaGuardada.setEspecie("otra especie");
					mascotaGuardada.setRaza("otra raza");
					mascotaGuardada.setFechaNacimiento("otra fecha");
					mascotaGuardada.setPesoMedio("otro pesoM");
					mascotaGuardada.setPesoActual("otro pesoAct");
					mascotaGuardada.setHistorial(historial);

					boolean changed = mascota.changeData(mascotaGuardada);
					mascota.save();

					assertTrue(changed);

					Mascota mascotaActualizada = Mascota.find.byId(mascota.getId());
					assertEquals(mascotaGuardada.getCodigoMascota(), mascotaActualizada.getCodigoMascota());
					assertEquals(mascotaGuardada.getApodo(), mascotaActualizada.getApodo());
					assertEquals(mascotaGuardada.getEspecie(), mascotaActualizada.getEspecie());
					assertEquals(mascotaGuardada.getRaza(), mascotaActualizada.getRaza());
					assertEquals(mascotaGuardada.getFechaNacimiento(), mascotaActualizada.getFechaNacimiento());
					assertEquals(mascotaGuardada.getPesoMedio(), mascotaActualizada.getPesoMedio());
					assertEquals(mascotaGuardada.getPesoActual(), mascotaActualizada.getPesoActual());
					assertEquals(mascotaGuardada.getHistorial(), mascotaActualizada.getHistorial());
				});
	}

	public Mascota insertMascota(String codigoMascota, String apodo, String especie, String raza, String fechaNacimiento,
			String pesoMedio, String pesoActual, Historial historial) {
		Mascota m = new Mascota();
		m.setCodigoMascota(codigoMascota);
		m.setApodo(apodo);
		m.setEspecie(especie);
		m.setRaza(raza);
		m.setFechaNacimiento(fechaNacimiento);
		m.setPesoMedio(pesoMedio);
		m.setPesoActual(pesoActual);
		m.setHistorial(historial);
		m.save();

		return m;
	}
}
