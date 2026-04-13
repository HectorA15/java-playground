package tarea.simulacion.Tema3;

import java.util.ArrayList;

public class CentroAyuda {

    public static void main(String[] args) {

        int lambda = 5;
        int maxK = 15;
        double probabilidadAcum = 0;
        int ticketMax = 8;
        System.out.println("=========== PROBABILIDADES ===========");
        ArrayList<Double> probabilidades = calcularProbabilidadPoisson(lambda, maxK);
        for (int i = 0; i < probabilidades.size(); i++) {
            double probabilidadReal = probabilidades.get(i) * 100;
            System.out.printf("%d \t: %.5f%% \t| %.5f%% %n", i, probabilidades.get(i), probabilidadReal);
            if (i <= ticketMax) {
                probabilidadAcum += probabilidadReal; // la probabilidad de que lleguen 8 tickets o menos en una hora

            }
        }
        System.out.println("=========== CENTRO DE AYUDA ===========");
        System.out.printf("Se reciben en promedio %d tickets por hora %n", lambda);
        System.out.printf("Promedio de que lleguen 8 tickets o menos en una hora: %.2f%%%n", probabilidadAcum);
        System.out.printf("La probabilidad de que lleguen mas de %d tickets en una hora es de: %.2f%%%n", ticketMax, 100 - probabilidadAcum);

    }

    public static double factorial(int n) {
        if (n == 0) return 1;
        double resultado = 1;
        for (int i = 1; i <= n; i++) {
            resultado *= i;
        }
        return resultado;
    }

    public static ArrayList<Double> calcularProbabilidadPoisson(double lambda, int maxK) {
        ArrayList<Double> probabilidades = new ArrayList<>();

        for (int k = 0; k <= maxK; k++) {
            double probabilidad = (Math.pow(lambda, k)) * (Math.exp(-lambda)) / factorial(k);
            probabilidades.add(probabilidad);
        }

        return probabilidades;
    }

}