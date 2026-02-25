package tarea.simulacion;

import java.util.Scanner;

// clase principal - el archivo se llama SimuladorServidor.java
public class SimuladorServidor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // iniciamos el generador Mersenne Twister con el tiempo actual
        MersenneTwister mt = new MersenneTwister(System.currentTimeMillis());

        System.out.println("=== PRUEBA DE ESTRES DE SERVIDOR  ===");

        System.out.print("Ingrese la capacidad maxima del servidor (peticiones por minuto): ");
        int capacidadMaxima = scanner.nextInt();

        System.out.print("Ingrese cuantos minutos desea simular: ");
        int minutos = scanner.nextInt();

        int caidas = 0;
        int traficoTotal = 0;

        System.out.println("\nIniciando simulacion de trafico con Mersenne Twister...");
        System.out.println("------------------------------------------------------");

        for (int i = 1; i <= minutos; i++) {
            // sacamos un trafico base del 70% de la capacidad
            int traficoBase = (int) (capacidadMaxima * 0.7);

            // el Mersenne Twister mete un pico random entre 0 y el 35% de la capacidad
            int picoAleatorio = mt.generarRango(0, (int) (capacidadMaxima * 0.35));

            // el trafico real del minuto es la suma de los dos
            int peticionesDelMinuto = traficoBase + picoAleatorio;

            traficoTotal += peticionesDelMinuto;

            if (peticionesDelMinuto > capacidadMaxima) {
                caidas++;
                System.out.println("Minuto " + i + ": ¡ALERTA! " + peticionesDelMinuto + " peticiones. El servidor colapso.");
            }
        }

        System.out.println("------------------------------------------------------");
        System.out.println("=== REPORTE FINAL DE AUDITORIA DE INFRAESTRUCTURA ===");
        System.out.println("Tiempo simulado: " + minutos + " minutos.");
        System.out.println("Trafico total procesado: " + traficoTotal + " peticiones.");
        System.out.println("Caidas del servidor: " + caidas);

        if (caidas == 0) {
            System.out.println("Conclusion: La infraestructura es estable y soporto la prueba.");
        } else {
            System.out.println("Conclusion: Se necesita escalar los servidores. Tuvimos " + caidas + " fallos criticos.");
        }

        scanner.close();
    }
}


class MersenneTwister {
    private static final int N = 624;
    private static final int M = 397;
    private static final int MATRIX_A = 0x9908b0df;
    private static final int UPPER_MASK = 0x80000000;
    private static final int LOWER_MASK = 0x7fffffff;

    private int[] mt = new int[N];
    private int mti = N + 1;

    public MersenneTwister(long seed) {
        mt[0] = (int) seed;
        for (mti = 1; mti < N; mti++) {
            mt[mti] = (1812433253 * (mt[mti - 1] ^ (mt[mti - 1] >>> 30)) + mti);
        }
    }

    public int extraerNumero() {
        if (mti >= N) {
            int[] mag01 = {0x0, MATRIX_A};
            int y;
            for (int kk = 0; kk < N - M; kk++) {
                y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
            }
            for (int kk = N - M; kk < N - 1; kk++) {
                y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
            }
            y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
            mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];
            mti = 0;
        }

        int y = mt[mti++];
        y ^= (y >>> 11);
        y ^= (y << 7) & 0x9d2c5680;
        y ^= (y << 15) & 0xefc60000;
        y ^= (y >>> 18);

        return y >>> 1;
    }

    public int generarRango(int min, int max) {
        return min + (extraerNumero() % ((max - min) + 1));
    }
}