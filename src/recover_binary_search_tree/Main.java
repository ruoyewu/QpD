package recover_binary_search_tree;

import default_struct.TreeNode;

/**
 * User: wuruoye
 * Date: 2019-05-10 09:59
 * Description:
 */
public class Main {
    private static TreeNode first, second, pre;

    public static void main(String[] args) {
        TreeNode root = TreeNode.toTree("[1,3,null,null,2]");

        recoverTree(root);
    }

    public static void recoverTree(TreeNode root) {
        recover(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
        System.out.println(root.val);
    }

    private static TreeNode recover(TreeNode node, int from, int to) {
        if (node == null) return null;
        int val = node.val;
        if (val < from || val > to) {
            return node;
        }

        TreeNode left = recover(node.left, from, val-1);
        if (left != null) {
            if (left.val < from || left.val > to) {
                return left;
            } else {
                node.val = left.val;
                left.val = val;
                return recover(node, from, to);
            }
        }

        val = node.val;
        TreeNode right = recover(node.right, val+1, to);
        if (right != null) {
            if (right.val < from || right.val > to) {
                return right;
            } else {
                node.val = right.val;
                right.val = val;
                return recover(node, from, to);
            }
        }

        return null;
    }

    public static void recoverTree2(TreeNode root) {
        first = second = pre = null;
        traversal(root);
        int val = first.val;
        first.val = second.val;
        second.val = val;
    }

    private static void traversal(TreeNode node) {
        if (node == null) return;
        traversal(node.left);
        if (pre != null && pre.val > node.val) {
            if (first == null) first = pre;
            second = node;
        } else if (first != null && first.val < node.val) {
            return;
        }
        pre = node;
        traversal(node.right);
    }
}
