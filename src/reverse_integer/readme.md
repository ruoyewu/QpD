Given a 32-bit signed integer, reverse digits of an integer.

**Example 1:**

```
Input: 123
Output: 321
```

**Example 2:**

```
Input: -123
Output: -321
```

**Example 3:**

```
Input: 120
Output: 21
```

**Note:**
Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.

### Solution

该题可以有两种解答方式：

1.  字符串
2.  取模

#### 字符串

将一个整形倒置，就其形状而言，可以看作是将一个数字字符串倒置，由此便可以得出一种解决方法，即先将整形 num 转化为一个字符串 A ，然后将字符串中前后两个位置`i`和`len(A) - i - 1`的字符交换即可，此时只需要遍历`len(A) / 2`次，完了之后只要再将字符串转换成整形就 Ok 了。

使用这种方法的时候需要注意一点，因为整形的符号不参与倒置，所以可以采用单独保存整形的符号的方式，将所有的数字都变成正数然后倒置完成之后再把符号添加上去。

还有一点，因为题目限制整形为 32 位的数字，所以对于数字`x^31 - 1 = 2147483647`来说，它的倒置是`7463847412`显然超出表示范围，这种情况的解决方法是就是，在字符串转整形的代码上`try`到异常然后返回 0 ，代码如下：

```java
public static int reverse(int x) {
    boolean negative = x < 0;
    if (negative) {
        x = -x;
    }
    int result;
    char[] origin = String.valueOf(x).toCharArray();
    int length = origin.length;
    int max = length / 2;
    for (int i = 0; i < max; i++) {
        char t = origin[i];
        origin[i] = origin[length - 1 - i];
        origin[length - 1 - i] = t;
    }
    try {
        result = Integer.valueOf(new String(origin));
    } catch (NumberFormatException ignored) {
        return 0;
    }
    return negative ? -result : result;
}
```

#### 取模

另一种方法就是直接对整形以取模的方式倒置，为了判断上的方便，仍然可以先将数字的符号保存下来，只计算正数。然后每次使用`rem = num % 10`对方式取出数字的最后一位，然后放到另一个数字上。

这种方式里，因为是通过对整形进行操作，一位一位地得到结果，所以就可以直接对结果进行判断以确保结果的范围：

```java
public static int reverse(int x) {
    boolean negative = x < 0;
    if (negative) {
        x = -x;
    }
    int result = 0;
    int maxNum = Integer.MAX_VALUE / 10;
    while (x > 0) {
        int rem = x % 10;
        x /= 10;
        if (result > maxNum) {
            return 0;
        }
        result = result * 10 + rem;
    }
    return negative ? -result : result;
}
```

如上，每次都会判断 result 是否大于 maxNum ，当这个不等式成立的时候，必然有`result * 10 > Integer.MAX_VALUE`成立，所以直接返回 0 。