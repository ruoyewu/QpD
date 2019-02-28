package diameter_of_binary_tree;

import default_struct.TreeNode;

/**
 * User: wuruoye
 * Date: 2019-02-28 08:24
 * Description:
 */
public class Main {
    private static int length;

    public static void main(String[] args) {

    }

    public static int diameterOfBinaryTree(TreeNode root) {
        length = 0;
        diameter(root);
        return length;
    }

    private static int diameter(TreeNode node) {
        if (node == null) {
            return 0;
        } else {
            int left = diameter(node.left);
            int right = diameter(node.right);
            if (left + right > length) {
                length = left + right;
            }
            return (left > right ? left : right) + 1;
        }
    }
}
