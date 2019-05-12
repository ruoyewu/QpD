package same_tree;

import default_struct.TreeNode;

/**
 * User: wuruoye
 * Date: 2019-05-11 14:29
 * Description:
 */
public class Main {
    public static void main(String[] args) {

    }

    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        if (p.val != q.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
