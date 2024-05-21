package org.example.lab_09.core;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class CarShowroomContainer implements Serializable {
    private static CarShowroomContainer instance;
    public Map<String, CarShowroom> carShowrooms;

    public CarShowroomContainer() {
        this.carShowrooms = new HashMap<>();
        //initializeShowrooms();
    }

    public static synchronized CarShowroomContainer getInstance() {
        if (instance == null) {
            instance = new CarShowroomContainer();
        }
        return instance;
    }

    /*
    private void initializeShowrooms() {
        // Dodanie salonów
        CarShowroom showroom1 = new CarShowroom("Salon Warszawa", 50);
        CarShowroom showroom2 = new CarShowroom("Salon Kraków", 30);
        CarShowroom showroom3 = new CarShowroom("Salon Lublin", 20);

        // Dodanie pojazdów do salonu Warszawa
        showroom1.addProduct(new Vehicle("Toyota", "Corolla", ItemCondition.NEW, 80000, 2022, 10, 1700, showroom1));
        showroom1.addProduct(new Vehicle("Ford", "Focus", ItemCondition.USED, 50000, 2019, 50000, 2300, showroom1));
        showroom1.addProduct(new Vehicle("Audi", "SQ8", ItemCondition.USED, 399750, 2021, 24000, 2340, showroom1));
        showroom1.addProduct(new Vehicle("Audi", "S6", ItemCondition.USED, 210000, 2018, 68500, 3210, showroom1));
        showroom1.addProduct(new Vehicle("Audi", "RS7", ItemCondition.USED, 253000, 2016, 112013, 1930, showroom1));
        showroom1.addProduct(new Vehicle("Ford", "Focus", ItemCondition.USED, 50000, 2019, 50000, 2300, showroom1));
        showroom1.addProduct(new Vehicle("Ford", "Focus", ItemCondition.USED, 50000, 2019, 50000, 2300, showroom1));
        showroom1.addProduct(new Vehicle("Toyota", "Corolla", ItemCondition.NEW, 80000, 2022, 10, 1700, showroom1));

        // Dodanie pojazdów do salonu Kraków
        showroom2.addProduct(new Vehicle("Volkswagen", "Golf", ItemCondition.NEW, 90000, 2021, 5, 1500, showroom2));
        showroom2.addProduct(new Vehicle("Honda", "Civic", ItemCondition.USED, 75000, 2018, 30000, 3400, showroom2));
        showroom2.addProduct(new Vehicle("Honda", "Civic", ItemCondition.USED, 75000, 2018, 30000, 2700, showroom2));
        showroom2.addProduct(new Vehicle("Mercedes-Benz", "EQS", ItemCondition.NEW, 418100, 2023, 1, 1520, showroom2));
        showroom2.addProduct(new Vehicle("BMW", "M8", ItemCondition.USED, 630000, 2023, 9300, 4920, showroom2));
        showroom2.addProduct(new Vehicle("Porsche", "718 Cayman", ItemCondition.USED, 849000, 2023, 15693, 2525, showroom2));
        showroom2.addProduct(new Vehicle("BMW", "M8", ItemCondition.USED, 439000, 2020, 79000, 4395, showroom2));

        // Dodanie pojazdów do salonu Lublin
        showroom3.addProduct(new Vehicle("Nissan", "GT-R Premium", ItemCondition.USED, 528029, 2015, 3388000, 3800, showroom3));
        showroom3.addProduct(new Vehicle("Nissan", "GT-R Premium", ItemCondition.USED, 528029, 2015, 3388000, 3800, showroom3));
        showroom3.addProduct(new Vehicle("Nissan", "GT-R Premium", ItemCondition.USED, 528029, 2015, 3388000, 3800, showroom3));
        showroom3.addProduct(new Vehicle("Nissan", "GT-R Premium", ItemCondition.USED, 528029, 2015, 3388000, 3800, showroom3));
        showroom3.addProduct(new Vehicle("Lamborghini", "Aventador", ItemCondition.USED, 1766210, 2019, 8500, 12000, showroom3));
        showroom3.addProduct(new Vehicle("Lamborghini", "Aventador", ItemCondition.USED, 1766210, 2019, 8500, 12000, showroom3));
        showroom3.addProduct(new Vehicle("Nissan", "Skyline", ItemCondition.USED, 200000, 1996, 213000, 2568, showroom3));
        showroom3.addProduct(new Vehicle("Toyota", "Supra", ItemCondition.USED, 259999, 2021, 16000, 2998, showroom3));
        showroom3.addProduct(new Vehicle("Ford", "Mustang", ItemCondition.NEW, 325000, 2023, 200, 5038, showroom3));

        carShowrooms.put("Salon Warszawa", showroom1);
        carShowrooms.put("Salon Kraków", showroom2);
        carShowrooms.put("Salon Lublin", showroom3);
    }

     */

    public void addCenter(String name, int maxCapacity) {
        carShowrooms.put(name, new CarShowroom(name, maxCapacity));
    }

    public void removeCenter(String name) {
        carShowrooms.remove(name);
    }

    public List<CarShowroom> findEmpty() {
        List<CarShowroom> emptyShowrooms = new ArrayList<>();
        for (CarShowroom s : carShowrooms.values()) {
            if (s.isEmpty()) {
                emptyShowrooms.add(s);
            }
        }
        for (CarShowroom s : emptyShowrooms) {
            System.out.println(s.getShowroomName());
        }
        return emptyShowrooms;
    }

    public void summary() {
        for (CarShowroom s : carShowrooms.values()) {
            System.out.println("Nazwa salonu: " + s.getShowroomName());
            System.out.println("Procent wypełnienia: " + s.getFillPercentage());
        }
    }

    public CarShowroom getShowroom(String name) {
        return carShowrooms.get(name);
    }

    public List<String> getShowroomNames() {
        return carShowrooms.keySet().stream().collect(Collectors.toList());
    }


    public int getSize() {
        return carShowrooms.size();
    }

    public void sortShowroomsByLoad() {
        carShowrooms = carShowrooms.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparingDouble(CarShowroom::getFillPercentage).reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public List<CarShowroom> getShowrooms() {
        return new ArrayList<>(carShowrooms.values());
    }

    public CarShowroom findShowroomByName(String name) {
        return carShowrooms.get(name);
    }


}
