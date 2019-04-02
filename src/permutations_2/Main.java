package permutations_2;

import java.util.*;

/**
 * User: wuruoye
 * Date: 2019-04-01 08:41
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int[] nums = new int[]{1,1,2};
        System.out.println(permuteUnique(nums));
    }

    public static List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        dfs(result, new boolean[nums.length], new ArrayList<>(), nums);
        return result;
    }

    private static void dfs(List<List<Integer>> result, boolean[] choose,
                            List<Integer> saved, int[] nums) {
        if (saved.size() == nums.length) {
            result.add(new ArrayList<>(saved));
        } else {
            int last = Integer.MIN_VALUE;
            for (int i = 0; i < nums.length; i++) {
                int num = nums[i];
                if (!choose[i] && num != last) {
                    last = num;
                    choose[i] = true;
                    saved.add(num);
                    dfs(result, choose, saved, nums);
                    choose[i] = false;
                    saved.remove(saved.size()-1);
                }
            }
        }
    }
}
