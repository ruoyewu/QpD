package spiral_matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: wuruoye
 * Date: 2019-04-15 21:20
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        System.out.println(spiralOrder(matrix));
        System.out.println(spiralOrder2(matrix));
    }

    public static List<Integer> spiralOrder(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return Collections.emptyList();
        }
        int m = matrix.length, n = matrix[0].length;
        int state = 1;
        int startX = 0, endX = n-1;
        int startY = 1, endY = m-1;

        int len = m * n;
        List<Integer> result = new ArrayList<>(len);
        int i = 0, j = 0;
        int pos = 0;
        while (pos++ < len) {
            result.add(matrix[i][j]);

            switch (state) {
                case 1:
                    if (j == endX) {
                        i++;
                        endX--;
                        state = 2;
                    } else {
                        j++;
                    }
                    break;
                case 2:
                    if (i == endY) {
                        j--;
                        endY--;
                        state = 3;
                    } else {
                        i++;
                    }
                    break;
                case 3:
                    if (j == startX) {
                        i--;
                        startX++;
                        state = 4;
                    } else {
                        j--;
                    }
                    break;
                case 4:
                    if (i == startY) {
                        j++;
                        startY++;
                        state = 1;
                    } else {
                        i--;
                    }
                    break;
            }
        }

        return result;
    }

    public static List<Integer> spiralOrder2(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return Collections.emptyList();
        }
        int[] MOVE_I = {0, 1, 0, -1};
        int[] MOVE_J = {1, 0, -1, 0};
        int m = matrix.length, n = matrix[0].length, len = m * n;
        int state = 0, i = 0, j = 0, pos = 0;
        List<Integer> result = new ArrayList<>(len);
        while (pos++ < len) {
            result.add(matrix[i][j]);
            matrix[i][j] = Integer.MIN_VALUE;

            i += MOVE_I[state];
            j += MOVE_J[state];
            if (i >= m || i < 0 || j >= n || j < 0 || matrix[i][j] == Integer.MIN_VALUE) {
                // 回退
                i -= MOVE_I[state];
                j -= MOVE_J[state];
                // 重新步进
                state = (state + 1) % 4;
                i += MOVE_I[state];
                j += MOVE_J[state];
            }
        }
        return result;
    }
}
