package four_sum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: wuruoye
 * Date: 2019-03-14 15:27
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int[] nums = new int[]{-5, -4, -2, -2, -2, -1, 0, 0, 1};
        int target = -9;
        System.out.println(fourSum(nums, target));
    }

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        if (nums.length < 4) return result;
        for (int i = 0; i < nums.length - 3; i++) {
            if (nums[i] + nums[i+1] + nums[i+2] + nums[i+3] > target) break;
            if (nums[i] + nums[nums.length-1] + nums[nums.length-2]
                    + nums[nums.length-3] < target) continue;
            if (i > 0 && nums[i] == nums[i-1]) continue;
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (nums[i] + nums[j] + nums[j+1] + nums[j+2] > target) break;
                if (nums[i] + nums[j] + nums[nums.length-1]
                        + nums[nums.length-2] < target) continue;
                if (j > i+1 && nums[j] == nums[j-1]) continue;
                int left = j + 1, right = nums.length-1;
                while (left < right) {
                    int sum = nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum == target) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        while (left < right && nums[right--] == nums[right]) ;
                        while (left < right && nums[left++] == nums[left]) ;
                    } else if (sum > target) {
                        while (left < right && nums[right--] == nums[right]) ;
                    } else {
                        while (left < right && nums[left++] == nums[left]) ;
                    }
                }
            }
        }
        return result;
    }
}
