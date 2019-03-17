package substring_with_concatenation_of_all_words;

import java.util.*;

/**
 * User: wuruoye
 * Date: 2019-03-16 09:01
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        String s = "barfoofoobarthefoobarman";
        String[] words = new String[]{"foo", "bar", "the"};
        System.out.println(findSubstring(s, words));
    }

    public static List<Integer> findSubstring(String s, String[] words) {
        if (words.length == 0 || words[0].length() > s.length()) return new ArrayList<>();
        Set<Integer> result = new HashSet<>();
        HashSet<String> set, tmp = new HashSet<>();
        set = new HashSet<>(Arrays.asList(words).subList(1, words.length));

        // 求 next 数组
        String p = words[0];
        int i = 0, j = -1, len = p.length();
        int[] next = new int[len];
        next[0] = -1;
        while (i < len-1) {
            if (j == -1 || p.charAt(i) == p.charAt(j)) {
                ++i;
                ++j;
                next[i] = j;
            } else {
                j = next[j];
            }
        }

        // 不断从 s 中索引 p 的位置，当找到 p 的位置之后，再沿着两端延伸，查找是否存在解
        i = 0;
        j = 0;
        while (i < s.length() && j < len) {
            if (j == -1 || s.charAt(i) == p.charAt(j)) {
                ++i;
                ++j;
                if (j == len) {
                    // 已找到 p 的位置，判断从这一位置延伸出去是否可以找到其他字符串
                    tmp.clear();
                    tmp.addAll(set);
                    expend(result, s, i-len, i, len, tmp);

                    // 恢复继续查询
                    i = i - len + 1;
                    j = 0;
                }
            } else {
                if (next[j] == -1) {
                    ++i;
                    j = 0;
                } else {
                    j = next[j];
                }
            }
        }
        return new ArrayList<>(result);
    }

    private static void expend(Set<Integer> result, String s, int left,
                               int right, int len, HashSet<String> set) {
        if (set.isEmpty()) {
            result.add(left);
            return;
        }

        if (left >= len) {
            String l = s.substring(left-len, left);
            if (set.remove(l)) {
                expend(result, s, left-len, right, len, set);
                set.add(l);
            }
        }

        if (right <= s.length()-len) {
            String r = s.substring(right, right+len);
            if (set.remove(r)) {
                expend(result, s, left, right+len, len, set);
                set.add(r);
            }
        }
    }
}
