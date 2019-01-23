### Question

Given a 2D board and a word, find if the word exists in the grid.

The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once.

**Example:**

```
board =
[
  ['A','B','C','E'],
  ['S','F','C','S'],
  ['A','D','E','E']
]

Given word = "ABCCED", return true.
Given word = "SEE", return true.
Given word = "ABCB", return false.
```

### Solution

对于一个给定的二维数组，从其中的某一个点开始，经过一段位移，经过的路径组成的字符串如果与给定的字符串相同，则有解，否则无解。其中，路径不能产生回路（即路径中不存在重复的点）。那么对于这个二维数组中的每一点，我们都要判断从这个点开始是否有某条路径能够组成待求解字符串。所以，如果二维数组中的某点`[i,j]`处的字符等于当前待求解字符串的第一个，那么，这个问题就可以化简为，从这一点的相邻位置（`[i-1,j],[i+1,j],[i,j-1],[i,j+1]`）开始，是否经过一定的位移能够组成原字符串移除第一位后构成的新字符串。并且，之后的路径不能再经过点`[i,j]`。所以这样一个问题，可以使用递归法求解。如下：

```java
public static boolean exist(char[][] board, String word) {
    int m = board.length, n = board[0].length;
    char[] wordC = word.toCharArray();
    boolean[][] visited = new boolean[m][n];
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (board[i][j] == wordC[0]) {
                visited[i][j] = true;
                if (exi2(board, wordC, i, j, 1, visited)) {
                    return true;
                }
                visited[i][j] = false;
            }
        }
    }
    return false;
}
public static boolean exi2(char[][] board, char[] word, int i, int j, int pos, boolean[][] visited) {
    if (pos == word.length) {
        return true;
    } else {
        int direct = 0;
        while (direct++ < 4) {
            int ii, jj;
            if (direct == 1) {
                ii = i-1;
                jj = j;
            } else if (direct == 2) {
                ii = i+1;
                jj = j;
            } else if (direct == 3) {
                ii = i;
                jj = j-1;
            } else {
                ii = i;
                jj = j+1;
            }
            if (ii >= 0 && ii < board.length && jj >= 0 && jj < board[0].length
                    && !visited[ii][jj] && board[ii][jj] == word[pos]) {
                visited[ii][jj] = true;
                if (exi2(board, word, ii, jj, pos+1, visited)) {
                    return true;
                }
                visited[ii][jj] = false;
            }
        }
        return false;
    }
}
```

上面为了标记已经走过的路径，使用了一个 visited 数组对已走过的位置作了标记，通过深度优先遍历这个二维数组，寻求是否存在问题的解。

那么这个 visited 数组是否可以省略？首先看 visited 的数组的作用：通过保存已走过位置，保证在之后的遍历中不会产生回路。那么如果我们能够通过别的方法保证不会重复走某一点就好了。于是想到，可以给已经走过的点赋值成一个值，这个值不会与待求解字符串中的任何一个字符相同，那么在之后的过程中，必然不会再走这个点。如下：

```java
public static boolean exist2(char[][] board, String word) {
    int m = board.length, n = board[0].length;
    char[] wordC = word.toCharArray();
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (board[i][j] == wordC[0]) {
                char c = board[i][j];
                board[i][j] = '\0';
                if (exi2(board, wordC, i, j, 1)) {
                    return true;
                }
                board[i][j] = c;
            }
        }
    }
    return false;
}
public static boolean exi2(char[][] board, char[] word, int i, int j, int pos) {
    if (pos == word.length) {
        return true;
    }
    int direct = 0;
    while (direct++ < 4) {
        int ii = i, jj = j;
        if (direct == 1) ii--;
        else if (direct == 2) ii++;
        else if (direct == 3) jj--;
        else if (direct == 4) jj++;
        if (ii >= 0 && ii < board.length && jj >= 0 && jj < board[0].length
                && board[ii][jj] == word[pos]) {
            char c = board[ii][jj];
            board[ii][jj] = '\0';
            if (exi2(board, word, ii, jj, pos+1)) {
                return true;
            }
            board[ii][jj] = c;
        }
    }
    return false;
}
```

给已经访问过的点赋值`'\0'`，因为字符串`word`中不存在`'\0'`，那么自然在之后的过程中不会再回到这个点，但是跟所有的递归函数一样，对某些变量的修改，要在使用之后将其复原，因为在之后的查询中还会使用到这个变量。

这个方法还有一种代码书写方式，看起来更清爽：

```java
public static boolean exist3(char[][] board, String word) {
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            if (exi3(board, word.toCharArray(), i, j, 0)) {
                return true;
            }
        }
    }
    return false;
}
public static boolean exi3(char[][] board, char[] word, int i, int j, int pos) {
    if (pos == word.length) {
        return true;
    }
    if (i >= board.length || i < 0 || j >= board[0].length || j < 0) {
        return false;
    }
    char c = board[i][j];
    if (c == word[pos++]) {
        board[i][j] = '\0';
        boolean exist = exi3(board, word, i-1, j, pos)
                || exi3(board, word, i+1, j, pos)
                || exi3(board, word, i, j-1, pos)
                || exi3(board, word, i, j+1, pos);
        board[i][j] = c;
        return exist;
    }
    return false;
}
```

