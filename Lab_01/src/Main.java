import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Wybierz figurę (1-Trójkąt, 2-Kwadrat, 3-Koło, 4-Graniastosłup prawidłowy, 5-Wyjście): ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    try {
                        System.out.println("Podaj długości boków trójkąta:");
                        double a = scanner.nextDouble();
                        double b = scanner.nextDouble();
                        double c = scanner.nextDouble();
                        Triangle triangle = new Triangle(a, b, c);
                        triangle.print();
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        System.out.println("Podaj długość boku kwadratu:");
                        double side = scanner.nextDouble();
                        Square square = new Square(side);
                        square.print();
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.println("Podaj promień koła:");
                        double radius = scanner.nextDouble();
                        Circle circle = new Circle(radius);
                        circle.print();
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        System.out.println("Podaj wysokość:");
                        double height = scanner.nextDouble();
                        System.out.println("Wybierz podstawę:");
                        int choice2 = scanner.nextInt();
                        switch (choice2) {
                            case 1:
                                try {
                                    System.out.println("Podaj długości boków trójkąta:");
                                    double a = scanner.nextDouble();
                                    double b = scanner.nextDouble();
                                    double c = scanner.nextDouble();
                                    Triangle triangle = new Triangle(a, b, c);

                                    Prism prism = new Prism(triangle, height);
                                    System.out.print("Podstawa: ");
                                    triangle.print();
                                    System.out.println("Wysokość graniastosłupa: " + height + ", objetość: " + prism.calculateVolume() + ", pole powierzchni bocznejL " + prism.calculateSurfaceArea());
                                } catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 2:
                                try {
                                    System.out.println("Podaj długość boku kwadratu:");
                                    double side = scanner.nextDouble();
                                    Square square = new Square(side);

                                    Prism prism = new Prism(square, height);
                                    System.out.print("Podstawa: ");
                                    square.print();
                                    System.out.println("Wysokość graniastosłupa: " + height + ", objetość: " + prism.calculateVolume() + ", pole powierzchni bocznejL " + prism.calculateSurfaceArea());
                                } catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 3:
                                try {
                                    System.out.println("Podaj promień koła:");
                                    double radius = scanner.nextDouble();
                                    Circle circle = new Circle(radius);

                                    Prism prism = new Prism(circle, height);
                                    System.out.print("Podstawa: ");
                                    circle.print();
                                    System.out.println("Wysokość graniastosłupa: " + height + ", objetość: " + prism.calculateVolume() + ", pole powierzchni bocznejL " + prism.calculateSurfaceArea());
                                } catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            default:
                                System.out.println("Niepoprawny wybór.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Koniec programu.");
                    return;
                default:
                    System.out.println("Niepoprawny wybór.");
            }
        }
    }
}