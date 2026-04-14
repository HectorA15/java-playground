package tarea.simulacion.tema2;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CincoMetodosEnUno {
    private static final int TOTAL_NUMEROS = 1000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== MENU DE SIMULACION ===");
            System.out.println("1. Bernoulli");
            System.out.println("2. ChiCuadrada");
            System.out.println("3. KolmogorovSmirnov");
            System.out.println("4. Poisson");
            System.out.println("5. SimuladorServidor");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opcion: ");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    ejecutarBernoulli(scanner);
                    break;
                case 2:
                    ejecutarChiCuadrada(scanner);
                    break;
                case 3:
                    ejecutarKolmogorovSmirnov();
                    break;
                case 4:
                    ejecutarPoisson(scanner);
                    break;
                case 5:
                    ejecutarSimuladorServidor(scanner);
                    break;
                case 0:
                    scanner.close();
                    return;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void ejecutarBernoulli(Scanner teclado) {
        System.out.println("--- Metodo Bernoulli Binomial ---");

        System.out.print("Cantidad de intentos (n): ");
        int n = teclado.nextInt();

        System.out.print("Numero de exitos deseados (k): ");
        int k = teclado.nextInt();

        System.out.print("Probabilidad de exito (p) [0-1]: ");
        double p = teclado.nextDouble();

        if (k > n || p < 0 || p > 1) {
            System.out.println("k no puede ser mayor que n.");
        } else {
            double resultado = calcularBinomial(n, k, p);
            System.out.println("La probabilidad de tener " + k + " exitos en " + n + " intentos es de " + resultado + " o " + resultado * 100 + "%");
        }
    }

    public static double calcularBinomial(int n, int k, double p) {
        double combinatoria = obtenerCombinatoria(n, k);
        return combinatoria * Math.pow(p, k) * Math.pow(1 - p, n - k);
    }

    public static double obtenerCombinatoria(int n, int k) {
        return factorial(n) / (factorial(k) * factorial(n - k));
    }

    public static double factorial(int n) {
        if (n <= 0) {
            return 1;
        }
        double f = 1;
        for (int i = 1; i <= n; i++) {
            f *= i;
        }
        return f;
    }

    private static void ejecutarChiCuadrada(Scanner sc) {
        double[] tablaChiCuadrada = {
                0.000,
                3.841,
                5.991,
                7.815,
                9.488,
                11.070,
                12.592,
                14.067,
                15.507,
                16.919,
                18.307,
                19.675,
                21.026,
                22.362,
                23.685,
                24.996,
                26.296,
                27.587,
                28.869,
                30.144,
                31.410,
                32.671,
                33.924,
                35.172,
                36.415,
                37.652,
                38.885,
                40.113,
                41.337,
                42.557,
                43.773,
                44.985,
                46.194,
                47.400,
                48.602,
                49.802,
                51.000,
                52.192,
                53.384,
                54.572,
                55.758,
                56.942,
                58.124,
                59.304,
                60.481,
                61.656,
                62.830,
                64.001,
                65.171,
                66.339,
                67.505
        };

        System.out.println("Simulacion de la prueba de Chi Cuadrada para numeros aleatorios");
        System.out.println("--------------------------------------------------------------");
        System.out.print("Intervalos (k) : ");
        int intervalo = sc.nextInt();

        double decIntervalo = 1.0 / intervalo;
        int[] frecuenciasObservadas = new int[intervalo];
        double[] numeros = new double[TOTAL_NUMEROS];
        Random random = new Random();

        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = random.nextDouble();
            int j = (int) (numeros[i] * intervalo);
            if (j == intervalo) {
                j = intervalo - 1;
            }
            frecuenciasObservadas[j]++;
        }

        double frecuenciaEsperada = (double) numeros.length / intervalo;
        double chiCuadrada = calcularChiCuadrada(frecuenciasObservadas, frecuenciaEsperada);
        int gradosDeLibertad = intervalo - 1;

        System.out.println("\n--- ----------------- ---");
        System.out.println("Tamano del intervalo: " + decIntervalo);
        System.out.println("Frecuencia Esperada por Intervalo: " + frecuenciaEsperada);

        System.out.println("\nFrecuencias Observadas:");
        for (int i = 0; i < frecuenciasObservadas.length; i++) {
            double limiteInferior = i * decIntervalo;
            double limiteSuperior = (i + 1) * decIntervalo;
            System.out.printf("Intervalo %.3f - %.3f: %d\n", limiteInferior, limiteSuperior, frecuenciasObservadas[i]);
        }

        System.out.println("\nValor de Chi Cuadrada calculado: " + chiCuadrada);
        System.out.println("Grados de Libertad: " + gradosDeLibertad);

        double puntoCritico;
        if (gradosDeLibertad < tablaChiCuadrada.length) {
            puntoCritico = tablaChiCuadrada[gradosDeLibertad];
            System.out.println("Punto critico: " + puntoCritico);
        } else {
            System.out.println("El numero de intervalos es muy grande para la tabla ya definida");
            System.out.print("Ingrese el punto critico de Chi Cuadrada para " + gradosDeLibertad + " grados de libertad: ");
            puntoCritico = sc.nextDouble();
        }

        if (chiCuadrada <= puntoCritico) {
            System.out.println("SI, los numeros aleatorios siguen una distribucion uniforme (el generador es pseudoaleatorio).");
        } else {
            System.out.println("NO, los numeros aleatorios NO siguen una distribucion uniforme (puede haber un sesgo)");
        }
    }

    public static double calcularChiCuadrada(int[] frecuenciasObservadas, double frecuenciaEsperada) {
        double chiCuadrada = 0.0;
        for (int i = 0; i < frecuenciasObservadas.length; i++) {
            chiCuadrada += Math.pow(frecuenciasObservadas[i] - frecuenciaEsperada, 2) / frecuenciaEsperada;
        }
        return chiCuadrada;
    }

    private static void ejecutarKolmogorovSmirnov() {
        double[] numeros = new double[TOTAL_NUMEROS];
        Random random = new Random();

        for (int i = 0; i < TOTAL_NUMEROS; i++) {
            numeros[i] = random.nextDouble();
        }

        java.util.Arrays.sort(numeros);

        double distanciaMaxima = 0.0;
        double puntoCritico = 1.36 / Math.sqrt(TOTAL_NUMEROS);

        for (int i = 0; i < TOTAL_NUMEROS; i++) {
            double esquinaSuperior = (i + 1.0) / TOTAL_NUMEROS - numeros[i];
            double esquinaInferior = numeros[i] - (i * 1.0) / TOTAL_NUMEROS;
            double mayorDistancia = Math.max(esquinaSuperior, esquinaInferior);
            distanciaMaxima = Math.max(distanciaMaxima, mayorDistancia);
        }

        System.out.println("--- ---------------- ---");
        System.out.println("Distancia maxima (D): " + distanciaMaxima);
        System.out.println("Punto critico de la tabla: " + puntoCritico);

        if (distanciaMaxima <= puntoCritico) {
            System.out.println("Pasa la prueba, los numeros tienen distribucion uniforme.");
        } else {
            System.out.println("Falla la prueba, el generador es probable de que tenga sesgo.");
        }
    }

    private static void ejecutarPoisson(Scanner sc) {
        System.out.println("Simulacion de la distribucion de Poisson");
        System.out.println("---------------------------------------");
        System.out.println("Media de eventos esperados en un intervalo de tiempo: ");
        System.out.print("= ");
        double lambda = sc.nextDouble();
        System.out.println();

        System.out.println("Maximo numero de eventos (k) para calcular la probabilidad: ");
        System.out.print("= ");
        int maxK = sc.nextInt();
        System.out.println();

        ArrayList<Double> probabilidades = calcularProbabilidadPoisson(lambda, maxK);
        for (int i = 0; i < probabilidades.size(); i++) {
            System.out.println(formatear(i, probabilidades.get(i)));
        }
    }

    public static ArrayList<Double> calcularProbabilidadPoisson(double lambda, int maxK) {
        ArrayList<Double> probabilidades = new ArrayList<>();

        for (int k = 0; k <= maxK; k++) {
            double probabilidad = (Math.pow(lambda, k)) * (Math.exp(-lambda)) / factorial(k);
            probabilidades.add(probabilidad);
        }

        return probabilidades;
    }

    public static String formatear(int k, double probabilidades) {
        String reset = "\u001B[0m";
        String blanco = "\u001B[37m";
        String azulClaro = "\u001B[94m";

        String probabilidadStr = String.format("%.12f", probabilidades);
        String probabilidadColoreado = colorearNumero(probabilidadStr, blanco, azulClaro, reset);

        double porcentaje = probabilidades * 100;
        String porcentajeStr = String.format("%.12f", porcentaje);
        String porcentajeColoreado = colorearNumero(porcentajeStr, blanco, azulClaro, reset);

        return String.format("K = %-6d | Probabilidad: %-15s | Porcentaje: %-15s%%", k, probabilidadColoreado, porcentajeColoreado);
    }

    private static String colorearNumero(String numero, String blanco, String azulClaro, String reset) {
        int primerNoNulo = -1;
        for (int i = 0; i < numero.length(); i++) {
            char caracter = numero.charAt(i);
            if (caracter != '0' && caracter != '.' && caracter != '-') {
                primerNoNulo = i;
                break;
            }
        }

        if (primerNoNulo == -1) {
            return blanco + numero + reset;
        }

        String cerosInicio = numero.substring(0, primerNoNulo);
        String restoNumeros = numero.substring(primerNoNulo);
        return blanco + cerosInicio + reset + azulClaro + restoNumeros + reset;
    }

    private static void ejecutarSimuladorServidor(Scanner scanner) {
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
            int traficoBase = (int) (capacidadMaxima * 0.7);
            int picoAleatorio = mt.generarRango(0, (int) (capacidadMaxima * 0.35));
            int peticionesDelMinuto = traficoBase + picoAleatorio;

            traficoTotal += peticionesDelMinuto;

            if (peticionesDelMinuto > capacidadMaxima) {
                caidas++;
                System.out.println("Minuto " + i + ": ALERTA! " + peticionesDelMinuto + " peticiones. El servidor colapso.");
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
    }

    static class MersenneTwister {
        private static final int N = 624;
        private static final int M = 397;
        private static final int MATRIX_A = 0x9908b0df;
        private static final int UPPER_MASK = 0x80000000;
        private static final int LOWER_MASK = 0x7fffffff;

        private final int[] mt = new int[N];
        private int mti = N + 1;

        MersenneTwister(long seed) {
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
}
