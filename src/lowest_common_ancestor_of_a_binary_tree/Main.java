package lowest_common_ancestor_of_a_binary_tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * User: wuruoye
 * Date: 2019-01-31 20:10
 * Description:
 */
public class Main {
    private static TreeNode result;
    private static boolean go;

    public static void main(String[] args) {

    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        result = null;
        containNode(root, p, q);
        return result;
    }

    private static boolean containNode(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) return false;
        int left = containNode(node.left, p, q) ? 1 : 0;
        int right = containNode(node.right, p, q) ? 1 : 0;
        int mid = (node.val == q.val || node.val == p.val) ? 1 : 0;
        if (left + right + mid == 2 && result == null) {
            result = node;
        }
        return (left + right + mid) > 0;
    }

    public static TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> pathP = new ArrayList<>(), pathQ = new ArrayList<>();
        go = true;
        postOrder(pathP, root, p);
        go = true;
        postOrder(pathQ, root, q);
        TreeNode parent = null;
        int i = 0;
        while (i < pathP.size() && i < pathQ.size()) {
            if (pathP.get(i) == pathQ.get(i)) {
                parent = pathP.get(i);
                i++;
            } else {
                break;
            }
        }
        return parent;
    }

    private static void postOrder(List<TreeNode> path, TreeNode node, TreeNode search) {
        if (node != null && go) {
            if (node.val == search.val) {
                path.add(node);
                go = false;
                return;
            }
            path.add(node);
            postOrder(path, node.left, search);
            postOrder(path, node.right, search);
            if (go) {
                path.remove(path.size()-1);
            }
        }
    }

    public static TreeNode lowestCommonAncestor4(TreeNode root, TreeNode p, TreeNode q) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null, node, result = null;
        boolean findOne = false, first = true;
        stack.push(root);

        while (!stack.empty()) {
            node = stack.peek();
            if (!first && (node.right == null || pre == node.right)) {
                if (result == stack.pop() && findOne) {
                    result = stack.peek();
                }
                pre = node;
            } else {
                if (first) {
                    first = false;
                } else {
                    node = node.right;
                }
                while (node != null) {
                    stack.push(node);
                    if (node == q || node == p) {
                        if (findOne) {
                            return result;
                        } else {
                            result = node;
                            findOne = true;
                        }
                    }
                    node = node.left;
                }
            }
        }
        return null;
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
