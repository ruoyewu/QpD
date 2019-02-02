package find_the_duplicate_number;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019-02-02 17:08
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int findDuplicate(int[] nums) {
        int slow = nums[0], fast = nums[0];
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        int head = nums[0];
        while (head != slow) {
            head = nums[head];
            slow = nums[slow];
        }
        return head;
    }

    public static int findDuplicate2(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i-1]) {
                return nums[i];
            }
        }
        return 0;
    }
}
