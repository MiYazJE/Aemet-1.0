package dominio;

import java.util.HashMap;
import java.util.Map;

public abstract class TemperaturaExtendida {

	private int maxima;
	private int minima;
	private HashMap<String, String> datos; 
	
	public TemperaturaExtendida(String maxima, String minima, HashMap<String, String> datos) {
		this.maxima = Integer.parseInt(maxima);
		this.minima = Integer.parseInt(minima);
		this.datos  = datos;
	}

	public int getMaxima() {
		return maxima;
	}

	public void setMaxima(int maxima) {
		this.maxima = maxima;
	}

	public int getMinima() {
		return minima;
	}

	public void setMinima(int minima) {
		this.minima = minima;
	}

	
	// 				*********************************
	// 				*			METHODS				*
	// 				*********************************

	/**
	 * Obtener media de los datos por horas
	 * @return media
	 */
	public int obtenerMediaHoras() {

		// Puede que el mapa datos obtenga Strings vacios
		int media = 0;
		int size = 0;

		for (Map.Entry<String, String> entry : datos.entrySet()) {
			if (!entry.getValue().isEmpty()) {
				media += Integer.valueOf(entry.getValue());
				size++;
			}
		}

		return media / size;
	}

	@Override
	public String toString() {
		return String.format("<maxima = '%s'> <minima = '%s'> %s\n", maxima, minima, datos);
	}

}
