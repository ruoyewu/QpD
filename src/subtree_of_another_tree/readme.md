### Question

Given two non-empty binary trees **s** and **t**, check whether tree **t** has exactly the same structure and node values with a subtree of **s**. A subtree of **s** is a tree consists of a node in **s** and all of this node's descendants. The tree **s** could also be considered as a subtree of itself.

**Example 1:**
Given tree s:

```
     3
    / \
   4   5
  / \
 1   2
```

Given tree t:

```
   4 
  / \
 1   2
```

Return true, because t has the same structure and node values with a subtree of s.

**Example 2:**
Given tree s:

```
     3
    / \
   4   5
  / \
 1   2
    /
   0
```

Given tree t:

```
   4
  / \
 1   2
```

Return false.

### Solution

判断二叉树 t 是否是二叉树 s 的子树，首先要做的就是在 s 中找到 t 的根结点所在，然后让两个树分别往下遍历，判断是否相同。所以这里需要两层遍历：

1.  第一层遍历，在 s 中找到可能作为 t 子树的根结点的结点
2.  第二层遍历，在 s 中某一个结点作为 t 的根结点的条件下，判断两个子树是否相同

```java
public static boolean isSubtree(TreeNode s, TreeNode t) {
    if (s != null && t != null) {
        if (s.val == t.val && (isEqual(s.left, t.left) && isEqual(s.right, t.right))) {
            return true;
        }
        return isSubtree(s.left, t) || isSubtree(s.right, t);
    }
    return t == s;
}
private static boolean isEqual(TreeNode s, TreeNode t) {
    if (s == null && t == null) {
        return true;
    } else if (s == null || t == null) {
        return false;
    } else {
        return s.val == t.val && isEqual(s.left, t.left) && isEqual(s.right, t.right);
    }
}
```

