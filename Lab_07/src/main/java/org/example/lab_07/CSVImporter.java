package org.example.lab_07;

import org.example.lab_07.core.CarShowroom;
import org.example.lab_07.core.CarShowroomContainer;
import org.example.lab_07.core.ItemCondition;
import org.example.lab_07.core.Vehicle;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CSVImporter {
    private CarShowroomController controller;

    public CSVImporter(CarShowroomController controller) {
        this.controller = controller;
    }

    public static List<Vehicle> importFromCSV(String path) throws IOException {
        List<Vehicle> vehicles = new ArrayList<>();
        CarShowroomContainer container = CarShowroomContainer.getInstance();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                Vehicle vehicle = csvToVehicle(line, container);
                if (vehicle != null) {
                    vehicles.add(vehicle);
                    vehicle.getShowroom().addProduct(vehicle);

                }
            }
        }
        //controller.refreshShowroomComboBox();
        return vehicles;
    }

    private static Vehicle csvToVehicle(String csv, CarShowroomContainer container) {
        String[] parts = csv.split(",");
        Vehicle vehicle = new Vehicle();

        // Mapowanie orderu pola do samego pola na podstawie adnotacji
        Field[] fields = Vehicle.class.getDeclaredFields();
        Map<Integer, Field> fieldOrder = new HashMap<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Exportable.class)) {
                Exportable exp = field.getAnnotation(Exportable.class);
                if (exp.order() > 0 && exp.order() <= parts.length) {
                    fieldOrder.put(exp.order() - 1, field);
                }
            }
        }

        // Przypisywanie wartości do pól obiektu Vehicle
        for (Map.Entry<Integer, Field> entry : fieldOrder.entrySet()) {
            Field field = entry.getValue();
            String value = removeQuotes(parts[entry.getKey()]);

            try {
                field.setAccessible(true);
                if ("showroom".equals(field.getName())) {
                    CarShowroom showroom = container.findShowroomByName(value);
                    if (showroom == null) {
                        showroom = new CarShowroom(value, 100);
                        container.carShowrooms.put(value, showroom);
                    }
                    field.set(vehicle, showroom);
                } else {
                    Class<?> type = field.getType();
                    if (type == int.class) {
                        field.setInt(vehicle, Integer.parseInt(value));
                    } else if (type == double.class) {
                        field.setDouble(vehicle, Double.parseDouble(value));
                    } else if (type == String.class) {
                        field.set(vehicle, value);
                    }
                }
            } catch (IllegalAccessException e) {
                System.err.println("Access error during field setting: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("Number format error for field " + field.getName() + " with value '" + value + "': " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
            }
        }

        vehicle.setImagePath();
        vehicle.setCondition(ItemCondition.NEW);
        return vehicle;
    }

    private static String removeQuotes(String input) {
        return input.trim().replace("\"", "");
    }
}
