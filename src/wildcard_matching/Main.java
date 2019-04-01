package wildcard_matching;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019-03-29 19:45
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        String s = "aa";
        String p = "a*";
        System.out.println(isMatch(s, p));
        System.out.println(isMatch2(s, p));
        System.out.println(isMatch3(s, p));
    }

    public static boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] map = new boolean[n+1][m+1];

        map[0][0] = true;
        if (p.length() > 0 && p.charAt(0) == '*') {
            Arrays.fill(map[1], true);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (p.charAt(i) == s.charAt(j) || p.charAt(i) == '?') {
                    map[i+1][j+1] = map[i][j];
                } else if (p.charAt(i) == '*') {
                    for (int k = 0; k <= j+1; k++) {
                        if (map[i][k]) {
                            Arrays.fill(map[i+1], k, j+2, true);
                            break;
                        }
                    }
                }
            }
        }

        return map[n][m];
    }

    public static boolean isMatch3(String s, String p) {
        int i = 0, j = 0, is = -1, js = -1;
        while (i < s.length()) {
            if (j < p.length() && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?')) {
                ++i;
                ++j;
            } else if (j < p.length() && p.charAt(j) == '*') {
                is = i;
                js = j++;
            } else if (is != -1) {
                i = ++is;
                j = js + 1;
            } else return false;
        }

        while (j < p.length() && p.charAt(j) == '*') ++j;

        return j == p.length();
    }

    public static boolean isMatch2(String s, String p) {
        return match(s, p, 0, 0) == 1;
    }

    private static int match(String s, String p, int i, int j) {
        while (i < s.length() && j < p.length() &&
                (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?')) {
            ++i;
            ++j;
        }

        if (s.length() == i && p.length() == j) return 1;
        if (j == p.length()) return -1;

        // 拒绝重入标志
        boolean insert = false;
        if (p.charAt(j) == '*') {
            insert = true;
            for (int k = i; k <= s.length(); k++) {
                int result = match(s, p, k, j+1);
                if (result == 1) return 1;
                else if (result == 0) return 0;
            }
        }
        return insert ? 0 : -1;
    }
}
