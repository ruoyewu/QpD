package house_robber;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019-01-29 14:13
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(rob(new int[]{1,2,3,1}));
    }

    public static int rob(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        int[] robs = new int[nums.length];
        int pp = 0, p = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int m = Math.max(p, pp + nums[i]);
            pp = p;
            p = m;
        }

        return Math.max(pp, p);
    }

    public static int rob3(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        int[] robs = new int[nums.length];
        int ppp = 0, pp = nums[0], p = nums[1];

        for (int i = 2; i < nums.length; i++) {
            int m = Math.max(pp, ppp) + nums[i];
            ppp = pp;
            pp = p;
            p = m;
        }
        return Math.max(p, pp);
    }

    public static int rob2(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        int[] saved = new int[nums.length];
        Arrays.fill(saved, -1);
        saved[nums.length-1] = nums[nums.length-1];
        return Math.max(robber(nums, 0, saved), robber(nums, 1, saved));
    }

    public static int robber(int[] nums, int pos, int[] saved) {
        if (pos >= nums.length) return 0;
        if (saved[pos] >= 0) {
            return saved[pos];
        }
        int max = Math.max(nums[pos] + robber(nums, pos+2, saved), robber(nums, pos+1, saved));
        saved[pos] = max;
        return max;
    }
}
