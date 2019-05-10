package interleaving_string;

/**
 * User: wuruoye
 * Date: 2019-05-08 16:09
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        String s1 = "aabcc";
        String s2 = "dbbca";
        String s3 = "aadbbbaccc";
        System.out.println(isInterleave(s1, s2, s3));
        System.out.println(isInterleave2(s1, s2, s3));
    }

    public static boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) return false;
        int[][] saved = new int[s1.length()+1][s2.length()+1];
        return isInterleave(s1, s2, s3, 0, 0, 0, saved);
    }

    private static boolean isInterleave(String s1, String s2, String s3,
                                        int p1, int p2, int p3, int[][] saved) {
        if (saved[p1][p2] == 1) return true;
        if (saved[p1][p2] == -1) return false;

        if (p3 == s3.length()) return true;

        boolean result = false;
        if (p1 < s1.length() && s1.charAt(p1) == s3.charAt(p3)
                && isInterleave(s1, s2, s3, p1+1, p2, p3+1, saved))
            result = true;
        if (p2 < s2.length() && s2.charAt(p2) == s3.charAt(p3)
                && isInterleave(s1, s2, s3, p1, p2+1, p3+1, saved))
            result =  true;

        saved[p1][p2] = result ? 1 : -1;

        return result;
    }

    public static boolean isInterleave2(String s1, String s2, String s3) {
        int l1 = s1.length(), l2 = s2.length(), l3 = s3.length();
        if (l1 + l2 != l3) return false;

        boolean[][] saved = new boolean[l1+1][l2+1];
        saved[0][0] = true;

        for (int i = 0; i <= l1; i++) {
            for (int j = 0; j <= l2; j++) {
                if (i < s1.length() && i+j < s3.length() && s1.charAt(i) == s3.charAt(i+j) && saved[i][j]) {
                    saved[i+1][j] = true;
                }
                if (j < s2.length() && i+j < s3.length() && s2.charAt(j) == s3.charAt(i+j) && saved[i][j]) {
                    saved[i][j+1] = true;
                }
            }
        }

        return saved[l1][l2];
    }
}
