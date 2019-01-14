package next_permutation;

/**
 * User: wuruoye
 * Date: 2019/1/14 08:42
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void nextPermutation(int[] nums) {
        int i = nums.length-1;
        while (i >= 1 && nums[i-1] >= nums[i])
            i--;
        if (i >= 1) {
            int j = nums.length-1;
            while (j >= 0 && nums[j] <= nums[i-1])
                j--;
            swap(nums, i-1, j);
        }

        int j = nums.length-1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }
}
