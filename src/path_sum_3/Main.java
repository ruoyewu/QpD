package path_sum_3;

import default_struct.TreeNode;

import java.util.Stack;

import static serialize_and_deserialize_binary_tree.Main.deserialize;

/**
 * User: wuruoye
 * Date: 2019-02-25 12:48
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        String code = "[1,null,2,null,3,null,4,null,5]";
        int sum = 3;
        TreeNode root = deserialize(code);
        System.out.println(pathSum(root, sum));
        System.out.println(pathSum2(root, sum));
    }

    public static int pathSum(TreeNode root, int sum) {
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> tmp = new Stack<>();
        int count = 0;
        TreeNode node = root, pre = null, n;

        while (node != null) {
            stack.push(node);
            node = node.left;
        }
        while (!stack.empty()) {
            node = stack.pop();
            if (node.right == null || pre == node.right) {
                int s = node.val;
                if (s == sum) count++;
                while (!stack.empty()) {
                    n = stack.pop();
                    s += n.val;
                    tmp.push(n);
                    if (s == sum) {
                        count++;
                    }
                }
                while (!tmp.empty()) {
                    stack.push(tmp.pop());
                }
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
        return count;
    }


    public static int pathSum2(TreeNode root, int sum) {
        int depth = depth(root);
        int[] path = new int[depth];
        return sum2(root, sum, 0, path);
    }

    private static int sum2(TreeNode node, int sum, int depth, int[] path) {
        if (node == null) return 0;
        int count = 0;
        path[depth] = node.val;
        int s = 0;
        for (int i = depth; i >= 0; i--) {
            s += path[i];
            if (s == sum) count++;
        }

        count += sum2(node.left, sum, depth+1, path);
        count += sum2(node.right, sum, depth+1, path);
        return count;
    }

    private static int depth(TreeNode node) {
        if (node == null) return 0;
        return Math.max(depth(node.left), depth(node.right)) + 1;
    }


    public static int pathSum3(TreeNode root, int sum) {
        if (root == null) return 0;
        return pathSum3(root.left, sum) + pathSum3(root.right, sum) + sum3(root, sum);
    }

    private static int sum3(TreeNode node, int left) {
        if (node == null) return 0;
        int count = node.val == left ? 1 : 0;
        count += sum3(node.left, left-node.val);
        count += sum3(node.right, left-node.val);
        return count;
    }
}
