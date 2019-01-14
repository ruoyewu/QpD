### Question

Given a string containing just the characters `'('` and `')'`, find the length of the longest valid (well-formed) parentheses substring.

**Example 1:**

```
Input: "(()"
Output: 2
Explanation: The longest valid parentheses substring is "()"
```

**Example 2:**

```
Input: ")()())"
Output: 4
Explanation: The longest valid parentheses substring is "()()"
```

### Solution

本题意为求出给定的一个字符串，其中最长的连续的符合规则的括号数量，这个问题实际上可以换种说法：

1.  以字符串中任一位置为起始的符合规则的最长括号序列
2.  以字符串中任一位置为结尾的符合规则的最长括号序列

首先以第一个条件为标准能够得到第一种解法。

#### S1:逐个判断

设定一个循环，每次从一个位置开始，找到从这个位置开始的最长的括号序列并记下来，当所有的位置都被找过之后，其中最长的必然就是所求解：

```java
public static int longestValidParentheses(String s) {
    char[] cs = s.toCharArray();
    int longest = 0;
    for (int i = 0; i < cs.length-1; i++) {
        if (cs.length - i <= longest) {
            break;
        }
        int max = 0, left = 0, save = 0;
        for (int j = i; j < cs.length; j++) {
            if (cs[j] == '(') {
                left++;
            }else {
                if (left == 0) {
                    i = j+1;
                    break;
                }
                left--;
                save++;
                if (left == 0) {
                    max += save;
                    save = 0;
                }
            }
        }
        if (longest < max) {
            longest = max;
        }
    }
    return longest*2;
}
```

很显然，此解是一个时间复杂度为$O(n^2)$的解法，不过它还是使用了一些方法尽量化简计算的，如`if (cs.length - i <= longest)`判断，当已经发现的最长序列大于剩下的未检测的部分序列长度的时候，意味着无论如何不可能再有比现在更长的序列，就可以放弃继续查找了。又如`if (left == 0) { i = j+1; break; }`判断，如果当前检测到的字符串序列是形如`...)`的形式，其中`...`是一个符合规则的序列段，那么必然有在`...`这个序列中的任意位置的最长符合规则字符串序列长度都不会大于`...`的长度，于是便可以跳过这些位置的检测。

不过这始终无法弥补一个数量级带来的差距，最好的方法应该是时间复杂度为$O(n)$，这边要求我们使用第二个条件解题。

#### S2:使用栈

由于这是一种可以匹配消除的模式，可以想到使用栈解决问题，如果将字符一个一个压入栈，而我们只会对栈顶操作，并且使用栈保存以某个位置为结尾的最长符合规则序列长度，便有如下算法：

```java
public static int longestValidParentheses(String s) {
    int longest = 0;
    Stack<Integer> stack = new Stack<>();
    stack.push(-1);
    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '(') {
            stack.push(i);
        } else {
            stack.pop();
            if (stack.empty()) {
                stack.push(i);
            } else {
                longest = Math.max(longest, i - stack.peek());
            }
        }
    }
    return longest;
}
```

此算法的基本思路是，使用栈顶保存无法完成配对的最近的符号的位置，那么对于 i 来说，i 与栈顶数值的差值就是这段序列的长度，这就需要将所有的`(`所在的位置压栈，并在`)`出现的时候出栈，但是仅有这些还不够，对于序列`()()`而言，如果没有辅助数据，会在位置 1 处栈为空，在位置 3 处也为空，而此时栈中只有位置 2 的记录，但很显然位置 0 和 1 本应该也包含进来的，所以需要在栈中增加一个额外空间保存这个不应该被消去的位置，如上述代码，当完全消去的时候栈不为空，而是还留有一个保存初始位置的栈帧，只有当多余的`)`出现的时候才会为空，此时便将初始位置移到这个`)`所在的位置，继续接下来的添加、消去的步骤。

这种算法的时间复杂度为$O(n)$，空间复杂度也为$O(n)$，但是却使用到了栈，依旧会产生一些开销。

#### S3:动态规划

