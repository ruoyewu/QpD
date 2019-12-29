package copy_list_with_random_pointer;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

    }

    public Node copyRandomList(Node head) {
        if (head == null) return null;
        Map<Node, Node> map = new HashMap<>();
        Node result = null;
        Node node = null;
        Node cur = head;

        while (cur != null) {
            if (result == null) {
                result = new Node(cur.val);
                node = result;
            } else {
                node.next = new Node(cur.val);
                node = node.next;
            }
            map.put(cur, node);
            cur = cur.next;
        }

        cur = head;
        node = result;
        while (cur != null) {
            node.random = map.getOrDefault(cur.random, null);
            cur = cur.next;
            node = node.next;
        }

        return result;
    }

    public Node copyRandomList2(Node head) {
        if (head == null) return null;
        Node node = head;

        // copy all node
        while (node != null) {
            Node n = new Node(node.val);
            n.next = node.next;
            node.next = n;
            node = n.next;
        }

        // set random node
        node = head;
        while (node != null) {
            if (node.random != null) {
                node.next.random = node.random.next;
            }
            node = node.next.next;
        }

        // extract copied node
        Node result = head.next;
        Node n = head.next;
        head.next = n.next;
        node = n.next;
        while (node != null) {
            n.next = node.next;
            n = n.next;
            node.next = node.next.next;
            node = node.next;
        }

        return result;
    }

    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
}
