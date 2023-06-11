package Clases_auxiliares;

import javafx.animation.PathTransition;
//import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Objects;
import java.util.Random;

public class Avion extends Rectangle implements Serializable {

    private Random random = new Random();

    //Velocidad del avión
    private int velocidad = definirVelocidad();

    //Consumo de combstible por segundo
    private int eficiencia = 10;

    //Vida del avión ante los disparos
    private int fortaleza = 2;

    //Combustible total del avión
    private int combustible = 100;


    private double distancia;

    private double inicioX,inicioY,finX,finY;

    private Path path;

    private HashMap<LineaArista,Object> hashMap;

    private LineaArista lineaLocal;

    private Pane pane;

    private boolean recargando_combustible = false;

    private Object zonaAterrizaje;

    //TranslateTransition transition;
    private PathTransition pathTransition = new PathTransition();

    public Avion() {

        setWidth(10);  //Ancho del objeto
        setHeight(10);  //Alto del objeto

        setStyle("-fx-fill: #c5bc0c");

    }

    public void moverAvion(){

        definirVelocidad();

        //setRotate(45);

        double desplazamiento = distancia / velocidad;

        pathTransition.setNode(this);
        pathTransition.setPath(path);
        pathTransition.setDuration(Duration.seconds(desplazamiento));
        pathTransition.play();

        recargando_combustible = false;

        //Evento que detecta si el avión llega a su destino
        pathTransition.setOnFinished(event -> {

            Object object = hashMap.get(lineaLocal);

            //Si el objeto es portaaviones
            if (object instanceof Portaaviones temp){

                //Si no se puede almacenar más aviones siga reduciendo el combustible, si no, que recargue
                if (temp.almacenarAvion(this)){
                    this.recargando_combustible = true;
                }

                //Indica que está en un nodo y procede a recargar combustible
                //this.recargando_combustible = true;

                //Se solicita recarga de combustible
                this.combustible = temp.solicitarRecarga();


            }
            //Si el objeto es aeropuerto
            else if (object instanceof Aeropuerto temp){

                //Si no se puede almacenar más aviones siga reduciendo el combustible, si no, que recargue
                if (temp.almacenarAvion(this)){
                    this.recargando_combustible = true;
                }

                //System.out.println(recargando_combustible);

                //Se solicita recarga de combustible
                this.combustible = temp.solicitarRecarga();

            }

        });


    }

    public void calcularDistancia(double startX,double startY,double endX,double endY){

        //Distancia entre el punto de inicio y llegada
        distancia = Math.sqrt(Math.pow(endX - startX,2) + Math.pow(endY - startY,2));

    }

    public int definirVelocidad(){

        int maxFast = 120, minFast = 80;

        return random.nextInt(maxFast - minFast + 1) + minFast;

    }

    public void destruirAvion(int i,ArrayList<Avion> listaAviones){

        pane.getChildren().remove(this);
        listaAviones.remove(i);

    }

    public int getCombustible() {
        return combustible;
    }

    public void disminuirCombustible(int i, ArrayList<Avion> listaAviones) {

        int x = this.combustible - eficiencia;

        System.out.println(x);

        //Si el avion se queda sin combustible
        if (x <= 0){

            destruirAvion(i,listaAviones);

        }
        else {

            this.combustible -= eficiencia;
        }
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

    //Getters y setters de el inicio y fin del vuelo del avión

    public double getInicioX() {
        return inicioX;
    }

    public void setInicioX(double inicioX) {
        this.inicioX = inicioX;
    }

    public double getInicioY() {
        return inicioY;
    }

    public void setInicioY(double inicioY) {
        this.inicioY = inicioY;
    }

    public double getFinX() {
        return finX;
    }

    public void setFinX(double finX) {
        this.finX = finX;
    }

    public double getFinY() {
        return finY;
    }

    public void setFinY(double finY) {
        this.finY = finY;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public HashMap<LineaArista, Object> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<LineaArista, Object> hashMap) {
        this.hashMap = hashMap;
    }

    public void setLineaLocal(LineaArista lineaLocal) {
        this.lineaLocal = lineaLocal;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public boolean isRecargando_combustible() {
        return recargando_combustible;
    }

    public void setRecargando_combustible(boolean recargando_combustible) {
        this.recargando_combustible = recargando_combustible;
    }
}
