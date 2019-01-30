package maximal_square;

/**
 * User: wuruoye
 * Date: 2019-01-30 19:02
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        char[][] matrix = new char[][] {
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}
        };
        System.out.println(maximalSquare2(matrix));
    }

    public static int maximalSquare(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0)
            return 0;

        int m = matrix.length, n = matrix[0].length;
        int[][] count = new int[m][n];
        for (int i = 0; i < m; i++) {
            int cnt = 0;
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    count[i][j] = ++cnt;
                } else {
                    count[i][j] = (cnt = 0);
                }
            }
        }

        int max = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int c = count[i][j];
                for (int k = i; k >= 0; k--) {
                    if (i-k+1 > c) break;
                    int cc = count[k][j];
                    if (cc == 0) break;
                    if (cc < c) c = cc;
                    max = Math.max(max, Math.min(c, i-k+1));
                }
            }
        }

        return max*max;
    }

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
}
