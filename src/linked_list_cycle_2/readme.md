### Question

Given a linked list, return the node where the cycle begins. If there is no cycle, return `null`.

To represent a cycle in the given linked list, we use an integer `pos` which represents the position (0-indexed) in the linked list where tail connects to. If `pos` is `-1`, then there is no cycle in the linked list.

**Note:** Do not modify the linked list.

 

**Example 1:**

```
Input: head = [3,2,0,-4], pos = 1
Output: tail connects to node index 1
Explanation: There is a cycle in the linked list, where tail connects to the second node.
```

![img](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist.png)

**Example 2:**

```
Input: head = [1,2], pos = 0
Output: tail connects to node index 0
Explanation: There is a cycle in the linked list, where tail connects to the first node.
```

![img](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist_test2.png)

**Example 3:**

```
Input: head = [1], pos = -1
Output: no cycle
Explanation: There is no cycle in the linked list.
```

![img](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist_test3.png)

 

**Follow up**:
Can you solve it without using extra space?

### Solution

之前一个题是判断是否有环路，那里使用的方法在本题中同样可以使用。

#### S1:保存已访问结点

将所有已访问的结点保存起来，然后在访问的过程中一旦遇到已访问过的结点，那这个结点一定就是循环段的起始结点。

```java
public static ListNode detectCycle(ListNode head) {
    HashSet<ListNode> set = new HashSet<>();
    while (head != null) {
        if (set.contains(head)) {
            return head;
        } else {
            set.add(head);
            head = head.next;
        }
    }
    return null;
}
```

#### S2:双指针追赶法

如果让两个指针从链表头开始后移，一个指针每次后移一位（跑得慢），一个每次后移两位（跑得快），那么当这两个指针相遇的时候，一则说明这是一个循环链表，二则这跑得快的指针走的路程应该是跑得慢的指针的路径的二倍。

![](./linked_list_cycle_2_1.jpg)

如上，红色和绿色分别是两个指针走的路程，并且绿色长度是红色的两倍。那么化简一下，有如下，红色与绿色相等：

![](./linked_list_cycle_2_2.jpg)

再化简一下：

![](linked_list_cycle_2_3.jpg)

此时，红色与绿色的长度相等，并且此时两指针相遇在 6 的位置，那么我们看一下，此时的红色长度是不是就是从头结点到开始循环结点的长度，而绿色的长度也正是从相遇结点到开始循环结点的长度，那么如果分别从头结点和相遇结点后移，那么它们最终会相遇在开始循环结点，如此一来，本题的解就求出来了。

当然，也有可能这两个速度不一样的指针不会在第一圈就相遇：如下：

![](./linked_list_cycle_2_4.jpg)

虽然最终化简之后还会是上一张图那样的结果，但是现在还有一个问题，这两个结点是否一定会在第一圈相遇？在什么情况下它们可能在第一圈不会相遇，即在慢指针第一次进入循环段之后，与快指针的擦肩而过，如果以上图为例的话，就是当慢指针走到 6 的位置的时候，快指针走到了 7 ，只有它们第一次可能相遇的机会（即慢指针刚进入循环段，快指针追赶慢指针这个过程）没有相遇，才有可能出现第一圈它们不相遇的情况。那么再看，如果在某一时刻慢指针走到了 6 ，而此时快指针已经走到了 7 ，假如循环段的开始结点是 4 ，那么必然有上一时刻它们的位置都是 5 ，所以这显然是不可能的，而唯一出现这种可能的就是，当慢指针走到了 4 （循环段开始结点）的时候，快指针走到了 5 ，那么前一时刻它们的位置应该分别是 3 和 9 ，当然上图不是这种情况，我仅是以上图作为参考，说明一种情况：

当快指针和慢指针第一次相遇的机会没有相遇的时候，它们的情况应该是这样的，假如链表总长 n ，循环段长 h ，当前时刻 t ，此时有$1 + 2 * t = n, 1 + t = n - h$，也就是说下一时刻，它们分别位于循环段 h 的前两个位置，而此后，它们也只会在循环段里面了，所以，在长度为 h 的循环段中，慢指针的位置为 1 ，快指针的位置为 2 ，那么在 h-1 个时刻之后，它们必然会相遇在 h 位置。而此时，还是慢指针的第一圈。

所以，按照上述推算，两个指针一定会在第一圈相遇。

代码如下：

```java
public static ListNode detectCycle(ListNode head) {
    if (head == null || head.next == null)
        return null;
    ListNode slow = head.next, fast = head.next.next;
    while (slow != fast) {
        if (fast == null || fast.next == null)
            return null;
        slow = slow.next;
        fast = fast.next.next;
    }
    while (head != slow) {
        head = head.next;
        slow = slow.next;
    }
    return slow;
}
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
        next = null;
    }
}
```

