### Question

You are given a binary tree in which each node contains an integer value.

Find the number of paths that sum to a given value.

The path does not need to start or end at the root or a leaf, but it must go downwards (traveling only from parent nodes to child nodes).

The tree has no more than 1,000 nodes and the values are in the range -1,000,000 to 1,000,000.

**Example:**

```
root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8

      10
     /  \
    5   -3
   / \    \
  3   2   11
 / \   \
3  -2   1

Return 3. The paths that sum to 8 are:

1.  5 -> 3
2.  5 -> 2 -> 1
3. -3 -> 11
```

### Solution

求出该二叉树的路径数满足某一条件的路径数，条件为路径上的结点值相加和为给定的一个数字。那么只要我们找出所有的路径，再判断每个路径是否满足这个条件，就能求出本题的解。而一条路径有一个起点和一个终点，我们可以通过遍历路径的起点或者终点判断，这分别对应着两种方法。

#### S1:终点遍历

想到二叉树的路径，就可以联想到二叉树的后续遍历的过程中，能够求出该二叉树的所有路径，那么可以在二叉树的后续遍历算法上进行一定的修改，来解决这道题。如下：

```java
public static int pathSum(TreeNode root, int sum) {
    Stack<TreeNode> stack = new Stack<>();
    Stack<TreeNode> tmp = new Stack<>();
    int count = 0;
    TreeNode node = root, pre = null, n;
    while (node != null) {
        stack.push(node);
        node = node.left;
    }
    while (!stack.empty()) {
        node = stack.pop();
        if (node.right == null || pre == node.right) {
            int s = node.val;
            if (s == sum) count++;
            while (!stack.empty()) {
                n = stack.pop();
                s += n.val;
                tmp.push(n);
                if (s == sum) {
                    count++;
                }
            }
            while (!tmp.empty()) {
                stack.push(tmp.pop());
            }
            pre = node;
        } else {
            stack.push(node);
            node = node.right;
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
    }
    return count;
}
```

这是一个二叉树的非递归后续遍历的实现，我只是在访问结点的那段代码中做了一些修改，代码行为 13～26 。每当访问一个结点时，栈中存放的必然是从根结点到该结点的一条路径，那么沿着这个结点向上寻找，直到根结点，便可以计算出以当前结点为终点的所有符合条件的路径数。

或者也可以使用递归的后续遍历：

```java
public static int pathSum(TreeNode root, int sum) {
    int depth = depth(root);
    int[] path = new int[depth];
    return sum2(root, sum, 0, path);
}
private static int sum2(TreeNode node, int sum, int depth, int[] path) {
    if (node == null) return 0;
    int count = 0;
    path[depth] = node.val;
    int s = 0;
    for (int i = depth; i >= 0; i--) {
        s += path[i];
        if (s == sum) count++;
    }
    count += sum2(node.left, sum, depth+1, path);
    count += sum2(node.right, sum, depth+1, path);
    return count;
}
private static int depth(TreeNode node) {
    if (node == null) return 0;
    return Math.max(depth(node.left), depth(node.right)) + 1;
}
```

在递归的算法里面，可以使用一个数组代替栈存放从根结点到当前结点的一条路径，采取与上述算法类似的操作即可。

#### S2:起点遍历

在上面的使用后续遍历的算法中，都是利用后续遍历存储路径的原理，从一条路径的终点开始向上判断，也就是说优先遍历的是路径的终点，然后在确定终点的情况下，向上判断以寻找符合条件路径的起点。

或者也可以优先遍历路径的起点，即外层是一个对二叉树的遍历（随便什么顺序的遍历），假设以任何一个结点作为路径的起点，然后向下遍历到叶子结点，找出所有符合条件的路径。

```java
public static int pathSum(TreeNode root, int sum) {
    if (root == null) return 0;
    return pathSum3(root.left, sum) + pathSum3(root.right, sum) + sum3(root, sum);
}
private static int sum3(TreeNode node, int left) {
    if (node == null) return 0;
    int count = node.val == left ? 1 : 0;
    count += sum3(node.left, left-node.val);
    count += sum3(node.right, left-node.val);
    return count;
}
```

如上，`pathSum()`函数是一个二叉树的遍历来确定起点，并将起点作为`sum3()`函数的参数，然后再利用`sum3()`这个递归函数寻找符合条件路径的终点。