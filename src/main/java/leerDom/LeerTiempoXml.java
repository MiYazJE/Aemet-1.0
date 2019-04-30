package leerDom;

import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import dominio.Cielo;
import dominio.CotaNieve;
import dominio.Humedad;
import dominio.Poblacion;
import dominio.Prediccion;
import dominio.ProbabilidadPrecipitacion;
import dominio.Rachas;
import dominio.SensacionTermica;
import dominio.Temperatura;
import dominio.Viento;

public interface LeerTiempoXml {

		public static Poblacion almacenarInformacion(String codigo) {

			String ruta = "http://www.aemet.es/xml/municipios/localidad_" + codigo + ".xml";

			Poblacion poblacion = new Poblacion(codigo);
			
			try {

				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				URL url = new URL(ruta);
				InputStream stream = url.openStream();
				Document doc = db.parse(stream);
				doc.getDocumentElement().normalize();

				// Leer nombre
				NodeList listaNombres = doc.getElementsByTagName("nombre");
				String nombre = listaNombres.item(0).getTextContent();
				poblacion.setNombre( nombre );
				
				// Dias
				NodeList listaDias = doc.getElementsByTagName("dia");
				for (int i = 0; i < listaDias.getLength(); i++) {
					
					Node nDias = listaDias.item(i);
					Element elem = (Element) listaDias.item(i);

					// Leer Atributo fecha="2018-04-06" en etiqueta <dia/>
					Element diaElement = (Element) nDias;
					String fecha = diaElement.getAttribute("fecha");
					Prediccion prediccion = new Prediccion(fecha);
					
					
					// Leer probabilidades de precipitacion <prob_precipitacion>
					Element elementoProb = (Element) nDias;
					NodeList elementosProb = elementoProb.getElementsByTagName("prob_precipitacion");
					for (int j = 0; j < elementosProb.getLength(); j++) {
						
						Element elemento = (Element) elementosProb.item(j);
						String periodo = elemento.getAttribute("periodo");
						String probabilidad = elemento.getTextContent();
						
						ProbabilidadPrecipitacion probPrecipitacion = new ProbabilidadPrecipitacion(
								periodo, probabilidad);
						
						prediccion.agregarProbPrecipitacion(probPrecipitacion);
					}	
					
					
					// Leer cota de nieve <cota_nieve_prov />
					NodeList elementosNieve = elem.getElementsByTagName("cota_nieve_prov");
					for (int j = 0; j < elementosNieve.getLength(); j++) {
						
						Element elemento = (Element) elementosNieve.item(j);
						String periodo      = elemento.getAttribute("periodo");
						String probabilidad = elemento.getTextContent();
						
						CotaNieve cotaNieve = new CotaNieve(
								periodo, probabilidad);
						
						prediccion.agregarCotaNieve(cotaNieve);
					}
					
					
					// Leer estado del cielo <estado_cielo />
					NodeList elementosCielo = elem.getElementsByTagName("estado_cielo");
					for (int j = 0; j < elementosCielo.getLength(); j++) {
						
						Element elemento = (Element) elementosCielo.item(j);
						String periodo = elemento.getAttribute("periodo");
						String estado  = elemento.getTextContent();
						String descripcion = elemento.getAttribute("descripcion");

						Cielo cielo = new Cielo(periodo, descripcion, estado);
						
						prediccion.agregarCielo(cielo);
					}
					
					
					// Leer Viento
					NodeList listaVientos = elem.getElementsByTagName("viento");
					for (int j = 0; j < listaVientos.getLength(); j++) {
						
						Element elemento = (Element) listaVientos.item(j);
						String periodo   = elemento.getAttribute("periodo");
						String direccion = elemento.getElementsByTagName("direccion").item(0).getTextContent();
						String velocidad = elemento.getElementsByTagName("velocidad").item(0).getTextContent();
						
						Viento viento = new Viento(periodo, direccion, velocidad);
						
						prediccion.agregarViento(viento);
					}
					
					
					// Leer rachas 
					NodeList listaRachas = elem.getElementsByTagName("racha_max");
					for (int j = 0; j < listaRachas.getLength(); j++) {
						
						Element elemento = (Element) listaRachas.item(j);
						String periodo = elemento.getAttribute("periodo");
						String valor   = elemento.getTextContent();
						
						Rachas racha = new Rachas(periodo, valor);
						
						prediccion.agregarRachas(racha);
					}
					
					
					// Leer TEMPERATURA
					NodeList listTemperatura = elem.getElementsByTagName("temperatura");
					Element elementosTemp = (Element) listTemperatura.item(0);
					
					// MAXIMA
					String maxima = elementosTemp.getElementsByTagName("maxima").item(0).getTextContent();
					
					// MINIMA
					elementosTemp = (Element) listTemperatura.item(0);
					String minima = elementosTemp.getElementsByTagName("minima").item(0).getTextContent();
					
					// DATOS <dato hora="06">10</hora>
					LinkedHashMap<String, String> mapa = new LinkedHashMap<String, String>();
					NodeList listaDatos = elementosTemp.getElementsByTagName("dato");
					for (int j = 0; j < listaDatos.getLength(); j++) {
						
						Element elem1 = (Element) listaDatos.item(j);
						String hora  = elem1.getAttribute("hora");
						String valor = elem1.getTextContent();
						
						mapa.put(hora, valor);
					}
					
					Temperatura temperatura = new Temperatura(maxima, minima, mapa);
					prediccion.setTemperatura(temperatura);
					
					
					// Leer SENSACION TERMICA
					listTemperatura = elem.getElementsByTagName("sens_termica");
					elementosTemp = (Element) listTemperatura.item(0);
					
					// MAXIMA
					maxima = elementosTemp.getElementsByTagName("maxima").item(0).getTextContent();
					
					// MINIMA
					elementosTemp = (Element) listTemperatura.item(0);
					minima = elementosTemp.getElementsByTagName("minima").item(0).getTextContent();
					
					// DATOS <dato hora="06">10</hora>
					mapa = new LinkedHashMap<String, String>();
					listaDatos = elementosTemp.getElementsByTagName("dato");
					for (int j = 0; j < listaDatos.getLength(); j++) {
						
						Element elem1 = (Element) listaDatos.item(j);
						String hora  = elem1.getAttribute("hora");
						String valor = elem1.getTextContent();
						
						mapa.put(hora, valor);
					}
					
					SensacionTermica sensTermica = new SensacionTermica(maxima, minima, mapa);
					prediccion.setSensacionTermica(sensTermica);
					
					
					// Leer HUMEDAD RELATIVA
					listTemperatura = elem.getElementsByTagName("humedad_relativa");
					elementosTemp = (Element) listTemperatura.item(0);
					
					// MAXIMA
					maxima = elementosTemp.getElementsByTagName("maxima").item(0).getTextContent();
					
					// MINIMA
					elementosTemp = (Element) listTemperatura.item(0);
					minima = elementosTemp.getElementsByTagName("minima").item(0).getTextContent();
					
					// DATOS <dato hora="06">10</hora>
					mapa = new LinkedHashMap<String, String>();
					listaDatos = elementosTemp.getElementsByTagName("dato");
					for (int j = 0; j < listaDatos.getLength(); j++) {
						
						Element elem1 = (Element) listaDatos.item(j);
						String hora  = elem1.getAttribute("hora");
						String valor = elem1.getTextContent();
						
						mapa.put(hora, valor);
					}
					
					Humedad humedad = new Humedad(maxima, minima, mapa);
					prediccion.setHumedadRelativa(humedad);
					
					
					// Leer INDICE ULTRAVIOLETA
					NodeList nUv = elem.getElementsByTagName("uv_max");
					Element eUv  = (Element) nUv.item(0);

					// El elemento puede estar null
					if (eUv != null) {
						String ultravioleta = eUv.getTextContent();
						prediccion.setIndiceUltravioleta(ultravioleta);
					}
					
					poblacion.agregarPrediccion(prediccion);
				}
					
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			return poblacion;
		}
	
}
