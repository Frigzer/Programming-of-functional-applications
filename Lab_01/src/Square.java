public class Square extends Figure implements Printable {
    private double a;

    public Square(double a) {
        if (a <= 0) {
            throw new IllegalArgumentException("Niepoprawne dane dla kwadratu");
        }
        this.a = a;
    }

    @Override
    public double calculateArea() {
        return a * a;
    }

    @Override
    public double calculatePerimeter() {
        return 4 * a;
    }

    @Override
    public void print() {
        System.out.println("Kwadrat: bok = " + a +
                ", pole = " + calculateArea() + ", obwód = " + calculatePerimeter());
    }
}
