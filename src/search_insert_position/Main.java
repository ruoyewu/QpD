package search_insert_position;

/**
 * User: wuruoye
 * Date: 2019-03-17 08:13
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int searchInsert(int[] nums, int target) {
        int left = 0, right = nums.length-1, mid;
        while (left <= right) {
            mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                right = mid-1;
            } else {
                left = mid+1;
            }
        }

        return left;
    }

    public static int searchInsert2(int[] nums, int target) {
        int pos = 0;
        while (pos < nums.length && nums[pos] < target) pos++;
        return pos;
    }
}
