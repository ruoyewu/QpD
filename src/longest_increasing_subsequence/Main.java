package longest_increasing_subsequence;

/**
 * User: wuruoye
 * Date: 2019-02-02 19:06
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(lengthOfLIS2(new int[]{3,5,6,2,5,4,19,5,6,7,12}));
    }

    public static int lengthOfLIS(int[] nums) {
        if (nums.length == 0) return 0;
        int[] length = new int[nums.length];
        length[0] = 1;
        int result = 1;
        for (int i = 1; i < nums.length; i++) {
            int max = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] > nums[i]) {
                    max = Math.max(max, 1+length[j]);
                }
            }
            length[i] = max;
            result = Math.max(result, max);
        }
        return result;
    }

    public static int lengthOfLIS3(int[] nums) {
        if (nums.length == 0) return 0;
        int[] length = new int[nums.length];
        length[0] = nums[0];
        int pos = 1;
        for (int i = 1; i < nums.length; i++) {
            int j = pos;
            while (j > 0 && length[j-1] >= nums[i]) j--;
            length[j] = nums[i];
            if (j == pos) pos++;
        }
        return pos;
    }

    public static int lengthOfLIS2(int[] nums) {
        if (nums.length == 0) return 0;
        int[] length = new int[nums.length];
        length[0] = nums[0];
        int len = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < length[0]) {
                length[0] = nums[i];
            } else if (nums[i] > length[len-1]) {
                length[len++] = nums[i];
            } else {
                int index = binarySearch(length, 0, len-1, nums[i]);
                length[index] = nums[i];
            }
        }
        return len;
    }

    private static int binarySearch(int[] nums, int start, int end, int target) {
        while (start < end) {
            int mid = (start + end) / 2;
            if (nums[mid] > target) {
                end = mid;
            } else if (nums[mid] < target) {
                start = mid+1;
            } else {
                return mid;
            }
        }
        return end;
    }
}
