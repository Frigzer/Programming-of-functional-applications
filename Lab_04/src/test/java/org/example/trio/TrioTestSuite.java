package org.example.trio;

import org.example.exceptions.EmptyArrayException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Nested
public class TrioTestSuite {

    @Test
    void testTrioFinder1() {
        TrioFinder trio = new TrioFinder();

        try {
            int[] nums1 = {0, 4, -5, 1, 0, -1, 9, -4};
            assertEquals(List.of(List.of(-5, -4, 9), List.of(-5, 1, 4), List.of(-4, 0, 4), List.of(-1, 0, 1)), trio.findTriplet(nums1));
        } catch (EmptyArrayException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    @Test
    void testTrioFinder2() {
        TrioFinder trio = new TrioFinder();

        try {
            int[] nums2 = {-1, 1, -1, 1, 0, 0, 0};
            assertEquals(List.of(List.of(-1, 0, 1), List.of(0, 0, 0)), trio.findTriplet(nums2));
        } catch (EmptyArrayException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    @Test
    void testTrioFinder3() {
        TrioFinder trio = new TrioFinder();

        try {
            int[] nums3 = {0, 0, 0};
            assertEquals(List.of(List.of(0, 0, 0)), trio.findTriplet(nums3));
        } catch (EmptyArrayException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    @Test
    void testTrioExceptionHandling() {
        TrioFinder trio = new TrioFinder();
        int[] nums = {};
        assertThrows(EmptyArrayException.class, () -> trio.findTriplet(nums));
    }
}
