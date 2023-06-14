package Clases_auxiliares;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Esta clase proporciona un generador de nombres para aviones.
 */
public class GeneradorNombres {

    /**
     * Método principal que permite al usuario generar nombres de aviones de forma interactiva.
     * Los nombres generados se almacenan en una lista y se imprimen al final.
     *
     * @param args los argumentos de la línea de comandos (no se utilizan en este caso)
     */
    public static void main(String[] args) {
        List<String> nombres = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        boolean generarMasNombres = true;
        while (generarMasNombres) {
            String nombreGenerado = generarNombreAleatorio();
            nombres.add(nombreGenerado);

            System.out.println("Nombre de avion generado: " + nombreGenerado);
            System.out.print("¿Desea generar otro nombre de avion? (s/n): ");
            String respuesta = scanner.nextLine();

            if (!respuesta.equalsIgnoreCase("s")) {
                generarMasNombres = false;
            }
        }

        System.out.println("Lista de nombres de aviones generados:");
        for (String nombre : nombres) {
            System.out.println(nombre);
        }

        // Ordenar la lista de nombres utilizando insertion sort
        insertionSort(nombres);
        System.out.println("\nLista de nombres de aviones ordenada por Insertion Sort:");
        for (String nombre : nombres) {
            System.out.println(nombre);
        }

        // Ordenar la lista de nombres utilizando shell sort
        shellSort(nombres);
        System.out.println("\nLista de nombres de aviones ordenada por Shell Sort:");
        for (String nombre : nombres) {
            System.out.println(nombre);
        }
    }

    /**
     * Genera un nombre de avión aleatorio utilizando una lista predefinida de nombres y añadiendo letras aleatorias y un número al final.
     *
     * @return el nombre generado para el avión
     */
    private static String generarNombreAleatorio() {
        String[] listaNombres = {"Falcon", "Thunderbird", "Viper", "Eagle", "Raptor", "Phoenix", "Hawk", "Valkyrie"};
        Random random = new Random();
        int indiceAleatorio = random.nextInt(listaNombres.length);
        String nombreAvion = listaNombres[indiceAleatorio];

        // Generar tres letras aleatorias
        String letrasAleatorias = generarLetrasAleatorias(3);

        // Generar un número aleatorio entre 100 y 999
        int numeroAleatorio = random.nextInt(900) + 100;

        // Concatenar las letras aleatorias y el número aleatorio al final del nombre del avión
        String nombreGenerado = nombreAvion + "-" + letrasAleatorias + numeroAleatorio;

        return nombreGenerado;
    }

    /**
     * Genera una cadena de letras aleatorias en mayúsculas.
     *
     * @param cantidad la cantidad de letras a generar
     * @return la cadena de letras generada
     */
    private static String generarLetrasAleatorias(int cantidad) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < cantidad; i++) {
            // Generar una letra aleatoria (mayúscula)
            char letra = (char) (random.nextInt(26) + 'A');
            sb.append(letra);
        }
        return sb.toString();
    }

    /**
     * Ordena una lista de nombres utilizando el algoritmo de ordenamiento de inserción (insertion sort).
     *
     * @param nombres la lista de nombres a ordenar
     */
    private static void insertionSort(List<String> nombres) {
        int n = nombres.size();
        for (int i = 1; i < n; ++i) {
            String key = nombres.get(i);
            int j = i - 1;

            while (j >= 0 && nombres.get(j).compareTo(key) > 0) {
                nombres.set(j + 1, nombres.get(j));
                j = j - 1;
            }
            nombres.set(j + 1, key);
        }
    }

    /**
     * Ordena una lista de nombres utilizando el algoritmo de ordenamiento de Shell (shell sort).
     *
     * @param nombres la lista de nombres a ordenar
     */
    private static void shellSort(List<String> nombres) {
        int n = nombres.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i += 1) {
                String temp = nombres.get(i);
                int j;
                for (j = i; j >= gap && nombres.get(j - gap).compareTo(temp) > 0; j -= gap) {
                    nombres.set(j, nombres.get(j - gap));
                }
                nombres.set(j, temp);
            }
        }
    }
}





