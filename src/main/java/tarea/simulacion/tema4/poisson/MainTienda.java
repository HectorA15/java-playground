package tarea.simulacion.tema4.poisson;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainTienda extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // El FXMLLoader lee tu archivo XML y construye la ventana
        Parent root = FXMLLoader.load(getClass().getResource("/Poisson.fxml"));

        primaryStage.setTitle("Simulador Poisson - Forja");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
