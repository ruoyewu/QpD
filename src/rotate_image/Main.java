package rotate_image;

/**
 * User: wuruoye
 * Date: 2019/1/15 16:46
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static void rotate(int[][] matrix) {
        int n = matrix.length;
        int[][] res = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[j][n-i-1] = matrix[i][j];
            }
        }

//        System.arraycopy(res, 0, matrix, 0, n*n);

        for (int i = 0; i < n; i++) {
            System.arraycopy(res[i], 0, matrix[i], 0, n);
        }
    }

    public static void rotate2(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n / 2 + n % 2; i++) {
            for (int j = 0; j < n / 2; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[n-1-j][i];
                matrix[n-1-j][i] = matrix[n-1-i][n-j-1];
                matrix[n-1-i][n-1-j] = matrix[j][n-1-i];
                matrix[j][n-1-i] = tmp;
            }
        }
    }
}
