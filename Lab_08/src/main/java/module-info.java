module org.example.lab_08 {
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
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.persistence;
    requires java.sql;
    requires com.h2database;

    opens org.example.lab_08 to javafx.fxml, java.naming, javafx.graphics, org.hibernate.orm.core;
    opens org.example.lab_08.core to javafx.fxml, javafx.base, java.naming, javafx.graphics, org.hibernate.orm.core;
    opens org.example.lab_08.carImages to javafx.fxml, javafx.base, javafx.graphics, javafx.media, javafx.scene;

    exports org.example.lab_08;
}