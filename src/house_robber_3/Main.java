package house_robber_3;

import java.util.HashMap;
import java.util.Map;

/**
 * User: wuruoye
 * Date: 2019-02-22 17:16
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static int rob(TreeNode root) {
        return rob(root, new HashMap<>());
    }

    private static int rob(TreeNode node, Map<TreeNode, Integer> saved) {
        if (node == null) return 0;
        if (saved.containsKey(node)) return saved.get(node);
        int max = rob(node.left, saved) + rob(node.right, saved);
        int m = node.val;
        if (node.left != null) {
            m += rob(node.left.left, saved) + rob(node.left.right, saved);
        }
        if (node.right != null) {
            m += rob(node.right.left, saved) + rob(node.right.right, saved);
        }
        max = Math.max(max, m);
        saved.put(node, max);
        return max;
    }

    public static int rob2(TreeNode node) {
        if (node == null) return 0;
        int max = rob2(node.left) + rob2(node.right);
        int m = node.val;
        if (node.left != null) {
            m += rob2(node.left.left) + rob2(node.left.right);
        }
        if (node.right != null) {
            m += rob2(node.right.left) + rob2(node.right.right);
        }
        max = Math.max(max, m);
        return max;
    }

    public static int rob3(TreeNode root) {
        int[] rob = max3(root);
        return Math.max(rob[0], rob[1]);
    }

    private static int[] max3(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0};
        }
        int[] rob = new int[2];
        int[] left = max3(node.left);
        int[] right = max3(node.right);

        rob[0] = left[1] + right[1] + node.val;
        rob[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        return rob;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            this.val = x;
        }
    }
}
