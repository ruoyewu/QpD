package path_sum_2;

import default_struct.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * User: wuruoye
 * Date: 2019-05-17 15:34
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> result = new ArrayList<>();
        path(result, new ArrayList<>(), root, sum);
        return result;
    }

    private static void path(List<List<Integer>> result,
                             List<Integer> cur, TreeNode node, int left) {
        if (node == null) return;
        if (node.val == left && node.left == null && node.right == null) {
            cur.add(node.val);
            result.add(cur);
        } else {
            List<Integer> right = new ArrayList<>(cur);
            right.add(node.val);
            path(result, right, node.right, left-node.val);
            cur.add(node.val);
            path(result, cur, node.left, left-node.val);
        }
    }

    private static void path2(List<List<Integer>> result,
                              List<Integer> cur, TreeNode node, int left) {
        if (node == null) return;
        cur.add(node.val);

        path2(result, cur, node.left, left-node.val);
        path2(result, cur, node.right, left-node.val);
        if (node.val == left && node.left == null && node.right == null) {
            result.add(new ArrayList<>(cur));
        }

        cur.remove(cur.size()-1);
    }
}
