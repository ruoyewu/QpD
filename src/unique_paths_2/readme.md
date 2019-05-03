### Question

A robot is located at the top-left corner of a *m* x *n* grid (marked 'Start' in the diagram below).

The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).

Now consider if some obstacles are added to the grids. How many unique paths would there be?

![img](https://assets.leetcode.com/uploads/2018/10/22/robot_maze.png)

An obstacle and empty space is marked as `1` and `0` respectively in the grid.

**Note:** *m* and *n* will be at most 100.

**Example 1:**

```
Input:
[
  [0,0,0],
  [0,1,0],
  [0,0,0]
]
Output: 2
Explanation:
There is one obstacle in the middle of the 3x3 grid above.
There are two ways to reach the bottom-right corner:
1. Right -> Right -> Down -> Down
2. Down -> Down -> Right -> Right
```

### Solution

这一题是 unique paths 一题的衍生题，增加了障碍机制，有障碍所在的空格不能经过，这就拒绝了在 unique paths 一题中使用数学公式解题的一种方法，只能使用其他的两种方法，即递归和动态规划，代码分别如下：

**递归**

```java
public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
    int m = obstacleGrid.length, n = obstacleGrid[0].length;
    int[][] saved = new int[m][n];
    for (int[] row : saved) {
        Arrays.fill(row, -1);
    }
    return paths(saved, obstacleGrid, 0, 0);
}
private static int paths(int[][] saved, int[][] grid, int i, int j) {
    if (i >= grid.length || j >= grid[0].length || grid[i][j] == 1) return 0;
    if (i == grid.length-1 && j == grid[0].length -1) return 1;
    if (saved[i][j] >= 0) return saved[i][j];
    int path = paths(saved, grid, i+1, j) + paths(saved, grid, i, j+1);
    saved[i][j] = path;
    return path;
}
```



**动态规划**

```java
public static int uniquePathsWithObstacles2(int[][] obstacleGrid) {
    int m = obstacleGrid.length, n = obstacleGrid[0].length;
    int[][] saved = new int[m][n];
    if (obstacleGrid[m-1][n-1] == 1) return 0;
    saved[m-1][n-1] = 1;
    for (int i = m-1; i >= 0; i--) {
        for (int j = n-1; j >= 0; j--) {
            if (obstacleGrid[i][j] == 1)
                saved[i][j] = 0;
            else {
                if (i < m-1) saved[i][j] += saved[i+1][j];
                if (j < n-1) saved[i][j] += saved[i][j+1];
            }
        }
    }
    return saved[0][0];
}
```

基本解法与 unique paths 一题无异。