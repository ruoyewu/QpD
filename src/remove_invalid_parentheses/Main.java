package remove_invalid_parentheses;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * User: wuruoye
 * Date: 2019-02-02 22:28
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        String s = ")d))";
        removeInvalidParentheses(s);
        removeInvalidParentheses2(s);
        removeInvalidParentheses4(s);
    }

    public static List<String> removeInvalidParentheses(String s) {
        List<String> result = new ArrayList<>();
        remove(s, result, 0, 0, '(', ')');
        return result;
    }

    private static void remove(String s, List<String> result,
                               int lastI, int lastJ, char left, char right) {
        int leftNum = 0;
        for (int i = lastI; i < s.length(); i++) {
            if (s.charAt(i) == left) leftNum++;
            if (s.charAt(i) == right) leftNum--;
            if (leftNum >= 0) continue;
            for (int j = lastJ; j <= i; j++) {
                if (s.charAt(j) == right && (j == lastJ || s.charAt(j-1) != right)) {
                    remove(s.substring(0, j) + s.substring(j+1), result, i, j, left, right);
                }
            }
            return;
        }
        if (leftNum == 0) {
            result.add(left == '(' ? s : new StringBuilder(s).reverse().toString());
            return;
        }
        String reversed = new StringBuilder(s).reverse().toString();
        if (left == '(') {
            remove(reversed, result, 0, 0, right, left);
        }
    }

    public static List<String> removeInvalidParentheses2(String s) {
        List<String> result = new ArrayList<>();
        LinkedList<SString> left = new LinkedList<>();
        boolean reversed = false;
        char leftC = '(', rightC = ')';
        left.add(new SString(s, 0, 0));
        while (!left.isEmpty()) {
            SString string = left.removeFirst();
            s = string.s;
            int startJ = string.startJ, leftNum = 0, i = string.startI;
            for ( ; i < s.length(); i++) {
                if (s.charAt(i) == leftC) leftNum++;
                else if (s.charAt(i) == rightC) leftNum--;
                if (leftNum < 0) break;
            }
            if (i == s.length()) {
                if (leftNum > 0) {
                    reversed = true;
                    leftC = ')';
                    rightC = '(';
                    int n = left.size();
                    left.add(new SString(new StringBuilder(s).reverse().toString(), 0, 0));
                    for (int j = 0; j < n; j++) {
                        left.add(new SString(
                                new StringBuilder(left.removeFirst().s).reverse().toString(), 0, 0));
                    }
//                    continue;
                } else {
                    result.add(reversed ? new StringBuilder(s).reverse().toString() : s);
                }
            } else {
                for (int j = startJ; j <= i; j++) {
                    if (s.charAt(j) == rightC && (j == startJ || s.charAt(j-1) != rightC)) {
                        left.add(new SString(s.substring(0, j) + s.substring(j+1), i, j));
                    }
                }
            }
        }

        return result;
    }

    public static List<String> removeInvalidParentheses4(String s) {
        int leftNum = 0, rightNum = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                leftNum++;
            } else if (c == ')'){
                if (leftNum == 0) {
                    rightNum++;
                } else {
                    leftNum--;
                }
            }
        }

        List<String> result = new ArrayList<>();
        remove(result, s, leftNum, rightNum, 0);
        return result;
    }

    private static void remove(List<String> result, String s, int leftNum, int rightNum, int start) {
        if (leftNum == 0 && rightNum == 0 && isValid(s)) {
            result.add(s);
        } else {
            for (int i = start; i < s.length(); i++) {
                if (i == start || s.charAt(i) != s.charAt(i-1)) {
                    if (s.charAt(i) == '(' && leftNum > 0) {
                        remove(result, s.substring(0, i) + s.substring(i+1), leftNum-1, rightNum, i);
                    } else if (s.charAt(i) == ')' && rightNum > 0) {
                        remove(result, s.substring(0, i) + s.substring(i+1), leftNum, rightNum-1, i);
                    }
                }
            }
        }
    }

    private static boolean isValid(String s){
        int count = 0;
        for(char c: s.toCharArray()){
            if(c == '('){
                count++;
            }
            else if(c == ')'){
                if(count == 0){ // remember to modify the count
                    return false;
                }
                count--;
            }
        }
        return count == 0;
    }

    static class SString {
        String s;
        int startI, startJ;
        SString(String s, int i, int j) {
            this.s = s;
            this.startI = i;
            this.startJ = j;
        }
    }
}
