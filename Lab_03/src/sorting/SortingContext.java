package sorting;

import exceptions.EmptyArrayException;

public class SortingContext {
    private SortingStrategy strategy;

    public SortingContext(SortingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SortingStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy(int[] arr) throws EmptyArrayException {
        if(arr.length == 0) {
            new EmptyArrayException("Pusta tablica");
        }
        long tStart = System.nanoTime();
        strategy.sort(arr);
        long tEnd = System.nanoTime();
        long duration = (tEnd - tStart) / 1000000;

        System.out.println("Czas sortowania: " + duration + " ms");
    }
}
