package matrix;

import exceptions.InvalidMatrixException;

public class MatrixTest {
    public MatrixTest() {
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

            for(int i = 0; i < matrix1.length; i++) {
                for (int j = 0; j < matrix1[0].length; j++) {
                    System.out.print(matrix1[i][j] + " ");
                }
                if ((i  == matrix1.length - i - 1) || (i  == matrix1.length - i))
                    System.out.print(" -> " + spiralMatrix.makeSpiral(matrix1));
                System.out.print("\n");
            }

            System.out.print("\n");

            int[][] matrix2 = {
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 16}
            };

            for(int i = 0; i < matrix2.length; i++) {
                for (int j = 0; j < matrix2[0].length; j++) {
                    System.out.print(matrix2[i][j] + " ");
                }
                if ((i  == matrix2.length - i - 1) || (i  == matrix2.length - i))
                    System.out.print(" -> " + spiralMatrix.makeSpiral(matrix2));
                System.out.print("\n");
            }
        } catch (InvalidMatrixException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }
}
