package tarea.simulacion.tema4.chicuadrada;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainChi extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/tarea.tema4/chicuadrada/ChiCuadrada.fxml")));

        primaryStage.setTitle("Chi Cuadrada Test");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
