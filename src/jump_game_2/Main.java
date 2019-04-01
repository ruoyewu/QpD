package jump_game_2;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019-03-31 13:29
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int[] nums = new int[]{};
        System.out.println(jump(nums));
    }

    public static int jump(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int i = 0; i < n; i++) {
            int num = nums[i];
            for (int j = 1; j <= num && j + i < n; j++) {
                dp[i+j] = Math.min(dp[j+i], dp[i] + 1);
                if (i+j == n-1) return dp[n-1];
            }
        }

        return dp[n-1];
    }

    public static int jump2(int[] nums) {
        if (nums.length < 2) return 0;
        int step = 0, max = 0, i = 0, next = 0;

        while (true) {
            ++step;

            for (; i <= max; i++) {
                next = Math.max(next, i + nums[i]);
                if (next >= nums.length-1) return step;
            }
            max = next;
        }
    }
}
