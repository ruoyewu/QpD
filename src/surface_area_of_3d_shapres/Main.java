package surface_area_of_3d_shapres;

/**
 * User: wuruoye
 * Date: 2019-03-09 21:18
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(surfaceArea(new int[][]{{1, 0}, {0, 2}}));
    }

    public static int surfaceArea(int[][] grid) {
        int n = grid.length;
        int area = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] > 0) {
                    area += 2 + 4 * grid[i][j];
                    if (j > 0) {
                        area -= Math.min(grid[i][j-1], grid[i][j]) * 2;
                    }
                    if (i > 0) {
                        area -= Math.min(grid[i-1][j], grid[i][j]) * 2;
                    }
                }
            }
        }
        return area;
    }
}
