package tarea.simulacion;

import java.util.Random;

import static java.util.Arrays.sort;

public class KolmogorovSmirnov {
    private static final int TOTAL_NUMEROS = 1000;

    public static void main(String[] args) {


        double[] numeros = new double[TOTAL_NUMEROS];
        Random random = new Random();

        for (int i = 0; i < TOTAL_NUMEROS; i++) {
            numeros[i] = random.nextDouble();
        }

        // Ordenar el arreglo de números generados
        sort(numeros);

        double esquinaSuperior = 0.0;
        double esquinaInferior = 0.0;
        double distanciaMaxima = 0.0;
        double puntoCritico = 1.36 / Math.sqrt(TOTAL_NUMEROS);


        for (int i = 0; i < TOTAL_NUMEROS; i++) {
            esquinaSuperior = (i + 1.0) / TOTAL_NUMEROS - numeros[i];
            esquinaInferior = numeros[i] - (i * 1.0) / TOTAL_NUMEROS;

            double mayorDistancia = Math.max(esquinaSuperior, esquinaInferior);

            distanciaMaxima = Math.max(distanciaMaxima, mayorDistancia);
        }

        System.out.println("--- ---------------- ---");
        System.out.println("Distancia maxima (D): " + distanciaMaxima);
        System.out.println("Punto crítico de la tabla: " + puntoCritico);

        if (distanciaMaxima <= puntoCritico) {
            System.out.println("Pasa la prueba, los números tienen distribución uniforme.");
        } else {
            System.out.println("Falla la prueba, el generador es probable de que tenga sesgo.");
        }

    }
}
