package Clases_auxiliares;

import Juego.AirWar;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;


public class Portaaviones extends Rectangle implements Serializable {


    private Random random = new Random();

    private Pane pane;

    //Label para saber cuál portaavión de la lista es
    private final Label label = new Label();
    private final Label hangarSize = new Label();
    private final Label cantidadCombustible = new Label();

    //Texto del label
    private String labelText;

    //Lista de líneas
    private final ArrayList<LineaArista> listaLineasSalida = new ArrayList<>();
    private final ArrayList<LineaArista> listaLineasLlegada = new ArrayList<>();

    //Mapa de líneas con Portaaviones y Aeropuertos
    private HashMap<LineaArista,Object> hashMap;

    //Hangar que delimita el máximo de aviones en el portaavion
    private final ArrayList<Avion> hangar = new ArrayList<>();
    private final int limiteAviones = 5;

    //Combustible total del Portaavion
    private int combustible = 100;
    private final int recarga_por_segundo = 4;
    private final int racionar = racionarCombustible();

    //Instancia de un Timeline para manejar la generación de aviones cada n segundos
    private Timeline timeline;

    public Portaaviones(){

        setWidth(22);  //Ancho del objeto
        setHeight(40);  //Alto del objeto

        //Imagen del aeropuerto
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Imagenes/portaAviones.png")));
        setFill(new ImagePattern(image));

    }

    public void crearLabel(){

        //Indice
        label.setTranslateX(getX() + 25);
        label.setTranslateY(getY());

        label.setStyle("-fx-text-fill: #ffffff");

        label.setText(labelText);

        //Hangar
        hangarSize.setTranslateX(getX() + 25);
        hangarSize.setTranslateY(getY() + 25);

        hangarSize.setStyle("-fx-text-fill: #ffffff");

        hangarSize.setText("0");

        //Combustible
        cantidadCombustible.setTranslateX(getX() - 35);
        cantidadCombustible.setTranslateY(getY());

        cantidadCombustible.setStyle("-fx-text-fill: #ffffff");

        cantidadCombustible.setText(String.valueOf(combustible));

        pane.getChildren().addAll(label,hangarSize,cantidadCombustible);

    }

    public void instanciarAviones(){

        //Aqui debe ir el llamado al algoritmo para determinar la mejor ruta
        if (listaLineasSalida.size() == 0) {
            return;
        }

        int elementoAleatorio = random.nextInt(listaLineasSalida.size());
        instanciarAviones(listaLineasSalida.get(elementoAleatorio),elementoAleatorio);

    }

    private void instanciarAviones(Avion avion){

        //Comprueba si el nodo tiene al menos 1 vertice saliendo de él
        if (listaLineasSalida.size() == 0){
            return;
        }

        avion.setVisible(true);

        //Aqui debe ir el llamado al algoritmo para determinar la mejor ruta

        int elementoAleatorio = random.nextInt(listaLineasSalida.size());

        instanciarAviones(listaLineasSalida.get(elementoAleatorio),elementoAleatorio,avion);

    }

    private void instanciarAviones(LineaArista lineaArista,int indiceRandom){

        double inicioX = listaLineasSalida.get(indiceRandom).getStartX();
        double inicioY = listaLineasSalida.get(indiceRandom).getStartY();
        double finX = listaLineasSalida.get(indiceRandom).getEndX();
        double finY = listaLineasSalida.get(indiceRandom).getEndY();
        double angulo = listaLineasSalida.get(indiceRandom).obtenerAngulo();
        LineaArista lineaAvion = listaLineasSalida.get(indiceRandom);

        //Path para controlar el desplazamiento del avión por la línea
        Path path = new Path();

        //Agregar puntos de inicio
        path.getElements().add(new MoveTo(lineaArista.getStartX(),lineaArista.getStartY()));

        //Agregar puntos de llegada
        path.getElements().add(new LineTo(lineaArista.getEndX(), lineaArista.getEndY()));

        Avion avion = new Avion(GeneradorNombres.generarNombre());

        avion.setInicioX(inicioX);
        avion.setInicioY(inicioY);
        avion.setFinX(finX);
        avion.setFinY(finY);
        avion.setRotate(angulo);
        avion.calcularDistancia(inicioX,inicioY,finX,finY);
        avion.setPath(path);
        avion.setLineaLocal(lineaAvion);

        //Guardar el avion en la lista global
        //listaAviones.add(avion);
        AirWar.agregarElementoListaAvion(avion);

        //Darle un Pane
        avion.setPane(pane);

        //Coords de spawn del avion
        avion.setX(listaLineasSalida.get(indiceRandom).getStartX());
        avion.setY(listaLineasSalida.get(indiceRandom).getStartY());
        avion.setHashMap(this.hashMap);

        avion.moverAvion();

        //Añado el avión al Pane
        Platform.runLater(() -> {
            pane.getChildren().add(avion);
        });


    }

