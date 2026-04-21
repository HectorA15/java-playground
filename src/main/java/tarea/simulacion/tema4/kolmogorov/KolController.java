package tarea.simulacion.tema4.kolmogorov;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class KolController {

    @FXML
    TextField txtDatos;
    @FXML
    Label lblDplus, lblDminus, lblResultado;
    @FXML
    private Slider slider;
    private Kolmogorov kolmogorov;

    @FXML
    private LineChart<Number, Number> graficoKolmogorov;

    @FXML
    public void initialize() {
        slider.setMin(1);
        slider.setMax(300);
        slider.setValue(100);

        txtDatos.setText("0");
        lblDplus.setText("0");
        lblDminus.setText("0");


        Kolmogorov kolmogorov = new Kolmogorov(100);
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int totalNumeros = (int) slider.getValue();
            kolmogorov.setNumerosTotales(totalNumeros);
            txtDatos.setText(String.format("%.0f", newVal.doubleValue()));
            actualizarGrafica(totalNumeros);
            lblDplus.setText(String.format("%.2f", kolmogorov.calcularDistanciaMaxima(kolmogorov.generarNumerosOrdenados())));
            lblDminus.setText(String.format("%.2f", kolmogorov.calcularPuntoCritico()));
            lblResultado.setText(kolmogorov.ejecutarPrueba().pasaPrueba() ? "Aprueba" : "No aprueba");
        });

        txtDatos.textProperty().addListener((obs, oldVal, newVal) -> {
            int totalNumeros;
            try {
                totalNumeros = Integer.parseInt(newVal);
                if (totalNumeros > 0) {
                    slider.setValue(totalNumeros);
                }
            } catch (NumberFormatException e) {
                totalNumeros = (int) slider.getValue();
            }
            kolmogorov.setNumerosTotales(totalNumeros);
            slider.setValue(Double.parseDouble(newVal));
            lblDplus.setText(String.format("%.2f", kolmogorov.calcularDistanciaMaxima(kolmogorov.generarNumerosOrdenados())));
            lblDminus.setText(String.format("%.2f", kolmogorov.calcularPuntoCritico()));
            lblResultado.setText(kolmogorov.ejecutarPrueba().pasaPrueba() ? "Aprueba" : "No aprueba");
        });


    }

    public void actualizarGrafica(int n) {
        graficoKolmogorov.getData().clear();
        Kolmogorov k = new Kolmogorov(n);
        Kolmogorov.ResultadoKolmogorov resultado = k.ejecutarPrueba();
        double[] numeros = resultado.numerosOrdenados();


        // Crear las series para la grafica de Kolmogorov
        XYChart.Series<Number, Number> serieTeorica = new XYChart.Series<>();
        serieTeorica.getData().add(new XYChart.Data<>(0.0, 0.0));
        serieTeorica.getData().add(new XYChart.Data<>(1.0, 1.0));


        XYChart.Series<Number, Number> serieEmpirica = new XYChart.Series<>();
        serieEmpirica.setName("Empírica (Datos)");

        serieEmpirica.getData().add(new XYChart.Data<>(0.0, 0.0));

        // Bucle para dibujar los escalones rectos
        for (int i = 0; i < numeros.length; i++) {
            double x = numeros[i]; // El numero generado

            // La altura de la linea en Y antes y después de subir el escalon
            double yAnterior = (double) i / numeros.length;
            double yNuevo = (double) (i + 1) / numeros.length;

            serieEmpirica.getData().add(new XYChart.Data<>(x, yAnterior)); // Dibujamos la linea horizontal hasta chocar con el valor X
            serieEmpirica.getData().add(new XYChart.Data<>(x, yNuevo)); // Dibujamos la linea vertical subiendo al siguiente escalon
        }


        serieEmpirica.getData().add(new XYChart.Data<>(1.0, 1.0));

        graficoKolmogorov.getData().addAll(serieTeorica, serieEmpirica);
        graficoKolmogorov.setCreateSymbols(false);
        graficoKolmogorov.setAnimated(false);
        graficoKolmogorov.setLegendVisible(false);


    }
}
