### Question

Given an integer array `nums`, find the contiguous subarray within an array (containing at least one number) which has the largest product.

**Example 1:**

```
Input: [2,3,-2,4]
Output: 6
Explanation: [2,3] has the largest product 6.
```

**Example 2:**

```
Input: [-2,0,-1]
Output: 0
Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
```

### Solution

#### S1:二重遍历

在一个数组中求得一个子数组，使得其内的所有元素的乘积最大。那么，一个二重遍历找到所有的子数组，然后计算其乘积，比较求最大即可。

```java
public static int maxProduct(int[] nums) {
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < nums.length; i++) {
        int product = 1;
        for (int j = i; j < nums.length; j++) {
            product *= nums[j];
            max = Math.max(max, product);
        }
    }
    return max;
}
```

#### S2:动态规划

动态规划的思想是根据之前遍历得到的结果来求解当前位置的题，如果求以某一位置 i 结尾的子数组中的最大乘积，保存在`max[i]`中，那么有以下几种情况：

1.  nums[i] > 0
    1.  max[i-1] > 0，因为两个正数相乘仍是两个正数，且这是一个正整数，则必有 max[i-1] * nums[i] > max[i-1]，那么此时最大乘积必然是 max[i] = max[i-1] \* nums[i-1]
    2.  max[i-1] < 0，如果某一位置结尾的数组的最大乘积是一个负数，意味着这个位置之前不存在负数，使得它与之相乘变成正数，也就是说从 0 至 i 位置只有一个负数，就是 nums[i-1] ，那么当前位置的最大乘积就是 nums[i]
    3.  max[i-1] = 0，如果数组中包含 0 ，那么乘积一定是 0 ，所以此时的最大乘积也是 nums[i]
2.  nums[i] < 0
    1.  如果当前位置是个负数，那么如果在这个位置之前存在负数的话，可以一直往前乘，直到乘到这个负数为止，此时因为有两个负数，那么这个乘积是一个正数，这还不够，还要判断在这个负数之前的一个位置的最大乘积是否大于 0 ，如果大于 0 还应该继续往前乘。如果这个位置之前不存在负数，那么这是第一个负数，此时的最大乘积就是它自己。
3.  nums[i] = 0，任何数与 0 相乘都是 0 ，所以结果也是 0 。

如下：

```java
public static int maxProduct(int[] nums) {
    if (nums.length == 0) return 0;
    int[] max = new int[nums.length];
    max[0] = nums[0];
    int maxProduct = nums[0];
    int lastNeg = nums[0] < 0 ? 0 : -1;
    for (int i = 1; i < nums.length; i++) {
        int num = nums[i];
        if (num > 0) {
            max[i] = num;
            if (max[i-1] > 0) {
                max[i] *= max[i-1];
            }
        } else if (num < 0) {
            max[i] = num;
            if (lastNeg >= 0) {
                int j = i-1;
                while (j >= lastNeg) {
                    max[i] *= nums[j--];
                }
                if (lastNeg-1 >= 0 && max[lastNeg-1] > 0) {
                    max[i] *= max[lastNeg-1];
                }
            }
            lastNeg = i;
        } else {
            max[i] = 0;
        }
        maxProduct = Math.max(maxProduct, max[i]);
    }
    return maxProduct;
}
```

或者换一种思路，以某一位置 i 结尾的子数组的乘积值，还可以这样求：当前位置的最大乘积，一定是它的前一个位置的最大乘积乘以这个数、前一个位置的最小乘积乘以这个数、这个数本身之中最大的一个。所以只需要知道前一位置的最大、最小乘积即可，而最小乘积一定是前面三个数之中最小的一个。所以：

```java
public static int maxProduct(int[] nums) {
    if (nums.length == 0) return 0;
    int max = nums[0], min = nums[0], result = nums[0];
    for (int i = 1; i < nums.length; i++) {
        int tmpMax = max;
        max = Math.max(max * nums[i], Math.max(min * nums[i], nums[i]));
        min = Math.min(tmpMax * nums[i], Math.min(min * nums[i], nums[i]));
        if (max > result) {
            result = max;
        }
    }
    return result;
}
```

#### S3:双向扫描

如果一个数组中包含 0 ，那么这个数组的乘积一定是 0 ，所以我们着重求解的其实是不包含 0 的一部分数组，那么，如果某个数组中有 0 ，我们只需要求 0 左右两部分的数组的乘积，即，0 将整个数组分成两个小数组，而这两个数组中乘积以及 0 种，较大的那个数则是整个数组的解。

如果一个数组中不包含 0 ，即只有正数和负数。那么负数的个数为奇数或偶数，当为偶数个的时候，那整个数组的乘积一定是最大值。当为奇数个的时候，只需要少乘一个负数，就能得到最大的正数值了。那么应该少乘哪个负数能够使乘积最大，应该是最左端的负数或者是最右端的负数。那么本题的求解就容易了。

首先，以数组中的 0 为分界点，分割成若干个不含 0 的数组，然后，分别从左向右和从右向左累乘，直到乘完整个数组（负数为偶数个）或者乘到最后一个负数为止（负数为奇数个），去这些值中的最大值，并且还要与 0 比较一下，取最大，即为解。

```java
public static int maxProduct(int[] nums) {
    int cur = 1, max = nums[0];
    for (int i = 0; i < nums.length; i++) {
        cur *= nums[i];
        if (cur > max) {
            max = cur;
        }
        if (nums[i] == 0) {
            cur = 1;
        }
    }
    cur = 1;
    for (int i= nums.length-1; i >= 0; i--) {
        cur *= nums[i];
        if (cur > max) {
            max = cur;
        }
        if (nums[i] == 0) {
            cur = 1;
        }
    }
    return max;
}
```

