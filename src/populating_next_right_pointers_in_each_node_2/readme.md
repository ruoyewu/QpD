### Question

Given a binary tree

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

![img](https://assets.leetcode.com/uploads/2019/02/15/117_sample.png)

```
Input: {"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":null,"right":null,"val":4},"next":null,"right":{"$id":"4","left":null,"next":null,"right":null,"val":5},"val":2},"next":null,"right":{"$id":"5","left":null,"next":null,"right":{"$id":"6","left":null,"next":null,"right":null,"val":7},"val":3},"val":1}

Output: {"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":{"$id":"4","left":null,"next":{"$id":"5","left":null,"next":null,"right":null,"val":7},"right":null,"val":5},"right":null,"val":4},"next":{"$id":"6","left":null,"next":null,"right":{"$ref":"5"},"val":3},"right":{"$ref":"4"},"val":2},"next":null,"right":{"$ref":"6"},"val":1}

Explanation: Given the above binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B.
```

 

**Note:**

-   You may only use constant extra space.
-   Recursive approach is fine, implicit stack space does not count as extra space for this problem.

### Solution

与上一题 populating next right pointers in each node 不同的是，此时的二叉树不再是满二叉树了，意味着某一个结点可能只有左孩子，而这个左孩子的 next 可能会指向它的某个同级结点的左孩子，这种情况下就只能使用层序遍历来实现了，一般来说层序遍历的过程中需要保存某一层的所有结点，才能得到下一层的所有结点，但是对于本题，同级的所有结点会被 next 指针连成一条链表，而这正是题目要我们做的事情。

所以，假使已经完成了第 i 层的转换，那么这一层就构成了一个以最左结点为头结点的链表，它们的子结点就可以轻而易举的再次构成一个链表。

```java
public static Node connect(Node root) {
    if (root == null) return null;
    Node start = new Node(Integer.MIN_VALUE, null, null, null);
    Node cur = start, node = root;
    while (node != null) {
        if (node.left != null) {
            cur.next = node.left;
            cur = cur.next;
        }
        if (node.right != null) {
            cur.next = node.right;
            cur = cur.next;
        }
        node = node.next;
    }
    connect(start.next);
    return root;
}
```

这是一种递归解法，每次调用`connect()`方法会完成某一层的转换，并调用`connect()`执行下一层转换。