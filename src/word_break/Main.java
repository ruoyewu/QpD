package word_break;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: wuruoye
 * Date: 2019/1/27 18:37
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static boolean wordBreak(String s, List<String> wordDict) {
        List<Set<char[]>> dict = new ArrayList<>();
        for (String word : wordDict) {
            int i = word.charAt(0) - 'A';
            while (dict.size() <= i) {
                dict.add(new HashSet<>());
            }
            dict.get(i).add(word.toCharArray());
        }
        int[] saved = new int[s.length()];
        return word(s.toCharArray(), 0, dict, saved);
    }

    private static boolean word(char[] s, int pos, List<Set<char[]>> dict, int[] saved) {
        if (pos == s.length) {
            return true;
        }
        if (pos > s.length) {
            return false;
        }
        if (saved[pos] == 1) {
            return true;
        } else if (saved[pos] == -1) {
            return false;
        }
        int i = s[pos] - 'A';
        if (dict.size() > i) {
            for (char[] word : dict.get(i)) {
                boolean ok = true;
                for (int j = 0; j < word.length; j++) {
                    if (pos + j < s.length && s[pos+j] != word[j]) {
                        ok = false;
                        break;
                    }
                }
                if (ok && word(s, pos+word.length, dict, saved)) {
                    saved[pos] = 1;
                    return true;
                }
            }
        }
        saved[pos] = -1;
        return false;
    }

    public static boolean wordBreak2(String s, List<String> wordDict) {
        int n = s.length();
        Set<String> set = new HashSet<>();
        int minLen = Integer.MAX_VALUE;
        int maxLen = 0;
        for (String word : wordDict) {
            if (word.length() > maxLen) maxLen = word.length();
            if (word.length() < minLen) minLen = word.length();
            set.add(word);
        }

        boolean[] ok = new boolean[n+1];
        ok[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            int start = Math.max(0, i-maxLen);
            int end = i - minLen;
            for (int j = start; j <= end; j++) {
                if (ok[j] && set.contains(s.substring(j, i))) {
                    ok[i] = true;
                    break;
                }
            }
        }
        return ok[n];}


}
