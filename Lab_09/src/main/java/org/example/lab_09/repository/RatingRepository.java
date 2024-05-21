package org.example.lab_09.repository;

import org.example.lab_09.core.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RatingRepository  extends JpaRepository<Rating, Long> {
    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.vehicle.id = :vehicleId")
    Double findAverageRatingByVehicleId(Long vehicleId);
}
