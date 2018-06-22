package string_to_integer;

import java.util.Scanner;

/**
 * User: wuruoye
 * Date: 2018/6/22 13:23
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        while (n-- > 0) {
            String in = scanner.next();
            System.out.println(myAtoi(in));
        }
    }

    public static int myAtoi(String str) {
        char[] arr = str.trim().toCharArray();
        int l = arr.length;
        int max = Integer.MAX_VALUE / 10;

        if (l == 0) {
            return 0;
        }

        int p = 0;
        int result = 0;
        boolean negative = false;
        if (arr[0] == '+') {
            p++;
        }else if (arr[0] == '-') {
            negative = true;
            p++;
        }
        while (p < l) {
            if (arr[p] >= '0' && arr[p] <= '9') {
                if (result > max) {
                    if (negative) {
                        return Integer.MIN_VALUE;
                    }else {
                        return Integer.MAX_VALUE;
                    }
                }
                if (result == max) {
                    if (negative && arr[p] >= '8') {
                        return Integer.MIN_VALUE;
                    }else if (!negative && arr[p] >= '7') {
                        return Integer.MAX_VALUE;
                    }
                }
                result = result * 10 + (arr[p] - '0');
            }else {
                break;
            }
            p++;
        }

        return negative ? -result : result;
    }
}
