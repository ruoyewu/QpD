### Question

Given an array of integers where 1 ≤ a[i] ≤ *n* (*n* = size of array), some elements appear twice and others appear once.

Find all the elements of [1, *n*] inclusive that do not appear in this array.

Could you do it without extra space and in O(*n*) runtime? You may assume the returned list does not count as extra space.

**Example:**

```
Input:
[4,3,2,7,8,2,3,1]

Output:
[5,6]
```

### Solution

解本题的基本思想应该是：先对数组进行一次遍历，记录下出现过的数字，然后再次遍历，将未记录的数字加入列表，再返回这个列表即可。

而且题目中给出，对于一个长度为 n 的数组，这个数组里所有元素的取值范围是 1～n ，于是，可以直接定义一个长度为 n 的新数组保存各数字的出现情况：`exit[nums[i] - 1] = true;`，也就是说，使用`exit[i-1]`保存数字 i 是否出现。那么第二次遍历的时候，直接将所有`exit[i-1] = false`的数字 i 加入列表即可。

```java
public static List<Integer> findDisappearedNumbers(int[] nums) {
    List<Integer> result = new ArrayList<>();
    boolean[] exit = new boolean[nums.length];
    for (int num : nums) {
        exit[num-1] = true;
    }
    for (int i = 0; i < exit.length; i++) {
        if (!exit[i]) {
            result.add(i+1);
        }
    }
    return result;
}
```

但是上面这个方法的空间复杂度是`O(n)`，但是题目要求空间复杂度为`O(1)`。也就是说，用来保存这个记录情况的 exit 数组应该省掉，但是这个记录情况又肯定是需要一个数组来保存的，那么就可以考虑，是否可以使用 nums 本身替代 exit 完成记录任务？

首先，它们的长度是一样的，都是 n ，这就意味着 nums 确实有可能替代 exit 。然后，对 exit 的操作是`exit[num-1] = true`，相应的，是否可以使用 nums 执行`nums[num-1]++`操作？这样肯定是不行的，有可能会出现`num-1 > n-1`的情况，也就是数组越界了。一般处理越界使用的是取模运算，如`nums[(num-1) % n]++`，这样可以吗？还是不行，如一个数组`[2,2,3]`，在执行完第一个数字的时候，数组变成了`[2,3,3]`，改变了原来数组的特点，这必然是不行的。然后，考虑到对 nums 操作的时候对 num 的取模操作，如果执行`nums[(num-1) % n] += n`会怎么样？届时`[2,2,3]`执行完第一个数字的时候变成了`[2,5,3]`，注意，对于第二个数字来说，虽然增加了 n ，但是由于每次取用数字的时候都要执行一个对 n 取模操作，所以 i 与 i+n 的结果是一样的。也就是说，上面那个操作不会修改原本数组的特点。

于是，经过一次遍历之后，会将所有出现过的数字对应的位置的数值进行 +n 处理，又因为数组中初始的每个数字的范围是 1～n ，于是得到，所有出现过的数字经过这一变换之后，对应位置的数值都会变成 num+n ，也就是大于 n ，而没有出现过的数字对应位置的数值还是 1～n ，于是便可以轻松判断哪些数字出现了，哪些未在数组中出现过。

```java
public static List<Integer> findDisappearedNumbers(int[] nums) {
    List<Integer> result = new ArrayList<>();
    int n = nums.length;
    for (int num : nums) {
        nums[(num-1) % n] += n;
    }
    for (int i = 0; i < n; i++) {
        if (nums[i] < n) {
            result.add(i+1);
        }
    }
    return result;
}
```

更重要的是，这些变换是在同一个数组中完成的，不需要额外的空间保存记录值。此时的算法，时间复杂度为`O(1)`。