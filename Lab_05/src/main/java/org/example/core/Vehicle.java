package org.example.core;

public class Vehicle implements Comparable<Vehicle>{
    public String brand;
    public String model;
    public ItemCondition condition;
    public double price;
    public int yearOfProduction;
    public double mileage;
    public double engineCapacity;
    public int quantity = 1;

    public Vehicle(String brand, String model, ItemCondition condition, double price, int yearOfProduction, double mileage, double engineCapacity) {
        this.brand = brand;
        this.model = model;
        this.condition = condition;
        this.price = price;
        this.yearOfProduction = yearOfProduction;
        this.mileage = mileage;
        this.engineCapacity = engineCapacity;
    }

    public void print() {
        System.out.println("Marka: " + brand + ", Model: " + model + ", Stan: " + condition + ", Cena: " + price + ", Rok produkcji: " + yearOfProduction + ", Przebieg: " + mileage + ", Pojemność silnika: " + engineCapacity);
    }


    @Override
    public int compareTo(Vehicle o) {
        return (this.brand.compareTo(o.brand));
    }

    public ItemCondition getCondition() {
        return condition;
    }

    public int getAmount() {
        return quantity;
    }
}
