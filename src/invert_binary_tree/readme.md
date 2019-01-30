### Question

Invert a binary tree.

**Example:**

Input:

```
     4
   /   \
  2     7
 / \   / \
1   3 6   9
```

Output:

```
     4
   /   \
  7     2
 / \   / \
9   6 3   1
```

**Trivia:**
This problem was inspired by [this original tweet](https://twitter.com/mxcl/status/608682016205344768) by [Max Howell](https://twitter.com/mxcl):

>   Google: 90% of our engineers use the software you wrote (Homebrew), but you can’t invert a binary tree on a whiteboard so f*** off.

### Solution

#### S1:递归

关于树的相关算法递归总不会缺席。

```java
public static TreeNode invertTree(TreeNode root) {
    if (root == null) return null;
    TreeNode right = invertTree(root.left);
    root.left = invertTree(root.right);
    root.right = right;
    return root;
}
```

每个递归函数完成一对左右结点的翻转，每个结点都会经历这么一个递归函数，so，翻转完成。

#### S2:非递归

总能尝试着将递归转化为非递归，使用栈即可。

```java
public static TreeNode invertTree2(TreeNode root) {
    if (root == null) return null;
    Stack<TreeNode> stack = new Stack<>();
    stack.push(root);
    TreeNode node;
    while (!stack.empty()) {
        node = stack.pop();
        TreeNode left = node.left, right = node.right;
        node.left = right;
        node.right = left;
        if (left != null) stack.push(left);
        if (right != null) stack.push(right);
    }
    return root;
}
```

每个循环翻转一对左右结点，使用栈保存所有待翻转的结点。