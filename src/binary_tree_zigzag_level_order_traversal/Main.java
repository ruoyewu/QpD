package binary_tree_zigzag_level_order_traversal;

import default_struct.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * User: wuruoye
 * Date: 2019-05-11 14:58
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) return new ArrayList<>();

        List<List<Integer>> result = new ArrayList<>();
        List<TreeNode> last = new ArrayList<>(), cur = new ArrayList<>(), tmp;
        last.add(root);

        boolean reverse = false;
        while (!last.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            for (int i = last.size()-1; i >= 0; i--) {
                TreeNode node = last.get(i);
                level.add(node.val);
                TreeNode left = reverse ? node.right : node.left;
                TreeNode right = reverse ? node.left : node.right;
                if (left != null) {
                    cur.add(left);
                }
                if (right != null) {
                    cur.add(right);
                }
            }

            tmp = last;
            last = cur;
            cur = tmp;
            cur.clear();
            reverse = !reverse;
            result.add(level);
        }

        return result;
    }

    public static List<List<Integer>> zigzagLevelOrder2(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        traversal(result, 0, root);
        return result;
    }

    private static void traversal(List<List<Integer>> result, int level, TreeNode node) {
        if (node == null) return;

        while (result.size() <= level) {
            result.add(new ArrayList<>());
        }

        if (level % 2 == 0) {
            result.get(level).add(node.val);
        } else {
            result.get(level).add(0, node.val);
        }

        traversal(result, level+1, node.left);
        traversal(result, level+1, node.right);
    }
}
