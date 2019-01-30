### Question

Implement a trie with `insert`, `search`, and `startsWith` methods.

**Example:**

```
Trie trie = new Trie();

trie.insert("apple");
trie.search("apple");   // returns true
trie.search("app");     // returns false
trie.startsWith("app"); // returns true
trie.insert("app");   
trie.search("app");     // returns true
```

**Note:**

-   You may assume that all inputs are consist of lowercase letters `a-z`.
-   All inputs are guaranteed to be non-empty strings.

### Solution

这是一个字符串的匹配问题，对于 Java 而言，它本身就有一个字符串的匹配功能，可以直接拿来试一下：

```java
public class Trie {
    HashSet<String> set;

    public Trie() {
        set = new HashSet<>();
    }

    public void insert(String word) {
        set.add(word);
    }

    public boolean search(String word) {
        return set.contains(word);
    }

    public boolean startsWith(String prefix) {
        for (String s : set) {
            if (s.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }
}
```

这种方法得到的结果显然不会很好。个人觉得，这里面的时间都浪费在了 startsWith 上面，要与所有的字符串一一匹配。一个理想的时间应该是这样，比如对于`prefix = "abc"`而言，先判断是否有一个由 a 开头的单词，然后接着判断由 a 开头的单词里是否有下一个字母是 b 的单……这样一直判断下去。

这种方法需要将所有的单词分成一个一个字符的形式，并且赋予它们一定的关系，使得它们能够表示出来一个一个的单词，所以要选择一个数据结构来保存添加进去的单词。观察一下，既要保存每个单词所有的字母，还要让字母能够重组成一个一个的单词，这种复杂的结构，一般考虑是否可以使用树或者图。使每个结点代表一个字母，然后通过结点之间的连接使其能够构成一个单词。

对于使用图来说，我没有想到一种合适的表示方法，下面说一说用树如何表示这样一个结构。首先，每个结点代表一个字母，这个结点的孩子们指向所有可能的下一个字母，那么初步设想，树的每一条路径表示一个单词，那么要保存单词`abc`和`abd`的时候，仅需要 4 个结点，还是比较高效的。但是还有一种情况：对于单词`abcd`和`abc`，这是树中只有四个结点一条路径，所以仅仅使用路径表示单词还不够，还要明确标出某个字母是否是一个单词的终点。

这样一来就算完了，添加的时候，先根据待添加单词找出这条路径，如果要条路径不完整，要将其补全，如果单词不能表达一个完整的路径，要标记一下此单词的终点。

搜索的时候仅需要根据单词不断走向各个分支即可。

还有，关于结点的孩子的存放，因为题中表示所有单词只含有小写的字母，那么为了查询的方便，可以直接定义一个数组，分别表示 26 个字母，要搜索当前结点是否有某个字母的后继结点时，可以直接定位。

```java
public class Trie {
    private class Node {
        Node[] children;
        boolean isEnd = false;

        public Node() {
            children = new Node[26];
        }
    }

    private Node head;

    public Trie() {
        head = new Node();
    }

    public void insert(String word) {
        Node node = head;
        char[] w = word.toCharArray();
        for (char c : w) {
            int i = c - 'a';
            if (node.children[i] == null) {
                node.children[i] = new Node();
            }
            node = node.children[i];
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        char[] w = word.toCharArray();
        Node node = head;
        for (char c : w) {
            int i = c - 'a';
            node = node.children[i];
            if (node == null) {
                return false;
            }
        }
        return node.isEnd;
    }

    public boolean startsWith(String prefix) {
        char[] w = prefix.toCharArray();
        Node node = head;
        for (char c : w) {
            int i = c - 'a';
            node = node.children[i];
            if (node == null) {
                return false;
            }
        }
        return true;
    }
}
```

顺便一提，关于运行效率上，在最开始的时候，第 35 至 38 行我是这么写的：

```java
if (node.children[i] == null) {
    return false;
}
node = node.children[i];
```

包括 startsWith 中的，然后在运行的过程中，前者比后者少了大概十分之一的时间，仅仅就是少了这么一个读取的时间。这还只是在一个算法上面的差距，当设计到具体的开发的话，可能同样一个功能，两个做出来的东西实际使用起来差别巨大。