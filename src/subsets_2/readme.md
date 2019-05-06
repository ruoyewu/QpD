### Question

Given a collection of integers that might contain duplicates, **nums**, return all possible subsets (the power set).

**Note:** The solution set must not contain duplicate subsets.

**Example:**

```
Input: [1,2,2]
Output:
[
  [2],
  [1],
  [1,2,2],
  [2,2],
  [1,2],
  []
]
```

### Solution

subsets 一题的扩展，相比于上一题，数组中的数字可重复出现，但是结果中不能出现重复的子数组。延续上一题的跳过法，再加上某一个位置不能选择相同的数字，就是本题的解答。代码如下：

```java
public static List<List<Integer>> subsetsWithDup(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> result = new ArrayList<>();
    sub(result, new ArrayList<>(nums.length), nums, 0);
    return result;
}
private static void sub(List<List<Integer>> result, List<Integer> cur, int[] nums, int pos) {
    result.add(new ArrayList<>(cur));
    for (int i = pos; i < nums.length; i++) {
        if (i == pos || nums[i] != nums[i-1]) {
            cur.add(nums[i]);
            sub(result, cur, nums, i+1);
            cur.remove(cur.size()-1);
        }
    }
}
```

在递归方法中的遍历中加入了一个判断，`if(i == pos || nums[i] != nums[i-1])`，就是为了规避如`[1,2,2,3]`这样一个数组中，当选择的时候会重复选择 2 导致重复的问题，如`[1,2(第一个 2),3]`和`[1,2(第二个 2),3]`，这种情况下只保留第一个 2，之后的 2 全部跳过就可以了。不过为了保留如`[1,2,2,3]`这样的结果，所以每次选择的时候，pos 位置的数字无论是否与前一个位置重复，都要选择。