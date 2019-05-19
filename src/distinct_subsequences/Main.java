package distinct_subsequences;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019-05-17 16:50
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        String s = "babgbag";
        String t = "bag";
        System.out.println(numDistinct(s, t));
        System.out.println(numDistinct2(s, t));
        System.out.println(numDistinct3(s, t));
        System.out.println(numDistinct4(s, t));
    }

    public static int numDistinct(String s, String t) {
        if (s.length() < t.length()) return 0;
        if (s.length() == t.length() && !s.equals(t)) return 0;

        int[][] saved = new int[s.length()+1][t.length()+1];
        for (int[] row : saved) Arrays.fill(row, -1);
        return distinct(s, t, 0, 0, saved);
    }

    private static int distinct(String s, String t, int si, int ti, int[][] saved) {
        if (saved[si][ti] >= 0) return saved[si][ti];
        if (s.length() - si == t.length() - ti)
            return s.substring(si).equals(t.substring(ti)) ? 1 : 0;
        if (ti == t.length()) {
            return 1;
        }
        int count = 0;
        for (int i = si; i <= s.length()-t.length()+ti; i++) {
            if (s.charAt(i) == t.charAt(ti)) {
                int c = distinct(s, t, i+1, ti+1, saved);
                count += c;
            }
        }
        saved[si][ti] = count;
        return count;
    }

    public static int numDistinct2(String s, String t) {
        int m = s.length(), n = t.length();
        if (m < n || (m == n && !s.equals(t))) return 0;

        int[][] saved = new int[m+1][n+1];

        for (int j = 1; j <= n; j++) {
            for (int i = j; i <= m; i++) {
                if (s.charAt(i-1) == t.charAt(j-1)) {
                    if (j == 1) {
                        saved[i][j] = 1;
                        continue;
                    }
                    for (int k = 1; k < i; k++) {
                        saved[i][j] += saved[k][j-1];
                    }
                }
            }
        }

        int result = 0;
        for (int i = 1; i <= m; i++) {
            result += saved[i][n];
        }
        return result;
    }

    public static int numDistinct3(String s, String t) {
        int m = s.length(), n = t.length();
        if (m < n || (m == n && !s.equals(t))) return 0;

        int[][] saved = new int[m+1][n+1];
        saved[0][0] = 1;

        for (int j = 0; j <= n; j++) {
            for (int i = 1; i <= m; i++) {
                if (j == 0) saved[i][j] = 1;
                else
                    saved[i][j] = saved[i-1][j] + (s.charAt(i-1) == t.charAt(j-1) ? saved[i-1][j-1] : 0);
            }
        }

        return saved[m][n];
    }

    public static int numDistinct4(String s, String t) {
        int m = s.length(), n = t.length();
        if (m < n || (m == n && !s.equals(t))) return 0;

        int[] startIdx = new int[128], preIdx = new int[n];
        Arrays.fill(startIdx, -1);

        for (int i = 0; i < n; i++) {
            preIdx[i] = startIdx[t.charAt(i)];
            startIdx[t.charAt(i)] = i+1;
        }

        int[] saved = new int[n+1];
        saved[0] = 1;
        for (int i = 0; i < m; i++) {
            for (int j = startIdx[s.charAt(i)]; j != -1; j = preIdx[j-1]) {
                saved[j] += saved[j-1];
            }
            System.out.println(Arrays.toString(saved));
        }

        return saved[n];
    }
}
