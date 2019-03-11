package palindrome_number;

/**
 * User: wuruoye
 * Date: 2019-03-11 08:31
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static boolean isPalindrome(int x) {
        if (x < 0) return false;
        String s = String.valueOf(x);
        int n = s.length();
        for (int i = 0; i < n/2; i++) {
            if (s.charAt(i) != s.charAt(n-i-1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPalindrome2(int x) {
        if (x < 0) return false;
        int[] list = new int[32];
        int pos = 0;
        while (x > 0) {
            list[pos++] = x % 10;
            x /= 10;
        }
        for (int i = 0; i < pos / 2; i++) {
            if (list[i] != list[pos-1-i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPalindrome3(int x) {
        if (x < 0) return false;

        int revert = 0, tmp = x;
        while (tmp > 0) {
            revert = revert * 10 + tmp % 10;
            tmp /= 10;
        }

        return revert == x;
    }

    public static boolean isPalindrome4(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) return false;

        int revert = 0;
        while (x > revert) {
            revert = revert * 10 + x % 10;
            x /= 10;
        }

        return x == revert || x == revert / 10;
    }
}
