package tarea.simulacion;

import java.util.ArrayList;
import java.util.Scanner;


public class Poisson {

    public static void main(String[] args) {
        int k = 0;

        System.out.println("Simulación de la distribución de Poisson");
        System.out.println("---------------------------------------");
        System.out.println("Media de eventos esperados en un intervalo de tiempo: ");

        Scanner sc = new Scanner(System.in);
        System.out.print("= ");
        double lambda = sc.nextDouble();
        System.out.println();

        System.out.println("Máximo número de eventos (k) para calcular la probabilidad: ");
        System.out.print("= ");
        int maxK = sc.nextInt();
        System.out.println();

        // Calcular y mostrar las probabilidades para k = 0, 1, ..., maxK
        ArrayList<Double> probabilidades = calcularProbabilidadPoisson(lambda, maxK);


        // Imprimir los resultados
        for (int i = 0; i < probabilidades.size(); i++) {
            System.out.println(formatear(i, probabilidades.get(i)));
        }

    }

    // Función para calcular la probabilidad de la distribución de Poisson para k eventos
    public static ArrayList<Double> calcularProbabilidadPoisson(double lambda, int maxK) {
        ArrayList<Double> probabilidades = new ArrayList<>();

        for (int k = 0; k <= maxK; k++) {
            double probabilidad = (Math.pow(lambda, k)) * (Math.exp(-lambda)) / factorial(k);
            probabilidades.add(probabilidad);
        }

        return probabilidades;
    }

    // Función para calcular el factorial de un número n
    public static double factorial(int n) {
        if (n == 0) return 1;
        double resultado = 1;
        for (int i = 1; i <= n; i++) {
            resultado *= i;
        }
        return resultado;
    }

    public static String formatear(int k, double probabilidades) {
        String reset = "\u001B[0m";
        String blanco = "\u001B[37m";
        String azulClaro = "\u001B[94m";

        // Probabilidad
        String probabilidadStr = String.format("%.12f", probabilidades);
        String probabilidadColoreado = colorearNumero(probabilidadStr, blanco, azulClaro, reset);

        // Porcentaje
        double porcentaje = probabilidades * 100;
        String porcentajeStr = String.format("%.12f", porcentaje);
        String porcentajeColoreado = colorearNumero(porcentajeStr, blanco, azulClaro, reset);

        return String.format("K = %-6d | Probabilidad: %-15s | Porcentaje: %-15s%%",
                k, probabilidadColoreado, porcentajeColoreado);
    }

    private static String colorearNumero(String numero, String blanco, String azulClaro, String reset) {
        // Encontrar el índice del primer dígito diferente de 0
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
        } else {
            String cerosInicio = numero.substring(0, primerNoNulo);
            String restoNumeros = numero.substring(primerNoNulo);
            return blanco + cerosInicio + reset + azulClaro + restoNumeros + reset;
        }
    }
}
