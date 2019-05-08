### Question

Reverse a linked list from position *m* to *n*. Do it in one-pass.

**Note:** 1 ≤ *m* ≤ *n* ≤ length of list.

**Example:**

```
Input: 1->2->3->4->5->NULL, m = 2, n = 4
Output: 1->4->3->2->5->NULL
```

### Solution

反转一个链表的一部分，算是反转链表的扩展版吧，本题的解题步骤大致有两个，一是如何将需要反转的链表拿出来，并再将反转之后的链表放回去，二是如何反转这一部分需要反转的链表。

关于反转链表，在 reverse linked list 一题中列出了三种解法，这里不再赘述，主要讨论一下如何反转这一部分的链表。题目给出了需要反转的部分链表的起止位置，所以应该有一个指针，记录链表的大小。假设要反转的链表是原链表的中间一部分，则原链表需要被分割成三个子链，左、中、右，中间一段是需要反转的，在反转了中间的子链之后需要再将左、中（反转后）、右连接成一个链表，而连接链表至少需要知道每一个链表的链头或者链尾，即按照 左尾、中头、中尾、右头 这样一种顺序连接，要找到四个结点只需要遍历一次链表，根据需要反转的位置确定即可。

接下来将中间的链表反转之后，利用已经保存下来的各标志性结点连接成一个链表就是最终结果。按照上面的描述，其时间复杂度应该是$O(n + \text{反转所需时间})$，考虑到反转链表的实现，这是可以直接在遍历的过程中完成的，参见下：

```java
public ListNode reverseList(ListNode head) {
    ListNode last = null, node = head, next;
    while (node != null) {
        next = node.next;
        node.next = last;
        last = node;
        node = next;
    }
    return last;
}
```

而上面也说到，需要先遍历一次链表，找出那四个标志性结点，是否可以将反转链表的操作集成在这个遍历过程中？分析两个操作的异同点，首先二者都是在遍历的基础上实现的，反转链表的操作通过改变指针完成，那么这是否会与上一个操作冲突？并不会，上一个操作的目的就是寻找到标志结点并记录下来，也就是说这个操作不会更改原链表，所以这两个操作不会产生冲突，代码如下：

```java
public static ListNode reverseBetween(ListNode head, int m, int n) {
    int pos = 0;
    ListNode node = head, midHead = null, midTail = null, leftTail = null, last = null, next;
    while (node != null) {
        next = node.next;
        pos++;
        if (pos >= m && pos <= n) {
            if (midHead == null) {
                midHead = midTail = node;
                node.next = null;
                if (last == null) head = node;
                else last.next = node;
                leftTail = last;
            } else {
                node.next = midTail;
                midTail = node;
            }
        } else if (midTail != null || next == null) {
            break;
        }
        last = node;
        node = next;
    }
    if (midHead == null) return head;
    midHead.next = node;
    if (leftTail != null) leftTail.next = midTail;
    else head = midTail;
    return head;
}
```

通过 pos 记录当前结点的位置，当在需要反转的范围内的时候就将当前结点指针反转，指向上一个结点，midHead、midTail、leftTail 分别是 中头、中尾、左尾这三个结点，而关于 右头 这个结点，当需要反转的部分遍历完了之后，下一个结点必然就是 右头 这个结点，也就是 node 。