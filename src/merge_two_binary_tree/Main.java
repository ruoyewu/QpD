package merge_two_binary_tree;

import default_struct.TreeNode;

/**
 * User: wuruoye
 * Date: 2019-03-01 13:54
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
            return null;
        } else if (t1 == null) {
            return t2;
        } else if (t2 == null) {
            return t1;
        } else {
            t1.val += t2.val;
            t1.left = mergeTrees(t1.left, t2.left);
            t1.right = mergeTrees(t1.right, t2.right);
            return t1;
        }
    }
}
