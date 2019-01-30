package implement_trie;

import java.util.HashSet;

/**
 * User: wuruoye
 * Date: 2019-01-30 14:28
 * Description:
 */
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
