package sort_colors;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019/1/18 23:40
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static void sortColors(int[] nums) {
        int n = nums.length;
        int red = 0, white = 0, blue = 0;
        for (int num : nums) {
            if (num == 0) {
                red++;
            } else if (num == 1) {
                white++;
            } else {
                blue++;
            }
        }

        Arrays.fill(nums, 0, red, 0);
        Arrays.fill(nums, red, red+white, 1);
        Arrays.fill(nums, red+white, n, 2);
    }

    public static void sortColors2(int[] nums) {
        int left = 0, mid = 0, right = nums.length-1;
        while (mid <= right) {
            if (nums[mid] == 0) {
                nums[mid] = nums[left];
                nums[left++] = 0;
                mid++;
            } else if (nums[mid] == 1) {
                mid++;
            } else if (nums[mid] == 2) {
                nums[mid] = nums[right];
                nums[right--] = 2;
            }
        }
    }
}
