package org.example.gui;

import org.example.core.CarShowroom;
import org.example.core.ItemCondition;
import org.example.core.Vehicle;
import org.example.models.VehicleTableModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;

public class VehiclePanel extends JPanel {
    private JTable vehicleTable;
    private VehicleTableModel vehicleTableModel;
    private JButton addButton, removeButton, sortByNameButton, sortByAmountButton;
    private CarShowroom showroom;
    private JTextField filterTextField;
    private JComboBox<ItemCondition> stateComboBox;

    public VehiclePanel(CarShowroom showroom) {
        this.showroom = showroom;

        setLayout(new BorderLayout());
        initUI();
        addListeners();
    }

    private void initUI() {
        vehicleTableModel = new VehicleTableModel(showroom.cars);
        vehicleTable = new JTable(vehicleTableModel);

        JScrollPane scrollPane = new JScrollPane(vehicleTable);
        add(scrollPane, BorderLayout.CENTER);

        addButton = new JButton("Add Vehicle");
        removeButton = new JButton("Remove Vehicle");
        sortByNameButton = new JButton("Sort by Name");
        sortByAmountButton = new JButton("Sort by Amount");

        filterTextField = new JTextField(20);

        stateComboBox = new JComboBox<>(ItemCondition.values());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(sortByNameButton);
        buttonPanel.add(sortByAmountButton);

        buttonPanel.add(filterTextField);

        buttonPanel.add(stateComboBox);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        addButton.addActionListener(e -> addVehicle());
        removeButton.addActionListener(e -> removeSelectedVehicle());
        sortByNameButton.addActionListener(e -> sortByName());
        sortByAmountButton.addActionListener(e -> sortByAmount());

        filterTextField.addActionListener(e -> {
            String searchText = filterTextField.getText();
            vehicleTableModel.setVehicles(showroom.searchPartial(searchText));
            vehicleTableModel.fireTableDataChanged();
        });

        stateComboBox.addActionListener(e -> {
            ItemCondition selectedCondition = (ItemCondition) stateComboBox.getSelectedItem();
            vehicleTableModel.setVehicles(showroom.filterByCondition(selectedCondition));
            vehicleTableModel.fireTableDataChanged();
        });
    }

    private void addVehicle() {
        try {
            String brand = JOptionPane.showInputDialog("Enter vehicle brand:");
            String model = JOptionPane.showInputDialog("Enter vehicle model:");
            try {
                ItemCondition condition = ItemCondition.valueOf(JOptionPane.showInputDialog("Enter vehicle condition:"));
                int price = Integer.parseInt(JOptionPane.showInputDialog("Enter price:"));
                int yearOfProduction = Integer.parseInt(JOptionPane.showInputDialog("Enter year of production:"));
                int mileage = Integer.parseInt(JOptionPane.showInputDialog("Enter mileage:"));
                int engineCapacity = Integer.parseInt(JOptionPane.showInputDialog("Enter engine capacity:"));
                Vehicle newVehicle = new Vehicle(brand, model, condition, price, yearOfProduction, mileage, engineCapacity);
                showroom.addProduct(newVehicle);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Invalid input for numeric value. Please enter a number.");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Invalid input for condition. Please enter: NEW, USED or DAMAGED.");
            }
            vehicleTableModel.fireTableDataChanged();
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Name is null.");
        }

    }

    private void removeSelectedVehicle() {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow >= 0) {
            Vehicle vehicle = showroom.cars.get(selectedRow);
            showroom.getProduct(vehicle);
            vehicleTableModel.fireTableDataChanged();
        }
    }

    private void sortByName() {
        showroom.sortByName();
        vehicleTableModel.fireTableDataChanged();
    }

    private void sortByAmount() {
        showroom.sortByAmount();
        vehicleTableModel.fireTableDataChanged();
    }

    public void updateShowroom(CarShowroom newShowroom) {
        this.showroom = newShowroom; // aktualizuje referencję do nowego salonu
        vehicleTableModel.setVehicles(showroom.cars); // aktualizuje dane w tabeli
        vehicleTableModel.fireTableDataChanged(); // informuje tabelę o zmianie danych
    }
}