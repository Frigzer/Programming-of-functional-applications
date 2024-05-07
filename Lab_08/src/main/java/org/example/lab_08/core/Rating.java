package org.example.lab_08.core;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "score")
    private double score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = true)
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



    /*
    public CarShowroom getShowroom() {
        return showroom;
    }

    public void setShowroom(CarShowroom showroom) {
        this.showroom = showroom;
    }
     */

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
}
