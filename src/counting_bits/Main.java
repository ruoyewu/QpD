package counting_bits;

/**
 * User: wuruoye
 * Date: 2019-02-23 17:09
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int[] result = countBits4(10000);
        for (int i : result) {
            System.out.print(i + " ");
        }
    }

    public static int[] countBits(int num) {
        int[] result = new int[num+1];
        result[0] = 0;
        int saved = 1;
        int tmp = 0;
        for (int i = 1; i <= num; i++) {
            if (saved == tmp) {
                saved <<= 1;
                tmp = 0;
            }
            result[i] = 1 + result[i-saved];
            tmp ++;
        }
        return result;
    }

    public static int[] countBits2(int num) {
        int[] result = new int[num+1];
        result[0] = 0;
        int saved = 1;
        for (int i = 1; i <= num; i++) {
            if (saved * 2 <= i) saved *= 2;
            result[i] = 1 + result[i-saved];
        }
        return result;
    }

    public static int[] countBits3(int num) {
        int[] result = new int[num + 1];
        result[0] = 0;
        for (int i = 1; i <= num; i++) {
            result[i] = Integer.bitCount(i);
        }
        return result;
    }

    public static int[] countBits4(int num) {
        int[] result = new int[num+1];
        bit(result, 1, 1, num);
        return result;
    }

    private static void bit(int[] result, int cur, int count, int num) {
        if (cur > num) return;
        result[cur] = count;
        bit(result, (cur << 1) + 1, count+1, num);
        bit(result, cur << 1, count, num);
    }
}
