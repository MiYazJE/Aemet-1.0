package controladores;

import animatefx.animation.Bounce;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ControladorBienvenido implements Initializable {

    @FXML Circle circulo1;
    @FXML Circle circulo2;
    @FXML Circle circulo3;

    private double posX;
    private double posY;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Bounce(circulo1).setCycleDuration(10).setCycleCount(10).setDelay(Duration.valueOf("500ms")).play();
        new Bounce(circulo2).setCycleDuration(10).setCycleCount(10).setDelay(Duration.valueOf("1000ms")).play();
        new Bounce(circulo3).setCycleDuration(10).setCycleCount(10).setDelay(Duration.valueOf("1100ms")).play();
        new iniciarVentana().start();
    }

    class iniciarVentana extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(10);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/fxml/aemet.fxml"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Stage stage = new Stage();
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.initStyle(StageStyle.TRANSPARENT);
                        Scene scene = new Scene(root);
                        scene.setFill(Color.TRANSPARENT);
                        stage.setScene(scene);

                        root.setOnMousePressed(e -> {
                            posX = stage.getX() - e.getScreenX();
                            posY = stage.getY() - e.getScreenY();
                        });
                        root.setOnMouseDragged(e -> {
                            stage.setX(e.getScreenX() + posX);
                            stage.setY(e.getScreenY() + posY);
                        });

                        // Ocultar el actual stage
                        circulo1.getScene().getWindow().hide();

                        // Mostrar la nueva ventana
                        stage.show();
                    }
                });

            } catch (Exception e) {};
        }

    }

}
