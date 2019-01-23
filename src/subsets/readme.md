### Question

Given a set of **distinct** integers, *nums*, return all possible subsets (the power set).

**Note:** The solution set must not contain duplicate subsets.

**Example:**

```
Input: nums = [1,2,3]
Output:
[
  [3],
  [1],
  [2],
  [1,2,3],
  [1,3],
  [2,3],
  [1,2],
  []
]
```

### Solution

本题的解题方法可以聚焦为，对于任何一个数字来说，它有被选择与不被选择两种结果，所以对于一个长为 n 的数组而言，它应该有$2^n$种结果，对于这样一个问题，第 1 个数的选择与否与它后面的数选择与否没有联系，只不过每一点的不同都会形成不同的结果，所以我们可以采用递归法解这样一个题。

使用递归的时候首先要对解分类，本题可以分为以下两类：

1.  解的长度。解的长度必然是从 1 到 n 。
2.  在选择某一位置 i 的数字之后，跳过一个位置再选择，跳过的位置的数量必然是 n-i-1 ，即跳过`[0,1,...,n-i-2]`个位置。

#### S1:剩余法

当未曾选择任何一个数字的时候，剩余的未选择的数字数量，应该是`[0,1,...,n]`个，然后没选择一个数字，未选择数量便减1，直到未选择数量为0时，选择结束。如下：

```java
public static List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i <= nums.length; i++) {
        sub(result, new ArrayList<>(), i, 0, nums);
    }
    return result;
}
public static void sub(List<List<Integer>> result, List<Integer> saved, int left, int i, int[] nums) {
    if (left == 0) {
        result.add(new ArrayList<>(saved));
    } else if (left > 0) {
        if (nums.length - i < left) {
            return;
        }
        if (nums.length - i == left) {
            List<Integer> cur = new ArrayList<>(saved);
            for (int j = i; j < nums.length; j++) {
                cur.add(nums[j]);
            }
            result.add(cur);
            return;
        }
        sub(result, saved, left, i + 1, nums);
        saved.add(nums[i]);
        sub(result, saved, left - 1, i + 1, nums);
        saved.remove(saved.size() - 1);
    }
}
```

其中，第 12 至 22 行，是对特殊情况的判断，如果未判断的剩余数字少于未选择的数字，那么这种情况必然无法构成解；如果剩余未判断数字数量等于未选择数字，那这时只有一种解，就是全部选择。

#### S2:跳过法

代码如下：

```java
public static List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    sub(result, new ArrayList<>(), 0, nums);
    return result;
}
public static void sub(List<List<Integer>> result, List<Integer> cur, int start, int[] nums) {
    result.add(new ArrayList<>(cur));
    for (int i = start; i < nums.length; i++) {
        cur.add(nums[i]);
        sub(result, cur, i+1, nums);
        cur.remove(cur.size()-1);
    }
}
```

我们从 0 开始，每个 sub 函数中都有一个当前位置，而这个 for 循环从当前位置一直遍历到最后，实际上就是一个跳过数量的选择过程，对于任何一个位置 start ，它的下一个位置是`[start+1,...,n+1]`，也就是说，下一个待添加的数字位置是`[start+1,...n,无]`，这样一来，对于任何一个位置跳过所有可能跳过的位置，必然就能得到所有的情况。