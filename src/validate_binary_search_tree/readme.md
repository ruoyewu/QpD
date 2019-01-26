### Question

Given a binary tree, determine if it is a valid binary search tree (BST).

Assume a BST is defined as follows:

-   The left subtree of a node contains only nodes with keys **less than** the node's key.
-   The right subtree of a node contains only nodes with keys **greater than** the node's key.
-   Both the left and right subtrees must also be binary search trees.

**Example 1:**

```
Input:
    2
   / \
  1   3
Output: true
```

**Example 2:**

```
    5
   / \
  1   4
     / \
    3   6
Output: false
Explanation: The input is: [5,1,4,null,null,3,6]. The root node's value
             is 5 but its right child's value is 4.
```

### Solution

一个排序二叉树的中序遍历序列必然是有序序列，相反的，一个非排序二叉树的中序遍历序列必然是无序序列。所以，判断一个二叉树是否是排序二叉树可以从它的中序遍历入手，判断中序遍历序列是否有序。

#### S1:顺序法

按照中序遍历递归的过程中的访问顺序，可以对其是否是排序二叉树。

```java
private static long last;
private static boolean isValidate = true;
public static boolean isValidBST(TreeNode root) {
    last = Long.MIN_VALUE;
    isValidate = true;
    validate(root);
    return isValidate;
}
public static void validate(TreeNode node) {
    if (isValidate && node != null) {
        validate(node.left);
        if (node.val <= last) {
            isValidate = false;
        } else {
            last = node.val;
        }
        validate(node.right);
    }
}
```

每访问到一个结点，那么它的值如果不大于上一访问结点的值，那么它就应该是一个非排序二叉树，如上是一个递归遍历的过程，在遍历的过程中加入比较即可。

或者还可以使用下面的非递归的遍历过程：

```java
public static boolean isValidBST(TreeNode root) {
    Stack<TreeNode> stack = new Stack<>();
    long last = Long.MIN_VALUE;
    TreeNode node = root;
    while (node != null) {
        stack.push(node);
        node = node.left;
    }
    while (!stack.empty()) {
        node = stack.pop();
        if (last >= node.val) {
            return false;
        } else {
            last = node.val;
        }
        node = node.right;
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }
    return true;
}
```

#### S2:区间法

对于二叉排序树的每一个结点，都有一定的取值区间：如果某个结点 A 是结点 B 地左子树上的某一结点，那么 A 的值必须小于 B 的值，如果 A 是结点 C 的右子树上的某个结点，那么 A 的值必须大于 C 的值。根据这个规则，我们就可以对某棵树是否满足排序二叉树进行判断：

```java
public static boolean isValidBST(TreeNode root) {
    return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
}
public static boolean validate(TreeNode node, long min, long max) {
    if (node == null) return true;
    if (node.val >= max || node.val <= min) return false;
    return validate(node.left, min, node.val) && validate(node.right, node.val, max);
}
```

