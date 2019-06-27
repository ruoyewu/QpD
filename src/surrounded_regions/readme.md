### Question

Given a 2D board containing `'X'` and `'O'` (**the letter O**), capture all regions surrounded by `'X'`.

A region is captured by flipping all `'O'`s into `'X'`s in that surrounded region.

**Example:**

```
X X X X
X O O X
X X O X
X O X X
```

After running your function, the board should be:

```
X X X X
X X X X
X X X X
X O X X
```

**Explanation:**

Surrounded regions shouldn’t be on the border, which means that any `'O'` on the border of the board are not flipped to `'X'`. Any `'O'` that is not on the border and it is not connected to an `'O'` on the border will be flipped to `'X'`. Two cells are connected if they are adjacent cells connected horizontally or vertically.

### Solution

题目比较容易理解，首先，所有处于边框位置上的 O 一定是不能被 X 包围的，而在中间的 O ，如果它能与边缘位置的 O 建立沟通，那么它也不属于被 X 包围的那一部分。所以本题可以使用深度优先搜索找出所有的未被 X 包围的 O ，然后将剩余的 O 都置为 X 即可。

在深度优先搜索中，先要找到所有的根结点，然后找到根结点的字节点，依次遍历。上面描述可以看到，可以讲边缘位置的 O 看作是深度遍历的根结点，二维数组可以转身变成四叉树：上下左右均为中间节点的字节点，那么从根结点开始延伸，便可以找到所有与之相连的 O 。

但是本质上这并不是一个四叉树，而是一个二维数组，所以在遍历的时候会遇到重复访问某一节点的情况，如果不加以修正，就会产生大量的重复计算。一种简单的方法是，通过某种手段将已经访问过的节点标志下来，当重复访问某一个节点的时候，就直接跳过。

而如何标志已经访问过的节点？一般两种方案，使用额外的存储空间，或者利用节点自身存储状态。从题目上看，整个二维数组中的节点有两种状态，O 或 X ，如果要标识出另一种状态，表示已经被访问到的 O ，可以将其置为其他任意一个字符表示，如 I 。于是深度遍历完成之后，整个二维数组有三种状态，O 表示未被访问到的 O ，而 I 表示已经访问到的 O 。此时只需要遍历一次二维数组，将 O 和 X 都置为 X ，将 I 置为 O 即可。

代码如下：

```java
public static void solve(char[][] board) {
    if (board.length == 0 || board[0].length == 0) return ;
    int m = board.length, n = board[0].length;
    for (int j = 0; j < n; j++) {
        if (board[0][j] == 'O') {
            expand(board, 0, j);
        }
        if (board[m-1][j] == 'O') {
            expand(board, m-1, j);
        }
    }
    for (int i = 0; i < m; i++) {
        if (board[i][0] == 'O') {
            expand(board, i, 0);
        }
        if (board[i][n-1] == 'O') {
            expand(board, i, n-1);
        }
    }
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (board[i][j] != 'I') {
                board[i][j] = 'X';
            } else {
                board[i][j] = 'O';
            }
        }
    }
}
private static void expand(char[][] board, int x, int y) {
    board[x][y] = 'I';
    // up
    if (x > 0 && board[x-1][y] == 'O') {
        expand(board, x-1, y);
    }
    // down
    if (x < board.length-1 && board[x+1][y] == 'O') {
        expand(board,x+1, y);
    }
    // left
    if (y > 0 && board[x][y-1] == 'O') {
        expand(board, x, y-1);
    }
    // right
    if (y < board[0].length-1 && board[x][y+1] == 'O') {
        expand(board, x, y+1);
    }
```

