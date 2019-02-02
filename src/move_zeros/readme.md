### Question

Given an array `nums`, write a function to move all `0`'s to the end of it while maintaining the relative order of the non-zero elements.

**Example:**

```
Input: [0,1,0,3,12]
Output: [1,3,12,0,0]
```

**Note**:

1.  You must do this **in-place** without making a copy of the array.
2.  Minimize the total number of operations.

### Solution

#### S1:复制数组

构造一个新的数组，将原数组中的非 0 数字依次填入新的数组，并将未填满的补 0 ，便可达到移动的效果。

```java
public static void moveZeros(int[] nums) {
    int[] tmp = new int[nums.length];
    System.arraycopy(nums, 0, tmp, 0, nums.length);
    int pos = 0;
    for (int i = 0; i < nums.length; i++) {
        if (tmp[i] != 0) {
            nums[pos++] = nums[i];
        }
    }
    for (int i = pos; i < nums.length; i++) {
        nums[i] = 0;
    }
}
```

#### S2:双指针

或者也可以使用两个指针，一者指向第一个 0 ，一者指向遍历位置，如遇到非 0 ，将二者交换。

```java
public static void moveZeros(int[] nums) {
    int pos = 0, i = 0;
    while (pos < nums.length && nums[pos] != 0) {
        pos++;
        i++;
    }
    for (; i < nums.length; i++) {
        if (nums[i] != 0) {
            nums[pos++] = nums[i];
            nums[i] = 0;
        }
    }
}
```

