### Question

You are given a list of non-negative integers, a1, a2, ..., an, and a target, S. Now you have 2 symbols `+`and `-`. For each integer, you should choose one from `+` and `-` as its new symbol.

Find out how many ways to assign symbols to make sum of integers equal to target S. 

**Example 1:**

```
Input: nums is [1, 1, 1, 1, 1], S is 3. 
Output: 5
Explanation: 

-1+1+1+1+1 = 3
+1-1+1+1+1 = 3
+1+1-1+1+1 = 3
+1+1+1-1+1 = 3
+1+1+1+1-1 = 3

There are 5 ways to assign symbols to make the sum of nums be target 3.
```

**Note:**

1.  The length of the given array is positive and will not exceed 20. 
2.  The sum of elements in the given array will not exceed 1000.
3.  Your output answer is guaranteed to be fitted in a 32-bit integer.

### Solution

#### S1:直接递归

题目中指出，将数组中的每个数前面加上一个符号（+ 或 -），使得它们构成一个运算式，并且其计算结果等于给定的 S ，求符号的添加种类。

那么也就是说，对于每一个数字，我们可以选择加上它或者减去它，于是，可以直接使用递归，进行$2^n$次运算，求出所有的符号添加方式，然后再求其中结果为 S 的即可：

```java
public static int findTargetSumWays(int[] nums, int S) {
    return ways(nums, S, 0, 0);
}
private static int ways(int[] nums, int S, int pos, int cur) {
    if (pos == nums.length) {
        return cur == S ? 1 : 0;
    } else {
        return ways(nums, S, pos+1, cur+nums[pos])
                + ways(nums, S, pos+1, cur-nums[pos]);
    }
}
```

#### S2:缓存递归

上述递归很有可能会增加很多不必要的计算，如对于一个数组`[1,1,1,1,1,1]`，如果前两个数字的符号分别是`[-,+]`和`[+,-]`，那么计算到第三个数字的时候，这两种符号添加方式指向了同一种情况，如果不加处理，肯定要造成后面的`[1,1,1,1]`的重复计算，此时可以考虑给递归函数加一个缓存。

每个递归函数有两个变量：

1.  当前位置
2.  当前的运算结果

所以初步考虑缓存应该是一个二维数组，此时就要判断这两个变量各自的取值范围：

1.  对于当前的位置，肯定是要在 nums 里面，也就是说，它的范围是 0～n-1
2.  由于数组中所有的数字都是正数，那么必然所有符号取 + 结果最大，所有符号取 - 结果最小，题目里面也指出，所有的数字和是不大于 1000 的，所以，范围取上下 1000 即可，也就是 2001

那么，添加了缓存之后就是：

```java
public static int findTargetSumWays(int[] nums, int S) {
    int[][] saved = new int[nums.length][2001];
    for (int[] row : saved) {
        Arrays.fill(row, -1);
    }
    return ways2(nums, S, 0, 0, saved);
}
private static int ways2(int[] nums, int S, int pos, int cur, int[][] saved) {
    if (pos == nums.length) {
        return cur == S ? 1 : 0;
    } else {
        if (saved[pos][cur+1000] >= 0) return saved[pos][cur+1000];
        int ways = ways2(nums, S, pos+1, cur+nums[pos], saved)
                + ways2(nums, S, pos+1, cur-nums[pos], saved);
        saved[pos][cur+1000] = ways;
        return ways;
    }
}
```

1000 是数组中数字和的上限，如果想要缩减一下内存使用量，也可以直接计算出数字和。

#### S3:动态规划

动态规划也需要一个数组保存记录值，与递归中使用的缓存数组类似。对于一个数组`[1,...,n]`来说，如果当前待判断位置是 i ，也就是说已经计算出了前面`[1,...,i-1]`这部分子数组的结果，每个数字有两种符号添加，也就是说总的应该有$2^{i-1}$种结果（可能会有重合的），那么在这$2^{i-1}$种结果加上（或者减去）nums[i] 之后，就会得到$2^i$种新的结果（当然，有可能在前面的$2^{i-1}$种结果中，存在 x y ，使得 x-nums[i] == y+nums[i] == cur），此时 cur 对应的解应该是 x y 两个数对应的解的和。那么当整个数组计算完成之后，就可以求出数字 S 对应的解。

