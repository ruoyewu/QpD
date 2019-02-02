package sliding_window_maximum;

import java.util.LinkedList;

/**
 * User: wuruoye
 * Date: 2019-02-01 16:41
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (k == 0) return new int[0];
        int n = nums.length, m = n - k + 1;
        int[] result = new int[m];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < k; i++) {
            if (max < nums[i]) max = nums[i];
        }
        result[0] = max;
        for (int i = k; i < n; i++) {
            if (nums[i] > max) {
                max = nums[i];
            } else if (nums[i-k] == max) {
                max = nums[i];
                for (int j = i-1; j > i-k; j--) {
                    if (nums[j] > max) max = nums[j];
                }
            }
            result[i-k+1] = max;
        }
        return result;
    }

    public static int[] maxSlidingWindow2(int[] nums, int k) {
        if (nums.length == 0 || k == 0) return new int[0];
        int n = nums.length, m = n - k + 1;
        int[] left = new int[n];
        int[] right = new int[n];

        left[0] = nums[0];
        right[n-1] = nums[n-1];
        for (int i = 1, j = n-2; i < n && j >= 0; i++, j--) {
            left[i] = (i % k == 0) ? nums[i] : Math.max(nums[i], left[i-1]);
            right[j] = (j % k == k-1) ? nums[j] : Math.max(nums[j], right[j+1]);
        }

        int[] result = new int[m];
        for (int i = 0; i < m; i++) {
            result[i] = Math.max(right[i], left[i+k-1]);
        }
        return result;
    }

    public static int[] maxSlidingWindow3(int[] nums, int k) {
        if (nums.length == 0 || k == 0) return new int[0];
        int n = nums.length, m = n-k+1;
        LinkedList<Integer> list = new LinkedList<>();
        int[] result = new int[m];

        int pos = 0;
        for (int i = 0; i < n; i++) {
            while (!list.isEmpty() && list.getFirst() <= i-k) list.removeFirst();
            while (!list.isEmpty() && nums[list.getLast()] < nums[i]) list.removeLast();
            list.add(i);
            if (i-k+1 >= 0) {
                result[pos++] = nums[list.getFirst()];
            }
        }
        return result;
    }
}