对于给定的一个序列，它的每一个字序列都有一个解，并且它们的解是相互关联的，于是思考使用动态规划解题，并且这也是基于第二种条件的解题，动态规划首先要规定一个解的数组对应原始序列各个子序列的解，即使用数组保存以位置 i 为结尾的最长符合规则序列的长度。于是这个数组有以下递推规则：

1.  s[i] == '('，则以此为结尾的序列必然没有满足条件的子序列，故 l[i] = 0
2.  s[i] == ')'，那么首先需要进行判断
    1.  l[i-1] == 0，此时也有两种情况：
        1.  s[i-1] == '('，那么 s[i] 和 s[i-1] 刚好组成一对，于是有 l[i] = l[i-2]
        2.  s[i-1] == ')'，此时前 i-1 个字符应该形如 `...)`，且这个`)`不能与之前的组成配对，那么必有 i 位置处的`)`也不能完成配对，故此时 l[i] = 0
    2.  l[i-1] != 0，此时前 i 个字符形如`AB)`，且`B`是符合规则的序列，而要判断此时加上一个`)`是否仍旧符合规则，则需要判断`A`部分的最后一个字符是否是`(`，当其为`(`时，加上`)`仍旧是符合规则的，即需要先判断 s[i - l[i-1] - 1] == '(' 是否成立，如果成立的话，则还应该判断`A`部分去掉最后的`(`之后是否还有符合规则的子序列，有 l[i] = 2 + l[i-1] + l[i - l[i-1] - 2]

具体代码如下：

```java
public static int longestValidParentheses2(String s) {
    char[] cs = s.toCharArray();
    int longest = 0;
    int[] ls = new int[cs.length];
    for (int i = 1; i < cs.length; i++) {
        if (cs[i] == ')') {
            if (cs[i-1] == '(') {
                ls[i] = (i >= 2 ? ls[i-2] : 0) + 2;
            } else if (i - ls[i-1] > 0 && cs[i - ls[i-1] - 1] == '(') {
                ls[i] = ls[i-1] + ((i - ls[i-1]) >= 2 ? ls[i - ls[i-1] - 2] : 0) + 2;
            }
            longest = longest > ls[i] ? longest : ls[i];
        }
    }
    return longest;
}
```

此时只需要使用一个额外数组便能求解，时间复杂度与空间复杂度均为$O(n)$。那么，是否能够进一步精简复杂度，使空间复杂度降到$O(1)$。结合最初所列的两个条件便是解题之路。

#### S4:双向求解

最初所列的两个条件，满足其中任何一个条件的同时也必然满足另一个条件，如果将那两个条件更改一下：

1.  从某一位置从左向右开始，左括号数量大于等于右括号数量
2.  从某一位置从右向左开始，右括号数量大于等于左括号数量

我们知道，对于一个序列段来说，如果它形如`...)`，其中`...`是符合条件的序列段，那么`...)`序列段必然是不能配对成功的，我们可以跳过这个`)`开始之后的判断，与使用栈求解的原理类似，只需要一遍扫描，通过`(`与`)`配对的原理，只需要保持`(`的数量始终大于等于`)`的数量，那么当它们数量相等时，必然是能够配对的。但是这种方法只适用于形如`...A)...`的情况，其中 A 是最长合规子序列，那么还要求形如`...(A...`的情况，与前一种情况对比，知这是第一种情况的倒置，所以只需要按着相反的方向再求一次就可以了。算法如下：

```java
public static int longestValidParentheses(String s) {
    int longest = 0, left = 0, right = 0;
    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '(') {
            left++;
        } else {
            right++;
        }
        if (left == right) {
            longest = Math.max(longest, right*2);
        } else if (right > left) {
            left = right = 0;
        }
    }
    left = right = 0;
    for (int i = s.length()-1; i >= 0; i--) {
        if (s.charAt(i) == '(') {
            left++;
        } else {
            right++;
        }
        if (left == right) {
            longest = Math.max(longest, left*2);
        } else if (left > right) {
            left = right = 0;
        }
    }
    return longest;
}
```

这样就可以使空间复杂度降为$O(1)$了，但是相对于前一种方法只需要遍历一次，这种则需要遍历两次。