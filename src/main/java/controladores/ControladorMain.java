package controladores;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import dominio.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import leerDom.LeerTiempoXml;
import org.controlsfx.control.textfield.*;

import javax.imageio.ImageIO;

public class ControladorMain implements Initializable {

	// 			*********************
	// 			*    COMPONENTES    *
	// 			*********************

	@FXML private ComboBox<String> comboAutonomias;
	@FXML private ComboBox<String> comboProvincias;
	@FXML private ComboBox<String> comboPoblaciones;
	@FXML private TextField busqueda;
	@FXML private Text textNombrePoblacion;
	@FXML private AnchorPane datosTiempo;
	@FXML private AnchorPane tiempoHoy;
	@FXML private Text textFecha;
	@FXML private ImageView imagenTiempoAhora;
	@FXML private Text textTemperatura;
	@FXML private Label textSensacion;
	@FXML private AnchorPane barTop;
	@FXML private AnchorPane cajaBotonCerrar;
	@FXML private ImageView imagenCerrar;
	@FXML private AnchorPane cajaBotonMinimizar;
	@FXML private ImageView imagenMinimizar;

	private Pais pais;
	private Autonomia autonomia;
	private Provincia provincia;
	private Poblacion poblacion;
	private ArrayList<Prediccion> predicciones;
	private ArrayList<Poblacion> listaVisitados;
	private TreeMap<String, String> mapaPoblacionCodigo;
	private TreeSet<String> listaNombrePoblaciones;
	private AnchorPane cajaListaVisitados;
	private Stage ventanaError;
	private double posX;
	private double posY;


	/**
	 * Salir de la aplicación
	 */
	public void salir() {
		Stage stage = (Stage) busqueda.getScene().getWindow();
		// EFECTO FADE OUT AL FINALIZAR LA APLICACIÓN
		Timeline timeline = new Timeline();
		KeyFrame key = new KeyFrame(Duration.millis(1000),
				       new KeyValue(stage.getScene().getRoot().opacityProperty(), 0));
		timeline.getKeyFrames().add(key);
		timeline.setOnFinished((ae) -> System.exit(1));
		timeline.play();
	}

    /**
     * Minimizar la ventana
     */
    public void minimizar() {
        Stage scenario = (Stage) busqueda.getScene().getWindow();
        scenario.setIconified(true);
    }

	/**
	 * Envia el valor de la poblacion seleccionada a la funcion cargarTiempo()
	 */
	public void pasarelaCargarTiempo() {
		cargarTiempo(busqueda.getText());
	}


	public void cargarTiempo() {
		try {
			cargarTiempo(comboPoblaciones.getValue());
		} catch (Exception e) {};
	}
	/**
	 * Cargar las predicciones, en caso de haber algún error en la poblacion seleccionada
	 * mostrar un error.
	 * @param buscar
	 */
	@FXML
	void cargarTiempo(String buscar) {
		if ((ventanaError == null || !ventanaError.isShowing())) {
			if (!listaNombrePoblaciones.contains(buscar)) {
				try {
					crearVentanaError();
				} catch (Exception e) {};
			}
			else {
				poblacion = cargarDatosPoblacion(buscar);
				predicciones = poblacion.getPredicciones();
				mostrarTiempoHoy();
				mostrarTiempo();
				actualizarListaVisitados();
			}
		}
	}

