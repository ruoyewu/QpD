### Question

Reverse a singly linked list.

**Example:**

```
Input: 1->2->3->4->5->NULL
Output: 5->4->3->2->1->NULL
```

**Follow up:**

A linked list can be reversed either iteratively or recursively. Could you implement both?

### Solution

#### S1:存取法

要对一个链表逆序，可以使用栈，通过进栈->出栈，就能够完成链表的逆序：

```java
public ListNode reverseList(ListNode head) {
    if (head == null || head.next == null) return head;
    Stack<ListNode> stack = new Stack<>();
    ListNode node = head;
    while (node != null) {
        stack.push(node);
        node = node.next;
    }
    head = stack.pop();
    node = head;
    while (!stack.empty()) {
        node.next = stack.pop();
    }
    node.next = null;
    return head;
}
```

#### S2:转向法

链表是由结点中的指针一个一个串联起来的，如下：

```
1->2->3->4->5->6
```

那么只需要将这个指针的方向翻转过来，就变成了：

```
1<-2<-3<-4<-5<-6
```

这就完成了链表的逆序，具体的操作步骤就是将所有的结点的指针指向它的前驱结点：

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

#### S3:递归

要翻转一个链表，可以先将除去头结点的链表翻转，然后将头结点加入这个链表的尾部，这是一种递归的思想，代码入下：

```java
public ListNode reverseList(ListNode head) {
    if (head == null || head.next == null)
        return head;
    ListNode node = reverseList(head.next);
    head.next.next = head;
    head.next = null;
    return node;
}
```

将`head.next`开始的链表翻转之后，`head.next`就自然成为了这个翻转之后的链表的尾结点，那么`head.next.next = head`就可以将 head 结点加入尾部，然后再断开`head`与`head.next`的联系即可。