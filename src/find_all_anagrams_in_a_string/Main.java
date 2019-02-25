package find_all_anagrams_in_a_string;

import java.util.ArrayList;
import java.util.List;

/**
 * User: wuruoye
 * Date: 2019-02-25 15:10
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        String s = "abab", p = "ab";
        System.out.println(findAnagrams2(s, p));
    }

    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        char[] ss = s.toCharArray(), pp = p.toCharArray();
        int[] pi = new int[26];
        int[] tmp = new int[26];
        for (char c : pp) {
            tmp[c - 'a'] ++;
        }
        System.arraycopy(tmp, 0, pi, 0, 26);

        for (int i = 0; i < ss.length; i++) {
            int j = 0;
            for ( ; j < pp.length && i+j < ss.length; j++) {
                if (pi[ss[i+j] - 'a']-- < 1) {
                    break;
                }
            }
            if (j == pp.length) {
                result.add(i);
            }
            System.arraycopy(tmp, 0, pi, 0, 26);
        }

        return result;
    }

    public static List<Integer> findAnagrams2(String s, String p) {
        List<Integer> result = new ArrayList<>();
        char[] ss = s.toCharArray(), pp = p.toCharArray();
        int[] next = new int[pp.length];
        getNext(pp, next);
        int i = 0, j = 0;
        while (i < ss.length && j < pp.length) {
            if (j == -1 || ss[i] == pp[j]) {
                i++;
                j++;
                if (j == pp.length) {
                    result.add(i-j);
                    i = i - j + 1;
                    j = 0;
                }
            } else {
                if (next[j] == -1) {
                    i++;
                    j = 0;
                } else {
                    j = next[j];
                }
            }
        }
        return result;
    }

    private static void getNext(char[] pp, int[] next) {
        int i = 0, j = -1;
        next[0] = -1;
        while (i < pp.length-1) {
            if (j == -1 || pp[i] == pp[j]) {
                ++i;
                ++j;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
    }

    public static List<Integer> findAnagrams3(String s, String p) {
        int n = s.length(), m = p.length();
        char[] ss = s.toCharArray(), pp = p.toCharArray();
        int[] counts = new int[26];
        int zeros = 0;
        for (char c : pp) {
            if (counts[c - 'a']++ == 0) {
                zeros--;
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            if (--counts[ss[i]] == 0) {
                zeros++;
            }
        }

        if (zeros == 0) result.add(0);

        for (int i = 0, j = m; j < n; i++, j++) {
            if (counts[ss[i]]++ == 0) {
                zeros--;
            }
            if (--counts[ss[j]] == 0) {
                zeros++;
            }
            if (zeros == 0) {
                result.add(i+1);
            }
        }
        return result;
    }
}
