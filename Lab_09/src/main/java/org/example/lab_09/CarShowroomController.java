package org.example.lab_09;

import jakarta.validation.Valid;
import org.example.lab_09.core.CarShowroom;
import org.example.lab_09.core.CarShowroomContainer;
import org.example.lab_09.core.Rating;
import org.example.lab_09.core.Vehicle;
import org.example.lab_09.repository.CarShowroomRepository;
import org.example.lab_09.service.CarShowroomService;
import org.example.lab_09.service.RatingService;
import org.example.lab_09.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class CarShowroomController {
    private final VehicleService vehicleService;
    private final CarShowroomService showroomService;
    private final RatingService ratingService;
    private final CSVExporter csvExporter;

    public CarShowroomController(VehicleService vehicleService, CarShowroomService showroomService, RatingService ratingService, CSVExporter csvExporter) {
        this.vehicleService = vehicleService;
        this.showroomService = showroomService;
        this.ratingService = ratingService;
        this.csvExporter = csvExporter;
    }

    @PostMapping("/product")
    public ResponseEntity<Vehicle> addOrUpdateVehicle(@RequestBody @Valid Vehicle vehicle) {
        Optional<Vehicle> existingVehicle = vehicleService.findVehicleByEverything(vehicle.getBrand(), vehicle.getModel(), vehicle.getCondition(), vehicle.getPrice(), vehicle.getYearOfProduction(), vehicle.getMileage(), vehicle.getEngineCapacity(), vehicle.getShowroom());
        if (existingVehicle.isPresent()) {
            Vehicle updatedVehicle = existingVehicle.get();
            updatedVehicle.increaseQuantity();
            vehicleService.addVehicle(updatedVehicle);
            return ResponseEntity.ok(updatedVehicle);
        } else {
            Vehicle savedVehicle = vehicleService.addVehicle(vehicle);
            return ResponseEntity.ok(savedVehicle);
        }
    }


    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> decreaseOrRemoveVehicle(@PathVariable Long id) {
        Optional<Vehicle> vehicleOptional = vehicleService.findVehicle(id);
        if (!vehicleOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Vehicle vehicle = vehicleOptional.get();
        int newQuantity = vehicle.getQuantity() - 1;
        if (newQuantity >= 0) {
            vehicle.setQuantity(newQuantity);
            vehicleService.addVehicle(vehicle);
            return ResponseEntity.ok().body("Vehicle quantity decreased to " + newQuantity);
        } else {
            return ResponseEntity.badRequest().body("Cannot remove vehicle, quantity would drop below zero.");
        }
    }

    @GetMapping("/product/{id}/rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long id) {
        Double averageRating = ratingService.calculateAverageRating(id);
        if (averageRating == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(averageRating);
    }

    @GetMapping("/product/csv")
    public ResponseEntity<byte[]> getAllVehiclesCsv() {
        try {
            byte[] csvData = csvExporter.exportToCsv();
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vehicles.csv");
            headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");
            return ResponseEntity.ok().headers(headers).body(csvData);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/fulfillment")
    public ResponseEntity<List<CarShowroom>> getAllShowrooms() {
        List<CarShowroom> showrooms = showroomService.findAllShowrooms();
        return ResponseEntity.ok(showrooms);
    }


    @PostMapping("/fulfillment")
    public ResponseEntity<CarShowroom> addShowroom(@RequestBody @Valid CarShowroom showroom) {
        CarShowroom savedShowroom = showroomService.addShowroom(showroom);
        return ResponseEntity.ok(savedShowroom);
    }

    @DeleteMapping("/fulfillment/{id}")
    public ResponseEntity<?> deleteShowroom(@PathVariable Long id) {
        if (!showroomService.existsShowroom(id)) {
            return ResponseEntity.notFound().build();
        }
        showroomService.deleteShowroom(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fulfillment/{id}/products")
    public ResponseEntity<List<Vehicle>> getVehiclesByShowroom(@PathVariable Long id) {
        List<Vehicle> vehicles = vehicleService.findByShowroomId(id);
        if (vehicles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/fulfillment/{id}/fill")
    public ResponseEntity<?> getFulfillmentPercentage(@PathVariable Long id) {
        Double fulfillment = showroomService.findFulfillmentPercentageById(id);
        if (fulfillment == 0.0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fulfillment + "%");
    }

    @PostMapping("/rating")
    public ResponseEntity<Rating> addRating(@RequestBody @Valid RatingDto ratingDto) {
        if (ratingDto.getScore() <= 0 || ratingDto.getScore() > 5) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Optional<Vehicle> vehicle = vehicleService.findVehicle(ratingDto.getVehicleId());
        if (!vehicle.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Rating rating = new Rating(vehicle.get(), ratingDto.getScore());
        Rating savedRating = ratingService.addRating(rating);
        return ResponseEntity.ok(savedRating);
    }

}


