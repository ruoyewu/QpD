package burst_balloons;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * User: wuruoye
 * Date: 2019-02-20 08:39
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5};
        int m1 = maxCoins(nums);
        int m2 = maxCoins2(nums);
        int m3 = maxCoins3(nums);
//        System.out.println(m1);
        System.out.println(m2);
        System.out.println(m3);
        System.out.println(m2-m3);
    }

    public static int maxCoins4(int[] nums) {
        if (nums.length == 0) return 0;
        Stack<Integer> stack = new Stack<>();
        int i = 0;
        stack.push(nums[i++]);
        int sum = 0;
        while (i < nums.length) {
            int num = nums[i++];
            while (stack.size() > 1 && num >= stack.peek()) {
                int top = stack.pop();
                if (top < stack.peek()) {
                    sum += stack.peek() * top * num;
                } else {
                    stack.push(top);
                    break;
                }
            }
            stack.push(num);
        }

        int len = stack.size() + 2;
        nums = new int[len];
        for (int j = 1; j < len-1; j++) {
            nums[j] = stack.get(j-1);
        }
        nums[0] = nums[len-1] = 1;
        sum += max(nums, 1, len-2, new int[len][len]);
        return sum;
    }

    public static int maxCoins(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int num : nums) list.add(num);
        int sum = 0;
        while (list.size() > 2) {
            int min = 1;
            for (int i = 2; i < list.size()-1; i++) {
                if (list.get(i) < list.get(min)) {
                    min = i;
                }
            }

            sum += list.get(min-1) * list.get(min) * list.get(min+1);
            list.remove(min);
        }
        if (list.size() == 1) {
            sum += list.get(0);
        } else if (list.size() == 2){
            sum += list.get(0) * list.get(1);
            sum += Math.max(list.get(0), list.get(1));
        }
        return sum;
    }

    public static int maxCoins2(int[] nums) {
        int[] newNums = new int[nums.length+2];
        System.arraycopy(nums, 0, newNums, 1, nums.length);
        newNums[0] = newNums[nums.length+1] = 1;
        int[][] saved = new int[nums.length+2][nums.length+2];
        return max(newNums, 1, nums.length, saved);
    }

    private static int max(int[] nums, int start, int end, int[][] saved) {
        if (start > end) return 0;
        if (saved[start][end] != 0) return saved[start][end];

        int max = 0;
        for (int i = start; i <= end; i++) {
            int m = max(nums, start, i-1, saved) +
                    nums[start-1] * nums[i] * nums[end+1] +
                    max(nums, i+1, end, saved);
            max = Math.max(max, m);
        }
        saved[start][end] = max;
        return max;
    }

    public static int maxCoins3(int[] nums) {
        int[] newNums = new int[nums.length+2];
        System.arraycopy(nums, 0, newNums, 1, nums.length);
        newNums[0] = newNums[nums.length+1] = 1;
        nums = newNums;
        int len = nums.length;

        int[][] dp = new int[len][len];
//        for (int left = len-1; left >= 0; left--) {
//            for (int right = left+1; right < len; right++) {
//                int sub = nums[left] * nums[right];
//                for (int k = left+1; k < right; k++) {
//                    dp[left][right] = Math.max(dp[left][right],
//                            sub * nums[k] + dp[left][k] + dp[k][right]);
//                }
//            }
//        }

        for (int right = 0; right < len; right++) {
            for (int left = right-1; left >= 0; left--) {
                int sub = nums[left] * nums[right];
                for (int k = left+1; k < right; k++) {
                    dp[left][right] = Math.max(dp[left][right],
                            sub * nums[k] + dp[left][k] + dp[k][right]);
                }
            }
        }
        return dp[0][len-1];
    }
}
