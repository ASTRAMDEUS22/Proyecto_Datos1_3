module Juego {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires opencv;
    requires com.fazecast.jSerialComm;

    opens Juego to javafx.fxml;
    exports Juego;
    opens Servidor to javafx.fxml;
    exports Servidor;
}