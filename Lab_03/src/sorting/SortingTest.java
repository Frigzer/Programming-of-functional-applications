package sorting;

import exceptions.EmptyArrayException;

import java.util.Arrays;
import java.util.Random;

public class SortingTest {
    public SortingTest() {
        Random random = new Random();

        try{
            int n = 10000;
            // Wersja pesymistyczna: posortowana w odwrotnej kolejności
            int[] arrPes = new int[n];

            // Wersja optymistyczna: już posortowana
            int[] arrOpt = new int[n];

            // Wersja oczekiwana: losowa
            int[] arrExp = new int[n];

            for (int i = 0; i < n; i++) {

                arrPes[i] = n - i;
                arrOpt[i] = i;
                arrExp[i] = random.nextInt(n);
            }

            SortingContext context = new SortingContext(new BubbleSort());
            System.out.println("Bubble Sort:");
            System.out.println("Pesymistyczny:");
            context.executeStrategy(Arrays.copyOf(arrPes, arrPes.length));
            System.out.println("Optymistyczny:");
            context.executeStrategy(Arrays.copyOf(arrOpt, arrOpt.length));
            System.out.println("Oczekiwany:");
            context.executeStrategy(Arrays.copyOf(arrExp, arrExp.length));

            context.setStrategy(new InsertionSort());
            System.out.println("\nInsertion Sort:");
            System.out.println("Pesymistyczny:");
            context.executeStrategy(Arrays.copyOf(arrPes, arrPes.length));
            System.out.println("Optymistyczny:");
            context.executeStrategy(Arrays.copyOf(arrOpt, arrOpt.length));
            System.out.println("Oczekiwany:");
            context.executeStrategy(Arrays.copyOf(arrExp, arrExp.length));

            context.setStrategy(new SelectionSort());
            System.out.println("\nSelection Sort:");
            System.out.println("Pesymistyczny:");
            context.executeStrategy(Arrays.copyOf(arrPes, arrPes.length));
            System.out.println("Optymistyczny:");
            context.executeStrategy(Arrays.copyOf(arrOpt, arrOpt.length));
            System.out.println("Oczekiwany:");
            context.executeStrategy(Arrays.copyOf(arrExp, arrExp.length));

            context.setStrategy(new QuickSort());
            System.out.println("\nQuick Sort:");
            System.out.println("Pesymistyczny:");
            context.executeStrategy(Arrays.copyOf(arrPes, arrPes.length));
            System.out.println("Optymistyczny:");
            context.executeStrategy(Arrays.copyOf(arrOpt, arrOpt.length));
            System.out.println("Oczekiwany:");
            context.executeStrategy(Arrays.copyOf(arrExp, arrExp.length));

            context.setStrategy(new MergeSort());
            System.out.println("\nMerge Sort:");
            System.out.println("Pesymistyczny:");
            context.executeStrategy(Arrays.copyOf(arrPes, arrPes.length));
            System.out.println("Optymistyczny:");
            context.executeStrategy(Arrays.copyOf(arrOpt, arrOpt.length));
            System.out.println("Oczekiwany:");
            context.executeStrategy(Arrays.copyOf(arrExp, arrExp.length));
        } catch (EmptyArrayException e) {
            System.out.println("Błąd: " + e.getMessage());
        }

    }
}
