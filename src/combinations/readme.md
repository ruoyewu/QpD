### Question

Given two integers *n* and *k*, return all possible combinations of *k* numbers out of 1 ... *n*.

**Example:**

```
Input: n = 4, k = 2
Output:
[
  [2,4],
  [3,4],
  [2,3],
  [1,2],
  [1,3],
  [1,4],
]
```

### Solution

求组合的问题，与排列一样，可以使用递归求解。不过组合的要点是解决重复的问题，如对于`[1,2]`和`[2,1]`而言，它们是两种排列，但是一种组合，不过消除组合的方式也很简单，保证排列始终是一个增序的即可，理论基础为，任何一个非增序的排列，都可以通过排序的方式将其化为增序排列，在这个过程中只涉及到顺序的变化，所以他们属于同一个组合，在元素相同且不存在重复的情况下只有一种增序排列，对应着只有一种组合。

代码如下：

```java
public static List<List<Integer>> combine(int n, int k) {
    List<List<Integer>> result = new ArrayList<>();
    com(result, new ArrayList<>(k), 0, n, k);
    return result;
}
private static void com(List<List<Integer>> result, List<Integer> cur, int pos, int n, int k) {
    if (k == 0) result.add(new ArrayList<>(cur));
    else {
        for (int i = pos+1; i <= n; i++) {
            if (n - i + 1 < k) break;
            else {
                cur.add(i);
                com(result, cur, i, n, k-1);
                cur.remove(cur.size()-1);
            }
        }
    }
}
```

求所有组合的时候，与排列不一样的就在于每次选择数字的时候可选的数字必须大于已选择的数字。