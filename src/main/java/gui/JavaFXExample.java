package gui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class JavaFXExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        GridPane gridPane = new GridPane();

        Label label = new Label("Log into your account ");
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");
        gridPane.add(label, 0, 0, 2, 1);

        Label usernameLabel = new Label("Username: ");
        usernameLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold");
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(new TextField(), 1, 1);

        Label passwordLabel = new Label("Password: ");
        passwordLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold");
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(new PasswordField(), 1, 2);


        Button button = new Button("Log In");
        button.setStyle("-fx-font-size: 12px; -fx-font-weight: bold");
        button.setPrefWidth(120);
        gridPane.add(button, 0, 3, 2, 1);
        GridPane.setHalignment(button, HPos.CENTER);


        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.setAlignment(Pos.CENTER);


        root.setCenter(gridPane);
        BorderPane.setAlignment(gridPane, Pos.CENTER);


        Scene scene = new Scene(root, 350, 300);

        stage.setTitle("JavaFX Example");
        stage.setScene(scene);
        stage.show();

    }
}
