package triangle;

import java.util.List;

/**
 * User: wuruoye
 * Date: 2019-05-19 14:29
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int minimumTotal(List<List<Integer>> triangle) {
        return min(triangle, 0, 0);
    }

    private static int min(List<List<Integer>> triangle, int level, int pos) {
        if (level == triangle.size()) return 0;
        return triangle.get(level).get(pos) + Math.min(
                min(triangle, level+1, pos),
                min(triangle, level+1, pos+1)
        );
    }

    public static int minimumTotal2(List<List<Integer>> triangle) {
        if (triangle.size() == 0) return 0;
        for (int i = triangle.size()-1; i > 0; i--) {
            List<Integer> cur = triangle.get(i-1);
            List<Integer> next = triangle.get(i);
            for (int j = 0; j < i; j++) {
                cur.set(j, Math.min(next.get(j), next.get(j+1)) + cur.get(j));
            }
        }
        return triangle.get(0).get(0);
    }

    public static int minimumTotal3(List<List<Integer>> triangle) {
        if (triangle.size() == 0) return 0;
        int n = triangle.size();
        int[] saved = new int[n];
        List<Integer> tail = triangle.get(n-1);
        for (int i = 0; i < n; i++) {
            saved[i] = tail.get(i);
        }

        for (int i = n-2; i >= 0; i--) {
            tail = triangle.get(i);
            for (int j = 0; j <= i; j++) {
                saved[j] = tail.get(j) + Math.min(saved[j], saved[j+1]);
            }
        }

        return saved[0];
    }
}
