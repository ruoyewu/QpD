package zigzag_conversion;

import java.util.Scanner;

/**
 * User: wuruoye
 * Date: 2018/6/19 07:42
 * Description: Ω‚Ã‚¥˙¬Î
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        int row = scanner.nextInt();

        System.out.println(solve(s, row));
    }

    // with StringBuilder array
    private static String solveWithStringBuilder(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }

        int one = numRows * 2 - 2;

        StringBuilder[] builders = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            builders[i] = new StringBuilder();
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            int col = i % one;
            col = col < numRows ? col : (numRows - 2 - (col - numRows));
            builders[col].append(c);
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            builder.append(builders[i]);
        }

        return builder.toString();
    }

    // without STL
    private static String solve(String s, int numRows) {
        if (numRows == 1 || s.length() <= numRows) {
            return s;
        }

        char[] cO = s.toCharArray();
        char[] cT = new char[s.length()];

        int outIndex = 0;
        for (int row = 0; row < numRows; row++) {
            int offset = row;
            cT[outIndex++] = cO[offset];

            int skipTop = row > 0 ? (row - 1) * 2 + 1 : 0;
            int skipBottom = row < numRows - 1 ? (numRows - row - 2) * 2 + 1 : 0;

            while (true) {
                if (skipBottom > 0) {
                    offset += skipBottom + 1;
                    if (offset >= s.length()) {
                        break;
                    }
                    cT[outIndex++] = cO[offset];
                }
                if (skipTop > 0) {
                    offset += skipTop + 1;
                    if (offset >= s.length()) {
                        break;
                    }
                    cT[outIndex++] = cO[offset];
                }
            }
        }

        return new String(cT);
    }
}
