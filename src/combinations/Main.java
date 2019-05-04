package combinations;

import java.util.ArrayList;
import java.util.List;

/**
 * User: wuruoye
 * Date: 2019-05-04 15:08
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int n = 4;
        int k = 2;
        System.out.println(combine(n, k));
    }

    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        com(result, new ArrayList<>(k), 0, n, k);
        return result;
    }

    private static void com(List<List<Integer>> result, List<Integer> cur, int pos, int n, int k) {
        if (k == 0) result.add(new ArrayList<>(cur));
        else {
            for (int i = pos+1; i <= n; i++) {
                if (n - i + 1 < k) break;
                else {
                    cur.add(i);
                    com(result, cur, i, n, k-1);
                    cur.remove(cur.size()-1);
                }
            }
        }
    }
}
