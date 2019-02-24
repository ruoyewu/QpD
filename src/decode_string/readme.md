### Question

Given an encoded string, return it's decoded string.

The encoding rule is: `k[encoded_string]`, where the *encoded_string* inside the square brackets is being repeated exactly *k* times. Note that *k* is guaranteed to be a positive integer.

You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.

Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, *k*. For example, there won't be input like `3a` or `2[4]`.

**Examples:**

```
s = "3[a]2[bc]", return "aaabcbc".
s = "3[a2[c]]", return "accaccacc".
s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
```

### Solution

这是一个典型的递归求解的例子，每个使用`[]`括住的都是一个整体：一个字符串被若干个`[]`分割成不同的部分，同级的`[]`相加处理，不同级则需要进入递归函数。如例子`1[a]2[b]`，等于`1[a]`与`2[b]`相加，例子`2[a3[b]]`，等于 2 倍的`a3[b]`，这时`a3[b]`就是需要进入一个新的递归函数再判断的。

另外，字母与`[]`也是同级的，如`ab2[c]`，等于`ab`与`2[c]`相加，而`2[c]`因为有一个下一级的`[]`，有`2[c] = 2 * c`，这里的 \* 不是数值上的乘，而是数量上的乘：`2 * c = cc`。

那么求解时，应该先将同级的分解开，下一级的则利用递归函数求解。如下：

```java
public static String decodeString(String s) {
    if (s.length() == 0) return "";
    StringBuilder builder = new StringBuilder();
    int pos = 0, start = 0, count = 0, left = 0;
    while (pos < s.length()) {
        char c = s.charAt(pos);
        if (left == 0) {
            if (c >= '0' && c <= '9') {
                // 数字前有字母
                if (start != pos) {
                    builder.append(s, start, pos);
                }
                // 求数字数值
                count = count * 10 + c - '0';
                start = pos+1;
            } else if (c == '[') {
                // 求当前这一级
                left = 1;
                start = pos+1;
            }
            pos++;
        } else {
            if (c == '[') {
                left++;
            } else if (c == ']') {
                left--;
                if (left == 0) {
                    // 求下一级
                    String ss = decodeString(s.substring(start, pos));
                    while (count-- > 0) {
                        builder.append(ss);
                    }
                    count = 0;
                    start = pos+1;
                }
            }
            pos++;
        }
    }
    // 最后以字母结尾，而不是 ]
    if (start != pos) {
        builder.append(s, start, pos);
    }
    return builder.toString();
}
```

这种解法，利用分割字符串的方式，将一个字符串同级的分开，然后再分别求解。如将`2[a3[b]]c`，先分成`2[a3[b]]c = 2 * a3[b] + c`，再分别求解`a3[b]`和`c`，有`a3[b] = a + 3 * b`，所以总的有`2[a3[b]]c = 2 * (a + 3 * b) + c`。

这里面最重要的就是关于`[`的判断，首先，left 保存当前`[`的数量，只有当 left 为 0 时的位置才是当前级的，其结果应该是直接计算的，而在 left 为 1 的位置都是下一级的，所以要先找到下一级所有的字母，然后利用分割字符串，将其分割出去。如`ab3[cd]`中，在`ab3`处时 left 为 0 ，在`cd`是 left 为 1 ，所以，因为同级为加，下级为乘，得出`ab3[cd] = a + b + 3 * cd`的结果。

或者也可以使用另一种方法：

```java
private static int index = 0;
public static String decodeString(String s) {
    index = 0;
    return s2(s);
}
private static String s2(String s) {
    StringBuilder builder = new StringBuilder();
    int count = 0;
    while (index < s.length()) {
        char c = s.charAt(index++);
        if (c >= '0' && c <= '9') {
            count = count * 10 + c - '0';
        } else if (c == '[') {
            String sub = s2(s);
            while (count-- > 0) builder.append(sub);
            count = 0;
        } else if (c == ']') {
            return builder.toString();
        } else {
            builder.append(c);
        }
    }
    return builder.toString();
}
```

较之于上面那种方法的逐级求解，这种则是没有判断完当前级地内容，就直接下潜到下一级，然后求完下一级之后接着求当前级。或者也可以改成非递归式的：

```java
public static String decodeString(String s) {
    Stack<String> stack = new Stack<>();
    Stack<Integer> counts = new Stack<>();
    StringBuilder builder = new StringBuilder();
    int count = 0;
    for (char c : s.toCharArray()) {
        if (c >= '0' && c <= '9') {
            count = count * 10 + c - '0';
        } else if (c == '[') {
            counts.push(count);
            count = 0;
            stack.push(builder.toString());
            builder.setLength(0);
        } else if (c == ']') {
            int size = counts.pop();
            String str = builder.toString();
            builder.setLength(0);
            builder.append(stack.pop());
            while (size-- > 0) {
                builder.append(str);
            }
        } else {
            builder.append(c);
        }
    }
    return builder.toString();
}
```

