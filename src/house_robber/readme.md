### Question

You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security system connected and **it will automatically contact the police if two adjacent houses were broken into on the same night**.

Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob tonight **without alerting the police**.

**Example 1:**

```
Input: [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
             Total amount you can rob = 1 + 3 = 4.
```

**Example 2:**

```
Input: [2,7,9,3,1]
Output: 12
Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
             Total amount you can rob = 2 + 9 + 1 = 12.
```

### Solution

#### S1:动态规划

求一组数字，这些数字在数组中的位置不能相邻，使得它们的和最大。对于某个位置 i 的数字而言，它有取或不取两种方法，如果不取这个数，此时最大能取到的位置就是 i-1 ，如果取这个数，上一个能取的最大位置就是 i-2 ，所以只要将这两种取法做个比较，便能够得到最大的那种取法。

```java
public static int rob(int[] nums) {
    if (nums.length == 0) return 0;
    if (nums.length == 1) return nums[0];
    int[] robs = new int[nums.length];
    int pp = 0, p = nums[0];
    for (int i = 1; i < nums.length; i++) {
        int m = Math.max(p, pp + nums[i]);
        pp = p;
        p = m;
    }
    return Math.max(pp, p);
}
```

或者换种思路：如果一定要取位置 i 处的数字，那么上一个可取的位置应该是 i-2 或 i-3 （如果上一个取 i-4 ，那么此时 i-4 与 i 中间隔了 3 个数，又因为所有的数都是非负数，所以 i-2 位置的数一定会取），那么只需要判断这二者取哪个更大就行了。

```java
public static int rob(int[] nums) {
    if (nums.length == 0) return 0;
    if (nums.length == 1) return nums[0];
    int[] robs = new int[nums.length];
    int ppp = 0, pp = nums[0], p = nums[1];
    for (int i = 2; i < nums.length; i++) {
        int m = Math.max(pp, ppp) + nums[i];
        ppp = pp;
        pp = p;
        p = m;
    }
    return Math.max(p, pp);
}
```

#### S2:递归

递归是一种反向求解，即对于某一位置 i 处的数字而言，它的下一个可取位置是 i+1 和 i+2 ，那么从 i 到结尾的这段子数组的解就可以有 i+1 和 i+2 这两个位置上的解推得，原理与上面基本一样：

```java
public static int rob(int[] nums) {
    if (nums.length == 0) return 0;
    if (nums.length == 1) return nums[0];
    int[] saved = new int[nums.length];
    Arrays.fill(saved, -1);
    saved[nums.length-1] = nums[nums.length-1];
    return Math.max(robber(nums, 0, saved), robber(nums, 1, saved));
}
public static int robber(int[] nums, int pos, int[] saved) {
    if (pos >= nums.length) return 0;
    if (saved[pos] >= 0) {
        return saved[pos];
    }
    int max = Math.max(nums[pos] + robber(nums, pos+2, saved), robber(nums, pos+1, saved));
    saved[pos] = max;
    return max;
}
```

