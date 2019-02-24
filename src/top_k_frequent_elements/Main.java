package top_k_frequent_elements;

import java.util.*;

/**
 * User: wuruoye
 * Date: 2019-02-23 21:49
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        topKFrequent(new int[]{1,1,1,2,2,3}, 2);
    }

    public static List<Integer> topKFrequent(int[] nums, int k) {
        Arrays.sort(nums);
        int[] result = new int[k];
        int[] counts = new int[k];
        int count = 1;
        for (int i = 0; i < nums.length; i++) {
            if (i < nums.length-1 && nums[i] == nums[i+1]) {
                count++;
            } else {
                int j = result.length;
                while (j > 0 && counts[j-1] < count) j--;

                if (j == result.length-1) {
                    result[j] = nums[i];
                    counts[j] = count;
                } else if (j < result.length-1) {
                    System.arraycopy(result, j, result, j+1, result.length-j-1);
                    System.arraycopy(counts, j, counts, j+1, result.length-j-1);
                    result[j] = nums[i];
                    counts[j] = count;
                }
                count = 1;
            }
        }
        List<Integer> list = new ArrayList<>();
        for (int r : result) {
            list.add(r);
        }
        return list;
    }

    public static List<Integer> topKFrequent2(int[] nums, int k) {
        List<Integer> result = new ArrayList<>();
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for (int num : nums) {
            if (num > max) max = num;
            if (num < min) min = num;
        }

        int len = max - min + 1;
        int[] frequency = new int[len];
        for (int num : nums) {
            frequency[num-min]++;
        }

        ArrayList[] buckets = new ArrayList[nums.length+1];
        int i = 0;
        for (int f : frequency) {
            if (buckets[f] == null) {
                buckets[f] = new ArrayList<>();
            }
            buckets[f].add(min+i++);
        }

        for (int j = buckets.length-1; j >= 0 && result.size() < k; j--) {
            if (buckets[j] != null) {
                result.addAll(buckets[j]);
            }
        }

        return result;
    }

    public static List<Integer> topKFrequent3(int[] nums, int k) {
        List<Integer> result = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        ArrayList[] buckets = new ArrayList[nums.length+1];
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int key = entry.getKey(), value = entry.getValue();
            if (buckets[value] == null) {
                buckets[value] = new ArrayList();
            }
            buckets[value].add(key);
        }

        for (int j = buckets.length-1; j >= 0 && result.size() < k; j--) {
            if (buckets[j] != null) {
                result.addAll(buckets[j]);
            }
        }

        return result;
    }
}
