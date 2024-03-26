package trio;

import exceptions.EmptyArrayException;

import java.util.Arrays;

public class TrioTest {
    public TrioTest() {
        TrioFinder trio = new TrioFinder();

        try {
            //int[] nums1 = {-1, 0, 1, 2, -1, -4};
            int[] nums1 = {0, 4, -5, 1, 0, -1, 9, -4};
            System.out.println(Arrays.toString(nums1) + " -> " + trio.findTriplet(nums1));

            //int[] nums2 = {0, 1, 1};
            int[] nums2 = {-1, 1, -1, 1, 0, 0, 0};
            System.out.println("\n" + Arrays.toString(nums2) + " -> " + trio.findTriplet(nums2));

            int[] nums3 = {0, 0, 0};
            System.out.println("\n" + Arrays.toString(nums3) + " -> " + trio.findTriplet(nums3));
        } catch (EmptyArrayException e) {
            System.out.println("Błąd: " + e.getMessage());
        }

    }
}
