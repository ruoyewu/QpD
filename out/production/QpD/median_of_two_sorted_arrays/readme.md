There are two sorted arrays **nums1** and **nums2** of size m and n respectively.

Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

**Example 1:**

```
nums1 = [1, 3]
nums2 = [2]

The median is 2.0
```

**Example 2:**

```
nums1 = [1, 2]
nums2 = [3, 4]

The median is (2 + 3)/2 = 2.5
```

### Solution

#### 二分法

首先，题目是要求两个有序数组整合在一起的中位数，那么首先看一下对于一个有序数组来说，怎么求中位数，如有：

```
A = [1, 2, 3, 4, 5]
B = [1, 2, 3, 4]
```

这两个有序数组，对于 A 来说，有中位数 3 ，对于 B 来说，有中位数 (2 + 3) / 2 = 2.5 ，这就是中位数的两种情况，偶数个元素和奇数个元素。但是针对这两种情况，总不能分开讨论，要寻求它们的共同点，使得对于这两种情况来说，都能够使用同一种方法解决求中位数的问题。

下面提出一个概念，“划分” ，假设 i 是数组 A 的中位数的划分，那么数组 A 就会以 i 为中介，将自身分散成两个子数组，如一个划分 3 ，将 A 分成数组：

```
A1 = [1, 2, 3]
A2 = [4, 5]
```

这样。

那么在求一个数组中位数的过程中，可求出某个划分，有：
$$
i = 
\begin{cases}
len(A) / 2 & len(A) \% 2 = 0 \\
(len(A) + 1) / 2 & len(A) \% 2 = 1
\end{cases}
$$
那么，对于上述的这个划分来说，就有：
$$
median = \begin{cases}
(A[i] + A[i - 1]) / 2 & len(A) \% 2 = 0 \\
A[i - 1] & len(A) \% 2 = 1
\end{cases}
$$
那么，基于上述的理论，就有下面的结果：

$i = (len(A) + 1) / 2$，因为当 $len(A)$ 为偶数的时候，先加个 1 再除以 2 得到的是同样的结果。所以根据这个 i 可以很轻松的得到单个有序数组的中位数。那么对于两个有序的数组，也可以使用相同的理论：

对于两个有序数组 A 和 B 来说，假设他们都有一个划分 i 和 j ，将 A 和 B 各划分成两个子数组：

```
A1 = {0...i-1}
A2 = {i...}
B1 = {0...j-1}
B2 = {j...}
```

那么肯定存在这样的两个划分 i 和 j ，假设下面的 + 代表两个数组的元素相加且排序，有：

```
C1 = A1 + B1
C2 = A2 + B2
C = A + B
```

使得 C1 和 C2 是能够用于求 C 的中位数的一个划分，那么就可以得到：

```
C1 = {...max{A[i-1], B[j-1]}} // 即 C1 中最大的数字为 max{A[i - 1], B[j - 1]}
C2 = {min{A[i], B[j]}...} // 即 C2 中最小的数字为 min{A[i], B[j]}
```

那么根据上述的 2 式中计算中位数的公式就可以得到：

```
mLeft = max{A[i - 1], B[j - 1]} = max{C1}
mRight = min{A[i], B[j]} = min{C2}

if (len(C) % 2 == 0) {
    median = mLeft
}else {
    median = (mLeft + mRight) / 2
}
```

由此便可以得到求两个有序数组的方法，即找到 A 和 B 的在上述情况下的 i 和 j ，假设 A 和 B 的元素个数分别是 m 和 n ，根据上面的对于单数组求中位数的推导有：

```{
i + j = (m + n + 1) / 2
```

其中 i 和 j 分别是 A 和 B 的划分，那么有 i 和 j 的范围分别是：

```
i = [0, m]
j = [0, n]
```

那么，下面的工作就是找到这两个划分 i 和 j ，而又因为它们的和值是一个确定的，所以只需要设其中一个为变量，另一个则变成了因变量，如有：

```
j = (m + n + 1) / 2 - i
```

但是其中有一点很重要，因为在这个环境中，j 是非负数，所以`(m + n + 1) / 2 - i`不能是负数，而前面的`(m + n + 1) /2`相对于两个固定的数组而言是一个常数，这个值取决于 i 的值，而 i 的范围是`[0, m]`，故当 i 最大时，有`(m + n + 1) / 2 - m = (n - m + 1) / 2 >= 0`，所以有`(n - m) >= -1`，所以，为了满足这个条件，求解的时候需要规定`len(A) <= len(B)`即`m <= n`才行。

那么接下来接可以将 i 的值取在`[0, m]`的范围内看是否满足求中位数的条件，而在 i 和 j 取了固定的值之后，有三种情况：

1.  `A[i - 1] > B[j] && i > 0`

    这种情况即划分后的 A 左部的某个值大于了 B 的右部，说明此时在 A 上的划分太靠后，所以需要将划分前移，即需要将 i 减小，

2.  `B[j - 1] > A[i] && i < m`

3.  其他情况