package Clases_auxiliares;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Random;


public class Portaaviones extends Rectangle implements Runnable{

    //Imagen del aeropuerto
    Image image = new Image(getClass().getResourceAsStream("/Imagenes/portaAviones.png"));

    int cont = 10000;

    Pane pane;

    public Portaaviones(Pane pane){

        setWidth(50);  //Ancho del objeto
        setHeight(50);  //Alto del objeto

        setFill(new ImagePattern(image));

        Thread hilo = new Thread(this);
        hilo.start();

        this.pane = pane;

    }

    public void instanciarAviones(){

        while (cont > 0){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Random random = new Random();

            int numRandom = random.nextInt(3);
            //System.out.println(numRandom);
            //System.out.println("--------------------------------");

            if (numRandom == 1){

                Avion avion = new Avion();
                avion.setX(getX());
                avion.setY(getY());

                Platform.runLater(() -> {

                    pane.getChildren().add(avion);

                });



            }

            cont--;


        }

    }


    @Override
    public void run(){
        instanciarAviones();

    }


}
