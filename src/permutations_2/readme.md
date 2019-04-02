### Question

Given a collection of numbers that might contain duplicates, return all possible unique permutations.

**Example:**

```
Input: [1,1,2]
Output:
[
  [1,1,2],
  [1,2,1],
  [2,1,1]
]
```

### Solution

本题是 permutations 的升级版，在那一题的基础上增加了可重复数组，所以本题的重点就是如何筛选出重复的情况。这一点其实比较好实现：在深度优先遍历的基础上，某一位置不能重复选择相同的数字即可。

第一种方法，使用 HashSet 保存已经选择过的数字：

```java
public static List<List<Integer>> permuteUnique(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> result = new ArrayList<>();
    dfs(result, new boolean[nums.length], new ArrayList<>(), nums);
    return result;
}
private static void dfs(List<List<Integer>> result, boolean[] choose,
                        List<Integer> saved, int[] nums) {
    if (saved.size() == nums.length) {
        result.add(new ArrayList<>(saved));
    } else {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (!choose[i] && !set.contains(num)) {
                set.add(num);
                choose[i] = true;
                saved.add(num);
                dfs(result, choose, saved, nums);
                choose[i] = false;
                saved.remove(saved.size()-1);
            }
        }
    }
}
```

上述方法涉及到 HashSet 的频繁实例化，还应该想一种方法避免 HashSet 的使用，那该如何判断重复数字？如果使用一个变量保存可能重复的数字，是否有可能？如果给数组排个序，那么所有的重复数字都会出现在一起，如此一来，在这些重复数字里面选择一个即可，这时因为所有的重复数字都是在一起的，就不需要使用 HashSet 判断了。如下：

```java
public static List<List<Integer>> permuteUnique(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> result = new ArrayList<>();
    dfs(result, new boolean[nums.length], new ArrayList<>(), nums);
    return result;
}
private static void dfs(List<List<Integer>> result, boolean[] choose,
                        List<Integer> saved, int[] nums) {
    if (saved.size() == nums.length) {
        result.add(new ArrayList<>(saved));
    } else {
        int last = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (!choose[i] && num != last) {
                last = num;
                choose[i] = true;
                saved.add(num);
                dfs(result, choose, saved, nums);
                choose[i] = false;
                saved.remove(saved.size()-1);
            }
        }
    }
}
```

