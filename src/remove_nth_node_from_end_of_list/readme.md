### Question

Given a linked list, remove the *n*-th node from the end of list and return its head.

**Example:**

```
Given linked list: 1->2->3->4->5, and n = 2.

After removing the second node from the end, the linked list becomes 1->2->3->5.
```

**Note:**

Given *n* will always be valid.

### Solution

删除一个链表的倒数第n个结点，可采用双指针法。步骤为：

1.  使一个指针指向表头
2.  将这个指针向后移动 n 个单位，然后使另一个指针指向表头
3.  继续移动第一个指针知道表尾，此刻第二个指针所指即为倒数第 n 个结点

但是要删除这倒数第 n 个结点还需要知道倒数 n+1 个结点，还需要一个指针保存这个结点，代码为：

```java
public class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
    }
}

public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode last = null, cur, first = head;
    for (int i = 0; i < n-1; i++) {
        first = first.next;
    }
    cur = head;
    while (first.next != null) {
        last = cur;
        cur = cur.next;
        first = first.next;
    }
    if (last == null) {
        head = cur.next;
    }else {
        last.next = cur.next;
    }
    return head;
}
```

