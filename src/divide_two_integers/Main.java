package divide_two_integers;

/**
 * User: wuruoye
 * Date: 2019-03-08 19:46
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE;
        return dividend / divisor;
    }

}
