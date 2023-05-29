package Clases_auxiliares;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class LineaArista extends Line{

    public LineaArista(){

        setOpacity(0.7);

    }

    //Si son dos portaaviones
    public void generarLinea(Portaaviones portaavionInicio, Portaaviones portaavioneFinal){
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

        //distancia /= 7;

        distancia = (int) distancia;

        //System.out.println(distancia);


    }

    //Si son dos aeropuertos
    public void generarLinea(Aeropuerto aeropuertoInicio, Aeropuerto aeropuertoFinal){
        //Linea

        //Empieza en
        setStartX(aeropuertoInicio.getBoundsInParent().getCenterX());
        setStartY(aeropuertoInicio.getY());

        //Linea de salida del portaavion
        aeropuertoInicio.setLinea_salida(this);

        //Termina en
        setEndX(aeropuertoFinal.getBoundsInParent().getCenterX());
        setEndY(aeropuertoFinal.getY() + aeropuertoFinal.getHeight());

        //Linea de llegada del portaavion
        aeropuertoFinal.setLinea_llegada(this);

        setStyle("-fx-stroke: #b751d5");
        //setStroke(Color.BLACK);

        //Distancia entre el punto de inicio y llegada
        double distancia = Math.sqrt(Math.pow(getEndX() - getStartX(),2) + Math.pow(getEndY() - getStartY(),2));

        //distancia /= 7;

        distancia = (int) distancia;

        //System.out.println(distancia);


    }

    //Si el inicio es aeropuerto y el destino es portaaviones
    public void generarLinea(Aeropuerto aeropuertoInicio, Portaaviones portaavionesFinal){
        //Linea

        //Empieza en
        setStartX(aeropuertoInicio.getBoundsInParent().getCenterX());
        setStartY(aeropuertoInicio.getY());

        //Linea de salida del portaavion
        aeropuertoInicio.setLinea_salida(this);

        //Termina en
        setEndX(portaavionesFinal.getBoundsInParent().getCenterX());
        setEndY(portaavionesFinal.getY() + portaavionesFinal.getHeight());

        //Linea de llegada del portaavion
        portaavionesFinal.setLinea_llegada(this);

        setStroke(Color.BLACK);

        //Distancia entre el punto de inicio y llegada
        double distancia = Math.sqrt(Math.pow(getEndX() - getStartX(),2) + Math.pow(getEndY() - getStartY(),2));

        //distancia /= 7;

        distancia = (int) distancia;

        //System.out.println(distancia);


    }

    //Si el inicio es portaaviones y el destino es aeropuerto
    public void generarLinea(Portaaviones portaavionesInicio, Aeropuerto aeropuertoFinal){
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
        aeropuertoFinal.setLinea_llegada(this);

        setStroke(Color.BLACK);

        //Distancia entre el punto de inicio y llegada
        double distancia = Math.sqrt(Math.pow(getEndX() - getStartX(),2) + Math.pow(getEndY() - getStartY(),2));

        //distancia /= 7;

        distancia = (int) distancia;

        //System.out.println(distancia);


    }




}
