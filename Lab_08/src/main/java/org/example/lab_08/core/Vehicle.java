package org.example.lab_08.core;

import jakarta.persistence.*;
import org.example.lab_08.Exportable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "vehicles")
public class Vehicle implements Comparable<Vehicle>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand")
    @Exportable(columnName = "Brand", order = 1)
    private String brand;

    @Column(name = "model")
    @Exportable(columnName = "Model", order = 2)
    private String model;

    @Column(name = "item_condition")
    @Exportable(columnName = "Item condition", order = 3)
    private ItemCondition condition;

    @Column(name = "price")
    @Exportable(columnName = "Price", order = 4)
    private double price;

    @Column(name = "year_of_production")
    @Exportable(columnName = "Year of Production", order = 5)
    private int yearOfProduction;

    @Column(name = "mileage")
    @Exportable(columnName = "Mileage", order = 6)
    private double mileage;

    @Column(name = "engine_capacity")
    @Exportable(columnName = "Engine Capacity", order = 7)
    private double engineCapacity;

    @Column(name = "quantity")
    @Exportable(columnName = "Quantity", order = 8)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "showroom_id")
    @Exportable(columnName = "Showroom", order = 9)
    private CarShowroom showroom;

    @Transient
    private String imagePath;

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY)
    private Set<Rating> ratings;

    public Vehicle(String brand, String model, ItemCondition condition, double price, int yearOfProduction, double mileage, double engineCapacity, CarShowroom showroom, Set<Rating> ratings) {
        this.brand = brand;
        this.model = model;
        this.condition = condition;
        this.price = price;
        this.yearOfProduction = yearOfProduction;
        this.mileage = mileage;
        this.engineCapacity = engineCapacity;
        this.showroom = showroom;
        this.ratings = ratings;
        this.imagePath = "carImages/" + brand.toLowerCase() + "_" + model.toLowerCase() + ".jpg";
    }

    public Vehicle(String brand, String model, ItemCondition condition, double price, int yearOfProduction, double mileage, double engineCapacity) {
        this.brand = brand;
        this.model = model;
        this.condition = condition;
        this.price = price;
        this.yearOfProduction = yearOfProduction;
        this.mileage = mileage;
        this.engineCapacity = engineCapacity;
        this.imagePath = "carImages/" + brand.toLowerCase() + "_" + model.toLowerCase() + ".jpg";
    }



    public Vehicle() {

    }

    public void print() {
        System.out.println("Marka: " + brand + ", Model: " + model + ", Stan: " + condition + ", Cena: " + price + ", Rok produkcji: " + yearOfProduction + ", Przebieg: " + mileage + ", Pojemność silnika: " + engineCapacity);
    }


    @Override
    public int compareTo(Vehicle o) {
        return (this.brand.compareTo(o.brand));
    }

    public String getBrand() {
        return brand;
    }
    public String getModel() {
        return model;
    }

    public ItemCondition getCondition() {
        return condition;
    }

    public double getPrice() {
        return price;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public double getMileage() {
        return mileage;
    }

    public double getEngineCapacity() {
        return engineCapacity;
    }

    public int getQuantity() {
        return quantity;
    }

    public CarShowroom getShowroom() {
        return showroom;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setShowroom(CarShowroom showroom) {
        this.showroom = showroom;
    }

    public void setCondition(ItemCondition condition) {
        this.condition = condition;
    }

    public void setImagePath() {
        this.imagePath = "carImages/" + brand.toLowerCase() + "_" + model.toLowerCase() + ".jpg";
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity() {
        this.quantity++;
    }

    public void decreaseQuantity() {
        this.quantity--;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }
}
