package populating_next_right_pointers_in_each_node_2;

import default_struct.Node;

/**
 * User: wuruoye
 * Date: 2019-05-19 10:49
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static Node connect(Node root) {
        if (root == null) return null;
        Node start = new Node(Integer.MIN_VALUE, null, null, null);
        Node cur = start, node = root;
        while (node != null) {
            if (node.left != null) {
                cur.next = node.left;
                cur = cur.next;
            }
            if (node.right != null) {
                cur.next = node.right;
                cur = cur.next;
            }
            node = node.next;
        }

        connect(start.next);
        return root;
    }
}
