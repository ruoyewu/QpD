### Question

Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).

For example, this binary tree `[1,2,2,3,4,4,3]` is symmetric:

```
    1
   / \
  2   2
 / \ / \
3  4 4  3
```

But the following `[1,2,2,null,3,null,3]` is not:

```
    1
   / \
  2   2
   \   \
   3    3
```

**Note:**
Bonus points if you could solve it both recursively and iteratively.

### Solution

判断一个二叉树是否是沿着根结点对称的，一种方法是分别对根结点的左右子树遍历，判断对称的位置上的值是否相等。而二叉树的遍历，可是分为递归遍历和非递归遍历。

#### S1:递归

如下：

```java
public static boolean isSymmetric(TreeNode root) {
    if (root == null) return true;
    return symmetric(root.left, root.right);
}
public static boolean symmetric(TreeNode left, TreeNode right) {
    if (left == null && right == null) return true;
    if (left != null && right != null) {
        return (left.val == right.val) &&
                symmetric(left.left, right.right) && symmetric(left.right, right.left);
    }
    return false;
}
```

分别从两棵子树的根结点开始，逐步向下层判断，这里有一点需要注意，因为是关于根结点对称的，所以 left 的左子树应该是与 right 的右子树对应，left 的右子树与 right 的左子树对应。

#### S2:非递归

使用栈保存所有的待检测结点对：

```java
public static boolean isSymmetric(TreeNode root) {
    if (root == null) return true;
    Stack<TreeNode> stack = new Stack<>();
    stack.push(root.left);
    stack.push(root.right);
    TreeNode left, right;
    while (!stack.empty()) {
        left = stack.pop();
        right = stack.pop();
        if (left == null && right == null) {
        }else if (left == null || right == null) {
            return false;
        } else {
            if (left.val == right.val) {
                stack.push(left.left);
                stack.push(right.right);
                stack.push(left.right);
                stack.push(right.left);
            } else {
                return false;
            }
        }
    }
    return true;
}
```

