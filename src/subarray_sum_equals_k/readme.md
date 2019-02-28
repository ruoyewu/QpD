### Question

Given an array of integers and an integer **k**, you need to find the total number of continuous subarrays whose sum equals to **k**.

**Example 1:**

```
Input:nums = [1,1,1], k = 2
Output: 2
```

**Note:**

1.  The length of the array is in range [1, 20,000].
2.  The range of numbers in the array is [-1000, 1000] and the range of the integer **k** is [-1e7, 1e7].

### Solution

#### S1:二重循环

本题要寻找一个数组 nums 的子数组，子数组是连续的，并且和为 k ，求子数组的总数，所以只要求出所有满足条件的子数组即可，对于这样一个子数组来说，只需要知道了这个子数组的起始位置和结束位置，就可以确定一个子数组，所以，可以选择使用一个二重循环求解：

```java
public static int subarraySum(int[] nums, int k) {
    int count = 0;
    int sum;
    for (int i = 0; i < nums.length; i++) {
        sum = 0;
        for (int j = i; j < nums.length; j++) {
            sum += nums[j];
            if (sum == k) {
                count++;
            }
        }
    }
    return count;
}
```

#### S2:缓存

是否可以不使用二重循环解题？如果不使用二重循环的话，如何能够快速求出任意一个子数组的和？比如要求一个子数组`[i,..,j]`的和，那么这个该怎么求？有关系式：`sum(i,j) = sum(0,j) - sum(0, i)`，所以，只要能求出所有`[0,...,i]`的和，那么其余子数组的和都可以通过这个结果一步求算。

于是，我们要求出`sum(i, j) = k`，就可以转化为求`k = sum(0, j) - sum(0, i)`。也就是说，通过这种转换，可以将一个二维的问题变成一个一维的问题（求一个子数组的起始位置和结束位置是一个二维问题，而子数组的结束位置是一个一维问题）。所以，对于 0～j 位置的和`sum(0, j)`而言，如果存在位置 i 使得`sum(0, j) - sum(0, i) = k`，也就是求出所有值为 sum(0, i) 使`sum(0, i) = sum(0, j) - k`。可以有多个值为`sum(0, i)`，比如说有`sum(0, i1) = sum(0, i2)`，那么就可以得出有`sum(i1, j) = sum(i2, j) = k`，也就是说有两个子数组的和都是 k 。

因为我们要求出所有`sum(0, i) = sum(0, j) - k`的`sum(0, i)`，那么如果有`sum(0, i1) = sum(0, i2)`的话，可以直接保存一个值`sum(0, i)`和等于这个值的子数组的个数 count ，然后使用一个 HashMap 保存起来。

```java
public static int subarraySum(int[] nums, int k) {
    if (nums.length == 1) {
        return nums[0] == k ? 1 : 0;
    }
    for (int i = 1; i < nums.length; i++) {
        nums[i] += nums[i-1];
    }
    int count = 0;
    HashMap<Integer, Integer> map = new HashMap<>();
    for (int num : nums) {
        if (num == k) {
            // 当前数字就是 k ，直接加 1
            count++;
        }
        if (map.containsKey(num-k)) {
            // 找到了 sum(0, i) = sum(0, j) - k，则加上值为 sum(0, i) 的子数组的个数
            count += map.get(num-k);
        }
        // 如果 map.get(num) 不为 0 ，也就是说之前存在若干个 sum(0, i) 与当前的和相等，则整合
        // 如果为 0 ，则置 1
        map.put(num, map.getOrDefault(num, 0) + 1);
    }
    return count;
}
```

HashMap 的键就是`sum(0, i)`的值，值就是等于`sum(0, i)`的子数组的个数。