package pascals_triangle_2;

import java.util.ArrayList;
import java.util.List;

/**
 * User: wuruoye
 * Date: 2019-05-19 14:18
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static List<Integer> getRow(int rowIndex) {
        List<Integer> last = new ArrayList<>(rowIndex+1),
                cur = new ArrayList<>(rowIndex+1), tmp;
        last.add(1);
        if (rowIndex == 0) return last;
        last.add(1);
        if (rowIndex == 1) return last;

        for (int i = 2; i <= rowIndex+1; i++) {
            cur.add(1);
            for (int j = 1; j < i-1; j++) {
                cur.add(last.get(j) + last.get(j-1));
            }
            cur.add(1);

            tmp = last;
            last = cur;
            cur = tmp;
            cur.clear();
        }

        return last;
    }

    public static List<Integer> getRow2(int rowIndex) {
        List<Integer> result = new ArrayList<>();
        if (rowIndex == 0) {
            result.add(1); return result;
        }
        if (rowIndex == 1) {
            result.add(1); result.add(1); return result;
        }

        int[] last = new int[rowIndex+1], cur = new int[rowIndex+1], tmp;
        last[0] = last[1] = last[rowIndex] = cur[0] = cur[rowIndex] = 1;

        for (int i = 3; i <= rowIndex+1; i++) {
            for (int j = 1; j < i-1; j++) {
                cur[j] = last[j] + last[j-1];
            }
            cur[i-1] = 1;

            tmp = last;
            last = cur;
            cur = tmp;
        }

        for (int n : last) {
            result.add(n);
        }
        return result;
    }
}
