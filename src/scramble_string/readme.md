### Question

Given a string *s1*, we may represent it as a binary tree by partitioning it to two non-empty substrings recursively.

Below is one possible representation of *s1* = `"great"`:

```
    great
   /    \
  gr    eat
 / \    /  \
g   r  e   at
           / \
          a   t
```

To scramble the string, we may choose any non-leaf node and swap its two children.

For example, if we choose the node `"gr"` and swap its two children, it produces a scrambled string `"rgeat"`.

```
    rgeat
   /    \
  rg    eat
 / \    /  \
r   g  e   at
           / \
          a   t
```

We say that `"rgeat"` is a scrambled string of `"great"`.

Similarly, if we continue to swap the children of nodes `"eat"` and `"at"`, it produces a scrambled string `"rgtae"`.

```
    rgtae
   /    \
  rg    tae
 / \    /  \
r   g  ta  e
       / \
      t   a
```

We say that `"rgtae"` is a scrambled string of `"great"`.

Given two strings *s1* and *s2* of the same length, determine if *s2* is a scrambled string of *s1*.

**Example 1:**

```
Input: s1 = "great", s2 = "rgeat"
Output: true
```

**Example 2:**

```
Input: s1 = "abcde", s2 = "caebd"
Output: false
```

### Solution

题中规定了一种字符串的递归变换，先将字符串分割成两个非空子串，两个子串也可再次进行变换，它们变换之后的结果组合在一起得到一个新的字符串，这个字符串就是原字符串的一个变换。

题目要求给定的两个字符串，是否可以将第一个字符串通过这种变换，得到第二个字符串。

根据上面的描述，可以确定一个字符串的变换过程分成两个阶段，第一，将原字符串分割成两个子字符串，第二，进行两个子字符串的变换。在字符串变换的过程中，只是字符之间的顺序发生了一些变化，而总的字符的种类数和个数是不变的。所以，两个字符串满足这个变换的首要条件是，它们包含的字符是相同的。

又因为这是一个递归的变换过程，判断的过程也应该是递归的。当判断两个字符串满足上面的第一条件之后，需要将两个字符串分别分割成两个子串，然后再判断两个子串是否满足变换。

关于分割，可以一个一个试验，如将这两个长度为 n 的字符串分割成长度分别为 1 和 n-1 的两个子串，然后判断对应的子串是否满足变换。分割的话一定是在字符串中选择一个位置，然后将左右两边各成一个子串，这里有两种情况，可以选择分割点为 1 ，也可以选择分割点为 n-1 ，即字符串`[1,...,n]`可以被分割成`[1],[2,...,n]`和`[1,...,n-1],[n]`，这两种情况都应该判断一下。

代码如下：

```java
public static boolean isScramble(String s1, String s2) {
    if (s1.length() != s2.length()) return false;
    if (s1.isEmpty() || s1.equals(s2)) return true;
    int len = s1.length();
    int count = 0;
    int[] saved = new int[128];
    // 正序
    for (int i = 0; i < len-1; i++) {
        if (++saved[s1.charAt(i)] == 0) count--;
        else if (saved[s1.charAt(i)] == 1) count++;
        if (--saved[s2.charAt(i)] == 0) count--;
        else if (saved[s2.charAt(i)] == -1) count++;
        if (count == 0) {
            if (isScramble(s1.substring(0, i+1), s2.substring(0, i+1)) &&
                    isScramble(s1.substring(i+1), s2.substring(i+1))) {
                return true;
            }
            break;
        }
    }
    if (count != 0) {
        if (++saved[s1.charAt(len-1)] == 0) count--;
        if (--saved[s2.charAt(len-1)] == 0) count--;
    }
    if (count != 0) return false;
    // 逆序
    for (int i = 0; i < len-1; i++) {
        if (++saved[s1.charAt(i)] == 0) count--;
        else if (saved[s1.charAt(i)] == 1) count++;
        if (--saved[s2.charAt(len-i-1)] == 0) count--;
        else if (saved[s2.charAt(len-i-1)] == -1) count++;
        if (count == 0) {
            if (isScramble(s1.substring(0, i+1), s2.substring(len-i-1)) &&
                    isScramble(s1.substring(i+1), s2.substring(0, len-i-1))) {
                return true;
            }
            break;
        }
    }
    return false;
}
```

使用 saved 保存当前各字符的出现数量，count 两个字符串矛盾的情况，即 saved 中非 0 的字符数量，使用 for 循环遍历分割点，没出现在 s1 中的字符会让 saved 中对应位置 +1 ，出现在 s2 中的字符会让 saved 对应位置 -1 ，所以当在某个分割点 i 的时候有 count 为 0 ，意味着此时 s1 的前 i 个字符组成的子串，与 s2 的前 i 字符组成子串满足了变换的首要条件，然后就继续跟进判断由 i 分割成的两对子串是否满足变换。

上面有两个 for 循环，对应着两种分割方式，假设分割点为 i ，前一种分割方式的对应规则为`s1[1,...,i] -> s2[1,...,i]`，后一种对应的规则为`s1[1,...,i] -> s2[n-i+1,...,n]`。

还有一点，中间有一个句对 count 值的判断。这个判断执行在判断完第一种分割方式之后，此时有两种情况，第一种为在上面找到了可以初步满足变换条件的一种子串，但是调用`isScramble()`判断发现不能完成变换。此时 count  0 ，且 saved 中所有位置也都是 0 ，是一种初始化的状态，可以直接进行下一种方式的判断。

当 count 不等于 0 的时候，意味着在第一个 for 循环中没有找到满足条件的子串，此时 i 的位置为 n-1 ，然后将两个字符串的最后一个字符也判断一下，如果 count 此时还不为 0 ，意味着两个字符串的内容（字符种类及数量）不同，那么它们必然不满足变换的条件，所以直接返回 false 。