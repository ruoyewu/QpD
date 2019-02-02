### Question

Given an array *nums*, there is a sliding window of size *k* which is moving from the very left of the array to the very right. You can only see the *k* numbers in the window. Each time the sliding window moves right by one position. Return the max sliding window.

**Example:**

```
Input: nums = [1,3,-1,-3,5,3,6,7], and k = 3
Output: [3,3,5,5,6,7] 
Explanation: 

Window position                Max
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7
```

**Note:** 
You may assume *k* is always valid, 1 ≤ k ≤ input array's size for non-empty array.

**Follow up:**
Could you solve it in linear time?

### Solution

#### S1：双端队列

每个位置都需要找到 k 个数字中的最大值，如果要单纯去找某个位置对应的最大值，当然需要遍历这 k 个数字，但是放到一个连续的位置上看，每个位置相比于前一个位置，都只是删除一个增加一个。这就使得问题在整体的范围内可以得到更加高效的解法。

如何使用一个双端队列解题？队列中应该存放什么？自然是当前窗口中的值，但不是全部。试想，对于位置 i 处的解，它的数字`nums[i]`，位于窗口的最后一个，那么现在在这个窗口中，位于 i 之前的、小于`nums[i]`的数字还会用到吗？因为求解是从前往后依次求解的，窗口也是不断后移的，如果在之后的窗口中包含`nums[i-1]`，那么它必然也包含`nums[i]`，所以，在当前窗口中所有小于`nums[i]`的值都是无用的。另外，窗口不断后移，就要不断将窗口中的第一个数字舍弃。

那么应该在双端队列中存放什么？应该存放当前窗口中的值，但不是所有，而是一个递减的序列：应该舍弃那些小于它右边的数字的数字；存放的也不应该是数字，而是数字在数组中的位置：这样才能判断某个数字是不是在当前的窗口里。

于是：

```java
public static int[] maxSlidingWindow(int[] nums, int k) {
    if (nums.length == 0 || k == 0) return new int[0];
    int n = nums.length, m = n-k+1;
    LinkedList<Integer> list = new LinkedList<>();
    int[] result = new int[m];
    int pos = 0;
    for (int i = 0; i < n; i++) {
        // 删去所有不在当前窗口的数字
        while (!list.isEmpty() && list.getFirst() <= i-k) list.removeFirst();
        // 删去所有小于当前数字的数字
        while (!list.isEmpty() && nums[list.getLast()] < nums[i]) list.removeLast();
        list.add(i);
        if (i-k+1 >= 0) {
            result[pos++] = nums[list.getFirst()];
        }
    }
    return result;
}
```

#### S2:直接比较

当求位置 i 处的解时，已经求得了位置 i-1 处对应窗口中的最大值，那么，如果当前位置的数字`nums[i]`大于上一个窗口的最大值的话，在新的窗口中，它一定是最大的，这时解就是它本身。否则的话，在新的窗口中，上一个窗口的最大值大概率上也是当前窗口中的最大值，不过有一例外：上一窗口的最大值位于窗口的第一个位置，这就意味着这个最大值不会再有存在于当前的窗口。此时，就要求当前窗口的最大值（如遍历）。

如下：

```java
public static int[] maxSlidingWindow(int[] nums, int k) {
    if (k == 0) return new int[0];
    int n = nums.length, m = n - k + 1;
    int[] result = new int[m];
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < k; i++) {
        if (max < nums[i]) max = nums[i];
    }
    result[0] = max;
    for (int i = k; i < n; i++) {
        if (nums[i] > max) {
            max = nums[i];
        } else if (nums[i-k] == max) {
            max = nums[i];
            for (int j = i-1; j > i-k; j--) {
                if (nums[j] > max) max = nums[j];
            }
        }
        result[i-k+1] = max;
    }
    return result;
}
```

