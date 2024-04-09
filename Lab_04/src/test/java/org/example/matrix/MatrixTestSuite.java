package org.example.matrix;

import org.example.exceptions.InvalidMatrixException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Nested
public class MatrixTestSuite {
    @Test
    void makeSpiralTest1() {
        SpiralMatrix spiralMatrix = new SpiralMatrix();

        try {
            int[][] matrix1 = {
                    {1, 2, 3},
                    {4, 5, 6},
                    {7, 8, 9},
                    {10, 11, 12},
                    {13, 14, 15},
                    {16, 17, 18}
            };


            assertEquals(List.of(1, 4, 7, 10, 13, 16, 17, 18, 15, 12, 9, 6, 3, 2, 5, 8, 11, 14), spiralMatrix.makeSpiral(matrix1));

        } catch (InvalidMatrixException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    @Test
    void makeSpiralTest2() {
        SpiralMatrix spiralMatrix = new SpiralMatrix();

        try {
            int[][] matrix2 = {
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 16}
            };

            assertEquals(List.of(1, 5, 9, 13, 14, 15, 16, 12, 8, 4, 3, 2, 6, 10, 11, 7), spiralMatrix.makeSpiral(matrix2));
        } catch (InvalidMatrixException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    @Test
    void testMatrixExceptionHandling() {
        SpiralMatrix spiralMatrix = new SpiralMatrix();
        int[][] matrix = {
                {}
        };
        assertThrows(InvalidMatrixException.class, () -> spiralMatrix.makeSpiral(matrix));
    }
}
