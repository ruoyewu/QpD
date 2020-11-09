package word_break_2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();

        String s = "pineapplepenapple";
        List<String> dict = new ArrayList<>();
        dict.add("apple");
        dict.add("pen");
        dict.add("applepen");
        dict.add("pine");
        dict.add("pineapple");

        System.out.println(main.wordBreak(s, dict));
    }

    public List<String> wordBreak(String s, List<String> wordDict) {
        List<String> result = new ArrayList<>();
        doBreak(s, result, new HashSet<>(wordDict), 0, 0, "");
        return result;
    }

    public void doBreak(String s, List<String> result, Set<String> dict, int start, int cur, String ss) {
        if (start == s.length()) {
            result.add(ss);
        }
        else if (cur > s.length()) {}
        else {
            if (dict.contains(s.substring(start, cur))) {
                doBreak(s, result, dict, cur, cur + 1, (ss.equals("") ? "" : ss + " ") + s.substring(start, cur));
            }
            doBreak(s, result, dict, start, cur + 1, ss);
        }
    }
}
