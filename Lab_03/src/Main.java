import matrix.MatrixTest;
import sorting.SortingTest;
import conversion.ConversionTest;
import median.MedianTest;
import trio.TrioTest;

public class Main {
    public static void main(String[] args) {

        // Zad 1 - sorting
        new SortingTest();

        // Zad 2 - conversion
        new ConversionTest();

        // Zad 3 - median
        new MedianTest();

        // Zad 4 - trio
        new TrioTest();

        // Zad 5 - matrix
        new MatrixTest();

    }
}