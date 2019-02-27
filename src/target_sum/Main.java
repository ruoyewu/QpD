package target_sum;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019-02-26 20:13
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int[] nums = new int[]{0,0,0,0,0};
        int S = 0;
        System.out.println(findTargetSumWays3(nums, S));
        System.out.println(findTargetSumWays2(nums, S));
    }

    public static int findTargetSumWays(int[] nums, int S) {
        return ways(nums, S, 0, 0);
    }

    private static int ways(int[] nums, int S, int pos, int cur) {
        if (pos == nums.length) {
            return cur == S ? 1 : 0;
        } else {
            return ways(nums, S, pos+1, cur+nums[pos])
                    + ways(nums, S, pos+1, cur-nums[pos]);
        }
    }

    public static int findTargetSumWays2(int[] nums, int S) {
        int[][] saved = new int[nums.length][2001];
        for (int[] row : saved) {
            Arrays.fill(row, -1);
        }
        return ways2(nums, S, 0, 0, saved);
    }

    private static int ways2(int[] nums, int S, int pos, int cur, int[][] saved) {
        if (pos == nums.length) {
            return cur == S ? 1 : 0;
        } else {
            if (saved[pos][cur+1000] >= 0) return saved[pos][cur+1000];
            int ways = ways2(nums, S, pos+1, cur+nums[pos], saved)
                    + ways2(nums, S, pos+1, cur-nums[pos], saved);
            saved[pos][cur+1000] = ways;
            return ways;
        }
    }

    public static int findTargetSumWays3(int[] nums, int S) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum < S || ((S + sum) & 1) == 1) return 0;

        int[] dp = new int[(sum<<1) + 1];
        dp[nums[0] + sum] = 1;
        dp[-nums[0] + sum] += 1;
        for (int i = 1; i < nums.length; i++) {
            int[] next = new int[(sum<<1) + 1];
            for (int j = -sum; j <= sum; j++) {
                if (dp[j + sum] > 0) {
                    next[j + nums[i] + sum] += dp[j + sum];
                    next[j - nums[i] + sum] += dp[j + sum];
                }
            }
            dp = next;
        }
        return S > sum ? 0 : dp[S + sum];
    }

    public static int findTargetSumWays4(int[] nums, int S) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum < S || ((sum + S) & 1) == 1) return 0;

        sum = (sum + S) >> 1;
        int[] dp = new int[sum+1];
        dp[0] = 1;
        for (int num : nums) {
            for (int i = sum; i >= num; i--) {
                dp[i] += dp[i-num];
            }
        }
        return dp[sum];
    }
}
