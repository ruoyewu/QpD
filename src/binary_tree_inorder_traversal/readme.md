### Question

Given a binary tree, return the *inorder* traversal of its nodes' values.

**Example:**

```
Input: [1,null,2,3]
   1
    \
     2
    /
   3

Output: [1,3,2]
```

**Follow up:** Recursive solution is trivial, could you do it iteratively?

### Solution

#### S1:递归

递归法方便理解，也方便写：

```java
public static List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    inorder(result, root);
    return result;
}
public static void inorder(List<Integer> result, TreeNode node) {
    if (node != null) {
        inorder(result, node.left);
        result.add(node.val);
        inorder(result, node.right);
    }
}
```



#### S2:非递归-使用栈

对于任意一个树，其中序遍历要访问的第一个结点就是该树的最左结点，完了之后，再对最左结点的右子树进行中序遍历，依次类推，如果使用栈保存经过的路径，那么有：

```java
public static List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Stack<TreeNode> stack = new Stack<>();
    TreeNode node = root;
    while (node != null) {
        stack.push(node);
        node = node.left;
    }
    while (!stack.empty()) {
        node = stack.pop();
        result.add(node.val);
        node = node.right;
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }
    return result;
}
```



#### S3:非递归-变形

二叉树可以用作二叉排序树，对一个二叉排序树而言，其中序遍历的序列就是一个有序序列。当一棵树的所有结点都只有右孩子的时候，就会只有一条单一路径，这条路径就是中序遍历的结果。如果将一个排序二叉树变形，并且不改变其作为排序二叉树的规则，如下：

![](./binary_tree_inorder_traversal_1.jpg)

一步步消除所有的左子树，便可以最终使其变成只有右孩子的树。同样的，对于一个普通的二叉树，其中序遍历也是按照这样一个规则的序列，我们也可以通过消除所有的左子树的方式求解。如下：

```java
public static List<Integer> inorderTraversal3(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    TreeNode cur = root;
    TreeNode pre;
    while (cur != null) {
        if (cur.left == null) {
            result.add(cur.val);
            cur = cur.right;
        } else {
            pre = cur.left;
            while (pre.right != null) {
                pre = pre.right;
            }
            pre.right = cur;
            TreeNode tmp = cur;
            cur = cur.left;
            tmp.left = null;
        }
    }
    return result;
}
```

