package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import play.data.validation.Constraints.Required;
import validators.Apellidos;
import validators.CodigoCliente;
import validators.Telefono;

import com.avaje.ebean.Model;

@Entity
public class Cliente extends Model {

	@Id
	private Long id;
	
	@Required
	@CodigoCliente
	private String codigoCliente;
	
	@Required
	@Apellidos
	private String apellidoFamilia;
	
	@Required
	@Telefono
	private String telefono;
	
	@Required
	private String tipoPago;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="cliente")
	@Valid
	private List<Persona> personas = new ArrayList<Persona>();	

	@OneToMany(cascade=CascadeType.ALL, mappedBy="cliente")
	@Valid
	private List<Mascota> mascotas = new ArrayList<Mascota>();

	public static final Find<Long, Cliente> find = new Find<Long, Cliente>(){};
	
	public void addPersona(Persona persona) {
		personas.add(persona);
		persona.setCliente(this);
	}

	public void addMascota(Mascota mascota) {
		mascotas.add(mascota);
		mascota.setCliente(this);
	}
	
	public static List<Cliente> findPagina(Integer pagina, Integer size) {
		return find.orderBy("id").setMaxRows(size).setFirstRow(pagina*size).findList();
	}

	public static Cliente findByUniqueCodigo(String codigo) {
		return find.where().eq("codigoCliente", codigo).findUnique();
	}
	
	public boolean changeData(Cliente newData) {
		boolean changed = false;
		
		if (newData.codigoCliente != null) {
			this.codigoCliente = newData.codigoCliente;
			changed = true;
		}
		
		if (newData.apellidoFamilia != null) {
			this.apellidoFamilia = newData.apellidoFamilia;
			changed = true;
		}
		
		if (newData.telefono != null) {
			this.telefono = newData.telefono;
			changed = true;
		}
		
		if (newData.tipoPago != null) {
			this.tipoPago = newData.tipoPago;
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

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getApellidoFamilia() {
		return apellidoFamilia;
	}

	public void setApellidoFamilia(String apellidoFamilia) {
		this.apellidoFamilia = apellidoFamilia;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}
	
	public List<Persona> getPersonas() {
		return personas;
	}

	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}
	
	public List<Mascota> getMascotas() {
		return mascotas;
	}

	public void setMascotas(List<Mascota> mascotas) {
		this.mascotas = mascotas;
	}

}
