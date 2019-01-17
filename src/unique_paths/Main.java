package unique_paths;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019/1/17 13:19
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int uniquePaths(int m, int n) {
        return max(m, n, 1, 1);
    }

    public static int max(int m, int n, int i, int j) {
        if (i == m && j == n) {
            return 1;
        } else if (i == m) {
            return max(m, n, i, j+1);
        } else if (j == n) {
            return max(m, n, i+1, j);
        } else {
            return max(m, n, i+1, j) + max(m, n, i, j+1);
        }
    }

    public static int uniquePaths2(int m, int n) {
        int[][] paths = new int[m][n];
        Arrays.fill(paths[0], 1);
        for (int i = 1; i < m; i++) {
            paths[i][0] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                paths[i][j] = paths[i-1][j] + paths[i][j-1];
            }
        }

        return paths[m-1][n-1];
    }

    public static int uniquePaths3(int m, int n) {
        if (m < n) {
            int tmp = m;
            m = n;
            n = tmp;
        }

        int[] pre = new int[n];
        Arrays.fill(pre, 1);
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                pre[j] = pre[j] + pre[j-1];
            }
        }
        return pre[n-1];
    }

    public static int uniquePaths4(int m, int n) {
        if (m == 0 || n == 0) return 0;
        if (m == 1 || n == 1) return 1;

        if (m < n) {
            int tmp = m;
            m = n;
            n = tmp;
        }

        long paths = 1;
        for (int i = 1; i < n; i++) {
            paths *= m + n - i -1;
            paths /= i;
        }
        return (int) paths;
    }
}
