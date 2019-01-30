### Question

Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.

**Example:**

```
Input: 

1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0

Output: 4
```

### Solution

本题与 maximal rectangle 一题类似，不过比那一题简单，直接使用动态规划即可。

```java
public static int maximalSquare2(char[][] matrix) {
    if (matrix.length == 0 || matrix[0].length == 0) return 0;
    int m = matrix.length, n = matrix[0].length;
    int[][] saved = new int[m][n];
    int maximal = 0;
    for (int i = 0; i < m; i++) {
        if (matrix[i][0] == '1') {
            saved[i][0] = 1;
            if (maximal == 0) {
                maximal = 1;
            }
        }
    }
    for (int i = 0; i < n; i++) {
        if (matrix[0][i] == '1') {
            saved[0][i] = 1;
            if (maximal == 0) {
                maximal = 1;
            }
        }
    }
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            if (matrix[i][j] == '1') {
                int pre = saved[i-1][j-1];
                //left check
                int left = 0, up = 0;
                while (left < pre && matrix[i][j-1-left] == '1') left++;
                //up check
                while (up < pre && matrix[i-1-up][j] == '1') up++;
                int max = 1 + (left > up ? up : left);
                saved[i][j] = max;
                maximal = Math.max(maximal, max);
            }
        }
    }
    return maximal*maximal;
}
```

在这种方法的基础上，还可以衍生出递归（倒着求），或者是精简空间复杂度（可以看出求第 i 行的结果时只需要知道第 i-1 行的结果）。