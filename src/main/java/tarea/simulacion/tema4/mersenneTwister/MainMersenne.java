package tarea.simulacion.tema4.mersenneTwister;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMersenne extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // El FXMLLoader lee tu archivo XML y construye la ventana
        Parent root = FXMLLoader.load(getClass().getResource("/tarea.tema4/mersenneTwister/Mersenne.fxml"));

        primaryStage.setTitle("Simulador Mersenne Twister");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
