package word_search;

/**
 * User: wuruoye
 * Date: 2019/1/23 17:12
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

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
}
