package org.example.sorting;

import org.example.exceptions.EmptyArrayException;

public class SortingContext {
    public SortingStrategy strategy;

    public SortingContext(SortingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SortingStrategy strategy) {
        this.strategy = strategy;
    }

    public long executeStrategy(int[] arr) throws EmptyArrayException {
        if(arr.length == 0) {
            throw new EmptyArrayException("Pusta tablica");
        }
        long tStart = System.nanoTime();
        strategy.sort(arr);
        long tEnd = System.nanoTime();
        long duration = (tEnd - tStart) / 1000000;

        return duration;
    }
}
