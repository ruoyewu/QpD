package three_sum_closest;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019-03-13 21:35
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int closest = Integer.MAX_VALUE, min, symbol = 1;
        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || nums[i] != nums[i-1]) {
                int left = i+1, right = nums.length-1;
                while (left < right) {
                    min = nums[left] + nums[right] + nums[i] - target;
                    if (min == 0) return target;
                    if (min > 0) {
                        if (min < closest) {
                            closest = min;
                            symbol = 1;
                        }
                        right--;
                    } else {
                        if (-min < closest) {
                            closest = -min;
                            symbol = -1;
                        }
                        left++;
                    }
                }
            }
        }
        return closest * symbol + target;
    }
}
