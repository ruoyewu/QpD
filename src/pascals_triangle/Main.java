package pascals_triangle;

import java.util.ArrayList;
import java.util.List;

/**
 * User: wuruoye
 * Date: 2019-05-19 14:12
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 1; i <= numRows; i++) {
            List<Integer> cur = new ArrayList<>();
            cur.add(1);
            if (i > 2) {
                List<Integer> last = result.get(i-2);
                for (int j = 1; j < i-1; j++) {
                    cur.add(last.get(j) + last.get(j-1));
                }
            }
            if (i > 1) {
                cur.add(1);
            }
            result.add(cur);
        }
        return result;
    }
}
