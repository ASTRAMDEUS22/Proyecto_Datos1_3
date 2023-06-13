package Clases_auxiliares;

/**
 * Representa un paso en el algoritmo de Dijkstra para encontrar el camino más corto en un grafo.
 */
public class Traverse {

    /**
     * Clase interna que representa un nodo en el paso del algoritmo.
     */
    public class Node {
        int from;       // Nodo de origen
        int weight;     // Peso acumulado
        boolean visited;    // Indica si el nodo ha sido visitado
        boolean origin;     // Indica si el nodo es el nodo de origen

        /**
         * Constructor de la clase Node.
         */
        public Node() {
            from = 0;
            weight = Integer.MAX_VALUE;
            visited = false;
            origin = false;
        }
    }

    public Node[] nodes;    // Array de nodos
    public int currN;       // Nodo actual
    public int currW;       // Peso acumulado actual
    public int nextN;       // Siguiente nodo a visitar
    public int nextW;       // Peso acumulado del siguiente nodo

    /**
     * Constructor de la clase Traverse.
     *
     * @param n El tamaño del array de nodos.
     */
    public Traverse(int n) {
        nodes = new Node[n];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node();
        }
    }

    /**
     * Imprime un paso del algoritmo de Dijkstra.
     */
    public void printStep() {
        System.out.print((currN + 1) + "\t");
        for (int i = 0; i < nodes.length; i++) {
            System.out.print(((nodes[i].weight == Integer.MAX_VALUE) ? "X" : nodes[i].weight) + "\t");
        }
        System.out.println();
    }
}

