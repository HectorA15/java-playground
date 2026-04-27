package tarea.simulacion.tema4.MonteCarlo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainMonte extends Application {

    public static void main(String[] args) {
        System.out.println("Lanzando aplicación...");
        Application.launch(MainMonte.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("=== Iniciando aplicación ===");

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/tarea.tema4/monteCarlo/MonteCarlo.fxml")));

        stage.setTitle("Simulador Monte Carlo");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();

    }
}
