package palindrome_partitioning;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String s = "aab";
        System.out.println(partition2(s));
    }

    public static List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        par(result, new ArrayList<>(), 0, s);
        return result;
    }

    private static void par(List<List<String>> result, List<String> cur, int pos, String s) {
        if (pos == s.length()) {
            result.add(new ArrayList<>(cur));
        } else {
            for (int i = pos+1; i <= s.length(); i++) {
                if (isPalindrome(s, pos, i-1)) {
                    String str = s.substring(pos, i);
                    cur.add(str);
                    par(result, cur, i, s);
                    cur.remove(cur.size()-1);
                }
            }
        }
    }

    private static boolean isPalindrome(String s, int start, int end) {
        while (start < end) {
            if (s.charAt(start++) != s.charAt(end--)) {
                return false;
            }
        }
        return true;
    }

    public static List<List<String>> partition2(String s) {
//        List<List<String>> result = new ArrayList<>();
        return par(new ArrayList<>(), 0, s, new ArrayList[s.length() + 1]);
//        return result;
    }

    private static boolean isPalindrome(String s) {
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length()-1-i)) return false;
        }
        return true;
    }

    private static List<List<String>> par(List<String> cur, int pos, String s, List<List<String>>[] saved) {
        if (saved[pos] != null) {
            return saved[pos];
        }
        if (pos == s.length()) {
//            result.add(new ArrayList<>(cur));
            return new ArrayList<>();
        } else {
            List<List<String>> tmp = new ArrayList<>();
            for (int i = pos+1; i <= s.length(); i++) {
                String str = s.substring(pos, i);
                if (isPalindrome(str)) {
                    cur.add(str);
                    List<List<String>> ls = par(cur, i, s, saved);
                    cur.remove(cur.size()-1);

                    if (i == s.length()) {
                        List<String> t = new ArrayList<>();
                        t.add(str);
                        tmp.add(t);
                    } else {
                        for (List<String> l : ls) {
                            List<String> lt = new ArrayList<>(l);
                            lt.add(0, str);
                            tmp.add(lt);
                        }
                    }
                }
            }
            saved[pos] = tmp;
            return tmp;
        }
    }
}
