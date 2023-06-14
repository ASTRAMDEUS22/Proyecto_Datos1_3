package Clases_auxiliares;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Esta clase representa un grafo y proporciona métodos para generar y manipular sus datos.
 */
public class Graph {
    public int[][] mat;
    public boolean[][] matB;
    public int size, edgeNum, defWeight;

    /**
     * Constructor de la clase Graph. Genera un grafo con un número determinado de nodos y aristas.
     *
     * @param num   el número de nodos del grafo (si es -1, se genera aleatoriamente)
     * @param edges el número de aristas del grafo (si es -1, se genera aleatoriamente)
     */
    public Graph(int num, int edges) {
        Random rand = new Random();
        int n;
        if (num == -1)
            n = rand.nextInt(9) + 2;
        else
            n = num;
        this.mat = new int[n][n];
        this.matB = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matB[i][j] = false;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mat[i][j] = Integer.MAX_VALUE;
            }
        }
        if (edges == -1)
            this.edgeNum = rand.nextInt(((n * n - n) / 2) - (n - 1) + 1) + (n - 1);
        else
            this.edgeNum = edges;
        int nE = 0;
        for (int j = 0; j < n; j++) {
            matB[j][j] = true;
            mat[j][j] = 0;
        }
        while (nE < edgeNum) {
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (rand.nextInt(2) == 1 && mat[i][j] == Integer.MAX_VALUE && nE < edgeNum) {
                        matB[i][j] = true;
                        mat[i][j] = rand.nextInt(10) + 1;
                        matB[j][i] = true;
                        mat[j][i] = mat[i][j];
                        nE++;
                    }
                }
            }
        }
        size = n;
    }

    /**
     * Imprime la matriz de adyacencia del grafo.
     */
    public void printMat() {
        System.out.print("x\\y\t");
        for (int i = 0; i < size; i++) {
            System.out.print((i + 1) + "\t");
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            System.out.print((i + 1) + "\t");
            for (int j = 0; j < size; j++) {
                System.out.print(((mat[i][j] == Integer.MAX_VALUE) ? "X" : mat[i][j]) + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Limpia la matriz de adyacencia del grafo, restableciendo sus valores a su estado inicial.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matB[i][j] = false;
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mat[i][j] = Integer.MAX_VALUE;
            }
        }
        for (int j = 0; j < size; j++) {
            matB[j][j] = true;
            mat[j][j] = 0;
        }
    }

    /**
     * Crea una arista entre dos nodos del grafo con un peso especificado.
     *
     * @param i el nodo origen
     * @param j el nodo destino
     * @param w el peso de la arista
     */
    public void createEdge(int i, int j, int w) {
        mat[i][j] = w;
        mat[j][i] = w;
        matB[i][j] = true;
        matB[j][i] = true;
        defWeight = w; // almacena el peso original
    }

    /**
     * Verifica si el grafo está completamente conectado.
     *
     * @return true si el grafo está completamente conectado, false de lo contrario
     */
    public boolean connected() {
        boolean[] columns = new boolean[size];
        for (int i = 0; i < columns.length; i++) {
            columns[i] = false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (mat[i][j] != 0 && mat[i][j] != Integer.MAX_VALUE) {
                    columns[j] = true;
                }
            }
        }
        for (int i = 0; i < size; i++) {
            if (mat[i][0] != 0 && mat[i][0] != Integer.MAX_VALUE) {
                columns[0] = true;
            }
        }
        return containsOnlyTrue(columns);
    }

    /**
     * Verifica si un arreglo contiene solo valores true.
     *
     * @param col el arreglo a verificar
     * @return true si el arreglo contiene solo valores true, false de lo contrario
     */
    public boolean containsOnlyTrue(boolean[] col) {
        for (int i = 0; i < col.length; i++) {
            if (!col[i])
                return false;
        }
        return true;
    }

    /**
     * Incrementa el peso de una arista existente en el grafo.
     *
     * @param i         el nodo origen de la arista
     * @param j         el nodo destino de la arista
     * @param increment el incremento en el peso de la arista
     */
    public void increaseWeight(int i, int j, int increment) {
        if (matB[i][j]) {
            mat[i][j] += increment;
            mat[j][i] += increment;
        }
    }

    /**
     * Decrementa el peso de una arista periódicamente utilizando un Timer.
     *
     * @param i el nodo origen de la arista
     * @param j el nodo destino de la arista
     */
    public void decrementWeightPeriodically(final int i, final int j) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (defWeight <= 0) {
                    timer.cancel();
                    timer.purge();
                    return;
                }
                decreaseWeightByOne(i, j);
                defWeight--;
            }
        }, 5000, 5000);
    }

    private void decreaseWeightByOne(int i, int j) {
        if (matB[i][j] && mat[i][j] > defWeight) {
            mat[i][j]--;
            mat[j][i]--;
        }
    }
}