package Clases_auxiliares;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase que representa un mensaje en el sistema.
 */
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

    /**
     * Obtiene la acción del mensaje.
     *
     * @return La acción del mensaje.
     */
    public String getAccion() {
        return accion;
    }

    /**
     * Obtiene el índice del objeto.
     *
     * @return El índice del objeto.
     */
    public int getI() {
        return i;
    }

    /**
     * Establece el índice del objeto.
     *
     * @param i El índice del objeto.
     */
    public void setI(int i) {
        this.i = i;
    }

    /**
     * Obtiene la cantidad de aviones en el hangar.
     *
     * @return La cantidad de aviones en el hangar.
     */
    public int getHangar() {
        return hangar;
    }

    /**
     * Establece la cantidad de aviones en el hangar.
     *
     * @param hangar La cantidad de aviones en el hangar.
     */
    public void setHangar(int hangar) {
        this.hangar = hangar;
    }

    public int getCombustiblePlataforma() {
        return combustiblePlataforma;
    }

    public void setCombustiblePlataforma(int combustiblePlataforma) {
        this.combustiblePlataforma = combustiblePlataforma;
    }

    /**
     * Obtiene la lista de mensajes.
     *
     * @return La lista de mensajes.
     */
    public ArrayList<Mensaje> getListaMensajes() {
        return listaMensajes;
    }

    /**
     * Establece la lista de mensajes.
     *
     * @param listaMensajes La lista de mensajes.
     */
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

    @Override
    public String toString(){

        return String.valueOf(i);

    }


}
