### Question

Given *s1*, *s2*, *s3*, find whether *s3* is formed by the interleaving of *s1* and *s2*.

**Example 1:**

```
Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
Output: true
```

**Example 2:**

```
Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
Output: false
```

### Solution

将两个字符串拆开成单个的字符，然后用这些字符组成新的字符串，要求是字符之间的相对位置与在在原字符串中保持一致，如将字符串`ab`、`cd`组成新的字符串，需要保证 a 始终在 b 的前面，c 始终在 d 的前面，题目中是给出了一个字符串，判断这个字符串是否符合以上规则。

将三个字符串分被设为 s1 s2 和 s3，有 s1 的前 i 个字符和 s2 的前 j 个字符组成了 s3 的前 i+j 个字符。以此为前提，下一步就是从 s1 的 i+1 位置或者 s2 的 j+1 位置选择一个字符，放到 s3 的 i+j+1 位置上。也就是对于 s3 来说，每一个位置可以有两个选择，选择 s1 或者 s2 中对应位置的字符。但是有一定的限制条件：首先，要使的 s1 中第 i+1 字符或者 s2 中第 j+1 字符可以被选，他们需要与 s3 中的第 i+j+1 字符相同，然后，当某一个字符串如 s1 中的所有字符已经选完之后，只能选择 s2 中的字符。

按照如上描述，可以得出下面的递归解法：

```java
public static boolean isInterleave(String s1, String s2, String s3) {
    if (s1.length() + s2.length() != s3.length()) return false;
    int[][] saved = new int[s1.length()+1][s2.length()+1];
    return isInterleave(s1, s2, s3, 0, 0, 0, saved);
}
private static boolean isInterleave(String s1, String s2, String s3,
                                    int p1, int p2, int p3, int[][] saved) {
    if (saved[p1][p2] == 1) return true;
    if (saved[p1][p2] == -1) return false;
    if (p3 == s3.length()) return true;
    boolean result = false;
    if (p1 < s1.length() && s1.charAt(p1) == s3.charAt(p3)
            && isInterleave(s1, s2, s3, p1+1, p2, p3+1, saved))
        result = true;
    if (p2 < s2.length() && s2.charAt(p2) == s3.charAt(p3)
            && isInterleave(s1, s2, s3, p1, p2+1, p3+1, saved))
        result =  true;
    saved[p1][p2] = result ? 1 : -1;
    return result;
}
```

递归方法的参数分别是三个字符串和它们的指针，另外，还使用一个二维数组，存储了已经计算了的结果，用作缓存。对于这样一个递归解来说，它会出现重复计算的情况，如对于字符串`ab`和`ac`，可以先选 s1 再选 s2 得到`aa`，也可以先选 s2 再选 s1 得到`aa`，这两种情况选择之后得到的状态是一样的，但是在递归解中，它们都需要进行一次判断，所以使用缓存数组保存已经计算过的结果十分必要。

或者也可以利用动态规划解这个题目，使用一个与上面的递归中功能类似的二维数组保存结果，如利用`dp[i][j]`保存 s1 的前 i 个字符和 s2 的前 j 个字符是否可以组成 s3 的前 i+j 个字符。那么有初始条件`dp[0][0] = true`，而最终的结果就是`dp[m][n]`。那么有，如果 s1 的第 i+1 字符等于 s3 的第 i+j+1 字符，那么如果有`dp[i][j] = true`，就有`dp[i+1][j] = true`，如果 s2 的 j+1 字符等于 s3 的 i+j+1 字符，那么如果有`dp[i][j] = true`，就有`dp[i][j+1] = true`，则：

```java
public static boolean isInterleave2(String s1, String s2, String s3) {
    int l1 = s1.length(), l2 = s2.length(), l3 = s3.length();
    if (l1 + l2 != l3) return false;
    boolean[][] saved = new boolean[l1+1][l2+1];
    saved[0][0] = true;
    for (int i = 0; i <= l1; i++) {
        for (int j = 0; j <= l2; j++) {
            if (i < s1.length() && i+j < s3.length()
                && s1.charAt(i) == s3.charAt(i+j) && saved[i][j]) {
                saved[i+1][j] = true;
            }
            if (j < s2.length() && i+j < s3.length() 
                && s2.charAt(j) == s3.charAt(i+j) && saved[i][j]) {
                saved[i][j+1] = true;
            }
        }
    }
    return saved[l1][l2];
}
```

