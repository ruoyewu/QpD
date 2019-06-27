### Question

Given a string *s*, partition *s* such that every substring of the partition is a palindrome.

Return all possible palindrome partitioning of *s*.

**Example:**

```
Input: "aab"
Output:
[
  ["aa","b"],
  ["a","a","b"]
]
```

### Solution

将一个字符串分割成数个回环子串，求出所有的分割情况，大致来看不过就是一个暴力遍历的题目，将原字符串分别截取成`[1,n-1]`、`[2,n-2]`、…、`[n,0]`等情况，并判断前一部分是否是回环子串，如果是，则接着求后一部分的解，并与前一部分结合在一起，构成最终解。代码如下：

```java
public static List<List<String>> partition(String s) {
    List<List<String>> result = new ArrayList<>();
    par(result, new ArrayList<>(), 0, s);
    return result;
}
private static void par(List<List<String>> result, List<String> cur, int pos, String s) {
    if (pos == s.length()) {
        result.add(new ArrayList<>(cur));
    } else {
        for (int i = pos+1; i <= s.length(); i++) {
            if (isPalindrome(s, pos, i-1)) {
                String str = s.substring(pos, i);
                cur.add(str);
                par(result, cur, i, s);
                cur.remove(cur.size()-1);
            }
        }
    }
}
private static boolean isPalindrome(String s, int start, int end) {
    while (start < end) {
        if (s.charAt(start++) != s.charAt(end--)) {
            return false;
        }
    }
    return true;
}
```

