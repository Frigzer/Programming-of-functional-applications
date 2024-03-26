import java.util.*;

public class CarShowroomContainer {
    private Map<String, CarShowroom> carShowrooms;

    public CarShowroomContainer() {
        this.carShowrooms = new HashMap<>();
    }

    public void addCenter(String name, int maxCapacity) {
        carShowrooms.put(name, new CarShowroom(name, maxCapacity));
    }

    public void removeCenter(String name) {
        carShowrooms.remove(name);
    }

    public List<CarShowroom> findEmpty() {
        List<CarShowroom> emptyShowrooms = new ArrayList<>();
        for (CarShowroom s : carShowrooms.values()) {
            if (s.isEmpty()) {
                emptyShowrooms.add(s);
            }
        }
        for (CarShowroom s : emptyShowrooms) {
            System.out.println(s.showroomName);
        }
        return emptyShowrooms;
    }

    public void summary() {
        for (CarShowroom s : carShowrooms.values()) {
            System.out.println("Nazwa salonu: " + s.showroomName);
            System.out.println("Procent wypełnienia: " + s.getFillPercentage());
        }
    }

    public CarShowroom getShowroom(String name) {
        return carShowrooms.get(name);
    }
}
