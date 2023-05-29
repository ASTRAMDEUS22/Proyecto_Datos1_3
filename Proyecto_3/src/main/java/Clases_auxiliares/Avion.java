package Clases_auxiliares;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Avion extends Rectangle {

    int velocidad;
    int eficiencia;
    int fortaleza;
    int combustible = 100;

    Image image = new Image(getClass().getResourceAsStream("/Imagenes/planeSprite.gif"));

    TranslateTransition transition;

    public Avion() {

        setWidth(50);  //Ancho del objeto
        setHeight(50);  //Alto del objeto

        setFill(new ImagePattern(image));

        this.transition = new TranslateTransition(Duration.millis(5000),this);

        transition.setInterpolator(Interpolator.EASE_BOTH);

        avanzarAbajo();

    }

    public void disminuyeCombustible(){
        while (true) {
            System.out.println(combustible);
            this.combustible-=3.5;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void avanzarAbajo(){

        transition.setToX(600);
        transition.setToY(400);



        transition.play();

    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getEficiencia() {
        return eficiencia;
    }

    public void setEficiencia(int eficiencia) {
        this.eficiencia = eficiencia;
    }

    public int getFortaleza() {
        return fortaleza;
    }

    public void setFortaleza(int fortaleza) {
        this.fortaleza = fortaleza;
    }
}
