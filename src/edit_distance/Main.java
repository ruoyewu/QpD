package edit_distance;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019/1/18 16:03
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int minDistance(String word1, String word2) {
        int[][] saved = new int[word1.length()][word2.length()];
        for (int i = 0; i < word1.length(); i++) {
            Arrays.fill(saved[i], -1);
        }
        return min(word1.toCharArray(), word2.toCharArray(), 0, 0, 0, saved);
    }

    public static int min(char[] w1, char[] w2, int s1, int s2, int cur, int[][] saved) {
        int min;
        if (s2 == w2.length) {
            return cur + w1.length - s1;
        }
        if (s1 == w1.length) {
            return cur + w2.length - s2;
        }
        if (saved[s1][s2] >= 0) {
            return saved[s1][s2] + cur;
        }
        if (w1[s1] == w2[s2]) {
            min = min(w1, w2, s1+1, s2+1, cur, saved);
        } else {
            // delete
            int m1 = min(w1, w2, s1+1, s2, cur+1, saved);
            // replace
            int m2 = min(w1, w2, s1+1, s2+1, cur+1, saved);
            // insert
            int m3 = min(w1, w2, s1, s2+1, cur+1, saved);
            min = Math.min(Math.min(m1, m2), m3);
        }
        saved[s1][s2] = min-cur;
        return min;
    }

    public static int minDistance2(String word1, String word2) {
        int l1 = word1.length(), l2 = word2.length();
        char[] c1 = word1.toCharArray(), c2 = word2.toCharArray();
        int[][] result = new int[l1+1][l2+1];
        for (int i = 0; i <= l1; i++) {
            result[i][0] = i;
        }
        for (int i = 0; i <= l2; i++) {
            result[0][i] = i;
        }

        for (int i = 1; i <= l1; i++) {
            for (int j = 1; j <= l2; j++) {
                int min = Integer.MAX_VALUE;
                if (c1[i-1] == c2[j-1]) {
                    min = result[i-1][j-1];
                }
                // insert
                int m1 = 1 + result[i-1][j];
                // delete
                int m2 = 1 + result[i][j-1];
                // replace
                int m3 = 1 + result[i-1][j-1];

                result[i][j] = Math.min(Math.min(min, m1), Math.min(m2, m3));
            }
        }

        return result[l1][l2];
    }
}
