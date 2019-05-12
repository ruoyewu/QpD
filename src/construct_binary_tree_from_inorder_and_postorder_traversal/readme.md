### Question

Given inorder and postorder traversal of a tree, construct the binary tree.

**Note:**
You may assume that duplicates do not exist in the tree.

For example, given

```
inorder = [9,3,15,20,7]
postorder = [9,15,7,20,3]
```

Return the following binary tree:

```
    3
   / \
  9  20
    /  \
   15   7
```

### Solution

根据一棵二叉树的中序遍历和后序遍历还原此二叉树，与 construct binary tree from inorder and preorder traversal 一题类似，有两种解法，第一种是直接根据两个遍历的特点：

1.  后序遍历最后一个数字是根结点
2.  中序遍历中以根结点为中心，两边分别是左右子树的中序遍历

如此便可以得到一个递归的生成树算法：

```java
public static TreeNode buildTree(int[] inorder, int[] postorder) {
    HashMap<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) {
        map.put(inorder[i], i);
    }
    return build(map, postorder,0, inorder.length-1,  0, postorder.length-1);
}

private static TreeNode build(Map<Integer, Integer> map,
                              int[] postorder, int is, int ie, int ps, int pe) {
    if (ps > pe || is-ie != ps-pe) return null;

    int root = postorder[pe];
    TreeNode node = new TreeNode(root);
    int pos = map.get(root);
    node.left = build(map, postorder, is, pos-1, ps, pos-is+ps-1);
    node.right = build(map, postorder, pos+1, ie, pos-is+ps, pe-1);
    return node;
}
```

上解还额外使用了一个哈希表以节省在中序遍历中检索根结点的时间。

除此之外，还可以使用另一种递归解法，按照深度优先生成，有中序遍历的最右边为树的最右结点，后序遍历的最右边为根结点这样一种规则，首先找出最右边的一条路径，然后逐渐往左偏移，相关的逻辑与 construct binary tree from inorder and preorder traversal 一样。

```java
private static int inIdx = 0;
private static int postIdx = 0;
public static TreeNode buildTree(int[] inorder, int[] postorder) {
    inIdx = inorder.length-1;
    postIdx = postorder.length-1;
    return build(inorder, postorder, Integer.MAX_VALUE);
}
private static TreeNode build(int[] inorder, int[] postorder, int rootVal) {
    if (postIdx < 0 || inorder[inIdx] == rootVal) return null;
    TreeNode root = new TreeNode(postorder[postIdx]);
    postIdx--;
    root.right = build(inorder, postorder, root.val);
    inIdx--;
    root.left = build(inorder, postorder, rootVal);
    return root;
}
```

