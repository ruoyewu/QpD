package three_sum;

import java.util.*;

/**
 * User: wuruoye
 * Date: 2018/7/5 16:44
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }

        List<List<Integer>> res = threeSum2(nums);
        threeSum(nums);

        for (List<Integer> l : res) {
            for (int i : l) {
                System.out.print(i + " ");
            }
            System.out.println("");
        }
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new LinkedList<>();
        HashSet<Integer> seen = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            int target = -nums[i];
            if (seen.contains(target)) continue;

            for (int left = i+1, right = nums.length-1; left < right && left < nums.length; ) {
                int res = nums[left] + nums[right];
                if (res == target) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (nums[left] == nums[++left] && nums[right] == nums[--right] && left < right) ;
                } else {
                    if (res > target) {
                        right--;
                    } else {
                        left++;
                    }
                }
            }
            seen.add(target);
        }

        return result;
    }

    public static List<List<Integer>> threeSum2(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        HashSet<Integer> seen = new HashSet<>();    //去重辅助集合

        //找到第一个非负数
        int mid = 0;
        while (mid < nums.length && nums[mid] < 0) {
            mid++;
        }

        //特殊情况判断 3零
        if (mid+2 < nums.length && nums[mid] == 0) {
            if (nums[mid] == nums[mid+1] && nums[mid+1] == nums[mid+2] && nums[mid+2] == 0) {
                result.add(Arrays.asList(0, 0, 0));
            }
        }

        for (int i = 0; i < nums.length; i++) {
            int target, left, right;
            target = -nums[i];
            if (seen.contains(target)) continue;    //去重

            //区分负数部分和非负数部分
            if (i < mid) {
                left = mid;
                right = nums.length-1;
            } else {
                left = 0;
                right = mid-1;
            }

            for ( ; left < right ; ) {
                int res = nums[left] + nums[right];
                if (res == target) {
                    result.add(Arrays.asList(-target, nums[left], nums[right]));
                    while (nums[left] == nums[++left] && nums[right] == nums[--right] && left < right) ;    //去重
                } else if (res > target) {
                    right--;
                } else {
                    left++;
                }
            }

            seen.add(target);
        }

        return result;
    }
}
