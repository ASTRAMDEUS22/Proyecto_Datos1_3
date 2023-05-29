package Juego;

import Clases_auxiliares.Aeropuerto;
import Clases_auxiliares.LineaArista;
import Clases_auxiliares.Portaaviones;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;


public class
AirWar extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Label label;

    //ArrayList<Portaaviones> listaPortaaviones = new ArrayList<>();

    ArrayList<Portaaviones> listaPortaaviones = new ArrayList<>();
    ArrayList<Aeropuerto> listaAeropuertos = new ArrayList<>();

    Pane pane;

    Random random = new Random();


    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        label = new Label();
        label.setText("hiii");

        //String rutaLocal = "Imagenes/mapaMundi.png";
        this.pane = new Pane();



        Image image = new Image(getClass().getResourceAsStream("/Imagenes/mapaDelMundo.jpg"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        pane.getChildren().add(imageView);

        int barcos_generados = 5,aeropuertos_generados = 8;

        int minAlto = 0,maxAlto = 700,minAncho = 0,maxAncho = 1500;

        PixelReader pixelReader = image.getPixelReader();

        //Generar barcos
        while (barcos_generados != 0){

            Random random = new Random();

            int y = random.nextInt(maxAlto - minAlto + 1) + minAlto;
            int x = random.nextInt(maxAncho - minAncho + 1) + minAncho;

            Color color = pixelReader.getColor(x,y);

            int red = (int) (color.getRed() * 255);
            int green = (int) (color.getGreen() * 255);
            int blue = (int) (color.getBlue() * 255);

            if (red == 63 && green == 71 && blue == 204) {  //Es mar

                Portaaviones portaaviones = new Portaaviones();
                portaaviones.setPane(pane);
                portaaviones.setX(x);
                portaaviones.setY(y);

                pane.getChildren().add(portaaviones);
                listaPortaaviones.add(portaaviones);

                barcos_generados--;

            }

        }

        //Generar aeropuertos
        while (aeropuertos_generados > 0){

            Random random = new Random();

            int y = random.nextInt(maxAlto - minAlto + 1) + minAlto;
            int x = random.nextInt(maxAncho - minAncho + 1) + minAncho;

            Color color = pixelReader.getColor(x,y);

            int red = (int) (color.getRed() * 255);
            int green = (int) (color.getGreen() * 255);
            int blue = (int) (color.getBlue() * 255);

            if (red != 63 && green != 71 && blue != 204) {  //Es mar

                //Instancia de un aeropuerto
                Aeropuerto aeropuerto = new Aeropuerto();
                aeropuerto.setX(x);
                aeropuerto.setY(y);

                pane.getChildren().addAll(aeropuerto);
                listaAeropuertos.add(aeropuerto);

                aeropuertos_generados--;

            }

        }

        crearAristas();

        Scene scene = new Scene(pane,1500,800);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void crearAristas() {

        generarLineaPortaaviones();
        generarLineaAeropuerto();

    }

    public void generarLineaPortaaviones(){

        //Ejecuta mientras el índice este en la lista
        for (int i = 0;i < listaPortaaviones.size();i++){

            //Genera un número aleatorio
            int aleatorio = random.nextInt(listaPortaaviones.size());

            //Generará números aleatorios mientras que el elemento actual sea igual al elemento aleatorio
            while (listaPortaaviones.get(i) == listaPortaaviones.get(aleatorio)){

                aleatorio = random.nextInt(listaPortaaviones.size());

            }

            //Recorre los demás elementos de la lista menos el primero
            for (int j = i + 1;j < listaPortaaviones.size();j++){

                //Probabilidad de 1/2 de generar una línea
                int probabilidad = random.nextInt(10);

                if (probabilidad > 2) {
                    generaPortaPorta(listaPortaaviones.get(i), listaPortaaviones.get(j));
                }
            }

            //Recorre la lista de aeropuertos para generar conexiones aleatorias
            for (Aeropuerto listaAeropuerto : listaAeropuertos) {

                int probabilidad = random.nextInt(10);

                if (probabilidad > 4) {

                    generaPortaAero(listaPortaaviones.get(i), listaAeropuerto);

                }

            }


        }
    }

    public void generarLineaAeropuerto(){

        //Ejecuta mientras el índice este en la lista
        for (int i = 0;i < listaAeropuertos.size();i++){

            //Genera un número aleatorio
            int aleatorio = random.nextInt(listaAeropuertos.size());

            //Generará números aleatorios mientras que el elemento actual sea igual al elemento aleatorio
            while (listaAeropuertos.get(i) == listaAeropuertos.get(aleatorio)){

                aleatorio = random.nextInt(listaAeropuertos.size());

            }

            //Recorre la lista menos el primero elemento
            for (int j = i + 1;j < listaAeropuertos.size();j++) {

                int probabilidad = random.nextInt(10);

                //Una probabilidad más alta de generar aristas, ya que hay más aeropuertos que portaaviones
                if (probabilidad > 4){

                    generaAeroAero(listaAeropuertos.get(i), listaAeropuertos.get(j));

                }
            }

            //Recorre la lista de portaaviones para generar conexiones aleatorias
            for (Portaaviones listaPortaavione : listaPortaaviones) {

                int probabilidad = random.nextInt(10);

                if (probabilidad > 3) {

                    generaAeroPorta(listaAeropuertos.get(i), listaPortaavione);

                }

            }

        }
    }

    public void generaPortaPorta(Portaaviones portaaviones,Portaaviones portaavionesRandom){

        LineaArista lineaArista = new LineaArista();

        lineaArista.generarLinea(portaaviones,portaavionesRandom);

        pane.getChildren().add(lineaArista);

    }

    public void generaAeroAero(Aeropuerto aeropuerto,Aeropuerto aeropuertoRandom){

        LineaArista lineaArista = new LineaArista();

        lineaArista.generarLinea(aeropuerto,aeropuertoRandom);

        pane.getChildren().add(lineaArista);

    }

    public void generaPortaAero(Portaaviones portaaviones,Aeropuerto aeropuertoRandom){

        LineaArista lineaArista = new LineaArista();

        lineaArista.generarLinea(portaaviones,aeropuertoRandom);

        pane.getChildren().add(lineaArista);

    }

    public void generaAeroPorta(Aeropuerto aeropuerto,Portaaviones portaavionesRandom){

        LineaArista lineaArista = new LineaArista();

        lineaArista.generarLinea(aeropuerto,portaavionesRandom);

        pane.getChildren().add(lineaArista);

    }


}
