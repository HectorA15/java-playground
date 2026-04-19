package tarea.simulacion.tema4.mersenneTwister;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MainMersenne extends Application {

    public static void main(String[] args) {
        System.out.println("Lanzando aplicación...");
        Application.launch(MainMersenne.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            System.out.println("=== Iniciando aplicación ===");

            URL fxmlUrl = getClass().getResource("/tarea.tema4/mersenneTwister/Mersenne.fxml");

            if (fxmlUrl == null) {
                throw new RuntimeException("Archivo FXML no encontrado");
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            primaryStage.setTitle("Simulador Mersenne Twister");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();

        } catch (Exception e) {
            System.err.println("ERROR al cargar la aplicación:");
            e.printStackTrace();
        }
    }
}