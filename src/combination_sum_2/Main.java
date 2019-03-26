package combination_sum_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: wuruoye
 * Date: 2019-03-26 20:11
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int[] candidates = new int[]{2, 5, 2, 1, 2};
        int target = 5;
        System.out.println(combinationSum2(candidates, target));
    }

    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<>();
        combination(result, 0, candidates, target, 0, new ArrayList<>());
        return result;
    }

    private static void combination(List<List<Integer>> result, int pos,
                                    int[] candidates, int target, int sum, List<Integer> saved) {
        for (int i = pos; i < candidates.length; i++) {
            int candidate = candidates[i];
            if (sum + candidate < target) {
                if (i > pos && candidate == candidates[i-1]) continue;

                sum += candidate;
                saved.add(candidate);
                combination(result, i+1, candidates, target, sum, saved);
                sum -= candidate;
                saved.remove(saved.size()-1);
            } else if (sum + candidates[i] == target) {
                List<Integer> l = new ArrayList<>(saved);
                l.add(candidates[i]);
                result.add(l);
                break;
            } else {
                break;
            }
        }
    }
}
