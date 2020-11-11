package evaluate_reverse_polish_notation;

import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        String[] s = new String[] {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        System.out.println(new Main().evalRPN(s));
    }

    public int evalRPN(String[] tokens) {
        if (tokens == null || tokens.length == 0) return 0;
        if (tokens.length == 1) return Integer.parseInt(tokens[0]);

        int idx = 0;

//        Stack<Integer> nums = new Stack<>();
        for (String token : tokens) {
            if (isOperator(token)) {
                tokens[idx - 2] = String.valueOf(calculate(token, Integer.parseInt(tokens[idx - 1]), Integer.parseInt(tokens[idx - 2])));
                idx -= 1;
//                nums.push(calculate(token, nums.pop(), nums.pop()));
            } else {
//                nums.push(Integer.valueOf(token));
                tokens[idx++] = token;
            }
        }

        return Integer.parseInt(tokens[0]);
    }

    private boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
    }

    private int calculate(String operator, int b, int a) {
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b;
        }
        return 0;
    }
}
