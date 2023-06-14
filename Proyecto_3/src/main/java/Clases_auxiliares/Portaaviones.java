package Clases_auxiliares;

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

/**
 * Esta clase contiene todos los aspectos relacionados a los portaaviones que se utilizan en el juego
 */
public class Portaaviones extends Rectangle implements Serializable {

    //Imagen del aeropuerto
    private Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Imagenes/portaAviones.png")));


    private Random random = new Random();

    private Pane pane;

    //Label para saber cuál portaavión de la lista es
    private Label label = new Label();

    //Texto del label
    private String labelText;

    //Lista de líneas
    private ArrayList<LineaArista> listaLineasSalida = new ArrayList<>();
    private ArrayList<LineaArista> listaLineasLlegada = new ArrayList<>();

    //Mapa de líneas con Portaaviones y Aeropuertos
    private HashMap<LineaArista,Object> hashMap;

    //Hangar que delimita el máximo de aviones en el portaavion
    private ArrayList<Avion> hangar = new ArrayList<>();
    private final int limiteAviones = 5;

    private ArrayList<Avion> listaAviones;

    //Combustible total del Portaavion
    private int combustible = 100;
    private final int recarga_por_segundo = 10;
    private final int racionar = racionarCombustible();


    //Instancia de un Timeline para manejar la generación de aviones cada n segundos
    private Timeline timeline;

    /**
     * Constructor de la clase
     */
    public Portaaviones(){

        setWidth(22);  //Ancho del objeto
        setHeight(40);  //Alto del objeto

        setFill(new ImagePattern(image));

    }

    /**
     *
     */
    public void crearLabel(){

        label.setTranslateX(getX() + 25);
        label.setTranslateY(getY());


        label.setStyle("-fx-text-fill: #ffffff");

        label.setText(labelText);

        pane.getChildren().add(label);

    }

    /**
     *
     */
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

    /**
     * Se encarga de verificar si el combustible es igual o mayor a la racion
     * @return x si se cumple, 0 en caso de que no
     */
    public int solicitarRecarga(){

        //Si el cumbustible es igual o mayor a la ración, darle el combustible, si no, no
        if (combustible >= racionar){
            int x = combustible;
            combustible -= racionar;
            return x;
        }else {
            return 0;
        }

    }

    /**
     * Se encarga de regargar el combustible al portaavion
     * @param recarga recarga
     */
    private void recargarCombustiblePortaavion(int recarga){

        if (combustible + recarga >= 100){
            combustible = 100;
        }else {
            combustible += recarga;
        }

    }

    /**
     * Genera aviones en el hangar
     */
    public void generarAvionHangar(){

        int numRandom = random.nextInt(3);

        if (numRandom == 0){
            Avion avion = hangar.get(0);

            instanciarAviones(avion);
            hangar.remove(0);


        }

    }

    /**
     * Se encarga de racionar el combustible
     * @return resultado
     */
    private int racionarCombustible(){

        int min = 3, max = 5;

        int x = random.nextInt(max - min + 1) + min;

        return combustible / x;

    }

    /**
     * Coloca la linea de salida del portaavion
     * @param linea_salida linea de salida
     */
    public void setLinea_salida(LineaArista linea_salida) {
        listaLineasSalida.add(linea_salida);
    }

    /**
     * Coloca la linea de llegada del portaavion
     * @param linea_llegada linea de llegada
     */
    public void setLinea_llegada(LineaArista linea_llegada){
        listaLineasLlegada.add(linea_llegada);
    }

    /**
     * Obtiene el texto del Label
     * @return labelText
     */
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

    /**
     * Obtiene el combustible
     * @return combustible
     */
    public double getCombustible() {
        return combustible;
    }

    /**
     * Agrega un avión al portaaviones
     */
    public boolean almacenarAvion(Avion avion){
        if (hangar.size() < limiteAviones) {
            hangar.add(avion);
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

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    @Override
    public String toString(){
        return "PortaAvión";
    }

    public void setListaAviones(ArrayList<Avion> listaAviones) {
        this.listaAviones = listaAviones;
    }

    /**
     * llama al metodo recargarCombustiblePortaavion
     */
    public void llenarTanquePortaavion(){
        recargarCombustiblePortaavion(recarga_por_segundo);
    }
}
