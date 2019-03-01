package shortest_unsorted_continuous_subarray;

import java.util.Arrays;
import java.util.Stack;

/**
 * User: wuruoye
 * Date: 2019-02-28 19:54
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int[] nums = new int[]{1,3,2,2,2};
        System.out.println(findUnsortedSubarray(nums));
        System.out.println(findUnsortedSubarray2(nums));
        System.out.println(findUnsortedSubarray3(nums));
        System.out.println(findUnsortedSubarray4(nums));
    }

    public static int findUnsortedSubarray(int[] nums) {
        int[] tmp = new int[nums.length];
        System.arraycopy(nums, 0, tmp, 0, nums.length);
        Arrays.sort(tmp);

        int start = 0, end = nums.length-1;
        while (start < nums.length && tmp[start] == nums[start]) start++;
        if (start == nums.length) return 0;
        while (end > start && tmp[end] == nums[end]) end--;

        return end-start+1;
    }

    public static int findUnsortedSubarray2(int[] nums) {
        int n = nums.length;
        Stack<Integer> stack = new Stack<>();

        stack.push(0);
        int start = n;
        for (int i = 1; i < n; i++) {
            int num = nums[i];
            if (stack.empty() || nums[stack.peek()] <= num) {
                stack.push(i);
            } else {
                while (!stack.empty() && nums[stack.peek()] > num) {
                    stack.pop();
                    start = stack.empty() ? 0 : Math.min(start, stack.peek()+1);
                }
                if (stack.empty()) break;
                stack.push(i);
            }
        }
        if (start == n) return 0;

        stack.clear();
        stack.push(n-1);
        int end = 0;
        for (int i = n-2; i >= 0; i--) {
            int num = nums[i];
            if (stack.empty() || nums[stack.peek()] >= num) {
                stack.push(i);
            } else {
                while (!stack.empty() && nums[stack.peek()] < num) {
                    stack.pop();
                    end = stack.empty() ? n : Math.max(end, stack.peek());
                }
                if (stack.empty()) break;
                stack.push(i);
            }
        }

        return end - start;
    }

    public static int findUnsortedSubarray3(int[] nums) {
        int pos = 1;
        while (pos < nums.length && nums[pos] >= nums[pos-1]) pos++;
        if (pos == nums.length) return 0;
        int start = pos-1, max = nums[pos-1];
        while (start >= 0 && nums[start] > nums[pos]) start--;

        int end = 0;
        while (pos < nums.length) {
            while (pos < nums.length && nums[pos] >= max) max = nums[pos++];
            if (pos < nums.length) {
                end = pos;
                while (start >= 0 && nums[start] > nums[end]) start--;
            }
            pos++;
        }

        return end - start;
    }

    public static int findUnsortedSubarray4(int[] nums) {
        int pos = 1;

        while (pos < nums.length && nums[pos] >= nums[pos-1]) pos++;
        if (pos == nums.length) return 0;
        int start = pos-1;
        while (pos < nums.length) {
            while (start >= 0 && nums[start] > nums[pos]) start--;
            if (start == -1) break;
            pos++;
        }

        pos = nums.length-2;
        while (pos > start && nums[pos] <= nums[pos+1]) pos--;
        int end = pos + 1;
        while (pos > start) {
            while (end < nums.length && nums[pos] > nums[end]) end++;
            if (end == nums.length) break;
            pos--;
        }

        return end - start - 1;
    }
}