	/**
	 * Ofrece todos los datos visuales del tiempo
	 */
	public void mostrarTiempoHoy() {

		// MOSTRAR NOMBRE DE LA POBLACION
		textNombrePoblacion.setText("El tiempo en " + poblacion.getNombre());

		// MOSTRAR HORA
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter formmat = DateTimeFormatter.ofPattern("HH:mm");
		String hora = formmat.format(ldt);
		String estadoCielo = poblacion.descripcionCieloAhora(0);
		textFecha.setText(hora + " " + estadoCielo);

		// CARGAR IMAGEN DEL ESTADO DE CIELO
		Image imagen = obtenerImagenCielo(estadoCielo);

		// Seteamos la imagen
		imagenTiempoAhora.setImage(imagen);

		// Añadir la temperatura en el text "textTemperatura"
		textTemperatura.setText(poblacion.temperaturaHoy() + "º");

		// Añadir la sensacion termica
		int sensacion = predicciones.get(0).getSensacionTermica().obtenerMediaHoras();
		textSensacion.setText("Sensación de " + sensacion + "º");

		// Mostrar el panel con la información ya cargada
		tiempoHoy.setVisible(true);
		datosTiempo.setVisible(true);

	}

	/**
	 * Muestra el tiempo de una semana por dias
	 *
	 */
	public void mostrarTiempo() {

		datosTiempo.getChildren().clear();
		int posYcaja = 10;
		int cajaWidth = 135;

		for (int i = 0; i < 7; i++) {

			// Crear la caja que va a contener el tiempo del día
			AnchorPane caja = new AnchorPane();
			caja.setPrefSize(cajaWidth, 310);
			caja.setLayoutX(posYcaja);
			caja.setLayoutY(10);

			// Aumentamos la posicion de la caja para crear un borde con la siguiente caja que creeemos
			posYcaja += 135 + 10;

			// posicion Y del ultimo componente añadido
			int posicionY = 0;

			// Añadir el día
			Text dia = new Text(obtenerDia(i));
			dia.setWrappingWidth(cajaWidth);
			dia.setLayoutY(50);
			posicionY += 50;
			dia.setTextAlignment(TextAlignment.CENTER);

			caja.getChildren().add(dia);

			// Añadir el dia del mes
			Text diaMes = new Text(obtenerDiaMes(i));
			diaMes.setWrappingWidth(cajaWidth);
			diaMes.setLayoutY((posicionY + 20));
			diaMes.setTextAlignment(TextAlignment.CENTER);
			posicionY += 30;

			caja.getChildren().add(diaMes);

			// Añadir imagen del estado del cielo
			ImageView imagenEstado = new ImageView();
			String estadoCielo = poblacion.descripcionCieloAhora(i);
			Image imagen = obtenerImagenCielo(estadoCielo);
			imagenEstado.setImage(imagen);
			imagenEstado.setFitWidth(50);
			imagenEstado.setFitHeight(50);
			imagenEstado.setLayoutX(35);
			imagenEstado.setLayoutY(posicionY + 10);
			posicionY += 20;

			caja.getChildren().add(imagenEstado);

			// En caso de haber lluvía se indicara la probabilidad de precipitación
			if (estadoCielo.contains("lluvia")) {
				Text probPrecipitacion = new Text(poblacion.getProbabilidadPrecipitacion(i) + "%");
				probPrecipitacion.setLayoutY(posicionY + 80);
				probPrecipitacion.setWrappingWidth(cajaWidth);
				probPrecipitacion.setTextAlignment(TextAlignment.CENTER);
				caja.getChildren().add(probPrecipitacion);

				probPrecipitacion.setStyle("-fx-fill: white;" +
				                           "fx-font-size: 12px;");
			}

			// Argegar temperatura máxima/minimia
			Text temperatura = new Text();
			temperatura.setWrappingWidth(cajaWidth);
			temperatura.setTextAlignment(TextAlignment.CENTER);
			temperatura.setText(predicciones.get(i).getTemperatura().getMaxima() + "º / " +
								predicciones.get(i).getTemperatura().getMinima() + "º");
			temperatura.setLayoutY(posicionY + 120);
			posicionY += 120;

			caja.getChildren().add(temperatura);

			// Agregar los componentes al padre
			datosTiempo.getChildren().add(caja);

			// Aplicar estilos
			caja.setStyle("-fx-font-family: System;" +
					      "-fx-background-color: #185d5e;" +
					      "-fx-font-weight: bold;");

			dia.setStyle("-fx-fill: white;" +
						 "-fx-font-size: 19px;");

			diaMes.setStyle("-fx-fill: white;" +
							"-fx-font-size: 14px;");

			temperatura.setStyle("-fx-font-size: 19;" +
								 "-fx-fill: white;");

		}

	}

