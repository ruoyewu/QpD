### Question

Given *n* pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

For example, given *n* = 3, a solution set is:

```
[
  "((()))",
  "(()())",
  "(())()",
  "()(())",
  "()()()"
]
```

### Solution

本题可以转化为对于一棵层数为 2n 的满二叉树，求满足以下条件的所有路径：

1.  该路径中左拐与右拐的次数相等，均为 n
2.  对于该路径中的任一位置，其左拐的次数必大于等于右拐次数

其中，左拐为'('，右拐为')'，那么所有的解构成了一个解空间树。于是就得到一种解题方法：先构造解空间树，然后遍历所有路径。在这种思路下，可以有第一种解法：直接构建一个树：

```java
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
```

每个结点都保存起来从根节点到此结点的路径，满足上述条件的树的所有叶子结点表示的路径便是本题的解。当然也可以不具体化成为一棵树，如下解：

```java
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
```

这里虽然没有明显将解题结构表示成一棵树，但是使用的方法是一样的，区别为，第一种方法确确实实表示出了一整棵树，从 head 结点能够访问到树的每一个结点，但是第二种方法，只保存了第 2n 层的所有结点，也就是所有的叶子结点，中间的处于计算过程中的结点都被舍弃了。不过上述两种方法虽然可解，但是都使用到了较为复杂的数据结构，也浪费了诸多的空间（第一种保存了所有的结点，第二种保存了所有的叶子结点），那么现在应该寻求一种方法，不需要再保存 n 个数量级的结点，而是保存有限个数据，如下面这种方法：

```java
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
```

如上，使用到了递归函数，可以看到，全局只使用到了一个 char 数组便完成了所有的计算过程，利用递归函数回溯的特点，如果接着延续上述本题解是一个满足一定条件的二叉树的基础上，这一种方法的解题过程就像是一次对这棵树的遍历过程，solution 函数沿着中序遍历的路径，找到解空间树的每一个叶子结点，然后再走向下一个叶子结点，这种方法相比之前无疑是最好的一种方法，一则只使用到了一个数组，而不用保存 n 数量级的数据，二则也没有使用到复杂的数据结构，虽然使用到了递归函数，但是相对于链表或者栈，还是有一定优势。

除此之外，还有另一种方法，采用的也是递归的方法：

```java
public static List<String> generateParentheses4(int n) {
    List<String> res = new ArrayList<>();
    if (n == 0) {
        res.add("");
    }else {
        for (int i = 0; i < n; i++) {
            for (String left : generateParentheses4(i))
                for (String right : generateParentheses4(n-i-1))
                    res.add('(' + left + ')' + right);
        }
    }
    return res;
}
```

这种方法虽然采用了递归，但是并不是一种比较有效的方法，如当 i 为 3 时，要分别求 3 和 n-4 对应的 res ，但是他们又分别要求  2 和 n-3 以及 n-5 和 4 对应的 res，由此可见需要重复计算多次相同的结果，造成了资源的浪费。