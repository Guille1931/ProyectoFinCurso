package interficie2repaso;

public class Cliente {

	private String nombre;
	private String dni;
	private String banco;

	public Cliente() {
		this.nombre = null;
		this.dni = null;
		this.banco = null;
	}

	public Cliente(String nombre, String dni, String banco) {
		this.nombre = nombre;
		this.dni = dni;
		this.banco = banco;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String preparar() {
		String linia;
		linia = "Nombre: "+getNombre()+
				" DNI: "+getDni()+
				" Banco: "+getBanco();
		return linia;
	}



}
