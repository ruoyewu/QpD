Suppose an array of length `n` sorted in ascending order is **rotated** between `1` and `n` times. For example, the array `nums = [0,1,2,4,5,6,7]` might become:

- `[4,5,6,7,0,1,2]` if it was rotated `4` times.
- `[0,1,2,4,5,6,7]` if it was rotated `7` times.

Notice that **rotating** an array `[a[0], a[1], a[2], ..., a[n-1]]` 1 time results in the array `[a[n-1], a[0], a[1], a[2], ..., a[n-2]]`.

Given the sorted rotated array `nums`, return *the minimum element of this array*.

 

**Example 1:**

```
Input: nums = [3,4,5,1,2]
Output: 1
Explanation: The original array was [1,2,3,4,5] rotated 3 times.
```

**Example 2:**

```
Input: nums = [4,5,6,7,0,1,2]
Output: 0
Explanation: The original array was [0,1,2,4,5,6,7] and it was rotated 4 times.
```

**Example 3:**

```
Input: nums = [11,13,15,17]
Output: 11
Explanation: The original array was [11,13,15,17] and it was rotated 4 times. 
```

 

**Constraints:**

- `n == nums.length`
- `1 <= n <= 5000`
- `-5000 <= nums[i] <= 5000`
- All the integers of `nums` are **unique**.
- `nums` is sorted and rotated between `1` and `n` times.

### Solution

（部分）有序数组，目的是查找一个数字，非常典型的二分法使用场景。比如这个问题，数据旋转，使得一个有序数组变成两个有序子数组（分别是升序和降序），那么对于这样一个数组，如果直接取中间数字，根据它 nums[mid] 与数组第一位 nums[from] 的比较，就可以判断出最小值再哪一半中，然后再对这一半进行同样的操作。比较有三种情况：

1. nums[mid] > nums[from]，从数组旋转的规则可知，旋转之后的数组分为两段，两段都是升序，但前一段中的数字始终比后一半大，所以如果 nums[mid] > nums[from]，则有从 from 至 mid 都在同一段，所以当前段最小的就是 nums[from]，那么此时只需要计算出从 from+1 至 to 中最小的，与 nums[from] 对比即可
2. nums[mid] < nums[from]，这种情况下，可得知 mid 属于后一段，from 属于前一段，那么最小值一定位于 from 和 mid 中间，此时直接计算出从 from 至 mid 中最小的即可
3. nums[mid] = nums[from]，如果原有序数组中允许重复数字出现，就有可能出现这种情况，此时就无法判断 mid 和 from 分别属于哪一段了，这种情况下就直接分别计算下 from 至 mid 和 mid+1 至 to 两段的最小值，再取其中更小的即可

```java
public int findMin(int[] nums) {
    if (nums.length == 1) return nums[0];
    if (nums.length == 2) return Math.min(nums[0], nums[1]);
    return findMin(nums, 0, nums.length - 1, Integer.MAX_VALUE);
}
private int findMin(int[] nums, int from, int to, int min) {
    if (from == to) return Math.min(nums[from], min);
    int mid = (from + to) / 2;
    if (nums[from] < nums[mid]) {
        return findMin(nums, mid + 1, to, Math.min(nums[from], min));
    } else if (nums[from] > nums[mid]) {
        return findMin(nums, from, mid, min);
    } else {
        return Math.min(findMin(nums, from, mid, Math.min(nums[from], min)), findMin(nums, mid + 1, to, Math.min(nums[from], min)));
    }
}
```

