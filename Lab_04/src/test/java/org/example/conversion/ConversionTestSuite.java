package org.example.conversion;

import org.example.exceptions.InvalidNumOfRowsException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Nested
public class ConversionTestSuite {

    @Test
    void convertTest1() {
        ZigzagConversion converter = new ZigzagConversion();


        try {
            String s1 = "PAYPALISHIRING";
            int numRows1 = 3;

            assertEquals("PAHNAPLSIIGYIR", converter.convert(s1, numRows1));
        } catch (InvalidNumOfRowsException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    @Test
    void convertTest2() {
        ZigzagConversion converter = new ZigzagConversion();

        try {
            String s2 = "PAYPALISHIRING";
            int numRows2 = 4;

            assertEquals("PINALSIGYAHRPI", converter.convert(s2, numRows2));
        } catch (InvalidNumOfRowsException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    @Test
    void testConversionExceptionHandling1() {
        ZigzagConversion converter = new ZigzagConversion();
        String s = "AUILKPOYDVS";
        int numRows = 0;
        assertThrows(InvalidNumOfRowsException.class, () -> converter.convert(s, numRows));
    }

    @Test
    void testConversionExceptionHandling2() {
        ZigzagConversion converter = new ZigzagConversion();
        String s = "KDHBMRUFHSN";
        int numRows = 1;
        assertThrows(InvalidNumOfRowsException.class, () -> converter.convert(s, numRows));
    }

    @Test
    void testConversionExceptionHandling3() {
        ZigzagConversion converter = new ZigzagConversion();
        String s = "AG";
        int numRows = 2;
        assertThrows(InvalidNumOfRowsException.class, () -> converter.convert(s, numRows));
    }

    @Test
    void testConversionExceptionHandling4() {
        ZigzagConversion converter = new ZigzagConversion();
        String s = "ABGR";
        int numRows = 6;
        assertThrows(InvalidNumOfRowsException.class, () -> converter.convert(s, numRows));
    }
}
