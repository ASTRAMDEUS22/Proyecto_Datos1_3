package Clases_auxiliares;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


public class Portaaviones extends Rectangle implements Runnable{

    //Imagen del aeropuerto
    Image image = new Image(getClass().getResourceAsStream("/Imagenes/portaAviones.png"));

    int cont = 10000;

    Pane pane;

    //Punto de llegada
    LineaArista linea_llegada;
    //Punto de salida
    LineaArista linea_salida;

    //Label para saber cuál portaavión de la lista es
    Label label = new Label();

    //Texto del label
    String labelText;

    public Portaaviones(){

        setWidth(22);  //Ancho del objeto
        setHeight(40);  //Alto del objeto

        setFill(new ImagePattern(image));

        Thread hilo = new Thread(this);
        hilo.start();

        this.pane = pane;

    }

    @Override
    public boolean equals(Object object){
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        return true;
    }

    public void crearLabel(){

        //System.out.println(getX() + "  " + getY());

        label.setTranslateX(getX() + 25);
        label.setTranslateY(getY());



        label.setStyle("-fx-text-fill: #ffffff");

        label.setText(labelText);

        pane.getChildren().add(label);

    }

    public void instanciarAviones(double inicioX,double inicioY,double finX,double finY,double anguloInclinacion){

        Avion avion = new Avion(inicioX,inicioY,finX,finY,anguloInclinacion);
        avion.setRotate(linea_llegada.getRotate());

        Platform.runLater(() -> {
            pane.getChildren().add(avion);
        });

        /*while (cont > 0){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Random random = new Random();

            int numRandom = random.nextInt(3);


            if (numRandom == 1){

                Avion avion = new Avion();
                avion.setX(getX());
                avion.setY(getY());

                Platform.runLater(() -> {

                    pane.getChildren().add(avion);

                });



            }

            cont--;

        }*/

    }


    @Override
    public void run(){
        //instanciarAviones();

    }


    public double getFinLinea_X() {
        return linea_salida.getEndX();
    }

    public double getFinLinea_Y(){
        return linea_salida.getEndY();
    }

    public double getComienzoLinea_X() {
        return linea_salida.getStartX();
    }

    public double getComienzoLinea_Y() {
        return linea_salida.getStartY();
    }

    public LineaArista getLinea_salida(){
        return linea_salida;
    }

    public void setLinea_llegada(LineaArista linea_llegada) {
        this.linea_llegada = linea_llegada;
    }

    public void setLinea_salida(LineaArista linea_salida) {
        this.linea_salida = linea_salida;
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
        label.setText(this.labelText);
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }
}
