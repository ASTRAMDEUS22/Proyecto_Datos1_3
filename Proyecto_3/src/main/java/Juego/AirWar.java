package Juego;

import Clases_auxiliares.Aeropuerto;
import Clases_auxiliares.Avion;
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
import java.util.Random;


public class
AirWar extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Label label;


    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        label = new Label();
        label.setText("hiii");

        //String rutaLocal = "Imagenes/mapaMundi.png";
        Pane pane = new Pane();



        Image image = new Image(getClass().getResourceAsStream("/Imagenes/mapaDelMundo.jpg"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        pane.getChildren().add(imageView);

        Avion avion = new Avion();
        avion.setX(200);
        avion.setY(200);
        avion.setRotate(180);

        //Hilo del avion
        Thread hiloAvion = new Thread(avion);
        hiloAvion.start();



        pane.getChildren().add(avion);

        int barcos_generados = 5,aeropuertos_generados = 8;

        int minAlto = 0,maxAlto = 700,minAncho = 0,maxAncho = 1500;

        PixelReader pixelReader = image.getPixelReader();

        while (barcos_generados > 0){

            Random random = new Random();

            int y = random.nextInt(maxAlto - minAlto + 1) + minAlto;
            int x = random.nextInt(maxAncho - minAncho + 1) + minAncho;

            Color color = pixelReader.getColor(x,y);

            int red = (int) (color.getRed() * 255);
            int green = (int) (color.getGreen() * 255);
            int blue = (int) (color.getBlue() * 255);

            if (red == 63 && green == 71 && blue == 204) {  //Es mar

                Portaaviones portaaviones = new Portaaviones();
                portaaviones.setX(x);
                portaaviones.setY(y);

                pane.getChildren().add(portaaviones);

                barcos_generados--;

            }

        }

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

                aeropuertos_generados--;

            }

        }



        Scene scene = new Scene(pane,1500,800);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
