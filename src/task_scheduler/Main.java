package task_scheduler;

import java.util.Arrays;

/**
 * User: wuruoye
 * Date: 2019-03-01 18:12
 * Description:
 */
public class Main {
    private static int result;

    public static void main(String[] args) {
        char[] task = new char[]{'A', 'A', 'A', 'A'};
        int n = 2;
        System.out.println(leastInterval(task, n));
        System.out.println(leastInterval2(task, n));
        System.out.println(leastInterval3(task, n));
    }

    public static int leastInterval(char[] tasks, int n) {
        int[] nums = new int[26];
        int[] last = new int[26];
        for (char c : tasks) nums[c - 'A']++;
        Arrays.fill(last, -n-1);

        result = Integer.MAX_VALUE;
        least(n, nums, last, 0, tasks.length);
        return result;
    }

    private static void least(int n, int[] nums, int[] last, int pos, int cur) {
        if (pos >= result) {
            return;
        }
        if (cur == 0) {
            result = Math.min(result, pos);
        }
        for (int i = 0; i < 26; i++) {
            if (nums[i] > 0 && pos - last[i] > n) {
                int l = last[i];
                last[i] = pos;
                nums[i]--;
                least(n, nums, last, pos+1, cur-1);
                last[i] = l;
                nums[i]++;
            }
        }
        least(n, nums, last, pos+1, cur);
    }

    public static int leastInterval2(char[] tasks, int n) {
        int[] nums = new int[26];
        for (char c : tasks) {
            nums[c-'A']++;
        }
        Arrays.sort(nums);

        int pos = 0;
        while (nums[25] > 0) {
            int i = 25;
            while (i >= 0 && 25 - i <= n && nums[i] > 0) {
                nums[i]--;
                i--;
                pos++;
            }
            if (nums[25] != 0) {
                pos += Math.max(0, n-24+i);
            }
            Arrays.sort(nums);
        }

        return pos;
    }

    public static int leastInterval3(char[] tasks, int n) {
        int[] nums = new int[26];
        for (char c : tasks) {
            nums[c-'A']++;
        }
        Arrays.sort(nums);

        int max = nums[25]-1, idle = max * n;
        for (int i = 24; i >= 0 && nums[i] > 0 && idle > 0; i--) {
            idle -= Math.min(max, nums[i]);
        }

        return idle > 0 ? idle + tasks.length : tasks.length;
    }

    public static int leastInterval4(char[] tasks, int n) {
        int max = 0, count = 0;
        int[] nums = new int[26];
        for (char c : tasks) {
            nums[c-'A']++;
            if (max == nums[c-'A']) {
                count++;
            } else if (nums[c-'A'] > max){
                max = nums[c-'A'];
                count = 1;
            }
        }

        return Math.max(tasks.length, (max-1) * (n+1) + count);
    }
}
