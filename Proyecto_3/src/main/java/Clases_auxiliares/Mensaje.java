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
    private int combustiblePlataforma;

    //Lista de mensajes
    private ArrayList<Mensaje> listaMensajes = new ArrayList<>();

    //Sector de aviones
    private String nombreAvion;
    private boolean recargandoCombusible;
    private int combustibleAvion;
    private int fortaleza;
    private int eficiencia;
    private int velocidad;
    private double distancia;



    //Avion
    private Avion avion;

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

    public int getCombustiblePlataforma() {
        return combustiblePlataforma;
    }

    public void setCombustiblePlataforma(int combustiblePlataforma) {
        this.combustiblePlataforma = combustiblePlataforma;
    }

    public ArrayList<Mensaje> getListaMensajes() {
        return listaMensajes;
    }

    public void setListaMensajes(ArrayList<Mensaje> listaMensajes) {
        this.listaMensajes = listaMensajes;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public String getNombreAvion() {
        return nombreAvion;
    }

    public void setNombreAvion(String nombreAvion) {
        this.nombreAvion = nombreAvion;
    }

    public boolean isRecargandoCombustible() {
        return recargandoCombusible;
    }

    public void setRecargandoCombustible(boolean recargandoCombusible) {
        this.recargandoCombusible = recargandoCombusible;
    }

    public int getCombustibleAvion() {
        return combustibleAvion;
    }

    public void setCombustibleAvion(int combustibleAvion) {
        this.combustibleAvion = combustibleAvion;
    }

    public int getFortaleza() {
        return fortaleza;
    }

    public void setFortaleza(int fortaleza) {
        this.fortaleza = fortaleza;
    }

    public int getEficiencia() {
        return eficiencia;
    }

    public void setEficiencia(int eficiencia) {
        this.eficiencia = eficiencia;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public boolean isRecargandoCombusible() {
        return recargandoCombusible;
    }

    public void setRecargandoCombusible(boolean recargandoCombusible) {
        this.recargandoCombusible = recargandoCombusible;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public Mensaje getMensaje(){
        return this;
    }

    @Override
    public String toString(){

        return String.valueOf(i);

    }


}
