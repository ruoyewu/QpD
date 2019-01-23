package subsets;

import java.util.ArrayList;
import java.util.List;

/**
 * User: wuruoye
 * Date: 2019/1/21 20:36
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i <= nums.length; i++) {
            sub(result, new ArrayList<>(), i, 0, nums);
        }
        return result;
    }

    public static void sub(List<List<Integer>> result, List<Integer> saved, int left, int i, int[] nums) {
        if (left == 0) {
            result.add(new ArrayList<>(saved));
        } else if (left > 0) {
            if (nums.length - i < left) {
                return;
            }
            if (nums.length - i == left) {
                List<Integer> cur = new ArrayList<>(saved);
                for (int j = i; j < nums.length; j++) {
                    cur.add(nums[j]);
                }
                result.add(cur);
                return;
            }
            sub(result, saved, left, i + 1, nums);
            saved.add(nums[i]);
            sub(result, saved, left - 1, i + 1, nums);
            saved.remove(saved.size() - 1);

        }
    }

    public static List<List<Integer>> subsets2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        sub2(result, new ArrayList<>(), 0, nums);
        return result;
    }

    public static void sub2(List<List<Integer>> result, List<Integer> cur, int start, int[] nums) {
        result.add(new ArrayList<>(cur));

        for (int i = start; i < nums.length; i++) {
            cur.add(nums[i]);
            sub2(result, cur, i+1, nums);
            cur.remove(cur.size()-1);
        }
    }
}
