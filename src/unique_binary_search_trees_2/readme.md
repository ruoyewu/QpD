### Question

Given an integer *n*, generate all structurally unique **BST's** (binary search trees) that store values 1 ... *n*.

**Example:**

```
Input: 3
Output:
[
  [1,null,3,2],
  [3,2,null,1],
  [3,1,null,null,2],
  [2,1,3],
  [1,null,2,null,3]
]
Explanation:
The above output corresponds to the 5 unique BST's shown below:

   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2     1            3
```

### Solution

unique binary search trees 一题的扩展，上一题只是求出可能的二叉搜索树的个数，本题要求出每一棵具体的树。要求出一棵树，首先需要确定一个根结点，根结点可以是任何一个结点，所以需要一个遍历。其次，因为这是一个二叉搜索树，所以当根结点确定之后，必然有比根结点小的数构成左子树，比根结点大的数构成右子树，而左右子树的生成，与这个二叉树的生成一样，是一个递归的过程。

```java
public static List<TreeNode> generateTrees(int n) {
    if (n <= 0) return new ArrayList<>();
    return generate(1, n);
}
private static List<TreeNode> generate(int start, int end) {
    if (start > end) return null;
    List<TreeNode> result = new ArrayList<>();
    for (int i = start; i <= end; i++) {
        List<TreeNode> left = generate(start, i-1);
        List<TreeNode> right = generate(i+1, end);
        if (left != null && right != null) {
            for (TreeNode l : left) {
                for (TreeNode r : right) {
                    TreeNode root = new TreeNode(i);
                    root.left = l;
                    root.right = r;
                    result.add(root);
                }
            }
        } else if (left != null) {
            for (TreeNode l : left) {
                TreeNode root = new TreeNode(i);
                root.left = l;
                result.add(root);
            }
        } else if (right != null) {
            for (TreeNode r : right) {
                TreeNode root = new TreeNode(i);
                root.right = r;
                result.add(root);
            }
        } else {
            result.add(new TreeNode(i));
        }
    }
    return result;
}
```

代码本身的逻辑比较简单，主要是后面关于生成树的过程，需要根据左右子树是否为空，决定如何生成，共有四个分支。