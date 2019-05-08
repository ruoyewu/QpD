### Question

Given a string containing only digits, restore it by returning all possible valid IP address combinations.

**Example:**

```
Input: "25525511135"
Output: ["255.255.11.135", "255.255.111.35"]
```

### Solution

一道分割字符串的题目，需要将原字符串分割成四份，使其能够转换成数字，且每一份都在`[0,255]`的范围内，所以不能存在如`01`这样以 0 开头，但不是 0 的数字，可以直接使用暴力遍历加上剪枝的方式求解。

遍历这部分，就是先假设某一份的长度，确定了之后再去假设下一份的长度，直到达到目标。本题有两个目标：正好分成四份且原字符串分割完全，假如出现了分成四份之后字符串还没用完或者是字符串用完了但是没有分到四份的，都是不满足条件的。然后是剪枝，首先，每一个数字都是小于 256 的，所以当截取的子串大于这个数时，说明不可取，之后的也无需判断，还有，如果子串以 0 开头，那么它只能是 0 ，而不能是 02 等，所以也无需判断它是否可以是一个两位数或三位数。

```java
public static List<String> restoreIpAddresses2(String s) {
    List<String> result = new ArrayList<>();
    restore(result, new char[s.length()+4], s, 0, 0);
    return result;
}
private static void restore(List<String> result, char[] saved, String s, int pos, int len) {
    if (len == 4 && pos == s.length())
        result.add(new String(saved, 0, s.length()+3));
    if (len == 4 || pos == s.length()) return;
    int num = 0;
    for (int i = pos; i < pos+3 && i < s.length(); i++) {
        num = num * 10 + s.charAt(i) - '0';
        if (num < 256) {
            saved[len+i] = s.charAt(i);
            saved[len+i+1] = '.';
            restore(result, saved, s, i+1, len+1);
        }
        if (num == 0) break;
    }
}
```

使用字符数组保存当前的选择情况，每个数字最多是一个三位数，所以每个递归方法里最多执行三次循环。