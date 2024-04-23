package org.example.lab_06;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import org.example.lab_06.core.CarShowroom;
import org.example.lab_06.core.CarShowroomContainer;
import org.example.lab_06.core.ItemCondition;
import org.example.lab_06.core.Vehicle;

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

    private final CarShowroomContainer showroomContainer = new CarShowroomContainer();


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
            // Wyświetl okno dialogowe z potwierdzeniem
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Payment confirmation");

            ImageView icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon.png"))));
            confirmationAlert.setGraphic(icon);


            // Personalizuj nagłówek i treść alertu
            confirmationAlert.setHeaderText("Are you sure you want to buy this car?");
            confirmationAlert.setContentText("Car price is: " + selectedVehicle.getPrice() + " zł");

            // Tutaj konfigurujesz DialogPane
            DialogPane dialogPane = confirmationAlert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("style.css").toExternalForm()
            );
            // Tutaj możesz także dodać dodatkowe klasy CSS do DialogPane, jeśli potrzebujesz
            dialogPane.getStyleClass().add("my-dialog");

            // Ustaw przyciski "Yes" i "No"
            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
            confirmationAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            // Czekaj na odpowiedź użytkownika
            confirmationAlert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    // Logika zakupu samochodu
                    selectedVehicle.getShowroom().getProduct(selectedVehicle);

                    // Aktualizacja tabeli po zakupie
                    filterVehicles();
                    vehicleTableView.refresh();
                    System.out.println("Purchase completed successfully.");
                } else if (buttonType == buttonTypeNo) {
                    // Użytkownik kliknął "No", nie rób nic
                    System.out.println("Purchase cancelled.");
                }
            });
        } else {
            // Obsługa, gdy nie wybrano żadnego pojazdu
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No selection");
            alert.setHeaderText("No Vehicle Selected");
            alert.setContentText("Please select a vehicle before buying.");
            alert.showAndWait();
        }
    }

}
