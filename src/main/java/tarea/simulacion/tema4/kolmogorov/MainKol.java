package tarea.simulacion.tema4.kolmogorov;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainKol extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/tarea.tema4/kolmogorov/kolmogorov.fxml")));

        primaryStage.setTitle("Kolmogorov Smirnov Test");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}
