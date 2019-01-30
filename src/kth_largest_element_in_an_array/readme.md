### Question

Find the **k**th largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.

**Example 1:**

```
Input: [3,2,1,5,6,4] and k = 2
Output: 5
```

**Example 2:**

```
Input: [3,2,3,1,2,4,5,5,6] and k = 4
Output: 4
```

**Note:** 
You may assume k is always valid, 1 ≤ k ≤ array's length.

### Solution

#### S1:数组排序

要求一个数组中倒数第 k 大的树，可以先将这个数组排序，然后直接访问即可：

```java
public static int findKthLargest(int[] nums, int k) {
    Arrays.sort(nums);
    return nums[nums.length-k];
}
```

#### S2:部分排序

在对数组排序的过程中，有些排序算法是每一步确定一个树的最终位置，如直接选择排序、堆排序、快速排序等。它们每次确定一个数的位置所需的时间分别是$O(n)$和$O(\log n)$，那么，如果使用后两种排序方法求解的话，时间复杂度就能降到$O(k\log n)$，如下：

使用堆排序：

```java
public static int findKthLargest(int[] nums, int k) {
    for (int i = nums.length/2; i >= 0 ; i--) {
        heapAdjust(nums, i, nums.length-1);
    }
    for (int i = nums.length-1; ; i--) {
        if (i == nums.length-k) {
            return nums[0];
        }
        int tmp = nums[0];
        nums[0] = nums[i];
        nums[i] = tmp;
        heapAdjust(nums, 0, i-1);
    }
}
private static void heapAdjust(int[] nums, int start, int end) {
    int tmp = nums[start];
    for (int i = 2 * start + 1; i <= end; i = 2 * i + 1) {
        if (i < end && nums[i] < nums[i+1]) {
            i += 1;
        }
        if (tmp > nums[i]) break;
        nums[start] = nums[i];
        start = i;
    }
    nums[start] = tmp;
}
```

使用快速排序：

```java
public static int findKthLargest3(int[] nums, int k) {
    return quickSort(nums, 0, nums.length-1, nums.length-k);
}
private static int quickSort(int[] nums, int start, int end, int k) {
    int p = partition(nums, start, end);
    if (p == k) return nums[p];
    return p > k ? quickSort(nums, start, p-1, k) : quickSort(nums, p+1, end, k);
}
private static int partition(int[] nums, int start, int end) {
    int tmp = nums[start];
    while (start < end) {
        while (start < end && nums[end] >= tmp) end--;
        nums[start] = nums[end];
        while (start < end && nums[start] <= tmp) start++;
        nums[end] = nums[start];
    }
    nums[start] = tmp;
    return start;
}
```

