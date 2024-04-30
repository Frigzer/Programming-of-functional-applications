module org.example.lab_07 {
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
    requires javafx.media;

    opens org.example.lab_07 to javafx.fxml;
    opens org.example.lab_07.core to javafx.fxml, javafx.base;
    opens org.example.lab_07.carImages to javafx.fxml, javafx.base, javafx.graphics, javafx.media, javafx.scene;

    exports org.example.lab_07;
}