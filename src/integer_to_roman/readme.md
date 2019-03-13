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

Given an integer, convert it to a roman numeral. Input is guaranteed to be within the range from 1 to 3999.

**Example 1:**

```
Input: 3
Output: "III"
```

**Example 2:**

```
Input: 4
Output: "IV"
```

**Example 3:**

```
Input: 9
Output: "IX"
```

**Example 4:**

```
Input: 58
Output: "LVIII"
Explanation: L = 50, V = 5, III = 3.
```

**Example 5:**

```
Input: 1994
Output: "MCMXCIV"
Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
```

### Solution

题目 Roman to Integer 的逆过程，有两种方法，分别是从左往右和从右往左。

#### S1:从左往右

从最大的 M 1000 开始判断，按照当前的最大能够达到的值选择一个合适的字符串加入。如对于数字 1001 ，因为超出了 1000 ，所以首先加入 M 并计算剩余的 1 对应的罗马数字，得到 I 。

```java
public static String intToRoman(int num) {
    StringBuilder builder = new StringBuilder();
    while (num > 0) {
        if (num >= 1000) {
            builder.append('M');
            num -= 1000;
        } else if (num >= 900) {
            builder.append("CM");
            num -= 900;
        } else if (num >= 500) {
            builder.append('D');
            num -= 500;
        } else if (num >= 400) {
            builder.append("CD");
            num -= 400;
        } else if (num >= 100) {
            builder.append('C');
            num -= 100;
        } else if (num >= 90) {
            builder.append("XC");
            num -= 90;
        } else if (num >= 50) {
            builder.append('L');
            num -= 50;
        } else if (num >= 40) {
            builder.append("XL");
            num -= 40;
        } else if (num >= 10) {
            builder.append('X');
            num -= 10;
        } else if (num >= 9) {
            builder.append("IX");
            num -= 9;
        } else if (num >= 5) {
            builder.append('V');
            num -= 5;
        } else if (num >= 4) {
            builder.append("IV");
            num -= 4;
        } else {
            builder.append('I');
            --num;
        }
    }
    return builder.toString();
}
```

这种方式比较复杂，还可以简化为使用数据保存：

```java
public static String intToRoman(int num) {
    int[] nums = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    String[] strings = new String[]{"M", "CM", "D", "CD", "C", "XC",
            "L", "XL", "X", "IX", "V", "IV", "I"};
    StringBuilder builder = new StringBuilder();
    while (num > 0) {
        for (int i = 0; i < nums.length; i++) {
            if (num >= nums[i]) {
                builder.append(strings[i]);
                num -= nums[i];
                break;
            }
        }
    }
    return builder.toString();
}
```

#### S2:从右往左

从最小的右位开始判断，根据当前的位数以及当前位的数字判断。对于每一位的数字，有三个分界线：4，5 和 9 。只需要根据这三个数字划分成四段即可：`9, 8-5, 4, 3-1`，对应的应当选取的分别是`IX, V, IV, I`，然后再根据之后的结果进一步选择，例如上面通过一步选择之后变成了`0, 3-0, 0, 2-0`，然后下一步选择就会变成`IX, VI, IV, II`，再然后变成`IX, VIII, IV, III`。

```java
public static String intToRoman(int num) {
    String[][] map = new String[][]{{"I", "IV", "V", "IX"}, {"X", "XL", "L", "XC"},
            {"C", "CD", "D", "CM"}};
    int[] index = new int[]{1, 4, 5, 9};
    StringBuilder builder = new StringBuilder();
    StringBuilder tmp = new StringBuilder();
    int cur, level = 0;
    while (num > 0) {
        cur = num % 10;
        if (level == 3) {
            while (num-- > 0) builder.append('M');
            break;
        } else {
            tmp.setLength(0);
            while (cur > 0) {
                int i;
                if (cur >= 9) i = 3;
                else if (cur >= 5) i = 2;
                else if (cur >= 4) i = 1;
                else i = 0;
                tmp.append(map[level][i]);
                cur -= index[i];
            }
            builder.append(tmp.reverse());
        }
        level++;
        num /= 10;
    }
    return builder.reverse().toString();
}
```

因为是反向求解，完成之后还需要将解翻转过来。