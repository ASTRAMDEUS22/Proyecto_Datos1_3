package Clases_auxiliares;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;


public class Aeropuerto extends Rectangle {

    //Imagen del aeropuerto
    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Imagenes/aeropuerto.gif")));

    //Instancia de random
    private Random random = new Random();

    //Ancho y alto del objeto
    private int ancho = 30, alto = 30;

    //Pane
    private Pane pane;

    //Maximo de aviones permitidos en el aeropuerto
    private ArrayList<Avion> hangar = new ArrayList<>();

    //Combustible
    private int combustible = 100;
    private final int racionar = racionarCombustible();
    private final int recarga_por_segundo = 10;

    //Punto de llegada
    private ArrayList<LineaArista> listaLineasLlegada = new ArrayList<>();
    //Punto de salida
    private ArrayList<LineaArista> listaLineasSalida = new ArrayList<>();

    //Mapa de líneas con Portaaviones y Aeropuertos
    private HashMap<LineaArista,Object> hashMap;

    //Lista de aviones
    private ArrayList<Avion> listaAviones;
    private final int limiteAviones = 5;

    //Instancia de un Timeline para manejar la generación de aviones cada n segundos
    private Timeline timeline;

    //Label para ver el índice del aeropuerto
    private String labelText;
    private Label label = new Label();






    public Aeropuerto(){

        setWidth(ancho);  //Ancho
        setHeight(alto);  //Alto

        setFill(new ImagePattern(image));

    }



    public int racionarCombustible(){

        int min = 3,max = 5;

        int x = random.nextInt(max - min + 1) + min;

        return combustible / x;

    }

    public void instanciarAviones(){

        //Aqui debe ir el llamado al algoritmo para determinar la mejor ruta

        int elementoAleatorio = random.nextInt(listaLineasSalida.size());

        instanciarAviones(listaLineasSalida.get(elementoAleatorio),elementoAleatorio);

    }

    private void instanciarAviones(Avion avion){

        //Comprueba si el nodo tiene al menos 1 vertice saliendo de él
        if (listaLineasSalida.size() == 0){
            return;
        }

        //Aqui debe ir el llamado al algoritmo para determinar la mejor ruta

        int elementoAleatorio = random.nextInt(listaLineasSalida.size());

        instanciarAviones(listaLineasSalida.get(elementoAleatorio),elementoAleatorio,avion);

    }

    public void crearLabel(){

        label.setTranslateX(getX() + 25);
        label.setTranslateY(getY());


        label.setStyle("-fx-text-fill: #ffffff");

        label.setText(labelText);

        pane.getChildren().add(label);

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

        Avion avion = new Avion();

        avion.setInicioX(inicioX);
        avion.setInicioY(inicioY);
        avion.setFinX(finX);
        avion.setFinY(finY);
        avion.setRotate(angulo);
        avion.calcularDistancia(inicioX,inicioY,finX,finY);
        avion.setPath(path);
        avion.setLineaLocal(lineaAvion);

        //Guardar el avion en la lista global
        listaAviones.add(avion);

        //Darle un pane
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

        //Guardar el avion en la lista global
        listaAviones.add(avion);

        //Darle un pane
        avion.setPane(pane);

        //Coords de spawn del avion
        avion.setX(listaLineasSalida.get(indiceRandom).getStartX());
        avion.setY(listaLineasSalida.get(indiceRandom).getStartY());
        avion.setHashMap(this.hashMap);

        avion.moverAvion();

    }

    public void generarAvionHangar(){

        int numRandom = random.nextInt(3);

        if (numRandom == 0){
            Avion avion = hangar.get(0);

            avion.setRecargando_combustible(false);
            instanciarAviones(avion);
            hangar.remove(0);


        }

    }

    public int solicitarRecarga(){

        if (combustible >= racionar){
            return combustible;
        }else {
            return 0;
        }

    }

    private void recargarCombustibleAeropuerto(int recarga){

        if (combustible + recarga >= 100){
            combustible = 100;
        }else {
            combustible += recarga;
        }

    }

    //Getters y setters

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public int hangarSize() {
        return hangar.size();
    }

    public void setLabelText(String labelText){
        this.labelText = labelText;
        label.setText(this.labelText);
    }

    //Agregar un avión al portaaviones
    public boolean almacenarAvion(Avion avion){
        if (hangar.size() < limiteAviones) {
            hangar.add(avion);
            return true;
        }

        return false;

    }

    public int getCombustible() {
        return combustible;
    }

    public void setCombustible(int combustible) {
        this.combustible = combustible;
    }

    public void setListaLineasLlegada(LineaArista linea_llegada){
        listaLineasLlegada.add(linea_llegada);
    }

    public void setListaLineasSalida(LineaArista linea_salida) {
        listaLineasSalida.add(linea_salida);
    }

    public HashMap<LineaArista, Object> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<LineaArista, Object> hashMap) {
        this.hashMap = hashMap;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public void setListaAviones(ArrayList<Avion> listaAviones) {
        this.listaAviones = listaAviones;
    }

    public void llenarTanqueAeropuerto(){
        recargarCombustibleAeropuerto(recarga_por_segundo);
    }
}
