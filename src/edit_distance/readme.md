### Question

Given two words *word1* and *word2*, find the minimum number of operations required to convert *word1*to *word2*.

You have the following 3 operations permitted on a word:

1.  Insert a character
2.  Delete a character
3.  Replace a character

**Example 1:**

```
Input: word1 = "horse", word2 = "ros"
Output: 3
Explanation: 
horse -> rorse (replace 'h' with 'r')
rorse -> rose (remove 'r')
rose -> ros (remove 'e')
```

**Example 2:**

```
Input: word1 = "intention", word2 = "execution"
Output: 5
Explanation: 
intention -> inention (remove 't')
inention -> enention (replace 'i' with 'e')
enention -> exention (replace 'n' with 'x')
exention -> exection (replace 'n' with 'c')
exection -> execution (insert 'u')
```

### Solution

#### S1:递归法

题中给出对于第一个字符串，如果两个字符串对应位置不相同，有三种操作：添加、删除和替换，如果当前位置相同，则还可以直接消去。

如下例子，我们若是要求 word1 和 word2 的解，有这么几种情况：

```
// 添加
word1 = "bc", word2 = "c" => word1 = "cbc" => word1 = "bc", word2 = "" => word2 后移一位
// 删除
word1 = "bc", word2 = "c" => word1 = "c" => word1 = "", word2 = "" => word1 后移一位
// 替换
word1 = "bc", word2 = "c" => word1 = "cc" => word1 = "c", word2 = "" => 均后移一位
// 消去
word1 = "cb", word2 = "c" => word1 = "b", word2 = "" => 均后移一位
```

所以，对于任何两个字符串，我们都可以将其依次采用上述方法试一遍，选择最小的那一个作为当前两个字符串的解即可。如下代码：

```java
public static int minDistance(String word1, String word2) {
    int[][] saved = new int[word1.length()][word2.length()];
    for (int i = 0; i < word1.length(); i++) {
        Arrays.fill(saved[i], -1);
    }
    return min(word1.toCharArray(), word2.toCharArray(), 0, 0, 0, saved);
}
public static int min(char[] w1, char[] w2, int s1, int s2, int cur, int[][] saved) {
    int min;
    if (s2 == w2.length) {
        return cur + w1.length - s1;
    }
    if (s1 == w1.length) {
        return cur + w2.length - s2;
    }
    if (saved[s1][s2] >= 0) {
        return saved[s1][s2] + cur;
    }
    if (w1[s1] == w2[s2]) {
        min = min(w1, w2, s1+1, s2+1, cur, saved);
    } else {
        // delete
        int m1 = min(w1, w2, s1+1, s2, cur+1, saved);
        // replace
        int m2 = min(w1, w2, s1+1, s2+1, cur+1, saved);
        // insert
        int m3 = min(w1, w2, s1, s2+1, cur+1, saved);
        min = Math.min(Math.min(m1, m2), m3);
    }
    saved[s1][s2] = min-cur;
    return min;
}
```

上述代码中，用 s1 记录当前子字符串1 的起始位置，s2 记录子字符串 2 的起始位置，cur 记录使得初始的字符串变成现在的字符串所需的最少变化，saved 保存从当前位置的两个子字符串消去所需的最少变化。

另外，在这种题目求解中使用递归计算的时候，不得不考虑的一个问题是，对于同一对字符串的反复计算，必然会导致数据量稍大的时候就会运行超时，此时一种解决方法就是额外使用一个数据保存已经求过解的位置的解，那么当再次到达这个位置的时候不需要重复计算而只需要读取数组即可。在之前的题目中，也有很多递归解题的方法是使用这种方法解决重复计算问题的。

#### S2:动态规划

上面的递归法，主要是将问题往后细分，我们要想求前面的位置的解，要先求得这个位置之后的解，也就是从当前位置为起点的子字符串的解，动态规划则相反，某一位置的解由这个位置之前的解决定。

如下，若是已知字符串 A 和 B 的解，那么：

```
// 消去
word1 = "Aa", word2 = "Ba" => word1 = "A", word2 = "B" => 值与 [A, B] 相同
// 添加
word1 = "A", word2 = "Bb" => word1 = "Ab", word2 = "Bb" => word1 = "A", word2 = "B" => 值为 [A, B] + 1
// 删除
word1 = "Aa", word2 = "B" => word1 = "A", word2 = "B" => 值为 [A, B] + 1
// 替换
word1 = "Aa", word2 = "Bb" => word1 = "Ab", word2 = "Bb" => 值为 [A, B] + 1
```

所以对于任一位置而言，它也可能是由之前位置通过某种变换得来的，取其中最小的，便是当前字符串的解。代码：

```java
public static int minDistance2(String word1, String word2) {
    int l1 = word1.length(), l2 = word2.length();
    char[] c1 = word1.toCharArray(), c2 = word2.toCharArray();
    int[][] result = new int[l1+1][l2+1];
    for (int i = 0; i <= l1; i++) {
        result[i][0] = i;
    }
    for (int i = 0; i <= l2; i++) {
        result[0][i] = i;
    }
    for (int i = 1; i <= l1; i++) {
        for (int j = 1; j <= l2; j++) {
            int min = Integer.MAX_VALUE;
            if (c1[i-1] == c2[j-1]) {
                min = result[i-1][j-1];
            }
            // insert
            int m1 = 1 + result[i-1][j];
            // delete
            int m2 = 1 + result[i][j-1];
            // replace
            int m3 = 1 + result[i-1][j-1];
            result[i][j] = Math.min(Math.min(min, m1), Math.min(m2, m3));
        }
    }
    return result[l1][l2];
}
```

此时时间复杂度和空间复杂度都是$O(mn)$。