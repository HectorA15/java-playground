package tarea.simulacion.tema4.chicuadrada;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class ChiController {

    @FXML
    private TextField txtIteraciones, txtIntervalos;

    @FXML
    private Slider slider;

    @FXML
    private BarChart<String, Number> graficaChi;

    @FXML
    private Label grados;

    @FXML
    private Label puntoCritico;

    @FXML
    private Label estado;

    private int iteraciones = 0;

    @FXML
    public void initialize() {
        txtIteraciones.setText("0");

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            iteraciones = newValue.intValue();
            txtIteraciones.setText(String.valueOf(iteraciones));
            actualizarGrafica(iteraciones);
        });

        txtIntervalos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d+")) {
                return;
            }
            actualizarGrafica((int) slider.getValue());
        });
    }

    private void actualizarGrafica(int iteraciones) {
        if (graficaChi == null || txtIntervalos.getText().isEmpty()) return;

        int n;
        try {
            n = Integer.parseInt(txtIntervalos.getText());
            if (n <= 0) return;
        } catch (NumberFormatException e) {
            return;
        }

        double[] numerosAleatorios = new double[iteraciones];
        for (int i = 0; i < iteraciones; i++) {
            numerosAleatorios[i] = Math.random();
        }

        ChiCuadrada motorChi = new ChiCuadrada(n, numerosAleatorios);

        motorChi.calChiCuadrada();


        int numGradosLibertad = motorChi.calGradosLibertad();
        double valorPuntoCritico = motorChi.calPuntoCritico();
        boolean aprueba = motorChi.isUniforme();
        int[] frecuenciasFinales = motorChi.getFrecuenciaObservada();


        if (grados != null) {
            grados.setText(String.valueOf(numGradosLibertad));
        }

        if (puntoCritico != null) {
            puntoCritico.setText(String.format("%.4f", valorPuntoCritico));
        }

        if (estado != null) {
            if (aprueba) {
                estado.setText("APROBADO");
                estado.setTextFill(Color.web("#2ecc71")); // Verde
            } else {
                estado.setText("RECHAZADO");
                estado.setTextFill(Color.web("#e74c3c")); // Rojo
            }
        }


        graficaChi.getData().clear();
        graficaChi.setLegendVisible(false);

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Frecuencias");

        for (int i = 0; i < n; i++) {
            double a = (double) i / n;
            double b = (double) (i + 1) / n;
            String etiqueta = String.format("[%.2f, %.2f)", a, b);

            serie.getData().add(new XYChart.Data<>(etiqueta, frecuenciasFinales[i]));
        }

        graficaChi.getData().add(serie);
        graficaChi.setAnimated(false);

    }
}