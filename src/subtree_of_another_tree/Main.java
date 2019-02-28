package subtree_of_another_tree;

import default_struct.TreeNode;

import static serialize_and_deserialize_binary_tree.Main.deserialize;

/**
 * User: wuruoye
 * Date: 2019-02-28 18:59
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        TreeNode s = deserialize("[1,1,null]");
        TreeNode t = deserialize("[1]");
        System.out.println(isSubtree(s, t));
    }

    public static boolean isSubtree(TreeNode s, TreeNode t) {
        if (s != null && t != null) {
            if (s.val == t.val && (isEqual(s.left, t.left) && isEqual(s.right, t.right))) {
                return true;
            }
            return isSubtree(s.left, t) || isSubtree(s.right, t);
        }
        return t == s;
    }

    private static boolean isEqual(TreeNode s, TreeNode t) {
        if (s == null && t == null) {
            return true;
        } else if (s == null || t == null) {
            return false;
        } else {
            return s.val == t.val && isEqual(s.left, t.left) && isEqual(s.right, t.right);
        }
    }
}
