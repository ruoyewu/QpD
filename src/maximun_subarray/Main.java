package maximun_subarray;

/**
 * User: wuruoye
 * Date: 2019/1/16 12:37
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int maxSubArray(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int[] sum = new int[n];
        int max = sum[0] = nums[0];
        for (int i = 1; i < n; i++) {
            if (sum[i-1] > 0) {
                sum[i] = nums[i] + sum[i-1];
            } else {
                sum[i] = nums[i];
            }
            if (sum[i] > max) {
                max = sum[i];
            }
        }
        return max;
    }

    public static int maxSubArray2(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int max = nums[0];
        int last = nums[0];
        for (int i = 1; i < n; i++) {
            int cur;
            if (last > 0) {
                cur = nums[i] + last;
            } else {
                cur = nums[i];
            }
            if (cur > max) {
                max = cur;
            }
            last = cur;
        }
        return max;
    }

    public static int maxSubArray3(int[] nums) {
        return max(nums, 0, nums.length-1);
    }

    public static int max(int[] nums, int start, int end) {
        if (start == end) {
            return nums[start];
        }

        int mid = (start + end) / 2;
        int left = max(nums, start, mid);
        int right = max(nums, mid+1, end);

        int maxL, sumL, maxR, sumR;
        maxL = sumL = nums[mid];
        maxR = sumR = nums[mid+1];
        for (int i = mid-1; i >= start; i--) {
            sumL += nums[i];
            maxL = Math.max(sumL, maxL);
        }

        for (int i = mid+2; i <= end; i++) {
            sumR += nums[i];
            maxR = Math.max(sumR, maxR);
        }

        return left > right ? Math.max(left, maxL + maxR) : Math.max(right, maxL + maxR);
    }
}
