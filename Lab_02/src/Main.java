import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Tworzenie salonów samochodowych
        CarShowroomContainer container = new CarShowroomContainer();
        container.addCenter("Moto Sports", 10);
        container.addCenter("Auto Com", 20);

        // Tworzenie pojazdów
        Vehicle v1 = new Vehicle("Toyota", "Yaris", ItemCondition.USED, 33900, 2014, 175000, 3800);
        Vehicle v2 = new Vehicle("Audi", "SQ8", ItemCondition.USED, 399750, 2021, 24000, 2340);
        Vehicle v3 = new Vehicle("Audi", "S6", ItemCondition.USED, 210000, 2018, 68500, 3210);
        Vehicle v4 = new Vehicle("Audi", "RS7", ItemCondition.USED, 253000, 2016, 112013, 1930);
        Vehicle v5 = new Vehicle("Mercedes-Benz", "EQS", ItemCondition.NEW, 418100, 2023, 1, 1520);
        Vehicle v6 = new Vehicle("BMW", "M8", ItemCondition.USED, 630000, 2023, 9300, 4920);
        Vehicle v7 = new Vehicle("Porche", "718 Cayman", ItemCondition.USED, 849000, 2023, 15693, 2525);
        Vehicle v8 = new Vehicle("BMW", "M8", ItemCondition.USED, 439000, 2020, 79000, 4395);

        // Dodawanie pojazdów do salonu
        CarShowroom s1 = container.getShowroom("Moto Sports");
        CarShowroom s2 = container.getShowroom("Auto Com");

        s1.addProduct(v1);
        s1.addProduct(v2);
        s1.addProduct(v3);
        s1.addProduct(v4);

        s2.addProduct(v5);
        s2.addProduct(v5);
        s2.addProduct(v5);
        s2.addProduct(v6);
        s2.addProduct(v7);
        s2.addProduct(v8);
        s2.addProduct(v6);
        s2.addProduct(v6);
        s2.addProduct(v6);
        s2.addProduct(v6);

        // Wypisanie wszystkich pojazdów
        System.out.println("Podsumowanie salonu 1:");
        s1.summary();
        System.out.println("\nPodsumowanie salonu 2:");
        s2.summary();

        // Usuwanie pojazdu
        s2.getProduct(v6);

        s1.removeProduct(v2);

        // Wyszukiwanie pojazdu
        System.out.println("\nWyniki wyszukiwan dla 'Toyota':");
        Vehicle searchedVehicle = s1.search("Toyota Yaris");
        if (searchedVehicle != null) {
            searchedVehicle.print();
        } else {
            System.out.println("Nie znaleziono.");
        }

        // Wyszukiwanie częściowe
        System.out.println("\nWyniki wyszukiwan dla 'Po':");
        List<Vehicle> partialSearchResult = s2.searchPartial("Po");
        if (!partialSearchResult.isEmpty()) {
            for (Vehicle v : partialSearchResult) {
                v.print();
            }
        } else {
            System.out.println("Nie znaleziono.");
        }

        // Ilość pojazdów w danym stanie
        System.out.println("\nLiczba NOWYCH pojazdow w salonie 2: " + s2.countByCondition(ItemCondition.NEW));

        // Sortowanie po nazwie
        s1.sortByName();
        System.out.println("\nPojazdy w salonie 1 posortowane po nazwie:");
        s1.summary();

        // Sortowanie po ilości
        s2.sortByAmount();
        System.out.println("\nPosortowane pojazdy w salonie 2 po ilosci:");
        s2.summary();

        // Pobranie pojazdu, którego jest najwięcej
        System.out.println("\nPojazd ktorego jest najwiecej w salonie 2:");
        s2.max().print();

        System.out.println("\nPodsumowanie obu salonow:");
        container.summary();

        // Znalezienie pustego salonu
        s1.removeProduct(v1);
        s1.removeProduct(v3);
        s1.removeProduct(v4);
        System.out.println("\nPuste salony");
        System.out.println("\n" + container.findEmpty());

        // Usuniecie salonu
        System.out.println("\nUsuniecie salonu 2");
        container.removeCenter("Auto Com");

    }

}