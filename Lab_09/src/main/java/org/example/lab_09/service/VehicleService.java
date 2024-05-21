package org.example.lab_09.service;

import jakarta.transaction.Transactional;
import org.example.lab_09.core.CarShowroom;
import org.example.lab_09.core.ItemCondition;
import org.example.lab_09.core.Vehicle;
import org.example.lab_09.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    private VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Transactional
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    public Optional<Vehicle> findVehicleByEverything(String brand, String model, ItemCondition condition, double price, int yearOfProduction, double mileage, double engineCapacity, CarShowroom showroom) {
        return vehicleRepository.findByBrandAndModelAndConditionAndPriceAndYearOfProductionAndMileageAndEngineCapacityAndShowroom(brand, model, condition, price, yearOfProduction, mileage, engineCapacity, showroom);
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public List<Vehicle> findByShowroomId(Long showroomId) {
        return vehicleRepository.findByShowroomId(showroomId);
    }

    public Optional<Vehicle> findVehicle(Long id) {
        return vehicleRepository.findById(id);
    }

    public VehicleRepository getVehicleRepository() {
        return vehicleRepository;
    }
}
