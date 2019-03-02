package palindromic_substrings;

/**
 * User: wuruoye
 * Date: 2019-03-02 08:39
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        String s = "aaaaa";
        System.out.println(countSubstrings(s));
    }

    public static int countSubstrings(String s) {
        int count = 0, n = s.length();
        boolean[][] saved = new boolean[n][n];

        for (int right = 0; right < n; right++) {
            saved[right][right] = true;
            for (int left = right - 1; left >= 0; left--) {
                if (s.charAt(right) == s.charAt(left)) {
                    if (left == right-1 || saved[left+1][right-1]) {
                        saved[left][right] = true;
                        count++;
                    }
                }
            }
        }

        return count + n;
    }

    public static int countSubstrings2(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            count++;
            count += count(i-1, i+1, s);
            count += count(i, i+1, s);
        }
        return count;
    }

    private static int count(int left, int right, String s) {
        int count = 0;
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            count++;
            left--;
            right++;
        }
        return count;
    }
}
