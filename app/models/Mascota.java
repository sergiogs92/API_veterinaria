package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import play.data.validation.Constraints.Required;
import validators.CodigoMascota;
import validators.Fecha;
import validators.Peso;

@Entity
public class Mascota extends Model {
	@Id
	private Long id;

	@Required
	@CodigoMascota
	private String codigoMascota;

	@Required
	private String apodo;

	@Required
	private String especie;

	@Required
	private String raza;

	@Required
	@Fecha
	private String fechaNacimiento;

	@Required
	@Peso
	private String pesoMedio;

	@Required
	@Peso
	private String pesoActual;

	@Required 
	@OneToOne(cascade = CascadeType.ALL)
	public Historial historial;

	@ManyToOne
	@JsonIgnore
	private Cliente cliente;

	public static final Find<Long, Mascota> find = new Find<Long, Mascota>() {};

	public static Mascota findByUniqueCodigo(String codigo) {
		return find.where().eq("codigoMascota", codigo).findUnique();
	}

	public static List<Mascota> findPagina(Integer pagina, Integer size, String especie) {
		return find.where().eq("especie", especie).setMaxRows(size).setFirstRow(pagina*size).findList();
	}
	
	public boolean changeData(Mascota newData) {
		boolean changed = false;

		if (newData.codigoMascota != null) {
			this.codigoMascota = newData.codigoMascota;
			changed = true;
		}

		if (newData.apodo != null) {
			this.apodo = newData.apodo;
			changed = true;
		}

		if (newData.especie != null) {
			this.especie = newData.especie;
			changed = true;
		}

		if (newData.raza != null) {
			this.raza = newData.raza;
			changed = true;
		}

		if (newData.fechaNacimiento != null) {
			this.fechaNacimiento = newData.fechaNacimiento;
			changed = true;
		}

		if (newData.pesoMedio != null) {
			this.pesoMedio = newData.pesoMedio;
			changed = true;
		}

		if (newData.pesoActual != null) {
			this.pesoActual = newData.pesoActual;
			changed = true;
		}

		if (newData.getHistorial().descripcion != null) {
			this.getHistorial().setDescripcion(newData.getHistorial().descripcion);
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

	public String getCodigoMascota() {
		return codigoMascota;
	}

	public void setCodigoMascota(String codigoMascota) {
		this.codigoMascota = codigoMascota;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getPesoMedio() {
		return pesoMedio;
	}

	public void setPesoMedio(String pesoMedio) {
		this.pesoMedio = pesoMedio;
	}

	public String getPesoActual() {
		return pesoActual;
	}

	public void setPesoActual(String pesoActual) {
		this.pesoActual = pesoActual;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Historial getHistorial() {
		return historial;
	}

	public void setHistorial(Historial historial) {
		this.historial = historial;
	}
}
