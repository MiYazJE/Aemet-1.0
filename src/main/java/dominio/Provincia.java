package dominio;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.TreeMap;
import java.util.Scanner;

import leerDom.LeerTiempoXml;

public class Provincia implements LeerTiempoXml {
    
	private String codigo;
	private String nombre;
	private TreeMap<String, String> poblaciones;
	
	public Provincia(String codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
		poblaciones = new TreeMap<String, String>();
		cargarPoblaciones();
	}

	
	//				***************************
	// 				*	GETTERS AND SETTERS   *
	// 				***************************
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TreeMap<String, String> getPoblaciones() {
		return poblaciones;
	}


	public void setCodigosPoblaciones(TreeMap<String, String> poblaciones) {
		this.poblaciones = poblaciones;
	}
	
	// 				*******************
    // 			    *     METHODS     *
	// 				*******************
	
	
	/**
	 * Carga todos los codigos y nombres de las poblaciones en funcion a el codigo
	 * de provincia que tengas
	 * 
	 */
	private void cargarPoblaciones() {
		
		String codigo;
		String poblacion;
		
		try {

			Scanner s = new Scanner(getClass().getClassLoader().getResourceAsStream("ficheros/poblaciones.in"), "ISO-8859-1");
			while (s.hasNext()) {

				codigo = s.next();
				if (codigo.substring(0, 2).equals(this.codigo)) {
					poblacion = s.nextLine().trim();
					this.poblaciones.put(codigo, poblacion);
				}
				else s.nextLine();
				
			}
			
			s.close();

			/*InputStream inputStream = this.getClass().getResourceAsStream("/ficheros/poblaciones.in");
			InputStreamReader inputReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputReader);

			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}*/

		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

}
