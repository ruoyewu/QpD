package plus_one;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019-05-03 16:10
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(plusOne(new int[]{9,9,9,9,9})));
    }

    public static int[] plusOne(int[] digits) {
        int addition = 1;
        for (int i = digits.length-1; i >= 0; i--) {
            int n = digits[i];
            n += addition;
            if (n < 10) {
                digits[i] = n;
                addition = 0;
                break;
            } else {
                digits[i] = 0;
                addition = 1;
            }
        }

        if (addition != 0) {
            int[] newDigits = new int[digits.length+1];
            System.arraycopy(digits, 0, newDigits, 1, digits.length);
            newDigits[0] = addition;
            digits = newDigits;
        }

        return digits;
    }
}
