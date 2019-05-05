### Question

Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e., `[0,0,1,2,2,5,6]` might become `[2,5,6,0,0,1,2]`).

You are given a target value to search. If found in the array return `true`, otherwise return `false`.

**Example 1:**

```
Input: nums = [2,5,6,0,0,1,2], target = 0
Output: true
```

**Example 2:**

```
Input: nums = [2,5,6,0,0,1,2], target = 3
Output: false
```

**Follow up:**

-   This is a follow up problem to [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/description/), where `nums` may contain duplicates.
-   Would this affect the run-time complexity? How and why?

### Solution

与 search in rotated sorted array 一题基本相同，不同点在于本题中数字可重复，后者不存在重复数字。当存在可重复数字的时候，判断某一个数字在左部还是右部就与之前一题有些区别了。

假设数组中，左边一部分的连续子数组称为左部，右边一部分的连续子数组称为右部，左部第一个数字为 start ，则判断某一个数字 mid 在左部还是右部的规则如下：

1.  mid > start，此时在左部
2.  mid < start，此时在右部
3.  mid == start，此时可能在左部也可能在右部

然后就是 mid 的位置与下一步要判断的范围：

1.  mid 在左部
    1.  mid > target，target 可能在左部也可能在右部
    2.  mid < target，target 在左部，但是在 mid 的右边
2.  mid 在右部
    1.  mid > target，target 在右部但是在 mid 的左边
    2.  mid < target，target 可能在左部也可能在右部

所以联合上面两种判断，最终能得到 6 中不同的结果，代码如下：

```java
public static boolean search(int[] nums, int target) {
    if (nums == null || nums.length == 0) return false;
    return search(nums, target, 0, nums.length-1);
}
private static boolean search(int[] nums, int target, int start, int end) {
    if (start >= end) {
        return nums[start] == target;
    }
    int mid = (start + end) / 2;
    int num = nums[mid];
    if (num > target) {
        if (num > nums[start]) {
            return search(nums, target, start, mid-1) || search(nums, target, mid+1, end);
        } else if (num == nums[start]) {
            return search(nums, target, mid+1, end);
        } else {
            return search(nums, target, start, mid-1);
        }
    } else if (num < target) {
        if (num > nums[start]) {
            return search(nums, target, mid+1, end);
        } else {
            return search(nums, target, mid+1, end) || search(nums, target, start, mid-1);
        }
    } else {
        return true;
    }
}
```

