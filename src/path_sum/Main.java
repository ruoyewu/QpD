package path_sum;

import default_struct.TreeNode;

/**
 * User: wuruoye
 * Date: 2019-05-16 22:28
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        TreeNode root = TreeNode.toTree("[1,-2,-3,1,3,-2,null,null,null,-1]");
        System.out.println(hasPathSum(root, -1));
    }

    public static boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) return false;
        int val = root.val;
        int left = sum - val;
        if (left == 0 && root.left == null && root.right == null) {
            return true;
        } else {
            return hasPathSum(root.left, left) || hasPathSum(root.right, left);
        }
    }
}
