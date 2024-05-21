package org.example.lab_09.repository;

import org.example.lab_09.core.CarShowroom;
import org.example.lab_09.core.ItemCondition;
import org.example.lab_09.core.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByShowroomId(Long showroomId);


    Optional<Vehicle> findByBrandAndModelAndConditionAndPriceAndYearOfProductionAndMileageAndEngineCapacityAndShowroom(String brand, String model, ItemCondition condition, double price, int yearOfProduction, double mileage, double engineCapacity, CarShowroom showroom);
}
