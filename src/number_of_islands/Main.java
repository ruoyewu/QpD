package number_of_islands;

/**
 * User: wuruoye
 * Date: 2019-01-29 17:17
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int numIslands(char[][] grid) {
        if (grid.length == 0) return 0;
        int cnt = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (!visited[i][j] && grid[i][j] == '1') {
                    expand(grid, i, j, visited);
                    cnt++;
                }
            }
        }
        return cnt;
    }

    private static void expand(char[][] grid, int i, int j, boolean[][] visited) {
        visited[i][j] = true;
        if (i-1 >= 0 && !visited[i-1][j] && grid[i-1][j] == '1') {
            expand(grid, i-1, j, visited);
        }
        if (i+1 < grid.length && !visited[i+1][j] && grid[i+1][j] == '1') {
            expand(grid, i+1, j, visited);
        }
        if (j-1 >= 0 && !visited[i][j-1] && grid[i][j-1] == '1') {
            expand(grid, i, j-1, visited);
        }
        if (j+1 < grid[0].length && !visited[i][j+1] && grid[i][j+1] == '1') {
            expand(grid, i, j+1, visited);
        }
    }

    public static int numIslands2(char[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) return 0;
        int cnt = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    cnt++;
                    expand2(grid, i, j);
                }
            }
        }
        return cnt;
    }

    private static void expand2(char[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] != '1') {
            return;
        }
        grid[i][j] = 0;
        expand2(grid, i+1, j);
        expand2(grid, i-1, j);
        expand2(grid, i, j-1);
        expand2(grid, i, j+1);
    }
}
