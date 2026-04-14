package tarea.simulacion.tema3;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Embotelladora {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();


        double tasaDefecto = 0.20;
        int tamanoMuestra = 200;

        System.out.println("--- Embotelladora ---");
        System.out.print("Cantidad de botellas producidas: ");
        int n = sc.nextInt();
        sc.close();

        // 1 = defectuosa; 0 = no defectuosa
        int[] botellas = new int[n];
        int totalDefectuosasReales = 0;
        for (int i = 0; i < botellas.length; i++) {
            if (random.nextDouble() < tasaDefecto) {
                botellas[i] = 1;
                totalDefectuosasReales++;
            } else {
                botellas[i] = 0;
            }
        }
        System.out.println("Botellas producidas y marcadas.");

        // Seleccionamos 200 botellas aleatorias (índices)
        Set<Integer> botellasAleatorias = new HashSet<>();
        while (botellasAleatorias.size() < tamanoMuestra && botellasAleatorias.size() < n) {
            int num = random.nextInt(n);
            botellasAleatorias.add(num);
        }
        System.out.println("Muestra de " + botellasAleatorias.size() + " botellas seleccionada.");

        int defectuosass = 0;
        int nodefectuosass = 0;
        for (int indice : botellasAleatorias) {
            if (botellas[indice] == 1) {
                defectuosass++;
            } else {
                nodefectuosass++;
            }
        }

        // Cálculos corregidos
        double defectuosas = ((double) defectuosass / botellasAleatorias.size()) * 100;
        double NoDefectuosas = 100.0 - defectuosas;
        double porcentajeDefectuosasReal = ((double) totalDefectuosasReales / n) * 100;
        double errorAbsolutoDefecto = Math.abs(defectuosas - porcentajeDefectuosasReal);

        System.out.println("=========== RESULTADOS ===========");
        System.out.println("Botellas Defectuosas en la muestra: " + defectuosass);
        System.out.println("Botellas NO Defectuosas en la muestra: " + nodefectuosass);
        System.out.println("Estimacion : " + NoDefectuosas + "% no seran defectuosas");
        System.out.println("Tasa Real de defecto :" + porcentajeDefectuosasReal);
        System.out.println("Error Absoluto " + errorAbsolutoDefecto);
    }
}