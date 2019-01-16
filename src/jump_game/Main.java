package jump_game;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019/1/16 14:15
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static boolean canJump(int[] nums) {
        if (nums.length == 1) {
            return true;
        }
        return jump(nums, 0);
    }

    public static boolean jump(int[] nums, int i) {
        if (nums[i] == 0) {
            return false;
        }
        if (nums[i] != 0) {
            for (int j = 1; j <= nums[i]; j++) {
                if (i + j < nums.length-1) {
                    if (jump(nums, i+j)) {
                        return true;
                    }
                } else if (i + j >= nums.length-1) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean canJump2(int[] nums) {
        boolean[] reach = new boolean[nums.length];
        Arrays.fill(reach, false);
        reach[0] = true;
        for (int i = 1; i < nums.length; i++) {
            for (int j = i-1; j >= 0; j--) {
                if (reach[j] && j + nums[j] >= i) {
                    reach[i] = true;
                    break;
                }
            }
        }
        return reach[nums.length-1];
    }

    public static boolean canJump3(int[] nums) {
        int last = nums.length-1;
        for (int i = last; i >= 0; i--) {
            if (nums[i] + i >= last) {
                last = i;
            }
        }
        return last == 0;
    }
}
