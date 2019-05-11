### Question

Two elements of a binary search tree (BST) are swapped by mistake.

Recover the tree without changing its structure.

**Example 1:**

```
Input: [1,3,null,null,2]

   1
  /
 3
  \
   2

Output: [3,1,null,null,2]

   3
  /
 1
  \
   2
```

**Example 2:**

```
Input: [3,1,4,null,null,2]

  3
 / \
1   4
   /
  2

Output: [2,1,4,null,null,3]

  2
 / \
1   4
   /
  3
```

**Follow up:**

-   A solution using O(*n*) space is pretty straight forward.
-   Could you devise a constant space solution?

### Solution

题目给出一个二叉搜索树，并将这个二叉树中任选两个结点交换位置，要求将这个二叉搜索树还原成正确的搜索树。

那么首先看二叉搜索树的规律，如果将这个二叉树按照中序遍历，那么遍历完了之后得到的序列，是一个递增序列，如果将其中两个结点交换位置，那么此时的中序遍历必然不是一个递增序列，而是其中一个结点大于两边的数，其中一个结点小于两边的数，这两个结点就是被交换的两个结点。如将`[1,...,i,...,j,...,n]`换成`[1,...,j,...,i,...,n]`，其中 j 大于两边的数，i 小于两边的数。

所以要找到这两个结点也是比较容易的，中序遍历交换过的二叉树，根据上面的描述，找到两个被交换的结点，然后将结点的值相互交换，便可以得到原来的二叉搜索树。

```java
private static TreeNode first, second, pre;
public static void recoverTree(TreeNode root) {
    first = second = pre = null;
    traversal(root);
    int val = first.val;
    first.val = second.val;
    second.val = val;
}
private static void traversal(TreeNode node) {
    if (node == null) return;
    traversal(node.left);
    if (pre != null && pre.val > node.val) {
        if (first == null) first = pre;
        second = node;
    } else if (first != null && first.val < node.val) {
        return;
    }
    pre = node;
    traversal(node.right);
}
```

如上，`traversal()`就是一个中序遍历的递归方法，通过 pre 记录上一个访问的结点，如果这是一个正常的二叉树，应当始终有`pre.val < node.val`，所以当`pre.val > node.val`时意味着不正常，也就是说交换的两个结点导致了这种情况的出现，交换的结点也正是出现在这种地方。另外，如果已经找到了两个结点，就结束遍历，逻辑在`else if`中。

这种方法的思想是，先遍历整个二叉树，找到两个被交换的结点，然后再将它们交换回来即可。还可以有另一种方法，直接通过二叉树的特点，左子树所有结点小于当前结点，右子树所有结点大于当前结点这样一种规则，找到不符合条件的结点，然后将其与当前结点交换，使其逐层满足条件，最终达到目的，当然这种方法与前一种方法相比，一般一次调整并不能得到结果，需要反复的调整才行。先看代码：

```java
public static void recoverTree(TreeNode root) {
    recover(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    System.out.println(root.val);
}
private static TreeNode recover(TreeNode node, int from, int to) {
    if (node == null) return null;
    int val = node.val;
    if (val < from || val > to) {
        return node;
    }
    TreeNode left = recover(node.left, from, val-1);
    if (left != null) {
        if (left.val < from || left.val > to) {
            return left;
        } else {
            node.val = left.val;
            left.val = val;
            return recover(node, from, to);
        }
    }
    val = node.val;
    TreeNode right = recover(node.right, val+1, to);
    if (right != null) {
        if (right.val < from || right.val > to) {
            return right;
        } else {
            node.val = right.val;
            right.val = val;
            return recover(node, from, to);
        }
    }
    return null;
}
```

`recover()`是一个递归的方法，它会返回一个结点，是当前子树中不满足条件的结点，方法中的判断分为三步，首先判断子树的根结点是否非法，如果非法则交给上层处理。然后调用`recover()`分别找到左子树和右子树中的非法结点，如果在左子树中找到了，则判断这个非法结点在当前的子树中是否可以处理，即是否满足`left.val > from && left.val < to`，也就是这个结点是否是在当前子树上，如果不在，则移交给上层处理，如果在，则交换子树的根结点和这个非法结点的值，然后重新调用`recover()`判断当前子树是否合法。同理，如果在右子树中找到，也按照上述步骤处理，如果没有找到非法结点，则说明这颗子树是合法的，返回 null 表示没有需要处理的结点。