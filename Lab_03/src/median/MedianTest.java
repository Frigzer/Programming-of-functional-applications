package median;

import exceptions.ArrayNotSortedException;

import java.util.Arrays;

public class MedianTest {
    public MedianTest() {
        MedianCalculator medianFinder = new MedianCalculator();

        try {
            //int[] num1 = {1, 3};
            //int[] num2 = {2};
            int[] num1 = {1, 3, 6, 7};
            int[] num2 = {3, 4, 5};
            System.out.println(Arrays.toString(num1) + " + " + Arrays.toString(num2) + " -> " + medianFinder.findMedian(num1, num2));

            int[] num3 = {1, 2, 6};
            int[] num4 = {3, 4, 5, 9};
            System.out.println(Arrays.toString(num3) + " + " + Arrays.toString(num4) + " -> " + medianFinder.findMedian(num3, num4));
        } catch (ArrayNotSortedException e) {
            System.out.println("Błąd: " + e.getMessage());
        }

    }
}
