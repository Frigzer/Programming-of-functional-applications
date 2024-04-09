package org.example.matrix;

import org.example.exceptions.InvalidMatrixException;

import java.util.ArrayList;
import java.util.List;

public class SpiralMatrix {
    public List<Integer> makeSpiral(int [][] matrix) throws InvalidMatrixException{
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            throw new InvalidMatrixException("Macierz jest pusta lub niepoprawna!");
        }

        int m = matrix.length;
        int n = matrix[0].length;
        int left = 0, right = n - 1, top = 0, bottom = m - 1;

        while (left <= right && top <= bottom) {
            for (int row = top; row <= bottom; row++) {
                result.add(matrix[row][left]);
            }
            left++;

            for (int col = left; col <= right; col++) {
                result.add(matrix[bottom][col]);
            }
            bottom--;

            if (left <= right) {
                for (int row = bottom; row  >= top; row--) {
                    result.add(matrix[row][right]);
                }
                right--;
            }

            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    result.add(matrix[top][col]);
                }
                top++;
            }
        }

        return result;
    }
}
