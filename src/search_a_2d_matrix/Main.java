package search_a_2d_matrix;

/**
 * User: wuruoye
 * Date: 2019-05-04 12:23
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;
        int m = matrix.length, n = matrix[0].length;

        int x = 0, y = n-1;

        while (x < m && y >= 0) {
            int num = matrix[x][y];
            if (num > target) {
                --y;
            } else if (num < target){
                ++x;
            } else {
                return true;
            }
        }

        return false;
    }

    public static boolean searchMatrix2(int[][] matrix, int target) {
        int m, n;
        if (matrix == null || (m = matrix.length) == 0 || (n = matrix[0].length) == 0)
            return false;

        // find row
        int row = -1;
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] <= target) {
                row = i;
            } else {
                break;
            }
        }

        // find col
        if (row != -1) {
            for (int i = 0; i < n; i++) {
                if (matrix[row][i] == target) {
                    return true;
                }
            }
        }

        return false;
    }
}
