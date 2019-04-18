### Question

Given a string *s* consists of upper/lower-case alphabets and empty space characters `' '`, return the length of last word in the string.

If the last word does not exist, return 0.

**Note:** A word is defined as a character sequence consists of non-space characters only.

**Example:**

```
Input: "Hello World"
Output: 5
```

### Solution

不做更多解释，代码如下：

```java
public static int lengthOfLastWord(String s) {
    if (s.length() == 0) return 0;
    int count = 0;
    int pos = s.length();
    while (pos > 0 && s.charAt(pos-1) == ' ') --pos;
    while (--pos >= 0) {
        if (s.charAt(pos) != ' ') {
            count++;
        } else {
            return count;
        }
    }
    return count;
}
```

