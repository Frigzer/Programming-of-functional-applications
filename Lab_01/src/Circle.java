public class Circle extends Figure implements Printable {
    private double r;

    public Circle(double r) {
        if (r <= 0) {
            throw new IllegalArgumentException("Niepoprawne dane dla koła");
        }
        this.r = r;
    }

    @Override
    double calculateArea() {
        return Math.PI * r * r;
    }

    @Override
    double calculatePerimeter() {
        return 2 * Math.PI * r;
    }

    @Override
    public void print() {
        System.out.println("Koło: promień = " + r +
                ", pole = " + calculateArea() + ", obwód = " + calculatePerimeter());
    }
}
