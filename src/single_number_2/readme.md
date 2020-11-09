### Question

Given a **non-empty** array of integers, every element appears *three* times except for one, which appears exactly once. Find that single one.

**Note:**

Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?

**Example 1:**

```
Input: [2,2,3,2]
Output: 3
```

**Example 2:**

```
Input: [0,1,0,1,0,1,99]
Output: 99
```

### Solution

#### 暴力计算

第一次遍历，保存每一个数字出现的次数；第二次遍历，找到只出现一次的数字：

```java
public int singleNumber(int[] nums) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int num : nums) {
        map.put(num, map.getOrDefault(num, 0) + 1);
    }
    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
        if (entry.getValue() == 1) {
            return entry.getKey();
        }
    }
    return -1;
}
```

### 位扩充

主要思想是将一维数组，转变成二维数组，然后在数组的每一列中，利用公式进行消除，得到属于结果的那一个数字，最后将一整行连接起来，形成最终结果。

将数字扩充之后再进行查找应该是一种比较常规的方法，而常规的扩充方式就是按进制拆分，17 按十进制拆分是 1 7，按二进制拆分就是 1 0 0 0 1，那么一个数组比如 [2, 2, 2, 3] 拆分之后就是 [[0010], [0010], [0011]]，那么纵向来看，就可以发现对应的规律，即对于任意一组数的任意一位，只有 0 1 两种情况，那么针对题目所设条件，如果总的有 n 个数，其中 q 个数字为 1，p 个数字为 0，另一个数字为 x (x = 0/1，且 x 对应的这一行就是待求解) 使得 n = q + p + 1，那么这一列的和为 3q + 0p + x = 3q + x，那么我们可以很轻松就计算出 x 的值，当我们把每一列的 x 都求出来之后，组合在一起就是解：

```java
public int singleNumber2(int[] nums) {
    int answer = 0;
    for (int i = 0; i < 32; i++) {
        int count = 0;
        for (int n : nums) {
            count += (n >> i) & 1;
        }
        answer |= (count % 3) << i;
    }
    return answer;
}
```

