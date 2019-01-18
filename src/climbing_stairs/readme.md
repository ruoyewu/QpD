### Question

You are climbing a stair case. It takes *n* steps to reach to the top.

Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

**Note:** Given *n* will be a positive integer.

**Example 1:**

```
Input: 2
Output: 2
Explanation: There are two ways to climb to the top.
1. 1 step + 1 step
2. 2 steps
```

**Example 2:**

```
Input: 3
Output: 3
Explanation: There are three ways to climb to the top.
1. 1 step + 1 step + 1 step
2. 1 step + 2 steps
3. 2 steps + 1 step
```

### Solution

对于每次只能走 1 或 2 步的爬楼梯问题，意味着到达任意一个位置有两种方法，即从上一个位置走一步，或者从上上个位置走两步，那么我们只需要求得到达前两个位置各有多少种走法，便可以得到到达当前位置的走法数量。

#### S1:动态规划

对于每一个固定位置而言，都有一个解，且这个解只与前两个位置的解有关，于是有如下的关系式：`[i] = [i-1] + [i-2]`，于是可以使用动态规划的方法求解，使用一个数组保存每个位置的解，一次遍历到达位置 n 便可。

```java
public static int climbStairs(int n) {
    if (n == 0) return 0;
    if (n == 1) return 1;
    if (n == 2) return 2;
    int[] stairs = new int[n];
    stairs[0] = 1;
    stairs[1] = 2;
    for (int i = 2; i < n; i++) {
        stairs[i] = stairs[i-2] + stairs[i-1];
    }
    return stairs[n-1];
}
```

这时对于空间复杂度为$O(n)$而言，还是可以优化的，因为每个位置只需要用到前两个位置的解，那么只使用两个变量保存着两个解也未尝不可：

```java
public static int climbStairs(int n) {
    if (n == 0) return 0;
    if (n == 1) return 1;
    if (n == 2) return 2;
    int l = 1;
    int ll = 2;
    int s = 0;
    for (int i = 2; i < n; i++) {
        s = l + ll;
        l = ll;
        ll = s;
    }
    return s;
}
```

此时时间复杂度为$O(n)$，空间复杂度为$O(1)$。

#### S2:矩阵求解

首先看一个证明：

已知$F_n$表示第 n 个阶梯的解，是否有矩阵$Q^n = \begin{pmatrix} F_n & F_{n-1} \\ F_{n-1} & F_{n-2} \end{pmatrix}$，使得则有$O^n[0][0] = F_n$？利用数学归纳法证明：

首先$Q = \begin{pmatrix} 1 & 1 \\ 1 & 0 \end{pmatrix}$，此时有$Q[0][0] = F_0$，成立；

假设有$Q^n = \begin{pmatrix} F_n & F_{n-1} \\ F_{n-1} & F_{n-2} \end{pmatrix}$，那么有$Q^{n+1} = Q * Q^n = \begin{pmatrix} 1 & 1 \\ 1 & 0 \end{pmatrix} * \begin{pmatrix} F_n & F_{n-1} \\ F_{n-1} & F_{n-2} \end{pmatrix} = \begin{pmatrix} F_n + F_{n-1} & F_n \\ F_{n-1} + F_{n-2} & F_{n-1} \end{pmatrix} = \begin{pmatrix} F_{n+1} & F_{n} \\ F_n & F_{n-1} \end{pmatrix}$，与$Q^n$进行比较，得证。

那么通过公式$Q^n = \begin{pmatrix} F_n & F_{n-1} \\ F_{n-1} & F_{n-2} \end{pmatrix}$如何对我们的求解过程化简？再看这个关系式$Q^{2n} = (Q^n)^2$，这个关系式意味着我们可以直接由$Q^n$得到$Q^{2n}$的值，也就是说可以由$F_n$直接求$F_{2n}$，而不是由$F_{2n-1}$和$F_{2n-2}$求解，那么这个问题的时间复杂度就变成了$O(\log n)$，如果使用这种方法求解，必然能提高求解效率。

但是待求解的 n 不一定是 2 的整指数倍，如，$57 = 2^5 + 2^4 + 2^3 + 2^0$，对于任何一个数而言，而 57 的二进制也恰好是$57_{10} = 111001_2$，所以，按照这样一个二进制的顺序，如果当前位置为 1 ，那么便把当前位置的值加上，那么对于 57 这样一个数而言，我们只需要计算 6 次，分别是$2^0,2^1,2^2,2^3,2^4,2^5$，便能得到 57 对应的解。具体代码如下：

```java
public static int climbStairs3(int n) {
    int[][] q = {{1,1}, {1,0}};
    int[][] res = {{1,0}, {0,1}};
    while (n > 0) {
        if ((n & 1) == 1) {
            res = multiply(res, q);
        }
        n >>= 1;
        q = multiply(q, q);
    }
    return res[0][0];
}
public static int[][] multiply(int[][] a, int[][] b) {
    int[][] c = new int[2][2];
    for (int i = 0; i < 2; i++) {
        for (int j = 0; j < 2; j++) {
            c[i][j] = a[i][0] * b[0][j] + a[i][1] * b[1][j];
        }
    }
    return c;
}
```

#### S3:公式法

其实这个问题的解序列就是斐波那契数列，而斐波那契数列中的某一项是可以通过公式求出的，如下：

$F_n = \frac {1} {\sqrt{5}} [(\frac {1 + \sqrt{5}} {2})^n - (\frac{1-\sqrt{5}} {2})^n]$

所以根据此公式可以直接求第 n 项的值：

```java
public static int climbStairs4(int n) {
    double sqrt5 = Math.sqrt(5);
    return (int) ((Math.pow((1+sqrt5)/2, n+1) - Math.pow((1-sqrt5)/2, n+1)) / sqrt5);
}
```

