package tarea.simulacion.tema4.bernoulli;

/**
 * The Bernoulli class provides methods to compute the probability of a Bernoulli
 * distribution using the binomial theorem. It handles the calculations for a number
 * of trials, the desired number of successes, and the probability of success for
 * each trial.
 */
public class Bernoulli {


    private int n; // cantidad de intentos
    private int k; // numero de exitos deseados
    private double p; // probabilidad de exito

    public Bernoulli(int n, int k, double p) {
        this.n = n;
        this.k = k;
        this.p = p;
    }

    public static double calBinomial(int n, int k, double p) {
        double combinatoria = obtenerCombinatoria(n, k);
        return combinatoria * Math.pow(p, k) * Math.pow(1 - p, n - k);
    }

    /* la formula es:
                           n!
                        --------  esto seria:   |n|
                        (n-k)!k!                |k|
     */
    public static double obtenerCombinatoria(int n, int k) {
        return calFactorial(n) / (calFactorial(k) * calFactorial(n - k));
    }

    public static double calFactorial(int n) {
        if (n <= 0) return 1;
        double f = 1;
        for (int i = 1; i <= n; i++) {
            f *= i;
        }
        return f;
    }

    public void bernoulli() {
    } // constructor vacio

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

}
