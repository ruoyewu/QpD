### Question

Given an array `nums` of *n* integers where *n* > 1,  return an array `output` such that `output[i]` is equal to the product of all the elements of `nums` except `nums[i]`.

**Example:**

```
Input:  [1,2,3,4]
Output: [24,12,8,6]
```

**Note:** Please solve it **without division** and in O(*n*).

**Follow up:**
Could you solve it with constant space complexity? (The output array **does not** count as extra space for the purpose of space complexity analysis.)

### Solution

#### S1:全乘法

要求其他所有数字的乘积，一种方法是将所有数字的乘积先求出来，然后再除以当前数字即可，这个过程中要着重注意 0 存在的情况（因为除以 0 会报错）：

```java
public static int[] productExceptSelf(int[] nums) {
    int[] result = new int[nums.length];
    int mul = 1;
    int zero = -1;
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] != 0) {
            mul *= nums[i];
        } else {
            if (zero >= 0) return result;
            zero = i;
        }
    }
    if (zero >= 0) {
        result[zero] = mul;
        return result;
    }
    for (int i = 0; i < nums.length; i++) {
        result[i] = mul / nums[i];
    }
    return result;
}
```

#### S2:连乘法

如果不能使用除法，如何在$O(n)$的时间内求出解？对于位置 i 处的解，是`[0...i-1]`与`[i+1...n]`的乘积，对于 i+1 处的解，是`[0...i]`和`[i+2...n]`的乘积，那么如果将其分开来看，位置 i 左边的乘积是`[0...i-1]`，位置 i+1 左边的乘积是`[0...i]`，那么可以看出，i+1 位置的解可以由 i 位置的解求算，那么一次遍历就可以得到一半的解，右边同理，两次遍历就可以得到完整的解。

```java
public static int[] productExceptSelf(int[] nums) {
    int[] result = new int[nums.length];
    int mul = 1;
    for (int i = 0; i < nums.length; i++) {
        result[i] = mul;
        mul *= nums[i];
    }
    mul = 1;
    for (int i = nums.length-1; i >= 0; i--) {
        result[i] *= mul;
        mul *= nums[i];
    }
    return result;
}
```