```java
public static int findTargetSumWays(int[] nums, int S) {
    int sum = 0;
    for (int num : nums) sum += num;
    if (sum < S || ((S + sum) & 1) == 1) return 0;
    
    int[] dp = new int[(sum<<1) + 1];
    dp[nums[0] + sum] = 1;
    dp[-nums[0] + sum] += 1;
    for (int i = 1; i < nums.length; i++) {
        int[] next = new int[(sum<<1) + 1];
        for (int j = -sum; j <= sum; j++) {
            if (dp[j + sum] > 0) {
                next[j + sum + nums[i]] += dp[j + sum];
                next[j + sum - nums[i]] += dp[j + sum];
            }
        }
        dp = next;
    }
    return dp[S + sum];
}
```

如上，第一个循环是遍历数组，第二个循环则是遍历以求出的$2^{i-1}$种结果，并将其分别加上（减去）nums[i] 以求出下一个位置的$2^i$种结果，当然，上述结果肯定是存在重合的，因为所有的运算结果都是在 -sum～sum 的范围内，所以当我们需要遍历已求出的$2^{i-1}$种结果时，由于会有重合，我们并不知道到底有多少种结果，这时的处理办法就是遍历整个可能的结果（从 -sum 到 sum），当这个结果对应的解不为 0 时，就意味着这是一个$2^{i-1}$个结果中的一个。

所以，本题中首先给执行了`dp[nums[0] + sum] = 1;`和`dp[-nums[0] + sum] += 1;`这两个代码，就是为了先求出$2^1$的结果，然后之后求第二个位置的$2^2$的结果的时候，才能利用已求出的结果。

另外，计算下一个位置的结果的时候的代码`next[j + sum + nums[i]] += dp[j + sum];`中使用的是`+=`符号，就是将所有导向统一结果的解相加

还有，S 与 sum 是否有什么关系？为什么要判断`((S + sum) & 1) == 1`？

本题中每个数字前都有一个符号，+ 或 - ，如果将整个数组按照数字之前的符号分成两部分，一部分的符号全是 + ，另一部分的符号全是 - ，然后让两部分的和分别是 x y ，那么有关系式：

```
x + y = sum
x - y = S
```

于是有`x + x = S + sum = 2 * x`，于是得出结论：S + sum 必然是偶数，所以才有了本题开始的判断。

#### S4:划分数组 & 动态规划

上面的这个关系式`S + sum = 2 * x`也可以加以利用，得出`(S + sum) / 2 = x`，也就是说本题可以变为，使 nums 数组的子数组的和等于 S+sum 的一半，求出符合条件的子数组的个数。

于是有：

```java
public static int findTargetSumWays(int[] nums, int S) {
    int sum = 0;
    for (int num : nums) sum += num;
    if (sum < S || ((sum + S) & 1) == 1) return 0;
    sum = (sum + S) >> 1;
    int[] dp = new int[sum+1];
    dp[0] = 1;
    for (int num : nums) {
        for (int i = sum; i >= num; i--) {
            dp[i] += dp[i-num];
        }
    }
    return dp[sum];
}
```

此时，外层是对数组的循环，内层通过一个 i=num～sum 的循环，求出所有加上当前位置的 num 能到得到 i 的解。外层对数组的循环，每到一个数字 num ，就是指对于子数组`[0,...,num]`来说，dp[i] 存放着数组`[0,...,num]`中和为 i 的子数组的个数，那么外层对数组的循环完了之后，dp[i] 存放的就是数组 nums 的和为 i 的子数组的个数，所以，此时 dp[sum] 就是本题的解。

其中，起始位置的`dp[0] = 1`，是整个计算的开端，由 0+num=num ，得到`dp[num] = 1`，然后以此类推。