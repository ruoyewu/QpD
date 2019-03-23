### Question

Determine if a 9x9 Sudoku board is valid. Only the filled cells need to be validated **according to the following rules**:

1.  Each row must contain the digits `1-9` without repetition.
2.  Each column must contain the digits `1-9` without repetition.
3.  Each of the 9 `3x3` sub-boxes of the grid must contain the digits `1-9` without repetition.

![img](https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Sudoku-by-L2G-20050714.svg/250px-Sudoku-by-L2G-20050714.svg.png)
A partially filled sudoku which is valid.

The Sudoku board could be partially filled, where empty cells are filled with the character `'.'`.

**Example 1:**

```
Input:
[
  ["5","3",".",".","7",".",".",".","."],
  ["6",".",".","1","9","5",".",".","."],
  [".","9","8",".",".",".",".","6","."],
  ["8",".",".",".","6",".",".",".","3"],
  ["4",".",".","8",".","3",".",".","1"],
  ["7",".",".",".","2",".",".",".","6"],
  [".","6",".",".",".",".","2","8","."],
  [".",".",".","4","1","9",".",".","5"],
  [".",".",".",".","8",".",".","7","9"]
]
Output: true
```

**Example 2:**

```
Input:
[
  ["8","3",".",".","7",".",".",".","."],
  ["6",".",".","1","9","5",".",".","."],
  [".","9","8",".",".",".",".","6","."],
  ["8",".",".",".","6",".",".",".","3"],
  ["4",".",".","8",".","3",".",".","1"],
  ["7",".",".",".","2",".",".",".","6"],
  [".","6",".",".",".",".","2","8","."],
  [".",".",".","4","1","9",".",".","5"],
  [".",".",".",".","8",".",".","7","9"]
]
Output: false
Explanation: Same as Example 1, except with the 5 in the top left corner being 
    modified to 8. Since there are two 8's in the top left 3x3 sub-box, it is invalid.
```

**Note:**

-   A Sudoku board (partially filled) could be valid but is not necessarily solvable.
-   Only the filled cells need to be validated according to the mentioned rules.
-   The given board contain only digits `1-9` and the character `'.'`.
-   The given board size is always `9x9`.

### Solution

数独问题，始终是一个 9x9 的二维数组，求对于这样一个数组，是否有解。但是本题本质上并不是求这个数独问题是否有解，而是判断是否满足「给定的条件」。题目中只说，对于所有非空的位置，要满足行、列和当前 3x3 格子中不能有重复，但是保证了上述的条件之后也并不一定能保证数独有解。

所以解本题的话，不能着眼于「数独」是否有解这个问题上，而是就题目中给出的条件求解。每一个非空的位置进行三种条件的判断，都满足了即可：

```java
public static boolean isValidSudoku(char[][] board) {
    int n = board.length;
    for (int row = 0; row < n; row++) {
        for (int col = 0; col < n; col++) {
            char c = board[row][col];
            if (c != '.') {
                // row
                for (int i = 0; i < n; i++) {
                    if (i != row && c == board[i][col]) return false;
                }
                // col
                for (int i = 0; i < n; i++) {
                    if (i != col && c == board[row][i]) return false;
                }
                // box
                int x = row / 3 * 3, y = col / 3 * 3;
                for (int i = x; i < x+3; i++) {
                    for (int j = y; j < y+3; j++) {
                        if (i != row && j != col && board[i][j] == c) return false;
                    }
                }
            }
        }
    }
    return true;
}
```

