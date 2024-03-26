package conversion;

import exceptions.InvalidNumOfRowsException;

public class ConversionTest {
    public ConversionTest() {
        ZigzagConversion converter = new ZigzagConversion();

        try {
            String s1 = "PAYPALISHIRING";
            int numRows1 = 3;
            System.out.println(s1 + " po konwersji dla " + numRows1 + " rzędów: " + converter.convert(s1, numRows1));

            String s2 = "PAYPALISHIRING";
            int numRows2 = 4;
            System.out.println(s2 + " po konwersji dla " + numRows2 + " rzędów: " + converter.convert(s2, numRows2));
        } catch (InvalidNumOfRowsException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

}