	/**
	 * Devuelve un String con el dia y el mes sumados al día que se le pasa como parámetro más la fecha actual
	 * @param diaPlus
	 * @return String
	 */
	private String obtenerDiaMes(int diaPlus) {

		LocalDateTime ldt = LocalDateTime.now().plusDays(diaPlus);
		DateTimeFormatter formmat = DateTimeFormatter.ofPattern("MM-dd");
		String diaMes = formmat.format(ldt);

		String fecha = String.format("%d", Integer.valueOf(diaMes.split("-")[1]));
		String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
		fecha += " " + meses[Integer.valueOf(diaMes.split("-")[0])-1];

		return fecha;
	}

	/**
	 * Obtener el dia ("Hoy, Martes, Miercoles, Jueves...") sumados a la cantidad
	 * de dias pasados como parametro mas la fecha actual
	 * @param dia
	 * @return String
	 */
	private String obtenerDia(int dia) {
		if (dia == 0) return "HOY";
		LocalDateTime ldt = LocalDateTime.now().plusDays(dia);
		int day = ldt.getDayOfWeek().getValue();
		String[] dias = {"LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO", "DOMINGO"};
		return dias[(day - 1)];
	}


	/**
	 * Devuelve una la buscar de una imagen acorde al tipo de cielo pasado como parametro
	 * @param estadoCielo
	 * @return String
	 */
	private Image obtenerImagenCielo(String estadoCielo) {

		String buscar = "/imagenes/test-weather-icons/";
		if (estadoCielo.contains("tormenta"))     buscar += "tormenta.png";
		else if (estadoCielo.contains("nieve"))   buscar += "nieve.png";
		else if (estadoCielo.contains("lluvia"))  buscar += "lluvia.png";
		else if (estadoCielo.equals("Despejado")) buscar += "despejado.png";
		else {
			switch (estadoCielo) {
				case "Intervalos nubosos":
				case "Poco nuboso":
				case "Nuboso":
				case "Nubes altas": buscar += "poco-nuboso.png"; break;
				default: buscar += "muy-nuboso.png";
			}
		}

		// TODO class path
		String ruta = getClass().getResource(buscar).getPath();
		Image imagen = new Image(buscar);
		return imagen;
	}

