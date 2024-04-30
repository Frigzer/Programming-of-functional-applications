package org.example.lab_07;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.lab_07.core.CarShowroomContainer;
import org.example.lab_07.core.ShoppingCart;
import org.example.lab_07.core.Vehicle;
import org.example.lab_07.core.CarShowroom;

import java.io.*;
import java.util.Objects;

public class CartController {
    private static CartController instance;

    public static synchronized CartController getInstance() {
        if (instance == null) {
            instance = new CartController();
        }
        return instance;
    }

    @FXML
    private TableColumn cartBrandCol;

    @FXML
    private TableColumn cartModelCol;

    @FXML
    private TableColumn cartPriceCol;

    @FXML
    private TableColumn cartConditionCol;

    @FXML
    private TableColumn cartYearCol;

    @FXML
    private TableColumn cartMileageCol;

    @FXML
    private TableColumn cartEngineCol;

    @FXML
    private TableColumn cartShowroomCol;

    @FXML
    private TableColumn cartQuantityCol;

    @FXML
    private TableView<Vehicle> cartTableView;

    @FXML
    private Button checkoutButton;

    @FXML
    private TableColumn removeButton;

    private CarShowroomController mainController;

    private ShoppingCart shoppingCart = new ShoppingCart();


    public void initialize() {
        setupTableColumns();
        setupRemoveButton();
    }

    private void setupTableColumns() {
        cartBrandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        cartModelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        cartConditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
        cartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        cartYearCol.setCellValueFactory(new PropertyValueFactory<>("yearOfProduction"));
        cartMileageCol.setCellValueFactory(new PropertyValueFactory<>("mileage"));
        cartEngineCol.setCellValueFactory(new PropertyValueFactory<>("engineCapacity"));
        //cartShowroomCol.setCellValueFactory(new PropertyValueFactory<>("showroom"));
        //cartQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    private void setupRemoveButton() {
        TableColumn<Vehicle, Void> removeCol = new TableColumn<>("Remove");
        removeCol.setCellFactory(col -> new TableCell<Vehicle, Void>() {
            private final Button removeButton = new Button("Remove");

            {
                removeButton.getStyleClass().add("button-remove");
                removeButton.setOnAction(event -> {
                    Vehicle vehicle = getTableView().getItems().get(getIndex());
                    removeVehicleFromCart(vehicle);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(removeButton);
                }
            }
        });
        cartTableView.getColumns().add(removeCol);


    }

    public void setMainController(CarShowroomController mainController) {
        this.mainController = mainController;
    }

    private void removeVehicleFromCart(Vehicle vehicle) {
        cartTableView.getItems().remove(vehicle);
        // Dodaj pojazd z powrotem do głównej listy pojazdów (implementacja zależy od struktury aplikacji)
        mainController.addVehicleBackToList(vehicle);
    }


    public void setCartItems(ObservableList<Vehicle> items) {
        cartTableView.setItems(items);
    }

    @FXML
    private void checkoutButtonAction(ActionEvent event) {

        if (!cartTableView.getItems().isEmpty()) {
            // Wyświetl okno dialogowe z potwierdzeniem
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Payment confirmation");

            ImageView icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon.png"))));
            confirmationAlert.setGraphic(icon);

            double totalPrice = cartTableView.getItems().stream().mapToDouble(Vehicle::getPrice).sum();

            // Personalizuj nagłówek i treść alertu
            confirmationAlert.setHeaderText("Are you sure you want to buy these cars?");
            confirmationAlert.setContentText("Cars price is: " + totalPrice + " zł");

            // Tutaj konfigurujesz DialogPane
            DialogPane dialogPane = confirmationAlert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("style.css").toExternalForm()
            );

            dialogPane.getStyleClass().add("my-dialog");

            // Ustaw przyciski "Yes" i "No"
            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
            confirmationAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            confirmationAlert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    // Logika zakupu samochodu
                    cartTableView.getItems().clear();
                    cartTableView.getItems().clear();

                    // Aktualizacja tabeli po zakupie
                    cartTableView.refresh();

                    System.out.println("Successful transaction");
                } else if (buttonType == buttonTypeNo) {
                    // Użytkownik kliknął "No", nie rób nic
                    System.out.println("Purchase cancelled.");
                }
            });


        } else {
            // Obsługa, gdy nie wybrano żadnego pojazdu
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty cart list");
            alert.setHeaderText("No vehicles in cart");
            alert.setContentText("Please add a vehicle to cart before buying.");
            alert.showAndWait();
        }
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        cartTableView.setItems(shoppingCart.getVehicles());  // Zaktualizuj tabelę z nowymi danymi
    }

}
