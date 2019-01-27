### Question

Given an unsorted array of integers, find the length of the longest consecutive elements sequence.

Your algorithm should run in O(*n*) complexity.

**Example:**

```
Input: [100, 4, 200, 1, 3, 2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
```

### Solution

#### S1:排序法

在一个数组中，求连续的数字的个数的最大值，忽略重复的。那么很容易想到先对数组排序，然后由小及大逐个判断即可。

```java
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
```

此时时间复杂度为$O(n\log n)$。

#### S2:集合法

对于一个数字，从这个数字开始向两边连续延伸，一直到断开，那么这个连续的数字序列就是一个连续的片段，如果按照这种方法，将所有的连续片段都求出来，要求出最长的那个就轻而易举了。而要对一个序列进行延伸的话，需要不断地查询某个数的下一个数是否存在，可以使用哈希表提供快速查询。

```java
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
```

