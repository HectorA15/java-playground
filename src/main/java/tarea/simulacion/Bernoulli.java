package tarea.simulacion;

import java.util.Scanner;

public class Bernoulli {

    /*
    distribucion bernoulli binomial formula:

    p (x=k) = |n|  k    n-k
              |k| p (1-p)

     */


    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.println("--- Metodo Bernoulli Binomial ---");

        System.out.print("Cantidad de intentos (n): ");
        int n = teclado.nextInt();

        System.out.print("Numero de exitos deseados (k): ");
        int k = teclado.nextInt();

        System.out.print("Probabilidad de exito (p) [0-1]: ");
        double p = teclado.nextDouble();

        if (k > n || p < 0 || p > 1) {
            System.out.println(" k no puede ser mayor que n.");
        } else {
            double resultado = calcularBinomial(n, k, p);
            System.out.println("La probabilidad de tener " + k + " exitos en " + n + " intentos es de " + resultado + " o " + resultado * 100 + "%");
        }
    }

    public static double calcularBinomial(int n, int k, double p) {
        double combinatoria = obtenerCombinatoria(n, k);
        return combinatoria * Math.pow(p, k) * Math.pow(1 - p, n - k);
    }


    /* la formula es:
                           n!
                        --------  esto seria:   |n|
                        (n-k)!k!                |k|
     */
    public static double obtenerCombinatoria(int n, int k) {
        return factorial(n) / (factorial(k) * factorial(n - k));
    }


    // para calcular factoriales que se utilizan en la formula de la combinatoria
    public static double factorial(int n) {
        if (n <= 0) return 1;
        double f = 1;
        for (int i = 1; i <= n; i++) {
            f *= i;
        }
        return f;
    }


}
