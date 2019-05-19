### Question

Given a triangle, find the minimum path sum from top to bottom. Each step you may move to adjacent numbers on the row below.

For example, given the following triangle

```
[
     [2],
    [3,4],
   [6,5,7],
  [4,1,8,3]
]
```

The minimum path sum from top to bottom is `11` (i.e., **2** + **3** + **5** + **1** = 11).

**Note:**

Bonus point if you are able to do this using only *O*(*n*) extra space, where *n* is the total number of rows in the triangle.

### Solution

一个三角形状的二维数组（非工整），求从第一层开始，到最后一层的所有路径中最短的一条，一个结点向下一层移动时，只能左移或右移一格。也就是说，可以把这个三角形近似比作为一棵满二叉树，第 i 层第 j 个结点的左右孩子分别是第 i+1 层的第 j 和 j+1 个结点，这样的话，本题就变成了求二叉树中最短的路径，这很简单，利用递归，求出左右两棵子树到叶子结点的最短路径，然后加上当前结点的值即可，如下，

```java
public static int minimumTotal(List<List<Integer>> triangle) {
    return min(triangle, 0, 0);
}
private static int min(List<List<Integer>> triangle, int level, int pos) {
    if (level == triangle.size()) return 0;
    return triangle.get(level).get(pos) + Math.min(
            min(triangle, level+1, pos),
            min(triangle, level+1, pos+1)
    );
}
```

但是不比实际的二叉树的是，这里的结点实际上是有交叉的，如例所给的三角形，结点 5 既是结点 3 的右孩子，又是结点 4 的左孩子，这在递归求解中需要两次求算，但是实际上只需要一次求算即可，也就是说，这里产生了重复计算。一般重复计算可以使用缓存解决，本题就可以，但是缓存显然需要耗费大量的空间，不再赘述。

而这种递归解法，一般又是可以转化为动态规划求解的，对于本题，所有的 i 层的解都直接依赖于第 i+1 层的解，于是，可以从三角形的最后一层开始不断向上求解，直到最上层只有一个解时，就是本题的解。有公式`level[i][j] = level[i][j] + min(level[i+1][j], level[i+1][j+1])`，

```java
public static int minimumTotal(List<List<Integer>> triangle) {
    if (triangle.size() == 0) return 0;
    int n = triangle.size();
    int[] saved = new int[n];
    List<Integer> tail = triangle.get(n-1);
    for (int i = 0; i < n; i++) {
        saved[i] = tail.get(i);
    }
    for (int i = n-2; i >= 0; i--) {
        tail = triangle.get(i);
        for (int j = 0; j <= i; j++) {
            saved[j] = tail.get(j) + Math.min(saved[j], saved[j+1]);
        }
    }
    return saved[0];
}
```

如上可以使用一个数组保存每一层的解，因为上一层的解依赖于下一层的解，且上一层位置为 j 的解只依赖于下一层位置为 j 和 j+1 的解，所以代码中仅使用一个数组就保存了第 i 和 i+1 层两层中有用的数据，以位置 j 为分割，前 j 个位置保存着已经求算出来的第 i 层的解，后面保存的则是第 i+1 层的解。