package Clases_auxiliares;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Random;


public class Aeropuerto extends Rectangle {

    //Imagen del aeropuerto
    Image image = new Image(getClass().getResourceAsStream("/Imagenes/aeropuerto.gif"));

    //Ancho y alto del objeto
    int ancho = 30, alto = 30;

    //Maximo de aviones permitidos en el aeropuerto
    int hangar;

    //Combustible
    int combustible = 100;
    int distribucion_combustible = racionarCombustible();

    public Aeropuerto(){

        setWidth(ancho);  //Ancho
        setHeight(alto);  //Alto

        this.hangar = maximoAleatorio();



        setFill(new ImagePattern(image));

    }

    public int maximoAleatorio(){

        int min = 3, max = 8;

        Random random = new Random();

        return random.nextInt(max - min + 1) + min;


    }

    public int racionarCombustible(){

        int min = 3,max = 5;

        Random random = new Random();

        return random.nextInt(max - min + 1) + min;

    }

    public int dispensarCombustible(){

        return combustible / distribucion_combustible;

    }




}
