package combination_sum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: wuruoye
 * Date: 2019/1/14 20:42
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }


    private static List<List<Integer>> result = new ArrayList<>();

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        result.clear();
        Arrays.sort(candidates);

        find(new ArrayList<>(), 0, 0, target, candidates);

        return result;
    }

    public static void find(List<Integer> saved, int sum, int pos, int target, int[] candidates) {
        for (int i = pos; i < candidates.length; i++) {
            if (sum + candidates[i] < target) {
                saved.add(candidates[i]);
                sum += candidates[i];
                find(saved, sum, i, target, candidates);
                sum -= candidates[i];
                saved.remove(saved.size()-1);
            } else if (sum + candidates[i] == target) {
                List<Integer> l = new ArrayList<>(saved);
                l.add(candidates[i]);
                result.add(l);
            } else {
                break;
            }
        }
    }
}
