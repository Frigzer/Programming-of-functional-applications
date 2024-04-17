package org.example.models;

import org.example.core.Vehicle;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class VehicleTableModel extends AbstractTableModel {
    private List<Vehicle> vehicles;

    public VehicleTableModel(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public int getRowCount() {
        return vehicles.size();
    }

    @Override
    public int getColumnCount() {
        return 8; // Liczba kolumn, na przykład: marka, model, stan, cena, rok, pojemność
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Vehicle vehicle = vehicles.get(rowIndex);
        switch (columnIndex) {
            case 0: return vehicle.brand;
            case 1: return vehicle.model;
            case 2: return vehicle.condition;
            case 3: return vehicle.price;
            case 4: return vehicle.yearOfProduction;
            case 5: return vehicle.mileage;
            case 6: return vehicle.engineCapacity;
            case 7: return vehicle.getAmount();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "Marka";
            case 1: return "Model";
            case 2: return "Stan";
            case 3: return "Cena";
            case 4: return "Rok produkcji";
            case 5: return "Przebieg";
            case 6: return "Pojemność silnika";
            case 7: return "Liczba pojazdów";
            default: return "";
        }
    }

}