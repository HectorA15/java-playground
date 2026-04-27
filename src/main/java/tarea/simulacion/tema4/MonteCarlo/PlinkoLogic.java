package tarea.simulacion.tema4.MonteCarlo;

import java.util.ArrayList;
import java.util.List;

public class PlinkoLogic {

    private final double PROBABILIDAD_DERECHA = 0.50;
    private final double RADIO_CLAVO = 6;
    private final double ESPACIADO_HORIZONTAL = 70;
    private final double ESPACIADO_VERTICAL_RATIO = 0.15;  // Proporción del alto
    private int[] contenedores;
    private int numFilas;
    private List<Clavo> clavos;

    public PlinkoLogic(int numFilas) {
        this.numFilas = numFilas;
        this.contenedores = new int[numFilas + 1];
        this.clavos = new ArrayList<>();
    }

    public void inicializarClavos(double centroX, double margenSuperior, double altoDisponible) {
        clavos.clear();
        double espacioVertical = altoDisponible / (numFilas - 1);

        for (int i = 1; i < numFilas; i++) {  // Empieza en 1 (sin fila 0)
            int numClavos = i + 1;

            for (int j = 0; j < numClavos; j++) {
                double x = centroX - (ESPACIADO_HORIZONTAL * numClavos / 2.0) + (ESPACIADO_HORIZONTAL * (j + 0.5));
                double y = margenSuperior + (espacioVertical * i);
                clavos.add(new Clavo(x, y, RADIO_CLAVO));
            }
        }
    }

    public List<Clavo> getClavos() {
        return clavos;
    }

    public int getNumFilas() {
        return numFilas;
    }

    public int getCantidadContenedores() {
        return contenedores.length;
    }

    public int[] getContenedores() {
        return contenedores;
    }

    public void tirarPelota() {
        int posicionFinal = 0;
        for (int i = 0; i < numFilas; i++) {
            if (Math.random() < PROBABILIDAD_DERECHA) {
                posicionFinal++;
            }
        }
        contenedores[posicionFinal]++;
    }

    public void simularPlinko(int cantidadPelotas) {
        for (int i = 0; i < cantidadPelotas; i++) {
            tirarPelota();
        }
    }
}