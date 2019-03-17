### Question

Given a sorted array and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

You may assume no duplicates in the array.

**Example 1:**

```
Input: [1,3,5,6], 5
Output: 2
```

**Example 2:**

```
Input: [1,3,5,6], 2
Output: 1
```

**Example 3:**

```
Input: [1,3,5,6], 7
Output: 4
```

**Example 4:**

```
Input: [1,3,5,6], 0
Output: 0
```

### Solution

一个有序数组的查找问题，可以有两种方式：二分查找和顺序查找。

#### S1:二分查找

```java
public static int searchInsert(int[] nums, int target) {
    int left = 0, right = nums.length-1, mid;
    while (left <= right) {
        mid = (left + right) / 2;
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] > target) {
            right = mid-1;
        } else {
            left = mid+1;
        }
    }
    return left;
}
```

#### S2:顺序查找

```java
public static int searchInsert2(int[] nums, int target) {
    int pos = 0;
    while (pos < nums.length && nums[pos] < target) pos++;
    return pos;
}
```

