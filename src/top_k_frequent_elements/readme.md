### Question

Given a non-empty array of integers, return the **k** most frequent elements.

**Example 1:**

```
Input: nums = [1,1,1,2,2,3], k = 2
Output: [1,2]
```

**Example 2:**

```
Input: nums = [1], k = 1
Output: [1]
```

**Note:** 

-   You may assume *k* is always valid, 1 ≤ *k* ≤ number of unique elements.
-   Your algorithm's time complexity **must be** better than O(*n* log *n*), where *n* is the array's size.

### Solution

#### S1:直接排序

对于任意一个数组，要求这里面前 k 个出现频率最高的数字，这大概需要两个步骤：

1.  求出每个数字的频率
2.  将按照频率排序后的数字取前 k 个

如何快速求出每一个数字的出现次数？最好的情况就是所有相同数字都是连续出现的，这时只需要一次遍历，就可以求出所有数字的出现次数了。为了完成这样一个任务，一般是对数组进行排序。

在遍历的过程中，各数字的频率是一个一个求出来的，那么应该以什么方法按照频率排序？根据这种特点可以选择插入排序，于是有：

```java
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
```

在对频率排序的时候，因为只需要前 k 个，所以可以对这个插入排序进行一定的简化处理。

#### S2:桶排序

对于排序来说，如果追求运行时间上的极致，可以考虑使用桶排序，在第二次对于频率排序的时候，也可以采取桶排序，如下：

```java
public static List<Integer> topKFrequent(int[] nums, int k) {
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
```

在经历过两次排序之后，所有的数字都按照出现次数的顺序存储在 buckets 中了，然后取其中的前 k 个，得解。但是这种方式太浪费空间了，如数组`[1,111111]`，只计算了两个数，却使用了`111113`的空间。此时，可以使用一定的方法微调，如利用 HashMap 存储 数字-频率 这一键值对。

```java
public static List<Integer> topKFrequent(int[] nums, int k) {
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
```

这就能利用较少的空间完成计算出现次数这一任务。然后之后的频率排序依旧是与上面一样，使用桶排序。