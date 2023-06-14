package Servidor;

import Clases_auxiliares.Aeropuerto;
import Clases_auxiliares.Avion;
import Clases_auxiliares.Mensaje;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    private ServerSocket serverSocket;

    private Stage stage;
    private TabPane tabPane = new TabPane();
    private Pane panePorta,paneAero,paneAviones;

    private Tab tab1,tab2,tab3;
    private Button botonGenerarAvion;

    //Lista de aviones
    private ObservableList<String> listaAviones = FXCollections.observableArrayList();
    private ListView<String> tablaAviones = new ListView<>(listaAviones);

    final static int ancho = 500,alto = 800;

    public Servidor() throws IOException {

        this.serverSocket = new ServerSocket(localPort);

        Thread thread = new Thread(this);
        thread.start();

    }

    //Menu para seleccionar un objeto de la lista
    private ComboBox<Mensaje> instanciasAeropuerto;
    private ComboBox<Mensaje> instanciasPortaaviones;
    private ComboBox<Avion> instanciasAviones;

    //Labels para mostrar los atributos de los aviones
    private Label
            labelNombre,
            labelCombustible,
            labelEficiencia,
            labelVelocidad,
            labelRecargandoCombus,
            labelFortaleza;

    //Botones
    private Button algoritmo_Busqueda;
    private Button algoritmo_InsertionSort;
    private Button algoritmo_ShellSort;

    //Labels para mostrar la información
    private Label labelCapacidadHangar;

    @Override
    public void start(Stage primaryStage) {

        this.stage = primaryStage;

        Scene scene = new Scene(tabPane,ancho,alto);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();


        tab1();
        tab2();
        tab3();

    }

    private void tab1(){

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

            Mensaje mensaje = new Mensaje("crearAvionPorta");
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

        //Añadir el tab al tabpane
        tabPane.getTabs().add(tab1);

    }

    private void tab2(){

        //Tab de los aeropuertos
        tab2 = new Tab();
        tab2.setText("Aeropuertos");
        tab2.setClosable(false);

        //Índice del portaavión seleccionado
        int[] index = new int[1];
        
        instanciasAeropuerto = new ComboBox<>();
        instanciasAeropuerto.setPromptText("Aeropuertos");

        //Boton para spawnear los aviones
        botonGenerarAvion = new Button();
        botonGenerarAvion.setText("Spawn avión");
        botonGenerarAvion.setOnAction(e -> {

            Mensaje mensaje = new Mensaje("crearAvionAero");
            mensaje.setI(index[0]);

            enviarMensajeCliente(mensaje);

        });
        botonGenerarAvion.setTranslateX(ancho - 100);
        botonGenerarAvion.setTranslateY(15);

        instanciasAeropuerto.setOnAction(new EventHandler<ActionEvent>() {  //Detecta un evento al seleccionar un Item del ComboBox
            @Override
            public void handle(ActionEvent event) {
                int selectedItem = instanciasAeropuerto.getSelectionModel().getSelectedIndex();  //Se obtiene el índice del evento

                index[0] = selectedItem;

            }
        });
        
        
        
        
        
        //Crear un Pane para lo relacionado al aeropuerto
        paneAero = new Pane();

        //Agregar elementos al Pane
        paneAero.getChildren().addAll(
                instanciasAeropuerto,
                botonGenerarAvion
        );

        tab2.setContent(paneAero);

        //Objeto TabPane que almacenará todos los objetos de tipo Tab
        tabPane.getTabs().add(tab2);

    }

    private void tab3(){

        //Índice del portaavión seleccionado
        int[] index = new int[1];

        tab3 = new Tab();
        tab3.setText("Aviones");
        tab3.setClosable(false);

        //Botones
        algoritmo_Busqueda = new Button("Búsqueda");
        algoritmo_Busqueda.setTranslateX(ancho - 100);
        algoritmo_Busqueda.setTranslateY(30);
        algoritmo_Busqueda.setOnAction(e -> algoritmoOrdenamientoBusqueda());

        algoritmo_InsertionSort = new Button("Insertion Sort");
        algoritmo_InsertionSort.setTranslateX(ancho - 100);
        algoritmo_InsertionSort.setTranslateY(60);
        algoritmo_InsertionSort.setOnAction(e -> insertionSort());

        algoritmo_ShellSort = new Button("Shell Sort");
        algoritmo_ShellSort.setTranslateX(ancho - 100);
        algoritmo_ShellSort.setTranslateY(90);
        algoritmo_ShellSort.setOnAction(e -> shellSort());

        //Labels
        labelNombre = new Label("Nombre: ");
        labelNombre.setTranslateX(20);
        labelNombre.setTranslateY(50);

        labelCombustible = new Label("Combustible: ");
        labelCombustible.setTranslateX(20);
        labelCombustible.setTranslateY(100);

        labelEficiencia = new Label("Eficiencia: ");
        labelEficiencia.setTranslateX(20);
        labelEficiencia.setTranslateY(150);

        labelFortaleza = new Label("Fortaleza: ");
        labelFortaleza.setTranslateX(20);
        labelFortaleza.setTranslateY(200);

        labelVelocidad = new Label("Velocidad: ");
        labelVelocidad.setTranslateX(20);
        labelVelocidad.setTranslateY(250);

        labelRecargandoCombus = new Label("Esta recargando: ");
        labelRecargandoCombus.setTranslateX(20);
        labelRecargandoCombus.setTranslateY(300);


        //Menu de todas las instancias de aviones
        instanciasAviones = new ComboBox<>();
        instanciasAviones.setPromptText("Aviones");


        instanciasAviones.setOnAction(new EventHandler<ActionEvent>() {  //Detecta un evento al seleccionar un Item del ComboBox
            @Override
            public void handle(ActionEvent event) {
                int selectedItem = instanciasAviones.getSelectionModel().getSelectedIndex();  //Se obtiene el índice del evento
                Avion avionSelected = instanciasAviones.getSelectionModel().getSelectedItem();

                index[0] = selectedItem;

                //Nombre
                labelNombre.setText("Nombre: " + avionSelected.getNombre());
                //Velocidad
                labelVelocidad.setText("Velocidad: " + avionSelected.getVelocidad());
                //Eficiencia
                labelEficiencia.setText("Eficiencia: " + avionSelected.getEficiencia());
                //Fortaleza
                labelFortaleza.setText("Fortaleza: " + avionSelected.getFortaleza());
                //Combustible
                labelCombustible.setText("Combustible: " + avionSelected.getCombustible());
                //Recargando
                labelRecargandoCombus.setText("Esta recargando: " + avionSelected.isRecargando_combustible());



            }
        });

        // Configurar la celda de fábrica personalizada
        instanciasAviones.setCellFactory(new Callback<ListView<Avion>, ListCell<Avion>>() {
            @Override
            public ListCell<Avion> call(ListView<Avion> param) {
                return new ListCell<Avion>() {
                    @Override
                    protected void updateItem(Avion item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getNombre());
                            //setTextFill(item.getColor());
                        }
                    }
                };
            }
        });

        //Combobox para ordenar los aviones actuales de forma según el algoritmo que se requiera
        tablaAviones = new ListView<>();
        tablaAviones.setMaxHeight(300);
        tablaAviones.setTranslateY(350);



        //Pane2
        paneAviones = new Pane();
        paneAviones.getChildren().addAll(
                instanciasAviones,
                tablaAviones,
                algoritmo_Busqueda,
                algoritmo_InsertionSort,
                algoritmo_ShellSort,
                labelCombustible,
                labelEficiencia,
                labelVelocidad,
                labelRecargandoCombus,
                labelNombre,
                labelFortaleza
        );



        tab3.setContent(paneAviones);

        tabPane.getTabs().add(tab3);


    }

    private void algoritmoOrdenamientoBusqueda(){

        //Se limpia la tabla de aviones
        tablaAviones.getItems().clear();

        //Se forma una con los nuevos elementos
        for (int i = 0; i < instanciasAviones.getItems().size();i++){
            listaAviones.set(i,instanciasAviones.getItems().get(i).getNombre());
        }

        boolean intercam = false;
        String  temp;


        for (int i = 0; i < listaAviones.size(); i++) {

            for (int j = 0; j < listaAviones.size() - i - 1; j++) {

                if (listaAviones.get(j).compareTo(listaAviones.get(j + 1)) > 0) {
                    // Intercambiar los elementos
                    temp = listaAviones.get(j);
                    listaAviones.set(j, listaAviones.get(j + 1));
                    listaAviones.set(j + 1, temp);
                    intercam = true;
                }

            }

            // Si no se realizó ningún intercambio en la pasada, el arreglo está ordenado y se puede salir del bucle
            if (!intercam) {
                break;
            }
        }

        //Agrega los elementos ordenados a la tabla
        for (int i = 0; i < listaAviones.size();i++){

            tablaAviones.getItems().add(listaAviones.get(i));

        }

    }

    //Ordena los aviones según su velocidad
    private void insertionSort() {

        ArrayList<Avion> list = new ArrayList<>(instanciasAviones.getItems());


        for (int i = 1; i < list.size(); i++) {
            Avion temp = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j).getVelocidad() > temp.getVelocidad()) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, temp);
        }

        //Se ordena el listview
        tablaAviones.getItems().clear();


        //Se forma una lista con los nuevos elementos
        for (int i = 0; i < list.size();i++){
            listaAviones.set(i,list.get(i).getNombre());
        }

        //Agrega los elementos ordenados a la tabla
        for (int i = 0; i < listaAviones.size();i++){

            tablaAviones.getItems().add(listaAviones.get(i));

        }
    }

    //Ordena los aviones según su eficiencia
    public void shellSort() {

        ArrayList<Avion> list = new ArrayList<>(instanciasAviones.getItems());


        int N = list.size();
        int h = 1;
        while (h < N / 3) {
            h = 3 * h + 1;
        }

        for (Avion lista: list){
            System.out.print(lista.getEficiencia());
        }

        while (h >= 1) {
            for (int i = h; i < N; i++) {
                Avion temp = list.get(i);
                int j = i;
                while (j >= h && list.get(j - h).getEficiencia() > temp.getEficiencia()) {
                    list.set(j, list.get(j - h));
                    j -= h;
                }
                list.set(j, temp);
            }
            h /= 3;
        }

        for (Avion lista: list){
            System.out.print(lista.getEficiencia());
        }

        //Se ordena el listview
        tablaAviones.getItems().clear();


        //Se forma una lista con los nuevos elementos
        for (int i = 0; i < list.size();i++){

            listaAviones.set(i, list.get(i).getNombre());

        }

        //Agrega los elementos ordenados a la tabla
        for (int i = 0; i < listaAviones.size();i++){

            tablaAviones.getItems().add(listaAviones.get(i));

        }


    }

    private void crearListaAviones(ArrayList<Mensaje> arrayList){

        System.out.println(arrayList.size());

        listaAviones.clear();

        //Revisa si la lista no tiene elementos
        if (arrayList.size() == 0){
            return;
        }
        Platform.runLater(() -> {
            //Se limpia el combobox
            instanciasAviones.getItems().clear();
        });

        for (int i = 0; i < arrayList.size();i++){

            Avion avion = new Avion(arrayList.get(i).getNombreAvion());

            avion.setRecargando_combustible(arrayList.get(i).isRecargandoCombustible());
            avion.setCombustible(arrayList.get(i).getCombustibleAvion());
            avion.setFortaleza(arrayList.get(i).getFortaleza());
            avion.setEficiencia(arrayList.get(i).getEficiencia());
            avion.setVelocidad(arrayList.get(i).getVelocidad());
            avion.setDistancia(arrayList.get(i).getDistancia());

            int finalX = i;
            int finalX1 = i;
            int finalX2 = i;

            listaAviones.add(avion.getNombre());

            Platform.runLater(() -> {
                instanciasAviones.getItems().add(avion);
            });

        }


    }

    private void enviarMensajeCliente(Mensaje mensaje){

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

    private void agregarItemsPortaaviones(ArrayList<Mensaje> arrayList){

        for (Mensaje mensaje : arrayList) {

            instanciasPortaaviones.getItems().add(mensaje);

        }

    }

    private void agregarItemsAeropuertos(ArrayList<Mensaje> arrayList){

        for (Mensaje mensaje : arrayList){
            instanciasAeropuerto.getItems().add(mensaje);
        }

    }

    @Override
    public void run(){

        try {

            while (true){

                //Acepta el socket entrante
                Socket socket = serverSocket.accept();
                //System.out.println("El cliente se ha conectado: " + socket);

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

                    case "listaAeropuertos" -> {
                        agregarItemsAeropuertos(mensaje.getListaMensajes());
                    }

                    case "listaAviones" -> {
                        crearListaAviones(mensaje.getListaMensajes());
                    }

                }

                socket.close();
                //System.out.println("Socket cerrado");


            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
