package populating_next_right_pointers_in_each_node;

import default_struct.Node;

/**
 * User: wuruoye
 * Date: 2019-05-18 21:28
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static Node connect(Node root) {
        if (root == null) return null;
        connect(root.left);
        connect(root.right);
        con(root.left, root.right);
        return root;
    }

    private static void con(Node left, Node right) {
        if (left == null || right == null) return;

        left.next = right;
        con(left.right, right.left);
    }
}
