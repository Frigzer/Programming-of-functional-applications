package org.example.lab_09.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "score")
    @NotNull(message = "Score cannot be null")
    private double score;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = true)
    @NotNull(message = "Vehicle id cannot be null")
    private Vehicle vehicle;

    @Column(name = "review_date")
    private Date reviewDate;

    @Column(name = "description")
    private String description;

    public Rating(Vehicle vehicle, double rating) {
        this.vehicle = vehicle;
        this.score = rating;
    }

    public Rating() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }


    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getVehicleId() {
        return vehicle.getId();
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicle.setId(vehicleId);
    }
}
