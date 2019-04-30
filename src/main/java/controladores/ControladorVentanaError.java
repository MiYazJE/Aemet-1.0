package controladores;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControladorVentanaError implements Initializable {

    @FXML
    private Button boton = new Button();

    @FXML
    private TextArea textArea = new TextArea();

    public void salir() {
        Stage ventana = (Stage) boton.getScene().getWindow();
        ventana.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textArea.setEditable(false);
        textArea.setMouseTransparent(true);
        textArea.setFocusTraversable(false);
    }
}
