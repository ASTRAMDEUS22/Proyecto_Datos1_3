package Clases_auxiliares;

import java.io.Serializable;
import java.util.ArrayList;

public class Mensaje implements Serializable {

    //Acción que debe interpretar el receptor
    private String accion;

    //Información de los Portaaviones

    //Índice del objeto
    private int i;

    //Cantidad de aviones en el hangar
    private int hangar;

    //Combustible de la plataforma
    private int combustible;

    //Lista de mensajes
    ArrayList<Mensaje> listaMensajes = new ArrayList<>();

    public Mensaje(){

    }


    public Mensaje(String accion){

        this.accion = accion;

    }


    public String getAccion(){
        return accion;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getHangar() {
        return hangar;
    }

    public void setHangar(int hangar) {
        this.hangar = hangar;
    }

    public int getCombustible() {
        return combustible;
    }

    public void setCombustible(int combustible) {
        this.combustible = combustible;
    }

    public ArrayList<Mensaje> getListaMensajes() {
        return listaMensajes;
    }

    public void setListaMensajes(ArrayList<Mensaje> listaMensajes) {
        this.listaMensajes = listaMensajes;
    }

    @Override
    public String toString(){

        return String.valueOf(i);

    }


}
