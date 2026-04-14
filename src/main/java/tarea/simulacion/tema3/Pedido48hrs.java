package tarea.simulacion.tema3;

import java.util.Random;

public class Pedido48hrs {

    public static void main(String[] args) {
        int k = 0;

        System.out.println("¿Que tan probable es que llegue tu pedido en 48hrs?");
        System.out.println("---------------------------------------");
        System.out.println("Desviacion estandar en 6 horas (tramo 1) : 1 ");
        System.out.println("Desviacion estandar en 30 horas (tramo 2) : 4 ");
        System.out.println("Desviacion estandar en 8 horas (tramo 1) : 2 ");


        Random random = new Random();
        int iteraciones = 1000000;
        int aciertos = 0;
        int tiempo = 48;
        double sumaTotal = 0;

        for (int i = 0; i < iteraciones; i++) {
            double desvRandom1 = random.nextDouble(-1, 1);
            double desvRandom2 = random.nextDouble(-4, 4);
            double desvRandom3 = random.nextDouble(-4, 2);

            double t1 = 5 + random.nextInt(3) + desvRandom1;
            double t2 = 26 + random.nextInt(9) + desvRandom2;
            double t3 = 6 + random.nextInt(5) + desvRandom3;

            double tiempoTotal = t1 + t2 + t3;
            sumaTotal += tiempoTotal;
            if (tiempoTotal <= tiempo) {
                aciertos++;
            }
        }


        double promedio = sumaTotal / iteraciones;
        double probabilidad = (double) aciertos / iteraciones;
        System.out.printf("Tiempo Medio Total: %.02f ", promedio);
        System.out.println("Resultados simulación (" + iteraciones + " veces):");
        System.out.println("Probabilidad de llegar en 48hrs o menos: " + (probabilidad * 100) + "%");
    }

}