package maximum_frequency_stack;

import java.util.*;

/**
 * User: wuruoye
 * Date: 2019-03-09 22:24
 * Description:
 */
public class FreqStack {
    private int capacity;
    private int size;
    private int[] nums;
    private HashMap<Integer, Integer> map;

    public FreqStack() {
        capacity = 4;
        size = 0;
        nums = new int[capacity];
        map = new HashMap<>();
    }

    public void push(int x) {
        if (size == capacity) {
            expend();
        }
        nums[size++] = x;
        map.put(x, map.getOrDefault(x, 0) + 1);
    }

    public int pop() {
        HashSet<Integer> set = new HashSet<>();
        int max = Integer.MIN_VALUE, num = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > max) {
                set.clear();
                num = entry.getKey();
                max = entry.getValue();
                set.add(num);
            } else if (entry.getValue() == max) {
                set.add(entry.getKey());
            }
        }

        int pos = size-1;
        while (!set.contains(nums[pos])) pos--;
        num = nums[pos];
        map.put(num, max-1);

        if (pos != size-1) {
            System.arraycopy(nums, pos+1, nums, pos, size-pos-1);
        }
        size--;
        return num;
    }

    private void expend() {
        int newCapacity = capacity << 1;
        if (newCapacity < 0) newCapacity = Integer.MAX_VALUE;
        int[] newNums = new int[newCapacity];
        System.arraycopy(nums, 0, newNums, 0, size);
        nums = newNums;
        capacity = newCapacity;
    }
}
