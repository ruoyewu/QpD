### Question

Given a Binary Search Tree (BST), convert it to a Greater Tree such that every key of the original BST is changed to the original key plus sum of all keys greater than the original key in BST.

**Example:**

```
Input: The root of a Binary Search Tree like this:
              5
            /   \
           2     13

Output: The root of a Greater Tree like this:
             18
            /   \
          20     13
```

### Solution

#### S1:中序遍历

对于一个二叉搜索树来说，它的中序遍历是一个递增序列，本题要求每一个结点的值都要加上大于这个结点的所有值，而中序遍历的序列中，左侧是所有小于当前结点的值，那么如果先求出所有结点的和，然后再减去左侧的和和自身的值，就是大于当前结点的值：

```java
private static int sum;
private static int cur;
public static TreeNode convertBST(TreeNode root) {
    sum = getSum(root);
    cur = 0;
    convert(root);
    return root;
}
private static int getSum(TreeNode node) {
    if (node == null) return 0;
    return node.val + getSum(node.left) + getSum(node.right);
}
private static void convert(TreeNode node) {
    if (node != null) {
        convert(node.left);
        cur += node.val;
        node.val = sum - cur + node.val;
        convert(node.right);
    }
}
```

这是一个利用中序遍历实现的方法，对二叉树进行了两次遍历，第一次求出总和，第二次求出每个结点的最终值。

#### S2:改进

或者我们也可以将已有的中序遍历进行改进，将访问顺序变成 右-中-左 ，那么此时中序遍历就是一个递减的序列，就不需要额外的一个遍历求出总的值了：

```java
private static int cur;
public static TreeNode convertBST2(TreeNode root) {
    cur = 0;
    convert2(root);
    return root;
}
private static void convert2(TreeNode node) {
    if (node != null) {
        convert2(node.right);
        node.val += cur;
        cur = node.val;
        convert2(node.left);
    }
}
```

或者也可以改写成非递归形式的：

```java
public static TreeNode convertBST3(TreeNode root) {
    int cur = 0;
    Stack<TreeNode> stack = new Stack<>();
    TreeNode node = root;
    while (node != null) {
        stack.push(node);
        node = node.right;
    }
    while (!stack.empty()) {
        node = stack.pop();
        node.val += cur;
        cur = node.val;
        node = node.left;
        while (node != null) {
            stack.push(node);
            node = node.right;
        }
    }
    return root;
}
```

