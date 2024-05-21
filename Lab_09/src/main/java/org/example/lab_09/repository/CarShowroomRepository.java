package org.example.lab_09.repository;

import org.example.lab_09.core.CarShowroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarShowroomRepository extends JpaRepository<CarShowroom, Long> {
    @Query("SELECT (SUM(v.quantity) / s.maxCapacity) * 100.0 FROM CarShowroom s JOIN s.cars v WHERE s.id = :id GROUP BY s")
    Double findFulfillmentPercentageById(@Param("id") Long id);
}
