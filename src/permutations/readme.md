### Question

Given a collection of **distinct** integers, return all possible permutations.

**Example:**

```
Input: [1,2,3]
Output:
[
  [1,2,3],
  [1,3,2],
  [2,1,3],
  [2,3,1],
  [3,1,2],
  [3,2,1]
]
```

### Solution

全排列，一般可以使用递归的深度优先遍历，可以将解看作是一个树，这棵树从根结点到每个叶子结点的路径中都包含所给数组的每一个元素并且无重复，也就是说路径长度都等于数组的大小。

如果使用递归的方式，递归函数应该至少包含两个参数：

1.  已选择的元素
2.  待选择的元素

每次从待选择元素中选择一个加入已选择元素，执行递归，递归函数结束的时候还要将这个操作撤销，类似于“回溯”的概念，然后选择另一个元素。所以如何能够使得这部操作的效率最大化是当前采用的方法的重点，对于下面这种方法：

```java
public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> left = new ArrayList<>();
        for (int i : nums) {
            left.add(i);
        }
        dfs(result, new ArrayList<>(), left);

        return result;
    }

    public static void dfs(List<List<Integer>> result, List<Integer> saved, List<Integer> left) {
        if (left.isEmpty()) {
            result.add(new ArrayList<>(saved));
        } else {
            for (int i = 0; i < left.size(); i++) {
                saved.add(left.remove(i));
                dfs(result, saved, left);
                left.add(i, saved.remove(saved.size()-1));
            }
        }
    }
}
```

主要的耗时操作在于每一次调用递归函数前后的数据添加与移除，而下面这种方法：

```java
public static List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    Set<Integer> saved = new HashSet<>();
    dfs(result, saved, nums);
    
    return result;
}
public static void dfs(List<List<Integer>> result, Set<Integer> saved, int[] nums) {
    if (saved.size() == nums.length) {
        result.add(new ArrayList<>(saved));
    } else {
        for (int num : nums) {
            if (!saved.contains(num)) {
                saved.add(num);
                dfs(result, saved, nums);
                saved.remove(num);
            }
        }
    }
}
```

虽然没有了两个链表频繁的增删操作，但是每个递归函数都要完整地遍历数组一遍，也会产生不少的耗时。