package subsets_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: wuruoye
 * Date: 2019-05-06 16:08
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int[] nums = new int[]{1,2,2};
        System.out.println(subsetsWithDup(nums));
    }

    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        sub(result, new ArrayList<>(nums.length), nums, 0);
        return result;
    }

    private static void sub(List<List<Integer>> result, List<Integer> cur, int[] nums, int pos) {
        result.add(new ArrayList<>(cur));
        for (int i = pos; i < nums.length; i++) {
            if (i == pos || nums[i] != nums[i-1]) {
                cur.add(nums[i]);
                sub(result, cur, nums, i+1);
                cur.remove(cur.size()-1);
            }
        }
    }
}
