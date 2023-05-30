package Clases_auxiliares;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Avion extends Rectangle {

    int velocidad;
    int eficiencia;
    int fortaleza;
    int combustible = 100;

    double inicioX,inicioY,finX,finY,anguloInclinacion;

    Image image = new Image(getClass().getResourceAsStream("/Imagenes/planeSprite.gif"));

    TranslateTransition transition;

    public Avion(double inicioX,double inicioY,double finX,double finY,double anguloInclinacion) {

        setWidth(50);  //Ancho del objeto
        setHeight(50);  //Alto del objeto

        //Coordenadas donde empezará y terminará el avión
        this.inicioX = inicioX;
        this.inicioY = inicioY;
        this.finX = finX;
        this.finY = finY;
        this.anguloInclinacion = anguloInclinacion;

        setFill(new ImagePattern(image));

        //Coords de spawn del avión
        setX(inicioX);
        setY(inicioY);

        this.transition = new TranslateTransition(Duration.millis(5000),this);

        transition.setInterpolator(Interpolator.EASE_BOTH);

        moverAvion();

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

    public void moverAvion(){

        System.out.println("Destino: " + finX + " " + finY);

        System.out.println("Angulo de inclinación: " + anguloInclinacion);

        /*Rotate rotate = new Rotate(anguloInclinacion,getWidth() / 2,getHeight() / 2);

        getTransforms().add(rotate);*/



        transition.setToX(finX - inicioX);
        transition.setToY(finY - inicioY);



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
