package symmetric_tree;


import java.util.Stack;

/**
 * User: wuruoye
 * Date: 2019/1/26 13:48
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return symmetric(root.left, root.right);
    }

    public static boolean symmetric(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left != null && right != null) {
            return (left.val == right.val) &&
                    symmetric(left.left, right.right) && symmetric(left.right, right.left);
        }
        return false;
    }

    public static boolean isSymmetric2(TreeNode root) {
        if (root == null) return true;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root.left);
        stack.push(root.right);
        TreeNode left, right;

        while (!stack.empty()) {
            left = stack.pop();
            right = stack.pop();
            if (left == null && right == null) {

            }else if (left == null || right == null) {
                return false;
            } else {
                if (left.val == right.val) {
                    stack.push(left.left);
                    stack.push(right.right);
                    stack.push(left.right);
                    stack.push(right.left);
                } else {
                    return false;
                }
            }
        }
        return true;
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
