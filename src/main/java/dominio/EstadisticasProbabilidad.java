package dominio;

public abstract class EstadisticasProbabilidad {

	private String periodo;
	private String valor;
	
	/**
	 * Constructor que recibe 2 parámetros(String periodo, int probabilidad)
	 * @author Ruben Saiz
	 */
	public EstadisticasProbabilidad(String periodo, String probabilidad) {
		this.periodo = periodo;
		this.valor = probabilidad;
	}

	
	/**
	 * Devuelve el periodo
	 * @return String : periodo
	 */
	public String getPeriodo() {
		return periodo;
	}

	/**
	 * Establece un nuevo periodo
	 * @param periodo String
	 */
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	/**
	 * Devuelve la probabilidad
	 * @return probabilidad : int 
	 */
	public String getProbabilidad() {
		return valor;
	}

	/**
	 * Devuelve la probabilidad : int
	 * @param probabilidad : int
	 */
	public void setProbabilidad(String probabilidad) {
		this.valor = probabilidad;
	}


	@Override
	public String toString() {
		return String.format("<Periodo = '%s'>  <valor = '%s'>\n", periodo, valor);
	}
	
	
}
