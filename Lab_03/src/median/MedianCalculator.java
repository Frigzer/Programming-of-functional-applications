package median;

import java.util.Arrays;
import exceptions.ArrayNotSortedException;

public class MedianCalculator {
    public double findMedian(int[] num1, int[] num2) throws ArrayNotSortedException{
        if(!isSorted(num1) || !isSorted(num2)) {
            throw new ArrayNotSortedException("Tablice nie są posortowane");
        }

        int m = num1.length;
        int n = num2.length;

        if (m > n) {
            int[] temp = num1;
            num1 = num2;
            num2 = temp;

            int tmp = m;
            m = n;
            n = tmp;
        }

        int start = 0, end = m;

        while(start <= end) {
            int part1 = (start + end) / 2;
            int part2 = (m + n + 1) / 2 - part1;

            int maxLeftNum1 = part1 == 0?Integer.MIN_VALUE:num1[part1 - 1];
            int maxLeftNum2 = part2 == 0?Integer.MIN_VALUE:num2[part2 - 1];
            int minRightNum1 = part1 == m?Integer.MAX_VALUE:num1[part1];
            int minRightNum2 = part2 == n?Integer.MAX_VALUE:num2[part2];

            if(maxLeftNum1 <= minRightNum2 && maxLeftNum2 <= minRightNum1) {
                if((m + n) % 2 == 0) {
                    return (Math.max(maxLeftNum1, maxLeftNum2) + Math.min(minRightNum1, minRightNum2)) / 2.0;
                }
                else {
                    return Math.max(maxLeftNum1, maxLeftNum2);
                }
            } else if (maxLeftNum1 > minRightNum2) {
                end = part1 - 1;
            }
            else {
                start = part1 + 1;
            }
        }
        return 0;

    }

    private boolean isSorted(int[] num) {
        for (int i = 1; i < num.length; i++) {
            if (num[i] < num[i - 1]) {
                return false;
            }
        }
        return true;
    }
}
