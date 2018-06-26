package regular_expression_matching;

import java.util.Scanner;

/**
 * User: wuruoye
 * Date: 2018/6/22 14:13
 * Description:
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        while (n-- > 0) {
            String s = scanner.next();
            String p = scanner.next();

            System.out.println(isMatch2(s, p));
        }
    }

    public static boolean isMatch(String s, String p) {
        if (p.isEmpty()) {
            return s.isEmpty();
        }

        boolean firstMatch = !s.isEmpty() &&
                (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.');

        if (p.length() >= 2 && p.charAt(1) == '*') {
            return isMatch(s, p.substring(2)) ||
                    (firstMatch && isMatch(s.substring(1), p));
        }else {
            return firstMatch && isMatch(s.substring(1), p.substring(1));
        }
    }

    public static boolean isMatch2(String s, String p) {
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[s.length()][p.length()] = true;

        for (int i = s.length(); i >= 0; i--) {
            for (int j = p.length() - 1; j >= 0; j--) {
                boolean isMatch = i < s.length() && (p.charAt(j) == s.charAt(i)
                        || p.charAt(j) == '.');
                if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
                    dp[i][j] = dp[i][j + 2] || isMatch && dp[i + 1][j];
                }else {
                    dp[i][j] = isMatch && dp[i+1][j+1];
                }
            }
        }

        return dp[0][0];
    }
}
