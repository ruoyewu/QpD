package kth_largest_element_in_an_array;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019-01-30 16:06
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(findKthLargest3(new int[]{3,2,1,5,6,4}, 2));
    }

    public static int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length-k];
    }

    public static int findKthLargest2(int[] nums, int k) {
        for (int i = nums.length/2; i >= 0 ; i--) {
            heapAdjust(nums, i, nums.length-1);
        }
        for (int i = nums.length-1; ; i--) {
            if (i == nums.length-k) {
                return nums[0];
            }
            int tmp = nums[0];
            nums[0] = nums[i];
            nums[i] = tmp;
            heapAdjust(nums, 0, i-1);
        }
    }

    private static void heapAdjust(int[] nums, int start, int end) {
        int tmp = nums[start];
        for (int i = 2 * start + 1; i <= end; i = 2 * i + 1) {
            if (i < end && nums[i] < nums[i+1]) {
                i += 1;
            }
            if (tmp > nums[i]) break;
            nums[start] = nums[i];
            start = i;
        }
        nums[start] = tmp;
    }

    public static int findKthLargest3(int[] nums, int k) {
        return quickSort(nums, 0, nums.length-1, nums.length-k);
    }

    private static int quickSort(int[] nums, int start, int end, int k) {
        int p = partition(nums, start, end);
        if (p == k) return nums[p];
        return p > k ? quickSort(nums, start, p-1, k) : quickSort(nums, p+1, end, k);
    }

    private static int partition(int[] nums, int start, int end) {
        int tmp = nums[start];
        while (start < end) {
            while (start < end && nums[end] >= tmp) end--;
            nums[start] = nums[end];
            while (start < end && nums[start] <= tmp) start++;
            nums[end] = nums[start];
        }
        nums[start] = tmp;
        return start;
    }
}
