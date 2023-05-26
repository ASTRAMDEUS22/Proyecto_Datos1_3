package Clases_auxiliares;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Avion extends Rectangle implements Runnable{

    int velocidad;
    int eficiencia;
    int fortaleza;
    int combustible = 100;

    Image image = new Image(getClass().getResourceAsStream("/Imagenes/planeSprite.gif"));

    public Avion() {

        setWidth(50);  //Ancho del objeto
        setHeight(50);  //Alto del objeto

        setFill(new ImagePattern(image));

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

    @Override
    public void run(){

        disminuyeCombustible();

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
