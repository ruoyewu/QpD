### Question

Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.

**Example:**

```
Input: 1->2->4, 1->3->4
Output: 1->1->2->3->4->4
```

### Solution

合并两个有序链表，算是一个很简单的题目，只需要每次从两个有序链表中取出较小的一个然后组成一个新的链表即可，不过本题除了解题思路以外还有别的值得思考的问题，除了时间复杂度必须的$O(n)$之外，是否可以将空间复杂度降到$O(0)$，如果本题要操作的是数组，那么因为数组的一次分配性（在使用之前要确定所需空间），必须要单独创建一个 m+n 个大小的数组，不过对于链表来说，每一个结点都是可以直接拿出来单独操作的，这就使得尽可能降低空间使用成为现实，要在不创建新的结点对象的情况下完成合并工作，成为解本题的重点。

代码如下：

```java
public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode head = null, node = null, tmp = null;
    while (l1 != null && l2 != null) {
        if (l1.val > l2.val) {
            tmp = l1;
            l1 = l2;
            l2 = tmp;
        }
        if (head == null) {
            head = l1;
            l1 = l1.next;
            node = head;
        }else {
            node.next = l1;
            l1 = l1.next;
            node = node.next;
        }
    }
    if (l2 != null) {
        l1 = l2;
    }
    if (node != null) {
        node.next = l1;
    }else {
        head = l1;
    }
    return head;
}
```

