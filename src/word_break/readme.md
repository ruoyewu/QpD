### Question

Given a **non-empty** string *s* and a dictionary *wordDict* containing a list of **non-empty** words, determine if *s* can be segmented into a space-separated sequence of one or more dictionary words.

**Note:**

-   The same word in the dictionary may be reused multiple times in the segmentation.
-   You may assume the dictionary does not contain duplicate words.

**Example 1:**

```
Input: s = "leetcode", wordDict = ["leet", "code"]
Output: true
Explanation: Return true because "leetcode" can be segmented as "leet code".
```

**Example 2:**

```
Input: s = "applepenapple", wordDict = ["apple", "pen"]
Output: true
Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
             Note that you are allowed to reuse a dictionary word.
```

**Example 3:**

```
Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
Output: false
```

### Solution

这题算是一种关于数组的搜索类问题，一般有两种方法：递归法和动态规划法。

#### S1:递归法

判断一个字符串是否可以由若干个给出的单词组合出来，可以从头开始判断，如果该字符串的前几个字母正好是某个单词，那么下一步就是判断除去这头几个字母之后的字符串是否可以再由这些单词组成。如字符串`abcedf`，单词`["abc", ...]`，那么下一步就是判断`edf`是否能够由那些单词组成即可，所以，这是一个逆向求解的问题，要求某个字符串是否有解，需要先求这个字符串的后半部分是否有解。

```java
public static boolean wordBreak(String s, List<String> wordDict) {
    List<Set<char[]>> dict = new ArrayList<>();
    for (String word : wordDict) {
        int i = word.charAt(0) - 'A';
        while (dict.size() <= i) {
            dict.add(new HashSet<>());
        }
        dict.get(i).add(word.toCharArray());
    }
    int[] saved = new int[s.length()];
    return word(s.toCharArray(), 0, dict, saved);
}
private static boolean word(char[] s, int pos, List<Set<char[]>> dict, int[] saved) {
    if (pos == s.length) {
        return true;
    }
    if (pos > s.length) {
        return false;
    }
    if (saved[pos] == 1) {
        return true;
    } else if (saved[pos] == -1) {
        return false;
    }
    int i = s[pos] - 'A';
    if (dict.size() > i) {
        for (char[] word : dict.get(i)) {
            boolean ok = true;
            for (int j = 0; j < word.length; j++) {
                if (pos + j < s.length && s[pos+j] != word[j]) {
                    ok = false;
                    break;
                }
            }
            if (ok && word(s, pos+word.length, dict, saved)) {
                saved[pos] = 1;
                return true;
            }
        }
    }
    saved[pos] = -1;
    return false;
}
```

因为每个单词长短不一，所以，使用递归的时候可能会有多次的重复计算，仍旧需要一个 saved 数组保存从某个位置开始的子字符串是否可以由这些单词组成。其中 1 代表可以，-1 代表不可，0 代表待计算。

#### S2:动态规划

这类问题的还可以使用动态规划解题，对于一个字符串，我们可以求从开始到某一位置是否能够由这些单词组成，从第一个位置一直计算到最后一个位置，便是整个字符串了。而对于某一位置而言，如果从这个位置往前数的几个字母能够组成某个单词，那么只需要判断去掉最后这几个字母之后的字符串是否有解即可。如字符串`abcedf`，字母`["edf", ...]`，因为字符串后三位组成的单词存在，所以只要`abc`有解，这个字符串就是有解了。这是一个正向求解，要求解一个字符串是否有解，需要知道这个字符串的前半部分是否有解。

```java
public static boolean wordBreak(String s, List<String> wordDict) {
    int n = s.length();
    Set<String> set = new HashSet<>();
    int minLen = Integer.MAX_VALUE;
    int maxLen = 0;
    for (String word : wordDict) {
        if (word.length() > maxLen) maxLen = word.length();
        if (word.length() < minLen) minLen = word.length();
        set.add(word);
    }
    boolean[] ok = new boolean[n+1];
    ok[0] = true;
    for (int i = 1; i <= s.length(); i++) {
        int start = Math.max(0, i-maxLen);
        int end = i - minLen;
        for (int j = start; j <= end; j++) {
            if (ok[j] && set.contains(s.substring(j, i))) {
                ok[i] = true;
                break;
            }
        }
    }
    return ok[n];
}
```

这里用到了一定的技巧，一次遍历所有的单词后记下这些单词的最短长度和最长长度，那么判断字符串从开始到位置 i 的后几位是否有解的时候，不需要从 0 遍历到 i ，因为单词有一个长度范围，所以遍历的范围也应该是`[i-maxLen, i-minLen]`。