package org.example.median;

import org.example.exceptions.ArrayNotSortedException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Nested
public class MedianTestSuite {

    @Test
    void testFindMedian1() {
        MedianCalculator medianFinder = new MedianCalculator();
        try {
            int[] num1 = {1, 3, 6, 7};
            int[] num2 = {3, 4, 5};

            assertEquals(4.0, medianFinder.findMedian(num1, num2));
        } catch (ArrayNotSortedException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    @Test
    void testFindMedian2() {
        MedianCalculator medianFinder = new MedianCalculator();
        try {
            int[] num3 = {1, 2, 6};
            int[] num4 = {3, 4, 5, 9};

            assertEquals(4.0, medianFinder.findMedian(num3, num4));
        } catch (ArrayNotSortedException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    @Test
    void testFindMedian3() {
        MedianCalculator medianFinder = new MedianCalculator();
        try {
            int[] num3 = {1, 2, 6, 9};
            int[] num4 = {3, 4, 5, 9, 11};

            assertEquals(5.0, medianFinder.findMedian(num3, num4));
        } catch (ArrayNotSortedException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    @Test
    void testFindMedian4() {
        MedianCalculator medianFinder = new MedianCalculator();
        try {
            int[] num3 = {1, 2, 6, 9};
            int[] num4 = {3, 4, 5, 9, 11, 17};

            assertEquals(5.5, medianFinder.findMedian(num3, num4));
        } catch (ArrayNotSortedException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    @Test
    void testFindMedian5() {
        MedianCalculator medianFinder = new MedianCalculator();
        try {
            int[] num3 = {6, 9, 10};
            int[] num4 = {1, 2};

            assertEquals(6.0, medianFinder.findMedian(num3, num4));
        } catch (ArrayNotSortedException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    @Test
    void testFindMedian6() {
        MedianCalculator medianFinder = new MedianCalculator();
        try {
            int[] num3 = {1, 3, 5};
            int[] num4 = {2, 4, 6};

            assertEquals(3.5, medianFinder.findMedian(num3, num4));
        } catch (ArrayNotSortedException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    @Test
    void testMedianExceptionHandling() {
        MedianCalculator medianFinder = new MedianCalculator();
        int[] num1 = {4, 6, 1};
        int[] num2 = {8, 5};
        assertThrows(ArrayNotSortedException.class, () -> medianFinder.findMedian(num1, num2));
    }

}
