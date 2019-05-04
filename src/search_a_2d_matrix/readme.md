### Question

Write an efficient algorithm that searches for a value in an *m* x *n* matrix. This matrix has the following properties:

-   Integers in each row are sorted from left to right.
-   The first integer of each row is greater than the last integer of the previous row.

**Example 1:**

```
Input:
matrix = [
  [1,   3,  5,  7],
  [10, 11, 16, 20],
  [23, 30, 34, 50]
]
target = 3
Output: true
```

**Example 2:**

```
Input:
matrix = [
  [1,   3,  5,  7],
  [10, 11, 16, 20],
  [23, 30, 34, 50]
]
target = 13
Output: false
```

### Solution

很简单的一个题目，每一行都是增序，每一列都比上一列大，所以可以先求出 target 可能在的行号，然后再遍历行求是否存在。代码如下：

```java
public static boolean searchMatrix(int[][] matrix, int target) {
    int m, n;
    if (matrix == null || (m = matrix.length) == 0 || (n = matrix[0].length) == 0)
        return false;
    // find row
    int row = -1;
    for (int i = 0; i < m; i++) {
        if (matrix[i][0] <= target) {
            row = i;
        } else {
            break;
        }
    }
    // find col
    if (row != -1) {
        for (int i = 0; i < n; i++) {
            if (matrix[row][i] == target) {
                return true;
            }
        }
    }
    return false;
}
```

时间复杂度为$O(m+n)$。