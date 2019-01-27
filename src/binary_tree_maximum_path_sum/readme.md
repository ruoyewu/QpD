### Question

Given a **non-empty** binary tree, find the maximum path sum.

For this problem, a path is defined as any sequence of nodes from some starting node to any node in the tree along the parent-child connections. The path must contain **at least one node** and does not need to go through the root.

**Example 1:**

```
Input: [1,2,3]

       1
      / \
     2   3

Output: 6
```

**Example 2:**

```
Input: [-10,9,20,null,null,15,7]

   -10
   / \
  9  20
    /  \
   15   7

Output: 42
```

### Solution

求一棵二叉树中的某条路径，使得这条路径中的结点值相加为所有路径中最大的一个。首先要明确这里的“路径”是什么，题目中关于路径的定义，是指从视觉效果上来看，能够一条路走到底，中间不存在分叉的情况，对于题目中的两个例子来说，它们的路径分别是`[2,1,3]`和`[15,20,7]`，也就是说，一条路径是可以包括某个子树的根结点的，因为这里的根结点并不是路径的起点，虽然从根结点来看它是分叉的一条路，但是换个起点之后他就不分叉了。

所以，明确这里这个路径的定义，是解题的关键。即，这条路径从树的角度而言，是可以分叉的，但是只能在树的根结点分叉，其他的结点是不可以分叉的。从树的根结点开始，可以有从左孩子开始的一条不分叉的路径，也可以有从右孩子开始的一条不分叉的路径，于是，只要将一棵树中，所有的子树的最长路径求出来，比较取最大就是本题的解。

对于树中的任意一个结点而言，如果路径从这里分叉，那么必然分别走向该结点的左子树和右子树，而且左右各是一条不分叉的路径。那么可以使用递归求解，如下：

```java
private static int maxPath;
public static int maxPathSum(TreeNode root) {
    maxPath = Integer.MIN_VALUE;
    maxPath(root);
    return maxPath;
}
private static int maxPath(TreeNode node) {
    if (node == null) return 0;
    int left = maxPath(node.left);
    int right = maxPath(node.right);
    int max = node.val;
    if (left > 0) max += left;
    if (right > 0) max += right;
    if (max > maxPath) {
        maxPath = max;
    }
    return Math.max(0, Math.max(left, right)) + node.val;
}
```

上面的递归函数`maxPath`求出了以结点`node`为起始的一条不分叉的路径上结点值的和，并且在这个求解过程中，还计算了以`node`为根结点（即分叉点），能够得到的最长路径，它通过递归调用`node.left`和`node.right`的递归函数求出。因为这是一个递归函数，所以在递归调用的过程中，以`root`为根结点的树中的每一个结点都会被递归函数求算，所以，递归调用完成之后，在这些路径中结点和最大的那条路径必然是所求路径。