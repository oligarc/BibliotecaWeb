package entidades;

public class Socio {
	
	private int idSocio;
	private String email;
	private String nombre;
	private String direccion;
	private String clave;
	private int version;
	private String telefono;
	
	public Socio() {
		
	}

	public Socio(int idSocio, String email, String nombre, String direccion, int version) {
		this.idSocio = idSocio;
		this.email = email;
		this.nombre = nombre;
		this.direccion = direccion;
		this.version = version;
	}
	
	
	
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public int getIdSocio() {
		return idSocio;
	}

	public void setIdSocio(int idSocio) {
		this.idSocio = idSocio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Socio [idSocio=" + idSocio + ", email=" + email + ", nombre=" + nombre + ", direccion=" + direccion
				+ ", version=" + version + "]";
	}
	
	

}
