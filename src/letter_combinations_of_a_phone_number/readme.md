### Question

Given a string containing digits from `2-9` inclusive, return all possible letter combinations that the number could represent.

A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.

![img](http://upload.wikimedia.org/wikipedia/commons/thumb/7/73/Telephone-keypad2.svg/200px-Telephone-keypad2.svg.png)

**Example:**

```
Input: "23"
Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
```

**Note:**

Although the above answer is in lexicographical order, your answer could be in any order you want.

### Solution

此问题求解需要一个多重循环，保存一个字符串列表，遍历输入字符串，每遍历到一个字符，就将字符串列表中的所有字符串加上当前字符对应的字母，然后重新构造一个字符串列表，以此递推。

问题求解步骤：

1.  将输入数字字符串转化为数组，并构造对应的字母数组
2.  以此根据输入数字，向已生成的字符串末尾加入对应的字母

代码如下：

```java
public static final String[] INDEX = {"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

public static List<String> letterCombinations(String digits) {
    List<String> result = new ArrayList<>();
    List<String> temps = new ArrayList<>();
    List<List<Character>> indexes = new ArrayList<>();
    
    //生成索引
    for (String i : INDEX) {
        List<Character> chs = new ArrayList<>();
        for (int j = 0; j < i.length(); j++) {
            chs.add(i.charAt(j));
        }
        indexes.add(chs);
    }
    //将字符串转化为数组
    int nums[] = new int[digits.length()];
    for (int i = 0; i < nums.length; i++) {
        nums[i] = (int) digits.charAt(i) - 48 -2;
    }
    //遍历数字
    for (int num : nums) {
        if (result.size() == 0) {
            for (char c : indexes.get(num)) {
                result.add("" + c);
            }
        } else {
            for (String s : result) {
                for (char c : indexes.get(num)) {
                    temps.add(s + c);
                }
            }
            result = temps;
            temps = new ArrayList<>();
        }
    }
    return result;
}
```

