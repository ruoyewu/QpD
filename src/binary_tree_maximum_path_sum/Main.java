package binary_tree_maximum_path_sum;

/**
 * User: wuruoye
 * Date: 2019/1/27 12:52
 * Description:
 */
public class Main {
    private static int maxPath;

    public static void main(String[] args) {

    }

    public static int maxPathSum(TreeNode root) {
        maxPath = Integer.MIN_VALUE;
        maxPath(root);
        return maxPath;
    }

    private static int maxPath(TreeNode node) {
        if (node == null) return 0;
        int left = maxPath(node.left);
        int right = maxPath(node.right);
        int max = node.val;
        if (left > 0) max += left;
        if (right > 0) max += right;
        if (max > maxPath) {
            maxPath = max;
        }
        return Math.max(0, Math.max(left, right)) + node.val;
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
