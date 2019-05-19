### Question

You are given a **perfect binary tree** where all leaves are on the same level, and every parent has two children. The binary tree has the following definition:

```
struct Node {
  int val;
  Node *left;
  Node *right;
  Node *next;
}
```

Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to `NULL`.

Initially, all next pointers are set to `NULL`.

 

**Example:**

![img](https://assets.leetcode.com/uploads/2019/02/14/116_sample.png)

```
Input: {"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":null,"right":null,"val":4},"next":null,"right":{"$id":"4","left":null,"next":null,"right":null,"val":5},"val":2},"next":null,"right":{"$id":"5","left":{"$id":"6","left":null,"next":null,"right":null,"val":6},"next":null,"right":{"$id":"7","left":null,"next":null,"right":null,"val":7},"val":3},"val":1}

Output: {"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":{"$id":"4","left":null,"next":{"$id":"5","left":null,"next":{"$id":"6","left":null,"next":null,"right":null,"val":7},"right":null,"val":6},"right":null,"val":5},"right":null,"val":4},"next":{"$id":"7","left":{"$ref":"5"},"next":null,"right":{"$ref":"6"},"val":3},"right":{"$ref":"4"},"val":2},"next":null,"right":{"$ref":"7"},"val":1}

Explanation: Given the above perfect binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B.
```

 

**Note:**

-   You may only use constant extra space.
-   Recursive approach is fine, implicit stack space does not count as extra space for this problem.

### Solution

题目就是要将每一层都是用 next 指针连成一条链表，可以使用层序遍历，遍历每一层的时候使用 next 指针链接，但是这种情况要保存上一层的所有结点，空间复杂度并非$O(1)$。又根据题目所给的二叉树的描述，这是一棵满二叉树，那么对于每一个非叶子结点，它的左右子结点一定是需要通过 next 连接的，此时，如上的`2->3,4->5,6->7`都很自然的需要连接起来，但是还需要将相邻的两个结点的子结点连接起来，即左结点的右子结点和右结点的左子结点，如图所示。但是除了一个结点的左右孩子要连接之外，这两个左右孩子的孩子还需要连接，即例中`5->6`之间的连接，这两个结点分别是左孩子的右孩子和右孩子的左孩子，综上，要链表化每一层的结点，首先需要做两件事：

1.  连接一个结点的左右孩子
2.  连接一个结点左右孩子的孩子

这是对每一个结点而言，要完成整个树的转化，要对所有的结点执行上述操操作。但是这还不够，当`5->6`这两个结点被连接了之后，如果他们不是叶子结点，那就意味着结点 5 和结点 6 的孩子仍然不是相连的，还需要再将它们的孩子连接起来，此时可以将它们看作是同一个结点的左右两个子结点，所以要再连接结点 5 的右孩子和结点 6 的左孩子，

```java
public static Node connect(Node root) {
    if (root == null) return null;
    connect(root.left);
    connect(root.right);
    con(root.left, root.right);
    return root;
}
private static void con(Node left, Node right) {
    if (left == null || right == null) return;
    left.next = right;
    con(left.right, right.left);
}
```

如上，`connect()`用于遍历所有结点，通过调用孩子结点的`connect()`方法将左右两颗子树分别完成转化，然后调用`con()`方法连接左右两棵子树，两棵子树有多少层就会调用多少次`con()`方法，从二子树的根结点（也就是树的根结点的左右子结点）开始连接，然后移动到下一层继续连接，直到连接完每一层。