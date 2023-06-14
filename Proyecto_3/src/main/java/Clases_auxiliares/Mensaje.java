package Clases_auxiliares;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase que representa un mensaje en el sistema.
 */
public class Mensaje implements Serializable {

    // Acción que debe interpretar el receptor
    private String accion;

    // Información de los Portaaviones

    // Índice del objeto
    private int i;

    // Cantidad de aviones en el hangar
    private int hangar;

    // Combustible de la plataforma
    private int combustible;

    // Lista de mensajes
    ArrayList<Mensaje> listaMensajes = new ArrayList<>();

    /**
     * Constructor sin argumentos de la clase Mensaje.
     */
    public Mensaje() {
    }

    /**
     * Constructor de la clase Mensaje.
     *
     * @param accion La acción a ser interpretada por el receptor.
     */
    public Mensaje(String accion) {
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

    /**
     * Obtiene el combustible de la plataforma.
     *
     * @return El combustible de la plataforma.
     */
    public int getCombustible() {
        return combustible;
    }

    /**
     * Establece el combustible de la plataforma.
     *
     * @param combustible El combustible de la plataforma.
     */
    public void setCombustible(int combustible) {
        this.combustible = combustible;
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

    /**
     * Devuelve una representación en forma de cadena del objeto Mensaje.
     *
     * @return La representación en forma de cadena del objeto Mensaje.
     */
    @Override
    public String toString() {
        return String.valueOf(i);
    }
}

