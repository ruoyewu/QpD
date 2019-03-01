### Question

Given an integer array, you need to find one **continuous subarray** that if you only sort this subarray in ascending order, then the whole array will be sorted in ascending order, too. 

You need to find the **shortest** such subarray and output its length.

**Example 1:**

```
Input: [2, 6, 4, 8, 10, 9, 15]
Output: 5
Explanation: You need to sort [6, 4, 8, 10, 9] in ascending order to make the whole array sorted in ascending order.
```

**Note:**

1.  Then length of the input array is in range [1, 10,000].
2.  The input array may contain duplicates, so ascending order here means **<=**. 

### Solution

#### S1:排序

要在一个数组中移动最小的一段子数组，使整个数组有序，那么在这段数组两边的两部分子数组必然都是与排序后的数组完全吻合的，所以如果我们求出这个数组有序时的状态，再与当前状态对比，求出这个数组两端与排序后数组吻合的位置，那么中间一部分不吻合的就是需要排序的那段子数组：

```java
public static int findUnsortedSubarray(int[] nums) {
    int[] tmp = new int[nums.length];
    System.arraycopy(nums, 0, tmp, 0, nums.length);
    Arrays.sort(tmp);
    
    int start = 0, end = nums.length-1;
    while (start < nums.length && tmp[start] == nums[start]) start++;
    if (start == nums.length) return 0;
    
    while (end > start && tmp[end] == nums[end]) end--;
    return end-start+1;
}
```

分别求出左右两端吻合的长度，长度分别是 start 和 nums.length-end-1 ，那么需要排序的数组就是 end-start+1 。特别的，当执行完第一个 while 循环的时候，如果有`start == num.length`，就意味着所有的位置都与有序数组吻合，则无序的一段子数组长度为 0 。

#### S2:直接求

本题要求这段无序子数组的长度，换言之就是求这段数组的起始位置 start 和结束位置 end 。上面一种方法使用先排序的方式求解，或者也可以不排序，直接根据这些未知的特点求解。经过分析可以得到：

1.  start-1 位置后面的数应该都大于它，否则 start 要向前移直到 0
2.  end+1 位置前面的数应该都小于它，否则 end 要向后移直到 nums.length

假设对于一个数组`[0,...,n-1]`来说，它的无序子数组段为`[start,...,end]`，那么就意味着`[0,...,start-1]`和`[end+1,...,n-1]`都是有序的，且有`nums[start-1] < nums[start,...,end] < nums[end+1]`，这个关系式便是上面那两个条件的由来。所以我们便可以不在排序的基础上直接对 start 和 end 进行一次遍历求算。

```java
public static int findUnsortedSubarray4(int[] nums) {
    // 求 start
    int pos = 1;
    while (pos < nums.length && nums[pos] >= nums[pos-1]) pos++;
    if (pos == nums.length) return 0;
    int start = pos-1;
    while (pos < nums.length) {
        while (start >= 0 && nums[start] > nums[pos]) start--;
        if (start == -1) break;
        pos++;
    }
    
    // 求 end
    pos = nums.length-2;
    while (pos > start && nums[pos] <= nums[pos+1]) pos--;
    int end = pos + 1;
    while (pos > start) {
        while (end < nums.length && nums[pos] > nums[end]) end++;
        if (end == nums.length) break;
        pos--;
    }
    
    return end - start - 1;
}
```

以求算 start 为例，先找到一个递增的序列`[0,...,pos-1]`，暂定 start 为 pos-1 ，再继续往后根据上面的条件 1 判断完整个数组即可。

或者也可以一次遍历完成：

```java
public static int findUnsortedSubarray(int[] nums) {
    int pos = 1;
    while (pos < nums.length && nums[pos] >= nums[pos-1]) pos++;
    if (pos == nums.length) return 0;
    int start = pos-1, max = nums[pos-1];
    while (start >= 0 && nums[start] > nums[pos]) start--;
    
    int end = 0;
    while (pos < nums.length) {
        while (pos < nums.length && nums[pos] >= max) max = nums[pos++];
        if (pos < nums.length) {
            end = pos;
            while (start >= 0 && nums[start] > nums[end]) start--;
        }
        pos++;
    }
    return end - start;
}
```

上面一段代码是为了先找到一个 start 的初始位置，然后接着进行循环，通过保存一个 max 值，在后续遍历的过程中不断判断 end 的值和 start 的值。