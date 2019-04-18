package insert_interval;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019-04-16 22:58
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int[][] intervals = new int[][] {
//                {1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}
//                {1, 2}
                {9, 10}
        };
        int[] newInterval = new int[] {4, 8};

        System.out.println(Arrays.deepToString(insert(intervals, newInterval)));
    }

    public static int[][] insert(int[][] intervals, int[] newInterval) {
        if (intervals.length == 0) return new int[][]{newInterval};
        int from = 0, to = 0;
        int start = newInterval[0], end = newInterval[1];
        boolean sOk = false, eOk = false;
        int min = start, max = end;
        int pos = 0;
        while (pos < intervals.length) {
            int s = intervals[pos][0], e = intervals[pos][1];

            if (!eOk) {
                if (s > end) {
                    eOk = true;
                } else {
                    to = pos+1;
                    max = Math.max(e, max);
                }
            }

            if (!sOk) {
                if (e >= start) {
                    sOk = true;
                    from = pos;
                    min = Math.min(s, min);
                } else {
                    from = pos+1;
                }
            }

            if (eOk) break;
            ++pos;
        }

        int newLength = intervals.length - (to - from) + 1;
        int[][] result = new int[newLength][2];
        for (int i = 0; i < newLength; i++) {
            if (i < from) {
                result[i] = intervals[i];
            } else if (i == from) {
                result[i] = new int[]{min, max};
            } else {
                result[i] = intervals[i + (to - from) - 1];
            }
        }
        return result;
    }
}
