package decode_ways;

/**
 * User: wuruoye
 * Date: 2019-05-06 16:23
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        String s = "110";
        System.out.println(numDecodings3(s));
    }

    public static int numDecodings(String s) {
        if (s == null || s.isEmpty() || s.charAt(0) == '0') return 0;
        int[] dp = new int[s.length()];

        dp[0] = 1;

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != '0') dp[i] += dp[i-1];
            int a = s.charAt(i-1) - '0', b = s.charAt(i) - '0';
            if (a != 0 && (a*10+b) <= 26) dp[i] += i > 1 ? dp[i-2] : 1;
        }

        return dp[s.length()-1];
    }

    public static int numDecodings2(String s) {
        if (s == null || s.isEmpty() || s.charAt(0) == '0') return 0;
        int[] dp = new int[s.length()];

        dp[0] = 1;
        char last = s.charAt(0);

        for (int i = 1; i < s.length(); i++) {
            char cur = s.charAt(i);
            if (cur != '0') dp[i] += dp[i-1];
            if (last == '1' || (last == '2' && cur < '7'))
                dp[i] += i > 1 ? dp[i-2] : 1;
            last = cur;
        }

        return dp[s.length()-1];
    }

    public static int numDecodings3(String s) {
        if (s.isEmpty() || s.charAt(0) == '0') return 0;
        int pp = 0, p = 1;
        char last = '3';

        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            int num = 0;
            if (cur != '0') num += p;
            if (last == '1' || (last == '2' && cur < '7')) num += pp;
            pp = p;
            p = num;
            last = cur;
        }

        return p;
    }

    public static int numDecoding4(String s) {
        if (s.isEmpty() || s.charAt(0) == '0') return 0;
        int pp = 0, p = 1;
        char last = '0';

        for (int i = s.length()-1; i >= 0; --i) {
            char cur = s.charAt(i);
            if (cur == '1' || (cur == '2' && last < '7')) {
                p += pp;
                pp = p - pp;
            } else {
                pp = p;
                if (cur == '0') p = 0;
            }
            last = cur;
        }

        return p;
    }
}
