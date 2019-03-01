### Question

Given a binary tree, return the *postorder* traversal of its nodes' values.

**Example:**

```
Input: [1,null,2,3]
   1
    \
     2
    /
   3

Output: [3,2,1]
```

**Follow up:** Recursive solution is trivial, could you do it iteratively?

### Solution

#### S1:递归

二叉树的后序遍历，首先，使用递归是最简单的：

```java
private static List<Integer> result = new ArrayList<>();
public static List<Integer> postorderTraversal(TreeNode root) {
    result.clear();
    traversal(root);
    return result;
}
private static void traversal(TreeNode node) {
    if (node != null) {
        traversal(node.left);
        traversal(node.right);
        result.add(node.val);
    }
}
```

#### S2:非递归

或者还可以使用非递归遍历，由于后序遍历的顺序是 左-右-中 ，所以这显然要使用栈保存起来访问路径。对于一个结点来说，首先需要将其入栈，访问其左子树，完了之后左子树会出栈，再次到达这个结点，此时还要接着将其入栈，然后访问其右子树，所有对于后序遍历的非递归实现来说，最重要的就是如果能够分辨出何时访问完了左子树，何时访问完了右子树。如下：

```java
public static List<Integer> postorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Stack<TreeNode> stack = new Stack<>();
    TreeNode node = root, pre = null;
    while (node != null) {
        stack.push(node);
        node = node.left;
    }
    while (!stack.empty()) {
        node = stack.pop();
        if (node.right == null || node.right == pre) {
            result.add(node.val);
            pre = node;
        } else {
            stack.push(node);
            node = node.right;
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
    }
    return result;
}
```

这里是通过一个 pre 结点完成的判断。当`node.right != pre`时访问完了左子树，当`node.right == pre`时才是访问完了右子树。

#### S3:非递归另一实现

后序遍历的访问顺序是 左-右-中 ，如果将这个访问顺序反过来，如 中-右-左 ，那么此时访问的结果应该与上面后序遍历的访问结果正好是两个逆序。既然后序遍历的非递归访问不好实现，那么对于非递归访问的逆序呢？这样确实能够实现，并且代码更加清晰：

```java
public static List<Integer> postorderTraversal(TreeNode root) {
    LinkedList<Integer> result = new LinkedList<>();
    Stack<TreeNode> stack = new Stack<>();
    if (root != null) {
        stack.push(root);
    }
    TreeNode node;
    while (!stack.empty()) {
        node = stack.pop();
        result.addFirst(node.val);
        if (node.left != null) {
            stack.push(node.left);
        }
        if (node.right != null) {
            stack.push(node.right);
        }
    }
    return result;
}
```

对于 中-右-左 这种顺序的访问，其实就是前序遍历的一种变形（前序遍历为 中-左-右 ），其实现就是上面的那个 while 循环，我们使用了 LinkedList 作为结果列表，并且使用头插法，这样得到的结果便与后序遍历的结果一致了。