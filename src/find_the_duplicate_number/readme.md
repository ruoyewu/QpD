### Question

Given an array *nums* containing *n* + 1 integers where each integer is between 1 and *n* (inclusive), prove that at least one duplicate number must exist. Assume that there is only one duplicate number, find the duplicate one.

**Example 1:**

```
Input: [1,3,4,2,2]
Output: 2
```

**Example 2:**

```
Input: [3,1,3,4,2]
Output: 3
```

**Note:**

1.  You **must not** modify the array (assume the array is read only).
2.  You must use only constant, *O*(1) extra space.
3.  Your runtime complexity should be less than *O*(*n*2).
4.  There is only one duplicate number in the array, but it could be repeated more than once.

### Solution

如果仅仅是对一个数组求解的话，可以用排序等方法。但是本题显然不是，首先，题目给出条件，对于长度为 n+1 的数组，即索引值为 0 到 n ，数字的范围是 1 到 n ，那么这个数组就可以表示一个链表，如数组`[1,3,4,2,2]`表示的链表是`0->1->3->2->4->2`，这是一个循环链表，循环部分是`2->4`，并且循环段的第一个结点正是数组中重复出现的数。这不是偶然，而是必然，数组中多个重复的数意味着在链表中有多个结点指向这同一个结点，这在链表中的确就是循环段的起始结点。

所以，这这样一个数组中寻找重复出现的数，变成了找一个链表中的循环段的起始结点，后者是出现过的一道题，即 Linked list cycle II，可使用双指针求出这个结点：

```java
public static int findDuplicate(int[] nums) {
    int slow = nums[0], fast = nums[0];
    do {
        slow = nums[slow];
        fast = nums[nums[fast]];
    } while (slow != fast);
    int head = nums[0];
    while (head != slow) {
        head = nums[head];
        slow = nums[slow];
    }
    return head;
}
```

