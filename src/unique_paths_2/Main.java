package unique_paths_2;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019-05-03 14:54
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(uniquePathsWithObstacles2(new int[][]{
                {0,0,0},
                {0,1,0},
                {0,0,0}
        }));
    }

    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length, n = obstacleGrid[0].length;
        int[][] saved = new int[m][n];
        for (int[] row : saved) {
            Arrays.fill(row, -1);
        }

        return paths(saved, obstacleGrid, 0, 0);
    }

    private static int paths(int[][] saved, int[][] grid, int i, int j) {
        if (i >= grid.length || j >= grid[0].length || grid[i][j] == 1) return 0;
        if (i == grid.length-1 && j == grid[0].length -1) return 1;
        if (saved[i][j] >= 0) return saved[i][j];

        int path = paths(saved, grid, i+1, j) + paths(saved, grid, i, j+1);
        saved[i][j] = path;
        return path;
    }

    public static int uniquePathsWithObstacles2(int[][] obstacleGrid) {
        int m = obstacleGrid.length, n = obstacleGrid[0].length;
        int[][] saved = new int[m][n];

        if (obstacleGrid[m-1][n-1] == 1) return 0;

        saved[m-1][n-1] = 1;
        for (int i = m-1; i >= 0; i--) {
            for (int j = n-1; j >= 0; j--) {
                if (obstacleGrid[i][j] == 1)
                    saved[i][j] = 0;
                else {
                    if (i < m-1) saved[i][j] += saved[i+1][j];
                    if (j < n-1) saved[i][j] += saved[i][j+1];
                }
            }
        }

        return saved[0][0];
    }
}
