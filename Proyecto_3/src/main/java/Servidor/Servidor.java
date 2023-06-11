package Servidor;

import Clases_auxiliares.Aeropuerto;
import Clases_auxiliares.Mensaje;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor extends Application implements Runnable{

    final static int localPort = 2222,serverPort = 4444;
    final static String host = "localhost";

    //Socket servidor
    ServerSocket serverSocket;

    Stage stage;
    TabPane tabPane = new TabPane();
    Pane panePorta,paneAero;

    Tab tab1,tab2;
    Button botonGenerarAvion;

    final static int ancho = 500,alto = 800;

    public Servidor() throws IOException {

        this.serverSocket = new ServerSocket(localPort);

        Thread thread = new Thread(this);
        thread.start();

    }

    //Menu para seleccionar un objeto de la lista
    ComboBox<Aeropuerto> instanciasAeropuerto;
    ComboBox<Mensaje> instanciasPortaaviones;

    //Labels para mostrar la información
    Label labelCapacidadHangar;

    @Override
    public void start(Stage primaryStage) {

        this.stage = primaryStage;

        Scene scene = new Scene(tabPane,ancho,alto);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();


        tab1();
        tab2();

    }

    public void tab1(){

        //Tab del portaaviones
        tab1 = new Tab();
        tab1.setText("Portaaviones");
        tab1.setClosable(false);

        //Índice del portaavión seleccionado
        int[] index = new int[1];

        //Menu de todas las instancias de aviones
        instanciasPortaaviones = new ComboBox<>();
        instanciasPortaaviones.setPromptText("Portaaviones");

        //Boton para spawnear los aviones
        botonGenerarAvion = new Button();
        botonGenerarAvion.setText("Spawn avión");
        botonGenerarAvion.setOnAction(e -> {

            Mensaje mensaje = new Mensaje("crearAvion");
            mensaje.setI(index[0]);

            enviarMensajeCliente(mensaje);

        });
        botonGenerarAvion.setTranslateX(ancho - 100);
        botonGenerarAvion.setTranslateY(15);

        instanciasPortaaviones.setOnAction(new EventHandler<ActionEvent>() {  //Detecta un evento al seleccionar un Item del ComboBox
            @Override
            public void handle(ActionEvent event) {
                int selectedItem = instanciasPortaaviones.getSelectionModel().getSelectedIndex();  //Se obtiene el índice del evento

                index[0] = selectedItem;

            }
        });

        //Crear un Pane para lo relacionado al portaaviones
        panePorta = new Pane();

        //Agrega elementos al Pane
        panePorta.getChildren().addAll(
                instanciasPortaaviones,
                botonGenerarAvion);

        //Tab que contendrá lo relacionado con los portaaviones
        tab1.setContent(panePorta);

    }

    public void tab2(){

        //Tab de los aeropuertos
        tab2 = new Tab();
        tab2.setText("Aeropuertos");
        tab2.setClosable(false);

        //Crear un Pane para lo relacionado al aeropuerto
        paneAero = new Pane();


        //Objeto TabPane que almacenará todos los objetos de tipo Tab
        tabPane.getTabs().addAll(tab1,tab2);

    }

    public void enviarMensajeCliente(Mensaje mensaje){

        try {
            //Crea un socket para comunicarse con el server
            Socket socket = new Socket(host,serverPort);

            //Crear medio para enviar información
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject(mensaje);

            socket.close();
            out.close();


        }catch (Exception e){

            e.printStackTrace();

        }

    }

    public void agregarItemsPortaaviones(ArrayList<Mensaje> arrayList){

        for (Mensaje mensaje : arrayList) {

            instanciasPortaaviones.getItems().add(mensaje);

        }

    }

    @Override
    public void run(){

        try {

            while (true){

                //Acepta el socket entrante
                Socket socket = serverSocket.accept();
                System.out.println("El cliente se ha conectado: " + socket);

                //Recibe el objeto enviado por el socket
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                Mensaje mensaje = (Mensaje) in.readObject();

                switch (mensaje.getAccion()) {

                    case "Hola" -> {

                        System.out.println("Hola cliente");


                    }

                    case "listaPortaaviones" -> {
                        agregarItemsPortaaviones(mensaje.getListaMensajes());

                    }

                }

                socket.close();
                System.out.println("Socket cerrado");


            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
