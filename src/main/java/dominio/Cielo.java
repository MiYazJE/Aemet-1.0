package dominio;

public class Cielo {

	private String periodo;
	private String descripcion;
	private String estado;
	
	public Cielo() {
		this("", "", null);
	}
	
	public Cielo(String periodo, String descripcion, String estado) {
		this.periodo = periodo;
		this.descripcion = descripcion;
		this.estado = estado;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	
	// 				************************
    //			    *        METHODS  	   *
	//				************************

	@Override
	public String toString() {
		return String.format("<periodo = '%s' /> <descripcion = '%s' /> <estado = '%s' />\n", periodo, descripcion, estado);
	}

}
