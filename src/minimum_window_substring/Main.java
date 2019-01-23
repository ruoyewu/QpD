package minimum_window_substring;

/**
 * User: wuruoye
 * Date: 2019/1/19 22:07
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(miniWindow("a", "aa"));
    }

    public static String miniWindow(String s, String t) {
        if (s.length() < t.length())
            return "";

        char[] sc = s.toCharArray(), tc = t.toCharArray();
        int[] index = new int[128];
        int head = 0, start = 0, end = 0, min = Integer.MAX_VALUE, cnt = tc.length;
        for (char c : tc) {
            index[c - 'A']++;
        }

        while (end < sc.length) {
            if (index[sc[end++] - 'A']-- > 0) {
                cnt--;
            }

            while (cnt == 0) {
                if (end - start < min) {
                    min = end - start;
                    head = start;
                }
                if (index[sc[start++] - 'A']++ == 0) {
                    cnt++;
                }
            }
        }

        return min == Integer.MAX_VALUE ? "" : s.substring(head, head+min);
    }

}