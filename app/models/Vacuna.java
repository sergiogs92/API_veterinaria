package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Required;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Vacuna extends Model {

	@Id
	private Long id;

	@Required
	private String tipo;

	@Required
	private String dosis;

	@Required
	private String detalle;
	
	@ManyToOne
	@JsonIgnore
	private Historial historial;

	public static final Find<Long, Vacuna> find = new Find<Long, Vacuna>(){};
	
	public boolean changeData(Vacuna newData) {
		boolean changed = false;
		
		if (newData.tipo != null) {
			this.tipo = newData.tipo;
			changed = true;
		}
		
		if (newData.dosis != null) {
			this.dosis = newData.dosis;
			changed = true;
		}
		
		if (newData.detalle != null) {
			this.detalle = newData.detalle;
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

	public String getDosis() {
		return dosis;
	}

	public void setDosis(String dosis) {
		this.dosis = dosis;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Historial getHistorial() {
		return historial;
	}

	public void setHistorial(Historial historial) {
		this.historial = historial;
	}
}
