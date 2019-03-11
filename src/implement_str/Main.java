package implement_str;

/**
 * User: wuruoye
 * Date: 2019-03-05 21:32
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int strStr(String haystack, String needle) {
        if (needle.length() > haystack.length()) return -1;
        for (int i = 0; i < haystack.length(); i++) {
            int j;
            for (j = 0; j < needle.length(); j++) {
                if (i+j >= haystack.length() || haystack.charAt(i+j) != needle.charAt(j)) {
                    break;
                }
            }
            if (j == needle.length()) {
                return i;
            }
        }
        return needle.equals("") ? 0 : -1;
    }

    public static int strStr2(String haystack, String needle) {
        int[] next = new int[needle.length()+1];
        next(needle, next);

        int i = 0, j = 0;
        while (i < haystack.length() && j < needle.length()) {
            if (j == -1 || haystack.charAt(i) == needle.charAt(j)) {
                ++i;
                ++j;
            } else {
                j = next[j];
            }
        }
        if (j == needle.length()) {
            return i-j;
        } else {
            return -1;
        }
    }

    private static void next(String needle, int[] next) {
        next[0] = -1;
        int i = 0, j = -1;

        while (i < needle.length()) {
            if (j == -1 || needle.charAt(i) == needle.charAt(j)) {
                ++i;
                ++j;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
    }
}