    private void instanciarAviones(LineaArista lineaArista,int indiceRandom,Avion avion){

        double inicioX = listaLineasSalida.get(indiceRandom).getStartX();
        double inicioY = listaLineasSalida.get(indiceRandom).getStartY();
        double finX = listaLineasSalida.get(indiceRandom).getEndX();
        double finY = listaLineasSalida.get(indiceRandom).getEndY();
        double angulo = listaLineasSalida.get(indiceRandom).obtenerAngulo();
        LineaArista lineaAvion = listaLineasSalida.get(indiceRandom);

        //Path para controlar el desplazamiento del avión por la línea
        Path path = new Path();

        //Agregar puntos de inicio
        path.getElements().add(new MoveTo(lineaArista.getStartX(),lineaArista.getStartY()));

        //Agregar puntos de llegada
        path.getElements().add(new LineTo(lineaArista.getEndX(), lineaArista.getEndY()));

        avion.setInicioX(inicioX);
        avion.setInicioY(inicioY);
        avion.setFinX(finX);
        avion.setFinY(finY);
        avion.setRotate(angulo);
        avion.calcularDistancia(inicioX,inicioY,finX,finY);
        avion.setPath(path);
        avion.setLineaLocal(lineaAvion);

        //Darle un pane
        avion.setPane(pane);

        //Coords de spawn del avion
        avion.setX(listaLineasSalida.get(indiceRandom).getStartX());
        avion.setY(listaLineasSalida.get(indiceRandom).getStartY());
        avion.setHashMap(this.hashMap);

        avion.moverAvion();

    }

    public int solicitarRecarga(){

        //Si el cumbustible es igual o mayor a la ración, darle el combustible, si no, no
        if (combustible >= racionar){

            int x;

            //Si el resultado es menor a cero, vamos a conservar la diferencia positiva
            if ((combustible - racionar) < 0){
                x = racionar - combustible;
            }
            else if ((combustible - racionar) > 0){
                x = combustible - racionar;
            }
            else {
                x = 0;
            }

            //int x = combustible -= racionar;
            //combustible -= racionar;

            combustible = x;


            cantidadCombustible.setText(String.valueOf(x));

            return x;
        }else {

            return 0;

        }

    }

    private void recargarCombustiblePortaavion(int recarga){

        if (combustible + recarga >= 100){
            combustible = 100;
        }else {
            combustible += recarga;
        }

        cantidadCombustible.setText(String.valueOf(combustible));

    }


    public void generarAvionHangar(){

        int numRandom = random.nextInt(3);

        if (numRandom == 0){
            Avion avion = hangar.get(0);

            avion.setRecargando_combustible(false);
            instanciarAviones(avion);

            hangar.remove(0);

            hangarSize.setText(String.valueOf(hangar.size()));


        }

    }

    private int racionarCombustible(){

        int min = 5, max = 7;

        int x = random.nextInt(max - min + 1) + min;

        return combustible / x;

    }

    public void setLinea_salida(LineaArista linea_salida) {
        listaLineasSalida.add(linea_salida);
    }

    public void setLinea_llegada(LineaArista linea_llegada){
        listaLineasLlegada.add(linea_llegada);
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
        label.setText(this.labelText);
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public double getCombustible() {
        return combustible;
    }

    //Agregar un avión al portaaviones
    public boolean almacenarAvion(Avion avion){
        if (hangar.size() < limiteAviones) {
            hangar.add(avion);
            //avion.setVisible(false);
            hangarSize.setText(String.valueOf(hangar.size()));
            return true;
        }

        return false;

    }

    public int hangarSize(){

        return hangar.size();

    }

    public ArrayList<LineaArista> getListaLineasSalida() {
        return listaLineasSalida;
    }

    public HashMap<LineaArista, Object> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<LineaArista, Object> hashMap) {
        this.hashMap = hashMap;
    }

    @Override
    public String toString(){
        return "PortaAvión";
    }

    public void llenarTanquePortaavion(){
        recargarCombustiblePortaavion(recarga_por_segundo);
    }
}
