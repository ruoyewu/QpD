### Question

Given a string, your task is to count how many palindromic substrings in this string.

The substrings with different start indexes or end indexes are counted as different substrings even they consist of same characters.

**Example 1:**

```
Input: "abc"
Output: 3
Explanation: Three palindromic strings: "a", "b", "c".
```

 

**Example 2:**

```
Input: "aaa"
Output: 6
Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".
```

 

**Note:**

1.  The input string length won't exceed 1000.

### Solution

#### S1:二重循环

要求出一个数组中所有的回环子数组，而一个回环数组有两个位置确定：起始位置也结束位置。所以一种求解本题的方法是，构建一个二重循环，分别对应起始位置和结束位置的循环，然后判断每个子数组是否满足条件即可。

```java
public static int countSubstrings(String s) {
    int count = 0, n = s.length();
    boolean[][] saved = new boolean[n][n];
    for (int right = 0; right < n; right++) {
        saved[right][right] = true;
        for (int left = right - 1; left >= 0; left--) {
            if (s.charAt(right) == s.charAt(left)) {
                if (left == right-1 || saved[left+1][right-1]) {
                    saved[left][right] = true;
                    count++;
                }
            }
        }
    }
    return count + n;
}
```

如上，两个循环分别对应子数组的 left 和 right 位置，而一个数组`[left,...,right]`是回环数组的条件为：

1.  left 与 right 位置的字符相同
2.  数组`[left+1,...,right-1]`是一个回环数组

上述就是根据这个条件判断的。

另外，上面的代码判断`[left+1,...,right-1]`是否是回环数组是通过一个 saved 数组判断的，`saved[i][j]`的值就是子数组`[i,...,j]`是否是个回环数组。并且关于 left 和 right 的循环，是 right 递增，left 递减的，这样的原因就是为了使得我们通过 saved 数组判断某个子数组`[left, right]`是否是一个回环数组的时候，对应的`[left+1, right-1]`是否是回环数组一定是已经求出来的。所以也就是说，求 left 需要先知道 left+1，求 right 需要先知道 right-1 ，所以有了 left 递减遍历、right 递增遍历。

### S2:中间延伸

或者也可以换一种想法，对于一个回环数组来说，它一定是一个对称的数组，所以，如果从这个数组的中心开始延伸，那么左右两边对应位置的字符一定是相同的，并且每个回环数组的中心一定是在数组中。所以，便可以利用这个特点，通过遍历回环数组中心的位置来求解所有的回环子数组：

```java
public static int countSubstrings(String s) {
    int count = 0;
    for (int i = 0; i < s.length(); i++) {
        count++;
        count += count(i-1, i+1, s);
        count += count(i, i+1, s);
    }
    return count;
}
private static int count(int left, int right, String s) {
    int count = 0;
    while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
        count++;
        left--;
        right++;
    }
    return count;
}
```

如上，`countSubstrings()`函数里面，有一个 i=0～s.length() 的循环，便是对回环子数组中心位置的遍历。一个回环子数组的个数可以分为奇数和偶数，所以对于一个中心位置在不同的数组中也有不同的表现：

1.  奇数：从中心位置的左右两边开始
2.  偶数：它的中心不是一个位置，而是中间两个位置的中间，所以从最中间的两个位置开始

而`count()`函数就是以某一个中心开始延伸，判断以这个位置为中心的回环子数组的个数。