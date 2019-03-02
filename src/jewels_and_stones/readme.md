### Question

You're given strings `J` representing the types of stones that are jewels, and `S` representing the stones you have.  Each character in `S` is a type of stone you have.  You want to know how many of the stones you have are also jewels.

The letters in `J` are guaranteed distinct, and all characters in `J` and `S` are letters. Letters are case sensitive, so `"a"` is considered a different type of stone from `"A"`.

**Example 1:**

```
Input: J = "aA", S = "aAAbbbb"
Output: 3
```

**Example 2:**

```
Input: J = "z", S = "ZZ"
Output: 0
```

**Note:**

-   `S` and `J` will consist of letters and have length at most 50.
-   The characters in `J` are distinct.

### Solution

很简单，就是判断 S 字符串中所有在 J 中出现过的字符。

1.  将所有 J 中出现的字符保存起来
2.  遍历 S ，判断某个位置的字符在 J 中出现过

```java
public static int numJewelsInStones(String J, String S) {
    boolean[] contains = new boolean[128];
    for (int i = 0; i < J.length(); i++) {
        contains[J.charAt(i)] = true;
    }
    int num = 0;
    for (int i = 0; i < S.length(); i++) {
        if (contains[S.charAt(i)]) {
            num++;
        }
    }
    return num;
}
```

如上。