	private void crearVentanaError() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/ventanaError.fxml"));
		ventanaError = new Stage();
		root.setOnMousePressed(e -> {
			posX = ventanaError.getX() - e.getScreenX();
			posY = ventanaError.getY() - e.getScreenY();
		});
		root.setOnMouseDragged(e -> {
			ventanaError.setX(e.getScreenX() + posX);
			ventanaError.setY(e.getScreenY() + posY);
		});
		Scene scene = new Scene(root, 546, 233);
		ventanaError.setScene(scene);
		ventanaError.initStyle(StageStyle.UNDECORATED);
		ventanaError.setAlwaysOnTop(true);
		ventanaError.setResizable(false);
		ventanaError.show();
	}

	/**
	 * Cargar en el combobox todos los nombres de las autonomías de España
	 */
	private void cargarComboAutonomias() {
		pais = new Pais();
		TreeMap<String, String> mapa = pais.getCodigoAutonomias();

		for (Map.Entry<String, String> entry : mapa.entrySet()) {
			comboAutonomias.getItems().add(entry.getValue());
		}
	}

	/**
	 * Cargar provincias dentro del ComboBox de "Autonomias"
	 */
	@FXML
	public void cargarProvincias() {

		// Econtrar el codigo en base al nombre de autonomia seleccionado
		String nombre = comboAutonomias.getValue();
		String codigo = "";
		for (Map.Entry<String, String> entry : pais.getCodigoAutonomias().entrySet()) {
			if (entry.getValue().equals(nombre)) { codigo = entry.getKey(); break; }
		}

		// Creamos la comunicad autonoma
		autonomia = new Autonomia(codigo, nombre);

		// Limpiamos los demas combobox para las siguientes busquedas
		comboProvincias.getItems().clear();
		comboPoblaciones.getItems().clear();

		// Agregamos al combobox Provincias todas las provincias de la autonomia seleccionada
		for (Map.Entry<String, String> entry : autonomia.getProvincias().entrySet()) {
			comboProvincias.getItems().add(entry.getValue());
		}

	}

	/**
	 * Cargar todas las poblaciones dentro del comboBox de Provincias
	 */
	@FXML
	public void cargarPoblaciones() {
		
		String nombre = comboProvincias.getValue();

		String codigo = "";
		for (Map.Entry<String, String> entry : autonomia.getProvincias().entrySet()) {
			if (entry.getValue().equals(nombre)) { codigo = entry.getKey(); break; }
		}
		
		provincia = new Provincia(codigo, nombre);

		comboPoblaciones.getItems().clear();

		for (Map.Entry<String, String> entry : provincia.getPoblaciones().entrySet()) {
			comboPoblaciones.getItems().add(entry.getValue());
		}

	}

	/**
	 * Cargar la predicción de la poblacion seleccionada
	 */
	public Poblacion cargarDatosPoblacion(String buscar) {
		// Obtener el codigo de poblacion
		String codigo = mapaPoblacionCodigo.get(buscar);
		return LeerTiempoXml.almacenarInformacion(codigo);
	}

	/**
	 * Cargar todas las poblaciones de España para ayudar al usuario a elegir una elección en el input
	 * textField. Cargo un mapa<String:"NombrePoblacion", String:"codigoPoblacion"> y
	 * un ArraList<String:"NombrePoblacion">.
	 */
	@FXML
	public void cargarTodasLasPoblaciones() {

		mapaPoblacionCodigo = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
		listaNombrePoblaciones = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		String codigo;
		String poblacion;

		try {

			Scanner s = new Scanner(getClass().getClassLoader().getResourceAsStream("ficheros/poblaciones.in"), "ISO-8859-1");
			while (s.hasNext()) {
				codigo = s.next();
				poblacion = s.nextLine().trim();
				listaNombrePoblaciones.add(poblacion);
				mapaPoblacionCodigo.put(poblacion, codigo);
			}
			s.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Carga el comboBox Autonomias y la lista de todas las poblaciones.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Ocultar el panel donde se mostraran los datos del tiempo al iniciar la aplicación
		datosTiempo.setVisible(false);
		tiempoHoy.setVisible(false);

		cargarComboAutonomias();
		cargarTodasLasPoblaciones();
		cargarListaVisitados();
		pintarListaVisitados();

		// Agregamos nuestra lista de poblaciones y el componente textField a la funcion bindAutoCompletion
		TextFields.bindAutoCompletion(busqueda, listaNombrePoblaciones);

		cajaBotonMinimizar.setOnMouseEntered(e -> {
			cambiarBrilloImagen(imagenMinimizar, 1);
		});
		cajaBotonMinimizar.setOnMouseExited(e -> {
			cambiarBrilloImagen(imagenMinimizar, -1);
		});

		cajaBotonCerrar.setOnMouseEntered(e -> {
			cambiarBrilloImagen(imagenCerrar, 1);
		});
		cajaBotonCerrar.setOnMouseExited(e -> {
			cambiarBrilloImagen(imagenCerrar, -1);
		});

	}

	/**
	 * Cambiar el brillo de la imágen
	 */
	private void cambiarBrilloImagen(ImageView imagen, int value) {
		ColorAdjust blanco = new ColorAdjust();
		blanco.setBrightness(value);
		imagen.setEffect(blanco);
	}

	/**
	 * Genera una lista de 3 poblaciones/ciudades con sus predicciones cargadas.
	 * Para que cuándo el usuario visite una localidad la lista se actualize.
	 */
	private void cargarListaVisitados() {
		Poblacion valencia = cargarDatosPoblacion("València");
		Poblacion madrid = cargarDatosPoblacion("Madrid");
		Poblacion barcelona = cargarDatosPoblacion("Barcelona");
		listaVisitados = new ArrayList<Poblacion>(Arrays.asList(valencia, madrid, barcelona));
	}

	/**
	 * Actualiza la lista de localidades visitadas
	 */
	private void actualizarListaVisitados() {
		boolean contains = false;
		for (Poblacion p : listaVisitados) {
			if (p.getNombre().equals(this.poblacion.getNombre())) {
				contains = true;
				break;
			}
		}
		if (!contains) {

			// LIMPIAR LOS OBJETOS QUE CONTIENE LA CAJA VISITADOS
			cajaListaVisitados.getChildren().clear();

			// ELIMINAR LA ULTIMA POBLACION DE LA LISTA Y AGREGAR AL INICIO LA ACTUAL
			listaVisitados.remove(listaVisitados.size()-1);
			listaVisitados.add(0, this.poblacion);

			pintarListaVisitados();
		}
	}

	/**
	 * Crear visualmente una lista de localidades ya visitadas.
	 */
	private void pintarListaVisitados() {

		int posYcaja = 0;

		// CREACION CAJA DONDE SE GUARDARAN LA LISTA VISITADOS
		cajaListaVisitados = new AnchorPane();
		cajaListaVisitados.setLayoutX(280);
		cajaListaVisitados.setPrefWidth(525);
		cajaListaVisitados.setPrefHeight(70);

		for (int i = 0; i < 3; i++) {

			AnchorPane caja = new AnchorPane();
			caja.setPrefHeight(70);
			caja.setPrefWidth(175);
			caja.setLayoutX(posYcaja);

			posYcaja += 189;

			Text nombre = new Text(listaVisitados.get(i).getNombre());
			nombre.setLayoutY(28);
			nombre.setLayoutX(48);
			nombre.setWrappingWidth(140);

			Text tempMaxima = new Text(listaVisitados.get(i).getPredicciones().get(i).getTemperatura().getMaxima() + "º");
			tempMaxima.setLayoutY(49);
			tempMaxima.setLayoutX(48);
			tempMaxima.setWrappingWidth(40);
			
			Text tempMinima = new Text(listaVisitados.get(i).getPredicciones().get(i).getTemperatura().getMinima() + "º");
			tempMinima.setLayoutY(49);
			tempMinima.setLayoutX(80);
			tempMinima.setWrappingWidth(40);

			ImageView imagenCielo = new ImageView(
									obtenerImagenCielo(listaVisitados.get(i)
									.descripcionCieloAhora(0)));
			imagenCielo.setFitWidth(30);
			imagenCielo.setFitHeight(30);
			imagenCielo.setLayoutX(7);
			imagenCielo.setLayoutY(17);

			// ESTILOS
			caja.getStyleClass().add("caja-lista-visitados");
			tempMinima.getStyleClass().add("temperaturaMin");
			tempMaxima.getStyleClass().add("temperaturaMax");

			caja.getChildren().addAll(nombre, tempMaxima, tempMinima, imagenCielo);
			cajaListaVisitados.getChildren().add(caja);

			// EVENTOS CAJA
			caja.setOnMouseClicked(e -> {
				cargarTiempo(nombre.getText());
			});

		}

		// AGREGAR LA CAJAVISITADOS YA GENERADO(CARGADO) AL PANEL BARTOP
		barTop.getChildren().add(cajaListaVisitados);
	}

}
