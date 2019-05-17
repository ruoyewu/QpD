### Question

Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.

**Note:** A leaf is a node with no children.

**Example:**

Given the below binary tree and `sum = 22`,

```
      5
     / \
    4   8
   /   / \
  11  13  4
 /  \    / \
7    2  5   1
```

Return:

```
[
   [5,4,11,2],
   [5,8,4,5]
]
```

### Solution

给定一个二叉树，求这个二叉树中路径上结点之和等于某一数值的所有路径，关于路径的定义，以根结点为起点，叶子结点为重点的一条链，叶子结点为没有孩子的结点。

那么这种问题一定离不开树的遍历了。按照题目描述，只需要将所有的路径都遍历一遍，并记录下每一条路径的和，判断一下就可以得到符合条件的路径，再将其收集起来即可。

```java
public static List<List<Integer>> pathSum(TreeNode root, int sum) {
    List<List<Integer>> result = new ArrayList<>();
    path(result, new ArrayList<>(), root, sum);
    return result;
}
private static void path(List<List<Integer>> result,
                         List<Integer> cur, TreeNode node, int left) {
    if (node == null) return;
    if (node.val == left && node.left == null && node.right == null) {
        cur.add(node.val);
        result.add(cur);
    } else {
        List<Integer> right = new ArrayList<>(cur);
        right.add(node.val);
        path(result, right, node.right, left-node.val);
        cur.add(node.val);
        path(result, cur, node.left, left-node.val);
    }
}
```

这种方法可以解题，但是有一个问题，如代码所示，每一条路径都需要建立一个单独的链表保存路径上的所有结点值，其中包括那些并不满足条件的路径，这就造成了空间的虚耗，同时创建的大量的链表也将耗费许多时间，一种更好的方法的，有多少满足条件的路径，就建立多少链表，这样才能效率最大化。

由此想到与路径相关的，得二叉树非递归后序遍历时，栈中存储的结点就是从根结点到当前结点的路径，于是将这种思想用于本题，得到如下代码：

```java
public static List<List<Integer>> pathSum(TreeNode root, int sum) {
    List<List<Integer>> result = new ArrayList<>();
    path(result, new ArrayList<>(), root, sum);
    return result;
}

private static void path2(List<List<Integer>> result,
                          List<Integer> cur, TreeNode node, int left) {
    if (node == null) return;
    cur.add(node.val);
    path2(result, cur, node.left, left-node.val);
    path2(result, cur, node.right, left-node.val);
    if (node.val == left && node.left == null && node.right == null) {
        result.add(new ArrayList<>(cur));
    }
    cur.remove(cur.size()-1);
}
```

使用后序遍历，使用一个链表保存当前遍历的路径，每访问到一个叶子结点就判断当前路径是否满足条件，只有当满足条件的时候，才将这个操作链表 cur 复制一份并将其加入 result 中，此时，求解过程中实际创建的链表个数是满足条件的路径数+1。