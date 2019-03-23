package sudoku_solver;

/**
 * User: wuruoye
 * Date: 2019-03-23 15:42
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        char[][] board = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        solveSudoku(board);
    }

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

    public static void solveSudoku2(char[][] board) {
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
}
