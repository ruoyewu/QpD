### Question

Determine whether an integer is a palindrome. An integer is a palindrome when it reads the same backward as forward.

**Example 1:**

```
Input: 121
Output: true
```

**Example 2:**

```
Input: -121
Output: false
Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
```

**Example 3:**

```
Input: 10
Output: false
Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
```

**Follow up:**

Coud you solve it without converting the integer to a string?

### Solution

#### S1:转换成字符串

要求一个数字是否是回文数，可以将其转化成字符串，再判断字符串是否回文即可。如下：

```java
public static boolean isPalindrome(int x) {
    if (x < 0) return false;
    String s = String.valueOf(x);
    int n = s.length();
    for (int i = 0; i < n/2; i++) {
        if (s.charAt(i) != s.charAt(n-i-1)) {
            return false;
        }
    }
    return true;
}
```

可以确定一个负数绝对不是回文数。上面这种方法使用了`String.valueOf(int)`方法，也可以不使用相关的字符串转换方法，而是直接将其转化为数组：

```java
public static boolean isPalindrome(int x) {
    if (x < 0) return false;
    int[] list = new int[32];
    int pos = 0;
    while (x > 0) {
        list[pos++] = x % 10;
        x /= 10;
    }
    for (int i = 0; i < pos / 2; i++) {
        if (list[i] != list[pos-1-i]) {
            return false;
        }
    }
    return true;
}
```

#### S2:直接求逆置值

或者是不作转变，直接求一个数字倒置之后的数字是否与原数字相同：

```java
public static boolean isPalindrome(int x) {
    if (x < 0) return false;
    int revert = 0, tmp = x;
    while (tmp > 0) {
        revert = revert * 10 + tmp % 10;
        tmp /= 10;
    }
    return revert == x;
}
```

或者也可以做一些计算量上的简化，将原本的数字拆分成两部分，其中一部分取逆，那么只要这两者相同，那这就是一个回文数，如例 1221 ，将其分成两部分 12 21 ，再将其中一个取逆得 12 12 ，则确定二者相同，原数字是回文数，或者对于奇数数字 121 ，分成两部分 1 21 ，取逆得 1 12 ，此时以数字少的那一部分为准，得到这两部分也是相同的，所以原数字也是回文数。此时代码为：

```java
public static boolean isPalindrome(int x) {
    if (x < 0 || (x % 10 == 0 && x != 0)) return false;
    int revert = 0;
    while (x > revert) {
        revert = revert * 10 + x % 10;
        x /= 10;
    }
    return x == revert || x == revert / 10;
}
```

如果一个数字最后一位是 0 ，而这个数字本身非 0 ，那么这个数必然不是回文数（非 0 数字的第一位必然不是 0 ）。