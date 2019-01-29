package majority_element;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * User: wuruoye
 * Date: 2019-01-29 12:13
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(majorityElement2(new int[]{3,2,3}));
    }

    public static int majorityElement(int[] nums) {
        int majority = nums[0];
        int cnt = 1;
        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            if (majority == num) {
                cnt++;
            } else {
                cnt--;
            }
            if (cnt < 0) {
                cnt = 1;
                majority = num;
            }
        }
        return majority;
    }

    public static int majorityElement2(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer num = map.get(nums[i]);
            map.put(nums[i], num == null ? 1 : num+1);
        }
        int major = 0, num = Integer.MIN_VALUE;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > num) {
                num = entry.getValue();
                major = entry.getKey();
            }
        }
        return major;
    }

    public static int majorityElement3(int[] nums) {
        Arrays.sort(nums);
        int major = nums[0], len = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == major) {
                len++;
            }else {
                if (len > nums.length/2) {
                    return major;
                } else {
                    major = nums[i];
                    len = 1;
                }
            }
        }
        return major;
    }

    public static int majorityElement4(int[] nums) {
        return majorityElementRange(nums, 0, nums.length-1);
    }

    private static int majorityElementRange(int[] nums, int start, int end) {
        if (start == end) {
            return nums[start];
        }
        int mid = (start + end) / 2;
        int left = majorityElementRange(nums, start, mid);
        int right = majorityElementRange(nums, mid+1, end);
        if (left == right) {
            return left;
        }
        return countElement(nums, start, mid, left) >
                countElement(nums, mid+1, end, right) ? left : right;
    }

    private static int countElement(int[] nums, int start, int end, int num) {
        int cnt = 0;
        for (int i = start; i <= end; i++) {
            if (nums[i] == num) {
                cnt++;
            }
        }
        return cnt;
    }
}
