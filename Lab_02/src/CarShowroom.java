import java.util.*;

public class CarShowroom{

    public String showroomName;
    List<Vehicle> cars;
    double maxCapacity;

    CarShowroom(String showroomName, int maxCapacity) {
        this.showroomName = showroomName;
        this.maxCapacity = maxCapacity;
        this.cars = new ArrayList<>();
    }

    public void addProduct(Vehicle vehicle) {
        if (cars.size() >= maxCapacity) {
            System.err.println("Pojemność magazynu została przekroczona");
            return;
        }

        for (Vehicle v : cars) {
            if (v.equals(vehicle)) {
                v.quantity++;
                return;
            }
        }

        cars.add(vehicle);
    }

    public void getProduct(Vehicle vehicle) {
        for (Vehicle v : cars) {
            if(v.equals(vehicle)) {
                v.quantity -=1;
                if(v.quantity == 0)
                    cars.remove(v);
                return;
            }
        }
    }

    public void removeProduct(Vehicle vehicle) {
        cars.remove(vehicle);
    }


    public Vehicle search(String name) {
        for (Vehicle v : cars) {
            String fullName = v.brand + " " + v.model;
            if(fullName.compareTo(name) == 0) {
                return v;
            }
        }
        return null;
    }


    public List<Vehicle> searchPartial(String name) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : cars) {
            if (v.brand.contains(name) || v.model.contains(name)) {
                result.add(v);
            }
        }
        return result;
    }

    public int countByCondition(ItemCondition condition) {
        int i = 0;
        for (Vehicle v : cars) {
            if (v.getCondition() == condition) {
                i+=v.quantity;
            }
        }
        return i;
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
        return Collections.max(cars, Comparator.comparingInt(Vehicle::getAmount));
    }

    public boolean isEmpty() {
        return cars.isEmpty();
    }

    public double getFillPercentage() {
        return ((double) cars.size() / maxCapacity) * 100;
    }

    static class AmountComparator implements Comparator<Vehicle> {
        @Override
        public int compare(Vehicle v1, Vehicle v2) {
            return Integer.compare(v2.getAmount(), v1.getAmount());
        }
    }

}
