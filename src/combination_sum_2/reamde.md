### Question

Given a collection of candidate numbers (`candidates`) and a target number (`target`), find all unique combinations in `candidates` where the candidate numbers sums to `target`.

Each number in `candidates` may only be used **once** in the combination.

**Note:**

-   All numbers (including `target`) will be positive integers.
-   The solution set must not contain duplicate combinations.

**Example 1:**

```
Input: candidates = [10,1,2,7,6,1,5], target = 8,
A solution set is:
[
  [1, 7],
  [1, 2, 5],
  [2, 6],
  [1, 1, 6]
]
```

**Example 2:**

```
Input: candidates = [2,5,2,1,2], target = 5,
A solution set is:
[
  [1,2,2],
  [5]
]
```

### Solution

本题与 combination sum 一题相比，有两处不同：

1.  上题所有的数字都可以重复选择，而本题，每一个位置的数字最多选择一次
2.  上题中数组中无重复数字，本题中数组中有重复数字

那么先来看上题的解：

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

对于第一个不同很好解决，不能重复使用某一位置的数字，就是下一个位置的选择的问题，将递进 0 变为递进 1 即可，对应着是将`find(saved, sum, i, target, candidates)`这一句中的参数 i 换为参数 i+1 。

第二个不同，现在的数组中可能会有重复数字，以`[1,2,2], target = 3`为例，如果还以上面的解法，解就会变成`[1,2(第1个2)], [1,2(第2个2)]`，但很显然这是一个重复解，所以这时的重点就是消除重复。那么最明显的方法就是增加一个重复判断：每个位置的选择不能是重复的。就上面的例子，第一个选择的是 1 ，没有重复的，但选择第二个数的时候，有两个 2 ，那么这时需要通过判断，只能选择其中一个 2 。

整个的代码如下：

```java
public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
    Arrays.sort(candidates);
    List<List<Integer>> result = new ArrayList<>();
    combination(result, 0, candidates, target, 0, new ArrayList<>());
    return result;
}
private static void combination(List<List<Integer>> result, int pos,
                                int[] candidates, int target, int sum, List<Integer> saved) {
    for (int i = pos; i < candidates.length; i++) {
        int candidate = candidates[i];
        if (sum + candidate < target) {
            if (i > pos && candidate == candidates[i-1]) continue;
            sum += candidate;
            saved.add(candidate);
            combination(result, i+1, candidates, target, sum, saved);
            sum -= candidate;
            saved.remove(saved.size()-1);
        } else if (sum + candidates[i] == target) {
            List<Integer> l = new ArrayList<>(saved);
            l.add(candidates[i]);
            result.add(l);
            break;
        } else {
            break;
        }
    }
}
```

这里为了消除重复，增加了一个 continue 和 break ，其中 continue 是为了消除中间的重复，break 是为了消除尾部的重复。