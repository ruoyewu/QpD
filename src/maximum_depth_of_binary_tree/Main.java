package maximum_depth_of_binary_tree;

import java.util.LinkedList;

/**
 * User: wuruoye
 * Date: 2019/1/26 15:36
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    public static int maxDepth2(TreeNode root) {
        if (root == null) return 0;
        LinkedList<TreeNode> list = new LinkedList<>();
        list.add(root);
        int level = 0, left;
        TreeNode node;

        while (!list.isEmpty()) {
            level++;
            left = list.size();
            while (left-- > 0) {
                node = list.removeFirst();
                if (node.left != null) {
                    list.add(node.left);
                }
                if (node.right != null) {
                    list.add(node.right);
                }
            }
        }

        return level;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
