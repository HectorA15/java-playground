package tarea.simulacion.tema4.mersenneTwister;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.apache.commons.math3.random.MersenneTwister;

import java.util.EnumMap;
import java.util.Map;

public class MersenneController {

    private final Map<TipoGrafico, Canvas> canvases = new EnumMap<>(TipoGrafico.class);
    @FXML
    private Slider slider;
    @FXML
    private TextField txtNum;
    @FXML
    private GridPane gridGraficos;
    private final Map<TipoGrafico, AnimationTimer> timers = new EnumMap<>(TipoGrafico.class);
    private final Map<TipoGrafico, MersenneTwister> mtMap = new EnumMap<>(TipoGrafico.class);
    private final Map<TipoGrafico, RanduGen> randuMap = new EnumMap<>(TipoGrafico.class);
    private final Map<TipoGrafico, MsGen> msMap = new EnumMap<>(TipoGrafico.class);
    private final Map<TipoGrafico, LfsrGen> lfsrMap = new EnumMap<>(TipoGrafico.class);
    @FXML
    private ToggleButton btnMT, btnRND, btnMS, btnLFSR;

    private void toggle(TipoGrafico tipo, ToggleButton btn) {
        if (btn.isSelected()) {
            Canvas c = new Canvas(360, 250);
            c.setStyle("-fx-border-color: #444; -fx-border-width: 1;");
            canvases.put(tipo, c);
            crearGenerador(tipo);
            reorganizarGrid();
            dibujar(tipo, c);
        } else {
            AnimationTimer t = timers.remove(tipo);
            if (t != null) t.stop();
            Canvas c = canvases.remove(tipo);
            if (c != null) gridGraficos.getChildren().remove(c);
            borrarGenerador(tipo);
            reorganizarGrid();
        }
    }

    @FXML
    public void initialize() {
        slider.setMin(100);
        slider.setMax(10000);
        slider.setValue(1000);
        txtNum.setText(String.format("%.0f", slider.getValue()));

        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            txtNum.setText(String.format("%.0f", newVal.doubleValue()));
            redibujarActivos();
        });

        btnMT.setOnAction(e -> toggle(TipoGrafico.MT, btnMT));
        btnRND.setOnAction(e -> toggle(TipoGrafico.RND, btnRND));
        btnMS.setOnAction(e -> toggle(TipoGrafico.MS, btnMS));
        btnLFSR.setOnAction(e -> toggle(TipoGrafico.LFSR, btnLFSR));
    }

    private void crearGenerador(TipoGrafico tipo) {
        switch (tipo) {
            case MT -> mtMap.put(tipo, new MersenneTwister());
            case RND -> randuMap.put(tipo, new RanduGen(123456789));
            case MS -> msMap.put(tipo, new MsGen(System.nanoTime()));
            case LFSR -> lfsrMap.put(tipo, new LfsrGen(0xACE1));
        }
    }

    private void borrarGenerador(TipoGrafico tipo) {
        mtMap.remove(tipo);
        randuMap.remove(tipo);
        msMap.remove(tipo);
        lfsrMap.remove(tipo);
    }

    private String nombreCompleto(TipoGrafico tipo) {
        return switch (tipo) {
            case MT -> "Mersenne Twister";
            case RND -> "Randu";
            case MS -> "Middle Square";
            case LFSR -> "Linear Feedback Shift Register";
        };
    }

    private void reorganizarGrid() {
        gridGraficos.getChildren().clear();
        int i = 0;
        for (TipoGrafico t : TipoGrafico.values()) {
            Canvas c = canvases.get(t);
            if (c != null) {
                gridGraficos.add(c, i % 2, i / 2);
                i++;
            }
        }
    }

    private void redibujarActivos() {
        for (TipoGrafico tipo : TipoGrafico.values()) {
            Canvas c = canvases.get(tipo);
            if (c != null) dibujar(tipo, c);
        }
    }

    private void dibujar(TipoGrafico tipo, Canvas canvas) {
        AnimationTimer old = timers.remove(tipo);
        if (old != null) old.stop();

        int total = (int) slider.getValue();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double w = canvas.getWidth();
        double h = canvas.getHeight();

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, w, h);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        gc.fillText(nombreCompleto(tipo), 8, 14);
        gc.setFill(color(tipo));


        AnimationTimer timer = new AnimationTimer() {
            int count = 0;
            final int porFrame = 800;

            @Override
            public void handle(long now) {
                for (int i = 0; i < porFrame && count < total; i++) {
                    double x = siguiente(tipo) * w;
                    double y = siguiente(tipo) * h + 20;
                    gc.fillOval(x, y, 2, 2);
                    count++;
                }


                if (count >= total) stop();
            }

        };


        timers.put(tipo, timer);
        timer.start();
    }

    private double siguiente(TipoGrafico tipo) {
        return switch (tipo) {
            case MT -> mtMap.get(tipo).nextDouble();
            case RND -> randuMap.get(tipo).next();
            case MS -> msMap.get(tipo).next();
            case LFSR -> lfsrMap.get(tipo).next();
        };
    }

    enum TipoGrafico {MT, RND, MS, LFSR}

    private Color color(TipoGrafico t) {
        return switch (t) {
            case MT -> Color.CYAN;
            case RND -> Color.LIME;
            case MS -> Color.RED;
            case LFSR -> Color.YELLOW;
        };
    }

    static class MsGen {
        long x;
        int repeats = 0;
        long last = -1;

        MsGen(long seed) {
            x = Math.abs(seed % 100000);
            if (x < 10000) x += 10000;
        }

        double next() {
            long sq = x * x;
            String s = String.format("%010d", sq);
            x = Long.parseLong(s.substring(3, 8));

            if (x == last) repeats++;
            else repeats = 0;

            if (x == 0 || repeats > 3) {
                x = (System.nanoTime() % 90000) + 10000;
                repeats = 0;
            }

            last = x;
            return x / 100000.0;
        }
    }

    static class LfsrGen {
        int state;

        LfsrGen(int seed) {
            state = seed == 0 ? 0xACE1 : seed;
        }

        double next() {
            int lsb = state & 1;
            state >>>= 1;
            if (lsb == 1) state ^= 0xB400;
            return (state & 0xFFFF) / 65536.0;
        }
    }

    static class RanduGen {
        static final long A = 65539;
        static final long M = 1L << 31;
        long x;

        RanduGen(long seed) {
            x = seed % M;
            if (x <= 0) x = 1;
        }

        double next() {
            x = (A * x) % M;
            return (double) x / M;
        }
    }
}