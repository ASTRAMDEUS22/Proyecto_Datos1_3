package Juego;

import Clases_auxiliares.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;


/**
 * Esta clase contiene la logica del juego principal, AirWar
 */
public class
AirWar extends Application implements Runnable{
    /**
     * Entrada principal del juego en JavaFX
     * @param args argumentos que se pasan al juego
     */
    public static void main(String[] args) {
        launch(args);
    }

    //Listas
    ArrayList<Portaaviones> listaPortaaviones = new ArrayList<>();
    ArrayList<Aeropuerto> listaAeropuertos = new ArrayList<>();

    //Pane
    Pane pane;

    //Random
    Random random = new Random();

    //Ints
    final static int minAlto = 75,maxAlto = 552,minAncho = 75,maxAncho = 1281,maxPorta = 8,minPorta = 4,minAero = 5,
            maxAero = 12;

    //Conexion con el servidor
    final static String host = "localhost";
    final static int localport = 4444,serverPort = 2222;

    //Socket servidor
    private ServerSocket serverSocket;

    //Hashmap para conectar una línea y un portaavion
    private HashMap<LineaArista, Object> mapaDestinos = new HashMap<>();


    //Lista de aviones
    private static ArrayList<Avion> listaAviones = new ArrayList<>();

    //Generar contador de tiempo general
    Temporizador temporizador = new Temporizador(
            //listaAviones,
            listaPortaaviones,
            listaAeropuertos
    );


    public AirWar(){

        try {
            this.serverSocket = new ServerSocket(localport);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Thread thread = new Thread(this);
        thread.start();


    }

    /**
     * Este metodo genera el apartado grafico del juego, como los portaviones y aeropuertos, que representan los nodos de un grafo, ademas inicia el temporizador
     * @param primaryStage la escena del juego
     */
    @Override
    public void start(Stage primaryStage) {

        this.pane = new Pane();

        temporizador.iniciarCont();

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Imagenes/Mapa_Mudo_del_Mundo.png")));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        pane.getChildren().add(imageView);


        int barcos_generados = generarPortaRandom(),aeropuertos_generados = generarAeroRandom();

        PixelReader pixelReader = image.getPixelReader();

        //Genera los portaaviones en el mapa
        generarPortaaviones(barcos_generados,pixelReader);

        //Genera los aeropuertos en el mapa
        generarAeropuertos(aeropuertos_generados,pixelReader);

        //Crea las líneas que conectan los nodos del grafo
        crearAristas();

        //Genera los primeros aviones
        generarAviones();

        Scene scene = new Scene(pane,maxAncho + 1,maxAlto + 1);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void generarPortaaviones(int aeropuertos_generados,PixelReader pixelReader){

        //Índice para identificar los barcos
        int i = 0;

        //Generar barcos
        while (aeropuertos_generados != 0){

            int x = generarCoordsRandomX();
            int y = generarCoordsRandomY();

            Color color = pixelReader.getColor(x,y);

            int red = (int) (color.getRed() * 255);
            int green = (int) (color.getGreen() * 255);
            int blue = (int) (color.getBlue() * 255);

            if (red == 63 && green == 72 && blue == 204) {  //Es mar

                Portaaviones portaaviones = new Portaaviones();
                portaaviones.setPane(pane);
                portaaviones.setX(x);
                portaaviones.setY(y);

                pane.getChildren().add(portaaviones);
                listaPortaaviones.add(portaaviones);

                portaaviones.setLabelText(String.valueOf(i));
                portaaviones.crearLabel();

                aeropuertos_generados--;
                i++;

            }

        }

    }

    public void generarAeropuertos(int aeropuertos_generados,PixelReader pixelReader){

        int i = 0;

        //Generar aeropuertos
        while (aeropuertos_generados > 0){

            int x = generarCoordsRandomX();
            int y = generarCoordsRandomY();


            Color color = pixelReader.getColor(x,y);

            int red = (int) (color.getRed() * 255);
            int green = (int) (color.getGreen() * 255);
            int blue = (int) (color.getBlue() * 255);

            if (red != 63 && green != 71 && blue != 204) {  //Es tierra

                //Instancia de un aeropuerto
                Aeropuerto aeropuerto = new Aeropuerto();
                aeropuerto.setX(x);
                aeropuerto.setY(y);
                aeropuerto.setPane(pane);

                pane.getChildren().addAll(aeropuerto);
                listaAeropuertos.add(aeropuerto);

                aeropuerto.setLabelText(String.valueOf(i));
                aeropuerto.crearLabel();

                aeropuertos_generados--;

                i++;

            }

        }

    }

    /**
     * Este metodo genera las aristas entre los nodos del grafo generado
     */
    public void crearAristas() {

        generarLineaPortaaviones();
        generarLineaAeropuerto();

    }

    /**
     * Este metodo genera las aristas de los portaaviones de forma aleatoria
     */
    public void generarLineaPortaaviones(){

        //Ejecuta mientras el índice este en la lista
        for (int i = 0;i < listaPortaaviones.size();i++){

            //Genera un número aleatorio
            int aleatorio = random.nextInt(listaPortaaviones.size());

            //Generará números aleatorios mientras que el elemento actual sea igual al elemento aleatorio
            while (listaPortaaviones.get(i).equals(listaPortaaviones.get(aleatorio))){

                aleatorio = random.nextInt(listaPortaaviones.size());

            }

            //Recorre los demás elementos de la lista menos el primero
            for (int j = i + 1;j < listaPortaaviones.size();j++){

                //Probabilidad de 1/2 de generar una línea
                int probabilidad = random.nextInt(10);

                if (probabilidad > 2) {
                    generaPortaPorta(listaPortaaviones.get(i), listaPortaaviones.get(j));
                }
            }

            //Recorre la lista de aeropuertos para generar conexiones aleatorias
            for (Aeropuerto listaAeropuerto : listaAeropuertos) {

                int probabilidad = random.nextInt(10);

                if (probabilidad > 4) {

                    generaPortaAero(listaPortaaviones.get(i), listaAeropuerto);

                }

            }


        }
    }

    /**
     * Este metodo genera las aristas de los aeropuertos de forma aleatoria
     */
    public void generarLineaAeropuerto(){

        //Ejecuta mientras el índice este en la lista
        for (int i = 0;i < listaAeropuertos.size();i++){

            //Genera un número aleatorio
            int aleatorio = random.nextInt(listaAeropuertos.size());

            //Generará números aleatorios mientras que el elemento actual sea igual al elemento aleatorio
            while (listaAeropuertos.get(i).equals(listaAeropuertos.get(aleatorio))){

                aleatorio = random.nextInt(listaAeropuertos.size());

            }

            //Recorre la lista menos el primero elemento
            for (int j = i + 1;j < listaAeropuertos.size();j++) {

                int probabilidad = random.nextInt(10);

                //Una probabilidad más alta de generar aristas, ya que hay más aeropuertos que portaaviones
                if (probabilidad > 4){

                    generaAeroAero(listaAeropuertos.get(i), listaAeropuertos.get(j));

                }
            }

            //Recorre la lista de portaaviones para generar conexiones aleatorias
            for (Portaaviones listaPortaavione : listaPortaaviones) {

                int probabilidad = random.nextInt(10);

                if (probabilidad > 3) {

                    generaAeroPorta(listaAeropuertos.get(i), listaPortaavione);

                }

            }

        }
    }

    public static void agregarElementoListaAvion(Avion avion){
        System.out.println("Se añade un avión");
        listaAviones.add(avion);
    }

    public static ArrayList<Avion> obtenerListaAviones(){
        return listaAviones;
    }

    public static void eliminarElementoListaAviones(Avion avion){
        System.out.println("Se elimina un avion");
        listaAviones.remove(avion);
    }

    private int generarCoordsRandomX(){
        return random.nextInt(maxAncho - minAncho + 1) + minAncho;
    }

    private int generarCoordsRandomY() {
        return random.nextInt(maxAlto - minAlto + 1) + minAlto;
    }

    private int generarPortaRandom(){return random.nextInt(maxPorta - minPorta + 1) + minPorta;}

    /**
     * Genera un aeropuerto aleatorio
     * @return aeropuerto generado
     */
    private int generarAeroRandom(){return random.nextInt(maxAero - minAero + 1) + minAero;}

    /**
     * Conecta un portaaviones con un portaaviones aleatorio
     * @param portaaviones portaaviones
     * @param portaavionesRandom portaaviones
     */
    public void generaPortaPorta(Portaaviones portaaviones,Portaaviones portaavionesRandom){

        LineaArista lineaArista = new LineaArista();

        lineaArista = lineaArista.generarLinea(portaaviones,portaavionesRandom);

        //Conecta la línea con el portaaviones de salida
        mapaDestinos.put(lineaArista,portaaviones);
        mapaDestinos.put(lineaArista,portaavionesRandom);


        pane.getChildren().add(lineaArista);

    }

    /**
     * Conecta un aeropuerto con un aeropuerto aleatoria
     * @param aeropuerto aeropuerto
     * @param aeropuertoRandom aeropuerto aleatorio
     */
    public void generaAeroAero(Aeropuerto aeropuerto,Aeropuerto aeropuertoRandom){

        LineaArista lineaArista = new LineaArista();

        lineaArista = lineaArista.generarLinea(aeropuerto,aeropuertoRandom);

        //Conecta la línea con el portaaviones de salida
        mapaDestinos.put(lineaArista,aeropuerto);
        mapaDestinos.put(lineaArista,aeropuertoRandom);



        pane.getChildren().add(lineaArista);

    }

    /**
     * Conecta un portaaviones con un aeropuerto aleatorio
     * @param portaaviones portaaviones
     * @param aeropuertoRandom aeropuerto aleatorio
     */
    public void generaPortaAero(Portaaviones portaaviones,Aeropuerto aeropuertoRandom){

        LineaArista lineaArista = new LineaArista();

        lineaArista = lineaArista.generarLinea(portaaviones,aeropuertoRandom);

        //Conecta la línea con el portaaviones de salida
        mapaDestinos.put(lineaArista,portaaviones);
        mapaDestinos.put(lineaArista,aeropuertoRandom);



        pane.getChildren().add(lineaArista);

    }

    /**
     * Conecta un aeropuerto con un portaviones aleatorio
     * @param aeropuerto aeropuerto
     * @param portaavionesRandom portaviones aleatorio
     */
    public void generaAeroPorta(Aeropuerto aeropuerto,Portaaviones portaavionesRandom){

        LineaArista lineaArista = new LineaArista();

        lineaArista = lineaArista.generarLinea(aeropuerto,portaavionesRandom);

        //Conecta la línea con el portaaviones de salida
        mapaDestinos.put(lineaArista,aeropuerto);
        mapaDestinos.put(lineaArista,portaavionesRandom);



        pane.getChildren().add(lineaArista);

    }

    /**
     * Este metodo se encarga de generar aviones
     */
    public void generarAviones(){

        Mensaje mensaje = new Mensaje("listaPortaaviones");

        ArrayList<Mensaje> listaMensajes = new ArrayList<>();

        for (Portaaviones portaaviones: listaPortaaviones){

            Mensaje mensaje1 = new Mensaje();

            portaaviones.setHashMap(mapaDestinos);

            portaaviones.instanciarAviones();

            //Obtener el índice del portaavion en la lista
            mensaje1.setI(Integer.parseInt(portaaviones.getLabelText()));

            listaMensajes.add(mensaje1);

        }

        for (Aeropuerto aeropuerto : listaAeropuertos){

            aeropuerto.setHashMap(mapaDestinos);
            aeropuerto.instanciarAviones();

        }

        //System.out.println(mapaDestinos.size());

        mensaje.setListaMensajes(listaMensajes);

        enviarMensajeServidor(mensaje);

    }

    public void crearAvionPorta(int i){

        listaPortaaviones.get(i).instanciarAviones();

    }

    private void crearAvionAero(int i){
        listaAeropuertos.get(i).instanciarAviones();
    }

    public void enviarMensajeServidor(Mensaje mensaje){

        try {
            //Crear un socket
            Socket socket = new Socket(host, serverPort);

            //Crear medio para enviar información
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject(mensaje);

            socket.close();
            out.close();


        }catch (IOException e) {
            System.out.println("No se pudo conectar con el Servidor: " + host + " " + serverPort);
        }

    }

    /**
     * Este metodo se encarga de inicializar la conexion por sockets
     */
    @Override
    public void run(){

        try {

            while (true){

                //Acepta el socket entrante
                Socket socket = serverSocket.accept();

                //Recibe el objeto enviado por el socket
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                Mensaje mensaje = (Mensaje) in.readObject();

                switch (mensaje.getAccion()){

                    case "crearAvionPorta" -> {

                        crearAvionPorta(mensaje.getI());

                    }
                    case "crearAvionAero" -> {

                    }

                }

                socket.close();

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
