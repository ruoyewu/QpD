### Question

Given a linked list and a value *x*, partition it such that all nodes less than *x* come before nodes greater than or equal to *x*.

You should preserve the original relative order of the nodes in each of the two partitions.

**Example:**

```
Input: head = 1->4->3->2->5->2, x = 3
Output: 1->2->2->4->3->5
```

### Solution

解题方式为，遍历原链表，将其按照值的大小区分成两个子链表，然后再将子链表连接在一起，即可。代码如下：

```java
public static ListNode partition(ListNode head, int x) {
    ListNode leftHead = null, rightHead = null, leftNode= null, rightNode = null;
    while (head != null) {
        if (head.val < x) {
            if (leftNode == null) leftHead = leftNode = head;
            else leftNode = (leftNode.next = head);
        } else {
            if (rightNode == null) rightHead = rightNode = head;
            else rightNode = (rightNode.next = head);
        }
        head = head.next;
    }
    if (rightNode != null) {
        rightNode.next = null;
    }
    if (leftNode != null) {
        leftNode.next = rightHead;
    }
    return leftHead == null ? rightHead : leftHead;
}
```

