### Question

Given a **non-empty** array containing **only positive integers**, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.

**Note:**

1.  Each of the array element will not exceed 100.
2.  The array size will not exceed 200.

**Example 1:**

```
Input: [1, 5, 11, 5]

Output: true

Explanation: The array can be partitioned as [1, 5, 5] and [11].
```

**Example 2:**

```
Input: [1, 2, 3, 5]

Output: false

Explanation: The array cannot be partitioned into equal sum subsets.
```

### Solution

将一个数组分成两份，使得两份的数字和相等，换言之，我们只需要判断存在一个子数组，其和为整个数组的一半即可。那么对于一个数组`[0,...,n-1]`，可以先求出整个数组的和，除以 2 得到子数组的和，使其为 sum 。

如果数组`[0,...,n-1]`中最大的一个数字大于 sum ，那么必然不存在两个字数组使其和为 sum ，因为这个最大的数不可分。

所以，

将数组`[0,...,n-1]`分成两部分，则必然位置 n-1 所在的数字属于其中一部分，假设我们是要求 n-1 所在的一部分。那么必然要在剩余的子数组`[0,...,n-2]`中找到这个子数组剩余的部分。然后在数组`[0,...,n-2]`中找到一个与 n-1 可能同属一组的数字。即如果`num[n-1] + num[i] < sum`，那么位置 i 处的数字就有可能选择。