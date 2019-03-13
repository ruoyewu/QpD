package integer_to_roman;

/**
 * User: wuruoye
 * Date: 2019-03-13 14:28
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(intToRoman(1994));
    }

    public static String intToRoman(int num) {
        StringBuilder builder = new StringBuilder();
        while (num > 0) {
            if (num >= 1000) {
                builder.append('M');
                num -= 1000;
            } else if (num >= 900) {
                builder.append("CM");
                num -= 900;
            } else if (num >= 500) {
                builder.append('D');
                num -= 500;
            } else if (num >= 400) {
                builder.append("CD");
                num -= 400;
            } else if (num >= 100) {
                builder.append('C');
                num -= 100;
            } else if (num >= 90) {
                builder.append("XC");
                num -= 90;
            } else if (num >= 50) {
                builder.append('L');
                num -= 50;
            } else if (num >= 40) {
                builder.append("XL");
                num -= 40;
            } else if (num >= 10) {
                builder.append('X');
                num -= 10;
            } else if (num >= 9) {
                builder.append("IX");
                num -= 9;
            } else if (num >= 5) {
                builder.append('V');
                num -= 5;
            } else if (num >= 4) {
                builder.append("IV");
                num -= 4;
            } else {
                builder.append('I');
                --num;
            }
        }
        return builder.toString();
    }

    public static String intToRoman2(int num) {
        int[] nums = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] strings = new String[]{"M", "CM", "D", "CD", "C", "XC",
                "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder builder = new StringBuilder();
        while (num > 0) {
            for (int i = 0; i < nums.length; i++) {
                if (num >= nums[i]) {
                    builder.append(strings[i]);
                    num -= nums[i];
                    break;
                }
            }
        }
        return builder.toString();
    }

    public static String intToRoman3(int num) {
        String[][] map = new String[][]{{"I", "IV", "V", "IX"}, {"X", "XL", "L", "XC"},
                {"C", "CD", "D", "CM"}};
        int[] index = new int[]{1, 4, 5, 9};

        StringBuilder builder = new StringBuilder();
        StringBuilder tmp = new StringBuilder();
        int cur, level = 0;
        while (num > 0) {
            cur = num % 10;
            if (level == 3) {
                while (num-- > 0) builder.append('M');
                break;
            } else {
                tmp.setLength(0);
                while (cur > 0) {
                    int i;
                    if (cur >= 9) i = 3;
                    else if (cur >= 5) i = 2;
                    else if (cur >= 4) i = 1;
                    else i = 0;
                    tmp.append(map[level][i]);
                    cur -= index[i];
                }
                builder.append(tmp.reverse());
            }
            level++;
            num /= 10;
        }
        return builder.reverse().toString();
    }
}
