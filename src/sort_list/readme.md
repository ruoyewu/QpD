### Question

Sort a linked list in *O*(*n* log *n*) time using constant space complexity.

**Example 1:**

```
Input: 4->2->1->3
Output: 1->2->3->4
```

**Example 2:**

```
Input: -1->5->3->4->0
Output: -1->0->3->4->5
```

### Solution

如何对一个链表进行排序？一般来说，对数组进行排序的时候，总是会涉及到许多的比较、交换等操作，因为数组是直接存取结构。但是对于一个链表而言，由于顺序访问，所以很多的对数组进行排序的方法都不能用。

#### S1:先换成数组

一种方法是，先将链表中的数转存到数组里面，在数组中排序好了之后，再放回链表中：

```java
public ListNode sortList(ListNode head) {
    ListNode node = head;
    int cnt = 0;
    while (node != null) {
        cnt++;
        node = node.next;
    }
    int[] nums = new int[cnt];
    node = head;
    cnt = 0;
    while (node != null) {
        nums[cnt++] = node.val;
        node = node.next;
    }
    Arrays.sort(nums);
    node = head;
    cnt = 0;
    while (node != null) {
        node.val = nums[cnt++];
        node = node.next;
    }
    return head;
}
```

总共有四个步骤：求链表长度、将链表转存到数组、数组排序、从数组放回链表。

#### S2:二路归并

在数组的排序中也有通过顺序访问进行排序的，即对给定的两个有序数组，合并成一个有序数组的时候，可以顺序访问并依次将其中较小的取出放入新的数组以完成排序。这种思想也可以用到对链表的排序中来，如将一个链表分成两份，先对各个小的链表进行排序，完了之后将二者归并到一起。所以，使用递归函数，将待排序的链表不断划分，直到划分为单结点链表，然后将它们按照两两归并的方式合并起来即可。

```java
public static ListNode sortList(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode pre = head, slow = head.next, fast = head.next.next;
    while (fast != null && fast.next != null) {
        pre = slow;
        slow = slow.next;
        fast = fast.next.next;
    }
    pre.next = null;
    ListNode l1 = sortList2(head);
    ListNode l2 = sortList2(slow);
    return merge(l1, l2);
}
private static ListNode merge(ListNode l1, ListNode l2) {
    if (l1 == null) return l2;
    if (l2 == null) return l1;
    ListNode head;
    if (l1.val < l2.val) {
        head = l1;
        l1 = l1.next;
    } else {
        head = l2;
        l2 = l2.next;
    }
    ListNode node = head;
    while (l1 != null && l2 != null) {
        if (l1.val < l2.val) {
            node.next = l1;
            l1 = l1.next;
        } else {
            node.next = l2;
            l2 = l2.next;
        }
        node = node.next;
    }
    if (l1 == null) {
        node.next = l2;
    } else {
        node.next = l1;
    }
    return head;
}
```

