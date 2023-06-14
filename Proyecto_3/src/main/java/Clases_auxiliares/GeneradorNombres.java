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

    private static final Random random = new Random();
    private static final List<String> nombres = new ArrayList<>();

    public static String generarNombre() {

        String nombreGenerado;


        //Bucle para no generar dos nombres iguales
        while (true){

            nombreGenerado = generarNombreAleatorio();
            boolean existe = false;

            for (String nombre : nombres) {

                if (nombre.equals(nombreGenerado)) {
                    existe = true;
                    break;
                }

            }

            if (!existe){
                break;
            }

        }

        nombres.add(nombreGenerado);

        //System.out.println("Nombre de avion generado: " + nombreGenerado);

        /*System.out.println("Lista de nombres de aviones generados:");
        for (String nombre : nombres) {
            System.out.println(nombre);
        }*/
        return nombreGenerado;
    }

    private static String generarNombreAleatorio() {
        String[] listaNombres = {"Falcon", "Thunderbird", "Viper", "Eagle", "Raptor", "Phoenix", "Hawk", "Valkyrie"};

        int indiceAleatorio = random.nextInt(listaNombres.length);
        String nombreAvion = listaNombres[indiceAleatorio];

        // Generar tres letras aleatorias
        String letrasAleatorias = generarLetrasAleatorias();

        // Generar un número aleatorio entre 100 y 999
        int numeroAleatorio = random.nextInt(900) + 100;

        // Concatenar las letras aleatorias y el número aleatorio al final del nombre del avión
        return nombreAvion + "-" + letrasAleatorias + numeroAleatorio;
    }

    private static String generarLetrasAleatorias() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            // Generar una letra aleatoria (mayúscula)
            char letra = (char) (random.nextInt(26) + 'A');
            sb.append(letra);
        }
        return sb.toString();
    }
}



