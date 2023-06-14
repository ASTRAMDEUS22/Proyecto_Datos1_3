package Clases_auxiliares;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * Esta clase permite realizar tareas en un intevalo de tiempo
 */
public class Temporizador {

    private final static Duration intervalo = Duration.seconds(1);

    private ArrayList<Avion> listaAviones;
    private ArrayList<Portaaviones> listaPortaaviones;
    private ArrayList<Aeropuerto> listaAeropuertos;

    /**
     * Constructor de la clase Temporizador
     * @param listaAviones lista de aviones
     * @param listaPortaaviones lista de portaaviones
     * @param listaAeropuertos lista de aeropuertos
     */
    public Temporizador(
            ArrayList<Avion> listaAviones,
            ArrayList<Portaaviones> listaPortaaviones,
            ArrayList<Aeropuerto> listaAeropuertos
    ){
        this.listaAviones = listaAviones;
        this.listaPortaaviones = listaPortaaviones;
        this.listaAeropuertos = listaAeropuertos;

    }

    /**
     * Este metodo se encarga de iniciar el contador del temporizador
     */
    public void iniciarCont() {
        Timeline timeline = new Timeline(new KeyFrame(intervalo, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

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

    /**
     * Este metodo se encarga de verificar la cantidad de hangares en cada aeropuerto y portaavion
     */
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

    /**
     * Este metodo se encarga de verificar si los aviones deben de recargar combustible
     */
    public void verificarAviones(){

        for (int i = 0; i < listaAviones.size();i++) {

            listaAviones.get(0).setStyle("-fx-fill: #ff0000");

            if (!listaAviones.get(i).isRecargando_combustible()){
                disminuirCombustible(listaAviones.get(i),i);

            }

        }

    }

    /**
     * Este metodo se encarga de recargar el combustible en los aeropuertos y los portaaviones
     */
    public void recargaCombustiblePlataformas(){

        for (Portaaviones portaaviones: listaPortaaviones){
            portaaviones.llenarTanquePortaavion();
        }

        for (Aeropuerto aeropuerto: listaAeropuertos){
            //System.out.println(aeropuerto.getCombustible());
            aeropuerto.llenarTanqueAeropuerto();
        }



    }

    /**
     * Este metodo se encarga de disminuir el combustible
     * @param avion avion al cual se le va disminuir el combustible
     * @param i cantidad de combustible a disminuir
     */
    private void disminuirCombustible(Avion avion,int i) {

        avion.disminuirCombustible(i,listaAviones);

    }



}
