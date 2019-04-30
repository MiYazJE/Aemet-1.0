package interfaz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Aplicacion extends Application {

    private double posX;
    private double posY;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/bienvenido.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);

        // MOVER LA VENTANA
        root.setOnMousePressed(e -> {
            posX = primaryStage.getX() - e.getScreenX();
            posY = primaryStage.getY() - e.getScreenY();
        });
        root.setOnMouseDragged(e -> {
            primaryStage.setX(e.getScreenX() + posX);
            primaryStage.setY(e.getScreenY() + posY);
        });

        // PANEL DE BIENBENIDA
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setScene(new Scene(root, 647, 384));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
