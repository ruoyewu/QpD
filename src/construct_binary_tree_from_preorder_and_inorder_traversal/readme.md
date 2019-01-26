### Question

Given preorder and inorder traversal of a tree, construct the binary tree.

**Note:**
You may assume that duplicates do not exist in the tree.

For example, given

```
preorder = [3,9,20,15,7]
inorder = [9,3,15,20,7]
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

根据一个二叉树的前序遍历序列和中序遍历序列可以确定二叉树的形状。有如下规则：

1.  preorder 的第一位是根结点
2.  inorder 中，位于根结点左侧的序列组成根结点的左子树，位于根结点右侧的序列组成根结点的右子树

故而，根结点的左子树和右子树也分别对应着各自的 preorder 和 inorder ，也能够求出来，然后求出子树，再一步步组成最终的树，可以用以下递归求解：

```java
public static TreeNode buildTree(int[] preorder, int[] inorder) {
    if (preorder.length == 0) return null;
    return build(preorder, inorder, 0, 0, inorder.length-1);
}
public static TreeNode build(int[] preorder, int[] inorder, int sp, int si, int ei) {
    if (si == ei) {
        return new TreeNode(preorder[sp]);
    } else {
        TreeNode node = new TreeNode(preorder[sp]);
        int val = preorder[sp];
        int left;
        for (left = 0; left + si <= ei && inorder[si+left] != val; left++) ;
        if (left > 0) {
            node.left = build(preorder, inorder, sp+1, si, si+left-1);
        }
        if (left+si < ei) {
            node.right = build(preorder, inorder, sp+left+1, si+left+1, ei);
        }
        return node;
    }
}
```

或者还可以使用另一种方式，对于一对前序遍历和中序遍历序列：

1.  preorder 的第一位是根结点
2.  inorder 的第一位是树的最左结点
3.  inorder 中根结点左边的必然是左子树的中的结点，也只有左边的是左子树中的结点，所以对于一个根结点，我们判断它是否是这一根结点的左子树中某一结点，只需要看在 inorder 中它是否在根结点的左边

所以，有：

```java
private static int preIdx = 0;
private static int inIdx = 0;
public static TreeNode buildTree2(int[] preorder, int[] inorder) {
    if (preorder == null || preorder.length == 0 || inorder == null || inorder.length == 0
            || preorder.length != inorder.length) {
        return null;
    }
    return build(preorder, inorder, Integer.MAX_VALUE);
}
private static TreeNode build(int[] preorder, int[] inorder, int rootVal) {
    if (preIdx >= preorder.length || inorder[inIdx] == rootVal) {
        return null;
    }
    TreeNode root = new TreeNode(preorder[preIdx]);
    preIdx += 1;
    root.left = build(preorder, inorder, root.val);
    inIdx += 1;
    root.right = build(preorder, inorder, rootVal);
    return root;
}
```

以 preIdx 为根据，利用 inIndx 判断是否还有子结点，注意这个递归函数的 rootVal ，我们发现，对于求左子树与求右子树的 rootVal 不一样，在求一个根结点的左子树的时候，rootVal 就等于根结点的值，意味只要在 inorder 中，在这个根结点左边的必然都是这个根结点的左子树，所以，这个 rootVal 其实就是规定了左子树的存在范围，同样的，求右子树的时候，这个 rootVal 是当前结点的根结点的值，这个也好理解，在 inorder 中，在当前结点的右边，在当前结点父结点的左边的值，就是当前结点的右子树的所在。通过 rootVal 划分好各子树的范围之后，根据 preorder 的第一个值为根结点的值这样一个规则，便可以一步步求出二叉树。