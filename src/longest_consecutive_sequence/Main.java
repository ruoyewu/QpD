package longest_consecutive_sequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * User: wuruoye
 * Date: 2019/1/27 13:58
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(longestConsecutive2(new int[]{0,-1}));
    }

    public static int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;
        Arrays.sort(nums);
        int longest = 1;
        int max = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] - nums[i-1] == 1) {
                longest++;
            } else if (nums[i] - nums[i-1] > 1){
                max = Math.max(max, longest);
                longest = 1;
            }
        }
        return Math.max(max, longest);
    }

    public static int longestConsecutive2(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums)
            set.add(num);

        int longest = 0;
        Iterator<Integer> i;
        while (!set.isEmpty()) {
            i = set.iterator();
            int start = i.next(), end = start;
            i.remove();

            while (true) {
                if (set.contains(start-1)) {
                    start--;
                    set.remove(start);
                } else if (set.contains(end+1)) {
                    end++;
                    set.remove(end);
                } else {
                    break;
                }
            }
            longest = Math.max(longest, end-start+1);
        }
        return longest;
    }
}
