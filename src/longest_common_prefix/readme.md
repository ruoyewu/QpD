### Question

Write a function to find the longest common prefix string amongst an array of strings.

If there is no common prefix, return an empty string `""`.

**Example 1:**

```
Input: ["flower","flow","flight"]
Output: "fl"
```

**Example 2:**

```
Input: ["dog","racecar","car"]
Output: ""
Explanation: There is no common prefix among the input strings.
```

**Note:**

All given inputs are in lowercase letters `a-z`.

### Solution

#### S1:横向求解-减法求解

要求出一个字符串数组中所有字符的公共前缀，那么可以先假设数组中第一个字符串是所有字符串的前缀，那么当第一个字符串与第二个字符串比较的时候，就可以求出这两个字符串的共同前缀，此时的前缀一般只是第一个字符串的一部分，然后再让前两个字符串的前缀与第三个字符串比较，就可以得到前三个字符串的前缀，以此类推：

```java
public static String longestCommonPrefix(String[] strs) {
    if (strs.length == 0) return "";
    int end = strs[0].length();
    for (int i = 1; i < strs.length; i++) {
        for (int j = 0; j < end; j++) {
            if (j == strs[i].length() || strs[0].charAt(j) != strs[i].charAt(j)) {
                end = j;
            }
        }
    }
    return strs[0].substring(0, end);
}
```

通过一个 end ，保存共同前缀的最大长度。

#### S2:纵向求解-加法求解

或者也可以一点一点判断，如先判断这些字符串的第一个字符是否一致，如果一致，那么再判断第二个字符，直到判断到第 i 个字符的时候发现并不是所有的字符串的第 i 个字符都是相同的，那么此时就可以得到最长公共前缀：

```java
public static String longestCommonPrefix2(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    if (strs.length == 1) return strs[0];
    int end = 0;
    while (end < strs[0].length()) {
        for (int i = 1; i < strs.length; i++) {
            if (end >= strs[i].length() || strs[0].charAt(end) != strs[i].charAt(end)) {
                return strs[0].substring(0, end);
            }
        }
        end++;
    }
    return strs[0].substring(0, end);
}
```

#### S3:二分求解

上述两种方式都是假定一个前缀，然后求所有的字符串是否满足，或者也可以换一种方法，如先求前两个字符串的前缀并保存，再求之后两个字符串的前缀，按照这样求下去，原来的长度为 n 的字符串就变成了长度为 (n+1)/2 的新的字符串，然后再利用相同的方法求解，直到 n 变为 1，那么此时这个唯一的字符串就是所有字符串的公共前缀：

```java
public static String longestCommonPrefix3(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    int n = strs.length, pos;
    while (n > 1) {
        pos = 0;
        for (int i = 0; i < n; i += 2) {
            if (i < n-1) {
                String left = strs[i], right = strs[i+1];
                int end = 0;
                while (end < left.length() && end < right.length() &&
                        left.charAt(end) == right.charAt(end)) end++;
                strs[pos++] = left.substring(0, end);
            } else {
                strs[pos++] = strs[i];
            }
        }
        n = pos;
    }
    return strs[0];
}
```

