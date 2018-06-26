Given an input string (`s`) and a pattern (`p`), implement regular expression matching with support for `'.'` and `'*'`.

```
'.' Matches any single character.
'*' Matches zero or more of the preceding element.
```

The matching should cover the **entire** input string (not partial).

**Note:**

-   `s` could be empty and contains only lowercase letters `a-z`.
-   `p` could be empty and contains only lowercase letters `a-z`, and characters like `.` or `*`.

**Example 1:**

```
Input:
s = "aa"
p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".
```

**Example 2:**

```
Input:
s = "aa"
p = "a*"
Output: true
Explanation: '*' means zero or more of the precedeng element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
```

**Example 3:**

```
Input:
s = "ab"
p = ".*"
Output: true
Explanation: ".*" means "zero or more (*) of any character (.)".
```

**Example 4:**

```
Input:
s = "aab"
p = "c*a*b"
Output: true
Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore it matches "aab".
```

**Example 5:**

```
Input:
s = "mississippi"
p = "mis*is*p*."
Output: false
```

### Solution

#### 递归

递归的思想为，将一个比较难判断的问题变成多个较容易判读的问题，对于本题而言，有三种匹配情况：

1.  直接匹配，即`s = "abc" p = "abc"`
2.  . 匹配，如`s = "abc" p = "a.c"`
3.  \* 匹配，如`s = "aac" p = "a*c"`

而直接匹配与 . 匹配都是一对一匹配的，只需要判断对应位置是否能匹配就可以了，但是对于 \* 匹配而言，因为它必须要有一个前置条件，且能够循环`0 ~ any`个前置字符，所以判断起来是比较困难的，所以采用递归的方法，就是旨在将 \* 匹配简单化。

所以就有了如下的判断步骤：

1.  判断 s 和 p 的第一个字符是否匹配

    因为对于这三种匹配来说，前两种匹配可以确定是否匹配，再看第三种匹配，因为它也需要一个前置字符，且只能是这个前置字符的循环，所以也需要先判断 p 和 s 第一个字符是否匹配

2.  如果当前的 p 是`x*`开始的，那么，它能够匹配的字符串是`"" or "x..."`开始的，即对应的为 0 次循环和 n(n > 0) 次循环：

    1.  0 次循环

        对于这种情况，即直接等价于 p 去除前面的`x*`字符。

    2.  n 次循环

        对于这种情况，等价于 s 去除前面的`x`字符，当然前提是 s 要以`x`开头。对于这种问题，有多种情况，如 s 以`xx`开头和以`xy`开头，显然，对于这种情况，有`n = 1` ，所以，s 去掉开始的这个`x`字符后再与 p 进行匹配，匹配结果不变，如 s 去掉`x`之后有`s = "x.." 或 "y.." p = "x*..."`，这时对于前者还有 n = 1， 对于后者有 n = 0，即又回到当前的这种判断。

3.  如果 p 不是以`x*`开始，即有任意的一个`y != x`，由`xy`或者`.`开头，那么就直接判断 s 的第一个字符是否可以与`x`或`.`匹配即可，这一步上面的已经判断过了，如果匹配，s 和 p 都删除第一个字符，进行接下来的判断即可。

一次判断有如上步骤，经过若干次上述判断之后，最后会出现两种情况：

1.  s 与 p 第一个字符不能匹配

    此时有结果匹配失败。

2.  s 与 p 都变成空串

    这意味着 s 与 p 能够完成匹配，即匹配成功。

代码如下：

```java
public static boolean isMatch(String s, String p) {
    if (p.isEmpty()) {
        return s.isEmpty();
    }
    boolean firstMatch = !s.isEmpty() &&
            (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.');
    if (p.length() >= 2 && p.charAt(1) == '*') {
        return isMatch(s, p.substring(2)) ||
                (firstMatch && isMatch(s.substring(1), p));
    }else {
        return firstMatch && isMatch(s.substring(1), p.substring(1));
    }
}
```

#### 动态规划

使用动态规划的方法解决问题的时候，首先要明确一点，即对于给定的 s 和 p 来说，可以定义一个状态$F_{ij}$为 s 和 p 的子字符串`s[i:]`和`p[j:]`是否能够匹配，那么对于任意的`0 <= i <= len(s)`和`0 <= j <= len(p)`来说，$F_{ij}$都是一个固定值。于是就有动态规划的状态转移方程：

1.  当前的子串`p[j:]`以`X*`开头

    1.  `s[i] == p[j]`

        `F[i][j] = F[i+1][j]`，参照递归方法中的分类，如果两个子字符串第一个字符相同，且匹配子字符串是以`X*`开头，就可以将其看作`XX*`，于是二者共同省去第一个字符，得到二者是否匹配取决于`s[i+1:]`与`p[j:]`这两个子字符串是否匹配，即$F_{i+1j}$的值。

    2.  `s[i] != p[j]`

        `F[i][j] = F[i][j+2]`，这里因为二者首字符不匹配，所以`X*`的 n 为 0，即`p[j:] = p[j+2:]`，于是有$F_{ij} = F_{ij+2}$。

2.  当前子串`p[j:]`不是以`X*`开头

    1.  `s[i] == p[j]`

        `F[i][j] = F[i+1][p+1]`，二者首字符匹配，则直接约去第一个字符。

    2.  `s[i] != p[j]`

        `F[i][j] = False`，不匹配。

所以由上可知，对于某一个`i`和`j`的$F_{ij}$来说，取决于比它们更大的`i`和`j`的$F_{ij}$的取值，故应该从后往前判断，且有初值$F_{len(s)len(p)} = True$，因为这是 s 和 p 的子字符串为空串。于是可以得到代码：

```java
public static boolean isMatch(String s, String p) {
    boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
    dp[s.length()][p.length()] = true;
    for (int i = s.length(); i >= 0; i--) {
        for (int j = p.length() - 1; j >= 0; j--) {
            boolean isMatch = i < s.length() && (p.charAt(j) == s.charAt(i)
                    || p.charAt(j) == '.');
            if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
                dp[i][j] = dp[i][j + 2] || isMatch && dp[i + 1][j];
            }else {
                dp[i][j] = isMatch && dp[i+1][j+1];
            }
        }
    }
    return dp[0][0];
}
```

