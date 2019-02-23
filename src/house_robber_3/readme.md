### Question

The thief has found himself a new place for his thievery again. There is only one entrance to this area, called the "root." Besides the root, each house has one and only one parent house. After a tour, the smart thief realized that "all houses in this place forms a binary tree". It will automatically contact the police if two directly-linked houses were broken into on the same night.

Determine the maximum amount of money the thief can rob tonight without alerting the police.

**Example 1:**

```
Input: [3,2,3,null,3,null,1]

     3
    / \
   2   3
    \   \ 
     3   1

Output: 7 
Explanation: Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
```

**Example 2:**

```
Input: [3,4,5,1,3,null,1]

     3
    / \
   4   5
  / \   \ 
 1   3   1

Output: 9
Explanation: Maximum amount of money the thief can rob = 4 + 5 = 9.
```

### Solution

题目为：有很多互相连接的房间，这些房间正好连成一棵二叉树的样子，小偷需要从这棵树的根结点房间出发开始偷东西。为了不被抓住，小偷不能偷相连的两个房间。

于是，有这么两个要求：

1.  如果当前结点的父结点没有被偷，那么当前结点可偷可不偷
2.  如果当前结点的父结点被偷了，那么当前结点不可偷

所以，当从根结点判断开始的时候，有：

1.  如果偷当前结点，那么下面能偷的只能是自己的孙子结点
2.  如果不偷当前结点，那么下面可以偷自己的孩子结点

代码如下：

```java
public static int rob(TreeNode node) {
    if (node == null) return 0;
    int max = rob(node.left) + rob(node.right);
    int m = node.val;
    if (node.left != null) {
        m += rob(node.left.left) + rob(node.left.right);
    }
    if (node.right != null) {
        m += rob(node.right.left) + rob(node.right.right);
    }
    max = Math.max(max, m);
    return max;
}
```

在这种方法中可以看到，从结点 node 有两条线，分别是走向 node 的子结点和孙子结点，这就导致一个问题：会产生重复计算。比如，对于从 node 结点到它的孙子结点，可以有两种方式：`node -> 孙子`和`node -> 孩子 -> 孩子`。从两条路大到达同一结点，就是说会从两个递归函数分别进入 node 的孙子结点，那么势必会计算两次这个结点的解。

所以需要采取一定的措施，使得重复进入同一结点的时候避免重复计算，即使用缓存：

```java
public static int rob(TreeNode root) {
    return rob(root, new HashMap<>());
}
private static int rob(TreeNode node, Map<TreeNode, Integer> saved) {
    if (node == null) return 0;
    if (saved.containsKey(node)) return saved.get(node);
    int max = rob(node.left, saved) + rob(node.right, saved);
    int m = node.val;
    if (node.left != null) {
        m += rob(node.left.left, saved) + rob(node.left.right, saved);
    }
    if (node.right != null) {
        m += rob(node.right.left, saved) + rob(node.right.right, saved);
    }
    max = Math.max(max, m);
    saved.put(node, max);
    return max;
}
```

使用一个 HashMap 保存已经求解过的结点，就是一种缓存的思想。

再看本方法的缺陷，这是一种“跳跃式”的解法，从一个结点出发到另一个结点，会有多条路径，而多条路径会导致重复计算，那么如果能使它不再跳跃，只能一步一步往下走的话，这个重复计算也就不复存在了。

如何改进？小偷对于每个结点有两种处理：偷或不偷。

1.  如果小偷偷了当前结点，那么它的子结点不能偷
2.  如果小偷不偷当前结点，那么子结点可以偷，也可以不偷（取其中较大的即可）

这次的条件好像与上面的条件一样，但是还是有区别的，那个涉及到了孙子结点，而这个，只涉及到孩子，如果上面那个是“跳跃式”的，那么这个就是“步进式”的。当一个结点的解，只与它的孩子结点有关时，这个问题就变得简单了。如下：

```java
public static int rob(TreeNode root) {
    int[] rob = max3(root);
    return Math.max(rob[0], rob[1]);
}
private static int[] max(TreeNode node) {
    if (node == null) {
        return new int[]{0, 0};
    }
    int[] rob = new int[2];
    int[] left = max(node.left);
    int[] right = max3(node.right);
    rob[0] = left[1] + right[1] + node.val;
    rob[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
    return rob;
}
```

函数`rob`返回了一个数组，数组的第一个数是偷了当前结点的解，第二个数是不偷当前结点的解，那么对于一个结点 node 来说，它只要知道了它的左右孩子结点的这样一个数组，就可以根据上面那个条件求出当前结点的最优解，因为没有了跳跃访问，所有的结点只会访问到一遍。

在本题中，通过稍微改变一下对问题的思考方式，就完成了算法的优化。