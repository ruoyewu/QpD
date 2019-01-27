package single_number;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * User: wuruoye
 * Date: 2019/1/27 17:45
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int singleNumber(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i += 2) {
            if (nums[i] != nums[i-1]) {
                return nums[i-1];
            }
        }
        return nums[nums.length-1];
    }

    public static int singleNumber2(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (set.contains(num)) {
                set.remove(num);
            } else {
                set.add(num);
            }
        }
        return set.iterator().next();
    }

    public static int singleNumber3(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int sum = 0;
        for (int n : nums) {
            set.add(n);
            sum += n;
        }

        for (Integer i : set) {
            sum -= i * 2;
        }
        return -sum;
    }

    public static int singleNumber4(int[] nums) {
        int result = 0;
        for (int n : nums) {
            result ^= n;
        }
        return result;
    }
}
