package trio;

import exceptions.EmptyArrayException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrioFinder {
    public List<List<Integer>> findTriplet(int[] nums) throws EmptyArrayException {
        if(nums.length == 0) {
            throw  new EmptyArrayException("Pusta tablica");
        }
        Arrays.sort(nums);

        List<List<Integer>> result = new ArrayList<>();


        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || (i > 0 && nums[i] != nums[i - 1])) {
                int j = i + 1, k = nums.length - 1, sum = -nums[i];
                while (j < k) {
                    if (nums[j] + nums[k] == sum) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[k]));
                        while (j < k && nums[j] == nums[j + 1]) j++;
                        while (j < k && nums[k] == nums[k - 1]) k--;
                        j++;
                        k--;
                    } else if (nums[j] + nums[k] < sum) {
                        j++;
                    } else {
                        k--;
                    }
                }
            }
        }

        return result;
    }
}
