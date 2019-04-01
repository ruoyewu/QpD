### Question

Given an array of non-negative integers, you are initially positioned at the first index of the array.

Each element in the array represents your maximum jump length at that position.

Determine if you are able to reach the last index.

**Example 1:**

```
Input: [2,3,1,1,4]
Output: true
Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
```

**Example 2:**

```
Input: [3,2,1,0,4]
Output: false
Explanation: You will always arrive at index 3 no matter what. Its maximum
             jump length is 0, which makes it impossible to reach the last index.
```

###  Solution

本题是 jump game 一题的升级版，上一题求这样一个数组是否有解，本题则是求在给定有解的条件下，求最少走的步数。对于这样一个数组类的问题，可以首先考虑动态规划求解，对于本题，使用动态规划求出从起点到达每一个位置的最少步数，就可以得出起点到终点的最少步数。如下：

```java
public static int jump(int[] nums) {
    int n = nums.length;
    if (n < 2) return 0;
    
    int[] dp = new int[n];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0;
    
    for (int i = 0; i < n; i++) {
        int num = nums[i];
        for (int j = 1; j <= num && j + i < n; j++) {
            dp[i+j] = Math.min(dp[j+i], dp[i] + 1);
        }
    }
    return dp[n-1];
}
```

上面的代码中，dp[i] 存放的是从 0 至 i 位置的最少步数。此时的时间复杂度为多少？假定一个极端情况，在保证 i+j < n 的情况下使 num 达到最大，此时时间复杂度是$O(n^2)$，如果是最好的情况下，时间复杂度是$O(n)$。

这个问题应该还可以优化。上面这种解法，是求出每一个位置的最少步数，也就是以位置为主导，去求步数，是否能够反过来，以步数为主导，求出每一步能到达的最远距离？每一步都能够走到一个最大距离，而在这个距离内继续走一步，就可以走到下一步的最大距离，所以，只需要求出走到终点位置的时候走了几步即可，这一定就是最少步数，代码如下：

```java
public static int jump(int[] nums) {
    if (nums.length < 2) return 0;
    int step = 0, max = 0, i = 0, next = 0;
    while (true) {
        ++step;
        for (; i <= max; i++) {
            next = Math.max(next, i + nums[i]);
            if (next >= nums.length-1) return step;
        }
        max = next;
    }
}
```

next 记为这一步能够走到的最大距离，max 是上一步走过的最大步数，i 为当前走到的位置，也就是上上步能够走到的最大距离，所以从 i 至 max 这一段距离，就是上一步能够达到的范围，从上一步的范围中各走一步，可以得到下一步走的最大距离，以此类推。