package org.example.lab_07.core;

import org.example.lab_07.Exportable;

import java.io.Serializable;

public class Vehicle implements Comparable<Vehicle>, Serializable {

    @Exportable(columnName = "Brand", order = 1)
    public String brand;

    @Exportable(columnName = "Model", order = 2)
    public String model;

    transient public ItemCondition condition;

    @Exportable(columnName = "Price", order = 3)
    public double price;

    @Exportable(columnName = "Year of Production", order = 4)
    public int yearOfProduction;

    @Exportable(columnName = "Mileage", order = 5)
    public double mileage;

    @Exportable(columnName = "Engine Capacity", order = 6)
    public double engineCapacity;

    transient public int quantity = 1;

    @Exportable(columnName = "Showroom", order = 7)
    private CarShowroom showroom;

    private String imagePath;

    public Vehicle(String brand, String model, ItemCondition condition, double price, int yearOfProduction, double mileage, double engineCapacity, CarShowroom showroom) {
        this.brand = brand;
        this.model = model;
        this.condition = condition;
        this.price = price;
        this.yearOfProduction = yearOfProduction;
        this.mileage = mileage;
        this.engineCapacity = engineCapacity;
        this.showroom = showroom;
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
}
