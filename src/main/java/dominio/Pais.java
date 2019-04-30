package dominio;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Pais {

	private TreeMap<String, String> codigoAutonomias;
	
	public Pais() {
		codigoAutonomias = new TreeMap<String, String>();
		cargarCodigosAutonomias();
	}
	
	public TreeMap<String, String> getCodigoAutonomias() {
		return this.codigoAutonomias;
	}

	/**
	 * Cargar todos los codigos de las autonomias de España
	 * 
	 * @return Devuelve un mapa con clave(codigoAutonomia), valor(nombreAutonomia)
	 */
	private void cargarCodigosAutonomias() {
		
		String codigo;
		String provincia;
		
		try {

			Scanner s = new Scanner(getClass().getClassLoader().getResourceAsStream("ficheros/autonomias.in"), "ISO-8859-1");
			while (s.hasNext()) {
				
				codigo    = s.next();
				provincia = s.nextLine().trim();
				
				this.codigoAutonomias.put(codigo, provincia);
			}
			
			s.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
