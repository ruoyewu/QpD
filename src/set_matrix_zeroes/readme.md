### Question

Given a *m* x *n* matrix, if an element is 0, set its entire row and column to 0. Do it [**in-place**](https://en.wikipedia.org/wiki/In-place_algorithm).

**Example 1:**

```
Input: 
[
  [1,1,1],
  [1,0,1],
  [1,1,1]
]
Output: 
[
  [1,0,1],
  [0,0,0],
  [1,0,1]
]
```

**Example 2:**

```
Input: 
[
  [0,1,2,0],
  [3,4,5,2],
  [1,3,1,5]
]
Output: 
[
  [0,0,0,0],
  [0,4,5,0],
  [0,3,1,0]
]
```

**Follow up:**

-   A straight forward solution using O(*m**n*) space is probably a bad idea.
-   A simple improvement uses O(*m* + *n*) space, but still not the best solution.
-   Could you devise a constant space solution?

### Solution

题目中有要求空间复杂度，没有说明时间复杂度，不过就题目来看，时间复杂度也就是在$O(mn)$，本题对空间复杂度的要求比较苛刻，要求达到$O(1)$，那么首先分析题目，找到解题至少需要的一些数据，然后再分析这些数据应该如何存储，就是这个题目的解题关键。

首先，根据题目，每一个 0 所在的行和列，都需要置为 0 ，也就是说，对于一个 m*n 的数组而言，至少也需要 m+n 个额外空间保存对应的行或列是否需要置 0 ，所以常规来说，其空间复杂度为$O(m+n)$，但是这还不够，如果要将时间复杂度变为$O(1)$，还需要存放这些数据，唯一的办法就是，在这个二维数组中找到合适的位置存放这些数据，并且不影响结果。

那么应该存放在哪里？可以先假设，使每一行或者每一列的第一个位置，保存当前行/列是否需要置 0 ，那么就可以将行/列的第一位置 0 来表示这一行/列需要置 0 ，但是有一个问题，第 1 列和第 1 行，它们的第行第一和列第一是同一个数字，这就导致在这里使用的时候会导致分歧，不能辨别这代表着当前行还是当前列需要置 0 ，这也可以解决，只使用这个数字标识当前行，而对于第一列是否需要置 0 ，单独使用一个变量标识即可。

然后遍历数组，按照如上规则将需要置 0 的行列标识出来，完了之后就可以将需要置 0 的置 0 。在置 0 第 1 行和第 1 列的时候还要加一步判断，因为如果将第 1 行置 0 了之后再去置 0 列，就会发现每一列都需要置 0 ，这就错了。所以第一行和第一列的置 0 需要最后执行，代码如下：

```java
public static void setZeros2(int[][] matrix) {
    int m = matrix.length, n = matrix[0].length;
    boolean firstCol = false;
    for (int i = 0; i < m; i++) {
        if (matrix[i][0] == 0) firstCol = true;
        for (int j = 1; j < n; j++) {
            if (matrix[i][j] == 0) {
                matrix[i][0] = 0;
                matrix[0][j] = 0;
            }
        }
    }
    // fill col
    for (int i = 1; i < n; i++) {
        if (matrix[0][i] == 0) {
            for (int j = 0; j < m; j++) {
                matrix[j][i] = 0;
            }
        }
    }
    // fill row
    for (int[] row : matrix) {
        if (row[0] == 0) {
            Arrays.fill(row, 0);
        }
    }
    // fill first col
    if (firstCol) {
        for (int i = 0; i < m; i++) {
            matrix[i][0] = 0;
        }
    }
}
```

上面先置 0 所有的列（除第一列），再置 0 所有行，最后置 0 第一列，这就可以保证在置 0 的时候，标识值没有被“置 0 ”这个过程污染。