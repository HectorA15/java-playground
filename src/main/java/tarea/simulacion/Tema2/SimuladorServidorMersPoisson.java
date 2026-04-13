package tarea.simulacion.Tema2;

import java.util.Scanner;

public class SimuladorServidorMersPoisson {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        MersenneTwister2 randomPro = new MersenneTwister2(System.currentTimeMillis());
        correrPrueba(teclado, randomPro);

        teclado.close();
    }

    private static void correrPrueba(Scanner teclado, MersenneTwister2 randomPro) {
        int topeServidor = pedirCapacidad(teclado);
        double promedio = pedirLambda(teclado);
        int vueltas = pedirVueltas(teclado);

        double probFormula = calcProbColapso(topeServidor, promedio);
        double probSim = simularProbColapso(topeServidor, promedio, vueltas, randomPro);

        System.out.println("\n--- RESULTADOS ---");
        System.out.println("Capacidad (C): " + topeServidor);
        System.out.println("Lambda: " + promedio);
        System.out.println("Simulaciones: " + vueltas);
        System.out.printf("Probabilidad analitica P(X > C): %.6f%n", probFormula);
        System.out.printf("Probabilidad simulada  P(X > C): %.6f%n", probSim);
    }

    private static int pedirCapacidad(Scanner teclado) {
        System.out.print("Ingrese la capacidad maxima del servidor (peticiones por minuto): ");
        return teclado.nextInt();
    }

    private static double pedirLambda(Scanner teclado) {
        System.out.print("Ingrese lambda (promedio de peticiones por minuto): ");
        return teclado.nextDouble();
    }

    private static int pedirVueltas(Scanner teclado) {
        System.out.print("Ingrese cuantas simulaciones desea ejecutar: ");
        return teclado.nextInt();
    }

    private static double calcProbColapso(int topeServidor, double promedio) {
        if (promedio <= 0) {
            return 0.0;
        }
        double acum = acumPoisson(topeServidor, promedio);
        double prob = 1.0 - acum;
        if (prob < 0.0) {
            return 0.0;
        }
        if (prob > 1.0) {
            return 1.0;
        }
        return prob;
    }

    private static double acumPoisson(int k, double promedio) {
        if (k < 0) {
            return 0.0;
        }
        double term = Math.exp(-promedio);
        double acumTotal = term;
        for (int i = 1; i <= k; i++) {
            term = term * promedio / i;
            acumTotal += term;
        }
        return acumTotal;
    }

    private static double simularProbColapso(int topeServidor, double promedio, int vueltas, MersenneTwister2 randomPro) {
        if (vueltas <= 0 || promedio <= 0) {
            return 0.0;
        }

        int seCayo = 0;
        for (int i = 0; i < vueltas; i++) {
            int petis = sacarPoisson(promedio, randomPro);
            if (petis > topeServidor) {
                seCayo++;
            }
        }
        return (double) seCayo / vueltas;
    }

    private static int sacarPoisson(double promedio, MersenneTwister2 numeroRandom) {
        if (promedio <= 0) {
            return 0;
        }
        double limite = Math.exp(-promedio);
        int k = 0;
        double multi = 1.0;
        do {
            k++;
            multi *= numeroRandom.randomTwister();
        } while (multi > limite);
        return k - 1;
    }
}

class MersenneTwister2 {
    private static final int N = 624;
    private static final int M = 397;
    private static final int MATRIX_A = 0x9908b0df;
    private static final int UPPER_MASK = 0x80000000;
    private static final int LOWER_MASK = 0x7fffffff;

    private final int[] mt = new int[N];
    private int mti;

    public MersenneTwister2(long seed) {
        mt[0] = (int) seed;
        for (mti = 1; mti < N; mti++) {
            mt[mti] = (1812433253 * (mt[mti - 1] ^ (mt[mti - 1] >>> 30)) + mti);
        }
    }

    public int sacarNumero() {
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

    public double randomTwister() {
        return (sacarNumero() + 1.0) / (Integer.MAX_VALUE + 1.0);
    }
}