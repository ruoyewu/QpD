package climbing_stairs;

/**
 * User: wuruoye
 * Date: 2019/1/17 19:05
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int climbStairs(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 2;
        int[] stairs = new int[n];
        stairs[0] = 1;
        stairs[1] = 2;
        for (int i = 2; i < n; i++) {
            stairs[i] = stairs[i-2] + stairs[i-1];
        }

        return stairs[n-1];
    }

    public static int climbStairs2(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 2;
        int l = 1;
        int ll = 2;
        int s = 0;
        for (int i = 2; i < n; i++) {
            s = l + ll;
            l = ll;
            ll = s;
        }
        return s;
    }

    public static int climbStairs3(int n) {
        int[][] q = {{1,1}, {1,0}};
        int[][] res = {{1,0}, {0,1}};
        while (n > 0) {
            if ((n & 1) == 1) {
                res = multiply(res, q);
            }
            n >>= 1;
            q = multiply(q, q);
        }
        return res[0][0];
    }

    public static int[][] multiply(int[][] a, int[][] b) {
        int[][] c = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                c[i][j] = a[i][0] * b[0][j] + a[i][1] * b[1][j];
            }
        }
        return c;
    }

    public static int climbStairs4(int n) {
        double sqrt5 = Math.sqrt(5);
        return (int) ((Math.pow((1+sqrt5)/2, n+1) - Math.pow((1-sqrt5)/2, n+1)) / sqrt5);
    }
}
