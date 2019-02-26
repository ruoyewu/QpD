package hamming_distance;

/**
 * User: wuruoye
 * Date: 2019-02-25 21:33
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int x = 1577962638, y = 1727613287;
        System.out.println(hammingDistance(x, y));
        System.out.println(hammingDistance2(x,y));
    }

    public static int hammingDistance(int x, int y) {
        if (x < y) return hammingDistance(y, x);
        String sx = Integer.toString(x, 2), sy = Integer.toString(y, 2);
        int i = sx.length()-1, j = sy.length()-1;
        int distance = 0;
        while (i >= 0 && j >= 0) {
            if (sx.charAt(i) != sy.charAt(j)) {
                distance++;
            }
            i--;
            j--;
        }
        while (i >= 0) {
            if (sx.charAt(i) == '1') {
                distance++;
            }
            i--;
        }
        return distance;
    }

    public static int hammingDistance2(int x, int y) {
        if (x < y) return hammingDistance2(y, x);
        int i = 1;
        int count = 0;
        while (i > 0 && i <= x) {
            if ((x & i) != (y & i)) {
                count++;
            }
            i <<= 1;
        }
        return count;
    }

    public static int hammingDistance3(int x, int y) {
        int count = 0, c = x ^ y;
        while (c != 0) {
            count += c & 1;
            c >>= 1;
        }
        return count;
    }
}
