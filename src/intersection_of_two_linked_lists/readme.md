### Question

Write a program to find the node at which the intersection of two singly linked lists begins.

For example, the following two linked lists:

![img](https://assets.leetcode.com/uploads/2018/12/13/160_statement.png)

begin to intersect at node c1.

 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2018/12/13/160_example_1.png)

```
Input: intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2, skipB = 3
Output: Reference of the node with value = 8
Input Explanation: The intersected node's value is 8 (note that this must not be 0 if the two lists intersect). From the head of A, it reads as [4,1,8,4,5]. From the head of B, it reads as [5,0,1,8,4,5]. There are 2 nodes before the intersected node in A; There are 3 nodes before the intersected node in B.
```

 

**Example 2:**

![img](https://assets.leetcode.com/uploads/2018/12/13/160_example_2.png)

```
Input: intersectVal = 2, listA = [0,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
Output: Reference of the node with value = 2
Input Explanation: The intersected node's value is 2 (note that this must not be 0 if the two lists intersect). From the head of A, it reads as [0,9,1,2,4]. From the head of B, it reads as [3,2,4]. There are 3 nodes before the intersected node in A; There are 1 node before the intersected node in B.
```

 

**Example 3:**

![img](https://assets.leetcode.com/uploads/2018/12/13/160_example_3.png)

```
Input: intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
Output: null
Input Explanation: From the head of A, it reads as [2,6,4]. From the head of B, it reads as [1,5]. Since the two lists do not intersect, intersectVal must be 0, while skipA and skipB can be arbitrary values.
Explanation: The two lists do not intersect, so return null.
```

 

**Notes:**

-   If the two linked lists have no intersection at all, return `null`.
-   The linked lists must retain their original structure after the function returns.
-   You may assume there are no cycles anywhere in the entire linked structure.
-   Your code should preferably run in O(n) time and use only O(1) memory.

### Solution

#### S1:倒推法

如题，如果两个链表有交点，那么从交点一直到两个链表最后，这一段结点两个链表是重合的，那么只要从两个链表的结尾处依次往前推，最后一个相同的结点自然是两链表的交点。但是这是一个单链表，不能直接倒推，所以要使用数组将其保存起来。

```java
public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    ListNode node, firstInterNode = null;
    ListNode[] A, B;
    int cnt = 0;
    node = headA;
    while (node != null) {
        cnt++;
        node = node.next;
    }
    A = new ListNode[cnt];
    cnt = 0;
    node = headA;
    while (node != null) {
        A[cnt++] = node;
        node = node.next;
    }
    cnt = 0;
    node = headB;
    while (node != null) {
        cnt++;
        node = node.next;
    }
    B = new ListNode[cnt];
    cnt = 0;
    node = headB;
    while (node != null) {
        B[cnt++] = node;
        node = node.next;
    }
    int pA = A.length - 1, pB = B.length - 1;
    while (pA >= 0 && pB >= 0 && A[pA] == B[pB]) {
        firstInterNode = A[pA];
        pA--;
        pB--;
    }
    return firstInterNode;
}
```

#### S2:环路法

还有一种思路挺有意思的，将两个链表连接起来，如果它们有交点，就会出现环路，那么将这个环路的第一个结点求出即可，之前的题目中求环路起点的。

```java
public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    if (headA == null || headB == null) return null;
    ListNode node = headA;
    while (node.next != null) {
        node = node.next;
    }
    node.next = headB;
    ListNode result = detectCycle(headA);
    node.next = null;
    return result;
}
private static ListNode detectCycle(ListNode head) {
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
```

