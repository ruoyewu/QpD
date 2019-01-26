package flatten_binary_tree_to_linked_list;

import java.util.Stack;

/**
 * User: wuruoye
 * Date: 2019/1/26 20:56
 * Description:
 */
public class Main {
    private static TreeNode node;
    TreeNode prev;

    public static void main(String[] args) {

    }

    public static void flatten(TreeNode root) {
        node = new TreeNode(0);
        inorder(root);
    }

    public static void inorder(TreeNode node) {
        if (node != null) {
            TreeNode left = node.left;
            TreeNode right = node.right;
            node.left = null;
            Main.node.right = node;
            Main.node = node;
            inorder(left);
            inorder(right);
        }
    }

    public static void flatten2(TreeNode root) {
        toList(root);
    }

    public static TreeNode toList(TreeNode node) {
        if (node == null) {
            return null;
        }
        if (node.left == null && node.right == null) {
            return node;
        }
        TreeNode leftTail = toList(node.left);
        TreeNode rightTail = toList(node.right);

        TreeNode left = node.left, right = node.right;
        if (left != null) {
            node.left = null;
            left.left = null;
            node.right = left;
            leftTail.right = right;
        }

        return right != null ? rightTail : leftTail;
    }

    public static void flatten4(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = new TreeNode(0);
        if (root != null) {
            stack.push(root);
        }

        while (!stack.empty()) {
            node.right = stack.pop();
            node = node.right;
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
            node.left = null;
        }
    }

    public void flatten3(TreeNode root) {
        if(root == null){
            return;
        }
        flatten3(root.right);
        flatten3(root.left);
        root.right = prev;
        root.left = null;
        prev = root;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
