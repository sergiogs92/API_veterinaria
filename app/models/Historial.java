package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Historial extends Model {

	@Id
	private Long id;

	@Required 
	String descripcion; //exótico, canino

	@OneToOne(mappedBy="historial")
	@JsonIgnore
	public Mascota mascota;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="historial")
	@Valid
	private List<Enfermedad> enfermedades = new ArrayList<Enfermedad>();	

	@OneToMany(cascade=CascadeType.ALL, mappedBy="historial")
	@Valid
	private List<Vacuna> vacunas = new ArrayList<Vacuna>();
	
	public static final Find<Long, Historial> find = new Find<Long, Historial>(){};
	
	public void addEnfermedad(Enfermedad enfermedad) {
		enfermedades.add(enfermedad);
		enfermedad.setHistorial(this);
	}

	public void addVacuna(Vacuna vacuna) {
		vacunas.add(vacuna);
		vacuna.setHistorial(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Mascota getMascota() {
		return mascota;
	}

	public void setMascota(Mascota mascota) {
		this.mascota = mascota;
	}
	
	public List<Enfermedad> getEnfermedades() {
		return enfermedades;
	}

	public void setEnfermedades(List<Enfermedad> enfermedades) {
		this.enfermedades = enfermedades;
	}
	
	public List<Vacuna> getVacunas() {
		return vacunas;
	}

	public void setVacunas(List<Vacuna> vacunas) {
		this.vacunas = vacunas;
	}
}
