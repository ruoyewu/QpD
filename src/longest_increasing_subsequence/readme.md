### Question

Given an unsorted array of integers, find the length of longest increasing subsequence.

**Example:**

```
Input: [10,9,2,5,3,7,101,18]
Output: 4 
Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4. 
```

**Note:** 

-   There may be more than one LIS combination, it is only necessary for you to return the length.
-   Your algorithm should run in O(*n2*) complexity.

**Follow up:** Could you improve it to O(*n* log *n*) time complexity?

### Solution

先构建一个数组以保存从位置 0 到某一位置 i 的最长子数组长度。那么位置 i 处的最长子数组长度可由之前的解求出：对于 i 处之前的每个位置 j ，如果有`nums[j] < nums[i]`，代表`nums[i]`可以加入以`nums[j]`结尾的一个子数组，于是有`len[i] = len[j] + 1`，将所有的 j 都判断一遍，取其中最大的，就是当前`len[i]`的值。以此类推，便可以求解。

```java
public static int lengthOfLIS(int[] nums) {
    if (nums.length == 0) return 0;
    int[] length = new int[nums.length];
    length[0] = 1;
    int result = 1;
    for (int i = 1; i < nums.length; i++) {
        int max = 1;
        for (int j = 0; j < i; j++) {
            if (nums[j] > nums[i]) {
                max = Math.max(max, 1+length[j]);
            }
        }
        length[i] = max;
        result = Math.max(result, max);
    }
    return result;
}
```

或者，求出所有符合条件的子数组，取其长度最大。单个元素组成的子数组，必然是符合条件的，每向其中增加一个元素`nums[i]`，如果大于当前子数组中的最后一位数（也就是子数组中最大的数），直接加入即可，否则的话要移出所有大于`nums[i]`的数，然后将其加入数组，不外乎这两种操作。

```java
public static int lengthOfLIS(int[] nums) {
    if (nums.length == 0) return 0;
    int[] length = new int[nums.length];
    length[0] = nums[0];
    int pos = 1;
    for (int i = 1; i < nums.length; i++) {
        int j = pos;
        while (j > 0 && length[j-1] >= nums[i]) j--;
        length[j] = nums[i];
        if (j == pos) pos++;
    }
    return pos;
}
```

上面这种方法中，在判断该移多少位出子数组上，使用的是顺序查找，但对于一个有序数组而言，折半查找显然更有效率。

```java
public static int lengthOfLIS(int[] nums) {
    if (nums.length == 0) return 0;
    int[] length = new int[nums.length];
    length[0] = nums[0];
    int len = 1;
    for (int i = 1; i < nums.length; i++) {
        if (nums[i] < length[0]) {
            length[0] = nums[i];
        } else if (nums[i] > length[len-1]) {
            length[len++] = nums[i];
        } else {
            int index = binarySearch(length, 0, len-1, nums[i]);
            length[index] = nums[i];
        }
    }
    return len;
}
private static int binarySearch(int[] nums, int start, int end, int target) {
    while (start < end) {
        int mid = (start + end) / 2;
        if (nums[mid] > target) {
            end = mid;
        } else if (nums[mid] < target) {
            start = mid+1;
        } else {
            return mid;
        }
    }
    return end;
}
```

