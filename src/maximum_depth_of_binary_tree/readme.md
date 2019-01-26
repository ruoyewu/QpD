### Question

Given a binary tree, find its maximum depth.

The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.

**Note:** A leaf is a node with no children.

**Example:**

Given binary tree `[3,9,20,null,null,15,7]`,

```
    3
   / \
  9  20
    /  \
   15   7
```

return its depth = 3.

### Solution

求取一棵二叉树的最大深度，也算是树的遍历的一种变形，可以分为递归和非递归。

#### S1:递归

某棵树的高度，等于它的左右子树中高度较大的那个加上 1 ，所以有：

```java
public static int maxDepth(TreeNode root) {
    if (root == null) return 0;
    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
}
```

#### S2:非递归

非递归遍历的时候，可以使用层序遍历或者是前序遍历这种，不过因为前序遍历属于深度优先遍历，需要保存每个走过的点的深度，而使用层序遍历的时候，因为每次遍历的都是属于同一个深度的结点，所以不需要给每个结点附加一个深度值。

```java
public static int maxDepth(TreeNode root) {
    if (root == null) return 0;
    LinkedList<TreeNode> list = new LinkedList<>();
    list.add(root);
    int level = 0, left;
    TreeNode node;
    while (!list.isEmpty()) {
        level++;
        left = list.size();
        while (left-- > 0) {
            node = list.removeFirst();
            if (node.left != null) {
                list.add(node.left);
            }
            if (node.right != null) {
                list.add(node.right);
            }
        }
    }
    return level;
}
```

