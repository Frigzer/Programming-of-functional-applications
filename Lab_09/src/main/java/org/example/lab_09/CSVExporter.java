package org.example.lab_09;

import org.example.lab_09.core.Vehicle;
import org.example.lab_09.repository.VehicleRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CSVExporter {

    private final VehicleRepository vehicleRepository;

    @Autowired
    public CSVExporter(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public byte[] exportToCsv() throws IOException {
        List<Vehicle> vehicles = vehicleRepository.findAll();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (OutputStreamWriter writer = new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8)) {
            Field[] fields = Vehicle.class.getDeclaredFields();
            List<Field> exportableFields = Arrays.stream(fields)
                    .filter(f -> f.isAnnotationPresent(Exportable.class))
                    .sorted(Comparator.comparingInt(f -> f.getAnnotation(Exportable.class).order()))
                    .collect(Collectors.toList());

            String header = exportableFields.stream()
                    .map(f -> f.getAnnotation(Exportable.class).columnName())
                    .collect(Collectors.joining(","));
            writer.write(header);
            writer.write("\n");

            for (Vehicle vehicle : vehicles) {
                String line = exportableFields.stream()
                        .map(f -> getFieldValue(f, vehicle))
                        .collect(Collectors.joining(","));
                writer.write(line);
                writer.write("\n");
            }
            writer.flush();
        }
        return byteArrayOutputStream.toByteArray();
    }

    private String getFieldValue(Field f, Vehicle vehicle) {
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
