### Question

Given `n` balloons, indexed from `0` to `n-1`. Each balloon is painted with a number on it represented by array `nums`. You are asked to burst all the balloons. If the you burst balloon `i` you will get `nums[left] * nums[i] * nums[right]` coins. Here `left` and `right` are adjacent indices of `i`. After the burst, the `left` and `right` then becomes adjacent.

Find the maximum coins you can collect by bursting the balloons wisely.

**Note:**

-   You may imagine `nums[-1] = nums[n] = 1`. They are not real therefore you can not burst them.
-   0 ≤ `n` ≤ 500, 0 ≤ `nums[i]` ≤ 100

**Example:**

```
Input: [3,1,5,8]
Output: 167 
Explanation: nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
             coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167
```

### Solution

本题要求消去整个数组的最大解，那么使用倒推的方法，最后一个消去的一定是数组`[0,...,n-1]`中的一个，假设是 i ，那么也就是说此时已经消去了左边的子数组`[0,...,i-1]`和右边的子数组`[i+1,...,n-1]`，有`[0,...,n-1]的解 = nums[i] * nums[-1] * nums[n] + [0,...,i-1]的解 + [i+1,...,n-1]的解`，为了使`[0,...,n-1]`的解最大，就需要求出`[0,...,i-1]`和`[i+1,...,n-1]`最大的解。

那么对于子数组`[0,...,i-1]`来说，也可以采用相同的求法，假设在这个数组中最后一个消去的位置是 j ，那么有`[0,...,i-1]的解 = nums[j] * num[-1] * num[i] + [0,...,j-1]的解 + [j+1,...,i-1]的解`。

子数组不断被分割成更小的子数组，最后，当这个子数组中只有一个元素的时候，如果这个在数组`[0,...,n-1]`中的位置是 k ，那么此时子数组`[k]`只有一个解为`nums[k] * nums[k-1] * nums[k+1]`，这是递归的结束条件。

于是有：

```java
public static int maxCoins(int[] nums) {
    int[] newNums = new int[nums.length+2];
    System.arraycopy(nums, 0, newNums, 1, nums.length);
    newNums[0] = newNums[nums.length+1] = 1;
    int[][] saved = new int[nums.length+2][nums.length+2];
    return max(newNums, 1, nums.length, saved);
}
private static int max(int[] nums, int start, int end, int[][] saved) {
    if (start > end) return 0;
    if (saved[start][end] != 0) return saved[start][end];
    int max = 0;
    for (int i = start; i <= end; i++) {
        int m = max(nums, start, i-1, saved) +
                nums[start-1] * nums[i] * nums[end+1] +
                max(nums, i+1, end, saved);
        max = Math.max(max, m);
    }
    saved[start][end] = max;
    return max;
}
```

这里还有一点，在计算伊始，将数组`[0,...,n-1]`扩展成了`[-1,...,n]`，因为题目里给出了条件，`nums[-1] = nums[n] = 1`，那么在计算元素个数小于 3 的数组的时候，也可以按照同样的规则进行计算。而此时注意，对于上面的这种来说，整个数组变成了`[0,...,n+1]`，待求解数组变成了`[1,...,n]`。

或者也可以将这个解法变成一个非递归的：

```java
public static int maxCoins3(int[] nums) {
    int[] newNums = new int[nums.length+2];
    System.arraycopy(nums, 0, newNums, 1, nums.length);
    newNums[0] = newNums[nums.length+1] = 1;
    nums = newNums;
    int len = nums.length;
    int[][] dp = new int[len][len];
    for (int right = 0; right < len; right++) {
        for (int left = right-1; left >= 0; left--) {
            int sub = nums[left] * nums[right];
            for (int k = left+1; k < right; k++) {
                dp[left][right] = Math.max(dp[left][right],
                        sub * nums[k] + dp[left][k] + dp[k][right]);
            }
        }
    }
    return dp[0][len-1];
}
```

在这里，外层的两个 right 和 left 循环，是为了求出子数组`[left,...,right]`的最大解，内层的 k 循环，是求最后消去的元素位置为 k 时的解。

right 从 0 开始递增，left 从 right 开始递减，对于数组`[0,...,n-1]`来说，`[0,...,right]`是长度递增的，对于数组`[0,...,right]`来说，`[left,...,right]`也是长度递增的，在这种情况下，才能使用动态规划求解。

或者上述的循环也可以是：

```java
for (int left = len-1; left >= 0; left--) {
    for (int right = left+1; right < len; right++) {
        int sub = nums[left] * nums[right];
        for (int k = left+1; k < right; k++) {
            dp[left][right] = Math.max(dp[left][right],
                    sub * nums[k] + dp[left][k] + dp[k][right]);
        }
    }
}
```

它分别从`[left,...,n-1]`和`[left,...,right]`递增。