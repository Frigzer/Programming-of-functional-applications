package org.example.lab_08;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.lab_08.core.*;
import org.hibernate.Session;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CarShowroomController {

    @FXML
    private TableView<Object[]> vehicleTableView;

    @FXML
    private TableColumn<Object[], Long> idCol;

    @FXML
    private TableColumn<Object[], String> brandCol;

    @FXML
    private TableColumn<Object[], String> modelCol;

    @FXML
    private TableColumn<Object[], ItemCondition> conditionCol;

    @FXML
    private TableColumn<Object[], Double> priceCol;

    @FXML
    private TableColumn<Object[], Integer> yearCol;

    //@FXML
    //private TableColumn<Vehicle, Double> mileageCol;

    //@FXML
    //private TableColumn<Vehicle, Double> engineCol;

    @FXML
    private TableColumn<Object[], Integer> quantityCol;

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
    private Button rateButton;

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

    private final CarShowroomDAO showroomDAO;

    @FXML
    private TableView<Object[]> summaryTableView;

    @FXML
    private TableColumn<Object[], String> showroomColumn;

    @FXML
    private TableColumn<Object[], Long> countColumn;

    private VehicleDAO vehicleDAO;

    @FXML
    private TableColumn<Object[], Integer> ratingCountCol;

    @FXML
    private TableColumn<Object[], Double> averageRatingCol;



    public CarShowroomController() {
        this.showroomDAO = new CarShowroomDAO(HibernateUtil.getSessionFactory());
    }


    @FXML
    private void initialize() {
        try {
            vehicleDAO = new VehicleDAO(HibernateUtil.getSessionFactory().openSession());
            setupTableColumns();
            loadData();
            setupShowroomComboBox();
            setupConditionComboBox();
            setupPriceChange();
            setupNameFilter();
            setupRowTooltips();
            setupCarImage();
            setupBuyAndRateButtons();
            //setupCarGif();
            //setupEZImage();


            filterVehicles(); // Wywołanie metody wyświetlającej wszystkie pojazdy

            System.out.println("Initialize completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupTableColumns() {
        showroomColumn.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[0]));

        countColumn.setCellValueFactory(cellData -> {
            Number count = (Number) cellData.getValue()[1];
            return new SimpleLongProperty(count != null ? count.longValue() : 0).asObject();
        });


        /*
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        conditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("yearOfProduction"));
        //mileageCol.setCellValueFactory(new PropertyValueFactory<>("mileage"));
        //engineCol.setCellValueFactory(new PropertyValueFactory<>("engineCapacity"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

         */



        idCol.setCellValueFactory(cellData -> new SimpleLongProperty((Long) cellData.getValue()[0]).asObject());
        idCol.setVisible(false);

        brandCol.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[1]));

        modelCol.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[2]));
        conditionCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>((ItemCondition) cellData.getValue()[3]));
        priceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty((Double) cellData.getValue()[4]).asObject());
        yearCol.setCellValueFactory(cellData -> new SimpleIntegerProperty((Integer) cellData.getValue()[5]).asObject());
        quantityCol.setCellValueFactory(cellData -> new SimpleIntegerProperty((Integer) cellData.getValue()[8]).asObject());

        ratingCountCol.setCellValueFactory(cellData -> {
            Object value = cellData.getValue()[10];
            return new SimpleIntegerProperty(value != null ? ((Number) value).intValue() : 0).asObject();
        });

        averageRatingCol.setCellValueFactory(cellData -> {
            Object value = cellData.getValue()[11];
            return new SimpleDoubleProperty(value != null ? ((Number) value).doubleValue() : 0.0).asObject();
        });

    }

    public void loadData() {
        List<Object[]> results = vehicleDAO.countVehiclesByShowroom();
        summaryTableView.setItems(FXCollections.observableArrayList(results));

        List<Object[]> vehicles = vehicleDAO.getVehicleRatings();
        vehicleTableView.setItems(FXCollections.observableArrayList(vehicles));
    }

    private void setupShowroomComboBox() {
        // Ustawienie danych i konwertera dla ComboBox
        List<CarShowroom> showrooms = showroomDAO.findAll();
        showroomComboBox.setItems(FXCollections.observableArrayList(showrooms));

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

    private void setupBuyAndRateButtons() {
        buyButton.setVisible(false);
        rateButton.setVisible(false);

        vehicleTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            // Sprawdź, czy został wybrany jakiś pojazd
            if (newVal != null) {
                // Jeśli tak, pokaż przycisk "Kup"
                buyButton.setVisible(true);
                rateButton.setVisible(true);
            } else {
                // Jeśli nie, ukryj przycisk "Kup"
                buyButton.setVisible(false);
                rateButton.setVisible(false);
            }
        });

        buyButton.setOnMouseClicked(this::handleBuyButtonClicked);
        rateButton.setOnMouseClicked(this::handleRateButtonClicked);
    }

    private void filterVehicles() {
        CarShowroom selectedShowroom = showroomComboBox.getValue();
        ItemCondition selectedCondition = conditionComboBox.getValue();
        Double minPrice = parseDouble(minPriceField.getText());
        Double maxPrice = parseDouble(maxPriceField.getText());
        String searchName = vehicleNameField.getText().trim().toLowerCase();

        List<Object[]> filteredVehicles = showroomDAO.findVehicles(selectedShowroom, selectedCondition, minPrice, maxPrice, searchName);
        vehicleTableView.setItems(FXCollections.observableArrayList(filteredVehicles));
    }


    public void refreshSummaryTable() {
        summaryTableView.getItems().clear();
        List<Object[]> results = vehicleDAO.countVehiclesByShowroom();
        summaryTableView.setItems(FXCollections.observableArrayList(results));
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
        vehicleTableView.setRowFactory(tv -> new TableRow<Object[]>() {
            @Override
            protected void updateItem(Object[] item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setTooltip(null);
                } else {
                    Tooltip tooltip = new Tooltip();
                    tooltip.setText(createTooltipText(item));
                    tooltip.setStyle("-fx-font-size: 12;");
                    setTooltip(tooltip);
                }
            }
        });
    }

    private String createTooltipText(Object[] item) {
        // Zakładając, że odpowiednie dane są w konkretnych indeksach
        String showroom = item[9] != null ? item[9].toString() : "N/A";
        String mileage = item[6] != null ? item[6].toString() : "N/A"; // Przykład
        String engineCapacity = item[7] != null ? item[7].toString() : "N/A"; // Przykład
        return "Showroom: " + showroom + "\nMileage: " + mileage + " km\nEngine capacity: " + engineCapacity + " cc";
    }

    private void handleRowClicked() {
        // Pobierz wybrany rząd jako tablicę obiektów
        Object[] rowData = vehicleTableView.getSelectionModel().getSelectedItem();
        if (rowData != null) {
            carImageView.setVisible(true);
            String brand = (String) rowData[1];
            String model = (String) rowData[2];
            // Ustawienie obrazka samochodu w lewym dolnym rogu ekranu
            updateVehicleImage(brand, model);
            carNameLabel.setText(brand + " " + model);
        } else {
            carImageView.setImage(null);
            carImageView.setVisible(false);
            carNameLabel.setText("");
        }
    }

    private void updateVehicleImage(String brand, String model) {
        String imagePath = "carImages/" + brand.toLowerCase() + "_" + model.toLowerCase() + ".jpg";
        try {
            InputStream is = getClass().getResourceAsStream(imagePath);
            if (is != null) {
                Image image = new Image(is);
                carImageView.setImage(image);
            } else {
                throw new FileNotFoundException("Image file not found: " + imagePath);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            carImageView.setImage(null);
            carNameLabel.setText("No image available");
        }
    }

    private void handleBuyButtonClicked(MouseEvent event) {
        Object[] selectedRow = vehicleTableView.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            Long vehicleId = (Long) selectedRow[0]; // Zakładam, że ID pojazdu znajduje się w pierwszej kolumnie.
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                session.beginTransaction();

                Vehicle vehicle = session.get(Vehicle.class, vehicleId);
                if (vehicle != null) {
                    if (vehicle.getQuantity() > 0) {
                        vehicle.decreaseQuantity();
                        session.update(vehicle);

                        // Dodaj pojazd do koszyka - logika aplikacji
                        shoppingCart.addVehicle(vehicle);

                        session.getTransaction().commit();

                        // Odświeżanie interfejsu użytkownika
                        filterVehicles();
                        refreshSummaryTable();
                        vehicleTableView.refresh();
                        summaryTableView.refresh();

                        System.out.println("Vehicle added to cart.");
                    } else {
                        // Jeśli quantity wynosi 0, wyświetl alert.
                        Alert alert = new Alert(Alert.AlertType.WARNING);

                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.getStylesheets().add(
                                getClass().getResource("style.css").toExternalForm()
                        );

                        dialogPane.getStyleClass().add("my-dialog");

                        alert.setTitle("Out of Stock");
                        alert.setHeaderText("Vehicle Not Available");
                        alert.setContentText("This vehicle is out of stock.");
                        alert.showAndWait();
                    }
                } else {
                    System.out.println("Vehicle not found.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Purchase Failed");
                alert.setContentText("An error occurred while processing your purchase.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Vehicle Selected");
            alert.setContentText("Please select a vehicle before buying.");
            alert.showAndWait();
        }
    }

    private void handleRateButtonClicked(MouseEvent event) {
        Object[] selectedRow = vehicleTableView.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            Long vehicleId = (Long) selectedRow[0];
            showRatingDialog(vehicleId);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Vehicle Selected");
            alert.setHeaderText("Select a Vehicle");
            alert.setContentText("Please select a vehicle to rate.");
            alert.showAndWait();
        }
    }

    private void showRatingDialog(Long vehicleId) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Rate Vehicle");
        dialog.setHeaderText("Enter your rating (0-5):");
        dialog.setContentText("Rating:");

        ImageView icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("ez2.png"))));
        dialog.setGraphic(icon);

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("style.css").toExternalForm()
        );

        dialogPane.getStyleClass().add("my-dialog");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(rating -> {
            try {
                double rateValue = Double.parseDouble(rating);
                if (rateValue < 0 || rateValue > 5) throw new IllegalArgumentException();
                saveRating(vehicleId, rateValue);
            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                DialogPane alertPane = alert.getDialogPane();
                alertPane.getStylesheets().add(
                        getClass().getResource("style.css").toExternalForm()
                );

                alertPane.getStyleClass().add("my-dialog");

                alert.setTitle("Invalid Rating");
                alert.setHeaderText("Error in rating input");
                alert.setContentText("Please enter a valid rating between 0 and 5.");
                alert.showAndWait();
            }
        });
    }

    private void saveRating(Long vehicleId, double rating) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Vehicle vehicle = session.get(Vehicle.class, vehicleId);
            if (vehicle != null) {
                Rating newRating = new Rating(vehicle, rating);
                session.save(newRating);
                session.getTransaction().commit();

                filterVehicles();
                vehicleTableView.refresh();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                        getClass().getResource("style.css").toExternalForm()
                );

                dialogPane.getStyleClass().add("my-dialog");

                alert.setTitle("Rating Added");
                alert.setHeaderText("Success");
                alert.setContentText("Your rating has been added successfully.");
                alert.showAndWait();
            } else {
                throw new Exception("Vehicle not found.");
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Saving Rating");
            alert.setContentText("An error occurred while saving the rating: " + e.getMessage());
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
        Long vehicleId = (Long) vehicle.getId();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            vehicle = session.get(Vehicle.class, vehicleId);
            if (vehicle != null) {

                vehicle.increaseQuantity();
                session.update(vehicle);

                session.getTransaction().commit();

                // Odświeżanie interfejsu użytkownika
                filterVehicles();
                refreshSummaryTable();
                vehicleTableView.refresh();
                summaryTableView.refresh();

                System.out.println("Vehicle returned.");

            } else {
                System.out.println("Vehicle not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Return Failed");
            alert.setContentText("An error occurred while trying to return product.");
            alert.showAndWait();
        }

        /*
        for(CarShowroom carShowroom : showroomContainer.getShowrooms()) {
            if(carShowroom.getShowroomName().equals(vehicle.getShowroom().getShowroomName())) {

                if(vehicle.getQuantity() == 0)
                    vehicle.setQuantity(1);
                carShowroom.addProduct(vehicle);
            }
        }

        filterVehicles();
        vehicleTableView.refresh();

         */
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
