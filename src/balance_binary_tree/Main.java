package balance_binary_tree;

import default_struct.TreeNode;

/**
 * User: wuruoye
 * Date: 2019-05-16 22:19
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        TreeNode root = TreeNode.toTree("[3,9,20,null,null,15,7]");
        System.out.println(isBalanced(root));
    }

    public static boolean isBalanced(TreeNode root) {
        return weight(root) >= 0;
    }

    private static int weight(TreeNode node) {
        if (node == null) return 0;
        int left = weight(node.left);
        int right = weight(node.right);
        if (left < 0 || right < 0 || Math.abs(left-right) > 1) return -1;
        return Math.max(left, right) + 1;
    }
}
