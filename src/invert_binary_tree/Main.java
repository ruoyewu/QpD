package invert_binary_tree;

import default_struct.TreeNode;

import java.util.Stack;

/**
 * User: wuruoye
 * Date: 2019-01-30 21:04
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        TreeNode right = invertTree(root.left);
        root.left = invertTree(root.right);
        root.right = right;
        return root;
    }

    public static TreeNode invertTree2(TreeNode root) {
        if (root == null) return null;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode node;
        while (!stack.empty()) {
            node = stack.pop();
            TreeNode left = node.left, right = node.right;
            node.left = right;
            node.right = left;
            if (left != null) stack.push(left);
            if (right != null) stack.push(right);
        }
        return root;
    }
}
