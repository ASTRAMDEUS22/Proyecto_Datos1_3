package Clases_auxiliares;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Clase que representa una línea de arista en un grafo.
 */
public class LineaArista extends Line implements Serializable {

    ArrayList<Avion> listaAviones = new ArrayList<>();

    //Peso de la ruta
    private int pesoRuta;

    //Valor extra dado si se conectan mínimo a 1 portaavión
    private static final double pesoXPorta = 1.25,pesoXMar = 1.50;

    /**
     * Constructor de la clase LineaArista.
     */
    public LineaArista(){

        setOpacity(0.2);
        setStrokeWidth(2);

        setOnMouseClicked(event -> {

            System.out.println("Peso ruta: " + this.pesoRuta);

        });

    }
    /**
     * Genera una línea de arista entre dos portaaviones.
     *
     * @param portaavionInicio  El portaavión de inicio.
     * @param portaavioneFinal  El portaavión de destino.
     * @return La línea de arista generada.
     */
    //Si son dos portaaviones
    public LineaArista generarLinea(Portaaviones portaavionInicio, Portaaviones portaavioneFinal){
        //Linea

        //Empieza en
        setStartX(portaavionInicio.getBoundsInParent().getCenterX());
        setStartY(portaavionInicio.getY());

        //Linea de salida del portaavion
        portaavionInicio.setLinea_salida(this);

        //Termina en
        setEndX(portaavioneFinal.getBoundsInParent().getCenterX());
        setEndY(portaavioneFinal.getY() + portaavioneFinal.getHeight());

        //Linea de llegada del portaavion
        portaavioneFinal.setLinea_llegada(this);

        setStyle("-fx-stroke: #5dff00");

        //Distancia entre el punto de inicio y llegada
        double distancia = Math.sqrt(Math.pow(getEndX() - getStartX(),2) + Math.pow(getEndY() - getStartY(),2));

        System.out.println(distancia);

        //Calcula el peso de la ruta
        calcularPesoRuta((int) distancia,true,true);

        System.out.println(pesoRuta);

        System.out.println("-----------");

        return this;

    }
    /**
     * Genera una línea de arista entre dos aeropuertos.
     *
     * @param aeropuertoInicio  El aeropuerto de inicio.
     * @param aeropuertoFinal   El aeropuerto de destino.
     * @return La línea de arista generada.
     */
    //Si son dos aeropuertos
    public LineaArista generarLinea(Aeropuerto aeropuertoInicio, Aeropuerto aeropuertoFinal){
        //Linea

        //Empieza en
        setStartX(aeropuertoInicio.getBoundsInParent().getCenterX());
        setStartY(aeropuertoInicio.getY());

        //Linea de salida del portaavion
        aeropuertoInicio.setListaLineasSalida(this);

        //Termina en
        setEndX(aeropuertoFinal.getBoundsInParent().getCenterX());
        setEndY(aeropuertoFinal.getY() + aeropuertoFinal.getHeight());

        //Linea de llegada del portaavion
        aeropuertoFinal.setListaLineasLlegada(this);

        setStyle("-fx-stroke: #b751d5");
        //setStroke(Color.BLACK);

        //Distancia entre el punto de inicio y llegada
        double distancia = Math.sqrt(Math.pow(getEndX() - getStartX(),2) + Math.pow(getEndY() - getStartY(),2));

        System.out.println(distancia);

        //Calcula el peso de la ruta
        calcularPesoRuta((int) distancia,false,false);

        System.out.println(pesoRuta);

        System.out.println("-----------");

        return this;

    }
    /**
     * Genera una línea de arista entre un aeropuerto y un portaavión.
     *
     * @param aeropuertoInicio      El aeropuerto de inicio.
     * @param portaavionesFinal     El portaavión de destino.
     * @return La línea de arista generada.
     */
    //Si el inicio es aeropuerto y el destino es portaaviones
    public LineaArista generarLinea(Aeropuerto aeropuertoInicio, Portaaviones portaavionesFinal){
        //Linea

        //Empieza en
        setStartX(aeropuertoInicio.getBoundsInParent().getCenterX());
        setStartY(aeropuertoInicio.getY());

        //Linea de salida del portaavion
        aeropuertoInicio.setListaLineasSalida(this);

        //Termina en
        setEndX(portaavionesFinal.getBoundsInParent().getCenterX());
        setEndY(portaavionesFinal.getY() + portaavionesFinal.getHeight());

        //Linea de llegada del portaavion
        portaavionesFinal.setLinea_llegada(this);

        setStroke(Color.BLACK);

        //Distancia entre el punto de inicio y llegada
        double distancia = Math.sqrt(Math.pow(getEndX() - getStartX(),2) + Math.pow(getEndY() - getStartY(),2));

        System.out.println(distancia);

        //Calcula el peso de la ruta
        calcularPesoRuta((int) distancia,false,true);

        System.out.println(pesoRuta);

        System.out.println("-----------");

        return this;

    }
    /**
     * Genera una línea de arista entre un portaavión y un aeropuerto.
     *
     * @param portaavionesInicio    El portaavión de inicio.
     * @param aeropuertoFinal       El aeropuerto de destino.
     * @return La línea de arista generada.
     */
    //Si el inicio es portaaviones y el destino es aeropuerto
    public LineaArista generarLinea(Portaaviones portaavionesInicio, Aeropuerto aeropuertoFinal){
        //Linea

        //Empieza en
        setStartX(portaavionesInicio.getBoundsInParent().getCenterX());
        setStartY(portaavionesInicio.getY());

        //Linea de salida del portaavion
        portaavionesInicio.setLinea_salida(this);

        //Termina en
        setEndX(aeropuertoFinal.getBoundsInParent().getCenterX());
        setEndY(aeropuertoFinal.getY() + aeropuertoFinal.getHeight());

        //Linea de llegada del portaavion
        aeropuertoFinal.setListaLineasLlegada(this);

        setStroke(Color.BLACK);

        //Distancia entre el punto de inicio y llegada
        double distancia = Math.sqrt(Math.pow(getEndX() - getStartX(),2) + Math.pow(getEndY() - getStartY(),2));

        System.out.println(distancia);

        //Calcula el peso de la ruta
        calcularPesoRuta((int) distancia,true,false);

        System.out.println(pesoRuta);

        System.out.println("-----------");

        return this;

    }
    /**
     * Calcula el peso de la ruta en función de la distancia y los tipos de nodos.
     *
     * @param distancia      La distancia entre el punto de inicio y llegada.
     * @param portaavion1    Indica si el primer nodo es un portaavión.
     * @param portaavion2    Indica si el segundo nodo es un portaavión.
     */
    //Calcular peso de la ruta
    private void calcularPesoRuta(int distancia,boolean portaavion1,boolean portaavion2){

        //Preguntar si son 2 portaaviones
        //Condicion para que se considere una ruta interoceánica
        int distanciaXMar = 600;
        if (portaavion1 && portaavion2){

            //Al ser portaavión es más caro aterrizar en él
            distancia *= pesoXPorta;

            //Al comunicarse por mar, aumenta el costo
            distancia *= pesoXMar;

            pesoRuta = distancia;

        }

        //Preguntar si son 2 aeropuertos
        else if (!portaavion1 && !portaavion2){

            //Pregunta si se comunican de forma interoceánica
            if (distancia > distanciaXMar){

                distancia *= pesoXMar;

                pesoRuta = distancia;

            }

            //Si no es interoceánica entonces la ruta vale lo mismo que distancia
            else {

                pesoRuta = distancia;

            }
        }

        //Preguntar si es un Aeropuerto y Portaavion
        else if (!portaavion1 && portaavion2){

            //Pregunta si la ruta es intercontinental
            if (distancia > distanciaXMar){

                //Al ser intercontinental tiene más peso
                distancia *= pesoXMar;

                //Al ser Aeropuerto y Portaavion tiene un peso adicional la ruta
                distancia *= pesoXPorta;

                pesoRuta = distancia;

            }else {

                //Costo de ir de Aeropuerto a portaavion
                distancia *= pesoXPorta;

                pesoRuta = distancia;

            }

        }

        //Al final, solo queda Portaavion y Aeropuerto
        else {

            //Pregunta si la ruta es intercontinental
            if (distancia > distanciaXMar){

                //Al ser intercontinental tiene más peso
                distancia *= pesoXMar;

                //Al ser Aeropuerto y Portaavion tiene un peso adicional la ruta
                distancia *= pesoXPorta;

                pesoRuta = distancia;

            }else {

                //Costo de ir de Aeropuerto a portaavion
                distancia *= pesoXPorta;

                pesoRuta = distancia;

            }


        }

        //Dividir el resultado para no tener un número tan grande
        pesoRuta /= 10;



    }
    /**
     * Obtiene el ángulo de la línea de arista.
     *
     * @return El ángulo de la línea de arista en grados.
     */
    public double obtenerAngulo(){

        //Diferencias entre coordenadas
        double dx = getEndX() - getStartX();
        double dy = getEndY() - getStartY();

        // Calcula el ángulo en radianes
        double angleRadians = Math.atan2(dy, dx);


        //Devuelve los radianes convertidos a grados
        return Math.toDegrees(angleRadians);

    }


}
