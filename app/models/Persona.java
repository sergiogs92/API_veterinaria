package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Required;
import validators.Apellidos;
import validators.Dni;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Persona extends Model {
	
	@Id
	private Long id;
	
	@Required
	private String nombre;
	
	@Required
	@Apellidos
	private String apellidos;

	@Required
	@Dni
	private String dni;

	@ManyToOne
	@JsonIgnore
	private Cliente cliente;

	public static final Find<Long, Persona> find = new Find<Long, Persona>(){};
	
	public static Persona findByUniqueDni(String dni) {
		return find.where().eq("dni", dni).findUnique();
	}
	
	public boolean changeData(Persona newData) {
		boolean changed = false;

		if (newData.nombre != null) {
			this.nombre = newData.nombre;
			changed = true;
		}

		if (newData.apellidos != null) {
			this.apellidos = newData.apellidos;
			changed = true;
		}

		if (newData.dni != null) {
			this.dni = newData.dni;
			changed = true;
		}

		return changed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
