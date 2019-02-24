package decode_string;

import java.util.Stack;

/**
 * User: wuruoye
 * Date: 2019-02-24 11:41
 * Description:
 */
public class Main {
    private static int index = 0;

    public static void main(String[] args) {
        String s = "3[a]2[b4[c]]";
        System.out.println(decodeString(s));
        System.out.println(decodeString2(s));
    }

    public static String decodeString(String s) {
        if (s.length() == 0) return "";
        StringBuilder builder = new StringBuilder();
        int pos = 0, start = 0, count = 0, left = 0;
        while (pos < s.length()) {
            char c = s.charAt(pos);
            if (left == 0) {
                if (c >= '0' && c <= '9') {
                    // 数字前有字母
                    if (start != pos) {
                        builder.append(s, start, pos);
                    }

                    count = count * 10 + c - '0';
                    start = pos+1;
                } else if (c == '[') {
                    left = 1;
                    start = pos+1;
                }
                pos++;
            } else {
                if (c == '[') {
                    left++;
                } else if (c == ']') {
                    left--;
                    if (left == 0) {
                        String ss = decodeString(s.substring(start, pos));
                        while (count-- > 0) {
                            builder.append(ss);
                        }
                        count = 0;
                        start = pos+1;
                    }
                }
                pos++;
            }
        }
        // 最后以字母串结尾
        if (start != pos) {
            builder.append(s, start, pos);
        }
        return builder.toString();
    }

    public static String decodeString2(String s) {
        index = 0;
        return s2(s);
    }

    private static String s2(String s) {
        StringBuilder builder = new StringBuilder();
        int count = 0;
        while (index < s.length()) {
            char c = s.charAt(index++);
            if (c >= '0' && c <= '9') {
                count = count * 10 + c - '0';
            } else if (c == '[') {
                String sub = s2(s);
                while (count-- > 0) builder.append(sub);
                count = 0;
            } else if (c == ']') {
                return builder.toString();
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    public static String decodeString3(String s) {
        Stack<String> stack = new Stack<>();
        Stack<Integer> counts = new Stack<>();
        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c >= '0' && c <= '9') {
                count = count * 10 + c - '0';
            } else if (c == '[') {
                counts.push(count);
                count = 0;
                stack.push(builder.toString());
                builder.setLength(0);
            } else if (c == ']') {
                int size = counts.pop();
                String str = builder.toString();
                builder.setLength(0);
                builder.append(stack.pop());
                while (size-- > 0) {
                    builder.append(str);
                }
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }
}
