package subarray_sum_equals_k;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: wuruoye
 * Date: 2019-02-28 08:43
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int[] nums = new int[]{0,0,0,0,0};
        int k = 0;
        System.out.println(subarraySum(nums, k));
        System.out.println(subarraySum2(nums, k));
        System.out.println(subarraySum3(nums, k));
        System.out.println(subarraySum4(nums, k));
    }

    public static int subarraySum(int[] nums, int k) {
        int count = 0;
        int sum;
        for (int i = 0; i < nums.length; i++) {
            sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum == k) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int subarraySum2(int[] nums, int k) {
        int count = 0;
        ArrayList<Integer> list =  new ArrayList<>();
        ArrayList<Integer> tmp = new ArrayList<>();
        ArrayList<Integer> s;

        for (int num : nums) {
            if (num == k) count++;
            for (int n : list) {
                int r = n + num;
                if (r == k) count++;
                tmp.add(r);
            }
            tmp.add(num);
            list.clear();
            s = list;
            list = tmp;
            tmp = s;
        }
        return count;
    }

    public static int subarraySum3(int[] nums, int k) {
        int count = 0;
        int[] sums = new int[nums.length+1];
        sums[0] = 0;
        for (int i = 1; i <= nums.length; i++) {
            sums[i] = nums[i-1] + sums[i-1];
        }

        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j <= nums.length; j++) {
                if (sums[j] - sums[i] == k) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int subarraySum4(int[] nums, int k) {
        if (nums.length == 1) {
            return nums[0] == k ? 1 : 0;
        }
        for (int i = 1; i < nums.length; i++) {
            nums[i] += nums[i-1];
        }

        int count = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (num == k) {
                count++;
            }
            if (map.containsKey(num-k)) {
                count += map.get(num-k);
            }
            map.put(num, map.getOrDefault(num, 0) + 1);
            System.out.println(map);
        }
        return count;
    }
}
