### Question

Given a 2d grid map of `'1'`s (land) and `'0'`s (water), count the number of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

**Example 1:**

```
Input:
11110
11010
11000
00000

Output: 1
```

**Example 2:**

```
Input:
11000
11000
00100
00011

Output: 3
```

### Solution

这样一个二维数组可以看作是一个图，然后`'1'`作为图中的点，那么这个问题就是求这样一个图中的连通分量数，每一个连通分量都可以从其中的任意一点通过遍历，到达连通分量中的每个点。然后再去找下一个点，当所有的点都访问到了之后，这是访问过的连通分量数就是这里的小岛的数量。所以有：

```
public static int numIslands(char[][] grid) {
    if (grid.length == 0 || grid[0].length == 0) return 0;
    int cnt = 0;
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == '1') {
                cnt++;
                expand2(grid, i, j);
            }
        }
    }
    return cnt;
}
private static void expand2(char[][] grid, int i, int j) {
    if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] != '1') {
        return;
    }
    grid[i][j] = 0;
    expand2(grid, i+1, j);
    expand2(grid, i-1, j);
    expand2(grid, i, j-1);
    expand2(grid, i, j+1);
}
```

用递归函数对连通分量进行遍历，并将所有访问过的点标记，以防止二次访问。关于时间复杂度，这种算法的时间复杂度为$O(mn + i)$，其中 i 为`'1'`的数量。