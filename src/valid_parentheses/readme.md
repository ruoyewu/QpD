### Question

Given a string containing just the characters `'('`, `')'`, `'{'`, `'}'`, `'['` and `']'`, determine if the input string is valid.

An input string is valid if:

1.  Open brackets must be closed by the same type of brackets.
2.  Open brackets must be closed in the correct order.

Note that an empty string is also considered valid.

**Example 1:**

```
Input: "()"
Output: true
```

**Example 2:**

```
Input: "()[]{}"
Output: true
```

**Example 3:**

```
Input: "(]"
Output: false
```

**Example 4:**

```
Input: "([)]"
Output: false
```

**Example 5:**

```
Input: "{[]}"
Output: true
```

### Solution

括号匹配问题，使用栈解题，代码如下：

```java
public static boolean isValid(String s) {
    char[] cs = s.toCharArray();
    Stack<Character> stack = new Stack<>();
    for (char cur : cs) {
        if (cur == '(' || cur == '[' || cur == '{') {
            stack.push(cur);
        } else {
            char op = (cur == ')') ? '(' : ((cur == ']') ? '[' : '{');
            if (!stack.empty() && stack.lastElement() == op) {
                stack.pop();
            } else {
                return false;
            }
        }
    }
    return stack.empty();
}
```

