### Question

Given a binary tree, you need to compute the length of the diameter of the tree. The diameter of a binary tree is the length of the **longest** path between any two nodes in a tree. This path may or may not pass through the root.

**Example:**
Given a binary tree 

```
          1
         / \
        2   3
       / \     
      4   5    
```

Return **3**, which is the length of the path [4,2,1,3] or [5,2,1,3].

**Note:** The length of path between two nodes is represented by the number of edges between them.

### Solution

对于一棵二叉树，求出最长的一条路径的长度，此处的路径可以是任意两个结点之间的，所以，最长的一条路径必然是以某个结点为中转、分别从这个结点的左右子结点延伸到叶子结点的两条“无转向”的路径和。所以只需要求出，以所有结点为中转的路径长度，取其中最大的即可。

```java
private static int length;
public static int diameterOfBinaryTree(TreeNode root) {
    length = 0;
    diameter(root);
    return length;
}
private static int diameter(TreeNode node) {
    if (node == null) {
        return 0;
    } else {
        int left = diameter(node.left);
        int right = diameter(node.right);
        if (left + right > length) {
            length = left + right;
        }
        return (left > right ? left : right) + 1;
    }
}
```

如上，每个递归函数`diameter()`能够求出从当前结点 node 到叶子结点的最长的一条路径的长度，然后在每个函数中，还计算了使用当前结点 node 作为中转的最长路径长度。