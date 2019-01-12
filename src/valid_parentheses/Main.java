package valid_parentheses;

import java.util.Scanner;
import java.util.Stack;

/**
 * User: wuruoye
 * Date: 2019/1/12 14:35
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        System.out.println(isValid(s));
    }

    public static boolean isValid(String s) {
        char[] cs = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (char cur : cs) {
            if (cur == '(' || cur == '[' || cur == '{') {
                stack.push(cur);
            } else {
                char op = (cur == ')') ? '(' : ((cur == ']') ? '[' : '{');
                if (!stack.empty() && stack.lastElement() == op) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }

        return stack.empty();
    }
}
