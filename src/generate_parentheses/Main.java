package generate_parentheses;

import java.util.*;

/**
 * User: wuruoye
 * Date: 2019/1/12 15:30
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int n = new Scanner(System.in).nextInt();
        List<String> ss = generateParentheses4(n);
        System.out.println(count);
//        for (String s : ss) {
//            System.out.println(s);
//        }
    }

    public static List<String> generateParentheses(int n) {
        if (n <= 0) {
            return Collections.singletonList("");
        }

        List<String> result = new ArrayList<>();
        List<String> _result = new ArrayList<>();
        List<Integer> left = new ArrayList<>();
        List<Integer> _left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> _right = new ArrayList<>();

        result.add("(");
        left.add(1);
        right.add(0);

        long round = n * 2 - 1;
        while (round > 0) {
            round--;
            for (int i = 0; i < result.size(); i++) {
                String cur = result.get(i);
                int l = left.get(i);
                int r = right.get(i);
                if (l < n) {
                    _result.add(cur + '(');
                    _left.add(l+1);
                    _right.add(r);
                }
                if (r < l) {
                    _result.add(cur + ')');
                    _left.add(l);
                    _right.add(r+1);
                }
            }

            result = _result;
            left = _left;
            right = _right;
            _result = new ArrayList<>();
            _left = new ArrayList<>();
            _right = new ArrayList<>();
        }

        return result;
    }

    public static List<String> generateParentheses2(int n) {
        if (n == 0) {
            return Collections.singletonList("");
        }

        LinkedList<String> result = new LinkedList<>();
        LinkedList<Integer> left = new LinkedList<>();
        LinkedList<Integer> right = new LinkedList<>();

        result.add("(");
        left.add(1);
        right.add(0);

        long round = n * 2 - 1;
        while (round > 0) {
            round--;
            int max = result.size();
            for (int i = 0; i < max; i++) {
                String cur = result.removeFirst();
                int l = left.removeFirst();
                int r = right.removeFirst();
                if (l < n) {
                    result.add(cur + '(');
                    left.add(l+1);
                    right.add(r);
                }
                if (r < l) {
                    result.add(cur + ')');
                    left.add(l);
                    right.add(r+1);
                }
            }
        }

        return result;
    }

    // 将需要使用的list结构转化为数组，并将原本频繁的增删操作转变为修改操作
    // 利用栈的优势，大大减少了运行时间
    private static List<String> result = new ArrayList<>();

    public static List<String> generateParentheses3(int n) {
        result.clear();

        solution(new char[n*2], 0, 0, 0, n);

        return result;
    }

    public static void solution(char[] res, int pos, int left, int right, int n) {
        if (pos == n*2) {
            result.add(new String(res));
        }else {
            if (left < n) {
                res[pos] = '(';
                solution(res, pos+1, left+1, right, n);
            }
            if (right < left) {
                res[pos] = ')';
                solution(res, pos+1, left, right+1, n);
            }
        }
    }

    // 构建解空间树
    public static List<String> generateParentheses5(int n) {
        List<String> result = new ArrayList<>();
        class Node {
            String s;
            int l,r;
            Node left;
            Node right;
            public Node(String s, int l, int r) {
                this.s = s;
                this.l = l;
                this.r = r;
            }
        }

        Node root = new Node("", 0, 0);
        Node node = root;
        Stack<Node> stack = new Stack<>();
        stack.push(node);
        while (!stack.empty()) {
            node = stack.pop();
            if (node.l + node.r < n*2) {
                if (node.l < n) {
                    Node no = new Node(node.s + '(', node.l+1, node.r);
                    node.left = no;
                    stack.push(no);
                }
                if (node.r < node.l) {
                    Node no = new Node(node.s + ')', node.l, node.r+1);
                    node.right = no;
                    stack.push(no);
                }
            } else {
                result.add(node.s);
            }
        }

        return result;
    }

    private static int count = 0;
    // 较有意思
    public static List<String> generateParentheses4(int n) {
        List<String> res = new ArrayList<>();
        if (n == 0) {
            res.add("");
        }else {
            for (int i = 0; i < n; i++) {
                for (String left : generateParentheses4(i))
                    for (String right : generateParentheses4(n-i-1)) {
                        res.add('(' + left + ')' + right);
                        count++;
                    }
            }
        }
        return res;
    }
}
