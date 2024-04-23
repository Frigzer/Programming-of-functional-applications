package org.example.lab_06.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CarShowroom {

    public String showroomName;
    public List<Vehicle> cars;
    public double maxCapacity;

    public CarShowroom(String showroomName, int maxCapacity) {
        this.showroomName = showroomName;
        this.maxCapacity = maxCapacity;
        this.cars = new ArrayList<>();
    }

    public void addProduct(Vehicle vehicle) {
        if (cars.size() >= maxCapacity) {
            System.err.println("Pojemność magazynu została przekroczona");
            return;
        }

        for (Vehicle v : cars) {
            String fullname1 = v.brand + " " + v.model;
            String fullname2 = vehicle.brand + " " + vehicle.model;
            if (fullname1.compareTo(fullname2) == 0) {
                v.quantity++;
                return;
            }
        }

        cars.add(vehicle);
    }

    public void getProduct(Vehicle vehicle) {
        for (Vehicle v : cars) {
            if(v.equals(vehicle)) {
                v.quantity -=1;
                if(v.quantity == 0)
                    cars.remove(vehicle);
                return;
            }
        }
    }

    public void removeProduct(Vehicle vehicle) {
        cars.remove(vehicle);
    }


    public Vehicle search(String name) {
        for (Vehicle v : cars) {
            String fullName = v.brand + " " + v.model;
            if(fullName.compareTo(name) == 0) {
                return v;
            }
        }
        return null;
    }


    public List<Vehicle> searchPartial(String name) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : cars) {
            if (v.brand.contains(name) || v.model.contains(name)) {
                result.add(v);
            }
        }
        return result;
    }

    public int countByCondition(ItemCondition condition) {
        int i = 0;
        for (Vehicle v : cars) {
            if (v.getCondition() == condition) {
                i+=v.quantity;
            }
        }
        return i;
    }

    public List<Vehicle> filterByCondition(ItemCondition condition) {
        return cars.stream().filter(v -> v.condition == condition).collect(Collectors.toList());
    }

    public void summary() {
        for (Vehicle v : cars) {
            v.print();
        }
    }

    public void sortByName() {
        Collections.sort(cars);
    }


    public void sortByAmount() {
        AmountComparator comparator = new AmountComparator();
        Collections.sort(cars, comparator);
    }


    public Vehicle max() {
        return Collections.max(cars, Comparator.comparingInt(Vehicle::getQuantity));
    }

    public boolean isEmpty() {
        return cars.isEmpty();
    }

    public double getFillPercentage() {
        return ((double) cars.size() / maxCapacity) * 100;
    }

    public List<Vehicle> getVehicleList() {
        return cars;
    }

    public String getShowroomName() {
        return showroomName;
    }


    static class AmountComparator implements Comparator<Vehicle> {
        @Override
        public int compare(Vehicle v1, Vehicle v2) {
            return Integer.compare(v2.getQuantity(), v1.getQuantity());
        }
    }

}
