package spiral_matrix_2;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019-04-18 16:17
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int n = 5;
        int[][] result = generateMatrix(n);
        System.out.println(Arrays.deepToString(result));
    }

    public static int[][] generateMatrix(int n) {
        int length = n * n;
        int[][] result = new int[n][n];

        int[] MOVE_I = {0, 1, 0, -1};
        int[] MOVE_J = {1, 0, -1, 0};
        int state = 0;
        int pos = 0;
        int i = 0, j = 0;
        while (pos++ < length) {
            result[i][j] = pos;

            i += MOVE_I[state];
            j += MOVE_J[state];
            if (i >= n || i < 0 || j >= n || j < 0 || result[i][j] != 0) {
                i -= MOVE_I[state];
                j -= MOVE_J[state];

                state = (state + 1) % 4;
                i += MOVE_I[state];
                j += MOVE_J[state];
            }
        }

        return result;
    }
}
