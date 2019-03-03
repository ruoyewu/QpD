### Question

Roman numerals are represented by seven different symbols: `I`, `V`, `X`, `L`, `C`, `D` and `M`.

```
Symbol       Value
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
```

For example, two is written as `II` in Roman numeral, just two one's added together. Twelve is written as, `XII`, which is simply `X` + `II`. The number twenty seven is written as `XXVII`, which is `XX` + `V`+ `II`.

Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not `IIII`. Instead, the number four is written as `IV`. Because the one is before the five we subtract it making four. The same principle applies to the number nine, which is written as `IX`. There are six instances where subtraction is used:

-   `I` can be placed before `V` (5) and `X` (10) to make 4 and 9. 
-   `X` can be placed before `L` (50) and `C` (100) to make 40 and 90. 
-   `C` can be placed before `D` (500) and `M` (1000) to make 400 and 900.

Given a roman numeral, convert it to an integer. Input is guaranteed to be within the range from 1 to 3999.

**Example 1:**

```
Input: "III"
Output: 3
```

**Example 2:**

```
Input: "IV"
Output: 4
```

**Example 3:**

```
Input: "IX"
Output: 9
```

**Example 4:**

```
Input: "LVIII"
Output: 58
Explanation: L = 50, V= 5, III = 3.
```

**Example 5:**

```
Input: "MCMXCIV"
Output: 1994
Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
```

### Solution

#### S1:正向遍历

将罗马数字转换为整数，一般情况下可以直接遍历字符串，将其对应的整数相加，不过其中例如`IX`或`CM`等并不是直接相加，而是说，这种类似的组合构成了一个新的数，这个新的数等于`X-I`或`M-C`，而这种组合都有一种共同的特点，那就是前面的罗马数字对应的数要小于后面的罗马数字对应的数，所以，在遍历的过程中只需要注意有没有这种组合即可，代码如下：

```java
public static int romanToInt(String s) {
    int result = 0;
    int last = get(s.charAt(0)), cur;
    for (int i = 1; i < s.length(); i++) {
        cur = get(s.charAt(i));
        if (last >= cur) {
            result += last;
            last = cur;
        } else {
            result += cur - last;
            if (i < s.length()-1) {
                last = get(s.charAt(++i));
            } else {
                last = 0;
            }
        }
    }
    result += last;
    return result;
}
private static int get(char c) {
    switch (c) {
        case 'I': return 1;
        case 'V': return 5;
        case 'X': return 10;
        case 'L': return 50;
        case 'C': return 100;
        case 'D': return 500;
        case 'M': return 1000;
        default: return 0;
    }
}
```

通过始终保存着上一个罗马数字，就可以根据上一个罗马数字与当前罗马数字的关系，判断它们是否是一个组合，然后求解即可。

#### S2:反向遍历

如果将一串罗马字符串反着看，也就是从低位数向高位数看的话，会发现一个规律：左边出现的罗马数字（或者组合）一定比右边出现的大，所以如果反向看的话，遇到的罗马数字一定是递增的，如果出现了一种左边的数字小于右边数字的情况，如`IX`，那就可以确定这构成了一个组合，而不能直接相加。

除此之外，以`I`为例，它可以出现的位置有四个：

1.  最后的位置
2.  右边是`I`
3.  右边是`V`
4.  右边是`X`

后两种是构成组合的情况，那么可以看到，如果从右向左开始遍历，那么当遍历到`I`的时候，如果当前的结果大于等于 5 的时候，就意味着它的右边至少是一个`V`，也就是说此时`I`必然是以组合情况下出现的，这时应该在当前结果的基础上减去`I`的值。这一规律对于`X`，`C`也是适用的，所以有：

```java
public static int romanToInt2(String s) {
    int result = 0;
    for (int i = s.length()-1; i >= 0; i--) {
        switch (s.charAt(i)) {
            case 'I':
                result += result < 5 ? 1 : -1;
                break;
            case 'V':
                result += 5;
                break;
            case 'X':
                result += result < 50 ? 10 : -10;
                break;
            case 'L':
                result += 50;
                break;
            case 'C':
                result += result < 500 ? 100 : -100;
                break;
            case 'D':
                result += 500;
                break;
            case 'M':
                result += 1000;
        }
    }
    return result;
}
```

