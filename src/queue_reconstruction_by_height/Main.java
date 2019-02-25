package queue_reconstruction_by_height;

import java.util.*;

/**
 * User: wuruoye
 * Date: 2019-02-24 22:46
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        int[][] people = new int[][]{{7,0}, {4,4}, {7,1}, {5,0}, {6,1}, {5,2}};
        reconstructQueue2(people);
    }

    public static int[][] reconstructQueue(int[][] people) {
        if (people.length == 0) return people;
        LinkedList<int[]> left = new LinkedList<>();
        int[] min = null;
        for (int[] p : people) {
            if (p[1] == 0 && (min == null || min[0] > p[0])) {
                if (min != null) left.add(min);
                min = p;
            } else {
                left.add(p);
            }
        }

        people[0] = min;
        construct(people, 1, left);
        return people;
    }

    private static boolean construct(int[][] people, int pos, LinkedList<int[]> left) {
        if (pos == people.length) return true;
        for (int i = 0; i < left.size(); i++) {
            int[] p = left.get(i);
            int k = 0, h = p[0];
            for (int j = 0; j < pos; j++) {
                if (people[j][0] >= h) {
                    k++;
                }
            }
            if (k == p[1]) {
                // 可选
                people[pos] = p;
                left.remove(i);
                if (construct(people, pos+1, left)) {
                    return true;
                }
                left.add(i, p);
            }
        }
        return false;
    }

    public static int[][] reconstructQueue2(int[][] people) {
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] == o2[0] ? o1[1] - o2[1] : o2[0] - o1[0];
            }
        });
        List<int[]> result = new ArrayList<>();
        for (int[] p : people) {
            result.add(p[1], p);
        }
        return result.toArray(people);
    }
}
