package Clases_auxiliares;

import java.util.Random;

public class main2 {
    public static void main(String[] args) {
        Graph graph = new Graph(-1, -1); // Genera un grafo aleatorio
        graph.printMat(); // Imprime el grafo inicial

        Random rand = new Random();
        int node1 = rand.nextInt(graph.size); // Primer nodo inicial

        while (true) {
            int node2 = rand.nextInt(graph.size); // Nodo final aleatorio

            if (node1 != node2) { // Asegura que los nodos sean diferentes
                System.out.println("Nodo inicial: " + (node1 + 1));
                System.out.println("Nodo final: " + (node2 + 1));

                int[] shortestPath = findShortestPath(graph, node1, node2);

                if (shortestPath != null) {
                    System.out.print("Camino más corto: ");
                    int totalWeight = 0;
                    for (int i = 0; i < shortestPath.length; i++) {
                        int node = shortestPath[i];
                        int nextNode = (i + 1 < shortestPath.length) ? shortestPath[i + 1] : -1;

                        if (nextNode != -1) {
                            int weight = graph.mat[node][nextNode];
                            System.out.print((node + 1) + " (" + weight + ") ");
                            totalWeight += weight;
                        } else {
                            System.out.print((node + 1));
                        }
                    }
                    System.out.println("\nPeso total: " + totalWeight);
                } else {
                    System.out.println("No se encontró un camino entre los nodos.");
                }

                System.out.println();

                node1 = node2; // El nodo final se convierte en el próximo nodo inicial
            }

            // Espera entre 1 y 5 segundos antes de continuar con la siguiente iteración
            try {
                int waitTime = rand.nextInt(5) + 1;
                Thread.sleep(waitTime * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static int[] findShortestPath(Graph graph, int start, int end) {
        int[] distance = new int[graph.size];
        boolean[] visited = new boolean[graph.size];

        for (int i = 0; i < graph.size; i++) {
            distance[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }

        distance[start] = 0;

        for (int i = 0; i < graph.size - 1; i++) {
            int minDistance = Integer.MAX_VALUE;
            int minIndex = -1;

            for (int j = 0; j < graph.size; j++) {
                if (!visited[j] && distance[j] < minDistance) {
                    minDistance = distance[j];
                    minIndex = j;
                }
            }

            if (minIndex == -1) {
                break;
            }

            visited[minIndex] = true;

            for (int j = 0; j < graph.size; j++) {
                if (!visited[j] && graph.mat[minIndex][j] != Integer.MAX_VALUE && distance[minIndex] + graph.mat[minIndex][j] < distance[j]) {
                    distance[j] = distance[minIndex] + graph.mat[minIndex][j];
                }
            }
        }

        if (distance[end] == Integer.MAX_VALUE) {
            return null; // No se encontró un camino entre los nodos
        }

        int[] path = new int[graph.size];
        int pathIndex = 0;
        int currentNode = end;

        while (currentNode != start) {
            path[pathIndex] = currentNode;
            pathIndex++;
            currentNode = findPreviousNode(graph, distance, currentNode);
        }

        path[pathIndex] = start;
        pathIndex++;

        int[] shortestPath = new int[pathIndex];
        for (int i = pathIndex - 1; i >= 0; i--) {
            shortestPath[pathIndex - 1 - i] = path[i];
        }

        return shortestPath;
    }

    public static int findPreviousNode(Graph graph, int[] distance, int node) {
        int minDistance = Integer.MAX_VALUE;
        int previousNode = -1;

        for (int i = 0; i < graph.size; i++) {
            if (graph.mat[node][i] != Integer.MAX_VALUE && distance[i] < minDistance) {
                minDistance = distance[i];
                previousNode = i;
            }
        }

        return previousNode;
    }
}







