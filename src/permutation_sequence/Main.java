package permutation_sequence;

/**
 * User: wuruoye
 * Date: 2019-04-18 16:55
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int n = 4;
        int k = 9;
        System.out.println(getPermutation(n, k));
        System.out.println(getPermutation2(n, k));
        System.out.println(getPermutation3(n, k));
    }

    public static String getPermutation(int n, int k) {
        if (n == 1) return "1";

        int[] map = new int[n];
        map[1] = map[0] = 1;
        for (int i = 2; i < n; i++) {
            map[i] = i * map[i-1];
        }

        boolean[] select = new boolean[n];

        k -= 1;

        char[] result = new char[n];
        int pos = 0;
        while (pos < n) {
            int base = map[n-pos-1];
            int skip = k / base;
            k = k % base;

            for (int i = 0; i < n; i++) {
                if (!select[i]) {
                    if (skip == 0) {
                        select[i] = true;
                        result[pos++] = (char) (i+1+48);
                        break;
                    } else {
                        skip--;
                    }
                }
            }
        }

        return new String(result);
    }


    public static String getPermutation2(int n, int k) {
        int base = 1;
        for (int i = 2; i < n; i++) {
            base *= i;
        }
        char[] chs = new char[n];

        permutation(chs, 0, base, k-1);

        return new String(chs);
    }

    private static void permutation(char[] chs, int pos, int base, int skip) {
        chs[pos] = (char) (skip / base + 1 + '0');
        if (chs.length - pos - 1 != 0) {
            permutation(chs, pos+1, base / (chs.length - pos - 1), skip % base);
        }
        for (int i = pos+1; i < chs.length; i++) {
            if (chs[i] >= chs[pos]) {
                chs[i] += 1;
            }
        }
    }

    public static String getPermutation3(int n, int k) {
        if (n == 1) return "1";

        int[] map = new int[n];
        map[1] = map[0] = 1;
        for (int i = 2; i < n; i++) {
            map[i] = i * map[i-1];
        }

        k -= 1;
        char[] result = new char[n];
        int pos = 0;
        while (pos < n) {
            int base = map[n-pos-1];
            result[pos] = (char) (k / base + 49);
            k %= base;

            pos++;
        }


        for (int i = n-2; i >= 0; --i) {
            for (int j = i+1; j < n; j++) {
                if (result[i] <= result[j]) {
                    result[j]++;
                }
            }
        }

        return new String(result);
    }
}
