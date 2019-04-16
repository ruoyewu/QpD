### Question

Given a matrix of *m* x *n* elements (*m* rows, *n* columns), return all elements of the matrix in spiral order.

**Example 1:**

```
Input:
[
 [ 1, 2, 3 ],
 [ 4, 5, 6 ],
 [ 7, 8, 9 ]
]
Output: [1,2,3,6,9,8,7,4,5]
```

**Example 2:**

```
Input:
[
  [1, 2, 3, 4],
  [5, 6, 7, 8],
  [9,10,11,12]
]
Output: [1,2,3,4,8,12,11,10,9,5,6,7]
```

### Solution

螺旋取出一个数组中所有的数，并将其加入链表中，本题的重点就是如何实现这样一个螺旋状态。简单分析之后可以得到，完成螺旋需要四个状态，即右、下、左、上，每种状态下沿着一个方向移动，当一种状态移动到终点的时候就会切换成下一种状态，在整个螺旋的过程中需要在四种状态中循环切换，并会始终按照右、下、左、上、右这样一种方式循环。

使用一个状态变量保存当前的状态，然后在每一个状态下判断可行性（是否到达边界），并且随着螺旋取数的进行，边界会越来越小，最终收缩到没有可用数字便可结束螺旋。所以还需要保存当前螺旋取数的边界（来保证已经取到的数不会再次取到）。代码如下：

```java
public static List<Integer> spiralOrder(int[][] matrix) {
    if (matrix.length == 0 || matrix[0].length == 0) {
        return Collections.emptyList();
    }
    int m = matrix.length, n = matrix[0].length;
    int state = 1;
    int startX = 0, endX = n-1;
    int startY = 1, endY = m-1;
    int len = m * n;
    List<Integer> result = new ArrayList<>(len);
    int i = 0, j = 0;
    int pos = 0;
    while (pos++ < len) {
        result.add(matrix[i][j]);
        switch (state) {
            case 1:
                if (j == endX) {
                    i++;
                    endX--;
                    state = 2;
                } else {
                    j++;
                }
                break;
            case 2:
                if (i == endY) {
                    j--;
                    endY--;
                    state = 3;
                } else {
                    i++;
                }
                break;
            case 3:
                if (j == startX) {
                    i--;
                    startX++;
                    state = 4;
                } else {
                    j--;
                }
                break;
            case 4:
                if (i == startY) {
                    j++;
                    startY++;
                    state = 1;
                } else {
                    i--;
                }
                break;
        }
    }
    return result;
}
```

这种代码略显啰嗦，主要原因就是内部含有四个 switch 判断，它们有着相似但不相同的逻辑处理，所以需要分别处理，但它们有一个致命的破绽，那就是状态是循环的，这就使得把它们合到一起成为可能。

可以设置一个状态数组，让状态按照这个数组不断循环，每当一个状态走到边界，就切换到下一个状态。如果要使这种思想可行，首先要解决的是边界如何确定，再上一种方法中，不同状态下边界的判断条件是不同的，并且每次到达边界之后都需要修改当前的边界值，因为四种状态的判断、操作各不相同，所以需要换一种方式来确定边界。

首先，在矩阵之外的数字肯定是不能选的（不存在），其次，一个已经被取到的数一定是不能选的，以上两种构成了边界的判断，这样一种转换，使得在上一种方法中每种状态的单独判断变成了一种模糊的、对每一种状态适用的判断，这一点也支持了合并的思想。

那么现在还有一个问题，如何判断某个位置的数字是否已经被取过？一般有两种方法，第一，额外声明一个二维数组，用于保存每一个人位置的数字的选取情况，第二，将已经被取过的位置的数字置空，但是这是一个 int 数组，不能为空，可以采取一种折中的办法，比如将被取过的位置置为 Integer.MIN_VALUE ，但是这有一种风险，如果原本这个数组中就存在 Integer.MIN_VALUE 这个数字就会使程序运行失败。代码如下：

```java
public static List<Integer> spiralOrder(int[][] matrix) {
    if (matrix.length == 0 || matrix[0].length == 0) {
        return Collections.emptyList();
    }
    int[] MOVE_I = {0, 1, 0, -1};
    int[] MOVE_J = {1, 0, -1, 0};
    int m = matrix.length, n = matrix[0].length, len = m * n;
    int state = 0, i = 0, j = 0, pos = 0;
    List<Integer> result = new ArrayList<>(len);
    while (pos++ < len) {
        result.add(matrix[i][j]);
        matrix[i][j] = Integer.MIN_VALUE;
        i += MOVE_I[state];
        j += MOVE_J[state];
        if (i >= m || i < 0 || j >= n || j < 0 || matrix[i][j] == Integer.MIN_VALUE) {
            // 回退
            i -= MOVE_I[state];
            j -= MOVE_J[state];
            // 重新步进
            state = (state + 1) % 4;
            i += MOVE_I[state];
            j += MOVE_J[state];
        }
    }
    return result;
}
```

