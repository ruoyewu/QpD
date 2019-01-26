### Question

Given a binary tree, flatten it to a linked list in-place.

For example, given the following tree:

```
    1
   / \
  2   5
 / \   \
3   4   6
```

The flattened tree should look like:

```
1
 \
  2
   \
    3
     \
      4
       \
        5
         \
          6
```

### Solution

将一棵二叉树按照先序遍历的顺序连成一棵单叉树（只有右孩子的二叉树），那么一种方法是，先按照先序遍历的顺序遍历整个二叉树，并在遍历的过程中将其连接起来。

#### S1:遍历法

先序遍历分两种：递归法和非递归法，如下：

```java
private static TreeNode node;
public static void flatten(TreeNode root) {
    node = new TreeNode(0);
    inorder(root);
}
public static void inorder(TreeNode node) {
    if (node != null) {
        TreeNode left = node.left;
        TreeNode right = node.right;
        node.left = null;
        Main.node.right = node;
        Main.node = node;
        inorder(left);
        inorder(right);
    }
}
```

```java
public static void flatten(TreeNode root) {
    Stack<TreeNode> stack = new Stack<>();
    TreeNode node = new TreeNode(0);
    if (root != null) {
        stack.push(root);
    }
    while (!stack.empty()) {
        node.right = stack.pop();
        node = node.right;
        if (node.right != null) {
            stack.push(node.right);
        }
        if (node.left != null) {
            stack.push(node.left);
        }
        node.left = null;
    }
}
```

上面的两种方法中，使用 node 作为连接上下两次遍历的过渡，使得分散的先序遍历连成一棵单叉树。

#### S2:分解法

对于一棵二叉树来说，可以将其分解成三个部分：根结点、左子树、右子树。而整棵树的列表其实就是等于`根结点+左子树列表+右子树列表`，所以，按照这样一种方法，可以将一棵树分成若干个小树，然后再将小树形成的列表结合起来，就能逐渐得到原始的二叉树形成的列表。

```java
public static void flatten2(TreeNode root) {
    toList(root);
}
public static TreeNode toList(TreeNode node) {
    if (node == null) {
        return null;
    }
    if (node.left == null && node.right == null) {
        return node;
    }
    TreeNode leftTail = toList(node.left);
    TreeNode rightTail = toList(node.right);
    TreeNode left = node.left, right = node.right;
    if (left != null) {
        node.left = null;
        left.left = null;
        node.right = left;
        leftTail.right = right;
    }
    return right != null ? rightTail : leftTail;
}
```

如上，对于一个根结点 node 而言，如果其左右子树各形成了一个列表，那么如何整合这两个列表和根结点？需要知道各列表的首尾结点才能进行组合，其中，对于左子树而言，其形成列表的首结点必然是左子树的根结点，所以我们需要知道它的尾结点，右子树同理，知道了各首尾结点之后，按照`根结点+左子树首->左子树尾+右子树首`的方法，就可以得到整棵树形成的列表，所以，在`toList`方法中我们返回的是形成的列表的尾结点。