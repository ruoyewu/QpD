### Question

Given a **set** of candidate numbers (`candidates`) **(without duplicates)** and a target number (`target`), find all unique combinations in `candidates` where the candidate numbers sums to `target`.

The **same** repeated number may be chosen from `candidates` unlimited number of times.

**Note:**

-   All numbers (including `target`) will be positive integers.
-   The solution set must not contain duplicate combinations.

**Example 1:**

```
Input: candidates = [2,3,6,7], target = 7,
A solution set is:
[
  [7],
  [2,2,3]
]
```

**Example 2:**

```
Input: candidates = [2,3,5], target = 8,
A solution set is:
[
  [2,2,2,2],
  [2,3,3],
  [3,5]
]
```

### Solution

对于一个给定的不重复的数组，选择任意个（可重复选择）使其和为某个定值，求所有的选择组合。对于这样一个问题的求解，因为涉及到数组元素的选择，我们一般有以下的方法：

1.  从数组的最小值开始选择，所以可以先将所给数组升序排序
2.  假设数组中有 n 个数升序排列，那么可选的方案应该是$n^k$，其中 k 代表的是所选择的数字个数
3.  假设已经选择了 k 个数之后，它们的和已经大于或等于这个定值，就不需要再往后选了。

如对于数组`[2,3,5]`，和为 8 而言，它有以下几种选择方案：

```
[2,2,2], [2,2,3], [2,2,5], [2,3,2], [2,3,3], [2,3,5], [2,5,2], [2,5,3], [2,5,5]
[3,2,2], [3,2,3], [3,2,5], [3,3,2], [3,3,3], [3,3,5], [3,5]
[5,2,2], [5,2,3], [5,2,5], [5,3], [5,5]
```

但是上述选择方案有一些事重复的，再加一个条件就可以去除那些重复的：后选的数大于等于先选的数，那么上述选择便变为了：

```
[2,2,2], [2,2,3], [2,2,5], [2,3,3,], [2,3,5], [2,5,5]
[3,3,3], [3,3,5], [3,5]
[5,5]
```

再对这些值判断，并将和为 8 的序列保存起来，就得到本题的解了。所以最后，本题的解就是先求出这些数字的排列（有重复选择，有不完全选择），然后筛选出合适的部分，所以使用递归求解比较妥当。算法如下：

```java
private static List<List<Integer>> result = new ArrayList<>();
public static List<List<Integer>> combinationSum(int[] candidates, int target) {
    result.clear();
    Arrays.sort(candidates);
    find(new ArrayList<>(), 0, 0, target, candidates);
    return result;
}
public static void find(List<Integer> saved, int sum, int pos, int target, int[] candidates) {
    for (int i = pos; i < candidates.length; i++) {
        if (sum + candidates[i] < target) {
            saved.add(candidates[i]);
            sum += candidates[i];
            find(saved, sum, i, target, candidates);
            sum -= candidates[i];
            saved.remove(saved.size()-1);
        } else if (sum + candidates[i] == target) {
            List<Integer> l = new ArrayList<>(saved);
            l.add(candidates[i]);
            result.add(l);
        } else {
            break;
        }
    }
}
```

