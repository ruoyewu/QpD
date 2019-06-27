### Question

Given two sorted integer arrays *nums1* and *nums2*, merge *nums2* into *nums1* as one sorted array.

**Note:**

- The number of elements initialized in *nums1* and *nums2* are *m* and *n* respectively.
- You may assume that *nums1* has enough space (size that is greater or equal to *m* + *n*) to hold additional elements from *nums2*.

**Example:**

```
Input:
nums1 = [1,2,3,0,0,0], m = 3
nums2 = [2,5,6],       n = 3

Output: [1,2,2,3,5,6
```

### Solution

要求这样一个问题，有两种解决办法，对应着两种不同的需求，比如直接将 nums2 所有元素插入到 nums1 的末端，然后对整个 nums1 排序，便可得到结果，这种方法下不会使用到额外的存储空间，但是时间复杂度却上升到$O(n\log n)$，或者是另一种，声明一个额外的空间存储结果，然后依次选择 nums1 和 nums2 两个数组中较小的一个加入到新的数组中，此时时间复杂度为$O(m+n)$，但是空间复杂度上升为$O(m)$。各有优劣，但不知道是否存在同时满足时间复杂度$O(m+n)$和空间复杂度$O(1)$的解法。

第一种解法代码如下：

```java
public static void merge(int[] nums1, int m, int[] nums2, int n) {
    if (n == 0) return;
    int p1 = 0;
    while (p1 < m) {
        if (nums1[p1] <= nums2[0]) {
            p1++;
        } else {
            int tmp = nums1[p1], p2 = 1;
            nums1[p1] = nums2[0];
            while (p2 < n && nums2[p2] < tmp) {
                nums2[p2-1] = nums2[p2];
                p2++;
            }
            nums2[p2-1] = tmp;
        }
    }
    for (int num : nums2) {
        nums1[p1++] = num;
    }
}
```

第二种如下：

```java
public static void merge(int[] nums1, int m, int[] nums2, int n) {
    if (m > n) {
        merge2(nums2, n, nums1, m);
        return;
    }
    int[] nums3 = new int[m];
    System.arraycopy(nums1, 0, nums3, 0, m);
    int p3 = 0, p2 = 0, p1 = 0;
    while (p3 < m && p2 < n) {
        if (nums3[p3] <= nums2[p2]) {
            nums1[p1++] = nums3[p3++];
        } else {
            nums1[p1++] = nums2[p2++];
        }
    }
    while (p3 < m) {
        nums1[p1++] = nums3[p3++];
    }
    while (p2 < n) {
        nums1[p1++] = nums2[p2++];
    }
}
```