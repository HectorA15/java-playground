package tarea.topicos.tema4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainDB extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/db/DB.fxml")));
        primaryStage.setTitle("Database Connection");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
