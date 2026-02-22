package tarea.simulacion;

import java.util.Random;
import java.util.Scanner;

public class ChiCuadrada {

    private static final int TOTAL_NUMEROS = 1000;

    public static void main(String[] args) {
        // Arreglo con los valores críticos de Chi-Cuadrada al 0.05 de error.
        // Cada indice representa los grados de libertad
        double[] tablaChiCuadrada = {
                0.000,  // Índice 0 (No se usa, 0 grados de libertad)
                3.841,  // Índice 1
                5.991,  // Índice 2
                7.815,  // Índice 3
                9.488,  // Índice 4
                11.070, // Índice 5
                12.592, // Índice 6
                14.067, // Índice 7
                15.507, // Índice 8
                16.919, // Índice 9
                18.307, // Índice 10
                19.675, // Índice 11
                21.026, // Índice 12
                22.362, // Índice 13
                23.685, // Índice 14
                24.996, // Índice 15
                26.296, // Índice 16
                27.587, // Índice 17
                28.869, // Índice 18
                30.144, // Índice 19
                31.410, // Índice 20
                32.671, // Índice 21
                33.924, // Índice 22
                35.172, // Índice 23
                36.415, // Índice 24
                37.652, // Índice 25
                38.885, // Índice 26
                40.113, // Índice 27
                41.337, // Índice 28
                42.557, // Índice 29
                43.773, // Índice 30
                44.985, // Índice 31
                46.194, // Índice 32
                47.400, // Índice 33
                48.602, // Índice 34
                49.802, // Índice 35
                51.000, // Índice 36
                52.192, // Índice 37
                53.384, // Índice 38
                54.572, // Índice 39
                55.758, // Índice 40
                56.942, // Índice 41
                58.124, // Índice 42
                59.304, // Índice 43
                60.481, // Índice 44
                61.656, // Índice 45
                62.830, // Índice 46
                64.001, // Índice 47
                65.171, // Índice 48
                66.339, // Índice 49
                67.505  // Índice 50
        };


        Scanner sc = new Scanner(System.in);
        System.out.println("Simulación de la prueba de Chi Cuadrada para números aleatorios");
        System.out.println("--------------------------------------------------------------");
        System.out.print("Intervalos (k) : ");
        int intervalo = sc.nextInt();

        // El tamaño del intervalo se calcula dividiendo 1 entre el número de intervalos,
        // ya que estamos trabajando con números aleatorios entre 0 y 1.
        double decIntervalo = 1.0 / intervalo;
        int[] frecuenciasObservadas = new int[intervalo];
        double[] numeros = new double[TOTAL_NUMEROS];
        Random random = new Random();

        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = random.nextDouble();

            // saca el indice de acuerdo al numero por ejemplo si el numero es 0.23 y el intervalo es 5,
            // entonces se multiplica 0.23 * 5 = 1.15, lo que significa que el numero debe estar en el segundo intervalo (indice 1) que va de 0.2 a 0.4
            int j = (int) (numeros[i] * intervalo);

            // si el numero es exactamente 1.0, entonces se asigna al ultimo intervalo (indice intervalo - 1) que va de 0.8 a 1.0
            if (j == intervalo) {
                j = intervalo - 1;
            }
            frecuenciasObservadas[j]++;
        }


        // La frecuencia esperada por intervalo se calcula dividiendo el total de números generados entre el número de intervalos,
        // ya que en una distribución uniforme se espera que cada intervalo tenga la misma cantidad de números.
        double frecuenciaEsperada = (double) numeros.length / intervalo;
        double chiCuadrada = calcularChiCuadrada(frecuenciasObservadas, frecuenciaEsperada);
        int gradosDeLibertad = intervalo - 1;


        System.out.println("\n--- ----------------- ---");
        System.out.println("Tamaño del intervalo: " + decIntervalo);
        System.out.println("Frecuencia Esperada por Intervalo: " + frecuenciaEsperada);

        System.out.println("\nFrecuencias Observadas:");
        for (int i = 0; i < frecuenciasObservadas.length; i++) {
            double limiteInferior = i * decIntervalo;
            double limiteSuperior = (i + 1) * decIntervalo;
            System.out.printf("Intervalo %.3f - %.3f: %d\n", limiteInferior, limiteSuperior, frecuenciasObservadas[i]);
        }

        System.out.println("\nValor de Chi Cuadrada calculado: " + chiCuadrada);
        System.out.println("Grados de Libertad: " + gradosDeLibertad);


        double puntoCritico = 0.0;

        // Si el número cabe en nuestro arreglo, lo sacamos en automático
        if (gradosDeLibertad < tablaChiCuadrada.length) {
            puntoCritico = tablaChiCuadrada[gradosDeLibertad];
            System.out.println("Punto crítico: " + puntoCritico);
        }

        // si el número es mayor a lo que tenemos en la tabla, le pedimos al usuario que lo ingrese manualmente
        else {
            System.out.println("El numero de intervalos es muy grande para la tabla ya definida");
            System.out.print("Ingrese el punto crítico de Chi Cuadrada para " + gradosDeLibertad + " grados de libertad: ");
            puntoCritico = sc.nextDouble();
        }

        // Si el valor de chi cuadrada calculado es menor o igual al punto crítico, entonces se acepta la hipótesis nula (los números siguen una distribución uniforme)
        if (chiCuadrada <= puntoCritico) {
            System.out.println("SI, los números aleatorios siguen una distribución uniforme (el generador es pseudoaleatorio).");
        } else {
            System.out.println("NO, los números aleatorios NO siguen una distribución uniforme (puede haber un sesgo)");
        }
    }

    public static double calcularChiCuadrada(int[] frecuenciasObservadas, double frecuenciaEsperada) {
        double chiCuadrada = 0.0;
        for (int i = 0; i < frecuenciasObservadas.length; i++) {
            chiCuadrada += Math.pow(frecuenciasObservadas[i] - frecuenciaEsperada, 2) / frecuenciaEsperada;
        }
        return chiCuadrada;
    }
}
