### Question

Given a *m* x *n* grid filled with non-negative numbers, find a path from top left to bottom right which *minimizes* the sum of all numbers along its path.

**Note:** You can only move either down or right at any point in time.

**Example:**

```
Input:
[
  [1,3,1],
  [1,5,1],
  [4,2,1]
]
Output: 7
Explanation: Because the path 1→3→1→1→1 minimizes the sum.
```

### Solution

类似于之前 unique_paths 一题的解，本题也是求路径的问题，不过不是求路径数，而是求最短路径，不过容易想到，要求解总的路径数量，自然要求所有的路径，而本题，要求最短路径，自然也要先求出所有的路径，比较之后才有这最短路径，所以两题的解法基本类似，本题也有两种解法。

#### S1:递归法

要求某一点`[i,j]`到`[m,n]`的最短路径，且只能下移或者右移，所以这个解也就等于`[i,j]->[i+1,j]->[m,n]`和`[i,j]->[i,j+1]->[m,n]`中较小的那一个，由此便可以得到一个递归关系式，代码如下：

```java
public static int minPathSum(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int[][] saved = new int[m][n];
    for (int i = 0; i < m; i++) {
        Arrays.fill(saved[i], -1);
    }
    saved[m-1][n-1] = grid[m-1][n-1];
    return min(grid, 0, 0, saved);
}
public static int min(int[][] grid, int i, int j, int[][] saved) {
    int m = grid.length, n = grid[0].length;
    if (saved[i][j] >= 0) {
        return saved[i][j];
    }
    int result;
    if (i == m-1) {
        result = min(grid, i, j+1, saved) + grid[i][j];
    } else if (j == n-1) {
        result = min(grid, i+1, j, saved) + grid[i][j];
    } else {
        result = Math.min(min(grid, i+1, j, saved), min(grid, i, j+1, saved)) + grid[i][j];
    }
    saved[i][j] = result;
    return result;
}
```

同样的，我们使用了一个 saved 数组以保存已经求解过的位置，免去重复计算。这个方法的时间复杂度为$O(mn)$，空间复杂度也为$O(mn)$。

#### S2:动态规划法

在这个二维数组中，对于任意一点`[i,j]`，必然有从`[1,1]`到它的最短路径，而这个最短路径，必然有如下的关系：从`[1,1]`到`[i,j]`的最短路径为`[1,1]->[i-1,j]->[i,j]`和`[1,1]->[i,j-1]->[i,j]`中较短的那一个，由此可以一直递推到`[1,1]->[m,n]`的最短路径，即为解。那么有如下方法：

```java
public static int minPathSum(int[][] grid) {
    int m = grid.length;
    int n = grid[0].length;
    int[] result = new int[n];
    result[0] = grid[0][0];
    for (int i = 1; i < n; i++) {
        result[i] = result[i-1] + grid[0][i];
    }
    for (int i = 1; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (j == 0) {
                result[j] = result[j] + grid[i][j];
            } else {
                result[j] = Math.min(result[j], result[j-1]) + grid[i][j];
            }
        }
    }
    return result[n-1];
}
```

可以使用一个数组保存从`[1,1]`到任意一点的最短路径，经过一个二重遍历之后，便求得了从`[1,1]`到每一点的最短路径，自然到`[m,n]`的最短路径也在其中。同时，因为从`[1,1]`到任意一点`[i,j]`的最短路径只与从`[1,1]`到`[i-1,j]`和`[i,j-1]`有关，从数组的角度，也就是只与当前位置之前的数有关，那么我们就可以将这个用于保存最短路径的额外的数组去掉，直接使用原始的数组保存最短路径也不会有影响，如：

```java
public static int minPathSum(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    for (int i = 1; i < n; i++) {
        grid[0][i] = grid[0][i] + grid[0][i-1];
    }
    for (int i = 1; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (j == 0) {
                grid[i][0] = grid[i][0] + grid[i-1][0];
            } else {
                grid[i][j] = grid[i][j] + Math.min(grid[i-1][j], grid[i][j-1]);
            }
        }
    }
    return grid[m-1][n-1];
}
```

这时空间复杂度降为$O(1)$。