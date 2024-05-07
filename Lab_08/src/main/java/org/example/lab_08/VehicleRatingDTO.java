package org.example.lab_08;

import org.example.lab_08.core.ItemCondition;

public class VehicleRatingDTO {
    private String brand;
    private String model;
    private ItemCondition condition;
    private Double price;
    private Integer yearOfProduction;
    private Integer quantity;
    private Long ratingCount;
    private Double averageScore;

    // Konstruktor
    public VehicleRatingDTO(String brand, String model, ItemCondition condition, Double price, Integer yearOfProduction, Integer quantity, Long ratingCount, Double averageScore) {
        this.brand = brand;
        this.model = model;
        this.condition = condition;
        this.price = price;
        this.yearOfProduction = yearOfProduction;
        this.quantity = quantity;
        this.ratingCount = ratingCount;
        this.averageScore = averageScore;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public ItemCondition getCondition() {
        return condition;
    }
    public void setCondition(ItemCondition condition) {
        this.condition = condition;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getYearOfProduction() {
        return yearOfProduction;
    }
    public void setYearOfProduction(Integer yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Long getRatingCount() {
        return ratingCount;
    }
    public void setRatingCount(Long ratingCount) {
        this.ratingCount = ratingCount;
    }
    public Double getAverageScore() {
        return averageScore;
    }
    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

}
