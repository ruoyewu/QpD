package search_in_rotated_sorted_array_2;

/**
 * User: wuruoye
 * Date: 2019-05-04 21:45
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int[] nums = new int[] {2, 2, 2, 0, 2, 2};
        int target = 0;
        System.out.println(search(nums, target));
    }

    public static boolean search(int[] nums, int target) {
        if (nums == null || nums.length == 0) return false;
        return search(nums, target, 0, nums.length-1);
    }

    private static boolean search(int[] nums, int target, int start, int end) {
        if (start >= end) {
            return nums[start] == target;
        }

        int mid = (start + end) / 2;
        int num = nums[mid];
        if (num > target) {
            if (num > nums[start]) {
                return search(nums, target, start, mid-1) || search(nums, target, mid+1, end);
            } else if (num == nums[start]) {
                return search(nums, target, mid+1, end);
            } else {
                return search(nums, target, start, mid-1);
            }
        } else if (num < target) {
            if (num > nums[start]) {
                return search(nums, target, mid+1, end);
            } else {
                return search(nums, target, mid+1, end) || search(nums, target, start, mid-1);
            }
        } else {
            return true;
        }
    }
}
