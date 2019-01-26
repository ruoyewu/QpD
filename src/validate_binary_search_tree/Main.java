package validate_binary_search_tree;

import java.util.Stack;

/**
 * User: wuruoye
 * Date: 2019/1/26 12:14
 * Description:
 */
public class Main {
    private static long last;
    private static boolean isValidate = true;

    public static void main(String[] args) {

    }

    public static boolean isValidBST(TreeNode root) {
        last = Long.MIN_VALUE;
        isValidate = true;
        validate(root);
        return isValidate;
    }

    public static void validate(TreeNode node) {
        if (isValidate && node != null) {
            validate(node.left);
            if (node.val <= last) {
                isValidate = false;
            } else {
                last = node.val;
            }
            validate(node.right);
        }
    }

    public static boolean isValidBST2(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        long last = Long.MIN_VALUE;
        TreeNode node = root;
        while (node != null) {
            stack.push(node);
            node = node.left;
        }

        while (!stack.empty()) {
            node = stack.pop();
            if (last >= node.val) {
                return false;
            } else {
                last = node.val;
            }

            node = node.right;
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
        return true;
    }

    public static boolean isValidBST3(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public static boolean validate(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val >= max || node.val <= min) return false;
        return validate(node.left, min, node.val) && validate(node.right, node.val, max);
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
