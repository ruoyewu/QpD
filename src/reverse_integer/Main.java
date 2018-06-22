package reverse_integer;

import java.util.Scanner;

/**
 * User: wuruoye
 * Date: 2018/6/22 12:30
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();

        System.out.println(reverse2(num));
    }

    public static int reverse(int x) {
        boolean negative = x < 0;
        if (negative) {
            x = -x;
        }
        int result;

        char[] origin = String.valueOf(x).toCharArray();
        int length = origin.length;
        int max = length / 2;

        for (int i = 0; i < max; i++) {
            char t = origin[i];
            origin[i] = origin[length - 1 - i];
            origin[length - 1 - i] = t;
        }

        try {
            result = Integer.valueOf(new String(origin));
        } catch (NumberFormatException ignored) {
            return 0;
        }

        return negative ? -result : result;
    }

    public static int reverse2(int x) {
        boolean negative = x < 0;
        if (negative) {
            x = -x;
        }
        int result = 0;
        int maxNum = Integer.MAX_VALUE / 10;

        while (x > 0) {
            int rem = x % 10;
            x /= 10;
            if (result > maxNum) {
                return 0;
            }
            result = result * 10 + rem;
        }

        return negative ? -result : result;
    }
}
