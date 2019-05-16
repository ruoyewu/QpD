package binary_tree_level_order_traversal_2;

import default_struct.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * User: wuruoye
 * Date: 2019-05-12 10:20
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        List<TreeNode> last = new ArrayList<>(), cur = new ArrayList<>(), tmp;
        if (root != null) last.add(root);

        while (!last.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            for (TreeNode node : last) {
                level.add(node.val);
                if (node.left != null) cur.add(node.left);
                if (node.right != null) cur.add(node.right);
            }
            result.add(0, level);
            tmp = last;
            last = cur;
            cur = tmp;
            cur.clear();
        }

        return result;
    }
}
