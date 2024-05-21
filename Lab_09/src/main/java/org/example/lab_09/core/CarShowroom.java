package org.example.lab_09.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "car_showroom")
public class CarShowroom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "showroom_name")
    @NotNull(message = "Showroom name cannot be null")
    private String showroomName;

    @Column(name = "max_capacity")
    @NotNull(message = "Max capacity cannot be null")
    private double maxCapacity;

    @JsonIgnore
    @OneToMany(mappedBy = "showroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicle> cars = new ArrayList<>();

    public CarShowroom(String showroomName, int maxCapacity) {
        this.showroomName = showroomName;
        this.maxCapacity = maxCapacity;
    }

    public CarShowroom() {
    }

    private int countQuantity() {
        int quantity = 0;
        for (Vehicle vehicle : cars) {
            quantity += vehicle.getQuantity();
        }
        return quantity;
    }

    public void addProduct(Vehicle vehicle) {
        if (cars.size() >= maxCapacity) {
            System.err.println("Pojemność magazynu została przekroczona");
            return;
        }

        for (Vehicle v : cars) {
            String fullname1 = v.getBrand() + " " + v.getModel();
            String fullname2 = vehicle.getBrand() + " " + vehicle.getModel();
            if (fullname1.compareTo(fullname2) == 0) {
                v.increaseQuantity();
                return;
            }
        }

        cars.add(vehicle);
    }

    public void getProduct(Vehicle vehicle) {
        for (Vehicle v : cars) {
            if(v.equals(vehicle)) {
                v.decreaseQuantity();
                if(v.getQuantity() < 1)
                    cars.remove(vehicle);
                return;
            }
        }
    }

    /*
    public void removeProduct(Vehicle vehicle) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Vehicle managedVehicle = em.find(Vehicle.class, vehicle.getId());
            if (managedVehicle != null) {
                em.remove(managedVehicle);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

     */


    public Vehicle search(String name) {
        for (Vehicle v : cars) {
            String fullName = v.getBrand() + " " + v.getModel();
            if(fullName.compareTo(name) == 0) {
                return v;
            }
        }
        return null;
    }


    public List<Vehicle> searchPartial(String name) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : cars) {
            if (v.getBrand().contains(name) || v.getModel().contains(name)) {
                result.add(v);
            }
        }
        return result;
    }

    public int countByCondition(ItemCondition condition) {
        int i = 0;
        for (Vehicle v : cars) {
            if (v.getCondition() == condition) {
                i+=v.getQuantity();
            }
        }
        return i;
    }

    public List<Vehicle> filterByCondition(ItemCondition condition) {
        return cars.stream().filter(v -> v.getCondition() == condition).collect(Collectors.toList());
    }

    public void summary() {
        for (Vehicle v : cars) {
            v.print();
        }
    }

    public void sortByName() {
        Collections.sort(cars);
    }


    public void sortByAmount() {
        AmountComparator comparator = new AmountComparator();
        Collections.sort(cars, comparator);
    }


    public Vehicle max() {
        return Collections.max(cars, Comparator.comparingInt(Vehicle::getQuantity));
    }

    public boolean isEmpty() {
        return cars.isEmpty();
    }

    public int getVehicleCount() {
        return countQuantity();
    }

    public double getFillPercentage() {
        return ((double) countQuantity() / maxCapacity) * 100;
    }

    @JsonIgnore
    public List<Vehicle> getVehicleList() {
        return cars;
    }

    public String getShowroomName() {
        return showroomName;
    }


    static class AmountComparator implements Comparator<Vehicle> {
        @Override
        public int compare(Vehicle v1, Vehicle v2) {
            return Integer.compare(v2.getQuantity(), v1.getQuantity());
        }
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setShowroomName(String showroomName) {
        this.showroomName = showroomName;
    }
    public List<Vehicle> getCars() {
        return cars;
    }
    public void setCars(List<Vehicle> cars) {
        this.cars = cars;
    }
    public double getMaxCapacity() {
        return maxCapacity;
    }
    public void setMaxCapacity(double maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

}
