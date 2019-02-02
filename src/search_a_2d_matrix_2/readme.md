### Question

Write an efficient algorithm that searches for a value in an *m* x *n* matrix. This matrix has the following properties:

-   Integers in each row are sorted in ascending from left to right.
-   Integers in each column are sorted in ascending from top to bottom.

**Example:**

Consider the following matrix:

```
[
  [1,   4,  7, 11, 15],
  [2,   5,  8, 12, 19],
  [3,   6,  9, 16, 22],
  [10, 13, 14, 17, 24],
  [18, 21, 23, 26, 30]
]
```

Given target = `5`, return `true`.

Given target = `20`, return `false`.

### Solution

#### S1:折半查找

横竖都是有序数组，折半查找是一个不错的方法。对于题目所给例子而言，要查找 5 ，可以先利用折半查找找到小于 5 的最大列号，即第二列 4 所在的那一列。因为每一列是向下递增的，所以在第一行中，大于 5 的那些列`[7,11,15]`必然不存在 5 ，只需要在前两列再利用折半查找。

如下：

```java
public static boolean searchMatrix(int[][] matrix, int target) {
    if (matrix.length == 0 || matrix[0].length == 0) return false;
    int c = search(matrix[0], target);
    for (int j = c; j >= 0; j++) {
        // search i
        int start = 0, end = matrix.length-1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (matrix[mid][j] < target) {
                start = mid+1;
            } else if (matrix[mid][j] > target) {
                end = mid-1;
            } else {
                return true;
            }
        }
    }
    return false;
}
private static int search(int[] nums, int target) {
    int start = 0, end = nums.length-1;
    while (start <= end) {
        int mid = (start + end) / 2;
        if (nums[mid] < target) {
            start = mid+1;
        } else if (nums[mid] > target) {
            end = mid-1;
        } else {
            return mid;
        }
    }
    return end;
}
```

#### S2:分支选择

如果将这个二维数组换个角度，如从左下角看，它是一个向右递增、向上递减的数组。那么从这个位置出发，大于 target 的时候向上，小于 target 的时候向右，如果 target 存在，就一定能够找到 target 。

如何能说明这样确实是对的？首先，如果从左下角出发，就只有向上和向右两种方向。假如当前位置的数字大于 target ，因为向右递增，那么本行剩余的值必然都大于 target ，可以省略判断；假如小于 target ，因为向上递减，本列剩余值也都小于 target ，也可省略。

如下：

```java
public static boolean searchMatrix(int[][] matrix, int target) {
    if (matrix.length == 0 || matrix[0].length == 0) return false;
    int i = matrix.length-1, j = 0;
    while (i >= 0 && j < matrix[0].length) {
        if (matrix[i][j] > target) {
            i--;
        } else if (matrix[i][j] < target) {
            j++;
        } else {
            return true;
        }
    }
    return false;
}
```

