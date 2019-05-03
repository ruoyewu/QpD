### Question

Given a linked list, rotate the list to the right by *k* places, where *k* is non-negative.

**Example 1:**

```
Input: 1->2->3->4->5->NULL, k = 2
Output: 4->5->1->2->3->NULL
Explanation:
rotate 1 steps to the right: 5->1->2->3->4->NULL
rotate 2 steps to the right: 4->5->1->2->3->NULL
```

**Example 2:**

```
Input: 0->1->2->NULL, k = 4
Output: 2->0->1->NULL
Explanation:
rotate 1 steps to the right: 2->0->1->NULL
rotate 2 steps to the right: 1->2->0->NULL
rotate 3 steps to the right: 0->1->2->NULL
rotate 4 steps to the right: 2->0->1->NULL
```

###  Solution

1.  求出链表总长，并顺便记录下表尾结点
2.  根据 k 值和链表总长，求出原表头到现表尾的长度
3.  找到现表头，并修改现表尾和原表尾的 next 指针，组成新的链表。

如下。

```java
public static ListNode rotateRight(ListNode head, int k) {
    if (head == null || head.next == null || k == 0) return head;
    int len = 1;
    ListNode node = head, tail;
    while (node.next != null) {
        node = node.next;
        len++;
    }
    tail = node;
    k = len - k % len;
    if (k == len) return head;
    ListNode newTail = head, newHead;
    int pos = 1;
    while (pos++ < k) {
        newTail = newTail.next;
    }
    newHead = newTail.next;
    newTail.next = null;
    tail.next = head;
    return newHead;
}
```

结束。