package Clases_auxiliares;

import Juego.AirWar;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class Temporizador {

    private final static Duration intervalo = Duration.seconds(1);

    private ArrayList<Avion> listaAviones = AirWar.obtenerListaAviones();
    private ArrayList<Portaaviones> listaPortaaviones;
    private ArrayList<Aeropuerto> listaAeropuertos;
    private static final String host = "localhost";
    private static final int serverPort = 2222;

    public Temporizador(
            //ArrayList<Avion> listaAviones,
            ArrayList<Portaaviones> listaPortaaviones,
            ArrayList<Aeropuerto> listaAeropuertos
    ){
        //this.listaAviones = listaAviones;
        this.listaPortaaviones = listaPortaaviones;
        this.listaAeropuertos = listaAeropuertos;

    }

    public void iniciarCont() {
        Timeline timeline = new Timeline(new KeyFrame(intervalo, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //System.out.println(listaAviones.size());

                //Revisa si hay aviones en los hangares de las plaatformas
                verificarHangares();

                //Revisa si los aviones deben de recargar combustible
                verificarAviones();

                //Le recarga combustible a los portaaviones y aeropuertos
                recargaCombustiblePlataformas();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void verificarHangares(){

        for (Portaaviones portaaviones : listaPortaaviones){

            if (portaaviones.hangarSize() != 0){
                portaaviones.generarAvionHangar();

            }

        }

        for (Aeropuerto aeropuerto : listaAeropuertos){

            if (aeropuerto.hangarSize() != 0){

                aeropuerto.generarAvionHangar();

            }

        }
    }

    public void verificarAviones(){

        //Verifica si la lista de aviones esta vacía
        if (listaAviones.size() == 0){
            return;
        }

        //Lista de aviones temporal
        ArrayList<Mensaje> tempList = new ArrayList<>();

        System.out.println(listaAviones.get(0).getCombustible());

        for (int i = 0; i < listaAviones.size();i++) {

            listaAviones.get(0).setStyle("-fx-fill: #ff0000");

            Avion avion = listaAviones.get(i);

            if (!listaAviones.get(i).isRecargando_combustible()){
                disminuirCombustible(listaAviones.get(i),i);

            }

            if (listaAviones.size() == 0) {
                return;
            }



            //Mensaje con las caracteristicas del avion actual
            Mensaje mensaje = new Mensaje();

            //Nombre
            mensaje.setNombreAvion(avion.getNombre());
            //Esta recargando combustible
            mensaje.setRecargandoCombustible(avion.isRecargando_combustible());
            //Combustible actual
            mensaje.setCombustibleAvion(avion.getCombustible());
            //Vida del avion
            mensaje.setFortaleza(avion.getFortaleza());
            //Eficiencia de combustible
            mensaje.setEficiencia(avion.getEficiencia());
            //Velocidad actual
            mensaje.setVelocidad(avion.getVelocidad());
            //Distancia que debe recorrer
            mensaje.setDistancia(avion.getDistancia());


            //Se añade el mensaje la lista de mensajes
            tempList.add(mensaje);

        }

        Mensaje mensaje = new Mensaje("listaAviones");
        mensaje.setListaMensajes(tempList);

       enviarMensajeServidor(mensaje);

    }

    public void recargaCombustiblePlataformas(){

        for (Portaaviones portaaviones: listaPortaaviones){
            portaaviones.llenarTanquePortaavion();
        }

        for (Aeropuerto aeropuerto: listaAeropuertos){
            //System.out.println(aeropuerto.getCombustible());
            aeropuerto.llenarTanqueAeropuerto();
        }



    }

    private void disminuirCombustible(Avion avion,int i) {

        avion.disminuirCombustible();

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



}
