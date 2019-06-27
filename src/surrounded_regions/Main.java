package surrounded_regions;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        char[][] board = new char[][] {
                {'X'}
        };
        solve(board);
        System.out.println(Arrays.deepToString(board));
    }

    public static void solve(char[][] board) {
        if (board.length == 0 || board[0].length == 0) return ;
        int m = board.length, n = board[0].length;
        boolean[][] empty = new boolean[m][n];


        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O' && !empty[0][j]) {
                expand(board, empty, 0, j);
            }
            if (board[m-1][j] == 'O' && !empty[m-1][j]) {
                expand(board, empty, m-1, j);
            }
        }

        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O' && !empty[i][0]) {
                expand(board, empty, i, 0);
            }
            if (board[i][n-1] == 'O' && !empty[i][n-1]) {
                expand(board, empty, i, n-1);
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!empty[i][j]) {
                    board[i][j] = 'X';
                }
            }
        }
    }

    private static void expand(char[][] board, boolean[][] empty, int x, int y) {
        empty[x][y] = true;
        // up
        if (x > 0 && board[x-1][y] == 'O' && !empty[x-1][y]) {
            expand(board, empty, x-1, y);
        }
        // down
        if (x < board.length-1 && board[x+1][y] == 'O' && !empty[x+1][y]) {
            expand(board, empty, x+1, y);
        }
        // left
        if (y > 0 && board[x][y-1] == 'O' && !empty[x][y-1]) {
            expand(board, empty, x, y-1);
        }
        // right
        if (y < board[0].length-1 && board[x][y+1] == 'O' && !empty[x][y+1]) {
            expand(board, empty, x, y+1);
        }
    }

    public static void solve2(char[][] board) {
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
    }
}
