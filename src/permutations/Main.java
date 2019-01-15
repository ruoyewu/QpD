package permutations;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * User: wuruoye
 * Date: 2019/1/15 15:59
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        permute2(new int[]{1,2,3});
    }

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> left = new ArrayList<>();
        for (int i : nums) {
            left.add(i);
        }
        dfs(result, new ArrayList<>(), left);

        return result;
    }

    public static void dfs(List<List<Integer>> result, List<Integer> saved, List<Integer> left) {
        if (left.isEmpty()) {
            result.add(new ArrayList<>(saved));
        } else {
            for (int i = 0; i < left.size(); i++) {
                saved.add(left.remove(i));
                dfs(result, saved, left);
                left.add(i, saved.remove(saved.size()-1));
            }
        }
    }

    public static List<List<Integer>> permute2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Set<Integer> saved = new LinkedHashSet<>();
        dfs2(result, saved, nums);

        return result;
    }

    public static void dfs2(List<List<Integer>> result, Set<Integer> saved, int[] nums) {
        if (saved.size() == nums.length) {
            result.add(new ArrayList<>(saved));
        } else {
            for (int num : nums) {
                if (!saved.contains(num)) {
                    saved.add(num);
                    dfs2(result, saved, nums);
                    saved.remove(num);
                }
            }
        }
    }
}
