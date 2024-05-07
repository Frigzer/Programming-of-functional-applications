package org.example.lab_08;

import org.example.lab_08.core.CarShowroom;
import org.example.lab_08.core.Vehicle;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CSVExporter {
    /*
    public static void export(List<CarShowroom> showrooms, String path) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            Field[] fields = Vehicle.class.getDeclaredFields();
            List<Field> exportableFields = Arrays.stream(fields)
                    .filter(f -> f.isAnnotationPresent(Exportable.class))
                    .sorted(Comparator.comparingInt(f -> f.getAnnotation(Exportable.class).order()))
                    .collect(Collectors.toList());

            String header = exportableFields.stream()
                    .map(f -> f.getAnnotation(Exportable.class).columnName())
                    .collect(Collectors.joining(","));
            writer.write(header);
            writer.newLine();

            for (CarShowroom showroom : showrooms) {
                for (Vehicle vehicle : showroom.getVehicleList()) {
                    for (int i = 0; i < vehicle.getQuantity(); i++) {
                        String line = exportableFields.stream()
                                .map(f -> getFieldValue(f, vehicle))
                                .collect(Collectors.joining(","));
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
        }
    }
     */

    private static Session session;

    public static void setSession(Session session) {
        CSVExporter.session = session;
    }

    public static void export(String path) throws IOException {
        // Utworzenie zapytania HQL do pobrania wszystkich pojazdów
        String hql = "SELECT v FROM Vehicle v JOIN FETCH v.showroom";
        Query<Vehicle> query = session.createQuery(hql, Vehicle.class);
        List<Vehicle> vehicles = query.getResultList();

        // Przetwarzanie wyników i eksport do CSV
        exportVehiclesToCSV(vehicles, path);
    }

    private static void exportVehiclesToCSV(List<Vehicle> vehicles, String path) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            Field[] fields = Vehicle.class.getDeclaredFields();
            List<Field> exportableFields = Arrays.stream(fields)
                    .filter(f -> f.isAnnotationPresent(Exportable.class))
                    .sorted(Comparator.comparingInt(f -> f.getAnnotation(Exportable.class).order()))
                    .collect(Collectors.toList());

            String header = exportableFields.stream()
                    .map(f -> f.getAnnotation(Exportable.class).columnName())
                    .collect(Collectors.joining(","));
            writer.write(header);
            writer.newLine();

            for (Vehicle vehicle : vehicles) {
                String line = exportableFields.stream()
                        .map(f -> getFieldValue(f, vehicle))
                        .collect(Collectors.joining(","));
                writer.write(line);
                writer.newLine();
            }
        }
    }


    private static String getFieldValue(Field f, Vehicle vehicle) {
        try {
            f.setAccessible(true);
            if ("showroom".equals(f.getName())) {
                return escapeSpecialCharacters(vehicle.getShowroom().getShowroomName());
            } else {
                return escapeSpecialCharacters(String.valueOf(f.get(vehicle)));
            }
        } catch (IllegalAccessException e) {
            return "";
        }
    }

    private static String escapeSpecialCharacters(String data) {
        String escapedData = data.replace("\"", "\"\"");
        if (data.contains(",") || data.contains("\"") || data.contains("\n")) {
            data = "\"" + escapedData + "\"";
        }
        return data;
    }
}

