package dijkstra;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class dijkstra {

    // ALGORITMO DIJKSTRA
    static int dijkstra(List<List<Arista>> grafo, int inicio, int fin) {
        int n = grafo.size();
        int[] distancia = new int[n];

        // Inicializar todas las distancias como infinito
        for (int i = 0; i < n; i++) {
            distancia[i] = Integer.MAX_VALUE;
        }
        // Excepto la ciudad inicial (costo 0)
        distancia[inicio] = 0;

        // PriorityQueue: bolsa que ordena automáticamente por distancia
        PriorityQueue<Nodo> pq = new PriorityQueue<>();
        pq.add(new Nodo(inicio, 0));

        // Mientras haya ciudades por procesar
        while (!pq.isEmpty()) {
            // Sacar la ciudad con MENOR distancia
            Nodo actual = pq.poll();

            // Si ya encontramos un camino mejor, ignorar
            if (actual.distancia > distancia[actual.id]) {
                continue;
            }

            // Revisar todos los vecinos (carreteras que salen de esta ciudad)
            for (Arista arista : grafo.get(actual.id)) {
                int vecino = arista.destino;
                int nuevaDistancia = actual.distancia + arista.costo;

                // Si encontramos un camino más barato
                if (nuevaDistancia < distancia[vecino]) {
                    // Actualizar la distancia
                    distancia[vecino] = nuevaDistancia;
                    // Agregar a la cola para procesarlo después
                    pq.add(new Nodo(vecino, nuevaDistancia));
                }
            }
        }

        return distancia[fin];
    }

    public static void main(String[] args) {
        // Crear el grafo (4 ciudades: 0, 1, 2, 3)
        List<List<Arista>> grafo = new ArrayList<>();

        // Inicializar 4 listas vacías (una por cada ciudad)
        for (int i = 0; i < 4; i++) {
            grafo.add(new ArrayList<>());
        }

        // AGREGAR CONEXIONES
        // Ciudad 0 -> Ciudad 1: costo 5
        grafo.get(0).add(new Arista(1, 5));
        // Ciudad 0 -> Ciudad 2: costo 3
        grafo.get(0).add(new Arista(2, 3));

        // Ciudad 1 -> Ciudad 3: costo 7
        grafo.get(1).add(new Arista(3, 7));

        // Ciudad 2 -> Ciudad 3: costo 2
        grafo.get(2).add(new Arista(3, 2));

        // EJECUTAR DIJKSTRA
        // Encontrar camino más barato de ciudad 0 a ciudad 3
        int caminoMasBarato = dijkstra(grafo, 0, 3);

        System.out.println("Costo mínimo de 0 a 3: " + caminoMasBarato);
    }

    // Clase para representar una conexión (carretera)
    static class Arista {
        int destino;
        int costo;

        Arista(int destino, int costo) {
            this.destino = destino;
            this.costo = costo;
        }
    }

    // Clase para la PriorityQueue
    static class Nodo implements Comparable<Nodo> {
        int id;           // Número de la ciudad
        int distancia;    // Costo para llegar a esta ciudad

        Nodo(int id, int distancia) {
            this.id = id;
            this.distancia = distancia;
        }

        // Cómo comparar dos Nodos (por distancia)
        @Override
        public int compareTo(Nodo otro) {
            return Integer.compare(this.distancia, otro.distancia);
        }
    }
}