package dominio;

public class Viento {

	private String periodo;
	private String direccion;
	private String velocidad;
	
	public Viento(String periodo, String direccion, String velocidad) {
		this.periodo   = periodo;
		this.direccion = direccion;
		this.velocidad = velocidad;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(String viento) {
		this.velocidad = viento;
	}
	
	
	
	// 				************************
    //			    *        METHODS  	   *
	//				************************
	
	@Override
	public String toString() {
		return String.format("<periodo = '%s'> <direccion = '%s'> <velocidad = '%s'>\n", periodo, direccion, velocidad);
	}
	
}
