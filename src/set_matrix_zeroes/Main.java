package set_matrix_zeroes;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019-05-03 17:11
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static void setZeros(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        boolean[] row = new boolean[m], col = new boolean[n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    row[i] = true;
                    col[j] = true;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (row[i] || col[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    public static void setZeros2(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;

        boolean firstCol = false;

        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) firstCol = true;
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        // fill col
        for (int i = 1; i < n; i++) {
            if (matrix[0][i] == 0) {
                for (int j = 0; j < m; j++) {
                    matrix[j][i] = 0;
                }
            }
        }

        // fill row
        for (int[] row : matrix) {
            if (row[0] == 0) {
                Arrays.fill(row, 0);
            }
        }

        // fill first col
        if (firstCol) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }
}
