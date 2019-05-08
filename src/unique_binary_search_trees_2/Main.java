package unique_binary_search_trees_2;

import default_struct.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * User: wuruoye
 * Date: 2019-05-08 15:12
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(generateTrees(10));
    }

    public static List<TreeNode> generateTrees(int n) {
        if (n <= 0) return new ArrayList<>();
        return generate(1, n);
    }

    private static List<TreeNode> generate(int start, int end) {
        if (start > end) return null;

        List<TreeNode> result = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            List<TreeNode> left = generate(start, i-1);
            List<TreeNode> right = generate(i+1, end);

            if (left != null && right != null) {
                for (TreeNode l : left) {
                    for (TreeNode r : right) {
                        TreeNode root = new TreeNode(i);
                        root.left = l;
                        root.right = r;
                        result.add(root);
                    }
                }
            } else if (left != null) {
                for (TreeNode l : left) {
                    TreeNode root = new TreeNode(i);
                    root.left = l;
                    result.add(root);
                }
            } else if (right != null) {
                for (TreeNode r : right) {
                    TreeNode root = new TreeNode(i);
                    root.right = r;
                    result.add(root);
                }
            } else {
                result.add(new TreeNode(i));
            }
        }

        return result;
    }
}
