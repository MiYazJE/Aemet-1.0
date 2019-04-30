package dominio;

import java.util.ArrayList;

public class Prediccion { 

	private String fecha;
	private ArrayList<ProbabilidadPrecipitacion> probabilidadPrecipitacion;
	private Humedad humedadRelativa;
	private SensacionTermica sensacionTermica;
	private Temperatura temperatura;
	private ArrayList<CotaNieve> cotaNieve;
	private ArrayList<Viento> viento;
	private ArrayList<Rachas> rachasViento;
	private ArrayList<Cielo>  estadoCielo;
	private String indiceUltravioleta;

	public Prediccion(String fecha) {
		this.fecha = fecha;
		indiceUltravioleta = "";
		estadoCielo = new ArrayList<Cielo>();
		viento = new ArrayList<Viento>();
		cotaNieve = new ArrayList<CotaNieve>();
		rachasViento = new ArrayList<Rachas>();
		probabilidadPrecipitacion = new ArrayList<ProbabilidadPrecipitacion>();
	}
	
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Humedad getHumedadRelativa() {
		return humedadRelativa;
	}

	public void setHumedadRelativa(Humedad humedadRelativa) {
		this.humedadRelativa = humedadRelativa;
	}

	public SensacionTermica getSensacionTermica() {
		return sensacionTermica;
	}

	public void setSensacionTermica(SensacionTermica sensacionTermica) {
		this.sensacionTermica = sensacionTermica;
	}

	public ArrayList<Viento> getViento() {
		return viento;
	}


	public void setViento(ArrayList<Viento> viento) {
		this.viento = viento;
	}

	public Temperatura getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Temperatura temperatura) {
		this.temperatura = temperatura;
	}

	public ArrayList<ProbabilidadPrecipitacion> getProbabilidadPrecipitacion() {
		return probabilidadPrecipitacion;
	}

	public void setProbabilidadPrecipitacion(ArrayList<ProbabilidadPrecipitacion> probabilidadPrecipitacion) {
		this.probabilidadPrecipitacion = probabilidadPrecipitacion;
	}

	public ArrayList<CotaNieve> getCotaNieve() {
		return cotaNieve;
	}

	public void setCotaNieve(ArrayList<CotaNieve> cotaNieve) {
		this.cotaNieve = cotaNieve;
	}

	public ArrayList<Cielo> getEstadoCielo() {
		return estadoCielo;
	}

	public void setEstadoCielo(ArrayList<Cielo> estadoCielo) {
		this.estadoCielo = estadoCielo;
	}
	
	public ArrayList<Rachas> getRachasViento() {
		return rachasViento;
	}
	
	public void setRachasViento(ArrayList<Rachas> rachasViento) {
		this.rachasViento = rachasViento;
	}
	
	public String getIndiceUltravioleta() {
		return indiceUltravioleta;
	}
	
	public void setIndiceUltravioleta(String indiceUltravioleta) {
		this.indiceUltravioleta = indiceUltravioleta;
	}
	
	
	
	// 					*************************
    //					*         METHODS	    *
	//					*************************

	public void agregarProbPrecipitacion(ProbabilidadPrecipitacion probPrecipitacion) {
		this.probabilidadPrecipitacion.add(probPrecipitacion);
	}
	
	public void agregarCotaNieve(CotaNieve cotaNieve) {
		this.cotaNieve.add(cotaNieve);
	}
	
	public void agregarCielo(Cielo cielo) {
		this.estadoCielo.add(cielo);
	}
	
	public void agregarRachas(Rachas racha) {
		this.rachasViento.add(racha);
	}
	
	public void agregarViento(Viento viento) {
		this.viento.add(viento);
	}

	@Override
	public String toString() {
		return String.format("fecha: %s\n"
				+ "%s\n"
				+ "%s\n"
				+ "%s\n"
				+ "%s\n"
				+ "%s\n"
				+ "%s\n"
				+ "%s\n"
				+ "%s\n"
				+ "Ultravioleta = '%s'\n",
				fecha, probabilidadPrecipitacion, cotaNieve, estadoCielo, viento,
				rachasViento, temperatura, sensacionTermica, humedadRelativa, indiceUltravioleta);
	}
	
}
