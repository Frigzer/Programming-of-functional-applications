package org.example.sorting;

import org.example.exceptions.EmptyArrayException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@Nested
public class SortingTestSuite {

    @Test
    void testBubbleSort() {
        int[] sortedArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] arr = {7, 9, 8, 1, 5, 4, 6, 3, 2, 10};

        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.sort(arr);

        assertEquals(Arrays.toString(sortedArr), Arrays.toString(arr));
    }

    @Test
    void testInsertionSort() {
        int[] sortedArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] arr = {7, 9, 8, 1, 5, 4, 6, 3, 2, 10};

        InsertionSort insertionSort = new InsertionSort();
        insertionSort.sort(arr);

        assertEquals(Arrays.toString(sortedArr), Arrays.toString(arr));
    }

    @Test
    void testMergeSort() {
        int[] sortedArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] arr = {7, 9, 8, 1, 5, 4, 6, 3, 2, 10};

        MergeSort mergeSort = new MergeSort();
        mergeSort.sort(arr);

        assertEquals(Arrays.toString(sortedArr), Arrays.toString(arr));
    }

    @Test
    void testQuickSort() {
        int[] sortedArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] arr = {7, 9, 8, 1, 5, 4, 6, 3, 2, 10};

        QuickSort quickSort = new QuickSort();
        quickSort.sort(arr);

        assertEquals(Arrays.toString(sortedArr), Arrays.toString(arr));
    }

    @Test
    void testSelectionSort() {
        int[] sortedArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] arr = {7, 9, 8, 1, 5, 4, 6, 3, 2, 10};

        SelectionSort selectionSort = new SelectionSort();
        selectionSort.sort(arr);

        assertEquals(Arrays.toString(sortedArr), Arrays.toString(arr));
    }

    @Test
    void testBubbleSortExecutionTime() {
        Random random = new Random();

        try {
            int n = 10000;

            int[] arr = new int[n];

            for (int i = 0; i < n; i++) {

                arr[i] = random.nextInt(n);
            }

            SortingContext context = new SortingContext(new BubbleSort());

            assertTrue(context.executeStrategy(Arrays.copyOf(arr, arr.length)) < 180);
        } catch (EmptyArrayException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    @Test
    void testInsertionSortExecutionTime() {
        Random random = new Random();

        try {
            int n = 10000;

            int[] arr = new int[n];

            for (int i = 0; i < n; i++) {

                arr[i] = random.nextInt(n);
            }

            SortingContext context = new SortingContext(new InsertionSort());

            assertTrue(context.executeStrategy(Arrays.copyOf(arr, arr.length)) < 100);
        } catch (EmptyArrayException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    @Test
    void testMergeSortExecutionTime() {
        Random random = new Random();

        try {
            int n = 10000;

            int[] arr = new int[n];

            for (int i = 0; i < n; i++) {

                arr[i] = random.nextInt(n);
            }

            SortingContext context = new SortingContext(new MergeSort());

            assertTrue(context.executeStrategy(Arrays.copyOf(arr, arr.length)) < 40);
        } catch (EmptyArrayException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    @Test
    void testQuickSortExecutionTime() {
        Random random = new Random();

        try {
            int n = 10000;

            int[] arr = new int[n];

            for (int i = 0; i < n; i++) {

                arr[i] = random.nextInt(n);
            }

            SortingContext context = new SortingContext(new QuickSort());

            assertTrue(context.executeStrategy(Arrays.copyOf(arr, arr.length)) < 10);
        } catch (EmptyArrayException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    @Test
    void testSelectionSortExecutionTime() {
        Random random = new Random();

        try {
            int n = 10000;

            int[] arr = new int[n];

            for (int i = 0; i < n; i++) {

                arr[i] = random.nextInt(n);
            }

            SortingContext context = new SortingContext(new SelectionSort());

            assertTrue(context.executeStrategy(Arrays.copyOf(arr, arr.length)) < 80);
        } catch (EmptyArrayException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    @Test
    void testSortingExceptionHandling() {
        SortingContext context = new SortingContext(new SelectionSort());
        context.setStrategy(new MergeSort());
        int[] arr = {};
        assertThrows(EmptyArrayException.class, () -> context.executeStrategy(Arrays.copyOf(arr, arr.length)));
    }
}
