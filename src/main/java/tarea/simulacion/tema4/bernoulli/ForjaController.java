package tarea.simulacion.tema4.bernoulli;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import static java.lang.Math.random;

public class ForjaController {


    Bernoulli bernoulli;
    @FXML
    private TextArea txtAreaLog;
    @FXML
    private TextField txtMaterial;
    @FXML
    private TextField txtExitos;
    @FXML
    private TextField txtProbabilidad;
    @FXML
    private Label lblBernoulli;
    @FXML
    private Label lblBernoulli1;
    @FXML
    private VBox vbx;
    private int n = 0;
    private int k = 0;
    private double p = 0;

    @FXML
    public void iniciarForja(ActionEvent actionEvent) {
        // Resetea colores iniciales
        txtAreaLog.setStyle("-fx-control-inner-background: #191919; -fx-text-fill: #ffffff;");
        Ascii ascii = new Ascii();

        try {
            int n = Integer.parseInt(txtMaterial.getText());
            int k = Integer.parseInt(txtExitos.getText());
            double p = Double.parseDouble(txtProbabilidad.getText());
            if (p > 1.0) p /= 100.0;


            Timeline timeline = new Timeline();

            int exitosReales = 0;
            int tiempoAcumulado = 0;


            for (int i = 0; i < n; i++) {
                boolean exito = random() <= p;
                if (exito) exitosReales++;

                // JavaFX necesita que las variables usadas dentro del KeyFrame sean "finales"
                final boolean fueExito = exito;
                final int intentoActual = i + 1;

                // FEspada sola
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(tiempoAcumulado), e -> {
                    txtAreaLog.setStyle("-fx-control-inner-background: #000000; -fx-text-fill: #ffffff;");
                    txtAreaLog.setText("Forjando material " + intentoActual + " de " + n + "...\n\n" + ascii.s);
                }));

                // Martillo
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(tiempoAcumulado + 400), e -> txtAreaLog.setText(ascii.f2)));
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(tiempoAcumulado + 600), e -> txtAreaLog.setText(ascii.f3)));
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(tiempoAcumulado + 750), e -> txtAreaLog.setText(ascii.f4)));

                // Impacto + Flash
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(tiempoAcumulado + 900), e -> {
                    txtAreaLog.setText(ascii.f5);
                    if (fueExito) {
                        txtAreaLog.setStyle("-fx-control-inner-background: #FFFFFF; -fx-text-fill: #000000;");
                    }
                }));

                // F Resultado individual
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(tiempoAcumulado + 1400), e -> {
                    if (fueExito) {
                        txtAreaLog.setStyle("-fx-control-inner-background: #000000; -fx-text-fill: #00FF00;");
                        txtAreaLog.setText("¡+1 Nivel!\n\n" + ascii.s);
                    } else {
                        txtAreaLog.setStyle("-fx-control-inner-background: #000000; -fx-text-fill: #FF0000;");
                        txtAreaLog.setText("SE HA ROTO...\n\n" + ascii.r);
                    }
                }));

                // 3. ¡LA CLAVE! Aumentamos el tiempo acumulado para el SIGUIENTE material
                // Le damos 2000 ms (2 segundos) a cada ciclo para que haya una breve pausa entre golpes
                tiempoAcumulado += 3000;
            }

            // 4. FRAME FINAL: Resumen de toda la forja
            final int totalExitos = exitosReales;
            final boolean exitoTotal = (exitosReales >= k);

            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(tiempoAcumulado), e -> {
                txtAreaLog.setStyle("-fx-control-inner-background: #191919; -fx-text-fill: #FFFFFF;");
                txtAreaLog.setText("=== RESUMEN DE LA FORJA ===\n");
                txtAreaLog.appendText("Materiales usados: " + n + "\n");
                txtAreaLog.appendText("Niveles subidos: " + totalExitos + " / " + k + "\n\n");

            }));

            // 5. REPRODUCIMOS LA PELÍCULA COMPLETA
            timeline.play();

        } catch (Exception e) {
            txtAreaLog.setText("Error en los datos.");
        }
    }

    private void intentarActualizarLabel() {
        try {
            int tempN = Integer.parseInt(txtMaterial.getText());
            int tempK = Integer.parseInt(txtExitos.getText());
            double tempP = Double.parseDouble(txtProbabilidad.getText());

            if (tempP > 1.0) tempP = tempP / 100.0;

            if (tempK > tempN) {
                lblBernoulli.setText("La probabilidad de que " + tempN + " veces se forje y solo " + tempK + " salgan exitosos es de: 0.000%");
                lblBernoulli1.setText("La probabilidad de que " + tempN + " veces se forje y más de " + tempK + " salgan exitosos es de: 0.000%");
                return;
            }

            Bernoulli b = new Bernoulli(tempN, tempK, tempP);

            double probExacta = b.calBinomial(tempN, tempK, tempP) * 100;
            if (probExacta > 100) probExacta = 100;

            double probMasDe = 0;
            for (int i = tempK + 1; i <= tempN; i++) {
                probMasDe += b.calBinomial(tempN, i, tempP);
            }
            probMasDe = probMasDe * 100;
            if (probMasDe > 100) probMasDe = 100;

            String probExactaStr = String.format("%.3f", probExacta);
            String probMayorStr = String.format("%.3f", probMasDe);

            lblBernoulli.setText("La probabilidad de que " + tempN + " veces se forje y solo " + tempK + " salgan exitosos es de: " + probExactaStr + "%");
            lblBernoulli1.setText("La probabilidad de que " + tempN + " veces se forje y más de " + tempK + " salgan exitosos es de: " + probMayorStr + "%");

        } catch (NumberFormatException e) {
            lblBernoulli.setText("La probabilidad de que _ veces se forje y solo _ salgan exitosos es de: _%");
            lblBernoulli1.setText("La probabilidad de que _ veces se forje y más de _ salgan exitosos es de: _%");
        }
    }


    @FXML
    public void initialize() {
        txtAreaLog.setStyle("-fx-control-inner-background: #191919; -fx-text-fill: #ffffff;");
        vbx.setStyle("-fx-background-color: #191919; -fx-control-inner-background: #2e2e2e; -fx-text-fill: #ffffff;");

        Platform.runLater(() -> {
            vbx.lookupAll(".label").forEach(nodo -> nodo.setStyle("-fx-text-fill: white;"));
        });

        txtMaterial.textProperty().addListener((obs, oldVal, newVal) -> intentarActualizarLabel());
        txtExitos.textProperty().addListener((obs, oldVal, newVal) -> intentarActualizarLabel());
        txtProbabilidad.textProperty().addListener((obs, oldVal, newVal) -> intentarActualizarLabel());
    }


}
