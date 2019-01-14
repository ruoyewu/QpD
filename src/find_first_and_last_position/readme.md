### Question

Given an array of integers `nums` sorted in ascending order, find the starting and ending position of a given `target` value.

Your algorithm's runtime complexity must be in the order of *O*(log *n*).

If the target is not found in the array, return `[-1, -1]`.

**Example 1:**

```
Input: nums = [5,7,7,8,8,10], target = 8
Output: [3,4]
```

**Example 2:**

```
Input: nums = [5,7,7,8,8,10], target = 6
Output: [-1,-1]
```

### Solution

题为一个有序数组的搜索问题，自然想到二分搜索，但是一般的二分搜索只能找到某个元素所在的位置，本题要找的是这一元素的存在序列位置，只需要对一般的二分搜索稍加改动即可。

#### S1:改动二分搜索

因为这是一个有序数组，故而同一元素必然相连，那么只需要找到其中一个元素的位置，然后沿着这个点向两边延伸，自然能够找到所有的位置，算法如下：

```java
public static int[] searchRange(int[] nums, int target) {
    if (nums.length == 0) {
        return new int[]{-1, -1};
    }
    int start = 0, end = nums.length - 1, pos = 0;
    while (start <= end) {
        pos = (start + end) / 2;
        if (nums[pos] == target) {
            break;
        }
        if (nums[pos] > target) {
            end = pos - 1;
        } else {
            start = pos + 1;
        }
    }
    if (nums[pos] != target) {
        return new int[]{-1, -1};
    }
    int i = 0;
    start = end = pos;
    while (true) {
        boolean change = false;
        if (pos - i >= 0 && nums[pos-i] == target) {
            start = pos-i;
            change = true;
        }
        if (pos + i < nums.length && nums[pos+i] == target) {
            end = pos+i;
            change = true;
        }
        if (!change) {
            break;
        }
        i++;
    }
    return new int[]{start, end};
}
```

不过虽然查找元素的时间是$\log n$，但是如果这一元素的个数太多，假设数组中所有的元素都一样，那么整个算法的时间复杂度就会增加到$O(n)$，是一个比较不稳定的算法，而更加理想的方法是，使用两次二分搜索，找到元素的开始和结束的位置。

#### S2:S1改进版

要找到元素的开始和结束的位置，也可以使用二分查找。不过此二分查找的终点并不是找到某一位置的值为待查找元素，而是找到小于待查找元素的最大值的位置，以及大于待查找元素的最小值的位置，所以要对原有的二分查找做一些变动：

```java
/**
 * @param left: true, return the start
 *            false, return the end
 */
public static int binarySearch(int[] nums, int target, boolean left) {
    int start = 0, end = nums.length - 1, mid = 0;
    while (start <= end) {
        mid = (start + end) / 2;
        if (left) {
            if (nums[mid] >= target) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        } else {
            if (nums[mid] <= target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
    }
    if (left) {
        return nums[mid] == target ? mid : mid+1;
    } else {
        return nums[mid] == target ? mid : mid-1;
    }
}

public static int[] searchRange(int[] nums, int target) {
    if (nums.length == 0) {
        return new int[]{-1, -1};
    }
    int left = binarySearch(nums, target, true);
    int right = binarySearch(nums, target, false);
    if (left > right) {
        return new int[]{-1, -1};
    }
    return new int[]{left, right};
}
```

如此，只需要两个$\log n$时间便可以确定元素存在的区间。