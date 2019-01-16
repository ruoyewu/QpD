### Question

Given an array of strings, group anagrams together.

**Example:**

```
Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
Output:
[
  ["ate","eat","tea"],
  ["nat","tan"],
  ["bat"]
]
```

**Note:**

-   All inputs will be in lowercase.
-   The order of your output does not matter.

### Solution

题目要求将含有字母种类即个数相同的不同排列归为一类，求各类分别是哪些，所以本题的关键在于快速区分不同类，即如何确定两个字符串是同一类。先看一下最简单又最复杂的解法：

#### S1:暴力法

```java
public static List<List<String>> groupAnagrams(String[] strs) {
    List<List<String>> result = new ArrayList<>();
    List<String> left = new ArrayList<>(Arrays.asList(strs));
    int[] index = new int[128];
    int length = 0;
    while (!left.isEmpty()) {
        String s = left.remove(left.size()-1);
        length = s.length();
        List<String> list = new ArrayList<>();
        list.add(s);
        Arrays.fill(index, 0);
        for (int i = 0; i < s.length(); i++) {
            index[s.charAt(i)] = index[s.charAt(i)] + 1;
        }
        for (int i = 0; i < left.size(); ) {
            s = left.get(i);
            int[] n = new int[128];
            System.arraycopy(index, 0, n, 0, 128);
            if (check(n, length, s)) {
                left.remove(i);
                list.add(s);
            } else {
                i++;
            }
        }
        result.add(list);
    }
    return result;
}
public static boolean check(int[] index, int length, String s) {
    if (s.length() == length) {
        for (int i = 0; i < s.length(); i++) {
            int l = index[s.charAt(i)];
            if (l > 0) {
                index[s.charAt(i)] = l - 1;
            } else {
                return false;
            }
        }
        return true;
    }
    return false;
}
```

从第一个开始，让其与之后的每一个比较，判断是否是同一类，显然，这个时间复杂度为$O(kn^2)$，运行起来费劲，写起来也费劲。那么就要求变，于是思考，是否可以将某一类具像化成为一个关键字，使得同一类的关键字相同，那么此时只需要一次遍历，将具有相同关键字的字符串放到一起，就得到了解。

#### S2:关键字法

要完成上述的操作，只需要一个 MAP 即可，一个关键字对应一个列表对应一类，列表保存该类所有的字符串，在一次遍历中，求出每个字符串的关键字并将其添加到对应的列表中，一切都一气呵成，如此美好。可是问题又来了，关键字怎么求？如何能使一类的关键字恰好都相同并且不同类的关键字必然不同？可以是如下的方法：

1.  因为题中给出，字符串中所有的字符都是小写英文字母，那么我们可以使用大小为 26 的数组保存每个字符出现的次数，那么同属于一个类的字符串必然有：相同字符出现的次数相同，所以可以使用每个字符出现的次数作为关键字，数组当然不能做关键字，要换成字符串才行，但是字符串不能区分啊，`1123`，到底是`[1,1,2,3]`还是`[11,23]`呢？所以还要加个分割号，`11_2_3`，如此便明了了。这时，代码为：

    ```java
    public static List<List<String>> groupAnagrams(String[] strs) {
        if (strs.length == 0)
            return new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        int[] count = new int[26];
        for (String s : strs) {
            Arrays.fill(count, 0);
            for (int i = 0; i < s.length(); i++) {
                count[s.charAt(i) - 'a']++;
            }
            StringBuilder builder = new StringBuilder();
            for (int c : count) {
                builder.append("_").append(c);
            }
            String key = builder.toString();
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(s);
        }
        return new ArrayList<>(map.values());
    }
    ```

2.  又或者，因为同一类字符串仅仅是排列的顺序不同而已，那么如果将所有的字符串变换为一个规则性的排列（如升序），那么同一类的字符串必然在变换之后都是相同的了，并且可以确定不同类的变换之后不同，于是这也可以作为一个关键字，代码为：

    ```java
    public static List<List<String>> groupAnagrams4(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] cs = s.toCharArray();
            Arrays.sort(cs);
            String key = new String(cs);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(s);
        }
        return new ArrayList<>(map.values());
    }
    ```

这种方法的时间复杂度为$O(kn)$和$O(kn\log n)$，或许还有别的选取关键字的方法，但是应该都不会超出 kn 的范畴，这应该已经是最好的时间复杂度了。