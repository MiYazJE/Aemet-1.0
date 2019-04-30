package dominio;

import java.io.File;
import java.util.*;

public class Autonomia {

	private String codigo;
	private String nombre;
	private TreeMap<String, String> provincias;
	
	public Autonomia(String codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
		provincias = new TreeMap<String, String>();
		cargarProvincias();
	}
	
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

	public TreeMap<String, String> getProvincias() {
		return provincias;
	}

	public void setProvincias(TreeMap<String, String> provincias) {
		this.provincias = provincias;
	}
	
	/**
	 * Carga todos los codigos y nombres de provincias de una autonomia en funcion
	 * a el codigo de autonomia que tengas.
	 * 
	 */
	private void cargarProvincias() {
		
		String codigo;
		String codProvincia;
		String provincia;
		
		try {

			Scanner s = new Scanner(getClass().getClassLoader().getResourceAsStream("ficheros/provincias.in"), "ISO-8859-1");
			while (s.hasNext()) {
				
				codigo = s.next();
				if (codigo.equals(this.codigo)) {
					codProvincia = s.next().trim();
					provincia = s.nextLine().trim();
					this.provincias.put(codProvincia, provincia);
				}
				else s.nextLine();
				
			}
			
			s.close();


		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
