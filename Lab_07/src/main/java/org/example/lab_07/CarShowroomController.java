package org.example.lab_07;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.lab_07.core.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CarShowroomController {



    @FXML
    private TableView<Vehicle> vehicleTableView;

    @FXML
    private TableColumn<Vehicle, String> brandCol;

    @FXML
    private TableColumn<Vehicle, String> modelCol;

    @FXML
    private TableColumn<Vehicle, ItemCondition> conditionCol;

    @FXML
    private TableColumn<Vehicle, Double> priceCol;

    @FXML
    private TableColumn<Vehicle, Integer> yearCol;

    //@FXML
    //private TableColumn<Vehicle, Double> mileageCol;

    //@FXML
    //private TableColumn<Vehicle, Double> engineCol;

    @FXML
    private TableColumn<Vehicle, Integer> quantityCol;

    @FXML
    private ComboBox<CarShowroom> showroomComboBox;

    @FXML
    public ComboBox<ItemCondition> conditionComboBox;

    @FXML
    private TextField minPriceField;

    @FXML
    private TextField maxPriceField;

    @FXML
    private TextField vehicleNameField;

    @FXML
    private ImageView carImageView;

    @FXML
    private Button buyButton;

    @FXML
    private Label carNameLabel;

    @FXML
    private Button viewCartButton;

    @FXML
    private ImageView carGif;

    @FXML
    private ImageView ezImage;

    private ShoppingCart shoppingCart = new ShoppingCart();

    private final CarShowroomContainer showroomContainer = CarShowroomContainer.getInstance();


    @FXML
    private void initialize() {
        try {
            setupTableColumns();
            setupShowroomComboBox();
            setupConditionComboBox();
            setupPriceChange();
            setupNameFilter();
            setupRowTooltips();
            setupCarImage();
            setupBuyButton();
            //setupCarGif();
            //setupEZImage();


            filterVehicles(); // Wywołanie metody wyświetlającej wszystkie pojazdy

            System.out.println("Initialize completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupTableColumns() {
        // Inicjalizacja kolumn TableView
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        conditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("yearOfProduction"));
        //mileageCol.setCellValueFactory(new PropertyValueFactory<>("mileage"));
        //engineCol.setCellValueFactory(new PropertyValueFactory<>("engineCapacity"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    private void setupShowroomComboBox() {
        // Ustawienie danych i konwertera dla ComboBox
        showroomComboBox.setItems(FXCollections.observableArrayList(showroomContainer.getShowrooms()));

        showroomComboBox.getItems().add(0, new CarShowroom("All", 0));  // Dodanie "All" na początek listy

        showroomComboBox.setConverter(new StringConverter<CarShowroom>() {
            @Override
            public String toString(CarShowroom showroom) {
                return showroom != null ? showroom.getShowroomName() : null;
            }

            @Override
            public CarShowroom fromString(String string) {
                return showroomContainer.getShowroom(string);
            }
        });

        // Ustawienie słuchacza dla wyboru salonu w ComboBox
        showroomComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> filterVehicles());

        // Ustawienie domyślnie wybranej opcji "All"
        showroomComboBox.getSelectionModel().selectFirst();
    }

    public void refreshShowroomComboBox() {

        showroomComboBox.getItems().clear(); // Czyść obecne dane
        //showroomComboBox.getItems().add("All"); // Dodaj opcję "All"
        showroomContainer.getShowrooms().forEach(showroom ->
                showroomComboBox.getItems().add(showroom)); // Dodaj nowe salony
        showroomComboBox.getSelectionModel().selectFirst(); // Wybierz pierwszy element
    }

    private void setupConditionComboBox() {
        conditionComboBox.setItems(FXCollections.observableArrayList(ItemCondition.values()));
        conditionComboBox.getItems().add(0, null); // Dodajemy "null", który będzie traktowany jako "Any"

        conditionComboBox.setConverter(new StringConverter<ItemCondition>() {
            @Override
            public String toString(ItemCondition condition) {
                return condition == null ? "Any" : condition.toString();
            }

            @Override
            public ItemCondition fromString(String string) {
                return string.equals("Any") ? null : ItemCondition.valueOf(string);
            }
        });

        conditionComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            filterVehicles();
        });

        conditionComboBox.getSelectionModel().selectFirst();
    }

    private void setupPriceChange() {
        minPriceField.textProperty().addListener((observable, oldValue, newValue) -> filterVehicles());
        maxPriceField.textProperty().addListener((observable, oldValue, newValue) -> filterVehicles());
    }

    private void setupNameFilter() {
        vehicleNameField.textProperty().addListener((observable, oldValue, newValue) -> filterVehicles());
    }

    private void setupCarImage() {
        // Dodanie obsługi zdarzenia kliknięcia wiersza
        vehicleTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            handleRowClicked();
        });
    }

    private void setupCarGif() {
        String imagePath = "carImages/drift.gif";
        Image image =new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        carGif.setImage(image);
    }

    private void setupEZImage() {
        String imagePath = "crazy.gif";
        Image image =new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        ezImage.setImage(image);
    }

    private void setupBuyButton() {
        buyButton.setVisible(false);

        vehicleTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            // Sprawdź, czy został wybrany jakiś pojazd
            if (newVal != null) {
                // Jeśli tak, pokaż przycisk "Kup"
                buyButton.setVisible(true);
            } else {
                // Jeśli nie, ukryj przycisk "Kup"
                buyButton.setVisible(false);
            }
        });

        buyButton.setOnMouseClicked(this::handleBuyButtonClicked);
    }


    private void filterVehicles() {
        CarShowroom selectedShowroom = showroomComboBox.getValue();
        ItemCondition selectedCondition = conditionComboBox.getValue();
        Double minPrice = parseDouble(minPriceField.getText());  // Może być null, jeśli pole jest puste
        Double maxPrice = parseDouble(maxPriceField.getText());  // Może być null, jeśli pole jest puste
        String searchName = vehicleNameField.getText().trim().toLowerCase();

        List<Vehicle> filteredVehicles;
        if (selectedShowroom != null && "All".equals(selectedShowroom.getShowroomName())) {
            // Wybierz pojazdy ze wszystkich salonów
            filteredVehicles = showroomContainer.getShowrooms().stream()
                    .flatMap(showroom -> showroom.getVehicleList().stream())
                    .filter(vehicle -> selectedCondition == null || vehicle.getCondition() == selectedCondition)
                    .filter(vehicle -> (minPrice == null || vehicle.getPrice() >= minPrice))  // Sprawdza cenę minimalną, jeśli została podana
                    .filter(vehicle -> (maxPrice == null || vehicle.getPrice() <= maxPrice))  // Sprawdza cenę maksymalną, jeśli została podana
                    .filter(vehicle -> searchName.isEmpty() || (vehicle.getBrand() + " " + vehicle.getModel()).toLowerCase().contains(searchName))
                    .collect(Collectors.toList());
        } else {
            // Wybierz pojazdy tylko z wybranego salonu
            filteredVehicles = showroomContainer.getShowrooms().stream()
                    .filter(showroom -> showroom.equals(selectedShowroom))
                    .flatMap(showroom -> showroom.getVehicleList().stream())
                    .filter(vehicle -> selectedCondition == null || vehicle.getCondition() == selectedCondition)
                    .filter(vehicle -> (minPrice == null || vehicle.getPrice() >= minPrice))  // Sprawdza cenę minimalną, jeśli została podana
                    .filter(vehicle -> (maxPrice == null || vehicle.getPrice() <= maxPrice))  // Sprawdza cenę maksymalną, jeśli została podana
                    .filter(vehicle -> searchName.isEmpty() || (vehicle.getBrand() + " " + vehicle.getModel()).toLowerCase().contains(searchName))
                    .collect(Collectors.toList());
        }

        vehicleTableView.setItems(FXCollections.observableArrayList(filteredVehicles));
    }

    private Double parseDouble(String text) {
        if (text == null || text.trim().isEmpty()) {
            return null;  // Zwraca null, jeśli pole tekstowe jest puste
        }
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return null;  // Zwraca null, jeśli wartość nie jest poprawną liczbą
        }
    }

    private void setupRowTooltips() {
        vehicleTableView.setRowFactory(tv -> new TableRow<Vehicle>() {
            @Override
            protected void updateItem(Vehicle vehicle, boolean empty) {
                super.updateItem(vehicle, empty);
                if (empty || vehicle == null) {
                    setTooltip(null);
                } else {
                    Tooltip tooltip = new Tooltip();
                    tooltip.setText(createTooltipText(vehicle));
                    tooltip.setStyle("-fx-font-size: 12;");
                    setTooltip(tooltip);
                }
            }
        });
    }

    private String createTooltipText(Vehicle vehicle) {
        return "Showroom: " + vehicle.getShowroom().getShowroomName() +
                "\nMileage: " + vehicle.getMileage() + " km" +
                "\nEngine capacity: " + vehicle.getEngineCapacity() + " cc";
    }

    private void handleRowClicked() {
        // Pobierz wybrany samochód
        Vehicle selectedVehicle = vehicleTableView.getSelectionModel().getSelectedItem();
        if (selectedVehicle != null) {
            carImageView.setVisible(true);
            // Ustawienie obrazka samochodu w lewym dolnym rogu ekranu
            String imagePath = selectedVehicle.getImagePath(); // Zakładając, że masz ścieżkę do obrazka w modelu pojazdu
            if (imagePath != null && !imagePath.isEmpty()) {
                Image image =new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
                carImageView.setImage(image);
                carNameLabel.setText(selectedVehicle.getBrand() + " " + selectedVehicle.getModel());


            } else {
                carImageView.setImage(null);
                carNameLabel.setText("");
            }
        } else
        {
            carImageView.setImage(null);
            carImageView.setVisible(false);
            carNameLabel.setText("");
        }
    }

    private void handleBuyButtonClicked(MouseEvent event) {
        // Pobierz wybrany pojazd z tabeli
        Vehicle selectedVehicle = vehicleTableView.getSelectionModel().getSelectedItem();

        if (selectedVehicle != null) {

            // Logika zakupu samochodu
            shoppingCart.addVehicle(selectedVehicle);
            selectedVehicle.getShowroom().getProduct(selectedVehicle);

            // Aktualizacja tabeli po zakupie
            filterVehicles();
            vehicleTableView.refresh();

            System.out.println("Vehicle added to cart.");

            //for(CarShowroom showroom : showroomContainer.getShowrooms()) {
            //    showroom.summary();
            //}

        } else {
            // Obsługa, gdy nie wybrano żadnego pojazdu
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No selection");
            alert.setHeaderText("No Vehicle Selected");
            alert.setContentText("Please select a vehicle before buying.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleViewCartButtonClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Cart-view.fxml"));
            Parent root = loader.load();

            CartController cartController = loader.getController();
            cartController.setShoppingCart(shoppingCart);
            cartController.setCartItems(shoppingCart.getVehicles());
            cartController.setMainController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Shopping Cart");

            Image applicationIcon2 = new Image(getClass().getResourceAsStream("shop.jpg"));
            stage.getIcons().add(applicationIcon2);

            stage.setScene(new Scene(root));
            stage.setResizable(false);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addVehicleBackToList(Vehicle vehicle) {
        for(CarShowroom carShowroom : showroomContainer.getShowrooms()) {
            if(carShowroom.getShowroomName().equals(vehicle.getShowroom().getShowroomName())) {
                /*for(Vehicle v : vehicleTableView.getItems()) {
                    if (v.equals(vehicle)) {
                        v.quantity+=1;
                        vehicleTableView.refresh();
                        return;
                    }
                }
                vehicle.quantity=1;

                 */
                if(vehicle.getQuantity() == 0)
                    vehicle.setQuantity(1);
                carShowroom.addProduct(vehicle);
                //carShowroom.getVehicleList().add(vehicle);
            }
        }

        filterVehicles();
        vehicleTableView.refresh();
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
