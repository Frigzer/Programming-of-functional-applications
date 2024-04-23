package org.example.lab_06;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class CarShowroomApp  extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CarShowroomApp .class.getResource("CarShowroom-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image applicationIcon = new Image(getClass().getResourceAsStream("title.jpg"));
        stage.getIcons().add(applicationIcon);
        stage.setTitle("Best Cars!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}