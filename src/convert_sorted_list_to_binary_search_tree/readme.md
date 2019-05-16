### Question

Given a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.

For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of *every* node never differ by more than 1.

**Example:**

```
Given the sorted linked list: [-10,-3,0,5,9],

One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:

      0
     / \
   -3   9
   /   /
 -10  5
```

### Solution

要将一个有序数组转化成二叉树，且是左右两边高度一致的二叉搜索树，那么必然有二叉树的根结点就是链表的中间结点，然后以中间结点为间隔将链表分成两个子链，分别组成根结点的左右子树。这样说来题目挺简单的，但是有一个问题，链表只能顺序存取，但是如果每次要求一个二叉树的根结点就要得到链表的中间结点值，这是一个随机存取，于是产生了矛盾。

按照辩证法，矛盾都是可以转化的，我们需要的只是一种方法。在本题中，链表不可随机存储，但是数组可以，所以一种想法是先讲链表转化成数组，再求解，如下代码：

```java
public static TreeNode sortedListToBST(ListNode head) {
    ListNode node = head;
    int len = 0;
    while (node != null) {
        len++;
        node = node.next;
    }
    int[] nums = new int[len];
    int pos = 0;
    node = head;
    while (node != null) {
        nums[pos++] = node.val;
        node = node.next;
    }
    return toBST(nums, 0, len-1);
}
private static TreeNode toBST(int[] nums, int start, int end) {
    if (start > end) return null;
    int mid = (start + end) / 2;
    TreeNode node = new TreeNode(nums[mid]);
    node.left = toBST(nums, start, mid-1);
    node.right = toBST(nums, mid+1, end);
    return node;
}
```

此时，时间复杂度为$O(n)$，空间复杂度也是$O(n)$，需要执行两次链表的遍历，和一个递归遍历，总的时间应该是$O(3n)$。

这看起来并不是一种好方法，是否有一种方法，链表的顺序存取就可以满足？本题要求的是一种很工整的二叉树，工整到只需要知道链表的长度，就可以确定二叉树的形状，不确定的只是每个结点的值是多少。又，待求的二叉排序树的中序遍历就是链表的排列顺序，所以只需要逆向中序遍历，便可以只需要顺序存取就可以生成二叉树。代码如下：

```java
public static TreeNode sortedListToBST(ListNode head) {
    ListNode node = head;
    int len = 0;
    while (node != null) {
        len++;
        node = node.next;
    }
    h = head;
    return toBST(0, len-1);
}
private static ListNode h;
private static TreeNode toBST(int start, int end) {
    if (start > end) return null;
    int mid = (start + end) / 2;
    TreeNode left = toBST(start, mid-1);
    TreeNode root = new TreeNode(h.val);
    h = h.next;
    TreeNode right = toBST(mid+1, end);
    root.left = left;
    root.right = right;
    return root;
}
```

利用链表的长度确定二叉树的结构，再利用中序遍历确定每个结点的值，就是上面代码的总结。