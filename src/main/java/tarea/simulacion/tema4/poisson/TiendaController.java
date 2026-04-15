package tarea.simulacion.tema4.poisson;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Arrays;

import static tarea.simulacion.tema4.poisson.Poisson.factorial;

public class TiendaController {

    private final double DURACION_DIA_SEGUNDOS = 20.0; // la simulacion dura 20 segundos
    private Poisson poisson;
    @FXML
    private Pane paneTienda;
    @FXML
    private Button btnIniciar;
    @FXML
    private GridPane grid;
    @FXML
    private Slider sldLambda;
    @FXML
    private TextField txtLambda;
    @FXML
    private TextField txtEstado;
    @FXML
    private TextField txtHora;
    private int horaActual = 8;
    private int clientesAtendidos = 0;

    @FXML
    public void initialize() {
        // Configurar Slider
        sldLambda.setMin(1);
        sldLambda.setMax(50);
        sldLambda.setValue(5);
        txtLambda.setText("5");
        txtHora.setText("8:00");

        generarTablaProbabilidades(5); // actualizar la tabla al inicio


        // escuchar cuando se mueve el Slider
        sldLambda.valueProperty().addListener((obs, oldVal, newVal) -> {
            txtLambda.setText(String.format("%.0f", newVal.doubleValue()));
            generarTablaProbabilidades(newVal.doubleValue());
        });

        // escuchar cuando se escribe en el TextField
        txtLambda.textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                double valor = Double.parseDouble(newVal);
                if (valor >= 1 && valor <= 20) {
                    sldLambda.setValue(valor);
                }
            } catch (NumberFormatException e) {
                // Ignorar si escriben letras
            }
        });
    }

    private void generarTablaProbabilidades(double lambda) {
        // Borrar tdo, incluyendo las filas de Scene Builder
        grid.getChildren().clear();
        grid.getRowConstraints().clear(); // Borra las reglas de altura de las filas
        grid.getColumnConstraints().clear(); // Borra las reglas de ancho de las columnas

        grid.setHgap(15);
        grid.setVgap(5);

        // Encabezados de la tabla
        Label headerK = new Label("k");
        Label headerProb = new Label("P(X=k)");
        Label headerGrafica = new Label("");

        String estiloHeader = "-fx-text-fill: #AAAAAA; -fx-font-weight: bold;";
        headerK.setStyle(estiloHeader);
        headerProb.setStyle(estiloHeader);

        grid.add(headerK, 0, 0);
        grid.add(headerProb, 1, 0);
        grid.add(headerGrafica, 2, 0);

        // cuantas filas del gridpanel se generan
        int rangoMaximo = (int) lambda + 5 + (int) (lambda / 2);

        double maxProb = 0;
        for (int i = 0; i <= lambda; i++) {
            double p = (Math.exp(-lambda) * Math.pow(lambda, i)) / factorial(i);
            if (p > maxProb) maxProb = p;
        }

        // generar filas
        for (int k = 0; k <= rangoMaximo; k++) {
            double prob = (Math.exp(-lambda) * Math.pow(lambda, k)) / factorial(k);

            Label lblK = new Label(String.valueOf(k));
            Label lblProb = new Label(String.format("%.2f%%", prob * 100));

            Rectangle barra = new Rectangle();
            barra.setHeight(10);
            // Max ancho de la barra: 120 pixeles
            double anchoCalculado = (prob / maxProb) * 120.0;
            barra.setWidth(Math.max(1, anchoCalculado));
            barra.setFill(Color.web("#555555"));

            lblK.setId("fila_k_" + k);
            lblProb.setId("fila_prob_" + k);
            barra.setId("fila_barra_" + k);

            String estiloBase = "-fx-text-fill: #AAAAAA; -fx-font-family: 'Consolas'; -fx-font-size: 14px;";
            lblK.setStyle(estiloBase);
            lblProb.setStyle(estiloBase);

            // Al hacer add(), JavaFX crea la fila automáticamente si no existe
            grid.add(lblK, 0, k + 1);
            grid.add(lblProb, 1, k + 1);
            grid.add(barra, 2, k + 1);
        }
    }

    @FXML
    public void iniciarSimulacion(ActionEvent event) {
        btnIniciar.setDisable(true);
        sldLambda.setDisable(true);
        txtLambda.setDisable(true);

        txtEstado.setDisable(false);
        txtEstado.setText("ABIERTO");

        horaActual = 8;
        txtHora.setText(horaActual + ":00");
        // Crear el cronómetro: 1 hora cada 2 segundos
        Timeline cronometro = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            horaActual++;
            txtHora.setText(horaActual + ":00");
        }));

        // Calculamos cuántas veces debe repetirse: 30 seg / 2 seg = 15 horas
        cronometro.setCycleCount((int) (DURACION_DIA_SEGUNDOS / 2));

        // Borrar los elementos anteriores (clientes y productos) pero no el entorno ni la tabla de probabilidades
        paneTienda.getChildren().removeIf(node -> node instanceof Circle || "producto".equals(node.getId()));

        dibujarEntornoTienda();

        clientesAtendidos = 0;
        iluminarProbabilidadActual(0);

        double lambda = sldLambda.getValue();
        int totalClientesHoy = obtenerNumeroPoisson(lambda);


        // generamos tiempos de llegada aleatorios para cada cliente dentro del día (0 a DURACION_DIA_SEGUNDOS)
        double[] tiemposLlegada = new double[totalClientesHoy];
        for (int i = 0; i < totalClientesHoy; i++) {
            tiemposLlegada[i] = Math.random() * DURACION_DIA_SEGUNDOS;
        }
        Arrays.sort(tiemposLlegada); // Los ordenamos del primero al último

        //  Programamos las animaciones
        Timeline relojDelDia = new Timeline();

        for (int i = 0; i < totalClientesHoy; i++) {
            final int numeroCliente = i + 1;

            relojDelDia.getKeyFrames().add(new KeyFrame(Duration.seconds(tiemposLlegada[i]), e -> {
                clientesAtendidos = numeroCliente;
                animarClienteLlegando(clientesAtendidos);
            }));
        }

        // Volver a activar el botón
        relojDelDia.getKeyFrames().add(new KeyFrame(Duration.seconds(DURACION_DIA_SEGUNDOS + 4), e -> {
            btnIniciar.setDisable(false);
            sldLambda.setDisable(false);
            txtLambda.setDisable(false);
            txtEstado.setText("CERRADO");
            txtEstado.setDisable(true);
        }));

        cronometro.play();
        relojDelDia.play();
    }

    private void dibujarEntornoTienda() {
        if (paneTienda.getChildren().isEmpty()) {
            double anchoMesa = 300;
            double anchoSalida = 250;

            double anchoPane = paneTienda.getWidth();
            double altoPane = paneTienda.getHeight();

            // Dibujar la Mesa
            Rectangle mesa = new Rectangle(anchoMesa, 50, Color.web("#8B4513")); // Color madera
            mesa.setX((anchoPane / 2) - (anchoMesa / 2));
            mesa.setY(50);

            // Dibujar la Entrada
            Rectangle entrada = new Rectangle(anchoSalida, 10, Color.web("#555555"));
            entrada.setX((anchoPane / 2) - anchoSalida / 2);
            entrada.setY(altoPane - 10);

            Label lblEntrada = new Label("ENTRADA");
            lblEntrada.setStyle("-fx-text-fill: #AAAAAA; -fx-font-weight: bold;");
            lblEntrada.setLayoutX((anchoPane / 2) - 30);
            lblEntrada.setLayoutY(altoPane - 30);

            // Añadimos tdo al lienzo
            paneTienda.getChildren().addAll(mesa, entrada, lblEntrada);
        }
    }

    private void animarClienteLlegando(int numeroCliente) {
        iluminarProbabilidadActual(numeroCliente);

        double anchoPane = paneTienda.getWidth();
        double altoPane = paneTienda.getHeight();

        // el cliente (circulo azul)
        Circle cliente = new Circle(15, Color.web("#4287f5"));
        double entradaX = anchoPane / 2 + 60;
        double entradaY = altoPane;

        cliente.setCenterX(entradaX);
        cliente.setCenterY(entradaY);
        paneTienda.getChildren().add(cliente);

        // CREAR EL PRODUCTO (Caja dorada, oculta al inicio)
        Rectangle producto = new Rectangle(15, 15, Color.web("#FFD700"));
        producto.setId("producto"); // Le ponemos ID para poder borrarlo al reiniciar
        producto.setVisible(false); // Es invisible hasta que lo compra
        paneTienda.getChildren().add(producto);

        // --- ANIMACIÓN ---

        // Caminar de la entrada a la mesa (Tarda 1.5s)
        TranslateTransition irAMesa = new TranslateTransition(Duration.seconds(1.5), cliente);
        double distanciaYMesa = altoPane - 110; // Distancia hacia arriba
        irAMesa.setToX(0);
        irAMesa.setToY(-distanciaYMesa); // Sube en el eje Y

        // Pausa comprando (Tarda 2s)
        PauseTransition comprar = new PauseTransition(Duration.seconds(2));
        comprar.setOnFinished(e -> {
            // Se da media vuelta (cambia de color a verde)
            cliente.setFill(Color.web("#32a852"));

            // Aparece el producto justo a su derecha
            producto.setX(cliente.getCenterX() + cliente.getTranslateX() + 20);
            producto.setY(cliente.getCenterY() + cliente.getTranslateY() - 10);
            producto.setVisible(true);
        });
        // El producto y el cliente se mueven juntos hacia la izquierda (Tarda 0.5s)
        TranslateTransition izquierdaProducto = new TranslateTransition(Duration.seconds(0.5), producto);
        izquierdaProducto.setByX(-130); // El producto baja la misma distancia

        TranslateTransition irIzquierda = new TranslateTransition(Duration.seconds(0.5), cliente);
        irIzquierda.setToX(-130);
        ParallelTransition izquierdaJuntos = new ParallelTransition(irIzquierda, izquierdaProducto);

        // el producto y el cliente se mueven hacia abajo para salir de la tienda (Tarda 1.5s)
        TranslateTransition salirProducto = new TranslateTransition(Duration.seconds(1.5), producto);
        salirProducto.setByY(distanciaYMesa); // El producto baja la misma distancia

        TranslateTransition salir = new TranslateTransition(Duration.seconds(1.5), cliente);
        salir.setByY(distanciaYMesa);
        ParallelTransition salirJuntos = new ParallelTransition(salir, salirProducto);


        // Al terminar de salir, desaparecen del lienzo
        salirJuntos.setOnFinished(e -> paneTienda.getChildren().removeAll(cliente, producto));

        // Iniciar animacion
        SequentialTransition secuenciaCompleta = new SequentialTransition(irAMesa, comprar, izquierdaJuntos, salirJuntos);
        secuenciaCompleta.play();
    }

    // Algoritmo de Knuth para simular un evento de Poisson
    // literal es poisson pero se le aplica la aleatoriedad para que ocurra con la probabilidad de poisson
    // uno de esos eventos al azar
    private int obtenerNumeroPoisson(double lambda) {
        double L = Math.exp(-lambda); // Probabilidad de que no llegue ningún cliente (0 eventos)
        int k = 0; // la cantidad de clientes que llegan en el día
        double p = 1.0; // Probabilidad de que lleguen k clientes, se va multiplicando por números aleatorios para simular la llegada real
        // generamos un numero aleatorio entre 0 y 1,
        // y lo multiplicamos por la probabilidad acumulada de que lleguen k clientes
        do {
            k++;
            p = p * Math.random();
        } while (p > L);
        return k - 1; // Restamos 1 porque el ciclo se ejecuta una vez más después de que p ya no es mayor que L
    }

    // Ilumina la fila correspondiente en la tabla de la derecha
    private void iluminarProbabilidadActual(int clientesActuales) {
        // regresamos letras a gris y barras a gris oscuro
        grid.getChildren().forEach(nodo -> {
            if (nodo instanceof Label) {
                nodo.setStyle("-fx-text-fill: #AAAAAA; -fx-font-family: 'Consolas'; -fx-font-size: 14px;");
            } else if (nodo instanceof Rectangle) {
                ((Rectangle) nodo).setFill(Color.web("#555555"));
            }
        });

        // buscamos la fila actual y la encendemos
        Label lblK = (Label) grid.lookup("#fila_k_" + clientesActuales);
        Label lblProb = (Label) grid.lookup("#fila_prob_" + clientesActuales);
        Rectangle barra = (Rectangle) grid.lookup("#fila_barra_" + clientesActuales);

        String estiloBrillante = "-fx-text-fill: #00FF00; -fx-font-weight: bold; -fx-font-size: 16px;";

        if (lblK != null) lblK.setStyle(estiloBrillante);
        if (lblProb != null) lblProb.setStyle(estiloBrillante);
        if (barra != null) barra.setFill(Color.web("#00FF00")); // Barra verde brillante
    }
}
