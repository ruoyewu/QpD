package longest_valid_parentheses;

import java.util.Stack;

/**
 * User: wuruoye
 * Date: 2019/1/14 09:14
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int longestValidParentheses(String s) {
        char[] cs = s.toCharArray();
        int longest = 0;

        for (int i = 0; i < cs.length-1; i++) {
            if (cs.length - i <= longest) {
                break;
            }
            int max = 0, left = 0, save = 0;
            for (int j = i; j < cs.length; j++) {
                if (cs[j] == '(') {
                    left++;
                }else {
                    if (left == 0) {
                        i = j+1;
                        break;
                    }
                    left--;
                    save++;
                    if (left == 0) {
                        max += save;
                        save = 0;
                    }
                }
            }
            if (longest < max) {
                longest = max;
            }
        }

        return longest*2;
    }

    public static int longestValidParentheses2(String s) {
        char[] cs = s.toCharArray();
        int longest = 0;
        int[] ls = new int[cs.length];

        for (int i = 1; i < cs.length; i++) {
            if (cs[i] == ')') {
                if (cs[i-1] == '(') {
                    ls[i] = (i >= 2 ? ls[i-2] : 0) + 2;
                } else if (i - ls[i-1] > 0 && cs[i - ls[i-1] - 1] == '(') {
                    ls[i] = ls[i-1] + ((i - ls[i-1]) >= 2 ? ls[i - ls[i-1] - 2] : 0) + 2;
                }
                longest = longest > ls[i] ? longest : ls[i];
            }
        }
        return longest;
    }

    public static int longestValidParentheses3(String s) {
        int longest = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.empty()) {
                    stack.push(i);
                } else {
                    longest = Math.max(longest, i - stack.peek());
                }
            }
        }
        return longest;
    }

    public static int longestValidParentheses4(String s) {
        int longest = 0, left = 0, right = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }

            if (left == right) {
                longest = Math.max(longest, right*2);
            } else if (right > left) {
                left = right = 0;
            }
        }

        left = right = 0;
        for (int i = s.length()-1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }

            if (left == right) {
                longest = Math.max(longest, left*2);
            } else if (left > right) {
                left = right = 0;
            }
        }

        return longest;
    }
}
