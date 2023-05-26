package Clases_auxiliares;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;



public class Portaaviones extends Rectangle {

    //Imagen del aeropuerto
    Image image = new Image(getClass().getResourceAsStream("/Imagenes/portaAviones.png"));

    public Portaaviones(){

        setWidth(50);  //Ancho del objeto
        setHeight(50);  //Alto del objeto

        setFill(new ImagePattern(image));

    }


}
