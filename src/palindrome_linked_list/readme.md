### Question

Given a singly linked list, determine if it is a palindrome.

**Example 1:**

```
Input: 1->2
Output: false
```

**Example 2:**

```
Input: 1->2->2->1
Output: true
```

**Follow up:**
Could you do it in O(n) time and O(1) space?

### Solution

要判断一个链表是否是一个回文序列，就要分别从链表的两端出发，依次判断相应位置是否相等，或者是从链表的中间出发走向两边。我们知道可以使用双指针法快速找到一个链表的中间结点，问题在于找到这个中间结点之后如何分别向前向后移动。

一种方法是，将链表的前半部分使用栈保存起来，那么出栈的时候自然就是一个逆序：

```java
public static boolean isPalindrome(ListNode head) {
    if (head == null || head.next == null) return true;
    ListNode slow = head, fast = head.next;
    LinkedList<ListNode> list = new LinkedList<>();
    list.add(head);
    while (fast.next != null && fast.next.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        list.add(slow);
    }
    slow = slow.next;
    if (fast.next != null) {
        slow = slow.next;
    }
    while (!list.isEmpty() && slow != null) {
        if (slow.val == list.getLast().val) {
            list.removeLast();
            slow = slow.next;
        } else {
            return false;
        }
    }
    return list.isEmpty();
}
```

栈中保存着前半部分链表，slow 指向链表的中间。

如何能够不使用额外的空间？在这里栈的作用主要就是将链表的前半部分做个逆序，其实也可以不使用栈直接对其逆序（直接修改每个结点的 next 指针使其指向其前驱结点），那么当找链表中点的过程中，就可以对中点之前的一部分链表直接逆序，找到中点之后，终点之前的链表已经完成了逆序，中点之后的链表还是顺序。

```java
public static boolean isPalindrome2(ListNode head) {
    if (head == null || head.next == null) return true;
    ListNode slow = head, fast = head.next, pre = null;
    while (true) {
        ListNode tmp = slow.next;
        slow.next = pre;
        pre = slow;
        slow = tmp;
        if (fast.next == null) {
            // 偶数个
            break;
        }
        if (fast.next.next == null) {
            // 奇数个
            slow = slow.next;
            break;
        }
        fast = fast.next.next;
    }
    while (pre != null && slow != null) {
        if (pre.val == slow.val) {
            pre = pre.next;
            slow = slow.next;
        } else {
            return false;
        }
    }
    return pre == slow;
}
```

