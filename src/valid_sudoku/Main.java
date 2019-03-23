package valid_sudoku;

/**
 * User: wuruoye
 * Date: 2019-03-17 09:39
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

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
}
