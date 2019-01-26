### Question

Given a binary tree, return the *level order* traversal of its nodes' values. (ie, from left to right, level by level).

For example:
Given binary tree `[3,9,20,null,null,15,7]`,

```
    3
   / \
  9  20
    /  \
   15   7
```

return its level order traversal as:

```
[
  [3],
  [9,20],
  [15,7]
]
```

### Solution

层序遍历，也有递归与非递归两种。

#### S1:递归

因为层序遍历是按照层数划分，所以要额外一个变量保存当前的层数：

```java
public static List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    level(result, root, 1);
    return result;
}
public static void level(List<List<Integer>> result, TreeNode node, int level) {
    if (node != null) {
        while (result.size() < level) {
            result.add(new ArrayList<>());
        }
        result.get(level-1).add(node.val);
        level(result, node.left, level+1);
        level(result, node.right, level+1);
    }
}
```

因为这是一个从上往下的扫描，访问到下层某个节点的时候，肯定已经访问过这个结点的父结点，所以上面的`while`循环只使用`if`判断也是可以的。通过在每个递归函数中保存当前结点的层数，再找到这一层在`result`中对应的位置，便可以得到层序遍历的效果。

#### S2:非递归

还有一种方法是直接的一层一层遍历，这时层数自然是逐渐增加的，只需要将每一层的所有结点保存下来，再利用这一层结点作为入口访问下一层的所有结点即可。

```java
public static List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) {
        return result;
    }
    LinkedList<TreeNode> list = new LinkedList<>();
    TreeNode node;
    list.add(root);
    int left;
    while (!list.isEmpty()) {
        left = list.size();
        List<Integer> level = new ArrayList<>();
        for (int i = 0; i < left; i++) {
            node = list.removeFirst();
            level.add(node.val);
            if (node.left != null) {
                list.add(node.left);
            }
            if (node.right != null) {
                list.add(node.right);
            }
        }
        result.add(level);
    }
    return result;
}
```

