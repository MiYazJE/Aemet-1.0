package dominio;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;

import leerDom.LeerTiempoXml;

public class Poblacion {

	private String nombre;
	private String codigo;
	private ArrayList<Prediccion> predicciones;
	
	/**
	 * Constructor con 1 parametro(String: codigo)
	 * @param codigo
	 */
	public Poblacion(String codigo) {
		this(codigo, "");
	}
	
	public Poblacion(String codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
		predicciones = new ArrayList<Prediccion>();
	}

	
	//				***************************
	// 				*	GETTERS AND SETTERS   *
	// 				***************************
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public ArrayList<Prediccion> getPredicciones() {
		return predicciones;
	}

	public void setPredicciones(ArrayList<Prediccion> predicciones) {
		this.predicciones = predicciones;
	}
	
	
	// 				*******************
    // 			    *     METHODS     *
	// 				*******************

	/**
	 * Busca y devuelve un String con la descripción del estado del cielo mas cercano al dia pasado como argumento
	 * @param dia
	 * @return String
	 */
	public String descripcionCieloAhora(int dia) {

		String estado = "";
		ArrayList<Cielo> estadoCielo = this.predicciones.get(dia).getEstadoCielo();

		for (int i = 0; i < estadoCielo.size(); i++) {
			if (!estadoCielo.get(i)
					.getDescripcion()
					.isEmpty()) {
				return estadoCielo.get(i).getDescripcion();
			}
		}

		return estado;
	}

	/**
	 * Devolver la probabilidad de precipitacion del dia que se le pase como parámetro
	 * @param dia
	 * @return String
	 */
	public String getProbabilidadPrecipitacion(int dia) {
		ArrayList<ProbabilidadPrecipitacion> prob = predicciones.get(dia).getProbabilidadPrecipitacion();
		String valor = "";
		for (int i = 0; i < prob.size(); i++) {
			valor = prob.get(i).getProbabilidad();
			if (!valor.equals("0") && !valor.isEmpty()) {
				return valor;
			}
		}
		return valor;
	}

	public String temperaturaHoy() {
		Temperatura t = predicciones.get(0).getTemperatura();
		return String.valueOf((t.getMaxima() + t.getMinima()) / 2);
	}

	public void agregarPrediccion(Prediccion prediccion) {
		this.predicciones.add(prediccion);
	}
	
}
