package candy;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();

        int[] ratings = new int[] {1,2,2};

        System.out.println(main.candy(ratings));

        System.out.println(main.candy2(ratings));
    }

    public int candy(int[] ratings) {
        int length = ratings.length;
        Set<Integer> saved = new HashSet<>();
        Set<Integer> left = new HashSet<>(length);
        for (int i = 0; i < ratings.length; i++) {
            left.add(i);
        }

        int result = 0;
        int current = 1;

        while (left.size() > 0) {
            for (int i : left) {
                if (i > 0 && left.contains(i-1) && ratings[i] > ratings[i-1]) {
                    continue;
                }
                if (i < length - 1 && left.contains(i+1) && ratings[i] > ratings[i+1]) {
                    continue;
                }
                result += current;
                saved.add(i);
            }
            left.removeAll(saved);
            saved.clear();
            current ++;
        }

        return result;
    }

    public int candy2(int[] ratings) {
        int length = ratings.length;
        int total = 0;
        int cur = 0;
        int level = 0;
        int start = -1;
        int last = Integer.MIN_VALUE;
        int lastLevel = 0;

        while (cur < length) {
            int c = ratings[cur];
            if (c > last) {
                start = cur;
                level ++;
                total += level;
                lastLevel = level;
            } else {
                if (c == last) {
                    start = cur;
                    lastLevel = 1;
                    total += 1;
                } else {
                    int dif = cur - start;
                    if (lastLevel <= dif) {
                        lastLevel ++;
                        total ++;
                    }
                    total += dif;
                }
                level = 1;
            }
            last = c;
            cur++;
        }

        return total;
    }
}
