package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Enfermedad extends Model {

	@Id
	private Long id;

	@Required
	private String tipo;

	@Required
	private String nombre;

	@Required
	private String motivo;

	@Required
	private String datacion;

	@Required
	private String estado;

	@ManyToOne
	@JsonIgnore
	private Historial historial;

	public static final Find<Long, Enfermedad> find = new Find<Long, Enfermedad>(){};
	
	public boolean changeData(Enfermedad newData) {
		boolean changed = false;
		
		if (newData.tipo != null) {
			this.tipo = newData.tipo;
			changed = true;
		}
		
		if (newData.nombre != null) {
			this.nombre = newData.nombre;
			changed = true;
		}
		
		if (newData.motivo != null) {
			this.motivo = newData.motivo;
			changed = true;
		}
		
		if (newData.datacion != null) {
			this.datacion = newData.datacion;
			changed = true;
		}

		if (newData.estado != null) {
			this.estado = newData.estado;
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
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	public String getDatacion() {
		return datacion;
	}

	public void setDatacion(String datacion) {
		this.datacion = datacion;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Historial getHistorial() {
		return historial;
	}

	public void setHistorial(Historial historial) {
		this.historial = historial;
	}
}
