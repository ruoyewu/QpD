### Question

Given a linked list, reverse the nodes of a linked list *k* at a time and return its modified list.

*k* is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of *k* then left-out nodes in the end should remain as it is.


**Example:**

Given this linked list: `1->2->3->4->5`

For *k* = 2, you should return: `2->1->4->3->5`

For *k* = 3, you should return: `3->2->1->4->5`

**Note:**

-   Only constant extra memory is allowed.
-   You may not alter the values in the list's nodes, only nodes itself may be changed.

### Solution

相对于 swap nodes in pairs ，这是一种要求更高的操作，如果这里的 k 等于 2 ，这个问题就变成了 swap nodes in pairs 了。求解这道题的基本思路为：每次截取出前 k 个结点，将其翻转，然后再与之后链表连接起来。于是有代码：

```java
public static ListNode reverseKGroup(ListNode head, int k) {
    if (head == null || k == 1) return head;
    int pos = 1;
    ListNode node = head, left = null;
    while (node.next != null && pos < k) {
        node = node.next;
        pos++;
    }
    if (pos < k) return head;
    if (node.next != null) {
        left = reverseKGroup(node.next, k);
        node.next = null;
    }
    node = reverser(head);
    head.next = left;
    return node;
}
private static ListNode reverser(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode pre = head, node = head.next, tmp;
    pre.next = null;
    while (node != null) {
        tmp = node.next;
        node.next = pre;
        pre = node;
        node = tmp;
    }
    return pre;
}
```

在`reverseKGroup()`中，第一个 while 循环是为了截取前 k 个结点，然后调用`reverseKGroup(node.next, k)`求截取之后的链表的解，然后调用`reverser()`翻转这 k 个结点，最后执行`head.next = left`将前后两部分连接起来。

在上面这种解法中，需要两次遍历，第一次遍历，求出 k 个结点，第二次遍历进行翻转，是否可以一次遍历就能够完成翻转？比如说在翻转的时候顺便计数，一次只翻转 k 个，然后递归调用翻转之后的链表？那么还有一个问题，按题目要求，最后一段如果不满 k 个，不能翻转，但是在这种方式下，当发现剩余的结点不足 k 个的时候，翻转已经完成了，那么此时该如何？那就只能再将这不足 k 个的结点再翻转一遍，成为原本的样子了。

所以大致思路就出来了：边翻转边计数，当计数达到 k 之后，递归调用，翻转之后的链表，如果计数不足 k 个但链表已经结束，那就再将这几个翻转回去。代码如下：

```java
public static ListNode reverseKGroup(ListNode head, int k) {
    if (head == null || head.next == null || k == 1) return head;
    boolean first = true;
    ListNode pre = head, node, tmp;
    while (true) {
        node = head.next;
        pre.next = null;
        int pos = 1;
        while (node != null && pos < k) {
            tmp = node.next;
            node.next = pre;
            pre = node;
            node = tmp;
            pos++;
        }
        if (first && pos < k) {
            head = pre;
            first = false;
        } else {
            break;
        }
    }
    head.next = reverseKGroup(node, k);
    return pre;
}
```

