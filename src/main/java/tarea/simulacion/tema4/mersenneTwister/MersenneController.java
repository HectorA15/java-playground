package tarea.simulacion.tema4.mersenneTwister;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;


public class MersenneController {
    @FXML
    private Button btnMersenne;

    @FXML
    private Slider slider;

    @FXML
    private TextField txtNum;

    @FXML
    private Canvas lienzo;

    @FXML
    public void initialize() {

        slider.setMin(0);
        slider.setMax(10000);
        slider.setValue(0);
        txtNum.setText("0");

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            txtNum.setText(String.format("%.0f", newValue.doubleValue()));
        });
    }


    @FXML
    public void mostrarMT(ActionEvent event) {


    }

    @FXML
    public void mostrarRND(ActionEvent event) {
    }

    @FXML
    public void mostrarMS(ActionEvent event) {
    }

    @FXML
    public void mostrarLFSR(ActionEvent event) {
    }


}
