package find_all_numbers_disappeared_in_an_array;

import java.util.ArrayList;
import java.util.List;

/**
 * User: wuruoye
 * Date: 2019-02-25 19:19
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> result = new ArrayList<>();
        boolean[] exit = new boolean[nums.length];
        for (int num : nums) {
            exit[num-1] = true;
        }
        for (int i = 0; i < exit.length; i++) {
            if (!exit[i]) {
                result.add(i+1);
            }
        }
        return result;
    }

    public static List<Integer> findDisappearedNumbers2(int[] nums) {
        List<Integer> result = new ArrayList<>();
        int n = nums.length;
        for (int num : nums) {
            nums[(num-1) % n] += n;
        }
        for (int i = 0; i < n; i++) {
            if (nums[i] < n) {
                result.add(i+1);
            }
        }
        return result;
    }
}
