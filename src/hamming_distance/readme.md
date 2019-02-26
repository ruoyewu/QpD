### Question

The [Hamming distance](https://en.wikipedia.org/wiki/Hamming_distance) between two integers is the number of positions at which the corresponding bits are different.

Given two integers `x` and `y`, calculate the Hamming distance.

**Note:**
0 ≤ `x`, `y` < 231.

**Example:**

```
Input: x = 1, y = 4

Output: 2

Explanation:
1   (0 0 0 1)
4   (0 1 0 0)
       ↑   ↑

The above arrows point to positions where the corresponding bits are different.
```

### Solution

对于任意两个正整数，求其海明距离，海明距离就是两个数的二进制表示中，对应位置不相同的个数。

#### S1:先转换成字符串表示

对于任意两个数字，可以先求出它们的字符串表示的二进制，然后从底位开始依次判断：

```java
public static int hammingDistance(int x, int y) { 
    if (x < y) return hammingDistance(y, x);
    String sx = Integer.toString(x, 2), sy = Integer.toString(y, 2);
    int i = sx.length()-1, j = sy.length()-1;
    int distance = 0;
    while (i >= 0 && j >= 0) {
        if (sx.charAt(i) != sy.charAt(j)) {
            distance++;
        }
        i--;
        j--;
    }
    while (i >= 0) {
        if (sx.charAt(i) == '1') {
            distance++;
        }
        i--;
    }
    return distance;
}
```

#### S2:直接求

对于两个数的二进制表示中对应位置是否相同，也可以直接使用 & 运算符与对应位置求出某个位置是否为 1 ，然后再比较两个数的相同位置：

```java
public static int hammingDistance(int x, int y) {
    if (x < y) return hammingDistance(y, x);
    int i = 1;
    int count = 0;
    while (i > 0 && i <= x) {
        if ((x & i) != (y & i)) {
            count++;
        }
        i <<= 1;
    }
    return count;
}
```

#### S3:先求异或

对于求两个数字对应位置是否相同，Java 语言也提供了一个运算符 ^ ，异或，直接计算，得到的结果中，二进制表示为 1 的位置就是它们不相同的位置，然后再计算这个结果中 1 的个数即可：

```java
public static int hammingDistance(int x, int y) {
    int count = 0, c = x ^ y;
    while (c != 0) {
        count += c & 1;
        c >>= 1;
    }
    return count;
}
```

