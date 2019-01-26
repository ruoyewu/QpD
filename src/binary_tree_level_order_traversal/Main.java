package binary_tree_level_order_traversal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * User: wuruoye
 * Date: 2019/1/26 14:54
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        LinkedList<TreeNode> list = new LinkedList<>();
        TreeNode node;
        list.add(root);
        int left;
        while (!list.isEmpty()) {
            left = list.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < left; i++) {
                node = list.removeFirst();
                level.add(node.val);
                if (node.left != null) {
                    list.add(node.left);
                }
                if (node.right != null) {
                    list.add(node.right);
                }
            }
            result.add(level);
        }
        return result;
    }

    public static List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        level(result, root, 1);
        return result;
    }

    public static void level(List<List<Integer>> result, TreeNode node, int level) {
        if (node != null) {
            while (result.size() < level) {
                result.add(new ArrayList<>());
            }
            result.get(level-1).add(node.val);
            level(result, node.left, level+1);
            level(result, node.right, level+1);
        }
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
