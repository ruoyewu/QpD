### Question

Given *n*, how many structurally unique **BST's** (binary search trees) that store values 1 ... *n*?

**Example:**

```
Input: 3
Output: 5
Explanation:
Given n = 3, there are a total of 5 unique BST's:

   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3
```

### Solution

#### S1:动态规划

求一个结点数固定的二叉树的形状的个数，并且这还是个排序二叉树。对于一个排序二叉树，我们知道，某个节点左子树上的结点值必然都小于这个点，其右子树上的结点值都大于这个点，所以，对于一个结点数为 n 的二叉排序树而言，如果它的左子树上的结点数为 i ，那么右子树上的结点数必然是 n-i-1 （总数-根结点-左子树），那么就得到：当左子树结点个数为 i 的时候，结点数为 i 的二叉排序树形状数与结点数为 n-i-1 的二叉排序树形状数的乘积，便是结点数为 n ，左子树为 i 时的总的形状数，那么将所有的左子树结点数的可能值都算进去，就得到结点数为 n 的二叉排序树的形状数。这就将一个结点数为 n 的问题化成了若干个结点数小于 n 的问题，而且显而易见：结点数为 0 时，只有一种形状（空树）；结点数为 1 时，只有一种形状。那么便能够得到之后的所有结点的递推式：

```java
public static int numTrees(int n) {
    int[] trees = new int[n+1];
    trees[0] = 1;
    trees[1] = 1;
    for (int i = 2; i <= n; i++) {
        for (int j = 0; j < i; j++) {
            trees[i] += trees[j] * trees[i-1-j];
        }
    }
    return trees[n];
}
```

此时时间复杂度是$O(n^2)$，空间复杂度为$O(n)$。

#### S2:数学公式法

对于这样一种数列，有一个名字，叫做**卡特兰数**，除了使用递推公式计算之外，它有一个计算公式，可以比较方便地求算，详细的可以见[百度词条](https://baike.baidu.com/item/卡特兰数/6125746?fr=aladdin#4)，公式的话就是：
$$
C_0 = 1;
C_{n+1} = \frac{2(2n+1)}{n+2}C_n
$$
代码为：

```java
public static int numTrees(int n) {
    long tree = 1;
    for (int i = 0; i < n; i++) {
        tree = tree * 2 * (2 * i + 1) / (i + 2);
    }
    return (int) tree;
}
```

时间复杂度为$O(n)$，空间复杂度$O(1)$。