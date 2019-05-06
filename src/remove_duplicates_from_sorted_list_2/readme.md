### Question

Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only *distinct*numbers from the original list.

**Example 1:**

```
Input: 1->2->3->3->4->4->5
Output: 1->2->5
```

**Example 2:**

```
Input: 1->1->1->2->3
Output: 2->3
```

### Solution

将所有存在重复的结点移除，这样一个题目有两个步骤比较关键，一是先要能判断某个结点是否是重复结点，二是判断完了之后如何移除重复结点。

首先判断是否存在重复，可以简单地通过判断当前结点与下一结点的值是否一样即可，如果不一样，说明当前结点不存在重复，则将当前结点加入新链表并判断下一结点。如果存在重复，就需要移除这些重复的结点。

移除也简单，直接逐个判断下一结点的值是否与上一结点相同，相同则移除，不同则意味着已经将所有重复的结点都移除了，然后接着按照判断是否重复的方法判断下一结点。

代码如下：

```java
public static ListNode deleteDuplicates(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode node = head, result = new ListNode(0), cur = result;
    int last;
    while (node != null) {
        if (node.next == null || node.val != node.next.val) {
            cur = (cur.next = node);
            node = node.next;
            cur.next = null;
        } else {
            last = node.val;
            node = node.next;
            while (node != null && node.val == last) node = node.next;
        }
    }
    return result.next;
}
```

或者也可以使用递归实现：

```java
public static ListNode deleteDuplicates(ListNode head) {
    if (head == null || head.next == null) return head;
    if (head.val == head.next.val) {
        int val = head.val;
        head = head.next.next;
        while (head != null && head.val == val) head = head.next;
        return deleteDuplicates2(head);
    } else {
        head.next = deleteDuplicates2(head.next);
        return head;
    }
}
```

