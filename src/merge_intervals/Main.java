package merge_intervals;

import java.util.*;

/**
 * User: wuruoye
 * Date: 2019/1/16 17:06
 * Description:
 */
public class Main {
    public static class Interval {
        int start;
        int end;

        Interval() {
            start = 0;
            end = 0;
        }

        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }

    public static void main(String[] args) {
        Interval i1 = new Interval(2,6);
        Interval i2 = new Interval(0,0);
        Interval i3 = new Interval(1,1);
        Interval i4 = new Interval(3,5);
//        merge(new ArrayList<>(Arrays.asList(i1, i2,i3,i4)));
    }

    public static List<Interval> merge(List<Interval> intervals) {
        if (intervals.size() <= 1) {
            return intervals;
        }
        Comparator<Interval> com = new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                return o1.start - o2.start;
            }
        };
        List<Interval> result = new ArrayList<>();
        result.add(intervals.remove(0));

        for (int i = 0; i < intervals.size(); i++) {
            int start = -1, end = -1, pos = 0;
            Interval interval = intervals.get(i);

            for (int j = 0; j < result.size(); j++) {
                if (start == -1 && interval.start <= result.get(j).end) {
                    start = j;
                }
                if (interval.start > result.get(j).end) {
                    pos = j + 1;
                }
                if (interval.end >= result.get(j).start) {
                    end = j;
                } else {
                    break;
                }
            }

            if (start <= end && start >= 0) {
                Interval s = result.get(start);
                Interval e = result.get(end);
                s.start = Math.min(s.start, interval.start);
                s.end = Math.max(e.end, interval.end);
                for (int j = start+1; j <= end; j++) {
                    result.remove(start+1);
                }
            } else {
                result.add(pos, interval);
            }
        }

        return result;
    }

    public static List<Interval> merge2(List<Interval> intervals) {
        int n = intervals.size();
        if (n <= 1) {
            return intervals;
        }
        int[] start = new int[n];
        int[] end = new int[n];
        int i = 0;
        for (Interval interval : intervals) {
            start[i] = interval.start;
            end[i++] = interval.end;
        }

        Arrays.sort(start);
        Arrays.sort(end);

        List<Interval> result = new ArrayList<>();
        int j = 0;
        int s = start[0];
        for (i = 1; i < n; i++, j++) {
            if (start[i] > end[j]) {
                result.add(new Interval(s, end[j]));
                s = start[i];
            }
        }
        result.add(new Interval(s, end[n-1]));
        return result;
    }

    public static List<Interval> merge3(List<Interval> intervals) {
        if (intervals.size() <= 1) {
            return intervals;
        }
        Interval[] ia = new Interval[intervals.size()];
        intervals.toArray(ia);
        intervals.clear();
        Arrays.sort(ia, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                return o1.start - o2.start;
            }
        });

        Interval last = ia[0];
        intervals.add(last);
        for (int i = 1; i < ia.length; i++) {
            Interval interval = ia[i];
            if (interval.start <= last.end) {
                last.end = Math.max(interval.end, last.end);
            } else {
                intervals.add(interval);
                last = interval;
            }
        }

        return intervals;
    }
}
