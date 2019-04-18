### Question

Given a positive integer *n*, generate a square matrix filled with elements from 1 to *n*2 in spiral order.

**Example:**

```
Input: 3
Output:
[
 [ 1, 2, 3 ],
 [ 8, 9, 4 ],
 [ 7, 6, 5 ]
]
```

### Solution

具体参见 spiral matrix 一题，本题参考代码如下：

```java
public static int[][] generateMatrix(int n) {
    int length = n * n;
    int[][] result = new int[n][n];
    
    int[] MOVE_I = {0, 1, 0, -1};
    int[] MOVE_J = {1, 0, -1, 0};
    int state = 0;
    int pos = 0;
    int i = 0, j = 0;
    
    while (pos++ < length) {
        result[i][j] = pos;
        
        i += MOVE_I[state];
        j += MOVE_J[state];
        if (i >= n || i < 0 || j >= n || j < 0 || result[i][j] != 0) {
            i -= MOVE_I[state];
            j -= MOVE_J[state];
            state = (state + 1) % 4;
            i += MOVE_I[state];
            j += MOVE_J[state];
        }
    }
    return result;
}
```

