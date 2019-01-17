package minimum_path_sum;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019/1/17 15:21
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] result = new int[n];

        result[0] = grid[0][0];
        for (int i = 1; i < n; i++) {
            result[i] = result[i-1] + grid[0][i];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (j == 0) {
                    result[j] = result[j] + grid[i][j];
                } else {
                    result[j] = Math.min(result[j], result[j-1]) + grid[i][j];
                }
            }
        }

        return result[n-1];
    }

    public static int minPathSum3(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        for (int i = 1; i < n; i++) {
            grid[0][i] = grid[0][i] + grid[0][i-1];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (j == 0) {
                    grid[i][0] = grid[i][0] + grid[i-1][0];
                } else {
                    grid[i][j] = grid[i][j] + Math.min(grid[i-1][j], grid[i][j-1]);
                }
            }
        }

        return grid[m-1][n-1];
    }

    public static int minPathSum2(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] saved = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(saved[i], -1);
        }
        saved[m-1][n-1] = grid[m-1][n-1];
        return min(grid, 0, 0, saved);
    }

    public static int min(int[][] grid, int i, int j, int[][] saved) {
        int m = grid.length, n = grid[0].length;
        if (saved[i][j] >= 0) {
            return saved[i][j];
        }
        int result;
        if (i == m-1) {
            result = min(grid, i, j+1, saved) + grid[i][j];
        } else if (j == n-1) {
            result = min(grid, i+1, j, saved) + grid[i][j];
        } else {
            result = Math.min(min(grid, i+1, j, saved), min(grid, i, j+1, saved)) + grid[i][j];
        }
        saved[i][j] = result;
        return result;
    }
}
