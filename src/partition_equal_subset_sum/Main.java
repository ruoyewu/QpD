package partition_equal_subset_sum;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019-02-25 10:33
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int[] nums = new int[]{1,1,2,5,5,5,5};
        System.out.println(canPartition(nums));
    }

    public static boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) sum+= num;
        if ((sum & 1) == 1) return false;
        sum = sum >> 1;
        Arrays.sort(nums);
        return partition2(nums, nums.length-1, sum);
    }

    private static boolean partition(int[] nums, int pos, int sum) {
        if (sum == 0) return true;
        if (sum > 0 && pos >= 0) {
            for (int i = pos; i >= 0 ; i--) {
                if (sum - nums[i] < 0) {
                    break;
                }
                if ((i == 0 || i == pos || nums[i] != nums[i-1])
                        && partition(nums, i-1, sum-nums[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean partition2(int[] nums, int pos, int sum) {
        if (sum == 0) return true;
        if (sum < 0 || pos == -1) return false;
        if (partition2(nums, pos-1, sum-nums[pos])) return true;
        int j = pos-1;
        while (j >= 0 && nums[j] == nums[pos]) j--;
        return partition2(nums, j, sum);
    }
}
