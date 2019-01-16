### Question

You are given an *n* x *n* 2D matrix representing an image.

Rotate the image by 90 degrees (clockwise).

**Note:**

You have to rotate the image [**in-place**](https://en.wikipedia.org/wiki/In-place_algorithm), which means you have to modify the input 2D matrix directly. **DO NOT** allocate another 2D matrix and do the rotation.

**Example 1:**

```
Given input matrix = 
[
  [1,2,3],
  [4,5,6],
  [7,8,9]
],

rotate the input matrix in-place such that it becomes:
[
  [7,4,1],
  [8,5,2],
  [9,6,3]
]
```

**Example 2:**

```
Given input matrix =
[
  [ 5, 1, 9,11],
  [ 2, 4, 8,10],
  [13, 3, 6, 7],
  [15,14,12,16]
], 

rotate the input matrix in-place such that it becomes:
[
  [15,13, 2, 5],
  [14, 3, 4, 1],
  [12, 6, 8, 9],
  [16, 7,10,11]
]
```

### Solution

#### S1:映射法

对于旋转一个矩阵来说，观察旋转前后每个位置的变化，就会发现旋转前后某个数字的位置是一个对应的关系，且有映射关系式：

```
before[i][j] = after[j][n-i-1]
```

所以可以利用这个关系式重新映射一个新的矩阵，然后将这个矩阵赋值给初始矩阵即可，这种方法的时间复杂度是$O(n^2)$，空间复杂度也是$O(n^2)$，代码如下：

```java
public static void rotate(int[][] matrix) {
    int n = matrix.length;
    int[][] res = new int[n][n];
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            res[j][n-i-1] = matrix[i][j];
        }
    }
    
    for (int i = 0; i < n; i++) {
        System.arraycopy(res[i], 0, matrix[i], 0, n);
    }
}
```

#### S2:旋转法

第一种方法，可以看作是基于整个矩阵的旋转法，所以需要的辅助空间为 n*n ，进一步，如果能够做到基于更小的单元的旋转法，那么需要的辅助空间就会降低，如果能够找到一个基于常数个数字为单位旋转法，那么空间复杂度就会降为$O(1)$，观察旋转前后的两个二重数组：

```
  [1,2,3],	[7,4,1],
  [4,5,6],	[8,5,2],
  [7,8,9]	[9,6,3]
```

发现这个矩阵的旋转可以拆分成两个子旋转：`[1,3,7,9]`和`[2,6,8,4]`的顺时针旋转，当 n 逐渐上升的时候，子旋转的数量会不断上升，但是子旋转的数字数量始终是 4 ，究其原因，我想可能是因为这是个二维数组吧。有了这个基础之后，我们就可以每次只完成四个数字的旋转，所以需要的辅助空间，无论 n 为多大，都只是常数个了。

通过观察，子旋转数目与 n 的关系为：$\text{子旋转数目} = (n / 2 + n \% 2) * (n / 2)$，算法如下：

```java
public static void rotate(int[][] matrix) {
    int n = matrix.lerength;
    for (int i = 0; i < n / 2 + n % 2; i++) {
        for (int j = 0; j < n / 2; j++) {
            int tmp = matrix[i][j];
            matrix[i][j] = matrix[n-1-j][i];
            matrix[n-1-j][i] = matrix[n-1-i][n-j-1];
            matrix[n-1-i][n-1-j] = matrix[j][n-1-i];
            matrix[j][n-1-i] = tmp;
        }
    }
}
```

置于上面的旋转坐标的计算，其实只需要考虑方法1中的那个关系式就好了，如需要将`[i, j]`移动到`[j, n-1-i]`，那么同理，需要将`[j, n-1-i]`移动到`[n-1-i, n-1-j]`，然后将`[n-1-i, n-1-j]`移动到`[n-1-j, n-1-(n-1-i)] = [n-1-j, i]`，如此便可。

顺便一提，这种旋转法只适合于方阵，对于 n*m 的矩阵来说，旋转法貌似就无能为力了，不过映射法还是能够奏效的。