package org.example.lab_07;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.example.lab_07.core.CarShowroomContainer;
import org.example.lab_07.core.ShoppingCart;
import org.example.lab_07.core.Vehicle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class CarShowroomApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        loadInitialData();

        setupStage(stage);

        URL resource = getClass().getResource("music.mp3");
        Media media = new Media(resource.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // Rozpocznij odtwarzanie muzyki

        // mediaPlayer.play(); // Muzyka - wystarczy odkomentować
        mediaPlayer.setVolume(0.3);

        stage.show();
    }

    private ShoppingCart loadShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        File file = new File("cart.ser");
        if (file.exists()) {
            try {
                shoppingCart.loadCart(file);
                System.out.println("Cart loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Failed to load shopping cart: " + e.getMessage());
            }
        }
        return shoppingCart;
    }

    private void setupStage(Stage stage)  throws IOException {
        ShoppingCart shoppingCart = loadShoppingCart();

        FXMLLoader fxmlLoader = new FXMLLoader(CarShowroomApp .class.getResource("CarShowroom-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image applicationIcon = new Image(getClass().getResourceAsStream("title.jpg"));
        stage.getIcons().add(applicationIcon);
        stage.setTitle("VroomVista!");
        stage.setScene(scene);
        stage.setResizable(false);

        CarShowroomController controller = fxmlLoader.getController();

        controller.setShoppingCart(shoppingCart);

        stage.setOnCloseRequest(event -> {
            event.consume();
            askToSaveBeforeExiting(shoppingCart);
        });
    }

    private void loadInitialData() {
        try {
            List<Vehicle> vehicles = CSVImporter.importFromCSV("cars.csv");

            System.out.println("Imported " + vehicles.size() + " vehicles.");
            if(vehicles.isEmpty()) {
                System.out.println("No vehicles were imported.\nImporting from backup file.");
                vehicles = CSVImporter.importFromCSV("carBackup.csv");
            }
        } catch (IOException e) {
            System.out.println("Cannot load CSV file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }

    private void askToSaveBeforeExiting(ShoppingCart shoppingCart) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("EXIT Confirmation");
        ImageView icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("warning.png"))));
        alert.setGraphic(icon);
        alert.setHeaderText("Do you want to save changes?");
        alert.setContentText("Select Option:");


        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("style.css").toExternalForm()
        );

        dialogPane.getStyleClass().add("my-dialog");

        if (alert.showAndWait().get() == ButtonType.OK) {
            try {

                CSVExporter.export(CarShowroomContainer.getInstance().getShowrooms(), "cars.csv");


                File file = new File( "cart.ser");
                shoppingCart.saveCart(file);
                System.out.println("Cart saved successfully.");

            } catch (IOException e) {
                System.out.println("We cannot save data: " + e.getMessage());

            }
            Platform.exit();
        } else {
            Platform.exit();
        }
    }


}