Implement `atoi` which converts a string to an integer.

The function first discards as many whitespace characters as necessary until the first non-whitespace character is found. Then, starting from this character, takes an optional initial plus or minus sign followed by as many numerical digits as possible, and interprets them as a numerical value.

The string can contain additional characters after those that form the integral number, which are ignored and have no effect on the behavior of this function.

If the first sequence of non-whitespace characters in str is not a valid integral number, or if no such sequence exists because either str is empty or it contains only whitespace characters, no conversion is performed.

If no valid conversion could be performed, a zero value is returned.

**Note:**

-   Only the space character `' '` is considered as whitespace character.
-   Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. If the numerical value is out of the range of representable values, INT_MAX (231 − 1) or INT_MIN (−231) is returned.

**Example 1:**

```
Input: "42"
Output: 42
```

**Example 2:**

```
Input: "   -42"
Output: -42
Explanation: The first non-whitespace character is '-', which is the minus sign.
             Then take as many numerical digits as possible, which gets 42.
```

**Example 3:**

```
Input: "4193 with words"
Output: 4193
Explanation: Conversion stops at digit '3' as the next character is not a numerical digit.
```

**Example 4:**

```
Input: "words and 987"
Output: 0
Explanation: The first non-whitespace character is 'w', which is not a numerical 
             digit or a +/- sign. Therefore no valid conversion could be performed.
```

**Example 5:**

```
Input: "-91283472332"
Output: -2147483648
Explanation: The number "-91283472332" is out of the range of a 32-bit signed integer.
             Thefore INT_MIN (−231) is returned.
```

### Solution

#### 使用字符串

题目的要求是对字符串处理，根据题目，大致有以下几个要求：

1.  出现在开始的字符串首的空格可忽略不计
2.  如果以数字开始，就开始解析（数字可以包含 + - 符号）
    1.  如果中间某个字符不是数字，就结束解析，返回已解析到的数字
    2.  如果数字超出范围
        1.  为正返回最大整形
        2.  为负返回最小整形
3.  如果以非数字开始，返回 0

根据以上几个条件，我选择使用字符串的方式解析数字，并将数字保存在一个整形数字上。解析的步骤为：

1.  首先删除字符串的首部空格字符

2.  然后判断是否有符号 + 或 - ，然后将其保存起来，默认为正数

3.  之后就是一个循环，逐个字符判断

    1.  如果当前字符不是一个数字，直接跳出循环

        1.  当前是第一个字符

            因为 result 默认是 0 ，所以直接跳出之后 result 依旧是 0

        2.  当前不是第一个字符

            说明前面已经解析了一部分数字，这时返回，result 是已经解析过的数字

    2.  如果当前字符是数字，进入判断

        1.  如果加上当前字符之后超过 Integer 的表示范围

            根据数字的符号，选择返回最大或最小整形

        2.  否则，直接将字符加到 result 上

代码为：

```java
public static int myAtoi(String str) {
    char[] arr = str.trim().toCharArray();
    int l = arr.length;
    int max = Integer.MAX_VALUE / 10;
    if (l == 0) {
        return 0;
    }
    int p = 0;
    int result = 0;
    boolean negative = false;
    if (arr[0] == '+') {
        p++;
    }else if (arr[0] == '-') {
        negative = true;
        p++;
    }
    while (p < l) {
        if (arr[p] >= '0' && arr[p] <= '9') {
            if (result > max) {
                if (negative) {
                    return Integer.MIN_VALUE;
                }else {
                    return Integer.MAX_VALUE;
                }
            }
            if (result == max) {
                if (negative && arr[p] >= '8') {
                    return Integer.MIN_VALUE;
                }else if (!negative && arr[p] >= '7') {
                    return Integer.MAX_VALUE;
                }
            }
            result = result * 10 + (arr[p] - '0');
        }else {
            break;
        }
        p++;
    }
    return negative ? -result : result;
}
```

