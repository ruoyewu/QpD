package implement_trie;

/**
 * User: wuruoye
 * Date: 2019-01-30 14:31
 * Description:
 */
public class Trie2 {
    private Node head;

    public Trie2() {
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

    private class Node {
        Node[] children;
        boolean isEnd = false;

        public Node() {
            children = new Node[26];
        }
    }
}
