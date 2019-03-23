### Question

Write a program to solve a Sudoku puzzle by filling the empty cells.

A sudoku solution must satisfy **all of the following rules**:

1.  Each of the digits `1-9` must occur exactly once in each row.
2.  Each of the digits `1-9` must occur exactly once in each column.
3.  Each of the the digits `1-9` must occur exactly once in each of the 9 `3x3` sub-boxes of the grid.

Empty cells are indicated by the character `'.'`.

![img](https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Sudoku-by-L2G-20050714.svg/250px-Sudoku-by-L2G-20050714.svg.png)
A sudoku puzzle...

![img](https://upload.wikimedia.org/wikipedia/commons/thumb/3/31/Sudoku-by-L2G-20050714_solution.svg/250px-Sudoku-by-L2G-20050714_solution.svg.png)
...and its solution numbers marked in red.

**Note:**

-   The given board contain only digits `1-9` and the character `'.'`.
-   You may assume that the given Sudoku puzzle will have a single unique solution.
-   The given board size is always `9x9`.

### Solution

求解一个数独，最常见的一种思想就是，对于一个二维数组，先找到一个空位置，然后填入一个可能值，再判断这个填入一个数字之后的数组是否有解，如果有解，则继续判断下一个空位置，如果无解，则回溯到上一个位置，选择另一个可能值，这是一种典型的递归思想，深度优先遍历解空间树。

如下：

```java
public static void solveSudoku(char[][] board) {
    solve(board, 0, 0);
}
private static boolean solve(char[][] board, int i, int j) {
    if (j == 9 && ++i == 9) return true;
    if (j == 9) j = 0;
    if (board[i][j] != '.') {
        return solve(board, i, j+1);
    }
    for (int k = '1'; k <= '9'; k++) {
        board[i][j] = (char) k;
        if (isValidSudoku(board, i, j) && solve(board, i, j+1)) {
            return true;
        }
        board[i][j] = '.';
    }
    return false;
}
private static boolean isValidSudoku(char[][] board, int i, int j) {
    char c = board[i][j];
    for (int k = 0; k < 9; k++) {
        if (i != k && board[k][j] == c) return false;
        if (j != k && board[i][k] == c) return false;
    }
    int row = i / 3 * 3, col = j / 3 * 3;
    for (int k = row; k < 3+row; k++) {
        for (int l = col; l < 3+col; l++) {
            if (i != k && j != l && board[k][l] == c) return false;
        }
    }
    return true;
}
```

递归函数为`solve()`，先在其中找到一个空位置，然后一次将 '1' 到 '9' 填入这个位置，先通过`isValidSudoku()`判断是否可以填入，再通过`solve()`判断此时的二维数组是否有解。

在这种解法中，每次填入一个数字的时候，都需要遍历它的行、列及周围判断是否可以填入。这是否是必须的遍历？是否可以采取某种方式去掉这种判断？首先，`isValidSudoku()`方法的目的，就是为了判断某一个位置对应的行、列及周围是否已经有要填入的数字，所以需要遍历，如果要将这个过程变成无需遍历的，那就需要能够直接判断待填入的数字是否已经填入过，联想到数字只能是 1-9 中的一个，那么如果可以定义一个长度为 10 的布尔数组，保存某一行对应的数字是否出现过，便可以完成无需遍历即可判断某个数字是否已经填入。

而且，在判断的过程中数字是一个一个填入的，也就是说，整个数组的状态是渐变的，这也为上面那种想法提供了条件。首先假设一种方法：问题是基于一个 9x9 的二维数组的，也就是说，有 9 行、9 列、9 个方格，那么对于每一行，定义一个长度为 10 数组，每个位置对应着当前行这个数字是否填入过，那么在之后的判断中，如果要判断某一行是否出现过 5 ，便可直接判断`row[5]`是否为 false ，然后当填入数字 5 的时候，直接将`row[5]`赋值为 true 即可。

代码如下：

```java
public static void solveSudoku(char[][] board) {
    int len = 9;
    boolean[][] row = new boolean[len][len],
            col = new boolean[len][len],
            box = new boolean[len][len];
    for (int i = 0; i < len; i++) {
        for (int j = 0; j < len; j++) {
            char c;
            if ((c = board[i][j]) != '.') {
                int num = c-'1';
                row[i][num] = col[j][num] = box[i/3*3+j/3][num] = true;
            }
        }
    }
    solve(board, row, col, box, 0, 0);
}
private static boolean solve(char[][] board, boolean[][] row,
                          boolean[][] col, boolean[][] box, int i, int j) {
    if (j == 9 && ++i == 9) return true;
    if (j == 9) j = 0;
    if (board[i][j] != '.')
        return solve(board, row, col, box, i, j+1);
    for (int k = 0; k < 9; k++) {
        if (!row[i][k] && !col[j][k] && !box[i/3*3+j/3][k]) {
            board[i][j] = (char) (k + '1');
            row[i][k] = col[j][k] = box[i/3*3+j/3][k] = true;
            if (solve(board, row, col, box, i, j+1)) {
                return true;
            }
            board[i][j] = '.';
            row[i][k] = col[j][k] = box[i/3*3+j/3][k] = false;
        }
    }
    return false;
}
```

上述代码首先遍历二维数组，将所有被填入的数字都记录到 row col 和 box 这三个数组中，然后在递归函数`solve()`中，通过判断 row col box 中对应位置的值判断是否可以填入。