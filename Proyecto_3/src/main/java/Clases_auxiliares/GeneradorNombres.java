package Clases_auxiliares;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GeneradorNombres {

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
    }

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
}



