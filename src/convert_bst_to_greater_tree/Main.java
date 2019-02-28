package convert_bst_to_greater_tree;

import default_struct.TreeNode;

import java.util.Stack;

import static serialize_and_deserialize_binary_tree.Main.deserialize;

/**
 * User: wuruoye
 * Date: 2019-02-27 16:46
 * Description:
 */
public class Main {
    private static int sum;
    private static int cur;

    public static void main(String[] args) {
        TreeNode root = deserialize("[5,2,13]");
    }

    public static TreeNode convertBST(TreeNode root) {
        sum = getSum(root);
        cur = 0;
        convert(root);
        return root;
    }

    private static int getSum(TreeNode node) {
        if (node == null) return 0;
        return node.val + getSum(node.left) + getSum(node.right);
    }

    private static void convert(TreeNode node) {
        if (node != null) {
            convert(node.left);

            cur += node.val;
            node.val = sum - cur + node.val;

            convert(node.right);
        }
    }

    public static TreeNode convertBST2(TreeNode root) {
        cur = 0;
        convert2(root);
        return root;
    }

    private static void convert2(TreeNode node) {
        if (node != null) {
            convert2(node.right);

            node.val += cur;
            cur = node.val;

            convert2(node.left);
        }
    }

    public static TreeNode convertBST3(TreeNode root) {
        int cur = 0;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (node != null) {
            stack.push(node);
            node = node.right;
        }

        while (!stack.empty()) {
            node = stack.pop();

            node.val += cur;
            cur = node.val;

            node = node.left;
            while (node != null) {
                stack.push(node);
                node = node.right;
            }
        }
        return root;
    }
}
