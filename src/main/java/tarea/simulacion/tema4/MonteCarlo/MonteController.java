package tarea.simulacion.tema4.MonteCarlo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


public class MonteController {

    private final double DELTA_TIME = 0.016;  // ~60 FPS
    @FXML
    Canvas plinko;
    @FXML
    Pane panelPlinko;
    PlinkoLogic plinkoLogic;
    @FXML
    private Label lblLanzamientos;
    @FXML
    private Label lblFilas;
    private int totalPelotas = 0;
    private int totalLanzamientos = 0;
    private List<Pelota> pelotas = new ArrayList<>();
    private int numFilas = 10;
    private Timeline timeline;

    public void initialize() {
        plinkoLogic = new PlinkoLogic(numFilas);

        panelPlinko.widthProperty().addListener((obs, oldV, newV) -> ajustarYRedibujar());
        panelPlinko.heightProperty().addListener((obs, oldV, newV) -> ajustarYRedibujar());

        ajustarYRedibujar();
    }

    private void ajustarYRedibujar() {
        double w = panelPlinko.getWidth();
        double h = panelPlinko.getHeight();

        if (w <= 0 || h <= 0) return;

        plinko.setWidth(w);
        plinko.setHeight(h);
        plinko.setLayoutX(0);
        plinko.setLayoutY(0);

        if (plinkoLogic.getClavos().isEmpty()) {
            double centroX = w / 2;
            double margenSuperior = 30;
            double altoDisponible = h - margenSuperior - 50;
            plinkoLogic.inicializarClavos(centroX, margenSuperior, altoDisponible);
        }

        dibujarPlinko(plinko);
    }

    public void dibujarPlinko(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        int filas = plinkoLogic.getNumFilas();
        double centroX = canvas.getWidth() / 2;
        double espacioHorizontal = 70;

        // Dibuja clavos
        gc.setFill(Color.WHITE);
        for (Clavo clavo : plinkoLogic.getClavos()) {
            gc.fillOval(clavo.getX() - clavo.getRadio(), clavo.getY() - clavo.getRadio(),
                    clavo.getRadio() * 2, clavo.getRadio() * 2);
        }

        // Dibuja todas las pelotas animadas
        gc.setFill(Color.RED);
        for (Pelota pelota : pelotas) {
            gc.fillOval(pelota.getX() - pelota.getRadio(),
                    pelota.getY() - pelota.getRadio(),
                    pelota.getRadio() * 2, pelota.getRadio() * 2);
        }
        gc.setFill(Color.WHITE);

        // Dibuja cajas
        int numContenedores = plinkoLogic.getCantidadContenedores();
        double altoCaja = 30;
        double y = canvas.getHeight() - altoCaja + 0;

        int clavosFila = filas + 1;
        double anchoPiramide = espacioHorizontal * clavosFila;
        double anchoCaja = espacioHorizontal;
        double xInicio = centroX - (anchoPiramide / 2.0);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        for (int i = 0; i < numContenedores; i++) {
            double x = xInicio + (anchoCaja * i);

            gc.strokeRect(x, y, anchoCaja, altoCaja);

            int cantidadPelotas = plinkoLogic.getContenedores()[i];
            gc.setFill(Color.BLACK);
            double porcentaje = 0;
            if (cantidadPelotas != 0) {
                porcentaje = cantidadPelotas / (double) totalPelotas * 100;
            }

            String text = String.format("%.2f%%", porcentaje);
            gc.setFill(Color.WHITE);
            gc.fillText((text), x + anchoCaja / 2 - 5, y + altoCaja / 2 + 5);
        }
    }

    private void iniciarAnimacion() {
        // Si ya hay una animación, no crees otra
        if (timeline != null && timeline.getStatus().equals(Animation.Status.RUNNING)) {
            return;
        }

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
                Duration.millis(16),
                event -> {
                    // Actualiza todas las pelotas
                    for (int i = pelotas.size() - 1; i >= 0; i--) {
                        Pelota pelota = pelotas.get(i);

                        // Física
                        pelota.update(DELTA_TIME);

                        // Colisiones con clavos
                        for (Clavo clavo : plinkoLogic.getClavos()) {
                            pelota.verificarColision(clavo.getX(), clavo.getY(), clavo.getRadio());
                        }

                        // Rebote en bordes
                        if (pelota.getX() < 0) {
                            pelota.setX(0);
                            pelota.setVx(-pelota.getVx() * 0.8);
                        } else if (pelota.getX() > plinko.getWidth()) {
                            pelota.setX(plinko.getWidth());
                            pelota.setVx(-pelota.getVx() * 0.8);
                        }

                        // Verifica si cayó en una caja
                        double altoCaja = 30;
                        double y = plinko.getHeight() - altoCaja - 10;
                        if (pelota.getY() > y) {
                            // Calcula en qué caja cayó
                            int numContenedores = plinkoLogic.getCantidadContenedores();
                            int cajaIndex = (int) ((pelota.getX() / plinko.getWidth()) * numContenedores);
                            cajaIndex = Math.max(0, Math.min(cajaIndex, numContenedores - 1));

                            // ACTUALIZA LA CAJA AQUÍ
                            totalPelotas++;
                            plinkoLogic.getContenedores()[cajaIndex]++;

                            // Remueve la pelota
                            pelotas.remove(i);
                        }
                    }

                    // Si no hay pelotas, detén la animación
                    if (pelotas.isEmpty()) {
                        timeline.stop();
                    }

                    dibujarPlinko(plinko);
                }
        );
        timeline.getKeyFrames().add(kf);

        timeline.play();
    }

    private void crearPelota() {
        Pelota p = new Pelota(plinko.getWidth() / 2, 50, 5);
        p.darVelocidadInicial();
        pelotas.add(p);
    }

    @FXML
    private void lanzar1() {
        crearPelota();
        totalLanzamientos++;
        lblLanzamientos.setText(String.valueOf(totalLanzamientos));
        iniciarAnimacion();
    }

    @FXML
    private void lanzar100() {
        for (int i = 0; i < 100; i++) {
            crearPelota();
        }
        totalLanzamientos += 100;
        lblLanzamientos.setText(String.valueOf(totalLanzamientos));
        iniciarAnimacion();
    }

    @FXML
    private void lanzar10000() {
        for (int i = 0; i < 10000; i++) {
            crearPelota();
        }
        totalLanzamientos += 10000;
        lblLanzamientos.setText(String.valueOf(totalLanzamientos));
        iniciarAnimacion();
    }

    @FXML
    private void reiniciar() {
        if (timeline != null) {
            timeline.stop();
        }
        plinkoLogic = new PlinkoLogic(numFilas);
        pelotas.clear();
        totalLanzamientos = 0;

        double w = plinko.getWidth();
        double h = plinko.getHeight();
        double centroX = w / 2;
        double margenSuperior = 30;
        double altoDisponible = h - margenSuperior - 50;
        plinkoLogic.inicializarClavos(centroX, margenSuperior, altoDisponible);

        lblLanzamientos.setText("0");
        ajustarYRedibujar();
    }
}