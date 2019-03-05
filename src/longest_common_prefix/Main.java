package longest_common_prefix;

/**
 * User: wuruoye
 * Date: 2019-03-04 17:44
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(longestCommonPrefix(new String[]{"aa", "a"}));
    }

    public static String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        int end = strs[0].length();
        for (int i = 1; i < strs.length; i++) {
            for (int j = 0; j < end; j++) {
                if (j == strs[i].length() || strs[0].charAt(j) != strs[i].charAt(j)) {
                    end = j;
                }
            }
        }
        return strs[0].substring(0, end);
    }

    public static String longestCommonPrefix2(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        if (strs.length == 1) return strs[0];
        int end = 0;
        while (end < strs[0].length()) {
            for (int i = 1; i < strs.length; i++) {
                if (end >= strs[i].length() || strs[0].charAt(end) != strs[i].charAt(end)) {
                    return strs[0].substring(0, end);
                }
            }
            end++;
        }
        return strs[0].substring(0, end);
    }

    public static String longestCommonPrefix3(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        int n = strs.length, pos;
        while (n > 1) {
            pos = 0;
            for (int i = 0; i < n; i += 2) {
                if (i < n-1) {
                    String left = strs[i], right = strs[i+1];
                    int end = 0;
                    while (end < left.length() && end < right.length() &&
                            left.charAt(end) == right.charAt(end)) end++;
                    strs[pos++] = left.substring(0, end);
                } else {
                    strs[pos++] = strs[i];
                }
            }
            n = pos;
        }
        return strs[0];
    }
}
