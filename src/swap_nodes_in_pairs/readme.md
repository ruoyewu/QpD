### Question

Given a linked list, swap every two adjacent nodes and return its head.

You may **not** modify the values in the list's nodes, only nodes itself may be changed.

 

**Example:**

```
Given 1->2->3->4, you should return the list as 2->1->4->3.
```

### Solution

与 reverse linked list 类似，同样有三种解法：修改指针、递归和新建链表。

#### S1:修改指针

链表由两个部分组成：结点和指针。结点决定某一位置的值，指针决定链表的顺序。所以对于两两交换位置，意味每隔一个指针，便将其翻转。如将`1->2->3->4`变为`(1<-2)->(3<-4)`，再变为`2->1->4->3`，所以，完成这样一个变换，有两个问题：

1.  需要翻转哪些指针，对应着上述第一次变换
2.  如何在翻转指针之后，再将其组成一个链表，对应着上述第二次变换

第一个问题，通过思考可以得到，从第一个指针开始，每隔一个指针完成翻转。第二个问题，如何连接？首先，组内连接（即互相交换的两个结点之间的连接），永远都是后一个指向前一个，对应着上面的`1<-2`和`3<-4`，然后是组间连接，永远是前一组的第一个结点与后一组的第二个结点连接，对应着上面的`1->4`，联合这三个便可以轻松得到最终结果`2->1->4->3`。代码为：

```java
public static ListNode swapPairs(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode pre = head, node = head.next, n, nn;
    head = node;
    pre.next = null;
    while (node != null) {
        n = node.next;
        node.next = pre;
        if (n == null || n.next == null) {
            pre.next = n;
            break;
        }
        nn = n.next;
        pre.next = nn;
        pre = n;
        node = nn;
    }
    return head;
}
```

每一次变换涉及到四个结点，其初始连接为`pre->node->n->nn`，变换之后的连接为`node->pre->nn->n`，并将 pre 和 node 分别后移为 n 和 nn 。

#### S2:递归

递归则比较简单说明，对于一个链表，首先截取其前两个结点将其翻转，然后再计算去掉这两个结点之后的链表的变换结果即可，如`(1->2->3->4)的变换 = 2->1->(3->4)的变换`。代码为：

```java
public static ListNode swapPairs(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode next = head.next, left = swapPairs(head.next.next);
    next.next = head;
    head.next = left;
    return next;
}
```

#### S3:新建链表

新建一个链表，然后在遍历原链表的过程中，每次读取出来两个结点，再将这两个结点逆序加入到新的链表中，如对于链表`1->2->3->4`，首先读取出来两个结点`1, 2`，原链表变为`3->4`，新链表变为`2->1`，然后继续读取`3, 4`，逆序加入新链表成为`2->1->4->3`。代码为：

```java
public static ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode h, node, n = head.next, nn = head.next.next;
        h = n;
        h.next = head;
        node = head;
        head = nn;
        while (head != null) {
            n = head.next;
            if (n == null) {
                node.next = head;
                node = head;
                break;
            }
            nn = n.next;
            node.next = n;
            n.next = head;
            node = head;
            head = nn;
        }
        node.next = null;
        return h;
    }
}

```

