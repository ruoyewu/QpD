### Question

Given an array with *n* objects colored red, white or blue, sort them **in-place** so that objects of the same color are adjacent, with the colors in the order red, white and blue.

Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.

**Note:** You are not suppose to use the library's sort function for this problem.

**Example:**

```
Input: [2,0,2,1,1,0]
Output: [0,0,1,1,2,2]
```

**Follow up:**

-   A rather straight forward solution is a two-pass algorithm using counting sort.
    First, iterate the array counting number of 0's, 1's, and 2's, then overwrite array with total number of 0's, then 1's and followed by 2's.
-   Could you come up with a one-pass algorithm using only constant space?

### Solution

#### S1:计数法

由于只有三种颜色，那么，只要得到这三种颜色各自的数量，就可以了，于是，我们可以通过一次遍历分别记下每个颜色出现的次数，然后再填满数组：

```java
public static void sortColors(int[] nums) {
    int n = nums.length;
    int red = 0, white = 0, blue = 0;
    for (int num : nums) {
        if (num == 0) {
            red++;
        } else if (num == 1) {
            white++;
        } else {
            blue++;
        }
    }
    Arrays.fill(nums, 0, red, 0);
    Arrays.fill(nums, red, red+white, 1);
    Arrays.fill(nums, red+white, n, 2);
}
```

#### S2:交换法

上述的计数法，需要两个步骤，首先记下各颜色数量，然后再改造数组，除此之外还可以直接对原数组进行操作：进行一次遍历，将所有的 0 都筛选到最左边，将所有的 2 都筛选到最右边，那么剩下的 1 必然是在中间。如下：

```java
public static void sortColors(int[] nums) {
    int left = 0, mid = 0, right = nums.length-1;
    while (mid <= right) {
        if (nums[mid] == 0) {
            nums[mid] = nums[left];
            nums[left++] = 0;
            mid++;
        } else if (nums[mid] == 1) {
            mid++;
        } else if (nums[mid] == 2) {
            nums[mid] = nums[right];
            nums[right--] = 2;
        }
    }
}
```

这里，left 表示当前 0 的个数，right 表示数组的长度与 2 的个数的差值（也就是倒着数的时候 2 的个数），而 mid 指向当前代检测元素的位置。