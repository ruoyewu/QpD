package sum_root_to_leaf_numbers;

import default_struct.TreeNode;

public class Main {
    private static int sum = 0;

    public static void main(String[] args) {

    }

    public static int sumNumbers(TreeNode root) {
        if (root == null) return 0;
        sum = 0;
        numbers(root, 0);
        return sum;
    }

    private static void numbers(TreeNode node, int parent) {
        parent = parent * 10 + node.val;
        if (node.left == null && node.right == null) {
            sum += parent;
        } else {
            if (node.left != null) {
                numbers(node.left, parent);
            }
            if (node.right != null) {
                numbers(node.right, parent);
            }
        }
    }
}
