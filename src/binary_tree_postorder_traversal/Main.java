package binary_tree_postorder_traversal;

import default_struct.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * User: wuruoye
 * Date: 2019-03-01 14:01
 * Description:
 */
public class Main {
    private static List<Integer> result = new ArrayList<>();

    public static void main(String[] args) {

    }

    public static List<Integer> postorderTraversal(TreeNode root) {
        result.clear();
        traversal(root);
        return result;
    }

    private static void traversal(TreeNode node) {
        if (node != null) {
            traversal(node.left);
            traversal(node.right);

            result.add(node.val);
        }
    }

    public static List<Integer> postorderTraversal2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root, pre = null;
        while (node != null) {
            stack.push(node);
            node = node.left;
        }

        while (!stack.empty()) {
            node = stack.pop();
            if (node.right == null || node.right == pre) {
                result.add(node.val);
                pre = node;
            } else {
                stack.push(node);
                node = node.right;
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
        }

        return result;
    }

    public static List<Integer> postorderTraversal3(TreeNode root) {
        LinkedList<Integer> result = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        if (root != null) {
            stack.push(root);
        }
        TreeNode node;

        while (!stack.empty()) {
            node = stack.pop();
            result.addFirst(node.val);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        return result;
    }
}
