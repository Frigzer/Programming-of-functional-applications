package org.example.lab_09.core;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class ShoppingCart implements Serializable {

    private transient ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();

    public ObservableList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    public int getSize() {
        return vehicles.size();
    }

    public void clear() {
        vehicles.clear();
    }

    public double getTotalPrice() {
        double total = 0;
        for (Vehicle vehicle : vehicles) {
            total += vehicle.getPrice();
        }
        return total;
    }


    public void saveCart(File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<>(vehicles));
        }
    }

    public void loadCart(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            ArrayList<Vehicle> list = (ArrayList<Vehicle>) ois.readObject();
            vehicles.clear();
            for (Vehicle vehicle : list) {
                vehicle.setQuantity(1);
                vehicle.setCondition(ItemCondition.NEW);
            }
            vehicles.addAll(FXCollections.observableArrayList(list));
        }
    }
}
