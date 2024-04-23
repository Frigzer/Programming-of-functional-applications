package org.example.lab_06.core;

public class Vehicle implements Comparable<Vehicle>{
    public String brand;
    public String model;
    public ItemCondition condition;
    public double price;
    public int yearOfProduction;
    public double mileage;
    public double engineCapacity;
    public int quantity = 1;
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
}
